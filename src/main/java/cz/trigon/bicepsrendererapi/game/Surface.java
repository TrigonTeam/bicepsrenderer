package cz.trigon.bicepsrendererapi.game;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cz.trigon.bicepsrendererapi.gl.bos.VboManager;
import cz.trigon.bicepsrendererapi.gl.shader.ShaderManager;
import cz.trigon.bicepsrendererapi.managers.SoundManager;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.input.InputManager;
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
    private VboManager vboManager;
    private ShaderManager shaderManager;

    protected int tps = 60; //TODO this should be changeable
    protected double tickTime = 1d / tps;
    protected double tickTimeSec = this.tickTime * 1000000000;
    protected long time, lastTime, lastInfo;
    protected int fps, ticks, currentFps;

    private boolean wasMusicPlaying;
    private int canvasHeight;
    private float canvasRatio;

    public Surface(Context context) {
        super(context);

        this.input = new InputManager(this);
        this.content = new ContentManager(context.getAssets());
        this.sound = new SoundManager(context);
        this.vboManager = new VboManager();
        this.shaderManager = new ShaderManager(this);
        this.input.registerListeners();

        this.bindWrappers();

        this.setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer(this);
    }

    public Surface(Context context, Game game) {
        this(context);
        this.setGame(game);
    }


    @Override
    public void onPause() {
        super.onPause();
        this.input.registerListeners();

        if (this.sound.getPlayingMusic() > -1 && this.sound.getPlayer().isPlaying()) {
            this.sound.pauseMusic();
            this.wasMusicPlaying = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.input.unregisterListeners();

        if (this.wasMusicPlaying)
            this.sound.resumeMusic();
    }

    public void bindWrappers() {
        Content.init(this);
        Texture.init(this);
        Spritesheet.init(this);
        Input.init(this);
        SoundEffect.init(this);
        Music.init(this);
    }


    public ShaderManager getShaders() {
        return this.shaderManager;
    }

    public VboManager getVbos() {
        return this.vboManager;
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
    public boolean onTouchEvent(MotionEvent e) {
        return this.input.onTouchEvent(e);
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
        this.canvasRatio = Surface.STATIC_WIDTH / width;
        this.canvasHeight = (int) (this.canvasRatio * height);

        this.game.surfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float ptt = (this.time - this.lastTime) / ((float) this.tickTimeSec);

        this.renderTick(ptt);
        this.currentFps++;

        this.time = System.nanoTime();
        while(time - lastTime >= this.tickTimeSec) {
            this.input.tick();
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

        Log.i(Surface.LDTAG, this.getFps() + "fps");
    }

    public int getFps() {
        return this.fps;
    }

    public int getTicks() {
        return this.ticks;
    }

    public int getCanvasWidth() {
        return Surface.STATIC_WIDTH;
    }

    public int getCanvasHeight() {
        return this.canvasHeight;
    }

    public float getCanvasRatio() {
        return this.canvasRatio;
    }

    // Maybe we should check if game != null here
    private void renderTick(float ptt) {
        this.game.renderTick(ptt);
    }

    private void tick() {
        this.game.tick(this.ticks);
    }
}
