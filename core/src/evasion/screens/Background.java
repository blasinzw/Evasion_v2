package evasion.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.DrawLevel;
import evasion.utils.GameDrawable;

/**
 * Created by Zander on 1/30/2015.
 */
public class Background extends GameDrawable {
    private DrawLevel drawLevel = DrawLevel.BACKGROUND;

    private Vector2 position, velocity;

    private Sprite image;

    public Background(Evasion game, String file, Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
        load(game, file);
    }

    private void load(Evasion game, String file) {
        image = new Sprite(game.manager.get(file, Texture.class));
    }

    @Override
    public DrawLevel getDrawLevel() {
        return this.drawLevel;
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

        wallCollide();
    }

    public void wallCollide() {
        if (position.y + image.getHeight() < Constants.VIRTUAL_HEIGHT) {
            position.set(position.x, 0);
        }
    }
}
