package backgammon.game.screens;

import backgammon.game.Backgammon;
import backgammon.game.basic_frontend.ErrorMessage;
import com.badlogic.gdx.Game;

public class ScreenHandler extends Game {

    public static ScreenHandler INSTANCE;

    public ScreenHandler() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        //setScreen(new MainMenu());
        setScreen(new MainMenu() );

    }
}
