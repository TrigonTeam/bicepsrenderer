package cz.trigon.bicepsrendererapi.gl.interfaces.bos;

public interface IFbo {
    String getName();

    int getTextureId(int n);
    int getFboId();

    int getWidth();
    int getHeight();

    int getAttachmentCount();
}