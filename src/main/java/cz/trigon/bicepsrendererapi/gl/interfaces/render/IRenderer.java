package cz.trigon.bicepsrendererapi.gl.interfaces.render;

import android.graphics.Color;
import android.opengl.GLES20;

import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;


public interface IRenderer {

    default void setClearColor(int color) {
        this.setClearColor(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f,
                Color.alpha(color) / 255f);
    }

    void flush();

    void setPrimitiveMode(PrimitiveMode mode);

    void setTexture(ITexture texture);

    void enableTexture(boolean enable);

    void setPointSize(float size);

    void setLineWidth(float width);

    void setClearColor(float r, float g, float b, float a);

    void useAttributeProvider(IAttributeProvider a);

    void useShader(IShader s);

    void useFramebuffer(IFbo f);

    void useVertexbuffer(IVbo v);

    void clear();

    void clearBuffers();

    void cleanup();

    /**
     * Modes of primitive rendering
     */
    enum PrimitiveMode {
        POINTS(GLES20.GL_POINTS),
        LINES(GLES20.GL_LINES),
        LINE_LOOP(GLES20.GL_LINE_LOOP),
        LINE_STRIP(GLES20.GL_LINE_STRIP),
        TRIANGLES(GLES20.GL_TRIANGLES),
        TRIANGLE_FAN(GLES20.GL_TRIANGLE_FAN),
        TRIANGLE_STRIP(GLES20.GL_TRIANGLE_STRIP);

        private int glPrimitive;

        PrimitiveMode(int glPrimitive) {
            this.glPrimitive = glPrimitive;
        }

        public int getGl() {
            return glPrimitive;
        }
    }

    /**
     * Modes of shape rendering
     */
    enum ShapeMode {
        OUTLINE, FILLED
    }
}