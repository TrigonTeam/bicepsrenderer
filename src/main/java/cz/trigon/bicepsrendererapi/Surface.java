package cz.trigon.bicepsrendererapi;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.trigon.bicepsrendererapi.game.Game;
import cz.trigon.bicepsrendererapi.managers.InputManager;

public class Surface extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Game game;
    private InputManager input;

    protected int tps = 20, magicConstant = 1000000000;
    protected double tickTime = 1d / tps;
    protected double tickTimeSec = this.tickTime * this.magicConstant;
    protected long time, lastTime, lastInfo;
    protected int fps, ticks, currentFps;

    public Surface(Context context) {
        super(context);
        this.input = new InputManager(this);

        this.setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
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

    // Maybe we should check if game != null here
    private void renderTick(float ptt) {
        this.game.renderTick(ptt);
    }

    private void tick() {
        this.game.tick();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        this.queueEvent(new Runnable() {
            @Override
            public void run() {
                input.onTouch(e);
            }
        });

        return true;
    }

    @Override
    public boolean onTrackballEvent(final MotionEvent e) {
        this.queueEvent(new Runnable() {
            @Override
            public void run() {
                input.onBall(e);
            }
        });

        return true;
    }



}
