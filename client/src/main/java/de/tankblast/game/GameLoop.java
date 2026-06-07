package de.tankblast.game;

import de.tankblast.Constants;
import de.tankblast.input.InputContext;
import de.tankblast.input.InputManager;
import de.tankblast.input.InputMapper;
import de.tankblast.model.entity.Bullet;
import de.tankblast.model.entity.Entity;
import de.tankblast.model.entity.Player;
import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Central fixed-step game loop. Runs on its own thread at {@link #TICKS_PER_SECOND}
 * and is the single point where inputs, the world, incoming/outgoing packets and
 * physics converge. Rendering is decoupled and reads {@link World#snapshot()}.
 *
 * Per tick, in order:
 *   1. drain & apply incoming packets
 *   2. read input, apply rotation/movement/shooting to the local player
 *   3. step physics for every entity (movement + collision/bounce)
 *   4. flush outgoing packets (no-op against the empty network shell)
 *   5. (world is already updated; render thread picks it up asynchronously)
 */
public class GameLoop implements Runnable {

    public static final int TICKS_PER_SECOND = 60;
    private static final long NANOS_PER_TICK = 1_000_000_000L / TICKS_PER_SECOND;

    private static final double BULLET_SPEED = 20.0;   // world units per second
    private static final int BULLET_MAX_BOUNCES = 3;
    private static final long SHOOT_COOLDOWN_NANOS = 250_000_000L; // 0.25s

    private final World world;
    private final InputManager inputManager;
    private final Player localPlayer;

    private final Queue<Runnable> incomingPackets = new ConcurrentLinkedQueue<>();
    private final Queue<Runnable> outgoingPackets = new ConcurrentLinkedQueue<>();

    private volatile boolean running;
    private Thread thread;

    private long lastShotAt = 0;

    public GameLoop(World world, InputManager inputManager, Player localPlayer) {
        this.world = world;
        this.inputManager = inputManager;
        this.localPlayer = localPlayer;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "game-loop");
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) thread.interrupt();
    }

    /** Called from the network thread to enqueue work to run on the next tick. */
    public void enqueueIncoming(Runnable packetHandler) {
        incomingPackets.add(packetHandler);
    }

    @Override
    public void run() {
        long previous = System.nanoTime();
        while (running) {
            long tickStart = System.nanoTime();
            long timeSinceLastTickNanos = tickStart - previous;
            previous = tickStart;

            try {
                tick(timeSinceLastTickNanos, tickStart);
            } catch (Exception e) {
                // a single bad tick shouldn't kill the loop
                e.printStackTrace();
            }

            long elapsed = System.nanoTime() - tickStart;
            long sleep = NANOS_PER_TICK - elapsed;
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep / 1_000_000L, (int) (sleep % 1_000_000L));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void tick(long timeSinceLastTickNanos, long now) {
        // 1. incoming packets
        Runnable handler;
        while ((handler = incomingPackets.poll()) != null) {
            handler.run();
        }

        // 2. input -> local player
        InputContext input = new InputContext(inputManager.onTick(now), timeSinceLastTickNanos);
        applyInput(input, now);

        // 3. physics for every entity
        stepPhysics(timeSinceLastTickNanos);

        // 4. outgoing packets (drains into the empty shell for now)
        Runnable out;
        while ((out = outgoingPackets.poll()) != null) {
            out.run();
        }
    }

    private void applyInput(InputContext input, long now) {
        // rotation
        double rotationDelta = InputMapper.getRotationDegrees(input, Constants.MAX_ROTATION_SPEED);
        localPlayer.setRotation(localPlayer.getRotation() - rotationDelta); // D (right) turns clockwise

        // movement along current facing
        double distance = InputMapper.getMovementDistance(input, Constants.MOVEMENT_SPEED);
        if (distance != 0) {
            double angleRad = Math.toRadians(localPlayer.getRotation());
            Vector forward = new Vector(Math.cos(angleRad), Math.sin(angleRad), 0);
            Vector displacement = forward.scale(distance);
            Vector next = PhysicsService.computeNextValidPosition(localPlayer, world, displacement);
            localPlayer.setLocation(localPlayer.getLocation().copy(next));
        }

        // shooting
        if (InputMapper.isShootPressed(input) && now - lastShotAt >= SHOOT_COOLDOWN_NANOS) {
            spawnBullet();
            lastShotAt = now;
        }
    }

    private void spawnBullet() {
        double angleRad = Math.toRadians(localPlayer.getRotation());
        Vector dir = new Vector(Math.cos(angleRad), Math.sin(angleRad), 0);
        // spawn just outside the player's radius so it doesn't self-collide
        Vector spawn = localPlayer.getLocation().getPosition()
                .add(dir.scale(localPlayer.getRadius() + 0.5));
        world.addEntity(new Bullet(null, spawn, dir, BULLET_SPEED, BULLET_MAX_BOUNCES));
    }

    private void stepPhysics(long timeSinceLastTickNanos) {
        double dt = timeSinceLastTickNanos / 1_000_000_000.0;
        List<Entity> toRemove = new ArrayList<>();

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Bullet bullet) {
                if (bullet.isDead()) {
                    toRemove.add(bullet);
                    continue;
                }
                Vector displacement = bullet.getLocation().getVelocity().asVector().scale(dt);
                Vector next = PhysicsService.computeNextValidPosition(bullet, world, displacement);
                bullet.setLocation(bullet.getLocation().copy(next));
                if (bullet.isDead()) {
                    toRemove.add(bullet);
                }
            }
        }

        for (Entity e : toRemove) {
            world.removeEntity(e);
        }
    }
}