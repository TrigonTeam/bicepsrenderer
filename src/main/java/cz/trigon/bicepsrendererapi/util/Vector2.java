package cz.trigon.bicepsrendererapi.util;

public class Vector2 {
    protected float x;
    protected float y;
    private int hash;

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
        this.hash *= 31 + ((int) (this.x * 100) * 3);
        this.hash *= 31 + ((int) (this.y * 100) * 7);
    }

    public float getDistance(Vector2 other) {
        if (other.equals(this))
            return 0;

        if (other.x == this.x)
            return Math.abs(other.y - this.y);

        if (other.y == this.y)
            return Math.abs(other.x - this.x);

        return (float) Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(this.y - other.y, 2));
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
        return this.x + ";" + this.y();
    }
}