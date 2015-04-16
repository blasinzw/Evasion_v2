package evasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.game.GameRenderer;
import evasion.game.GameWorld;
import evasion.input.InputHandler;

public class GameScreen implements Screen {
    //variables
    private final Evasion game;

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    //camera and rendering
    private OrthographicCamera camera;
    private Viewport viewport;

    public GameScreen(final Evasion game) {
        this.game = game;

        camera = game.getCamera();
        viewport = game.getViewport();
    }

    @Override
    public void show() {
        gameWorld = new GameWorld(game);
        gameRenderer = new GameRenderer(game, gameWorld);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(new InputHandler(gameWorld));
    }

    @Override
    public void render(float delta) {
        camera.update();
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        gameWorld.update(delta);
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setCursorCatched(false);
    }

    @Override
    public void dispose() {

    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }
}
