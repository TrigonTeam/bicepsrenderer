package cz.trigon.bicepsrendererapi.util;

public class Vector3 {
    protected float x;
    protected float y;
    protected float z;

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
    }

    public float getDistance(Vector3 other) {
        if (other.equals(this))
            return 0;

        if (other.x() == this.x)
            return Math.abs(other.y - this.y);

        if (other.y() == this.y)
            return Math.abs(other.x - this.x);

        return (float) Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(this.y - other.y, 2)
                + Math.pow(other.z - this.z, 2));
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
        return (Float.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode());
    }

    @Override
    public String toString() {
        return this.x + ";" + this.y + ";" + this.z;
    }
}