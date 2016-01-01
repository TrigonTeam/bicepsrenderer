package cz.trigon.bicepsrendererapi.managers.input;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.IInputManager;
import cz.trigon.bicepsrendererapi.util.Vector2;
import cz.trigon.bicepsrendererapi.util.Vector3;

public class InputManager implements IInputManager {
    private Surface context;

    public InputManager(Surface c) {
        this.context = c;

    }

    @Override
    public boolean isTouched() {
        return false;
    }

    @Override
    public float getTouchX() {
        return 0;
    }

    @Override
    public float getTouchY() {
        return 0;
    }

    @Override
    public float getDeltaX() {
        return 0;
    }

    @Override
    public float getDeltaY() {
        return 0;
    }

    @Override
    public Vector2 getTouch() {
        return null;
    }

    @Override
    public Vector2 getDelta() {
        return null;
    }

    @Override
    public float[] getTouchesX() {
        return new float[0];
    }

    @Override
    public float[] getTouchesY() {
        return new float[0];
    }

    @Override
    public float[] getDeltasX() {
        return new float[0];
    }

    @Override
    public float[] getDeltasY() {
        return new float[0];
    }

    @Override
    public Vector2[] getTouches() {
        return new Vector2[0];
    }

    @Override
    public Vector2[] getDeltas() {
        return new Vector2[0];
    }

    @Override
    public int getTouchCount() {
        return 0;
    }

    @Override
    public Vector3 getAcceleration() {
        return null;
    }

    @Override
    public Vector3 getRotation() {
        return null;
    }

    @Override
    public Vector3 getMagneticBias() {
        return null;
    }

    @Override
    public float getProximity() {
        return 0;
    }

    @Override
    public float getLightLevel() {
        return 0;
    }

    @Override
    public void performHapticFeedback(float force, int time) {

    }

    @Override
    public boolean isGyroSupported() {
        return false;
    }

    @Override
    public boolean isAccelSupported() {
        return false;
    }

    @Override
    public boolean isMultiTouchSupported() {
        return false;
    }

    @Override
    public boolean isCompassSupported() {
        return false;
    }

    @Override
    public boolean isHapticSupported() {
        return false;
    }

    @Override
    public boolean isLightSupported() {
        return false;
    }

    @Override
    public boolean isProximitySupported() {
        return false;
    }
}
