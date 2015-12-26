package cz.trigon.bicepsrendererapi.obj;

import cz.trigon.bicepsrendererapi.game.Surface;

public class Texture {
    private static Surface surface;

    public static void init(Surface s) {
        Texture.surface = s;
    }

    public Texture(String contentPath) {

    }
}
