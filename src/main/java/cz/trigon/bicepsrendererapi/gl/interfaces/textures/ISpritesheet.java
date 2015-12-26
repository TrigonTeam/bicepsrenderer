package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import cz.trigon.bicepsrendererapi.util.Vector2;

public interface ISpritesheet {
    int getId();
    boolean isSameSize();
    Vector2 getDefaultSize();
    ITexture[] getTextures();
}
