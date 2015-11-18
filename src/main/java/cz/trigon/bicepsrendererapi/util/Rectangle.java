package cz.trigon.bicepsrendererapi.util;

public class Rectangle {
    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 maxPosition;

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public Rectangle(float x, float y, float width, float height) {
        this(new Vector2(x, y), new Vector2(width, height));
    }

    public Rectangle(Vector2 pos, Vector2 size) {
        this.position = pos;
        this.size = size;
        this.maxPosition = new Vector2(pos.x() + size.x(), pos.y() + size.y());
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 pos) {
        this.setPosition(pos.x(), pos.y());
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void setSize(Vector2 size) {
        this.setSize(size.x(), size.y());
    }

    public Vector2 getMaxPosition() {
        return this.maxPosition;
    }

    public float x1() {
        return this.position.x();
    }

    public float y1() {
        return this.position.y();
    }

    public float x2() {
        return this.maxPosition.x();
    }

    public float y2() {
        return this.maxPosition.y();
    }

    public float width() {
        return this.size.x();
    }

    public float height() {
        return this.size.y();
    }

    public void setPosition(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void setSize(float width, float height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public void setX(float x) {
        this.position.setX(x);
        this.maxPosition.setX(x + this.size.x());
    }

    public void setY(float y) {
        this.position.setY(y);
        this.maxPosition.setY(y + this.size.y());
    }

    public void setWidth(float width) {
        this.size.setX(width);
        this.maxPosition.setX(this.position.x() + width);
    }

    public void setHeight(float height) {
        this.size.setY(height);
        this.maxPosition.setY(this.position.y() + height);
    }

    public void setX2(float x2) {
        this.setWidth(x2 - this.x1());
    }

    public void setY2(float y2) {
        this.setHeight(y2 - this.y1());
    }

    public void move(float x, float y) {
        this.setPosition(this.x1() + x, this.y1() + y);
    }

    public boolean intersects(Rectangle other) {
        if (other.equals(this))
            return true;

        return (this.x2() > other.x1() && this.x1() < other.x2() && this.y2() > other.y1() && this.y1() < other.y2());
    }

    public boolean contains(Vector2 point) {
        return (this.x1() <= point.x() && this.x2() >= point.x() && this.y1() <= point.y() && this.y2() >= point.y());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Rectangle) {
            Rectangle r = (Rectangle) other;
            return (this.position.equals(r.position) && this.size.equals(r.size));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.position.hashCode() ^ this.size.hashCode();
    }

}
