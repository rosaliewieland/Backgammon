package basic_backend;

import java.security.SecureRandom;

public class Dice {
    public int getDice()
    {
        SecureRandom rand = new SecureRandom();
        int randomNumber = rand.nextInt(5) + 1;
        return randomNumber;
    }
}
