package evasion.utils;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A GameDrawable knows how to draw itself by default.
 */
public abstract class GameDrawable implements Comparable<GameDrawable>{

    public abstract DrawLevel getDrawLevel();

    public abstract void draw(Batch batch);

    @Override
    public int compareTo(GameDrawable gameDrawable) {
        return this.getDrawLevel().getLevel() - gameDrawable.getDrawLevel().getLevel();
    }

    public abstract void update(float delta);
}
