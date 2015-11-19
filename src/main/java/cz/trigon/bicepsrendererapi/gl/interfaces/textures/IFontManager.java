package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import cz.trigon.bicepsrendererapi.gl.interfaces.ILockable;

public interface IFontManager extends ILockable {
    IFont getFont(String name);

    void loadFont(String name, String path, float nominalSize, IFont.FontStyle style, IFont.FontDecoration[] decorations);

    void loadFont(String name, String path, float nominalSize, IFont.FontStyle style);

    void loadFont(String name, String path, float nominalSize);

    void cleanup();

}
