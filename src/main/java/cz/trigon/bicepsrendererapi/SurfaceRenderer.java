package cz.trigon.bicepsrendererapi;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.trigon.bicepsrendererapi.game.Game;

public class SurfaceRenderer implements GLSurfaceView.Renderer {
    protected Context context;
    private Game game;

    protected int tps = 20, magicConstant = 1000000000;
    protected double tickTime = 1d / tps;
    protected double tickTimeSec = this.tickTime * this.magicConstant;
    protected long time, lastTime, lastInfo;
    protected int fps, ticks, currentFps;

    public SurfaceRenderer(Context context, Game game) {
        this.context = context;
        this.game = game;

        this.game.setRenderer(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1, 1, 1, 1);

        this.time = System.nanoTime();
        this.lastTime = time;
        this.lastInfo = time;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.game.surfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float ptt = (this.time - this.lastTime) / ((float) this.tickTimeSec);

        this.renderTick(ptt);
        this.currentFps++;

        this.time = System.nanoTime();
        while(time - lastTime >= this.tickTimeSec) {
            this.tick();
            this.ticks++;
            this.lastTime += this.tickTimeSec;
        }

        if (this.time - lastInfo >= this.magicConstant) {
            this.lastInfo += this.magicConstant;
            this.fps = this.currentFps;
            this.currentFps = 0;
        }
    }

    public int getFps() {
        return this.fps;
    }

    public int getTicks() {
        return this.ticks;
    }

    public Context getContext() {
        return this.context;
    }

    // Maybe we should check if game != null here
    private void renderTick(float ptt) {
        this.game.renderTick(ptt);
    }

    private void tick() {
        this.game.tick();
    }


}
