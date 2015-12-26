package cz.trigon.bicepsrendererapi.gl.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.gl.interfaces.render.IAttributeProvider;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IRenderer;

public class VertexAttributeProvider implements IAttributeProvider {

    public static final int MAX_VERTICES = Renderer.BUFFER_SIZE / (10 << 2);

    protected IRenderer renderer;
    protected int vertexCount;
    protected FloatBuffer buffer;

    public VertexAttributeProvider(IRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void useBuffer(ByteBuffer buffer) {
        this.buffer = buffer.asFloatBuffer();
    }

    @Override
    public void preFlush() {

    }

    @Override
    public void onFlush() {
        GLES20.glDrawArrays(this.renderer.getPrimitiveMode().getGl(), 0, this.vertexCount);
    }

    @Override
    public void postFlush() {
        this.vertexCount = 0;
    }

    @Override
    public boolean canFlush() {
        return this.vertexCount > 0;
    }

    public void vertex(float x, float y) {
        if(this.vertexCount == VertexAttributeProvider.MAX_VERTICES) {
            this.renderer.flush();
        }

        this.buffer.put(x).put(y);
        this.vertexCount++;
    }
}
