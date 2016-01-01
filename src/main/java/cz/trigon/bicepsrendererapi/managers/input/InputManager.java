package cz.trigon.bicepsrendererapi.managers.input;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.IInputManager;
import cz.trigon.bicepsrendererapi.util.Vector2;
import cz.trigon.bicepsrendererapi.util.Vector3;

public class InputManager implements IInputManager, SensorEventListener {

    public static final int MAX_TOUCHES = 16;

    private Surface surface;
    private SensorManager sensor;
    private boolean gyro, accel, multi, compass, light, prox;
    private Vector3 gyroV, accelV, compassV;
    private float lightV, proxV;

    private float[] touchX, touchY;

    // These will be sent to hell one day
    private float xtouchX, xtouchY, xlastTouchX, xlastTouchY;
    private boolean xisDown;

    public InputManager(Surface c) {
        this.surface = c;
        this.sensor = (SensorManager) c.getContext().getSystemService(Context.SENSOR_SERVICE);
        this.touchX = new float[InputManager.MAX_TOUCHES];
        this.touchY = new float[InputManager.MAX_TOUCHES];
        this.checkSupported();
    }

    private void checkSupported() {
        this.gyro = this.sensor.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null;
        this.accel = this.sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
        this.compass = this.sensor.getDefaultSensor(Sensor.TYPE_GRAVITY) != null;
        this.light = this.sensor.getDefaultSensor(Sensor.TYPE_LIGHT) != null;
        this.prox = this.sensor.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null;
        this.multi = this.surface.getContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
    }

    public void registerListeners() {
        this.sensor.registerListener(this, this.sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        this.sensor.registerListener(this, this.sensor.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);
        this.sensor.registerListener(this, this.sensor.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
        this.sensor.registerListener(this, this.sensor.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_GAME);
        this.sensor.registerListener(this, this.sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterListeners() {
        this.sensor.unregisterListener(this);
    }

    public void tick() {
        this.xtouchX = this.xlastTouchX;
        this.xtouchY = this.xlastTouchY;
    }

    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                this.xlastTouchX = e.getX(0) * this.surface.getCanvasRatio();
                this.xlastTouchY = e.getY(0) * this.surface.getCanvasRatio();
                this.xisDown = true;
                break;

            case MotionEvent.ACTION_MOVE:
                this.xlastTouchX = e.getX(0) * this.surface.getCanvasRatio();
                this.xlastTouchY = e.getY(0) * this.surface.getCanvasRatio();
                break;

            case MotionEvent.ACTION_UP:
                this.xisDown = false;
                break;
        }

        return true;
    }

    @Override
    public boolean isGyroSupported() {
        return this.gyro;
    }

    @Override
    public boolean isAccelSupported() {
        return this.accel;
    }

    @Override
    public boolean isMultiTouchSupported() {
        return this.multi;
    }

    @Override
    public boolean isMagneticSupported() {
        return this.compass;
    }

    @Override
    public boolean isLightSupported() {
        return this.light;
    }

    @Override
    public boolean isProximitySupported() {
        return this.prox;
    }

    @Override
    public boolean isTouched() {
        return this.xisDown;
    }

    @Override
    public float getTouchX() {
        return this.xtouchX;
    }

    @Override
    public float getTouchY() {
        return this.xtouchY;
    }

    public float getLatestTouchX() {
        return this.xlastTouchX;
    }

    public float getLatestTouchY() {
        return this.xlastTouchY;
    }

    @Override
    public Vector2 getTouch() {
        return new Vector2(this.xtouchX, this.xtouchY);
    }


    // TODO Multitouch

    @Override
    public float[] getTouchesX() {
        return new float[0];
    }

    @Override
    public float[] getTouchesY() {
        return new float[0];
    }

    @Override
    public Vector2[] getTouches() {
        return new Vector2[0];
    }

    @Override
    public int getTouchCount() {
        return this.xisDown ? 1 : 0;
    }

    // END

    @Override
    public Vector3 getAcceleration() {
        return this.accelV;
    }

    @Override
    public Vector3 getRotation() {
        return this.gyroV;
    }

    @Override
    public Vector3 getMagneticBias() {
        return this.compassV;
    }

    @Override
    public float getProximity() {
        return this.proxV;
    }

    @Override
    public float getLightLevel() {
        return this.lightV;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                this.accelV = new Vector3(event.values);
                break;
            case Sensor.TYPE_GYROSCOPE:
                this.gyroV = new Vector3(event.values);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.compassV = event.values.length > 3 ?
                        new Vector3(event.values[3], event.values[4], event.values[5]) :
                        new Vector3(event.values);
                break;
            case Sensor.TYPE_PROXIMITY:
                this.proxV = event.values[0];
                break;
            case Sensor.TYPE_LIGHT:
                this.lightV = event.values[0];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
