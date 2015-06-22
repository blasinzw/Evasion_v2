package evasion.input;

import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.InputProcessor;
import evasion.game.GameWorld;

public class InputHandler implements InputProcessor {

    private final GameWorld world;

    public InputHandler (final GameWorld world) {
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                world.getPlayer().setMovingLeft(true);
                break;
            case Keys.RIGHT:
                world.getPlayer().setMovingRight(true);
                break;
            case Keys.A:
                world.getPlayer().setMovingLeft(true);
                break;
            case Keys.D:
                world.getPlayer().setMovingRight(true);
                break;
            case Keys.SPACE:
                world.setFiring(true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                world.getPlayer().setMovingLeft(false);
                break;
            case Keys.RIGHT:
                world.getPlayer().setMovingRight(false);
                break;
            case Keys.A:
                world.getPlayer().setMovingLeft(false);
                break;
            case Keys.D:
                world.getPlayer().setMovingRight(false);
                break;
            case Keys.SPACE:
                world.setFiring(false);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        world.setFiring(true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        world.setFiring(false);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
