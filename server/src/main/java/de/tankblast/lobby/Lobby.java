package de.tankblast.lobby;

import de.tankblast.network.ClientSession;
import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;
import de.tankblast.protocol.dto.player.PlayerInfo;
import xyz.wireway.protocol.Packet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Lobby {

    private static final int STARTING_LIVES = 5;

    private final int id;
    private final String name;
    private final int maxPlayers;
    private final int map;
    private final UUID ownerId;

    private final Map<UUID, ClientSession> players = new LinkedHashMap<>();
    private final Map<UUID, Integer> lives = new LinkedHashMap<>();

    private boolean started;
    private boolean finished;

    public Lobby(int id, String name, int maxPlayers, int map, UUID ownerId) {
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.map = map;
        this.ownerId = ownerId;
    }

    public synchronized void addPlayer(ClientSession session){
        players.put(session.getPlayerId(), session);
        lives.put(session.getPlayerId(), STARTING_LIVES);
    }

    public synchronized void removePlayer(UUID playerId){
        players.remove(playerId);
    }

    public synchronized void leaveLobby(UUID playerId){
        players.remove(playerId);
        lives.remove(playerId);
    }

    public synchronized boolean isEmpty(){
        return players.isEmpty();
    }

    public synchronized boolean isFull(){
        return players.size() >= maxPlayers;
    }

    public synchronized List<PlayerInfo> getPlayerInfos(){
        List<PlayerInfo> result = new ArrayList<>();
        for (UUID playerId : players.keySet()) {
            result.add(new PlayerInfo(playerId, "Player-" + playerId.toString().substring(0, 4)));
        }
        return result;
    }

    public synchronized LobbyInfo toLobbyInfo(){
        return new LobbyInfo(name, id, maxPlayers, map, getPlayerInfos(), ownerId);
    }

    public synchronized AvailableGame toAvailableGame(){
        return new AvailableGame(id, players.size(), map, name, maxPlayers);
    }

    public synchronized void broadcast(Packet packet){
        for (ClientSession session : players.values()) session.sendAsync(packet);
    }

    public synchronized void broadcastExcept(UUID exceptPlayerId, Packet packet){
        for (ClientSession session : players.values()) {
            if (!session.getPlayerId().equals(exceptPlayerId)) session.sendAsync(packet);
        }
    }

    public synchronized boolean registerHit(UUID targetPlayerId){
        Integer remaining = lives.get(targetPlayerId);
        if (remaining == null || remaining <= 0) return false;
        remaining--;
        lives.put(targetPlayerId, remaining);
        return remaining == 0;
    }

    public synchronized boolean eliminate(UUID playerId){
        Integer remaining = lives.get(playerId);
        if (remaining == null || remaining <= 0) return false;
        lives.put(playerId, 0);
        return true;
    }

    public synchronized UUID getWinnerIfDecided(){
        List<UUID> alive = new ArrayList<>();
        for (Map.Entry<UUID, Integer> entry : lives.entrySet()) {
            if (entry.getValue() > 0) alive.add(entry.getKey());
        }
        if (lives.size() > 1 && alive.size() == 1) return alive.get(0);
        return null;
    }

    public boolean isOwner(UUID playerId){
        return ownerId.equals(playerId);
    }

    public int getId() {
        return id;
    }

    public int getMap() {
        return map;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
