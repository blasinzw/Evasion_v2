package evasion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;
import evasion.assets.Assets;
import evasion.game.Constants;
import evasion.game.Evasion;
import evasion.game.objects.Player;
import evasion.utils.Difficulty;
import evasion.utils.SaveData;

public class MainMenuScreen implements Screen{
    private Evasion game;
    private Assets assets;
    private BitmapFont font;
    private float progress;

    //stateTime
    private float stateTime;

    private Table table; //table for buttons etc.
    private Table optionsTable;

    //code for background
    public Background background;

    //code for player
    public Player player;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    //skin and buttons
    private Skin skin;
    private float eventDelay;
    private Stage stage;
    private BitmapFont buttonFont;
    private TextButtonStyle style;
    private TextButton play;
    private TextButton exit;
    private TextButton optionMenu;

    //texture atlas
    private TextureAtlas textureAtlas;

    //slider
    private Slider difficultySlider;
    private SliderStyle sliderStyle;
    private Label difficultyLabel;
    private LabelStyle labelStyle;

    //android only
    private Slider sensitivitySlider; //sensitivity for tilting.
    private Label sensitivityLabel;
    private float sensitivity;

    private boolean createAssetDependents;
    private boolean isOptionMenu;
    private Difficulty difficulty;

    public MainMenuScreen (Evasion game) {
        init(game);
    }

    private void init(Evasion game) {
        //Initializer code for stuff
        setGame(game);
        camera = game.getCamera();
        viewport = game.getViewport();
        batch = game.batch;
        assets = new Assets(game);
        font = new BitmapFont();

        eventDelay = 0f;

        table = new Table();
        optionsTable = new Table();
        table.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        optionsTable.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        stateTime = 0;

        difficulty = SaveData.getDifficulty();

        sensitivity = SaveData.getSensitivity();

        createAssetDependents = false;
        isOptionMenu = false;

        assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.manager.update() && !createAssetDependents) {
            initImages();
            createAssetDependents = true;
        }

        batch.setProjectionMatrix(camera.combined);

