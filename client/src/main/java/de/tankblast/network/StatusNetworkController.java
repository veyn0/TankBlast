package de.tankblast.network;

import de.tankblast.protocol.dto.AvailableGame;

import java.util.List;

public class StatusNetworkController {

    public List<AvailableGame> getAvailableGames(){

        //TODO: implement real networking

        return List.of(new AvailableGame(0, 0, 3 ,"PlaceholderGame", 8));
    }

}
