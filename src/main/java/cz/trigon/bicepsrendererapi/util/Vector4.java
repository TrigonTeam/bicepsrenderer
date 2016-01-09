package cz.trigon.bicepsrendererapi.util;

public class Vector4 {
    protected float x;
    protected float y;
    protected float z;
    protected float w;
    private int hash;
    private String str;

    public Vector4() {
        this(0, 0, 0, 0);
    }

    public Vector4(Vector4 toCopy) {
        this(toCopy.x(), toCopy.y(), toCopy.z(), toCopy.w());
    }

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        this.hash = 23;
        this.hash *= 31 + Float.floatToIntBits(x);
        this.hash *= 31 + Float.floatToIntBits(y);
        this.hash *= 31 + Float.floatToIntBits(z);
        this.hash *= 31 + Float.floatToIntBits(w);
    }

    public Vector4(float[] values) {
        this(values[0], values[1], values[2], values[4]);
    }

    public float getDistance(Vector4 other) {
        float dX = this.x - other.x;
        float dY = this.y - other.y;
        float dZ = this.z - other.z;
        float dW = this.w - other.w;

        return (float) Math.sqrt(dX * dX + dY * dY + dZ * dZ + dW * dW);
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

    public float w() {
        return this.w;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector4) {
            Vector4 v = (Vector4) o;
            return (v.x == this.x && v.y == this.y && v.z == this.z && v.w == this.w);
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
            this.str = this.x + ";" + this.y + ";" + this.z + ";" + this.w;

        return this.str;
    }
}
