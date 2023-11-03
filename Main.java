import basic_backend.Board;
import basic_backend.Dice;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int prev = 0;
        int randomNumber = 0;
        boolean isGameFinished = false;
        Board b1 = new Board(1);
        Dice dice = new Dice();



        while(isGameFinished == false){
            System.out.println("Würfel: ");
            scanner.nextInt();
            randomNumber = dice.getDice();
            System.out.println("Ausgabe von Eignabe: " + randomNumber);

            isGameFinished = b1.moveStone(randomNumber+prev, prev);
            prev = randomNumber+prev;
            b1.printBoard();
            System.out.println("isGameFinished: " + isGameFinished);
        }

        /*
        System.out.println("Wie weit willst du Laufen?: ");
        eingabe = scanner.nextInt();
        System.out.println("Ausgabe von Eignabe: " + eingabe);
        b1.moveStone(eingabe+prev, prev);
        b1.printBoard();*/
        System.out.println("Test");

    }
}
