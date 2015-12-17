package cz.trigon.bicepsrendererapi.gl.impl.render;

import android.graphics.Color;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.gl.impl.BufferHelper;
import cz.trigon.bicepsrendererapi.gl.impl.matrices.Matrix4;
import cz.trigon.bicepsrendererapi.gl.interfaces.render.IRenderer;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;

public class Renderer implements IRenderer {

    public static final int BUFFER_SIZE = 1024 * 1024 * 4; // 4MB
    public static final int MAX_VERTICES = BUFFER_SIZE / (6 * 4);

    private FloatBuffer buffer;
    private PrimitiveMode primitiveMode;

    private int vertexCount = 0;

    private float cRed, cGreen, cBlue, cAlpha;

    private int vboHandle;

    private static final String sV =
            "uniform    mat4        pMat;" +
                    "attribute  vec2        vPosition;" +
                    "attribute  vec4        vColor;" +
                    "varying  vec4        color;" +
                    "void main() {" +
                    "  gl_Position = pMat * vec4(vPosition, 0.0, 1.0);" +
                    "  color = vColor;" +
                    "}";

    private static final String sF =
            "precision mediump float;" +
                    "varying  vec4        color;" +
                    "void main() {" +
                    "  gl_FragColor = color;" +
                    "}";

    public Renderer() {
        this.primitiveMode = PrimitiveMode.TRIANGLES;
        this.cRed = this.cGreen = this.cBlue = this.cAlpha = 1f;

        ByteBuffer buf = ByteBuffer.allocateDirect(Renderer.BUFFER_SIZE);
        buf.order(ByteOrder.nativeOrder());

        this.buffer = buf.asFloatBuffer();

        this.vboHandle = BufferHelper.genBuffer();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.vboHandle);

        int progid, vertid, fragid;

        vertid = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        fragid = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        GLES20.glShaderSource(vertid, sV);
        GLES20.glShaderSource(fragid, sF);

        GLES20.glCompileShader(vertid);

        System.out.println("Vertex log: " + GLES20.glGetShaderInfoLog(vertid));

        GLES20.glCompileShader(fragid);

        System.out.println("Fragment log: " + GLES20.glGetShaderInfoLog(fragid));

        progid = GLES20.glCreateProgram();

        GLES20.glAttachShader(progid, vertid);
        GLES20.glAttachShader(progid, fragid);

        GLES20.glLinkProgram(progid);

        System.out.println("Program log: " + GLES20.glGetShaderInfoLog(fragid));

        GLES20.glValidateProgram(progid);

        int attribPos = GLES20.glGetAttribLocation(progid, "vPosition");
        int attribCol = GLES20.glGetAttribLocation(progid, "vColor");

        GLES20.glUseProgram(progid);

        int matLoc = GLES20.glGetUniformLocation(progid, "pMat");

        GLES20.glUniformMatrix4fv(matLoc, 1, false, Matrix4.makeOrthoMatrix(0, 1080, 1920, 0, -1, 1).getData().asFloatBuffer());

        GLES20.glVertexAttribPointer(attribPos, 2, GLES20.GL_FLOAT, false, 6 * 4, 0);
        GLES20.glVertexAttribPointer(attribCol, 4, GLES20.GL_FLOAT, false, 6 * 4, 2 * 4);

        GLES20.glEnableVertexAttribArray(attribPos);
        GLES20.glEnableVertexAttribArray(attribCol);
    }

    @Override
    public void flush() {
        GLES20.glFlush();
        this.buffer.flip();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.vertexCount * 6 * 4, this.buffer, GLES20.GL_STREAM_DRAW);

        GLES20.glDrawArrays(this.getPrimitiveMode().getGl(), 0, this.vertexCount);

        this.buffer.clear();
        this.vertexCount = 0;
    }

    @Override
    public void color(float r, float g, float b, float a) {
        this.cRed = r;
        this.cGreen = g;
        this.cBlue = b;
        this.cAlpha = a;
    }

    @Override
    public void vertex(float x, float y) {
        this.buffer.put(x).put(y).put(cRed).put(cGreen).put(cBlue).put(cAlpha);

        vertexCount++;

        if (this.vertexCount == MAX_VERTICES) {
            this.flush();
        }
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

    @Override
    public void clear() {

    }

    @Override
    public void cleanup() {

    }
}
