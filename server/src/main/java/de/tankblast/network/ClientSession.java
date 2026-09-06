package de.tankblast.network;

import de.tankblast.lobby.Lobby;
import de.tankblast.lobby.LobbyManager;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.JoinLobbyRequest;
import de.tankblast.protocol.packet.play.ClientBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ClientBoundGameOverPacket;
import de.tankblast.protocol.packet.play.ClientBoundInitGamePacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerEliminatedPacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerLivesPacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundAtomBombPacket;
import de.tankblast.protocol.packet.play.ServerBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerHitPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundStartLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ClientBoundAvailableGamesResponse;
import de.tankblast.protocol.packet.status.ClientBoundLobbyInfoResponsePacket;
import de.tankblast.protocol.packet.status.ClientBoundLobbyUpdatePacket;
import de.tankblast.protocol.packet.status.ServerBoundAvailableGameRequest;
import de.tankblast.protocol.packet.status.ServerBoundCreateLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ServerBoundJoinLobbyRequestPacket;
import xyz.wireway.protocol.Packet;
import xyz.wireway.service.WireWay;
import xyz.wireway.service.PacketStream;

import java.util.UUID;

public class ClientSession {

    private final WireWay wireWay;
    private final PacketStream statusStream;
    private final LobbyManager lobbyManager;

    private volatile UUID playerId;
    private volatile Lobby currentLobby;

    public ClientSession(WireWay wireWay, LobbyManager lobbyManager) {
        this.wireWay = wireWay;
        this.lobbyManager = lobbyManager;
        this.statusStream = wireWay.openStream(0);
    }

    public void register(){
        statusStream.setListener(this::handleStatusPacket);
        wireWay.addPacketListener(this::handleAsyncPacket);
    }

    private Packet handleStatusPacket(Packet packet){
        if (packet instanceof ServerBoundAvailableGameRequest) {
            return new ClientBoundAvailableGamesResponse(lobbyManager.listAvailableGames());
        }
        if (packet instanceof ServerBoundCreateLobbyRequestPacket p) {
            CreateLobbyRequest request = p.getRequest();
            playerId = request.getPlayerId();
            currentLobby = lobbyManager.createLobby(this, request);
            return new ClientBoundLobbyInfoResponsePacket(currentLobby.toLobbyInfo());
        }
        if (packet instanceof ServerBoundJoinLobbyRequestPacket p) {
            JoinLobbyRequest request = p.getRequest();
            Lobby lobby = lobbyManager.getLobby(request.getLobbyId());
            if (lobby == null || lobby.isStarted() || lobby.isFull()) {
                return currentLobby != null ? new ClientBoundLobbyInfoResponsePacket(currentLobby.toLobbyInfo()) : null;
            }
            playerId = request.getPlayerId();
            currentLobby = lobby;
            lobby.addPlayer(this);
            lobby.broadcastExcept(playerId, new ClientBoundLobbyUpdatePacket(lobby.toLobbyInfo()));
            return new ClientBoundLobbyInfoResponsePacket(lobby.toLobbyInfo());
        }
        return null;
    }

    private void handleAsyncPacket(Packet packet){
        if (currentLobby == null) return;

        if (packet instanceof ServerBoundStartLobbyRequestPacket p) {
            if (!currentLobby.isOwner(p.getRequest().getPlayerId())) return;
            currentLobby.setStarted(true);
            currentLobby.broadcast(new ClientBoundInitGamePacket(currentLobby.getMap(), currentLobby.getPlayerInfos()));
            return;
        }
        if (packet instanceof ServerBoundPlayerStatePacket p) {
            currentLobby.broadcastExcept(playerId, new ClientBoundPlayerStatePacket(playerId, p.getX(), p.getY(), p.getRotation()));
            return;
        }
        if (packet instanceof ServerBoundBulletSpawnPacket p) {
            currentLobby.broadcastExcept(playerId, new ClientBoundBulletSpawnPacket(playerId, p.getX(), p.getY(), p.getDirX(), p.getDirY()));
            return;
        }
        if (packet instanceof ServerBoundPlayerHitPacket p) {
            UUID targetId = p.getTargetPlayerId();
            boolean eliminated = currentLobby.registerHit(targetId);
            currentLobby.broadcast(new ClientBoundPlayerLivesPacket(targetId, currentLobby.getLives(targetId)));
            if (eliminated) {
                currentLobby.broadcast(new ClientBoundPlayerEliminatedPacket(targetId));
            }
            checkForWinner();
            return;
        }
        if (packet instanceof ServerBoundAtomBombPacket) {
            triggerAtomBomb();
        }
    }

    private void triggerAtomBomb(){
        if (currentLobby.isFinished()) return;
        currentLobby.setFinished(true);
        for (UUID id : currentLobby.eliminateAll()) {
            currentLobby.broadcast(new ClientBoundPlayerEliminatedPacket(id));
        }
        currentLobby.broadcast(new ClientBoundGameOverPacket(null, null));
        lobbyManager.removeLobby(currentLobby.getId());
    }

    public void onDisconnect(){
        Lobby lobby = currentLobby;
        UUID id = playerId;
        if (lobby == null || id == null) return;

        if (lobby.isStarted()) {
            if (!lobby.isFinished() && lobby.eliminate(id)) {
                lobby.broadcast(new ClientBoundPlayerEliminatedPacket(id));
            }
            lobby.removePlayer(id);
            checkForWinner();
        } else {
            lobby.leaveLobby(id);
            if (lobby.isEmpty()) {
                lobbyManager.removeLobby(lobby.getId());
            } else {
                lobby.broadcastExcept(id, new ClientBoundLobbyUpdatePacket(lobby.toLobbyInfo()));
            }
        }
    }

    private void checkForWinner(){
        UUID winnerId = currentLobby.getWinnerIfDecided();
        if (winnerId != null && !currentLobby.isFinished()) {
            currentLobby.setFinished(true);
            String winnerName = "Player-" + winnerId.toString().substring(0, 4);
            currentLobby.broadcast(new ClientBoundGameOverPacket(winnerId, winnerName));
            lobbyManager.removeLobby(currentLobby.getId());
        }
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void sendAsync(Packet packet){
        wireWay.sendPacket(packet);
    }

}
