package cz.trigon.bicepsrendererapi.gl.interfaces.render;

import java.nio.ByteBuffer;

public interface IAttributeProvider {
    void useBuffer(ByteBuffer buffer);
    void preFlush();
    void onFlush();
    void postFlush();
    boolean canFlush();
}