package basic_backend;

import java.util.Random;

public class Dice {
    private int randomNumber;
    public int getDice()
    {
        Random random = new Random();
        randomNumber = random.nextInt(5) + 1;
        return randomNumber;
    }
}
