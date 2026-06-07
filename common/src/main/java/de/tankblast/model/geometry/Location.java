package de.tankblast.model.geometry;

public class Location {

    private Vector position;
    private Velocity velocity;

    public Location(Vector position, Velocity velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Location copy(Vector position){
        return new Location(position, velocity.copy());
    }

    public Location copy(){
        return new Location(position.copy(), velocity.copy());
    }

    public Location copy(Velocity velocity){
        return new Location(position.copy(), velocity);
    }

    public Vector getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }
}