package cz.trigon.bicepsrendererapi.managers;

import android.view.MotionEvent;

import cz.trigon.bicepsrendererapi.game.Surface;

public class InputManager {
    private Surface context;

    public InputManager(Surface c) {
        this.context = c;

    }

    public void onTouch(MotionEvent e) {
        int act = e.getActionMasked();

        if (act == MotionEvent.ACTION_DOWN) {

        } else if (act == MotionEvent.ACTION_UP) {

        } else if (act == MotionEvent.ACTION_POINTER_DOWN) {

        } else if (act == MotionEvent.ACTION_POINTER_UP) {

        }
    }

    public void onBall(MotionEvent e) {

    }


    public int getTouchedPoints() {
        return 0;
    }

    public boolean haptic(int setting) {
        return this.context.performHapticFeedback(setting);
    }
}
