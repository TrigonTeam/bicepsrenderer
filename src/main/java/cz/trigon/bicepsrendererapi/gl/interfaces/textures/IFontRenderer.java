package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import cz.trigon.bicepsrendererapi.gl.interfaces.ILockable;
import cz.trigon.bicepsrendererapi.util.Anchor;
import cz.trigon.bicepsrendererapi.util.Vector2;

public interface IFontRenderer extends ILockable {

	IFont getFont();

	void setFont(IFont font);

	float getSize();

	void setSize(float size);

	Anchor getAnchor();

	void setAnchor(Anchor anchor);

	void drawString(String string, float x, float y);

	default void drawString(String string, Vector2 pos) {
		drawString(string, pos.x(), pos.y());
	}

	float getStringWidth(String string);

	float getStringHeight(String string);

	float getMaxHeight();
}