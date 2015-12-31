package cz.trigon.bicepsrendererapi.managers.interfaces;

import android.content.res.AssetFileDescriptor;

public interface ISoundManager {
    int addMusic(AssetFileDescriptor fd);
    void resumeMusic();
    void playMusic(int id);
    void pauseMusic();
    int getPlayingMusic();
    void stopMusic();

    int addSound(AssetFileDescriptor fd);

    int playSound(int id, float volume);

    int playSound(int id);

    void stopSound(int playId);
}
