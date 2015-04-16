package evasion.utils;

import com.badlogic.gdx.audio.Sound;
import evasion.game.Constants;
import evasion.game.Evasion;

/**
 * A helper class that plays the sound effects for the game.
 */
public class SoundEffect {

    public static void playLaser(Evasion game) {
        game.manager.get("sounds/laser.ogg", Sound.class).play(Constants.LASER_VOLUME);
    }

    public static void playMinePrime(Evasion game) {
        game.manager.get("sounds/minePrime.ogg", Sound.class).play(Constants.MINE_VOLUME);
    }

    public static void playExplosion(Evasion game) {
        game.manager.get("sounds/explosion.ogg", Sound.class).play(Constants.EXPLOSION_VOLUME);
    }
}
