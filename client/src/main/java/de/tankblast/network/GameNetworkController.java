package de.tankblast.network;

import de.tankblast.app.TankBlastClientApplication;
import de.tankblast.protocol.dto.game.lobby.startLobbyRequest;
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
import de.tankblast.protocol.packet.status.ClientBoundLobbyUpdatePacket;
import xyz.wireway.protocol.Packet;
import xyz.wireway.service.WireWay;
import xyz.wireway.service.AsyncPacketListener;

import java.util.UUID;

public class GameNetworkController implements AsyncPacketListener {

    private final WireWay wireWay;

    private final UUID playerId;

    private final TankBlastClientApplication clientApplication;

    public GameNetworkController(WireWay wireWay, UUID playerId, TankBlastClientApplication clientApplication) {
        this.wireWay = wireWay;
        this.playerId = playerId;
        this.clientApplication = clientApplication;
        wireWay.addPacketListener(this);
    }

    @Override
    public void onPacketReceive(Packet p) {
        if (p instanceof ClientBoundInitGamePacket packet) {
            clientApplication.startGameSession(packet.getMap(), packet.getPlayers());
        } else if (p instanceof ClientBoundPlayerStatePacket packet) {
            clientApplication.getGameSessionManager().onRemotePlayerState(packet.getPlayerId(), packet.getX(), packet.getY(), packet.getRotation());
        } else if (p instanceof ClientBoundBulletSpawnPacket packet) {
            clientApplication.getGameSessionManager().onRemoteBulletSpawn(packet.getShooterId(), packet.getX(), packet.getY(), packet.getDirX(), packet.getDirY());
        } else if (p instanceof ClientBoundPlayerLivesPacket packet) {
            clientApplication.getGameSessionManager().onPlayerLivesUpdate(packet.getPlayerId(), packet.getLives());
        } else if (p instanceof ClientBoundPlayerEliminatedPacket packet) {
            clientApplication.getGameSessionManager().onPlayerEliminated(packet.getPlayerId());
        } else if (p instanceof ClientBoundGameOverPacket packet) {
            clientApplication.onGameOver(packet.getWinnerId(), packet.getWinnerName());
        } else if (p instanceof ClientBoundLobbyUpdatePacket packet) {
            clientApplication.getLobbyScreen().onLobbyUpdate(packet.getLobbyInfo());
        }
    }

    public void requestStartLobby(){
        wireWay.sendPacket(new ServerBoundStartLobbyRequestPacket(new startLobbyRequest(playerId)));
    }

    public void sendPlayerState(double x, double y, double rotation){
        wireWay.sendPacket(new ServerBoundPlayerStatePacket(x, y, rotation));
    }

    public void sendBulletSpawn(double x, double y, double dirX, double dirY){
        wireWay.sendPacket(new ServerBoundBulletSpawnPacket(x, y, dirX, dirY));
    }

    public void sendPlayerHit(UUID targetPlayerId){
        wireWay.sendPacket(new ServerBoundPlayerHitPacket(targetPlayerId));
    }

    public void sendAtomBomb(){
        wireWay.sendPacket(new ServerBoundAtomBombPacket());
    }

}
