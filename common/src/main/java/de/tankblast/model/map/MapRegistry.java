package de.tankblast.model.map;

import de.tankblast.model.entity.Obstacle;
import de.tankblast.model.geometry.Vector;

import java.util.ArrayList;
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

    private static final double WALL_RADIUS = 1.6;
    private static final double WALL_SPACING = 2.4;

    public static List<Obstacle> createObstacles(int mapId){
        return switch (mapId) {
            case 1 -> diamondChambers();
            case 2 -> serpentineLanes();
            default -> crossArena();
        };
    }

    public static Vector getSpawnPoint(int mapId, int index){
        return SPAWN_POINTS[index % SPAWN_POINTS.length];
    }

    private static List<Obstacle> crossArena(){
        List<Obstacle> result = new ArrayList<>();
        result.addAll(wall(-26, 0, -9, 0));
        result.addAll(wall(9, 0, 26, 0));
        result.addAll(wall(0, -26, 0, -9));
        result.addAll(wall(0, 9, 0, 26));
        result.add(new Obstacle(new Vector(16, 16, 0), 2.5));
        result.add(new Obstacle(new Vector(-16, 16, 0), 2.5));
        result.add(new Obstacle(new Vector(16, -16, 0), 2.5));
        result.add(new Obstacle(new Vector(-16, -16, 0), 2.5));
        return result;
    }

    private static List<Obstacle> diamondChambers(){
        List<Obstacle> result = new ArrayList<>();
        result.addAll(wallWithGap(0, 24, 24, 0));
        result.addAll(wallWithGap(24, 0, 0, -24));
        result.addAll(wallWithGap(0, -24, -24, 0));
        result.addAll(wallWithGap(-24, 0, 0, 24));
        result.add(new Obstacle(new Vector(0, 0, 0), 4));
        return result;
    }

    private static List<Obstacle> serpentineLanes(){
        List<Obstacle> result = new ArrayList<>();
        result.addAll(wall(-32, 16, 6, 16));
        result.addAll(wall(-6, 0, 32, 0));
        result.addAll(wall(-32, -16, 6, -16));
        return result;
    }

    private static List<Obstacle> wallWithGap(double x1, double y1, double x2, double y2){
        List<Obstacle> result = new ArrayList<>();
        double dx = x2 - x1;
        double dy = y2 - y1;
        result.addAll(wall(x1, y1, x1 + dx * 0.4, y1 + dy * 0.4));
        result.addAll(wall(x1 + dx * 0.6, y1 + dy * 0.6, x2, y2));
        return result;
    }

    private static List<Obstacle> wall(double x1, double y1, double x2, double y2){
        List<Obstacle> result = new ArrayList<>();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.hypot(dx, dy);
        int segments = Math.max(1, (int) Math.round(length / WALL_SPACING));
        for (int i = 0; i <= segments; i++) {
            double t = (double) i / segments;
            result.add(new Obstacle(new Vector(x1 + dx * t, y1 + dy * t, 0), WALL_RADIUS));
        }
        return result;
    }

}
