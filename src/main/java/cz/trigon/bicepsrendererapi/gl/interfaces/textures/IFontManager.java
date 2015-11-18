package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

public interface IFontManager {
    IFont getFont(String name);

    void loadFont(String name, String path, float nominalSize, IFont.FontStyle style, IFont.FontDecoration[] decorations);

    default void loadFont(String name, String path, float nominalSize, IFont.FontStyle style) {
        this.loadFont(name, path, nominalSize, style, new IFont.FontDecoration[0]);
    }

    default void loadFont(String name, String path, float nominalSize) {
        this.loadFont(name, path, nominalSize, IFont.FontStyle.PLAIN);
    }

    void cleanup();

}
