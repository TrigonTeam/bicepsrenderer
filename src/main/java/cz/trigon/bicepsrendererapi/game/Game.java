package cz.trigon.bicepsrendererapi.game;

import android.content.Context;

public abstract class Game {
    protected Surface surface;
    protected Context context;

    public void setSurface(Surface surface) {
        this.surface = surface;
        this.context = surface.getContext();
    }

    public abstract void setup();
    public abstract void tick();
    public abstract void renderTick(float ptt);
    public abstract void surfaceChanged(int w, int h);
}
