package cz.trigon.bicepsrendererapi;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.trigon.bicepsrendererapi.game.Game;

public class Surface extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Game game;
    private Input input;

    protected int tps = 20, magicConstant = 1000000000;
    protected double tickTime = 1d / tps;
    protected double tickTimeSec = this.tickTime * this.magicConstant;
    protected long time, lastTime, lastInfo;
    protected int fps, ticks, currentFps;

    public Surface(Context context) {
        super(context);
        this.input = new Input();

        this.setEGLContextClientVersion(2);
        this.setRenderer(this);
    }

    public Surface(Context context, Game game) {
        this(context);
        this.setGame(game);
    }

    public void setGame(Game game) {
        this.game = game;
        this.game.setSurface(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.time = System.nanoTime();
        this.lastTime = time;
        this.lastInfo = time;
        this.game.setup();
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

    public Input getInput() {
        return this.input;
    }

    // Maybe we should check if game != null here
    private void renderTick(float ptt) {
        this.game.renderTick(ptt);
    }

    private void tick() {
        this.game.tick();
    }

    public boolean onTouchEvent(final MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_UP) {
            this.input.isTouched = false;
            return false;
        } else if(e.getAction() == MotionEvent.ACTION_DOWN) {
            this.input.isTouched = true;
        }

        return true;
    }

}
