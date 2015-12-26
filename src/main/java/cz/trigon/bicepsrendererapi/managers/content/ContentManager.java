package cz.trigon.bicepsrendererapi.managers.content;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.IContentManager;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;

public class ContentManager implements IContentManager {

    private ContentEntry root;
    private AssetManager asset;
    private ContentPreloader preloader;
    private Map<String, ContentEntry> pathMappings;

    public ContentManager(AssetManager assetManager) {
        this.asset = assetManager;
        this.root = new ContentEntry(false, "", "root", null);
        this.pathMappings = new HashMap<>();
        this.pathMappings.put("/", this.root);
    }

    private boolean isFile(String path) {
        try {
            this.asset.open(path).close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    // Slow like a verstopfung, it would be better to do compile-time indexing,
    // but this will have to suffice for now.
    public void load() throws IOException {
        long start = System.nanoTime();
        this.load(this.root);
        long time = System.nanoTime() - start;
        Log.i(Surface.LDTAG, "Load time: " + (time / 1000000d) + " ms");
    }

    private void load(ContentEntry entry) throws IOException {
        if (!entry.isFile) {
            for (String s : this.asset.list(entry.assetsPath)) {
                String path = entry.assetsPath.equals("") ? s : (entry.assetsPath + "/" + s);

                boolean file = this.isFile(path);
                ContentEntry rE = new ContentEntry(file, path, s, entry);
                entry.addChild(rE);

                if (!file) {
                    this.load(rE);
                }

                this.pathMappings.put(rE.path, rE);
            }
        }
    }

    public <T extends ILoadable> T get(String path, Class<T> type) throws IOException {
        return this.get(path, type, true);
    }

    public <T extends ILoadable> T get(String path, Class<T> type, boolean cache) throws IOException {
        ContentEntry e = this.pathMappings.get(path);
        if (e == null)
            return null;

        if (cache && e.repr.containsKey(type))
            return (T) e.repr.get(type);

        try {
            T l = type.newInstance();
            if (!l.canLoad(this, path))
                throw new InvalidClassException(type.getName(),
                        "Loaded data aren't represented by supplied ILoadable type");

            l.load(this, path);

            if (cache)
                e.repr.put(type, l);

            return l;

        } catch (InstantiationException ex) {
            Log.e(Surface.LDTAG, "Couldn't create " + type.getName() + " object", ex);
        } catch (IllegalAccessException ignored) {
        }

        // TODO

        return null;
    }

    public List<String> listFiles(String dir) {
        ContentEntry e = this.pathMappings.get(dir);
        if (e == null || e.isFile)
            return null;

        return new ArrayList<String>(e.files);
    }

    public List<String> listDirectories(String dir) {
        ContentEntry e = this.pathMappings.get(dir);
        if (e == null || e.isFile)
            return null;

        return new ArrayList<String>(e.directories);
    }

    public InputStream openStream(String path, int mode) throws IOException {
        ContentEntry e = this.pathMappings.get(path);
        if (e == null || !e.isFile)
            return null;

        return this.asset.open(e.assetsPath, mode);
    }

    public InputStream openStream(String path) throws IOException {
        return this.openStream(path, AssetManager.ACCESS_STREAMING);
    }

    public String combine(String... parts) {
        StringBuilder b = new StringBuilder(parts.length * 2);
        if (!parts[0].startsWith("/"))
            b.append('/');

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].endsWith("/") && i != (parts.length - 1))
                parts[i] += "/";

            b.append(parts[i]);
        }

        return b.toString();
    }

    public boolean containsFile(String path) {
        return this.pathMappings.containsKey(path);
    }

    public ContentPreloader getPreloader() {
        return this.preloader;
    }
}
