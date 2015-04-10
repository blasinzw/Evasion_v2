package evasion.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.Collidable;
import evasion.utils.CollisionType;
import evasion.utils.DrawLevel;

/**
 * Created by Zander on 1/30/2015.
 */
public class Laser extends GameObject implements Pool.Poolable{

    private Sprite image;
    private Rectangle bounds;


    public Laser(Vector2 position, int speed, final Evasion game) {
        super (position, new Vector2(0, speed), DrawLevel.OBJECTS, game);

        image = new Sprite(game.manager.get("images/player/laser.png", Texture.class));
        image.setScale(Constants.LASER_RESIZE);
        bounds = image.getBoundingRectangle();
    }

    public Laser(final Evasion game) {
        super(DrawLevel.OBJECTS, game);

        image = new Sprite(game.manager.get("images/player/laser.png", Texture.class));
        image.setScale(Constants.LASER_RESIZE);
        bounds = image.getBoundingRectangle();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void collideWith(Collidable collidable) {
        if (collidable.getBounds().overlaps(bounds)) {
            switch(collidable.getCollisionType()) {
                case PLAYER:
                    break;
                case ASTEROID:
                    collidable.collide();
                    setLiving(false);
                    break;
                case MINE:
                    collidable.collide();
                    setLiving(false);
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
    public CollisionType getCollisionType() {
        return CollisionType.LASER;
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
        position.add(velocity.scl(delta));
        velocity.scl(1/delta);

        image.setPosition(position.x, position.y);
        bounds.setPosition(position);

        checkIfHitWall();
    }

    public void checkIfHitWall() {
        if (position.y > Constants.VIRTUAL_HEIGHT) {
            setLiving(false);
        }
    }

    /**
     * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
     */
    @Override
    public void reset() {
        //put off screen somewhere
        position.set(-100,-100);
        velocity.set(0,0);
        setLiving(true);

    }

    public void init(Vector2 position, int speed) {
        this.position = position;
        velocity.set(0,speed);
    }
}
