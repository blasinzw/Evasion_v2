package evasion.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import evasion.assets.Assets;
import evasion.screens.GameScreen;
import evasion.screens.HighScoreScreen;
import evasion.screens.MainMenuScreen;

public class Evasion extends Game {

	public SpriteBatch batch;
	public AssetManager manager;

	//Screens
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private HighScoreScreen highScoreScreen;

	//Global classes
	public OrthographicCamera camera;
	private Viewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();

		//Global classes
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
		viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);

		//Screens
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		highScoreScreen = new HighScoreScreen(this);

		setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		manager.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public HighScoreScreen getHighScoreScreen() {
		return highScoreScreen;
	}
}
