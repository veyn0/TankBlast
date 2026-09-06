package de.tankblast.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads short wav clips from the classpath and plays them. One-shot effects are opened
 * on a fresh {@link Clip} per call so overlapping plays don't cut each other off; loop
 * sounds keep a single persistent {@link Clip} that is started/stopped as state changes.
 * Falls back to doing nothing if no audio device is available (e.g. headless environments).
 */
public class SoundManager {

    private final Map<SoundEffect, ClipData> effectData = new EnumMap<>(SoundEffect.class);
    private final Map<LoopSound, ClipData> loopData = new EnumMap<>(LoopSound.class);
    private final Map<LoopSound, Clip> activeLoops = new ConcurrentHashMap<>();

    // AudioSystem.getClip()/open() can throw IllegalArgumentException or SecurityException
    // (not just LineUnavailableException) when no real audio device is present, e.g. in a
    // headless/containerized environment. Once that happens, stop retrying every call.
    private volatile boolean audioAvailable = true;

    public SoundManager() {
        for (SoundEffect effect : SoundEffect.values()) {
            effectData.put(effect, load(effect.getResourcePath()));
        }
        for (LoopSound loop : LoopSound.values()) {
            loopData.put(loop, load(loop.getResourcePath()));
        }
    }

    public void play(SoundEffect effect) {
        if (!audioAvailable) return;
        ClipData data = effectData.get(effect);
        if (data == null) return;
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(data.format(), data.bytes(), 0, data.bytes().length);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (LineUnavailableException | IllegalArgumentException | SecurityException e) {
            System.err.println("Could not play sound " + effect + ": " + e.getMessage());
            audioAvailable = false;
        }
    }

    public void setLoopActive(LoopSound loop, boolean active) {
        if (active) {
            startLoop(loop);
        } else {
            stopLoop(loop);
        }
    }

    private void startLoop(LoopSound loop) {
        if (!audioAvailable) return;
        activeLoops.computeIfAbsent(loop, l -> {
            ClipData data = loopData.get(l);
            if (data == null) return null;
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(data.format(), data.bytes(), 0, data.bytes().length);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                return clip;
            } catch (LineUnavailableException | IllegalArgumentException | SecurityException e) {
                System.err.println("Could not start loop " + l + ": " + e.getMessage());
                audioAvailable = false;
                return null;
            }
        });
    }

    private void stopLoop(LoopSound loop) {
        Clip clip = activeLoops.remove(loop);
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    private ClipData load(String resourcePath) {
        URL url = getClass().getClassLoader().getResource(resourcePath);
        if (url == null) {
            System.err.println("Sound resource not found: " + resourcePath);
            return null;
        }
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(url)) {
            return new ClipData(stream.getFormat(), stream.readAllBytes());
        } catch (IOException | javax.sound.sampled.UnsupportedAudioFileException e) {
            System.err.println("Failed to load sound " + resourcePath + ": " + e.getMessage());
            return null;
        }
    }

    private record ClipData(AudioFormat format, byte[] bytes) {
    }
}
