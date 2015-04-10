package evasion.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import evasion.utils.GameDrawable;

public class GameRenderer {

    private final GameWorld world;

    private final Evasion game;

    public GameRenderer(final Evasion game, final GameWorld world) {
        this.world = world;
        this.game = game;
    }

    public void render() {
        // clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //draw all things
        world.sortDrawList();
        for (GameDrawable drawable: world.getDrawables()) {
            drawable.draw(game.batch);
        }

        game.batch.end();

    }
}
