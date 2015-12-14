package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import cz.trigon.bicepsrendererapi.gl.interfaces.ILocker;

public interface IFontManager extends ILocker {
    IFont getFont(String name);

    void loadFont(String name, String path, float nominalSize, IFont.FontStyle style, IFont.FontDecoration[] decorations);

    void loadFont(String name, String path, float nominalSize, IFont.FontStyle style);

    void loadFont(String name, String path, float nominalSize);

    void cleanup();

}
