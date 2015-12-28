package cz.trigon.bicepsrendererapi.obj;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ISpritesheet;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.util.Vector2;

public class Spritesheet implements ISpritesheet, ILoadable {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isSameSize() {
        return false;
    }

    @Override
    public Vector2 getDefaultSize() {
        return null;
    }

    @Override
    public ITexture[] getTextures() {
        return new ITexture[0];
    }

    @Override
    public void load(ContentManager content, String path) throws IOException {

    }

    @Override
    public boolean canLoad(ContentManager content, String path) {
        return false;
    }

    public void sort(String[] ssFileArgs) {

    }
}
