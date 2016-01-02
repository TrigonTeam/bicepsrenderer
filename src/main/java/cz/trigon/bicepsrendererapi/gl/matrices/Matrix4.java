package cz.trigon.bicepsrendererapi.gl.matrices;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.gl.interfaces.matrices.IMatrix;

public class Matrix4 implements IMatrix {

    private ByteBuffer data;

    public Matrix4() {
        this.data = ByteBuffer.allocateDirect(4 * 4 * 4);
        this.data.order(ByteOrder.nativeOrder());
    }

    public static Matrix4 makeProjMatrix(float fov, float aspect, float near, float far) {
        Matrix4 mat = new Matrix4();
        FloatBuffer data = mat.getData().asFloatBuffer();

        float uh = (float) (1f / Math.tan(fov / 2f));
        float uw = -uh / aspect;

        float[] dataArray = new float[]{
                uw, 0, 0, 0,
                0, uh, 0, 0,
                0, 0, far/(far-near), 1,
                0, 0, -(far*near)/(far-near), 0
        };

        data.put(dataArray);

        return mat;
    }

    public static Matrix4 makeOrthoMatrix(float l, float r, float b, float t, float n, float f) {
        Matrix4 mat = new Matrix4();
        FloatBuffer data = mat.getData().asFloatBuffer();

        float[] dataArray = new float[]{
                2f / (r - l), 0, 0, 0,
                0, 2f / (t - b), 0, 0,
                0, 0, -2f / (f - n), 0,
                -(r + l) / (r - l), -(t + b) / (t - b), -(f + n) / (f - n), 1
        };

        data.put(dataArray);

        return mat;
    }

    @Override
    public ByteBuffer getData() {
        return this.data;
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public void setData(ByteBuffer data) {
        this.data = data;
    }
}
