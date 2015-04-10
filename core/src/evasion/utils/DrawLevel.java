package evasion.utils;

/**
 * Different levels for draw order
 */
public enum DrawLevel {

    MENU(3), HUD(2), OBJECTS(1), BACKGROUND(0);

    private int level;

    public int getLevel() {
        return level;
    }

    DrawLevel (int level) {
        this.level = level;
    }

}
