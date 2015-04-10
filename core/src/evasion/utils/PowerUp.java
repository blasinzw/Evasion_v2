package evasion.utils;

/**
 * Simple enum for containing data on different power ups.
 */
public enum PowerUp {
    MAGNET(7, true), INVINCIBLE(5, true),
    RAPID_FIRE(4, true), NONE(0, false);

    private float duration;
    private boolean activated;

    PowerUp(int duration, boolean activated) {
        this.duration = duration;
        this.activated = activated;
    }

    public float getDuration() {
        return duration;
    }

    public boolean isActivated() {
        return activated;
    }

}
