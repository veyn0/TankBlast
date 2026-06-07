package de.tankblast.texture;

public class MapUtils {

    public static Texture createMapPreview(int mapId){
        return new ImageTextureLoader().loadResource("textures/map/preview/preview_"+mapId + ".png");

    }


}
