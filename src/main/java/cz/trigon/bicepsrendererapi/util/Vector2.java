package cz.trigon.bicepsrendererapi.util;

public class Vector2 {
    protected float x;
    protected float y;
    private int hash;
    private String str;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(Vector2 toCopy) {
        this(toCopy.x(), toCopy.y());
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;

        this.hash = 23;
        this.hash *= 31 + Float.floatToIntBits(x);
        this.hash *= 31 + Float.floatToIntBits(y);
    }

    public Vector2(float[] values) {
        this(values[0], values[1]);
    }

    public float getDistance(Vector2 other) {
        float dX = other.x - this.x;
        float dY = other.y - this.y;
        return (float) Math.sqrt(dX * dX + dY * dY);
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector2) {
            Vector2 v = (Vector2) o;
            return (v.x == this.x && v.y == this.y);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public String toString() {
        if(this.str == null)
            this.str = this.x + ";" + this.y;

        return this.str;
    }
}