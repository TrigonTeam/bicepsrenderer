package cz.trigon.bicepsrendererapi.gl.interfaces.bos;

public interface IVboManager {

    boolean exists(String name);

    int create(String name);

    void bind(String name);

    void delete(String name);

}
