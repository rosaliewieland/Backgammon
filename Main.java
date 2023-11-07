import basic_backend.Board;
import basic_backend.Dice;
import basic_backend.Player;
import basic_backend.PrintBoard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {

    // Anfang: Spiel mit einem Stein ohne die neue Klasse Board.java
        /*
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
        b1.printBoard();
        */
    // Ende: Spiel mit einem Stein ohne die neue Klasse Board.java

    // Anfang: Spiel mit fuenf Steinen und der neuen Klasse Board.java
        // Initialisierung von zwei Würfeln die beiden Spieler zum Würfeln zur Verfügung stehen
        Dice diceOne = new Dice();
        Dice diceTwo = new Dice();

        // Initialisierung von zwei Spielern.
        Player playerOne = new Player("Marco", false);
        Player playerTwo = new Player("Cey", true);

        playerOne.rollDice(diceOne, diceTwo);
        playerTwo.rollDice(diceOne, diceTwo);

        // Spieler ausgaben
        System.out.println(playerOne.getDiceNumber1());
        //System.out.println(playerTwo.toString() + " " + playerTwo.getDiceNumber1() + " " + playerTwo.getDiceNumber2());

        int field[][] = new int[24][5];

        //PrintBoard.printBoard(field);


        Board board = new Board();


        PrintBoard.printBoard(board.getField());

        playerOne.moveStone(board.getField(), playerOne.getDiceNumber1());

        PrintBoard.printBoard(board.getField());




        // Ende:  Spiel mit fuenf Steinen und der neuen Klasse Board.java
    }
}
