package cz.trigon.bicepsrendererapi.gl.bos;

import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.interfaces.ILockable;
import cz.trigon.bicepsrendererapi.gl.interfaces.ILocker;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVboManager;

public class VboManager implements IVboManager, ILocker {

    private Map<String, Integer> vbos;

    private int current = 0;
    private ILockable lock;

    public VboManager() {
        this.vbos = new HashMap<>();
    }

    private int createVbo() {
        int[] temp = new int[1];
        GLES20.glGenBuffers(1, temp, 0);
        return temp[0];
    }

    @Override
    public boolean exists(String name) {
        return this.vbos.containsKey(name);
    }

    public int get(String name) {
        Integer vbo = this.vbos.get(name);
        if (vbo == null)
            return -1;

        return vbo;
    }

    @Override
    public int create(String name) {
        int vbo = this.createVbo();
        this.vbos.put(name, vbo);

        return vbo;
    }

    private void bind(int glBufferId) {
        if (this.current != glBufferId) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glBufferId);
            this.current = glBufferId;
        }
    }

    @Override
    public void bind(String name) {
        this.unlock();

        if (this.vbos.containsKey(name))
            bind(this.vbos.get(name));
        else
            Log.e(Surface.LDTAG, name + " VBO doesn't exist");
    }

    @Override
    public void delete(String name) {
        Integer vbo = this.vbos.get(name);

        if (vbo != null) {
            if (vbo == this.current)
                this.bind(0);

            this.vbos.remove(name);
        }
    }

    @Override
    public void lockTo(ILockable o) {
        this.lock = o;
    }

    @Override
    public void unlock() {
        this.lock = null;
    }

    @Override
    public boolean isLocked() {
        return this.lock != null;
    }

    @Override
    public ILockable getLockedObject() {
        return this.lock;
    }
}
