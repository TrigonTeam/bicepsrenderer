package cz.trigon.bicepsrendererapi.managers.interfaces;

import cz.trigon.bicepsrendererapi.util.Vector2;
import cz.trigon.bicepsrendererapi.util.Vector3;

public interface IInputManager {
    boolean isTouched();

    float getTouchX();

    float getTouchY();

    float getDeltaX();

    float getDeltaY();

    Vector2 getTouch();

    Vector2 getDelta();

    float[] getTouchesX();

    float[] getTouchesY();

    float[] getDeltasX();

    float[] getDeltasY();

    Vector2[] getTouches();

    Vector2[] getDeltas();

    int getTouchCount();

    Vector3 getAcceleration();

    Vector3 getRotation();

    Vector3 getMagneticBias();

    float getProximity();

    float getLightLevel();

    boolean isGyroSupported();

    boolean isAccelSupported();

    boolean isMultiTouchSupported();

    boolean isMagneticSupported();

    boolean isLightSupported();

    boolean isProximitySupported();
}
