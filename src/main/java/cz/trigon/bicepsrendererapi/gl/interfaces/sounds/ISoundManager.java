package cz.trigon.bicepsrendererapi.gl.interfaces.sounds;

public interface ISoundManager {

    void cleanup();

    /**
     * Sets master volume and turns sound on/off
     *
     * @param soundOn     Determines if the sound is on
     * @param soundVolume New sound volume
     */
    void setVolume(boolean soundOn, float soundVolume);

    /**
     * Adds new sound effect
     *
     * @param key  Effect ID
     * @param name Effect content path
     */
    void addSound(int key, String name);

    /**
     * Adds new music
     *
     * @param key  Music ID
     * @param name Music content path
     */
    void addMusic(int key, String name);

    /**
     * Plays looping music on 100% volume
     *
     * @param key Music ID
     */
    void playMusic(int key);

    /**
     * Plays looping music
     *
     * @param key    Music ID
     * @param volume Volume relative to the master volume
     */
    void playMusic(int key, float volume);

    /**
     * Plays music
     *
     * @param key    Music ID
     * @param volume Volume relative to the master volume
     * @param loop   Determines if the music should loop
     */
    void playMusic(int key, float volume, boolean loop);

    /**
     * Resumes paused music
     */
    void resumeMusic();

    /**
     * Pauses currently playing music
     */
    void pauseMusic();

    /**
     * Stops currently playing music
     */
    void stopMusic();

    boolean isMusicPlaying();

    /**
     * Plays effect
     *
     * @param key    Effect ID
     * @param pitch  Effect pitch
     * @param volume Effect volume relative to the master volume, 0.0f - 1.0f
     * @param loop   Determines if the effect should loop
     */
    void playSound(int key, float pitch, float volume, boolean loop);

    /**
     * Plays effect non-looping
     *
     * @param key    Effect ID
     * @param pitch  Effect pitch
     * @param volume Effect volume relative to the master volume
     */
    void playSound(int key, float pitch, float volume);

    /**
     * Plays effect non-looping on 100% volume
     *
     * @param key   Effect ID
     * @param pitch Effect pitch
     */
    void playSound(int key, float pitch);

    /**
     * Plays effect non-looping on 100% volume with no pitch
     *
     * @param key Effect ID
     */
    void playSound(int key);

    /**
     * Stops given effect if it's playing
     *
     * @param key Effect ID
     */
    void stopSound(int key);

    /**
     * Stops all currently playing effects
     */
    void stopSounds();
}
