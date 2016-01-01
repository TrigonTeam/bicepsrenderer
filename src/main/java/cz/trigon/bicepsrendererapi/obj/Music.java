package cz.trigon.bicepsrendererapi.obj;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.SoundManager;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;

public class Music implements ILoadable {

    private static Surface surface;

    public static void init(Surface s) {
        Music.surface = s;
    }

    private int musicId;
    private SoundManager sound;
    private float volume = 1f;

    public Music() {
        this.sound = Music.surface.getSound();
    }

    public void play(float volume) {
        this.sound.playMusic(this.musicId);
        this.sound.setVolume(volume);
    }

    public void play() {
        this.play(this.volume);
    }

    public boolean isPaused() {
        return this.isPlaying() && !this.sound.getPlayer().isPlaying();
    }

    public void resume() {
        if (this.isPlaying()) {
            this.sound.resumeMusic();
        }
    }

    public void stop() {
        if (this.isPlaying()) {
            this.sound.stopMusic();
        }
    }

    public void pause() {
        if (this.isPlaying()) {
            this.sound.pauseMusic();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isPlaying() {
        return this.sound.getPlayingMusic() == this.musicId;
    }

    @Override
    public void load(ContentManager content, String path) throws IOException {
        this.musicId = this.sound.addMusic(content.getDescriptor(path));
    }

    @Override
    public boolean canLoad(ContentManager content, String path) {
        // TODO
        return true;
    }
}
