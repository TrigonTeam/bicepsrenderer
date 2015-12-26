package cz.trigon.bicepsrendererapi.managers.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;

// This class does all the dirty work
class ContentEntry {
    String path;
    String assetsPath;
    List <ContentEntry> childEntries;
    boolean isFile;
    String name;
    ContentEntry parentEntry;
    List<String> files, directories;
    Map<Class<? extends ILoadable>, ILoadable> repr;
    boolean preloaded;

    protected void addChild(ContentEntry e) {
        this.childEntries.add(e);
        if(e.isFile) {
            this.files.add(e.path);
        } else {
            this.directories.add(e.path);
        }
    }

    protected ContentEntry(boolean isFile) {
        if(!isFile) {
            this.childEntries = new ArrayList<>();
            this.files = new ArrayList<>();
            this.directories = new ArrayList<>();
        }

        this.repr = new HashMap<>();
        this.isFile = isFile;
    }

    protected ContentEntry(boolean isFile, String assPath, String name, ContentEntry parent) {
        this(isFile);
        this.parentEntry = parent;
        this.path = "/" + assPath;
        this.assetsPath = assPath;
        this.name = name;
    }
}
