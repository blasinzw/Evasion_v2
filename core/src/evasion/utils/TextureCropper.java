package evasion.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class has several methods to help crop a texture region.
 */
public class TextureCropper {

    /**
     * Crops from origin of the textureRegion.
     * @param region the TextureRegion to crop.
     * @param width width from origin.
     * @param height height from origin.
     * @return new TextureRegion.
     */
    public static TextureRegion cropTexture (TextureRegion region, int width, int height) {
        return new TextureRegion(region.getTexture(), region.getRegionX(), region.getRegionY(),
                width, height);

    }

}
