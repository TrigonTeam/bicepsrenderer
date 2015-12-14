package cz.trigon.bicepsrendererapi.content;

import java.util.ArrayList;
import java.util.List;

// This class does all the dirty work
class ContentEntry {
    String path;
    String assetsPath;
    List <ContentEntry> childEntries;
    boolean isFile;
    String name;
    ContentEntry parentEntry;
    List<String> files, directories;

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
