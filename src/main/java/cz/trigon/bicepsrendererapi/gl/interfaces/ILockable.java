package cz.trigon.bicepsrendererapi.gl.interfaces;

public interface ILockable {
    void lockTo(Object o);
    void unlock();
    boolean isLocked();
    Object getLockingObject();
}
