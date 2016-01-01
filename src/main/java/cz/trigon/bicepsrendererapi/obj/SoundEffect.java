package cz.trigon.bicepsrendererapi.obj;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.SoundManager;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;

public class SoundEffect implements ILoadable {
    private static Surface surface;

    public static void init(Surface s) {
        SoundEffect.surface = s;
    }

    private int soundId, lastPlayId;
    private boolean loop = false;
    private float pitch = 1f;
    private float volume = 1f;
    private SoundManager sound;

    public SoundEffect() {
        this.sound = SoundEffect.surface.getSound();
    }

    @Override
    public void load(ContentManager content, String path) throws IOException {
        this.soundId = this.sound.addSound(content.getDescriptor(path));
    }

    @Override
    public boolean canLoad(ContentManager content, String path) {
        // TODO
        return true;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setLooping(boolean loop) {
        this.loop = loop;
    }

    public int play(float pitch, boolean loop) {
        return this.play(this.volume, pitch, loop);
    }

    public int play(float volume) {
        return this.play(volume, this.pitch, this.loop);
    }

    public int play(float volume, float pitch) {
        return this.play(volume, pitch, this.loop);
    }

    public int play(float volume, float pitch, boolean loop) {
        return this.lastPlayId = this.sound.playSound(this.soundId, volume, pitch, loop);
    }

    public int play() {
        return this.play(this.volume, this.pitch, this.loop);
    }

    public void stopLast() {
        this.stop(this.lastPlayId);
    }

    public void stop(int streamId) {
        this.sound.stopSound(streamId);
    }
}
