package evasion.game;

/**
 * A file with all the constants for the game Evasion.
 */
public class Constants {
    //magic numbers go here
    public static final String VERSION = "2.3.1";

    public static final String NAME = "EVASION -v" + VERSION;

    public static final int VIRTUAL_WIDTH = 480; //800;
    public static final int VIRTUAL_HEIGHT = 800; //480;

    //for HUD
    public static final int ENERGY_METER_X = VIRTUAL_WIDTH - 240;//560;
    public static final int ENERGY_METER_Y = VIRTUAL_HEIGHT - 50;//430;
    public static final int MONEY_DISPLAY_X = 50;
    public static final int MONEY_DISPLAY_Y = VIRTUAL_HEIGHT - 25;
    public static final int MONEY_ICON_X = MONEY_DISPLAY_X - 20;
    public static final int MONEY_ICON_Y = MONEY_DISPLAY_Y - 15;

    //for laser
    public static final float LASER_RATE_OF_FIRE = 0.2f;
    public static final int MAX_LASERS = 10;
    public static final float LASER_A_X_OFFSET = 17.5f;
    public static final float LASER_A_AND_B_Y_OFFSET = 30.0f;
    public static final float LASER_B_X_OFFSET = 42.5f;
    public static final int LASER_SPEED = 1750;
    public static final int LASER_RESIZE = 3;
    public static final int MAX_ROUNDS = 15;

    //for player
    public static final float PLAYER_SPEED = 600.0f;

    //drops
    public static final float DROP_PERCENTAGE = 0.70f;
    public static final float MONEY_SCALE = 1.125f;
    public static final int THRESHOLD_LOW_YIELD = 5;
    public static final int THRESHOLD_MEDIUM_YIELD = 12;
    public static final int THRESHOLD_HIGH_YIELD = 20;
    public static final float LOW__YIELD_PERCENTAGE = 0.60f;
    public static final float MEDIUM_YIELD_PERCENTAGE = 0.30f;
    public static final float HIGH_YIELD_PERCENTAGE = 0.10f;

}
