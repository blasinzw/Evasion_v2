package evasion.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Circle;
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

import java.util.Random;

/**
 * Created by Zander on 4/11/2015.
 */
public class Mine extends GameObject implements Pool.Poolable {

    private int speed, radius;

    private boolean primed, exploding;

    private float primeTimer, primeDuration;

    //images
    private TextureAtlas textureAtlas;

    //bounds
    private Sprite image;
    private Rectangle bounds;
    private Circle detectionBounds;

    private Random rand = new Random();

    public Mine(final Evasion game, int speed, int radius, float primeDuration) {
        super(DrawLevel.OBJECTS, game);

        position = new Vector2((float)rand.nextInt(Constants.VIRTUAL_WIDTH), (float) Constants.VIRTUAL_HEIGHT + 200);
        velocity = new Vector2(0, speed);
        this.speed = speed;
        this.radius = radius;

        this.primeDuration = primeDuration;

        primeTimer = 0f;

        load();
        bounds = image.getBoundingRectangle();
        detectionBounds = new Circle(position, radius);

    }

    private void load() {
        textureAtlas = game.manager.get("images/mine/mine.pack", TextureAtlas.class);

        image = new Sprite(textureAtlas.findRegion("mineDefault"));
        image.scale(Constants.MINE_SCALE);
    }

    public void init() {
        velocity.set(0, speed);
        setLiving(true);
        load();
        bounds = image.getBoundingRectangle();
        detectionBounds = new Circle(position, radius);

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
                    break;
                case ASTEROID:
                    collidable.collide();
                    break;
                case MINE:
                    setLiving(false);
                    break;
                case DROP:
                    break;
                case LASER:
                    break;
            }
        }
        if (Intersector.overlaps(detectionBounds, collidable.getBounds()) && !isPrimed()) {
            switch (collidable.getCollisionType()) {
                case PLAYER:
                    setPrimed(true);
                    SoundEffect.playMinePrime(game);
                    break;
                case ASTEROID:
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

        if (primed) {
            image = new Sprite(textureAtlas.findRegion("minePrimed"));
            image.scale(Constants.MINE_SCALE);
            primeTimer += delta;
        }
        if (primeTimer > primeDuration) {
            setExploding(true);
        }

        image.setPosition(position.x, position.y);
        bounds.setPosition(position);
        detectionBounds.setPosition(position);

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
        position.set((float) rand.nextInt(Constants.VIRTUAL_WIDTH), (float) Constants.VIRTUAL_HEIGHT + 200);
        velocity.set(0, 0);
        primeTimer = 0f;
        setPrimed(false);
        setExploding(false);
    }

    public boolean isPrimed() {
        return primed;
    }

    public void setPrimed(boolean primed) {
        this.primed = primed;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }
}
