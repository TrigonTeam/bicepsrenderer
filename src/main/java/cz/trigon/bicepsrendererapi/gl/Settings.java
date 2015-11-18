package cz.trigon.bicepsrendererapi.gl;

import android.opengl.GLES20;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private Map<Integer, Integer> texParameters = new HashMap<>();

    public Settings() {
        this.setMagFilter(Filters.MAG_FILTER_DEF);
        this.setMinFilter(Filters.MIN_FILTER_DEF);
        this.setWrapS(Wrap.WRAPS_DEF);
        this.setWrapT(Wrap.WRAPT_DEF);
    }

    public void setTexParameter(int parameter, int value) {
        this.texParameters.put(parameter, value);
    }

    public int getMinFilter() {
        return texParameters.get(GLES20.GL_TEXTURE_MIN_FILTER);
    }

    public void setMinFilter(int filter) {
        this.texParameters.put(GLES20.GL_TEXTURE_MIN_FILTER, filter);
    }

    public int getMagFilter() {
        return texParameters.get(GLES20.GL_TEXTURE_MAG_FILTER);
    }

    public void setMagFilter(int filter) {
        if (filter == Filters.LINEAR || filter == Filters.NEAREST)
            this.texParameters.put(GLES20.GL_TEXTURE_MAG_FILTER, filter);
    }

    public int getWrapS() {
        return texParameters.get(GLES20.GL_TEXTURE_WRAP_S);
    }

    public void setWrapS(int wrapS) {
        this.texParameters.put(GLES20.GL_TEXTURE_WRAP_S, wrapS);
    }

    public int getWrapT() {
        return texParameters.get(GLES20.GL_TEXTURE_WRAP_T);
    }

    public void setWrapT(int wrapT) {
        this.texParameters.put(GLES20.GL_TEXTURE_WRAP_T, wrapT);
    }

    public Map<Integer, Integer> getTexParameters() {
        return texParameters;
    }

    public class Filters {
        public static final int NEAREST = GLES20.GL_NEAREST;
        public static final int LINEAR = GLES20.GL_LINEAR;
        public static final int MIPMAP_NEAREST = GLES20.GL_NEAREST_MIPMAP_NEAREST;
        public static final int MIPMAP_LINEAR = GLES20.GL_LINEAR_MIPMAP_LINEAR;
        public static final int MIPMAP_LINEAR_NEAREST = GLES20.GL_LINEAR_MIPMAP_NEAREST;
        public static final int MIPMAP_NEAREST_LINEAR = GLES20.GL_NEAREST_MIPMAP_LINEAR;

        public static final int MIN_FILTER_DEF = Filters.MIPMAP_LINEAR;
        public static final int MAG_FILTER_DEF = Filters.LINEAR;
    }

    public class Wrap {
        public static final int CLAMP_TO_EDGE = GLES20.GL_CLAMP_TO_EDGE;
        public static final int MIRRORED_REPEAT = GLES20.GL_MIRRORED_REPEAT;
        public static final int REPEAT = GLES20.GL_REPEAT;

        public static final int WRAPS_DEF = Wrap.REPEAT;
        public static final int WRAPT_DEF = Wrap.REPEAT;
    }
}
