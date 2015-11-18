package cz.trigon.bicepsrendererapi.gl.impl.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IAttributeProvider;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IRenderer;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;

public class Renderer implements IRenderer{

    public static final int BUFFER_SIZE = 1024 * 1024;

    private IAttributeProvider atrp;
    private ByteBuffer buffer;
    private PrimitiveMode primitiveMode;

    public Renderer() {
        this.buffer = ByteBuffer.allocateDirect(Renderer.BUFFER_SIZE);
        this.buffer.order(ByteOrder.nativeOrder());
    }

    @Override
    public void flush() {
        if(this.atrp.canFlush()) {
            this.atrp.preFlush();

            GLES20.glFlush();
            this.buffer.flip();
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, Renderer.BUFFER_SIZE, this.buffer, GLES20.GL_DYNAMIC_DRAW);
            this.atrp.onFlush();
            this.buffer.clear();
            this.atrp.postFlush();
        }
    }

    @Override
    public void setPrimitiveMode(PrimitiveMode mode) {

    }

    @Override
    public PrimitiveMode getPrimitiveMode() {
        return this.primitiveMode;
    }

    @Override
    public void setTexture(ITexture texture) {

    }

    @Override
    public ITexture getTexture() {
        return null;
    }

    @Override
    public void enableTexture(boolean enable) {

    }

    @Override
    public void setPointSize(float size) {

    }

    @Override
    public float getPointSize() {
        return 0;
    }

    @Override
    public void setLineWidth(float width) {

    }

    @Override
    public float getLineWidth() {
        return 0;
    }

    @Override
    public void setClearColor(float r, float g, float b, float a) {

    }

    @Override
    public void useAttributeProvider(IAttributeProvider a) {
        this.atrp = a;
        a.useBuffer(this.buffer);
    }

    @Override
    public void useShader(IShader s) {

    }

    @Override
    public void useFramebuffer(IFbo f) {

    }

    @Override
    public void useVertexbuffer(IVbo v) {

    }

    @Override
    public IShader getShader() {
        return null;
    }

    @Override
    public IFbo getFramebuffer() {
        return null;
    }

    @Override
    public IVbo getVertexbuffer() {
        return null;
    }

    @Override
    public ByteBuffer getGraphicsBuffer() {
        return this.buffer;
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearBuffers() {

    }

    @Override
    public void cleanup() {

    }
}
