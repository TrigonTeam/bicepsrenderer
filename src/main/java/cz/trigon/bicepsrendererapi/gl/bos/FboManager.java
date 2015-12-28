package cz.trigon.bicepsrendererapi.gl.bos;

import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;
import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFboManager;
import cz.trigon.bicepsrendererapi.util.TextureSettings;

public class FboManager implements IFboManager {

    @Override
    public void createFbo(String name, int width, int height, int attachments, TextureSettings s) {

    }

    @Override
    public IFbo getFbo(String name) {
        return null;
    }

    @Override
    public void deleteFbo(String name, boolean deleteAttached) {

    }
}
