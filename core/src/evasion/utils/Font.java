package evasion.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import evasion.game.Evasion;

/**
 * Created by Zander on 4/11/2015.
 */
public class Font extends GameDrawable {

    private BitmapFont font;
    private String data;
    private DrawLevel drawLevel;
    private Vector2 position;
    private float scale;
    private Color color;

    public Font(final Evasion game, Vector2 position, String data, float scale, Color color, DrawLevel drawLevel) {
        this.data = data;
        this.drawLevel = drawLevel;
        this.position = position;
        this.scale = scale;
        this.color = color;

        font = new BitmapFont(Gdx.files.internal("images/ui/font.fnt"), new TextureRegion(game.manager.get("images/ui/font.png", Texture.class)));
    }

    @Override
    public DrawLevel getDrawLevel() {
        return drawLevel;
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, data, position.x, position.y);
    }

    @Override
    public void update(float delta) {
        font.setScale(scale);
        font.setColor(color);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