        //loading screen and menu
        //menu
        if (game.manager.update() && createAssetDependents) {
            stage.act(delta);

            generalUpdates(Gdx.graphics.getDeltaTime());

            batch.begin();
            background.draw(batch);
            player.draw(batch);
            batch.end();

            //Shows main menu or option menu
            if (isOptionMenu) {
                play.setVisible(false);
                exit.setVisible(false);
                optionsTable.setVisible(true);
                stage.draw();
            }else{
                play.setVisible(true);
                exit.setVisible(true);
                optionsTable.setVisible(false);
                stage.draw();
            }


        }else{
            // loading screen
            progress = game.manager.getProgress() * 100;

            batch.begin();

            font.draw(batch, "Loading: " + String.valueOf(progress) + "%", 610, 50);

            batch.end();
        }
        stateTime += Gdx.graphics.getDeltaTime();

    }

    private void initImages() {
        //skin
        skin = new Skin();
        textureAtlas = game.manager.get("images/ui/ui.pack", TextureAtlas.class);
        skin.add("upButton", textureAtlas.createSprite("buttonUp"));
        skin.add("downButton", textureAtlas.createSprite("buttonDown"));
        skin.add("sliderBackground", textureAtlas.createSprite("sliderBackground"));
        skin.add("knob", textureAtlas.createSprite("sliderKnob"));

        //Button init and slider
        style = new TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("images/ui/font.fnt"), new TextureRegion(game.manager.get("images/ui/font.png", Texture.class)));

        style.up = skin.getDrawable("upButton");
        style.down = skin.getDrawable("downButton");
        style.pressedOffsetX = 1;
        style.pressedOffsetY = -1;
        style.font = buttonFont;

        sliderStyle = new SliderStyle();
        labelStyle = new LabelStyle();
        labelStyle.font = buttonFont;
        sliderStyle.background = skin.getDrawable("sliderBackground");
        sliderStyle.knob = skin.getDrawable("knob");

        //setting up difficultySlider
        difficultyLabel = new Label("", labelStyle);
        difficultySlider = new Slider(0f, 2f, .05f, false, sliderStyle);

        switch (difficulty){
            case EASY: difficultySlider.setValue(0); difficultyLabel.setText("EASY");
                break;
            case NORMAL: difficultySlider.setValue(1); difficultyLabel.setText("NORMAL");
                break;
            case HARD: difficultySlider.setValue(2); difficultyLabel.setText("HARD");
                break;
        }

        //setting up control Slider
        sensitivityLabel = new Label("", labelStyle);
        sensitivitySlider = new Slider(.01f, 1, .01f, false, sliderStyle);
        sensitivitySlider.setValue(sensitivity);
        sensitivityLabel.setText("Sensitivity For Tilting: " + String.valueOf(sensitivity * 100) + "%");

        play = new TextButton("PLAY", style);
        exit = new TextButton("EXIT", style);
        optionMenu = new TextButton("OPTIONS", style);

        //for basic menu
        table.add(play);
        table.getCell(play).spaceBottom(50).padRight(Constants.MENU_X_OFFSET);
        table.getCell(play).left();
        table.row();

        table.add(exit);
        table.getCell(exit).spaceBottom(50);
        table.getCell(exit).left();
        table.row();

        table.add(optionMenu);
        table.getCell(optionMenu).spaceRight(200);
        table.getCell(optionMenu).left();

        //for options
        optionsTable.add(difficultyLabel).spaceBottom(10);
        optionsTable.row();
        optionsTable.add(difficultySlider).width(300).spaceBottom(20);
        optionsTable.row();

        optionsTable.add(sensitivityLabel).spaceBottom(10);
        optionsTable.row();
        optionsTable.add(sensitivitySlider).width(300).spaceBottom(20);
        optionsTable.row();

        stage.addActor(table);
        stage.addActor(optionsTable);

        //code for background
        background = new Background(game, "images/background/background.png", new Vector2(0,0), new  Vector2(0, -20));
        //player init
        player = new Player(game);

    }

    private void generalUpdates(float delta) {
        //Delay for clicking
        if (stateTime - eventDelay > .1F) {
            //switches between option menu and main menu
            if (!isOptionMenu) {
                optionMenu.setText("OPTIONS");
                if (play.isPressed()) {
                    game.setScreen(game.getGameScreen());
                }else if (exit.isPressed()) {
                    Gdx.app.exit();
                }else if (optionMenu.isChecked() && !optionMenu.isPressed()) {
                    isOptionMenu = true;
                }
            }else{
                optionMenu.setText("RETURN");
                if (!optionMenu.isChecked() && !optionMenu.isPressed()) {
                    isOptionMenu = false;
                }

                //sensitivity slider
                sensitivity = sensitivitySlider.getValue();
                sensitivityLabel.setText("Sensitivity for Tilting: " + String.valueOf((int) (sensitivity * 100)) + "%");
                SaveData.setSensitivity(sensitivity);

                if (java.lang.Math.round(difficultySlider.getValue()) == 0) {
                    difficulty = Difficulty.EASY;
                    difficultyLabel.setText("EASY");
                }else if (java.lang.Math.round(difficultySlider.getValue()) == 1) {
                    difficulty = Difficulty.NORMAL;
                    difficultyLabel.setText("NORMAL");
                }else if (java.lang.Math.round(difficultySlider.getValue()) == 2) {
                    difficulty = Difficulty.HARD;
                    difficultyLabel.setText("HARD");
                }
                SaveData.setDifficulty(difficulty);
            }

            eventDelay = stateTime;
        }

        player.update(delta);
        background.update(delta);
    }

    private void setGame(Evasion game) {
        this.game = game;
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
        game.manager.dispose();
        stage.dispose();
        batch.dispose();
        font.dispose();
    }

    public TextButtonStyle getStyle() {
        return style;
    }
}
