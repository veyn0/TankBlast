package de.tankblast.texture;

import de.tankblast.protocol.dto.player.PlayerInfo;
import de.tankblast.texture.text.TextInfo;
import de.tankblast.texture.text.TextTextureCreator;

import java.awt.*;
import java.util.List;

public class PlayerUtils {


    public static Texture createPlayerInfoTexture(Texture background, PlayerInfo playerInfo){
        return TextTextureCreator.createTextureWithText(background, "SansSerif", List.of(
                new TextInfo(playerInfo.getName(), 20,88, 80, Color.WHITE)
        ));
    };

}
