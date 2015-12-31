package cz.trigon.bicepsrendererapi.managers;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.ISoundManager;

public class SoundManager implements ISoundManager, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private Map<Integer, AssetFileDescriptor> music, sounds;
    private Map<Integer, MediaPlayer> soundsPlaying;
    private int musicPlaying = -1;
    private int musicCounter, soundCounter, playCounter;
    private MediaPlayer musicPlayer;
    private boolean prepared;

    public SoundManager() {
        this.music = new HashMap<>();
        this.sounds = new HashMap<>();
        this.soundsPlaying = new HashMap<>();
        this.musicPlayer = new MediaPlayer();
        this.musicPlayer.setOnPreparedListener(this);
        this.musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.musicPlayer.setOnCompletionListener(this);
    }

    @Override
    public int addMusic(AssetFileDescriptor fd) {
        this.music.put(++this.musicCounter, fd);
        return this.musicCounter;
    }

    @Override
    public void resumeMusic() {
        if(this.prepared && !this.musicPlayer.isPlaying())
            this.musicPlayer.start();
    }

    @Override
    public void playMusic(int id) {
        AssetFileDescriptor a = this.music.get(id);
        if(a == null)
            return;

        if(this.musicPlayer.isPlaying())
            this.musicPlayer.stop();

        try {
            this.musicPlayer.setDataSource(a.getFileDescriptor(), a.getStartOffset(), a.getLength());
        } catch (IOException e) {
            Log.e(Surface.LDTAG, "Couldn't open music ID " + id, e);
        }

        this.musicPlaying = id;

        try {
            this.musicPlayer.prepare();
            this.prepared = true;
            this.musicPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseMusic() {
        if(this.prepared)
            this.musicPlayer.pause();
    }

    @Override
    public int getPlayingMusic() {
        return this.musicPlaying;
    }

    @Override
    public void stopMusic() {
        this.musicPlayer.reset();
    }

    @Override
    public int addSound(AssetFileDescriptor fd) {
        this.sounds.put(++this.soundCounter, fd);
        return this.soundCounter;
    }

    @Override
    public int playSound(int id, float volume) {
        AssetFileDescriptor a = this.sounds.get(id);
        if(a == null)
            return -1;

        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(a.getFileDescriptor(), a.getStartOffset(), a.getLength());
            m.prepare();
            m.setVolume(volume, volume);
            m.setAudioStreamType(AudioManager.STREAM_MUSIC);

            this.soundsPlaying.put(++this.playCounter, m);
        } catch (IOException e) {
            Log.e(Surface.LDTAG, "Couldn't play sound ID " + id, e);
        }

        m.start();
        return this.playCounter;
    }

    @Override
    public int playSound(int id) {
        return this.playSound(id, 1f);
    }

    @Override
    public void stopSound(int playId) {
        MediaPlayer m = this.soundsPlaying.get(playId);
        if(m == null)
            return;

        m.stop();
        this.soundsPlaying.remove(playId);
    }

    public MediaPlayer getMusicPlayer() {
        return this.musicPlayer;
    }

    public MediaPlayer getSoundPlayer(int playId) {
        return this.soundsPlaying.get(playId);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        this.musicPlaying = -1;
    }
}
