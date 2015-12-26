package cz.trigon.bicepsrendererapi.content;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.trigon.bicepsrendererapi.managers.interfaces.IContentManager;

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
            this.asset.list(path);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    // Slow like a verstopfung, it would be better to do compile-time indexing,
    // but this will have to suffice for now.
    public void load() throws IOException {
        long start = System.nanoTime();
        this.load(this.root);
        long time = System.nanoTime() - start;
        Log.i("TBR-Debug", "Load time: " + (time / 1000000d) + " ms");
    }

    private void load(ContentEntry entry) throws IOException {
        if(!entry.isFile) {
            for (String s : this.asset.list(entry.assetsPath)) {
                String path = entry.assetsPath.equals("") ? s : (entry.assetsPath + "/" + s);

                boolean file = this.isFile(s);
                ContentEntry rE = new ContentEntry(file, path, s, entry);
                entry.childEntries.add(rE);

                if(!file) {
                    this.load(rE);
                }

                this.pathMappings.put(rE.path, rE);
            }
        }
    }


    public <T extends ILoadable> T get(String path, Class<T> type) {
        ContentEntry e = this.pathMappings.get(path);
        if(e == null || !e.isFile)
            return null;

        // TODO

        return null;
    }

    public List<String> listFiles(String dir) {
        ContentEntry e = this.pathMappings.get(dir);
        if(e == null || e.isFile)
            return null;

        return new ArrayList<String>(e.files);
    }

    public List<String> listDirectories(String dir) {
        ContentEntry e = this.pathMappings.get(dir);
        if(e == null || e.isFile)
            return null;

        return new ArrayList<String>(e.directories);
    }

    public InputStream openStream(String path, int mode) throws IOException {
        ContentEntry e = this.pathMappings.get(path);
        if(e == null || !e.isFile)
            return null;

        return this.asset.open(e.assetsPath, mode);
    }

    public InputStream openStream(String path) throws IOException {
        return this.openStream(path, AssetManager.ACCESS_STREAMING);
    }

    public ContentPreloader getPreloader() {
        return this.preloader;
    }
}
