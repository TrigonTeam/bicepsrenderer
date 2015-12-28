package cz.trigon.bicepsrendererapi.gl.bos;

import android.opengl.GLES20;

import java.util.HashMap;
import java.util.Map;

import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVboManager;

public class VboManager implements IVboManager {

    private Map<String, Vbo> vbos;

    private int current = 0;

    public VboManager() {
        this.vbos = new HashMap<>();
    }

    @Override
    public IVbo getVbo(String name) {
        Vbo vbo = this.vbos.get(name);
        if (vbo == null) {
            vbo = new Vbo(this, name);
            this.vbos.put(name, vbo);
        }
        return vbo;
    }

    @Override
    public void bind(IVbo toBind) {
        int id = toBind.getId();
        if (this.current != id) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, id);
            this.current = id;
        }
    }

    @Override
    public void bind(String name) {
        bind(getVbo(name));
    }

    @Override
    public void deleteVbo(String name) {
        Vbo vbo = this.vbos.get(name);
        if (vbo != null) {
            GLES20.glDeleteBuffers(1, new int[]{vbo.getId()}, 0);
        }
        this.vbos.remove(name);
    }
}