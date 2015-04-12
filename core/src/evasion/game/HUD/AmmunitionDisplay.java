package evasion.game.HUD;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.DrawLevel;
import evasion.utils.GameDrawable;

import java.util.ArrayList;


/**
 * Displays the amount of ammunition left.
 */
public class AmmunitionDisplay extends GameDrawable {

    //constants
    private final int Y_POS = Constants.ENERGY_METER_Y;
    private final int X_0 = Constants.ENERGY_METER_X;
    private final int DELTA_X = 10;

    private final float RESPAWN_TIME = 1.5f; //in seconds

    private float internalTime = 0.0f;

    private class AmmoBar extends GameDrawable implements Pool.Poolable {

        private Vector2 position;
        private Sprite image;

        public AmmoBar(Evasion game) {
            position = new Vector2();
            image = new Sprite(game.manager.get("images/hud/laserBar.png", Texture.class));
        }

        /**
         * Createa new ammobar variables
         * @param position
         */
        public void init(Vector2 position) {
            this.position = position;
            image.setPosition(position.x, position.y);
        }

        @Override
        public DrawLevel getDrawLevel() {
            return DrawLevel.HUD;
        }

        @Override
        public void draw(Batch batch) {
            image.draw(batch);
        }

        @Override
        public void update(float delta) {

        }

        /**
         * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
         */
        @Override
        public void reset() {
            position.set(-Constants.VIRTUAL_HEIGHT, -Constants.VIRTUAL_WIDTH); //sets object off screen.
        }
    }

    public Pool<AmmoBar> ammoBarPool;
    private ArrayList<AmmoBar> ammoBars;

    public AmmunitionDisplay(final Evasion game) {
        ammoBarPool = new Pool<AmmoBar>() {
            @Override
            protected AmmoBar newObject() {
                return new AmmoBar(game);
            }
        };
        ammoBars = new ArrayList<AmmoBar>();

        AmmoBar item = ammoBarPool.obtain();
        item.init(new Vector2(X_0, Y_POS));
        ammoBars.add(item);
        for (int i=1; i<Constants.MAX_ROUNDS; i++) {
            item = ammoBarPool.obtain();
            item.init(new Vector2(X_0 + (i * DELTA_X), Y_POS));
            ammoBars.add(item);
        }
    }

    public void update(float delta) {
        internalTime += delta;
        if (internalTime  > RESPAWN_TIME) {
            if (ammoBars.size() < Constants.MAX_ROUNDS && ammoBars.size() > 0) {
                AmmoBar item = ammoBarPool.obtain();
                item.init(new Vector2(ammoBars.get(ammoBars.size()-1).position.x + DELTA_X, Y_POS));
                ammoBars.add(item);
            }else if (ammoBars.size() < Constants.MAX_ROUNDS) {
                AmmoBar item = ammoBarPool.obtain();
                item.init(new Vector2(X_0 + DELTA_X, Y_POS));
                ammoBars.add(item);
            }
            internalTime = 0.0f;
        }
    }

    public boolean hasAmmo() {
        if (ammoBars.size() > 0) return true;
        else return false;
    }

    /**
     * Removes one bar of ammo.
     */
    public void removeAmmo() {
        AmmoBar item = ammoBars.get(ammoBars.size()-1);
        ammoBars.remove(ammoBars.size()-1);
        ammoBarPool.free(item);
    }

    @Override
    public DrawLevel getDrawLevel() {
        return DrawLevel.HUD;
    }

    @Override
    public void draw(Batch batch) {
        for (AmmoBar a: ammoBars) a.draw(batch);
    }
}