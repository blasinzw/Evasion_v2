package evasion.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import evasion.game.Constants;
import evasion.game.GameWorld;
import evasion.utils.SaveData;

/**
 * Class with static methods to handle accelerometer input as it can only be accessed via polling and not event handling.
 */
public class AndroidInputHandler {

    public static void checkForInput(GameWorld world) {
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            float roll = Gdx.input.getRoll();
            float sensitivity = SaveData.getSensitivity();
            float minAngle = Constants.MIN_ANGLE * (1 - sensitivity);
            float maxAngle = Constants.MAX_ANGLE * (1 - sensitivity);

            if (roll > minAngle) world.getPlayer().setMovingRight(true);
            else if (roll < -minAngle) world.getPlayer().setMovingLeft(true);
            else {
                world.getPlayer().setMovingLeft(false);
                world.getPlayer().setMovingRight(false);
            }

            world.getPlayer().setSpeed(Constants.PLAYER_SPEED * (Math.abs(roll) > maxAngle ? 1f : Math.abs(roll)/maxAngle));
        }
    }
}
