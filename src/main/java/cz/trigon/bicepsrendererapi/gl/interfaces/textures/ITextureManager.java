package cz.trigon.bicepsrendererapi.gl.interfaces.textures;

import android.graphics.Bitmap;

import java.util.Map;

import cz.trigon.bicepsrendererapi.util.TextureSettings;
import cz.trigon.bicepsrendererapi.util.Vector2;

public interface ITextureManager {
    void cleanup();

    ITexture getTexture(int id);

    ITexture getTexture(int spritesheetId, int id);

    ISpritesheet getSpritesheet(int spritesheetId);

    // ID 0 = default spritesheet
    void loadTexture(int id, Bitmap bitmap);

    /**
     * Loads spritesheet from one file (images numbered by x-position)
     *
     * @param spritesheetId ID of created spritesheet
     * @param contentPath          Path of spritesheet file
     * @param spriteSize    Size of one image
     */
    void loadSpritesheet(int spritesheetId, String contentPath, Vector2 spriteSize);

    /**
     * Loads spritesheet from multiple files
     *
     * @param spritesheetId ID of created spritesheet
     * @param contentPathMask Path mask (use %n for image number, e.g "images/player_%n", starting with <b>zero</b>)
     * @param numberOfFiles Number of loaded files
     */
    void loadSpritesheet(int spritesheetId, String contentPathMask, int numberOfFiles);

    /**
     * Loads one texture file
     *
     * @param id          ID of loaded texture
     * @param contentPath Name of texture in used content manager
     * @param settings    Custom settings for texture
     */
    void loadTexture(int id, String contentPath, TextureSettings settings);

    /**
     * Loads spritesheet from one file (images numbered by x-position)
     *
     * @param spritesheetId ID of created spritesheet
     * @param contentPath   Path of spritesheet file
     * @param spriteSize    Size of one image
     * @param settings      Map with custom settings for each texture
     */
    void loadSpritesheet(int spritesheetId, String contentPath, Vector2 spriteSize, Map<Integer, TextureSettings> settings);
    
    void loadSpritesheet(int spritesheetId, String contentPath, Vector2 spriteSize, TextureSettings settings);

    /**
     * Loads spritesheet from multiple files
     *
     * @param spritesheetId   ID of created spritesheet
     * @param contentPathMask Path mask (use %n for image number, e.g "images/player_%n", starting with <b>zero</b>)
     * @param numberOfFiles   Number of loaded files
     * @param settings        Map with custom settings for each texture
     */
    void loadSpritesheet(int spritesheetId, String contentPathMask, int numberOfFiles, Map<Integer, TextureSettings> settings);

    /**
     * Finishes texture loading
     *
     * @return Loaded successfully
     */
    boolean finishLoading();
}
