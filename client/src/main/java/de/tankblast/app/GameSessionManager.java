package de.tankblast.app;

import de.tankblast.game.GameLoop;
import de.tankblast.game.World;
import de.tankblast.input.InputManager;
import de.tankblast.model.entity.Bullet;
import de.tankblast.model.entity.Entity;
import de.tankblast.model.entity.Obstacle;
import de.tankblast.model.entity.Player;
import de.tankblast.model.geometry.Vector;
import de.tankblast.render.EntityGraphicsComponent;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.PlayerCenteredCamera;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class GameSessionManager {

    private final TankBlastClientApplication clientApplication;
    private final InputManager inputManager;
    private final PlayerCenteredCamera camera;

    private World world;
    private Player localPlayer;
    private GameLoop gameLoop;

    private Texture playerTexture;
    private Texture bulletTexture;
    private Texture obstacleTexture;

    public GameSessionManager(TankBlastClientApplication clientApplication,
                              InputManager inputManager,
                              PlayerCenteredCamera camera) {
        this.clientApplication = clientApplication;
        this.inputManager = inputManager;
        this.camera = camera;
    }

    public void startGameSession() {
        ImageTextureLoader loader = new ImageTextureLoader();
        this.playerTexture = loader.loadResource("textures/entity/player.png");
        this.bulletTexture = loader.loadResource("textures/entity/bullet.png");
        this.obstacleTexture = loader.loadResource("textures/entity/obstacle.png");

        this.world = new World();
        this.localPlayer = new Player();
        world.addEntity(localPlayer);

        // a few obstacles for bullets to bounce off
        world.addEntity(new Obstacle(new Vector(20, 10, 0), 4));
        world.addEntity(new Obstacle(new Vector(-15, -8, 0), 3));
        world.addEntity(new Obstacle(new Vector(5, 25, 0), 5));

        clientApplication.setCurrentView(null);

        this.gameLoop = new GameLoop(world, inputManager, localPlayer);
        gameLoop.start();
    }

    public void stopGameSession() {
        if (gameLoop != null) gameLoop.stop();
    }

    public boolean isRunning() {
        return world != null;
    }

    /**
     * Moves the camera to follow the local player. Called from the render loop
     * each frame, independent of the game tick. Only the X/Y are tracked; the
     * Z (zoom/distance) is left as configured.
     */
    public void updateCamera() {
        if (localPlayer == null) return;
        Vector pos = localPlayer.getLocation().getPosition();
        camera.setX(pos.getX());
        camera.setY(pos.getY());
    }

    /** Snapshot of renderable components for the (decoupled) render thread. */
    public List<GraphicsComponent> getGraphicsComponents() {
        List<GraphicsComponent> components = new ArrayList<>();
        if (world == null) return components;
        for (Entity entity : world.snapshot()) {
            Texture texture = textureFor(entity);
            if (texture != null) {
                components.add(new EntityGraphicsComponent(entity, texture));
            }
        }
        return components;
    }

    private Texture textureFor(Entity entity) {
        if (entity instanceof Player) return playerTexture;
        if (entity instanceof Bullet) return bulletTexture;
        if (entity instanceof Obstacle) return obstacleTexture;
        return null;
    }

    public World getWorld() {
        return world;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }
}