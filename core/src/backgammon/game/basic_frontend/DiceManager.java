package backgammon.game.basic_frontend;

import backgammon.game.basic_backend.Dice;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.security.SecureRandom;
import java.util.HashMap;

public class DiceManager extends Dice {
    //public TextureRegion diceanimation(x,y){

    //dicesheet = new Texture("assets/sprites.png");


    ////split to make equal split frames of dicesheet
    //		//devide through number of height and with to get the single frames
    //		TextureRegion[][] tmp = TextureRegion.split(dicesheet,
    //				dicesheet.getWidth()/FRAME_COLS, dicesheet.getHeight()/FRAME_ROWS);
    //
    //		// put in correct order in 1d array to be able to work with animation constructor
    //		TextureRegion[] diceFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
    //		int index = 0;
    //		for (int i = 0; i < FRAME_ROWS; i++) {
    //			for (int j = 0; j < FRAME_COLS; j++) {
    //				diceFrames[index++] = tmp[i][j];
    //			}
    //		}
    //
    //		//initialize animations + refresh rate
    //		diceanimation = new Animation<>(0.25f, diceFrames);
    //		diceanimation2 = new Animation<>(0.25f, diceFrames);


    //textureregion
    //batch.draw(currentframe,x,y)

    public Texture getDiceTexture(int x, int y) {


        Texture side1 = new Texture("assets/dice_1.png");
        Texture side2 = new Texture("assets/dice_2.png");
        Texture side3 = new Texture("assets/dice_3.png");
        Texture side4 = new Texture("assets/dice_4.png");
        Texture side5 = new Texture("assets/dice_5.png");
        Texture side6 = new Texture("assets/dice_6.png");

        HashMap<Integer, Texture> dice_textures = new HashMap<>();
        dice_textures.put(1, side1);
        dice_textures.put(2, side2);
        dice_textures.put(3, side3);
        dice_textures.put(4, side4);
        dice_textures.put(5, side5);
        dice_textures.put(6, side6);

        SecureRandom rand = new SecureRandom();
        int randomNumber = rand.nextInt(5) + 1;
        System.out.println(randomNumber);

        return dice_textures.get(randomNumber);
    }
   // public Texture getDiceResult(int x, int y) {
        //SecureRandom rand = new SecureRandom();
        //int randomNumber = rand.nextInt(5) + 1;


//}
}

