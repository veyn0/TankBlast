package de.tankblast.model.map;

import de.tankblast.model.entity.Obstacle;
import de.tankblast.model.geometry.Vector;

import java.util.List;

public class MapRegistry {

    private static final Vector[] SPAWN_POINTS = {
            new Vector(30, 30, 0),
            new Vector(-30, 30, 0),
            new Vector(30, -30, 0),
            new Vector(-30, -30, 0),
            new Vector(0, 38, 0),
            new Vector(0, -38, 0),
            new Vector(38, 0, 0),
            new Vector(-38, 0, 0)
    };

    public static List<Obstacle> createObstacles(int mapId){
        return switch (mapId) {
            case 1 -> List.of(
                    new Obstacle(new Vector(0, 0, 0), 6),
                    new Obstacle(new Vector(20, 20, 0), 4),
                    new Obstacle(new Vector(-20, 20, 0), 4),
                    new Obstacle(new Vector(20, -20, 0), 4),
                    new Obstacle(new Vector(-20, -20, 0), 4)
            );
            case 2 -> List.of(
                    new Obstacle(new Vector(-20, 0, 0), 3),
                    new Obstacle(new Vector(-10, 0, 0), 3),
                    new Obstacle(new Vector(0, 0, 0), 3),
                    new Obstacle(new Vector(10, 0, 0), 3),
                    new Obstacle(new Vector(20, 0, 0), 3)
            );
            default -> List.of(
                    new Obstacle(new Vector(20, 10, 0), 4),
                    new Obstacle(new Vector(-15, -8, 0), 3),
                    new Obstacle(new Vector(5, 25, 0), 5)
            );
        };
    }

    public static Vector getSpawnPoint(int mapId, int index){
        return SPAWN_POINTS[index % SPAWN_POINTS.length];
    }

}
