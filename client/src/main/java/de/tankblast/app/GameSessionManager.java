package de.tankblast.app;

import de.tankblast.Constants;
import de.tankblast.game.GameLoop;
import de.tankblast.game.World;
import de.tankblast.input.InputManager;
import de.tankblast.model.entity.Bullet;
import de.tankblast.model.entity.Entity;
import de.tankblast.model.entity.Obstacle;
import de.tankblast.model.entity.Player;
import de.tankblast.model.geometry.Vector;
import de.tankblast.model.map.MapRegistry;
import de.tankblast.network.GameNetworkController;
import de.tankblast.protocol.dto.player.PlayerInfo;
import de.tankblast.render.EntityGraphicsComponent;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.LivesHudComponent;
import de.tankblast.render.PlayerCenteredCamera;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.PlayerColorPalette;
import de.tankblast.texture.Texture;
import de.tankblast.texture.TextureUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameSessionManager {

    private final TankBlastClientApplication clientApplication;
    private final InputManager inputManager;
    private final PlayerCenteredCamera camera;
    private final int screenWidth;
    private final int screenHeight;

    private World world;
    private Player localPlayer;
    private GameLoop gameLoop;

    private final Map<UUID, Texture> playerTextures = new HashMap<>();
    private final Map<UUID, Integer> livesByPlayer = new HashMap<>();
    private Texture bulletTexture;
    private Texture obstacleTexture;

    public GameSessionManager(TankBlastClientApplication clientApplication,
                              InputManager inputManager,
                              PlayerCenteredCamera camera,
                              int screenWidth,
                              int screenHeight) {
        this.clientApplication = clientApplication;
        this.inputManager = inputManager;
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void startGameSession(int mapId, List<PlayerInfo> players) {
        ImageTextureLoader loader = new ImageTextureLoader();
        Texture basePlayerTexture = loader.loadResource("textures/entity/player.png");
        this.bulletTexture = loader.loadResource("textures/entity/bullet.png");
        this.obstacleTexture = loader.loadResource("textures/entity/obstacle.png");

        this.world = new World();
        this.playerTextures.clear();
        this.livesByPlayer.clear();

        UUID localPlayerId = clientApplication.getPlayerId();
        for (int i = 0; i < players.size(); i++) {
            PlayerInfo info = players.get(i);
            Player player = new Player(info.getPlayerId(), MapRegistry.getSpawnPoint(mapId, i), 90.0);
            world.addEntity(player);
            playerTextures.put(info.getPlayerId(), TextureUtils.tint(basePlayerTexture, PlayerColorPalette.colourFor(i)));
            livesByPlayer.put(info.getPlayerId(), Constants.STARTING_LIVES);
            if (info.getPlayerId().equals(localPlayerId)) {
                this.localPlayer = player;
            }
        }

        for (Obstacle obstacle : MapRegistry.createObstacles(mapId)) {
            world.addEntity(obstacle);
        }

        clientApplication.setCurrentView(null);

        this.gameLoop = new GameLoop(world, inputManager, localPlayer);

        GameNetworkController networkController = clientApplication.getNetworkManager().getGameNetworkController();
        gameLoop.setOnLocalPlayerTick(player -> {
            Vector position = player.getLocation().getPosition();
            networkController.sendPlayerState(position.getX(), position.getY(), player.getRotation());
        });
        gameLoop.setOnLocalBulletSpawn(bullet -> {
            Vector position = bullet.getLocation().getPosition();
            Vector direction = bullet.getLocation().getVelocity().getDirection();
            networkController.sendBulletSpawn(position.getX(), position.getY(), direction.getX(), direction.getY());
        });
        gameLoop.setOnLocalBulletHit(networkController::sendPlayerHit);

        gameLoop.start();
    }

    public void stopGameSession() {
        if (gameLoop != null) gameLoop.stop();
        gameLoop = null;
        world = null;
        localPlayer = null;
        playerTextures.clear();
        livesByPlayer.clear();
        camera.setX(0);
        camera.setY(0);
    }

    public boolean isRunning() {
        return world != null;
    }

    public void updateCamera() {
        if (localPlayer == null) return;
        Vector pos = localPlayer.getLocation().getPosition();
        camera.setX(pos.getX());
        camera.setY(pos.getY());
    }

    public void onRemotePlayerState(UUID playerId, double x, double y, double rotation) {
        if (world == null) return;
        Player player = world.findPlayer(playerId);
        if (player == null || player == localPlayer) return;
        player.setLocation(player.getLocation().copy(new Vector(x, y, 0)));
        player.setRotation(rotation);
    }

    public void onRemoteBulletSpawn(UUID shooterId, double x, double y, double dirX, double dirY) {
        if (world == null) return;
        world.addEntity(new Bullet(shooterId, new Vector(x, y, 0), new Vector(dirX, dirY, 0), GameLoop.BULLET_SPEED, GameLoop.BULLET_MAX_BOUNCES));
    }

    public void onPlayerEliminated(UUID playerId) {
        if (world == null) return;
        Player player = world.findPlayer(playerId);
        if (player != null) world.removeEntity(player);
        livesByPlayer.put(playerId, 0);
        if (localPlayer != null && localPlayer.getPlayerId().equals(playerId) && gameLoop != null) {
            gameLoop.eliminate();
        }
    }

    public void onPlayerLivesUpdate(UUID playerId, int lives) {
        livesByPlayer.put(playerId, lives);
    }

    public List<GraphicsComponent> getGraphicsComponents() {
        List<GraphicsComponent> components = new ArrayList<>();
        if (world == null) return components;
        for (Entity entity : world.snapshot()) {
            Texture texture = textureFor(entity);
            if (texture != null) {
                components.add(new EntityGraphicsComponent(entity, texture));
            }
        }
        if (localPlayer != null) {
            int lives = livesByPlayer.getOrDefault(localPlayer.getPlayerId(), Constants.STARTING_LIVES);
            components.add(new LivesHudComponent(camera, screenWidth, screenHeight, lives, Constants.STARTING_LIVES));
        }
        return components;
    }

    private Texture textureFor(Entity entity) {
        if (entity instanceof Player player) return playerTextures.get(player.getPlayerId());
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
