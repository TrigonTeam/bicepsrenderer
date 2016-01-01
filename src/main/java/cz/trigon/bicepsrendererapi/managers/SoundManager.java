package cz.trigon.bicepsrendererapi.managers;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.ISoundManager;

public class SoundManager implements ISoundManager, MediaPlayer.OnPreparedListener {

    private Map<Integer, Integer> sounds;
    private Map<Integer, AssetFileDescriptor> music;

    private MediaPlayer musicPlayer;
    private int musicPlaying = -1;
    private int musicCounter, soundCounter;

    private boolean loadingMusic;
    private SoundPool soundPool;
    private final Object lock = new Object();

    public SoundManager(Context context) {
        this.music = new HashMap<>();
        this.sounds = new HashMap<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.soundPool = new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(
                    new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()).build();
        } else {
            this.soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        }
    }

    @Override
    public int addMusic(AssetFileDescriptor fd) {
        this.music.put(this.musicCounter++, fd);
        return this.musicCounter;
    }

    @Override
    public void resumeMusic() {
        if (this.musicPlayer != null) {
            this.musicPlayer.start();
        }
    }

    @Override
    public void playMusic(int id) {
        AssetFileDescriptor fd = this.music.get(id);
        if (fd == null)
            return;

        this.stopMusic();

        this.musicPlayer = new MediaPlayer();
        try {
            this.musicPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());

            synchronized (this.lock) {
                this.loadingMusic = true;
                this.musicPlaying = id;
                this.musicPlayer.prepareAsync();
            }
        } catch (IOException e) {
            Log.e(Surface.LDTAG, "Couldn't play music ID " + id, e);
        }
    }

    @Override
    public void pauseMusic() {
        if (this.musicPlayer != null) {
            this.musicPlayer.pause();
        }
    }

    @Override
    public int getPlayingMusic() {
        return this.musicPlaying;
    }

    @Override
    public void stopMusic() {
        if (this.musicPlayer != null) {
            this.musicPlayer.stop();
            this.musicPlayer = null;
        }
    }

    @Override
    public int addSound(AssetFileDescriptor fd) {
        int sId = this.soundPool.load(fd, 1);
        this.sounds.put(++this.soundCounter, sId);

        try {
            fd.close();
        } catch (IOException e) {
            Log.e(Surface.LDTAG, "Error when adding sound", e);
        }

        return this.soundCounter;
    }

    @Override
    public int playSound(int id, float volume, float pitch, boolean loop) {
        Integer sound = this.sounds.get(id);
        if (sound == null)
            return -1;

        int streamId = this.soundPool.play(sound, volume, volume, 1, loop ? -1 : 0, pitch);
        if (streamId == 0)
            return -1;

        return streamId;
    }

    @Override
    public void stopSound(int streamId) {
        this.soundPool.stop(streamId);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        synchronized (this.lock) {
            mp.start();
            this.loadingMusic = false;
        }
    }
}