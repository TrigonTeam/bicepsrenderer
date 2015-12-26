package cz.trigon.bicepsrendererapi.gl.interfaces.textures;


import cz.trigon.bicepsrendererapi.util.Vector2;

public interface ITexture {
    int getId();
    boolean isInSpritesheet();
    Vector2 getSize();
}
