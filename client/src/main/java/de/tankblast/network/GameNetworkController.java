package de.tankblast.network;

import de.tankblast.app.TankBlastClientApplication;
import de.tankblast.protocol.dto.game.lobby.startLobbyRequest;
import de.tankblast.protocol.packet.play.ClientBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ClientBoundGameOverPacket;
import de.tankblast.protocol.packet.play.ClientBoundInitGamePacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerHitPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundStartLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ClientBoundLobbyUpdatePacket;
import xyz.wireway.protocol.Packet;
import xyz.wireway.service.WireWay;
import xyz.wireway.service.asyncpacketstream.AsyncPacketListener;

import java.util.UUID;

public class GameNetworkController implements AsyncPacketListener {

    private final WireWay wireWay;

    private final UUID playerId;

    private final TankBlastClientApplication clientApplication;

    public GameNetworkController(WireWay wireWay, UUID playerId, TankBlastClientApplication clientApplication) {
        this.wireWay = wireWay;
        this.playerId = playerId;
        this.clientApplication = clientApplication;
        wireWay.addAsyncPacketChannelListener(this);
    }

    @Override
    public void onPacketReceive(Packet p) {
        if (p instanceof ClientBoundInitGamePacket packet) {
            clientApplication.startGameSession(packet.getMap(), packet.getPlayers());
        } else if (p instanceof ClientBoundPlayerStatePacket packet) {
            clientApplication.getGameSessionManager().onRemotePlayerState(packet.getPlayerId(), packet.getX(), packet.getY(), packet.getRotation());
        } else if (p instanceof ClientBoundBulletSpawnPacket packet) {
            clientApplication.getGameSessionManager().onRemoteBulletSpawn(packet.getShooterId(), packet.getX(), packet.getY(), packet.getDirX(), packet.getDirY());
        } else if (p instanceof ClientBoundGameOverPacket packet) {
            clientApplication.onGameOver(packet.getWinnerId(), packet.getWinnerName());
        } else if (p instanceof ClientBoundLobbyUpdatePacket packet) {
            clientApplication.getLobbyScreen().onLobbyUpdate(packet.getLobbyInfo());
        }
    }

    public void requestStartLobby(){
        wireWay.sendPacketAsync(new ServerBoundStartLobbyRequestPacket(new startLobbyRequest(playerId)));
    }

    public void sendPlayerState(double x, double y, double rotation){
        wireWay.sendPacketAsync(new ServerBoundPlayerStatePacket(x, y, rotation));
    }

    public void sendBulletSpawn(double x, double y, double dirX, double dirY){
        wireWay.sendPacketAsync(new ServerBoundBulletSpawnPacket(x, y, dirX, dirY));
    }

    public void sendPlayerHit(UUID targetPlayerId){
        wireWay.sendPacketAsync(new ServerBoundPlayerHitPacket(targetPlayerId));
    }

}
