package evasion.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import evasion.game.Constants;
import evasion.game.Evasion;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Constants.NAME;
		config.width = Constants.VIRTUAL_WIDTH;
		config.height = Constants.VIRTUAL_HEIGHT;
		new LwjglApplication(new Evasion(), config);
	}
}
