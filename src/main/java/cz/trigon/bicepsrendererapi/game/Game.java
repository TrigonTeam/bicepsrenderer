package cz.trigon.bicepsrendererapi.game;

import android.content.Context;

import cz.trigon.bicepsrendererapi.SurfaceRenderer;

public abstract class Game {
    protected SurfaceRenderer renderer;
    protected Context context;

    public void setRenderer(SurfaceRenderer renderer) {
        this.renderer = renderer;
        this.context = renderer.getContext();
    }

    public abstract void setup();
    public abstract void tick();
    public abstract void renderTick(float ptt);
    public abstract void surfaceChanged(int w, int h);
}
