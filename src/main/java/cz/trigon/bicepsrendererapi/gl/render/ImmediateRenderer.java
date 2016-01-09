package cz.trigon.bicepsrendererapi.gl.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.bos.VboManager;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IImmediateRenderer;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.gl.matrices.Matrix4;
import cz.trigon.bicepsrendererapi.gl.shader.ShaderManager;
import cz.trigon.bicepsrendererapi.util.Color;

import static android.opengl.GLES20.glBufferSubData;

public class ImmediateRenderer implements IImmediateRenderer {

    public static final int BUFFER_SIZE = 1024 * 1024 * 2; // 2MB
    public static final int VERTEX_SIZE_FLOAT = 3;
    public static final int VERTEX_SIZE = VERTEX_SIZE_FLOAT * 4;

    public static final int MAX_VERTICES = (int) Math.floor(Math.floor(BUFFER_SIZE / VERTEX_SIZE_FLOAT) / 12) * 12;

    private Surface surface;

    private FloatBuffer buffer;
    //private FloatBuffer bufferF;
    //private IntBuffer bufferI;


    private PrimitiveMode primitiveMode;

    // THIS IS TEMPORARY
    private ShaderManager sm;
    private VboManager vm;

    private String vboId;
    private IShader shader;

    private float colorRed = 1f, colorGreen = 1f, colorBlue = 1f, colorAlpha = 1f;

    private float[] vertArray;
    private int arrayPos = 0;

    private int vertices = 0;

    private float color = 0;

    public ImmediateRenderer(Surface surface) {
        ByteBuffer b = ByteBuffer.allocateDirect(ImmediateRenderer.BUFFER_SIZE*4);
        b.order(ByteOrder.nativeOrder());

        this.buffer = b.asFloatBuffer();

        //this.bufferF = this.buffer.asFloatBuffer();
        //this.bufferI = this.buffer.asIntBuffer();

        this.vertArray = new float[MAX_VERTICES*VERTEX_SIZE_FLOAT];

        this.surface = surface;
        this.primitiveMode = PrimitiveMode.TRIANGLES;
        this.sm = surface.getShaders();
        this.sm.loadShader("default", "/default_shader.vsh", "/default_shader.fsh");
        this.vm = surface.getVbos();
        this.shader = this.sm.getShader("default");

        Matrix4 mat = Matrix4.makeOrthoMatrix(0, surface.getCanvasWidth(), surface.getCanvasHeight(), 0, -1, 1);

        this.shader.bind();

        this.shader.setUniformMatrix4f("pMat", mat);

        int attribPos = this.shader.getAttribLocation("vPos");
        int attribCol = this.shader.getAttribLocation("vColor");

        this.vboId = "default";
        this.vm.create(this.vboId);
        this.vm.bind(this.vboId);

        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, BUFFER_SIZE, null, GLES20.GL_STREAM_DRAW);

        GLES20.glVertexAttribPointer(attribPos, 2, GLES20.GL_FLOAT, false, VERTEX_SIZE, 0);
        GLES20.glVertexAttribPointer(attribCol, 4, GLES20.GL_UNSIGNED_BYTE, true, VERTEX_SIZE, 2 * 4);

        GLES20.glEnableVertexAttribArray(attribPos);
        GLES20.glEnableVertexAttribArray(attribCol);
    }

    @Override
    public void flush() {
        //GLES20.glFlush();

        //System.out.println("ArrayPos " + this.arrayPos + ", vertices " + this.vertices);

        this.buffer.put(this.vertArray, 0, this.arrayPos);
        this.buffer.flip();

        glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, this.buffer.limit() * 4, this.buffer);
        GLES20.glDrawArrays(this.getPrimitiveMode().getGl(), 0, this.vertices);

        this.buffer.clear();

        this.vertices = 0;
        this.arrayPos = 0;
    }


    @Override
    public void vertex(float x, float y) {
        //this.bufferF.put(x).put(y);
        //this.bufferI.put(Float.floatToRawIntBits(x)).put(Float.floatToRawIntBits(y)).put(0xFFFFFFFF);
        //this.buffer.putFloat(x).putFloat(y).putInt(0xFFFFFFFF);

        //this.bufferF.put(x).put(y).put(-99999999999999f);

        vertArray[arrayPos++] = x;
        vertArray[arrayPos++] = y;
        vertArray[arrayPos++] = this.color;

        this.vertices++;

        if (vertices == ImmediateRenderer.MAX_VERTICES) {
            flush();
        }
    }

    @Override
    public void color(int r, int g, int b, int a) {
        this.color(Color.packColor(r, g, b, a));
    }

    @Override
    public void color(float color) {
        this.color = color;
    }

    @Override
    public void color(int i) {
        this.color = new Color(i).val();
    }

    @Override
    public void color(Color color) {
        this.color = color.val();
    }

    @Override
    public void setClearColor(int color) {
        Color c = new Color(color);
        GLES20.glClearColor(c.x(), c.y(), c.z(), c.w());
    }

    @Override
    public void setPrimitiveMode(PrimitiveMode mode) {
        if(this.vertices > 0)
            this.flush();

        this.primitiveMode = mode;
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
        GLES20.glLineWidth(width);
    }

    @Override
    public float getLineWidth() {
        return 0;
    }

    @Override
    public void setClearColor(float r, float g, float b, float a) {
        GLES20.glClearColor(r, g, b, a);
    }

    @Override
    public void setClearColor(cz.trigon.bicepsrendererapi.util.Color c) {
        GLES20.glClearColor(c.x(), c.y(), c.z(), c.w());
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
    public void useVertexbuffer(String v) {
        this.vm.bind(v);
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
    public String getVertexbuffer() {
        return this.vboId;
    }

    @Override
    public ByteBuffer getGraphicsBuffer() {
        return null;
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
