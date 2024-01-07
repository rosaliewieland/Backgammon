package backgammon.game.screens;

import backgammon.game.Backgammon;
//import backgammon.game.basic_frontend.ErrorMessage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenHandler extends Game {

    public static ScreenHandler INSTANCE;

    public ScreenHandler() {
        INSTANCE = this;
    }
    public void error()
    {
      //  setScreen(new ErrorMessage());
    }

    @Override
    public void create() {
        setScreen(new MainMenu());
        //setScreen(new Backgammon() );

    }
}
