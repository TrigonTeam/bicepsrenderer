package cz.trigon.bicepsrendererapi.gl.bos;

import android.opengl.GLES20;

import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;

public class Vbo implements IVbo {

    private VboManager mgr;
    private String name;
    private int id;

    public Vbo(VboManager mgr, String name) {
        this.mgr = mgr;
        this.name = name;
        this.id = genGlBuffer();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void bind() {
        this.mgr.bind(this);
    }

    @Override
    public void delete() {
        this.mgr.deleteVbo(this.name);
    }

    private static int genGlBuffer() {
        int[] temp = new int[1];
        GLES20.glGenBuffers(1, temp, 0);
        return temp[0];
    }

}