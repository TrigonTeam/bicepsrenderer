package cz.trigon.bicepsrendererapi.managers.content;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.obj.Spritesheet;
import cz.trigon.bicepsrendererapi.obj.Texture;

public class ContentPreloader {

    private ContentManager content;

    public ContentPreloader(ContentManager content) {
        this.content = content;
    }

    public void preload() {
        // this.preloadSomething("/default.fonts", .class);
        // this.preloadSomething("/default.music", .class);
        // this.preloadSomething("/default.sounds", .class);

        this.preloadTextures("/default.images");
    }

    // ^.*\.(spritesheet2\[)((?:\d)?\d+x(?:\d)?\d+)(\])(\.png)$ hell meant to be use later

    private void preloadFiles(String path, Class<? extends ILoadable> type) {
        for (String f : this.content.listFiles(path)) {
            if (f.endsWith(".png")) {
                try {
                    this.content.get(path, type);
                } catch (IOException e) {
                    Log.e(Surface.LDTAG, "Couldn't preload file " + path, e);
                }
            }
        }
    }

    private void preloadTextures(String path) {
        try {
            InputStream ssFile = this.content.openStream(this.content.combine(path, "spritesheets"));
            BufferedReader bf = new BufferedReader(new InputStreamReader(ssFile));
            String line;
            ArrayList<String> ssFiles = new ArrayList<>();

            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(";");
                Spritesheet s = this.content.get(this.content.combine(path, parts[1]),
                        Spritesheet.class, true, new Object[] { parts });

                if (parts[0].equalsIgnoreCase("file")) {
                    ssFiles.add(parts[1].toLowerCase());
                }
            }

            for (String f : this.content.listFiles(path)) {
                if(!ssFiles.contains(f.substring(0, f.lastIndexOf('/')))) {
                    try {
                        this.content.get(path, Texture.class);
                    } catch (IOException e) {
                        Log.e(Surface.LDTAG, "Couldn't preload file " + path, e);
                    }
                }
            }

        } catch (IOException e) {
            Log.e(Surface.LDTAG, "Spritesheets file doesn't exist", e);
        }
    }
}
