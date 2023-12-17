package backgammon.game.screens;

import com.badlogic.gdx.Game;

public class ScreenHandler extends Game {

    public static ScreenHandler INSTANCE;

    public ScreenHandler() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        setScreen(new MainMenu());

    }
}
