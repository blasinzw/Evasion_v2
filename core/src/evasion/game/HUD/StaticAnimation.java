package evasion.game.HUD;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import evasion.game.Evasion;
import evasion.utils.DrawLevel;
import evasion.utils.GameDrawable;

/**
 * Created by Zander on 4/11/2015.
 */
public class StaticAnimation extends GameDrawable {

    private Vector2 position;
    private final DrawLevel drawLevel = DrawLevel.HUD;

    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureRegion[] textureRegions;
    private TextureRegion currentFrame;
    private Sprite image;

    private boolean flip;

    private float scale;

    private float stateTime;

    /**
     * Creates an animation that does not move its x and y coordinates.
     * @param game The game class, used to access asset manager.
     * @param position The position.
     * @param path The path used to locate the Texture Atlas.
     * @param name The name used in the .pack file to identify each image, for example: name1, name2, etc.
     * @param scale The factor used to scale the image.
     * @param playMode The type of playmode used to govern the animation sequence.
     */
    public StaticAnimation(final Evasion game, Vector2 position, String path, String name, float scale, Animation.PlayMode playMode) {
        this.position = position;
        this.scale = scale;
        stateTime = 0f;

        flip = false;

        textureAtlas = game.manager.get(path, TextureAtlas.class);
        textureRegions = new TextureRegion[textureAtlas.findRegions(name).size];
        for (int i=0; i<textureAtlas.findRegions(name).size; i++) {
            textureRegions[i] = textureAtlas.findRegion(name, i);
        }
        animation = new Animation(0.1f, textureRegions);
        animation.setPlayMode(playMode);
        currentFrame = animation.getKeyFrame(stateTime);
        image = new Sprite(animation.getKeyFrame(0, true));
        image.scale(scale);
        image.setPosition(position.x, position.y);
    }

    @Override
    public DrawLevel getDrawLevel() {
        return drawLevel;
    }

    @Override
    public void draw(Batch batch) {
        image.draw(batch);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        //setting the current frame
        currentFrame = animation.getKeyFrame(stateTime, true);

        //flips animation
        if (!flip && animation.getKeyFrameIndex(stateTime) == 5) {
            flip = true;
        }else if (flip && animation.getKeyFrameIndex(stateTime) == 0) {
            flip = false;
        }
        if (flip) {
            currentFrame.flip(true, false);
        }

        //set new frame and bounds
        image = new Sprite(currentFrame);
        image.scale(scale);
        image.setPosition(position.x, position.y);
    }
}
