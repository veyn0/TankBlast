package de.tankblast.input;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InputManager {

    private Object lock = new Object();

    private Map<Integer, Long> keycodesPressedSinceLastTick = new ConcurrentHashMap<>();

    private Map<Integer, Long> keyPressedSince = new ConcurrentHashMap<>();

    private long currentTickStart;

    public Map<Integer, Long> onTick(long currentTime){
        synchronized (lock) {
            applyAndResetTimes(currentTime);
            Map<Integer, Long> result = new ConcurrentHashMap<>(keycodesPressedSinceLastTick);
            keycodesPressedSinceLastTick.clear();
            currentTickStart = currentTime;
            return result;
        }
    }

    public void onKeyPress(int keyCode){
        synchronized (lock) {
            if (keyPressedSince.containsKey(keyCode)) return;
            keyPressedSince.put(keyCode, System.nanoTime());
        }
    }

    public void onKeyRelease(int keyCode){
        synchronized (lock){
            long value = getPressedSinceTickBeginn(keyCode, System.nanoTime());
            keyPressedSince.remove(keyCode);
            keycodesPressedSinceLastTick.merge(keyCode, value, Long::sum);
        }
    }

    public void onWindowOutOfFocus(){
        synchronized (lock){
            keyPressedSince.clear();
        }
    }

    private void applyAndResetTimes(long currentTime){
        synchronized (lock){
            for(int i : keyPressedSince.keySet()){
                long value = getPressedSinceTickBeginn(i, currentTime);
                keycodesPressedSinceLastTick.merge(i, value, Long::sum);
            }
        }
    }

    private long getPressedSinceTickBeginn(int keyCode, long currentTime){
        return currentTime - (Math.max(keyPressedSince.get(keyCode), currentTickStart));
    }

}
