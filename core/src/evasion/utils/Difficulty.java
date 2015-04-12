package evasion.utils;

public enum Difficulty {
    EASY(-400, 1f, 1, 5, .3f, .5f, 300),
    NORMAL(-600, .75f, 1, 10, .4f, .25f, 400),
    HARD(-800, .5f, 3, 12, .5f, .125f, 500);

    private final int globalSpeed;
    private final float globalSpawnModifier;
    private final int maxMines, maxAsteroids;
    private final float mineExplosionDuration, minePrimeDuration;
    private final int mineDetectionRadius;


    Difficulty(int globalSpeed, float globalSpawnModifier, int maxMines, int maxAsteroids, float mineExplosionDuration, float minePrimeDuration, int mineDetectionRadius) {
        this.globalSpeed = globalSpeed;
        this.globalSpawnModifier = globalSpawnModifier;
        this.maxMines = maxMines;
        this.maxAsteroids = maxAsteroids;
        this.mineExplosionDuration = mineExplosionDuration;
        this.minePrimeDuration = minePrimeDuration;
        this.mineDetectionRadius = mineDetectionRadius;
    }

    public float getMineExplosionDuration() {
        return mineExplosionDuration;
    }

    public float getMinePrimeDuration() {return minePrimeDuration;}

    public int getMineDetectionRadius() {return mineDetectionRadius;}

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
