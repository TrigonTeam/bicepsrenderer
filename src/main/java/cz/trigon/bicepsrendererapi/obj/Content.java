package cz.trigon.bicepsrendererapi.obj;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.content.ContentManager;
import cz.trigon.bicepsrendererapi.managers.content.ContentPreloader;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.managers.interfaces.IContentManager;

public class Content implements IContentManager {
    private static Surface surface;

    public static void init(Surface s) {
        Content.surface = s;
    }

    private ContentManager c;

    public Content() {
        this.c = Content.surface.getContent();
    }

    @Override
    public void load() throws IOException {
        this.c.load();
    }

    @Override
    public <T extends ILoadable> T get(String path, Class<T> type) {
        return this.c.get(path, type);
    }

    @Override
    public List<String> listFiles(String dir) {
        return this.c.listFiles(dir);
    }

    @Override
    public List<String> listDirectories(String dir) {
        return this.c.listDirectories(dir);
    }

    @Override
    public InputStream openStream(String path, int mode) throws IOException {
        return this.c.openStream(path, mode);
    }

    @Override
    public InputStream openStream(String path) throws IOException {
        return this.c.openStream(path);
    }

    @Override
    public ContentPreloader getPreloader() {
        return this.c.getPreloader();
    }
}
