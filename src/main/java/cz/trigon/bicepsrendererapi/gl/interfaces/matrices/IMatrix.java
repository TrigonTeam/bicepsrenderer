package cz.trigon.bicepsrendererapi.gl.interfaces.matrices;

import java.nio.ByteBuffer;

public interface IMatrix {

    void setData(ByteBuffer data);

    ByteBuffer getData();

    int getSize();
}
