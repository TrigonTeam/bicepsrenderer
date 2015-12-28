package cz.trigon.bicepsrendererapi.gl.interfaces.shaders;

import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.gl.interfaces.matrices.IMatrix;

public interface IShader {

    int getId();

    String getName();

    void setUniform1f(String name, float val1);

    void setUniform2f(String name, float val1, float val2);

    void setUniform3f(String name, float val1, float val2, float val3);

    void setUniform4f(String name, float val1, float val2, float val3, float val4);

    void setUniform1i(String name, int val1);

    void setUniform2i(String name, int val1, int val2);

    void setUniform3i(String name, int val1, int val2, int val3);

    void setUniform4i(String name, int val1, int val2, int val3, int val4);

    void setUniformMatrix2f(String name, IMatrix matrix);

    void setUniformMatrix3f(String name, IMatrix matrix);

    void setUniformMatrix4f(String name, IMatrix matrix);

    boolean hasUniform(String name);

    boolean hasAttribute(String name);

    int getUniformLocation(String name);

    int getAttribLocation(String name);

    void deleteShader();

}