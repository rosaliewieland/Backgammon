package backgammon.game.screens;

import backgammon.game.basic_frontend.DiceManager;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;

public class PlayerSettings extends ScreenAdapter {
    DiceManager dice1;
    DiceManager dice2;
    Texture dicebutton;
    float stateTime;
    float stateTime2;
    private final int DICE_BUTTON_WIDTH = 70;
    private final int DICE_BUTTON_HEIGHT = 80;
    int dicenumber1;
    int dicenumber2;
    private Texture diceResultTexture;
    private Texture diceResultTexture2;
    private final int DICE1_BUTTON_X = 50;
    private final int DICE1_BUTTON_Y = 50;
    private final int DICE2_BUTTON_X = 50;
    private final int DICE2_BUTTON_Y = 120;


    public PlayerSettings() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
