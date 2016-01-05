package cz.trigon.bicepsrendererapi.util;

public class Vector3 {
    protected float x;
    protected float y;
    protected float z;
    private int hash;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 toCopy) {
        this(toCopy.x(), toCopy.y(), toCopy.z());
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.hash = 23;
        this.hash *= 31 + Float.floatToIntBits(x);
        this.hash *= 31 + Float.floatToIntBits(y);
        this.hash *= 31 + Float.floatToIntBits(z);
    }

    public Vector3(float[] values) {
        this(values[0], values[1], values[2]);
    }

    public float getDistance(Vector3 other) {
        float dX = this.x - other.x;
        float dY = this.y - other.y;
        float dZ = this.z - other.z;

        return (float) Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector3) {
            Vector3 v = (Vector3) o;
            return (v.x == this.x && v.y == this.y && v.z == this.z);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public String toString() {
        return this.x + ";" + this.y + ";" + this.z;
    }
}