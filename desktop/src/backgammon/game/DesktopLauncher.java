package backgammon.game;

import backgammon.game.basic_backend.*;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import backgammon.game.Backgammon;
import com.badlogic.gdx.graphics.Color;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Backgammon");


		// Set Window size
		config.setWindowedMode(1400, 950);
		new Lwjgl3Application(new Backgammon(), config);

	}
}
