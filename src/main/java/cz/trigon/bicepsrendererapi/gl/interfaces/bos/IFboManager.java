package cz.trigon.bicepsrendererapi.gl.interfaces.bos;

import cz.trigon.bicepsrendererapi.util.TextureSettings;

public interface IFboManager {

    void createFbo(String name, int width, int height, int attachments, TextureSettings s);

    IFbo getFbo(String name);

    void deleteFbo(String name, boolean deleteAttached);
}
