package cz.trigon.bicepsrendererapi.game;

import android.content.Context;

public abstract class Game {
    protected Surface surface;
    protected Context context;

    public void setSurface(Surface surface) {
        this.surface = surface;
        this.context = surface.getContext();
    }

    public Surface getSurface() {
        return this.surface;
    }

    public abstract void setup();

    public abstract void tick(int tick);
    public abstract void renderTick(int tick, float ptt);
    public abstract void surfaceChanged(int w, int h);
}
