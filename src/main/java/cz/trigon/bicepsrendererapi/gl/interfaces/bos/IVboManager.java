package cz.trigon.bicepsrendererapi.gl.interfaces.bos;

public interface IVboManager {

    IVbo getVbo(String name);

    void bind(IVbo toBind);

    void bind(String name);

    void deleteVbo(String name);

}
