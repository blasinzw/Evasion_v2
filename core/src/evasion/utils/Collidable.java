package evasion.utils;

import com.badlogic.gdx.math.Rectangle;

/**
 * A Collidable knows how to collide with other collideable objects.
 */
public interface Collidable {

    Rectangle getBounds();

    CollisionType getCollisionType();

    void setLiving(boolean living);

    boolean isLiving();

    void collideWith(Collidable collidable);

    void collide();

}
