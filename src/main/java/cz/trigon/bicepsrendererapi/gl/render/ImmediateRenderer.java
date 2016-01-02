package cz.trigon.bicepsrendererapi.gl.render;

import android.graphics.Color;
import android.opengl.GLES20;
import android.util.Log;

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

    public static final int BUFFER_SIZE = 1024 * 1024; // 1MB
    public static final int MAX_VERTICES = 87372;

    private Surface surface;

    private ByteBuffer buffer;


    private PrimitiveMode primitiveMode;

    // THIS IS TEMPORARY
    private ShaderManager sm;
    private VboManager vm;

    private IShader shader;
    private IVbo vbo;

    private float colorRed = 1f, colorGreen = 1f, colorBlue = 1f, colorAlpha = 1f;

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

        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, MAX_VERTICES * 3 * 4, null, GLES20.GL_STREAM_DRAW);

        GLES20.glVertexAttribPointer(attribPos, 2, GLES20.GL_FLOAT, false, 3 * 4, 0);
        GLES20.glVertexAttribPointer(attribCol, 4, GLES20.GL_UNSIGNED_BYTE, true, 3 * 4, 2 * 4);

        GLES20.glEnableVertexAttribArray(attribPos);
        GLES20.glEnableVertexAttribArray(attribCol);
    }

    @Override
    public void flush() {
        //GLES20.glFlush();
        this.buffer.flip();
        //GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.vertices * 3 * 4, this.buffer, GLES20.GL_STREAM_DRAW);

        //GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, MAX_VERTICES * 3 * 4, null, GLES20.GL_STREAM_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, this.vertices * 3 * 4, this.buffer);

        GLES20.glDrawArrays(this.getPrimitiveMode().getGl(), 0, this.vertices);

        this.buffer.clear();
        this.vertices = 0;

        Log.i(Surface.LDTAG, "FLUSH!");
    }


    @Override
    public void vertex(float x, float y) {
        this.buffer.putFloat(x).putFloat(y).put((byte) (colorRed * 255f)).put((byte) (colorGreen * 255f)).put((byte) (colorBlue * 255f)).put((byte) (colorAlpha * 255f));
        this.vertices++;

        if (vertices == ImmediateRenderer.MAX_VERTICES) {
            flush();
        }
    }

    @Override
    public void color(float r, float g, float b, float a) {
        this.colorRed = r;
        this.colorGreen = g;
        this.colorBlue = b;
        this.colorAlpha = a;
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
