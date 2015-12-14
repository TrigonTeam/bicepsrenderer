package cz.trigon.bicepsrendererapi.gl.interfaces.shaders;

public interface IShaderManager {
    void loadShader(String name, String vertex, String fragment);
    IShader getShader(String name);
}
