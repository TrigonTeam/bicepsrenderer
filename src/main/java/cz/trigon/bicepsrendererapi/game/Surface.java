package cz.trigon.bicepsrendererapi.game;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.trigon.bicepsrendererapi.managers.InputManager;
import cz.trigon.bicepsrendererapi.managers.SoundManager;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.obj.Content;
import cz.trigon.bicepsrendererapi.obj.Input;
import cz.trigon.bicepsrendererapi.obj.Music;
import cz.trigon.bicepsrendererapi.obj.SoundEffect;
import cz.trigon.bicepsrendererapi.obj.Spritesheet;
import cz.trigon.bicepsrendererapi.obj.Texture;

public class Surface extends GLSurfaceView implements GLSurfaceView.Renderer {

    public static final String LDTAG = "TBR-Debug";

    public static final int STATIC_WIDTH = 1280;

    private Game game;
    private InputManager input;
    private ContentManager content;
    private SoundManager sound;

    protected int tps = 60; //TODO this should be changeable
    protected double tickTime = 1d / tps;
    protected double tickTimeSec = this.tickTime * 1000000000;
    protected long time, lastTime, lastInfo;
    protected int fps, ticks, currentFps;

    public Surface(Context context) {
        super(context);

        this.input = new InputManager(this);
        this.content = new ContentManager(context.getAssets());
        this.sound = new SoundManager(context);
        this.bindWrappers();

        this.setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer(this);
    }

    public Surface(Context context, Game game) {
        this(context);
        this.setGame(game);
    }

    public void bindWrappers() {
        Content.init(this);
        Texture.init(this);
        Spritesheet.init(this);
        Input.init(this);
        SoundEffect.init(this);
        Music.init(this);
    }

    public SoundManager getSound() {
        return this.sound;
    }

    public InputManager getInput() {
        return this.input;
    }

    public ContentManager getContent() {
        return this.content;
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

        if (this.time - lastInfo >= 1000000000) {
            this.lastInfo += 1000000000;
            this.fps = this.currentFps;
            this.currentFps = 0;
        }

        int glError;
        while ((glError = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(Surface.LDTAG,  "GL error: " + GLU.gluErrorString(glError));
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
        this.game.tick(this.ticks);
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
