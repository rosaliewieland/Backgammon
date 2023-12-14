package backgammon.game.basic_backend;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.security.SecureRandom;
import java.util.HashMap;

public class Dice {
    public int getDice()
    {
        SecureRandom rand = new SecureRandom();
        int randomNumber = rand.nextInt(5) + 1;
        return randomNumber;
    }
}








