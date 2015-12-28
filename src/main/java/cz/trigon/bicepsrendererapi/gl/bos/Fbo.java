package cz.trigon.bicepsrendererapi.gl.bos;

import cz.trigon.bicepsrendererapi.gl.interfaces.bos.IFbo;

public class Fbo implements IFbo{


    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getTextureId(int n) {
        return 0;
    }

    @Override
    public int getFboId() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getAttachmentCount() {
        return 0;
    }
}
