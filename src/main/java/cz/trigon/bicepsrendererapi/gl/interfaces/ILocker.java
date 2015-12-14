package cz.trigon.bicepsrendererapi.gl.interfaces;

public interface ILocker {
    void lockTo(ILockable o);
    void unlock();
    boolean isLocked();
    ILockable getLockedObject();
}
