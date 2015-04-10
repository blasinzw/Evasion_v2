package evasion.utils;

import evasion.game.Constants;

import java.util.Random;

/**
 * Created by Zander on 3/6/2015.
 */
public class SpawnTimer {

    private float asteroidTime = 0.0f;
    private float asteroidSpawnDelay = 0.0f;

    private float laserTime = 0.0f;
    private float laserSpawnDelay = Constants.LASER_RATE_OF_FIRE;

    private float mineTime = 0.0f;
    private float mineSpawnDelay = 0.0f;

    private float globalSpawnModifier;

    private boolean spawnAsteroid, spawnLaser;

    private Random random = new Random();

    public SpawnTimer(float globalSpawnModifier) {
        this.globalSpawnModifier = globalSpawnModifier;

        spawnAsteroid = spawnLaser = false;
    }

    public void update(float delta) {
        spawnAsteroid = spawnLaser = false;

        asteroidTime += delta;
        laserTime += delta;
        mineTime += delta;

        if (asteroidTime > asteroidSpawnDelay) {
            spawnAsteroid = true;
            asteroidSpawnDelay = random.nextFloat() * globalSpawnModifier;
        }

        if (laserTime > laserSpawnDelay) {
            spawnLaser = true;
        }
    }


    public boolean canSpawnAsteroid() {
        return spawnAsteroid;
    }

    public boolean canSpawnLaser() {
        return spawnLaser;
    }

    public void setAsteroidTimer(float asteroidTime) {
        this.asteroidTime = asteroidTime;
    }

    public void setLaserTimer(float laserTime) {
        this.laserTime = laserTime;
    }
}
