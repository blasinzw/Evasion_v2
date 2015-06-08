package evasion.game.objects;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.game.GameWorld;
import evasion.utils.Collidable;
import evasion.utils.CollisionType;
import evasion.utils.DrawLevel;
import evasion.utils.SoundEffect;

import java.util.Random;

/**
 * The Asteroid class
 */
public class Asteroid extends GameObject implements Pool.Poolable{

    private int speed;

    private boolean exploding;

    //images
    private int frame;
    private TextureAtlas textureAtlas;

    //explosion images
    private TextureAtlas explosionTextureAtlas;
    private TextureRegion[] explosionTextures;
    private Animation explosionAnimation;
    private float stateTime;

    //bounds
    private Sprite image;
    private Rectangle bounds;

    //randomization code
    private Random rand = new Random();
    private float resize, rotation;

    private GameWorld world;

    public Asteroid(final Evasion game, final GameWorld world, int speed) {
        super(DrawLevel.OBJECTS, game);

        position = new Vector2((float)rand.nextInt(Constants.VIRTUAL_WIDTH), (float) Constants.VIRTUAL_HEIGHT + 200);
        velocity = new Vector2(0, speed);
        this.speed = speed;

        frame = rand.nextInt(3);
        rotation = (float)rand.nextInt(360);
        resize = (float)rand.nextInt(2)+1;

        load();
        loadExplosions();

        this.world = world;

        bounds = image.getBoundingRectangle();

        exploding = false;
        stateTime = 0;
    }

    public void init() {
        //re-initializes all variables
        frame = rand.nextInt(3);
        rotation = (float)rand.nextInt(360);
        resize = (float)rand.nextInt(2)+1;
        load();
        bounds = image.getBoundingRectangle();
        velocity.set(0, speed);
        setLiving(true);
        exploding = false;
        stateTime = 0;
        explosionAnimation = new Animation(0.03f, explosionTextures);
    }

    private void load() {
        textureAtlas = game.manager.get("images/asteroid/asteroids.pack", TextureAtlas.class);

        switch (frame) {
            case 0:
                image = textureAtlas.createSprite("asteroid1");
                break;
            case 1:
                image = textureAtlas.createSprite("asteroid2");
                break;
            case 2:
                image = textureAtlas.createSprite("asteroid3");
                break;
            case 3:
                image = textureAtlas.createSprite("asteroid4");
                break;
            default:
                System.out.println("broken");
                break;
        }

        image.setScale(resize, resize);
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        image.rotate(rotation);

    }

    private void loadExplosions() {
        explosionTextureAtlas = game.manager.get("images/asteroid/asteroidExplosions.pack", TextureAtlas.class);

        explosionTextures = new TextureRegion[explosionTextureAtlas.findRegions("explosion").size];

        for (int i=0; i<explosionTextureAtlas.findRegions("explosion").size; i++)  {
            explosionTextures[i] = explosionTextureAtlas.findRegion("explosion", i+1);
        }

        explosionAnimation = new Animation(0.03f, explosionTextures);

    }

    @Override
    public void update(float delta) {
        //  velocity.scl(delta) accounts for time.
        position.add(velocity.scl(delta));
        velocity.scl(1 / delta); //unscales velocity vector

        if (exploding) {
            stateTime += delta;
            image = new Sprite(explosionAnimation.getKeyFrame(stateTime, false));
            bounds = image.getBoundingRectangle();
            image.setPosition(position.x - bounds.getWidth()/2, position.y - bounds.getHeight()/2);
            bounds.setPosition(position.x - bounds.getWidth()/2, position.y - bounds.getHeight()/2);

            if (explosionAnimation.isAnimationFinished(stateTime)) setLiving(false);
        }else {
            image.setPosition(position.x, position.y);
            bounds.setPosition(position);
        }

        checkIfInScreen();

    }

    private void checkIfInScreen() {
        if (bounds.getY() < -bounds.getHeight()) {
            setLiving(false);
        }
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void collideWith(Collidable collidable) {
        if ((collidable.getBounds().overlaps(bounds)) && collidable.isLiving() && !exploding) {
            switch (collidable.getCollisionType()) {
                case PLAYER:
                    collidable.collide();
                    collide();
                    SoundEffect.playExplosion(game);
                    break;
                case ASTEROID:
                    collidable.setLiving(false);
                    break;
                case MINE:
                    setExploding(true);
                    SoundEffect.playExplosion(game);
                    break;
                case DROP:
                    break;
                case LASER:
                    collidable.collide();
                    collide();
                    SoundEffect.playExplosion(game);
                    setExploding(true);
                    break;
            }
        }
    }

    @Override
    public void collide() {
        if (rand.nextFloat() < Constants.DROP_PERCENTAGE && !exploding) {
            world.addDrop(position);
        }
        setExploding(true);
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.ASTEROID;
    }

    @Override
    public DrawLevel getDrawLevel() {
        return drawLevel;
    }

    @Override
    public void draw(Batch batch) {
        image.draw(batch);
    }

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {
        position.set((float) rand.nextInt(Constants.VIRTUAL_WIDTH), (float) Constants.VIRTUAL_HEIGHT + 200);
        velocity.set(0,0);
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }
}
