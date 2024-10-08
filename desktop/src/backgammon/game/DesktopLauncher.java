package backgammon.game;

import backgammon.game.screens.ScreenHandler;
import backgammon.game.basic_backend.Main;
import backgammon.game.screens.MainMenu;
import backgammon.game.screens.ScreenHandler;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;



// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Backgammon");
		config.setWindowIcon("AppIcon.png");
		// Set Window size
		config.setWindowedMode(1350, 1000);
		new Lwjgl3Application(new ScreenHandler(), config);
	}
}
