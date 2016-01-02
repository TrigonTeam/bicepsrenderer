package cz.trigon.bicepsrendererapi.managers;

import android.annotation.TargetApi;
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

public class SoundManager implements ISoundManager, MediaPlayer.OnPreparedListener, AudioManager.OnAudioFocusChangeListener {

    private Map<Integer, Integer> sounds;
    private Map<Integer, AssetFileDescriptor> music;

    private MediaPlayer musicPlayer;
    private int musicPlaying = -1;
    private int musicCounter, soundCounter;
    private int wasPlaying;
    private float wasPlayingVolume;

    private boolean loadingMusic;
    private SoundPool soundPool;
    private final Object lock = new Object();

    class LollipopSoundPoolBuilder {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public SoundPool createSoundPoolBuilder() {
            return new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(
                    new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()).build();
        }
    }

    public SoundManager(Context context) {
        this.music = new HashMap<>();
        this.sounds = new HashMap<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            this.soundPool = new LollipopSoundPoolBuilder().createSoundPoolBuilder();
        else
            this.soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);

        AudioManager m = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        m.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (this.wasPlaying != -1) {
                    this.playMusic(this.wasPlaying);
                    this.wasPlaying = -1;
                } else if (this.musicPlaying != -1) {
                    this.resumeMusic();
                }

                this.musicPlayer.setVolume(this.wasPlayingVolume, this.wasPlayingVolume);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (this.musicPlaying != -1) {
                    this.wasPlaying = this.musicPlaying;
                    this.stopMusic();
                }

                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                this.pauseMusic();

                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (this.musicPlaying != -1)
                    this.musicPlayer.setVolume(0.1f, 0.1f);

                break;
        }
    }

    @Override
    public int addMusic(AssetFileDescriptor fd) {
        this.music.put(++this.musicCounter, fd);
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
        this.musicPlayer.setOnPreparedListener(this);
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
            this.musicPlayer.release();
            this.musicPlayer = null;
            this.musicPlaying = -1;
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

    public void setVolume(float volume) {
        this.wasPlayingVolume = volume;
        this.musicPlayer.setVolume(this.wasPlayingVolume, this.wasPlayingVolume);
    }

    public MediaPlayer getPlayer() {
        return this.musicPlayer;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        synchronized (this.lock) {
            mp.start();
            this.loadingMusic = false;
        }
    }
}
