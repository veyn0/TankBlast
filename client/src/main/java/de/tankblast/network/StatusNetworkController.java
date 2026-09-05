package de.tankblast.network;

import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.JoinLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;
import de.tankblast.protocol.packet.status.ClientBoundAvailableGamesResponse;
import de.tankblast.protocol.packet.status.ClientBoundLobbyInfoResponsePacket;
import de.tankblast.protocol.packet.status.ServerBoundAvailableGameRequest;
import de.tankblast.protocol.packet.status.ServerBoundCreateLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ServerBoundJoinLobbyRequestPacket;
import xyz.wireway.service.packetstream.PacketStream;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class StatusNetworkController {

    private final PacketStream statusPacketStream;

    private final UUID playerId;

    public StatusNetworkController(PacketStream statusPacketStream, UUID playerId) {
        this.statusPacketStream = statusPacketStream;
        this.playerId = playerId;
    }

    public List<AvailableGame> getAvailableGames(){
        CompletableFuture<List<AvailableGame>> future = new CompletableFuture<>();
        statusPacketStream.sendPacket(new ServerBoundAvailableGameRequest(), response -> {
            future.complete(((ClientBoundAvailableGamesResponse) response).getAvailableGames());
        });
        try {
            return future.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            return List.of();
        }
    }

    public void createLobby(CreateLobbyRequest request, Consumer<LobbyInfo> lobbyInfoConsumer){
        statusPacketStream.sendPacket(new ServerBoundCreateLobbyRequestPacket(request), response -> {
            lobbyInfoConsumer.accept(((ClientBoundLobbyInfoResponsePacket) response).getLobbyInfo());
        });
    }

    public void requestJoinLobby(int lobbyId, Consumer<LobbyInfo> lobbyInfoConsumer){
        statusPacketStream.sendPacket(new ServerBoundJoinLobbyRequestPacket(new JoinLobbyRequest(playerId, lobbyId)), response -> {
            lobbyInfoConsumer.accept(((ClientBoundLobbyInfoResponsePacket) response).getLobbyInfo());
        });
    }

}
