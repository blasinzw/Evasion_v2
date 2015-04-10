package evasion.utils;

public enum Difficulty {
    EASY(-400, 1f, 2, 5, .3f),
    NORMAL(-600, .75f, 3, 10, .4f),
    HARD(-800, .5f, 4, 12, .5f);

    private int globalSpeed;
    private float globalSpawnModifier;
    private int maxMines, maxAsteroids;
    private float mineExplosionDuration;

    Difficulty(int globalSpeed, float globalSpawnModifier, int maxMines, int maxAsteroids, float mineExplosionDuration) {
        this.globalSpeed = globalSpeed;
        this.globalSpawnModifier = globalSpawnModifier;
        this.maxMines = maxMines;
        this.maxAsteroids = maxAsteroids;
        this.mineExplosionDuration = mineExplosionDuration;
    }

    public float getMineExplosionDuration() {
        return mineExplosionDuration;
    }

    public int getMaxAsteroids() {
        return maxAsteroids;
    }

    public int getMaxMines() {
        return maxMines;
    }

    public int getGlobalSpeed() {
        return globalSpeed;
    }

    public float getGlobalSpawnModifier() {
        return globalSpawnModifier;
    }
}
