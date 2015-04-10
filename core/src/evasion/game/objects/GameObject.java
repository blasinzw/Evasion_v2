package evasion.game.objects;

import com.badlogic.gdx.math.Vector2;
import evasion.game.Evasion;
import evasion.utils.Collidable;
import evasion.utils.DrawLevel;
import evasion.utils.GameDrawable;

/**
 * An abstract class for game classes.
 */
public abstract class GameObject extends GameDrawable implements Collidable{

    protected Vector2 position;
    protected Vector2 velocity;
    protected DrawLevel drawLevel;

    private boolean living;
    protected final Evasion game;

    public GameObject(Vector2 position, Vector2 velocity, DrawLevel drawLevel, final Evasion game) {
        this.position = position;
        this.velocity = velocity;
        this.drawLevel = drawLevel;
        this.game = game;
        living = true;
    }

    public GameObject(DrawLevel drawLevel, final Evasion game) {
        this.drawLevel = drawLevel;
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        this.game = game;
        living = true;
    }

    @Override
    public boolean isLiving() {
        return living;
    }

    @Override
    public void setLiving(boolean living) {
        this.living = living;
    }
}
