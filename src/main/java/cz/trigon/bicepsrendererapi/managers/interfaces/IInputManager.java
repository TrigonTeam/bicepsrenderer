package cz.trigon.bicepsrendererapi.managers.interfaces;

import cz.trigon.bicepsrendererapi.util.Vector2;
import cz.trigon.bicepsrendererapi.util.Vector3;

public interface IInputManager {
    boolean isTouched();

    float getTouchX();

    float getTouchY();

    Vector2 getTouch();

    float[] getTouchesX();

    float[] getTouchesY();

    Vector2[] getTouches();

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
