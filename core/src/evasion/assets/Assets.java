package evasion.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import evasion.game.Evasion;

public class Assets {
    private AssetManager manager;

    public Assets(Evasion game) {
        this.manager = game.manager;
    }

    public void load() {
        manager.load("images/player/player.pack", TextureAtlas.class);
        manager.load("images/asteroid/asteroids.pack", TextureAtlas.class);
        manager.load("images/asteroid/asteroidExplosions.pack", TextureAtlas.class);
        manager.load("images/drops/coins.pack", TextureAtlas.class);
        manager.load("images/background/background.png", Texture.class);
        manager.load("images/player/laser.png", Texture.class);
        manager.load("images/hud/laserBar.png", Texture.class);
        manager.load("images/ui/font.png", Texture.class);
        manager.load("images/ui/ui.pack", TextureAtlas.class);
        manager.load("images/mine/mine.pack", TextureAtlas.class);
    }
}
