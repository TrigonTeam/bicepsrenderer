package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

public interface IFont {

    float getStringWidth(String string);

    float getStringHeight(String string);

    float getCharWidth(char c);

    float getCharHeight(char c);

    float getMaxHeight();

    FontStyle getFontStyle();

    FontDecoration[] getFontDecorations();

    float getNominalSize();

    enum FontStyle {
        BOLD, ITALIC, BOLD_ITALIC, PLAIN
    }

    enum FontDecoration {
        UNDERLINE, LINE_THROUGH // We shall always miss you, OVERLINE. Rest in RIP
    }
}
