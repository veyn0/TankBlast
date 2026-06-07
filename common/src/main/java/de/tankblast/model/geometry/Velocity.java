package de.tankblast.model.geometry;

public class Velocity {
    private Vector direction;
    private double speed;

    public Velocity(Vector direction, double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    public Velocity copy(){
        return new Velocity(direction, speed);
    }

    public Velocity copy(double speed){
        return new Velocity(direction, speed);
    }

    public Velocity copy(Vector direction){
        return new Velocity(direction, speed);
    }

    public Vector getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * The velocity as a displacement vector: direction (normalized) * speed.
     */
    public Vector asVector(){
        return direction.normalized().scale(speed);
    }
}