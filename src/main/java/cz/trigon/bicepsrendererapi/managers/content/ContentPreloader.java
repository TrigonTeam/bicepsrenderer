package cz.trigon.bicepsrendererapi.managers.content;

import android.util.Log;

import java.io.IOException;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.managers.interfaces.ILoadable;
import cz.trigon.bicepsrendererapi.obj.Content;
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
        for(String f : this.content.listFiles(path)) {
            if(f.endsWith(".png")) {
                try {
                    this.content.get(path, type);
                } catch (IOException e) {
                    Log.e(Surface.LDTAG, "Couldn't preload file " + path, e);
                }
            }
        }
    }

    private void preloadTextures(String path) {
        for(String f : this.content.listFiles(path)) {
            try {
                this.content.get(path, Texture.class);
            } catch (IOException e) {
                Log.e(Surface.LDTAG, "Couldn't preload file " + path, e);
            }
        }
    }
}
