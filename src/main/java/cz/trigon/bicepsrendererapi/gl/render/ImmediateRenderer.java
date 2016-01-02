package cz.trigon.bicepsrendererapi.gl.render;

import android.graphics.Color;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.bos.Vbo;
import cz.trigon.bicepsrendererapi.gl.bos.VboManager;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IVbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IImmediateRenderer;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.gl.matrices.Matrix4;
import cz.trigon.bicepsrendererapi.gl.shader.Shader;
import cz.trigon.bicepsrendererapi.gl.shader.ShaderManager;

public class ImmediateRenderer implements IImmediateRenderer {

    public static final int BUFFER_SIZE = 1024 * 1024 * 4; // 4MB

    private Surface surface;

    private ByteBuffer buffer;
    private PrimitiveMode primitiveMode;

    // THIS IS TEMPORARY
    private ShaderManager sm;
    private VboManager vm;

    private IShader shader;
    private IVbo vbo;

    private int vertices = 0;

    public ImmediateRenderer(Surface surface) {
        this.buffer = ByteBuffer.allocateDirect(ImmediateRenderer.BUFFER_SIZE);
        this.buffer.order(ByteOrder.nativeOrder());
        this.surface = surface;
        this.primitiveMode = PrimitiveMode.TRIANGLES;
        this.sm = new ShaderManager(surface);
        this.sm.loadShader("default", "/default_shader.vsh", "/default_shader.fsh");
        this.vm = new VboManager();
        this.shader = this.sm.getShader("default");
        this.vbo = this.vm.getVbo("default");

        Matrix4 mat = Matrix4.makeOrthoMatrix(0, 1080, 1920, 0, -1, 1);

        this.shader.bind();

        this.shader.setUniformMatrix4f("pMat", mat);

        int attribPos = this.shader.getAttribLocation("vPos");
        int attribCol = this.shader.getAttribLocation("vColor");

        this.vbo.bind();
        GLES20.glVertexAttribPointer(attribPos, 2, GLES20.GL_FLOAT, false, 6 * 4, 0);
        GLES20.glVertexAttribPointer(attribCol, 4, GLES20.GL_FLOAT, false, 6 * 4, 2 * 4);

        GLES20.glEnableVertexAttribArray(attribPos);
        GLES20.glEnableVertexAttribArray(attribCol);
    }

    @Override
    public void flush() {
        GLES20.glFlush();
        this.buffer.flip();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.vertices * 6 * 4, this.buffer, GLES20.GL_STREAM_DRAW);

        GLES20.glDrawArrays(this.getPrimitiveMode().getGl(), 0, this.vertices);

        this.buffer.clear();
        this.vertices = 0;
    }

    @Override
    public void vertex(float x, float y) {
        this.buffer.putFloat(x).putFloat(y).putFloat(1).putFloat(1).putFloat(1).putFloat(1);
        this.vertices++;
    }

    @Override
    public void setClearColor(int color) {
        this.setClearColor(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f,
                Color.alpha(color) / 255f);
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

    /*@Override
    public void useAttributeProvider(IAttributeProvider a) {
        this.atrp = a;
        a.useBuffer(this.buffer);
    }*/

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
