package cz.trigon.bicepsrendererapi.managers.interfaces;

import cz.trigon.bicepsrendererapi.managers.content.ContentManager;

public interface ILoadable {
    void load(ContentManager content, String path);
}
