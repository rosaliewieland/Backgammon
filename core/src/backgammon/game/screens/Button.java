package backgammon.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Button extends InputListener{
    Texture texture;
    int value;

    MainMenu menu;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        menu.changeScreen(button);
        return super.touchDown(event, x, y, pointer, button);
    }
}
