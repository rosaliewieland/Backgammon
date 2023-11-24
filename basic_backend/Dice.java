package basic_backend;

import java.util.Random;

public class Dice {
    private int randomNumber;
    public int getDice()
    {
        Random random = new Random();
        randomNumber = random.nextInt(5) + 1; // (Anni) bound ist einschließlich null, daher + 1 um die 0 zu vermeiden
        return randomNumber;
    }
}
