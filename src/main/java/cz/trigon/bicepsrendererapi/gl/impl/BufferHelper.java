package cz.trigon.bicepsrendererapi.gl.impl;

import android.opengl.GLES20;

public class BufferHelper {

    public static int genBuffer() {
        return BufferHelper.genBuffers(1)[0];
    }

    public static int[] genBuffers(int n) {
        int[] handles = new int[n];
        GLES20.glGenBuffers(n, handles, 0);
        return handles;
    }

}
