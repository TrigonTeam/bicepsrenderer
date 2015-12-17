package cz.trigon.bicepsrendererapi.gl.interfaces;

public interface ILockable {
    boolean isLocked();
    void setLocked(ILocker lockable);
    ILocker getLocked();

}
