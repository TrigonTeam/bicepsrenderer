package cz.trigon.bicepsrendererapi.obj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.util.Vector2;

public class Texture implements ILoadable, ITexture {
    private static Surface surface;

    public static void init(Surface s) {
        Texture.surface = s;
    }

    private Bitmap bitmap;

    public Texture() {
    }

    @Override
    public void load(ContentManager content, String path) throws IOException {
        if (this.bitmap == null) {
            this.loadBitmap(content, path);

            if(this.bitmap == null)
                throw new IOException("Couldn't load file " + path);
        }

        // TODO
    }

    private void loadBitmap(ContentManager content, String path) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inScaled = false;

            this.bitmap = BitmapFactory.decodeStream(content.openStream(path), null, o);
        } catch (IOException ignored) {
        }
    }

    @Override
    public boolean canLoad(ContentManager content, String path) {
        this.loadBitmap(content, path);

        return this.bitmap != null;
    }


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isInSpritesheet() {
        return false;
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(this.bitmap.getWidth(), this.bitmap.getHeight());
    }
}
