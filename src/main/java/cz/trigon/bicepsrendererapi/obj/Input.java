package cz.trigon.bicepsrendererapi.obj;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.input.InputManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.IInputManager;
import cz.trigon.bicepsrendererapi.util.Vector2;
import cz.trigon.bicepsrendererapi.util.Vector3;

public class Input implements IInputManager {

    private static Surface surface;

    public static void init(Surface s) {
        Input.surface = s;
    }

    private InputManager input;

    public Input() {
        this.input = Input.surface.getInput();
    }

    @Override
    public boolean isTouched() {
        return this.input.isTouched();
    }

    @Override
    public float getTouchX() {
        return this.input.getTouchX();
    }

    @Override
    public float getTouchY() {
        return this.input.getTouchY();
    }

    @Override
    public Vector2 getTouch() {
        return this.input.getTouch();
    }

    @Override
    public float[] getTouchesX() {
        return this.input.getTouchesX();
    }

    @Override
    public float[] getTouchesY() {
        return this.input.getTouchesY();
    }

    @Override
    public Vector2[] getTouches() {
        return this.input.getTouches();
    }

    @Override
    public int getTouchCount() {
        return this.input.getTouchCount();
    }

    @Override
    public Vector3 getAcceleration() {
        return this.input.getAcceleration();
    }

    @Override
    public Vector3 getRotation() {
        return this.input.getRotation();
    }

    @Override
    public Vector3 getMagneticBias() {
        return this.input.getMagneticBias();
    }

    @Override
    public float getProximity() {
        return this.input.getProximity();
    }

    @Override
    public float getLightLevel() {
        return this.input.getLightLevel();
    }

    @Override
    public boolean isGyroSupported() {
        return this.input.isGyroSupported();
    }

    @Override
    public boolean isAccelSupported() {
        return this.input.isAccelSupported();
    }

    @Override
    public boolean isMultiTouchSupported() {
        return this.input.isMultiTouchSupported();
    }

    @Override
    public boolean isMagneticSupported() {
        return this.input.isMagneticSupported();
    }

    @Override
    public boolean isLightSupported() {
        return this.input.isLightSupported();
    }

    @Override
    public boolean isProximitySupported() {
        return this.input.isProximitySupported();
    }
}
