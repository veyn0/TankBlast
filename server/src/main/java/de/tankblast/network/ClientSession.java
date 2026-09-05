package de.tankblast.network;

import de.tankblast.lobby.Lobby;
import de.tankblast.lobby.LobbyManager;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.JoinLobbyRequest;
import de.tankblast.protocol.packet.play.ClientBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ClientBoundGameOverPacket;
import de.tankblast.protocol.packet.play.ClientBoundInitGamePacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerStatePacket;
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
import xyz.wireway.service.packetstream.PacketStream;

import java.util.UUID;

public class ClientSession {

    private final WireWay wireWay;
    private final PacketStream statusStream;
    private final LobbyManager lobbyManager;

    private UUID playerId;
    private Lobby currentLobby;

    public ClientSession(WireWay wireWay, LobbyManager lobbyManager) {
        this.wireWay = wireWay;
        this.lobbyManager = lobbyManager;
        this.statusStream = wireWay.createPacketChannel(0);
    }

    public void register(){
        statusStream.setListener(this::handleStatusPacket);
        wireWay.addAsyncPacketChannelListener(this::handleAsyncPacket);
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
            playerId = request.getPlayerId();
            Lobby lobby = lobbyManager.getLobby(request.getLobbyId());
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
            currentLobby.registerHit(p.getTargetPlayerId());
            UUID winnerId = currentLobby.getWinnerIfDecided();
            if (winnerId != null && !currentLobby.isFinished()) {
                currentLobby.setFinished(true);
                String winnerName = "Player-" + winnerId.toString().substring(0, 4);
                currentLobby.broadcast(new ClientBoundGameOverPacket(winnerId, winnerName));
                lobbyManager.removeLobby(currentLobby.getId());
            }
        }
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void sendAsync(Packet packet){
        wireWay.sendPacketAsync(packet);
    }

}
