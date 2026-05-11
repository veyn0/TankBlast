package de.tankblast.game;

import de.tankblast.input.InputManager;
import de.tankblast.input.InputMapper;

import java.util.Map;

public class TankBlastClient {

    private InputManager inputManager;

    private PhysicsService physicsService;

    private InputMapper inputMapper;

    private long tickStart = System.nanoTime();

    private void onTick(){
        long currentTime = System.nanoTime();
        long timeSinceLastTickNanos = currentTime - tickStart;
        tickStart = currentTime;
        inputMapper.setKeyStrokes(inputManager.onTick(currentTime));



    }

}
