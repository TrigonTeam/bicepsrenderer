package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import cz.trigon.bicepsrendererapi.gl.interfaces.ILocker;
import cz.trigon.bicepsrendererapi.util.Anchor;

public interface IFontRenderer extends ILocker {

	IFont getFont();

	void setFont(IFont font);

	float getSize();

	void setSize(float size);

	Anchor getAnchor();

	void setAnchor(Anchor anchor);

	void drawString(String string, float x, float y);

	float getStringWidth(String string);

	float getStringHeight(String string);

	float getMaxHeight();
}