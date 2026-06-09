package de.tankblast.game;

import de.tankblast.model.entity.Bullet;
import de.tankblast.model.entity.Entity;
import de.tankblast.model.geometry.Vector;

import java.util.List;

public class PhysicsService {

    public static Vector computeNextValidPosition(Entity entity, World world, Vector displacement) {
        Vector current = entity.getLocation().getPosition();
        Vector target = current.add(displacement);

        if (entity instanceof Bullet bullet) {
            return resolveBullet(bullet, world, current, target);
        }
        return resolveBlocking(entity, world, current, target);
    }

    private static Vector resolveBullet(Bullet bullet, World world, Vector current, Vector target) {
        Entity hit = firstCollision(bullet, world, target);
        if (hit == null) {
            return target;
        }
        Vector normal = current.subtract(hit.getLocation().getPosition()).normalized();
        Vector travel = target.subtract(current);
        Vector reflected = travel.reflect(normal);

        bullet.registerBounce();
        bullet.setLocation(bullet.getLocation().copy(
                bullet.getLocation().getVelocity().copy(reflected.normalized())));

        double separation = bullet.getRadius() + hit.getRadius() + 0.001;
        return hit.getLocation().getPosition().add(normal.scale(separation));
    }

    private static Vector resolveBlocking(Entity entity, World world, Vector current, Vector target) {
        Entity hit = firstCollision(entity, world, target);
        if (hit == null) {
            return target;
        }
        Vector normal = target.subtract(hit.getLocation().getPosition()).normalized();
        double separation = entity.getRadius() + hit.getRadius();
        return hit.getLocation().getPosition().add(normal.scale(separation));
    }

    private static Entity firstCollision(Entity moving, World world, Vector at) {
        List<Entity> entities = world.getEntities();
        Entity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Entity other : entities) {
            if (other == moving) continue;
            double minDist = moving.getRadius() + other.getRadius();
            double dist = at.subtract(other.getLocation().getPosition()).length();
            if (dist < minDist && dist < closestDist) {
                closest = other;
                closestDist = dist;
            }
        }
        return closest;
    }

}