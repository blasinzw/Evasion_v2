package evasion.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import evasion.game.HUD.AmmunitionDisplay;
import evasion.game.objects.Asteroid;
import evasion.game.objects.Drop;
import evasion.game.objects.Laser;
import evasion.game.objects.Player;
import evasion.screens.Background;
import evasion.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameWorld {
    private Random random;

    //background
    private Background background;

    //global arrays
    private ArrayList<GameDrawable> drawables;
    private  ArrayList<Collidable> collidables;

    //Asteroids
    private ArrayList<Asteroid> asteroids;
    private Pool<Asteroid> asteroidPool;
    private int maxAsteroids;

    //Drops
    private ArrayList<Drop> drops;
    private Pool<Drop> dropPool;

    //HUD
    private AmmunitionDisplay ammunitionDisplay;

    //Lasers
    private ArrayList<Laser> lasers;
    private Pool<Laser> laserPool;
    private boolean isFiring;

    //Difficulty
    private Difficulty difficulty;
    private float globalSpawnModifier;
    private int globalSpeed;

    //spawn timer
    private SpawnTimer spawnTimer;

    private Player player;

    public GameWorld(final Evasion game) {
        drawables = new ArrayList<GameDrawable>();
        collidables = new ArrayList<Collidable>();

        //background
        background = new Background(game, "images/background/background.png", new Vector2(0,0), new Vector2(0, -20));
        drawables.add(background);

        //random
        random = new Random();

        //difficulty
        difficulty = Difficulty.NORMAL;
        globalSpawnModifier = difficulty.getGlobalSpawnModifier();
        globalSpeed = difficulty.getGlobalSpeed();

        //HUD
        ammunitionDisplay = new AmmunitionDisplay(game);
        drawables.add(ammunitionDisplay);

        //timer
        spawnTimer = new SpawnTimer(difficulty.getGlobalSpawnModifier());

        player = new Player(game);
        drawables.add(player);
        collidables.add(player);

        //lasers
        lasers = new ArrayList<Laser>();
        laserPool = new Pool<Laser>() {
            @Override
            protected Laser newObject() {
                return new Laser(game);
            }
        };
        isFiring = false;


        //Asteroids
        asteroids = new ArrayList<Asteroid>();
        final GameWorld world = this; //awful find more elegant solution
        asteroidPool = new Pool<Asteroid>() {
            @Override
            protected Asteroid newObject() {
                return new Asteroid(game, world, globalSpeed);
            }
        };
        maxAsteroids = difficulty.getMaxAsteroids();

        //Drops
        drops = new ArrayList<Drop>();
        dropPool = new Pool<Drop>() {
            @Override
            protected Drop newObject() {
                return new Drop(game);
            }
        };
    }

    public void update(float delta) {
        //spawn stuff
        spawn(delta);

        for (GameDrawable drawable: drawables) {
            drawable.update(delta);
        }

        collision();

        kill();
    }

    public void collision() {
        for (Collidable collidable: collidables) {
            for (Collidable otherCollidable: collidables) {
                if (!collidable.equals(otherCollidable)) {
                    collidable.collideWith(otherCollidable);
                }
            }
        }
    }

    public void spawn(float delta) {
        spawnTimer.update(delta);

        if (asteroids.size() < maxAsteroids && spawnTimer.canSpawnAsteroid()) {
                Asteroid item = asteroidPool.obtain();
                item.init();
                asteroids.add(item);
                drawables.add(item);
                collidables.add(item);
                spawnTimer.setAsteroidTimer(0);

        }

        spawnLaser();

        //fixes java.util.ConcurrentModificationException
        for (Drop d: drops) {
            if (!d.isAddedToCollidables()) {
                collidables.add(d);
                d.setAddedToCollidables(true);
            }
        }
    }

    public void spawnLaser() {
        if (lasers.size() < Constants.MAX_LASERS && spawnTimer.canSpawnLaser() && isFiring && ammunitionDisplay.hasAmmo()) {
            Laser item = laserPool.obtain();
            item.init(new Vector2(player.getPosition().cpy().add(Constants.LASER_A_X_OFFSET, Constants.LASER_A_AND_B_Y_OFFSET)), Constants.LASER_SPEED);
            lasers.add(item);
            drawables.add(item);
            collidables.add(item);

            item = laserPool.obtain();
            item.init(new Vector2(player.getPosition().cpy().add(Constants.LASER_B_X_OFFSET, Constants.LASER_A_AND_B_Y_OFFSET)), Constants.LASER_SPEED);
            lasers.add(item);
            drawables.add(item);
            collidables.add(item);

            spawnTimer.setLaserTimer(0);

            //removes one from ammoBars
            ammunitionDisplay.removeAmmo();
        }
    }

    public void kill() {
        for (int i=0; i<asteroids.size(); i++) {
            Asteroid item = asteroids.get(i);
            if (!item.isLiving()) {
                asteroids.remove(i);
                removeItemFromDrawables(item);
                removeItemFromCollidables(item);
                asteroidPool.free(item);
            }
        }

        for (int i=0; i<lasers.size(); i++) {
            Laser item = lasers.get(i);
            if (!item.isLiving()) {
                lasers.remove(i);
                removeItemFromDrawables(item);
                removeItemFromCollidables(item);
                laserPool.free(item);
            }
        }

        for (int i=0; i<drops.size(); i++) {
            Drop item = drops.get(i);
            if (!item.isLiving()) {
                drops.remove(i);
                removeItemFromDrawables(item);
                removeItemFromCollidables(item);
                dropPool.free(item);
            }
        }
    }

    public void removeItemFromCollidables(GameDrawable item) {
        for (int i=0; i<collidables.size(); i++) {
            if (collidables.get(i).equals(item)) {
                collidables.remove(i);
            }
        }
    }

    public void removeItemFromDrawables(GameDrawable item) {
        for (int i=0; i<drawables.size(); i++) {
            if (drawables.get(i).equals(item)) {
                drawables.remove(i);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<GameDrawable> getDrawables() {
        return drawables;
    }

    public void sortDrawList() {
        Collections.sort(drawables);
    }

    public void setFiring(boolean isFiring) {
        this.isFiring = isFiring;
    }

    public void addDrop(Vector2 position) {
        DropType dropType = DropType.getRandomType();

        int size = 1;

        //calculates the amount of money dropped based of threshold weights
        if (dropType == DropType.MONEY) {
            float prob = random.nextFloat();

            if (prob < Constants.LOW__YIELD_PERCENTAGE) {
                size = random.nextInt(Constants.THRESHOLD_LOW_YIELD);
            }else if (prob < Constants.LOW__YIELD_PERCENTAGE + Constants.MEDIUM_YIELD_PERCENTAGE) {
                size = random.nextInt(Constants.THRESHOLD_MEDIUM_YIELD - Constants.THRESHOLD_LOW_YIELD) + Constants.THRESHOLD_LOW_YIELD;
            }else {
                size = random.nextInt(Constants.THRESHOLD_HIGH_YIELD - Constants.THRESHOLD_MEDIUM_YIELD) + Constants.THRESHOLD_MEDIUM_YIELD;
            }
        }

        for (int i=0; i<size; i++) {
            position = new Vector2(position.x + random.nextInt(50)-25, position.y + random.nextInt(50)-25);
            Drop item = dropPool.obtain();
            item.init(position, globalSpeed, dropType);
            drops.add(item);
            drawables.add(item);
        }
    }
}
