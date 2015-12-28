package cz.trigon.bicepsrendererapi.managers.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cz.trigon.bicepsrendererapi.managers.content.ContentPreloader;

public interface IContentManager {
    void load() throws IOException;

    <T extends ILoadable> T get(String path, Class<T> type, boolean cache, Object[] parameters,
                                Class<?>[] parameterTypes) throws IOException;

    <T extends ILoadable> T get(String path, Class<T> type, boolean cache,
                                Object... parameters) throws IOException;

    <T extends ILoadable> T get(String path, Class<T> type) throws IOException;

    List<String> listFiles(String dir);

    List<String> listDirectories(String dir);

    InputStream openStream(String path, int mode) throws IOException;

    InputStream openStream(String path) throws IOException;

    ContentPreloader getPreloader();
}
