package de.tankblast.game;

import de.tankblast.Constants;
import de.tankblast.input.InputContext;
import de.tankblast.input.InputManager;
import de.tankblast.input.InputMapper;

import java.util.Map;

public class TankBlastClient {

    private InputManager inputManager;

    private PhysicsService physicsService;

    private long tickStart = System.nanoTime();

    private void onTick(){
        long currentTime = System.nanoTime();
        long timeSinceLastTickNanos = currentTime - tickStart;
        tickStart = currentTime;
        InputContext inputContext = new InputContext(inputManager.onTick(currentTime), timeSinceLastTickNanos);
        double movementDistance = InputMapper.getMovementDistance(inputContext, Constants.MOVEMENT_SPEED);
        double rotationDegrees = InputMapper.getRotationDegrees(inputContext, Constants.MAX_ROTATION_SPEED);

    }

}
