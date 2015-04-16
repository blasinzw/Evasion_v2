package evasion.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.Collidable;
import evasion.utils.CollisionType;
import evasion.utils.DrawLevel;
import evasion.utils.SoundEffect;

/**
 * Created by Zander on 4/11/2015.
 */
public class MineExplosion extends GameObject implements Pool.Poolable {

    private float duration, durationTimer;

    //images
    private TextureAtlas textureAtlas;

    //bounds
    private Sprite image;
    private Rectangle bounds;

    public MineExplosion(final Evasion game, float duration) {
        super(DrawLevel.OBJECTS, game);

        this.duration = duration;

        durationTimer = 0f;

        load();
        bounds = image.getBoundingRectangle();

    }

    private void load() {
        textureAtlas = game.manager.get("images/mine/mine.pack", TextureAtlas.class);

        image = textureAtlas.createSprite("explosion");

        image.scale(Constants.MINE_EXPLOSION_SCALE);
    }

    public void init(Vector2 position, Vector2 velocity) {
        this.velocity = velocity;
        this.position = position;
        setLiving(true);
        durationTimer = 0f;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.MINE;
    }

    @Override
    public void collideWith(Collidable collidable) {
        if (Intersector.overlaps(collidable.getBounds(), bounds)) {
            switch (collidable.getCollisionType()) {
                case PLAYER:
                    collidable.collide();
                    break;
                case ASTEROID:
                    collidable.collide();
                    break;
                case MINE:
                    break;
                case DROP:
                    break;
                case LASER:
                    break;
            }
        }
    }

    @Override
    public void collide() {
        setLiving(false);
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
        //  velocity.scl(delta) accounts for time.
        position.add(velocity.scl(delta));
        velocity.scl(1 / delta); //unscales velocity vector

        image.setPosition(position.x, position.y);
        bounds.setPosition(position);

        if (durationTimer > duration) {
            setLiving(false);
        }
        durationTimer += delta;

        checkIfInScreen();
    }

    private void checkIfInScreen() {
        if (bounds.getY() < -bounds.getHeight()) {
            setLiving(false);
        }
    }

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {
        position.set(0, 200);
        velocity.set(0, 0);
    }


}
