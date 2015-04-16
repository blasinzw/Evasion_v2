package evasion.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.Collidable;
import evasion.utils.CollisionType;
import evasion.utils.DrawLevel;

public class Player extends GameObject{

    private float speed = Constants.PLAYER_SPEED;

    private boolean movingLeft, movingRight;

    private float stateTime;

    //image and sprite
    private TextureAtlas atlas;
    private TextureRegion[] playerForwardImages;
    private TextureRegion[] playerRightImages;
    private TextureRegion[] playerLeftImages;
    private TextureRegion image;
    private Rectangle bounds;
    private Animation animation;

    public Player(final Evasion game) {
        super(new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y), new Vector2(0,0), DrawLevel.OBJECTS, game);

        movingLeft = movingRight = false;

        stateTime = 0f;

        load();

        bounds = new Rectangle(position.x, position.y, image.getRegionWidth()*2, image.getRegionHeight()*2);
    }

    private void load() {
        atlas = game.manager.get("images/player/player.pack");

        playerForwardImages = new TextureRegion[3];
        playerLeftImages = new TextureRegion[3];
        playerRightImages = new TextureRegion[3];

        for (int i=0; i<3; i++) {
            playerForwardImages[i] = atlas.findRegion("player_forward", i + 1);
            playerLeftImages[i] = atlas.findRegion("player_turn_left", i+1);
            playerRightImages[i] = atlas.findRegion("player_turn_right", i+1);
        }
        animation = new Animation(0.2F, playerForwardImages);
        image = animation.getKeyFrame(0f, true);

    }

    private void checkBounds(float delta) {
        if (position.x < 0 || position.x + bounds.getWidth() > Constants.VIRTUAL_WIDTH) {
            setMovingLeft(false);
            setMovingRight(false);
            move(delta);
        }
    }

    @Override
    public void update(float delta) {
        move(delta);

        checkBounds(delta);

        stateTime += delta;
        image = animation.getKeyFrame(stateTime, true);
    }

    /**
     * Moves player and updates animation sequence
     * @param delta
     */
    private void move(float delta) {
        if (movingLeft) {
            velocity.x = -speed;
            animation = new Animation(0.2f, playerLeftImages);
        }else if (movingRight) {
            velocity.x = speed;
            animation = new Animation(0.2f, playerRightImages);
        }else {
            velocity.x = 0;
            animation = new Animation(0.2f, playerForwardImages);
        }

        //  velocity.scl(delta) accounts for time.
        position.add(velocity.scl(delta));
        velocity.scl(1/delta); //unscales velocity vector
        bounds.setPosition(position);
    }

    /**
     * Moves player
     * @param delta
     */
    private void moveRaw(float delta) {
        position.add(velocity.scl(delta));
        velocity.scl(1/delta); //unscales velocity vector
        bounds.setPosition(position);
    }


    @Override
    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y, image.getRegionWidth()*2, image.getRegionHeight()*2);
    }

    @Override
    public DrawLevel getDrawLevel() {
        return drawLevel;
    }

    @Override
    public void collideWith(Collidable collidable) {
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.PLAYER;
    }

    @Override
    public void collide() {
        setLiving(false);
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }


    public Vector2 getPosition() {
        return position;
    }


    public void setMovingLeft(boolean movingLeft) {
        if (movingRight && movingLeft) movingRight = false;
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        if (movingLeft && movingRight) movingLeft = false;
        this.movingRight = movingRight;
    }
}
