package cz.trigon.bicepsrendererapi.obj;

import cz.trigon.bicepsrendererapi.game.Surface;

public class Input {

    private static Surface surface;

    public static void init(Surface s) {
        Input.surface = s;
    }

    // TODO
    boolean isTouched;
    public boolean isTouched() {
        return this.isTouched;
    }
}
