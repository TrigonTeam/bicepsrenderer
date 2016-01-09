package cz.trigon.bicepsrendererapi.obj;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.bos.VboManager;
import cz.trigon.bicepsrendererapi.gl.interfaces.ILockable;

public class Vbo implements ILockable {
    private static Surface surface;

    public static void init(Surface s) {
        Vbo.surface = s;
    }

    private String name;
    private int glId;
    private VboManager vbo;

    public Vbo(String name) {
        this.vbo = Vbo.surface.getVbos();

        this.name = name;

        if (this.vbo.exists(name))
            this.glId = this.vbo.get(name);
        else
            this.glId = this.vbo.create(name);
    }

    public int getGlId() {
        return this.glId;
    }

    public void bind() {
        this.vbo.bind(this.name);
        this.lock();
    }

    @Override
    public boolean isLocked() {
        return this.vbo.getLockedObject() == this;
    }

    @Override
    public void lock() {
        this.vbo.lockTo(this);
    }
}
