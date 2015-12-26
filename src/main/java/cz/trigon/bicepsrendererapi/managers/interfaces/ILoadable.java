package cz.trigon.bicepsrendererapi.managers.interfaces;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.managers.content.ContentManager;

public interface ILoadable {
    void load(ContentManager content, String path) throws IOException;
    boolean canLoad(ContentManager content, String path);
}
