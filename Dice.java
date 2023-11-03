import java.util.Random;

public class Dice {
    private int randomNumber;
    public int getDice()
    {
        Random random = new Random();
        //randomNumber = Math.random(5);
        randomNumber = random.nextInt(5) + 1;
        return randomNumber;
    }
}
