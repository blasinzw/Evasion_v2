package evasion.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import evasion.assets.Assets;
import evasion.screens.MainMenuScreen;

public class Evasion extends Game {

	public SpriteBatch batch;
	public AssetManager manager;
	private Assets assets;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		assets = new Assets(this);
		assets.load();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		manager.dispose();
	}

}
