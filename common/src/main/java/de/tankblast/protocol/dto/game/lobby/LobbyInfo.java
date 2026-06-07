package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.player.PlayerInfo;

import java.util.List;

public class LobbyInfo {

    private String name;
    private int lobbyId, maxPlayers, map;
    private List<PlayerInfo> players;

}
