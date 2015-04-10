package evasion.screens;

import com.badlogic.gdx.Screen;
import evasion.game.Evasion;

public class MainMenuScreen implements Screen {

    //variables
    private final Evasion game;

    public MainMenuScreen(final Evasion game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (game.manager.update()) game.setScreen(new GameScreen(game));//tmp
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
