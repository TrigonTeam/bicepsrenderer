package cz.trigon.bicepsrendererapi.gl.interfaces.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;

import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;


public interface IRenderer {

    void setClearColor(int color);

    void flush();

    void setPrimitiveMode(PrimitiveMode mode);

    PrimitiveMode getPrimitiveMode();

    void setTexture(ITexture texture);

    ITexture getTexture();

    void enableTexture(boolean enable);

    void setPointSize(float size);

    float getPointSize();

    void setLineWidth(float width);

    float getLineWidth();

    void setClearColor(float r, float g, float b, float a);

    void useShader(IShader s);

    void useFramebuffer(IFbo f);

    void useVertexbuffer(IVbo v);

    IShader getShader();

    IFbo getFramebuffer();

    IVbo getVertexbuffer();

    ByteBuffer getGraphicsBuffer();

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