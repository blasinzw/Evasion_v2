package evasion.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.utils.Difficulty;
import evasion.utils.SaveData;
import evasion.utils.TextureCropper;

public class HighScoreScreen implements Screen{

    private Evasion game;
    private SpriteBatch batch;
    private Viewport viewport;
    private Difficulty difficulty;

   //Textures for sliders
    private TextureAtlas textureAtlas;
    private TextureRegion empty;
    private TextureRegion full;
    private Label timeLabel;
    private Label moneyLabel;
    private Label scoreLabel;
    private LabelStyle labelStyle;

    //stats
    private float time;
    private float bestTime;
    private float mostMoney;
    private float money;
    private float score;
    private float oldScore;

    private BitmapFont font;
    private Stage stage;
    private Table table;
    private TextButtonStyle style;
    private TextButton exit;

    private OrthographicCamera camera;

    public HighScoreScreen (Evasion game){
        this.game = game;
    }

    private void init(Evasion game) {
        //Initializer code for stuff
        setGame(game);
        camera = game.getCamera();
        //viewport
        viewport = game.getViewport();

        batch = game.batch;
        font = new BitmapFont(Gdx.files.internal("images/ui/font.fnt"), new TextureRegion(game.manager.get("images/ui/font.png", Texture.class)));
        font.setColor(255, 0, 0, 1);

        //loads stat bars
        textureAtlas = game.manager.get("images/ui/ui.pack", TextureAtlas.class);
        empty = textureAtlas.findRegion("progressBarBackground");
        full = textureAtlas.findRegion("progressBarForeground");

        time = game.getGameScreen().getGameWorld().getTime();
        money= game.getGameScreen().getGameWorld().getMoneySupply();

        //formula for score
        score = Constants.TIME_MULTIPLIER*time + Constants.MONEY_MULTIPLIER*money;

        difficulty = SaveData.getDifficulty();

        loadSave();
        writeSave();

        //Button init
        stage = new Stage();
        style = game.getMainMenuScreen().getStyle();
        exit = new TextButton("RETURN", style);
        table = new Table();
        labelStyle = new LabelStyle();
        labelStyle.font = font;

        timeLabel = new Label("Seconds Alive: " +(int) time, labelStyle);
        scoreLabel = new Label("Your Score: " + (int)score, labelStyle);
        moneyLabel = new Label("Coins Collected: " + (int)money, labelStyle);

        exit = new TextButton("RETURN", style);

        table.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        table.add(timeLabel).padRight(350).left().padBottom(50);
        table.row();
        table.add(moneyLabel).padRight(350).left().padBottom(50);
        table.row();
        table.add(scoreLabel).padRight(350).left().padBottom(100);
        table.row();

        table.add(exit);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    private void setGame(Evasion game) {
        this.game = game;
    }

    private void loadSave() {
        oldScore = SaveData.getScore();
        bestTime = SaveData.getTime();
        mostMoney = SaveData.getMoney();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        generalUpdates();

        //drawing phase
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        //draw bars
        batch.draw(empty, table.getCell(timeLabel).getActorX() + table.getCell(timeLabel).getActorWidth() + 25, table.getCell(timeLabel).getActorY());
        batch.draw(empty, table.getCell(moneyLabel).getActorX() + table.getCell(moneyLabel).getActorWidth() + 25, table.getCell(moneyLabel).getActorY());
        batch.draw(empty, table.getCell(scoreLabel).getActorX() + table.getCell(scoreLabel).getActorWidth() + 25, table.getCell(scoreLabel).getActorY());


        if (time < bestTime) {
//            batch.draw(full, table.getCell(timeLabel).getActorX() + table.getCell(timeLabel).getActorWidth() + 25, table.getCell(timeLabel).getActorY(), 0, 0, (int) ((time/bestTime) * 300), 25);
            batch.draw(TextureCropper.cropTexture(full, (int) (time/bestTime * 300), full.getRegionHeight()), table.getCell(timeLabel).getActorX() + table.getCell(timeLabel).getActorWidth() + 25, table.getCell(timeLabel).getActorY());
        }else{
            batch.draw(full, table.getCell(timeLabel).getActorX() + table.getCell(timeLabel).getActorWidth() + 25, table.getCell(timeLabel).getActorY());
        }
        if (money < mostMoney) {
//            batch.draw(full, table.getCell(moneyLabel).getActorX() + table.getCell(moneyLabel).getActorWidth() + 25, table.getCell(moneyLabel).getActorY(), 0, 0, (int) ((money/mostMoney) * 300), 25);
            batch.draw(TextureCropper.cropTexture(full, (int) (money/mostMoney * 300), full.getRegionHeight()), table.getCell(moneyLabel).getActorX() + table.getCell(moneyLabel).getActorWidth() + 25, table.getCell(moneyLabel).getActorY());
        }else{
            batch.draw(full, table.getCell(moneyLabel).getActorX() + table.getCell(moneyLabel).getActorWidth() + 25, table.getCell(moneyLabel).getActorY());
        }
        if (score < oldScore) {
//            batch.draw(full, table.getCell(scoreLabel).getActorX() + table.getCell(scoreLabel).getActorWidth() + 25, table.getCell(scoreLabel).getActorY(), 0, 0, (int) ((score/oldScore) * 300), 25);
            batch.draw(TextureCropper.cropTexture(full, (int) (score/oldScore * 300), full.getRegionHeight()), table.getCell(scoreLabel).getActorX() + table.getCell(scoreLabel).getActorWidth() + 25, table.getCell(scoreLabel).getActorY());
        }else{
            batch.draw(full, table.getCell(scoreLabel).getActorX() + table.getCell(scoreLabel).getActorWidth() + 25, table.getCell(scoreLabel).getActorY());

        }

        batch.end();

        stage.draw();

    }

    private void generalUpdates() {
        if (exit.isPressed()) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private void writeSave() {
        if (score > oldScore) {
            SaveData.setScore(score);
        }
        if (time > bestTime) {
            SaveData.setTime(time);
        }
        if (money > mostMoney) {
            SaveData.setMoney(money);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.setViewport(viewport);

    }

    @Override
    public void show() {
        init(game);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

}
