package cz.trigon.bicepsrendererapi.obj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.interfaces.ILockable;
import cz.trigon.bicepsrendererapi.gl.interfaces.ILocker;
import cz.trigon.bicepsrendererapi.gl.interfaces.textures.ITexture;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.util.Vector2;

public class Texture implements ILoadable, ILockable, ITexture {
    private static Surface surface;

    public static void init(Surface s) {
        Texture.surface = s;
    }

    private ILocker locker;
    private Bitmap bitmap;

    public Texture() {
    }

    public Texture(String contentPath) throws IOException {
        this.load(Texture.surface.getContent(), contentPath);
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
    public boolean isLocked() {
        return this.locker != null;
    }

    @Override
    public void setLocked(ILocker lockable) {
        this.locker = lockable;
    }

    @Override
    public ILocker getLocked() {
        return this.locker;
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(this.bitmap.getWidth(), this.bitmap.getHeight());
    }
}
