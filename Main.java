import basic_backend.*;

import java.util.Scanner;

// ToDo's: - Stein ins Ziel bringen ermoeglichen
//         - (Erledigt) Stein schlaegt Gegnerstein raus
//              - Dokumetation schreiben
//         - (Erledigt) Steine ausserhalb des Spielesfeldes darstellen (Falls Stein gerade nicht im Spiel)
//             - (Erledigt) Es Fehlt nocht die Steine wieder ins Spielfeld bringen
//              - Dokumetation schreiben
//         - (Erledigt) Stein wieder ins Spiel bringen
//         (- Zwei Wuerfel Option)(Rosi)
//         - (erledigt) Anni: Unentschieden + auslagern
//         - Anni: 3 Arten von Siege (Sieg, Gammon-Sieg, BackGammon-Sieg)
//         - Anni: Eingabe überprüfen (isValidInput) --> try, except
//         - Anni: andere Variablen für currentPlayer / otherPlayer
//         - Eingabe Spielername, Farbenzuordnung
//         - Eingabe von Spielername
//         - Eingabe -> prüfen
//         - Unentschieden start
//         - moveStone -> Schoener
//         - Ziel des Spiels
//              -ist im Feld (Cey)
//              -Abbauen (Marco)

public class Main {
    public static void main(String[] args)
    {

    // Anfang: Spiel mit fuenf Steinen und der neuen Klasse Board.java
        // Initialisierung von zwei Würfeln die beiden Spieler zum Würfeln zur Verfügung stehen
        Dice diceOne = new Dice();
        Dice diceTwo = new Dice();

        // Initialisierung von zwei Spielern.
        Player playerOne = new Player("Marco", false);
        Player playerTwo = new Player("Cey", true);

        int field[][] = new int[24][5];

        //PrintBoard.printBoard(field);

        Board board = new Board();
        PrintBoard.printBoard(board.getField());

        // Spieler mit höherem Würfelergebnis startet
        Player startPlayer = new Player();

        Player currentPlayer; // Spieler der startet
        Player otherPlayer;
        int playerOneDice = playerOne.getDiceNumber1();
        int playerTwoDice = playerTwo.getDiceNumber2();

        currentPlayer = startPlayer.startPlayer(playerOne, playerTwo, diceOne, diceTwo);
        if (currentPlayer == playerOne)
            otherPlayer = playerTwo;
        else
            otherPlayer = playerOne;


        // Aktuell wird unentschieden nicht berücksichtigt!

        /*private static void startPlayer1() {

        playerOne.rollDice(diceOne, diceTwo);
        System.out.println("Spieler 1 " +playerOne.getName() + " Gewürfelt: " + playerOne.getDiceNumber1());
        playerTwo.rollDice(diceOne,diceTwo);
        System.out.println("Spieler 2 " +playerTwo.getName()+ " Gewürfelt: " + playerTwo.getDiceNumber2());

        if (rule.startPlayer(playerOne.getDiceNumber1(), playerTwo.getDiceNumber2())) {
            System.out.println("Spieler 1 startet");
            currentPlayer = playerOne;
            otherPlayer = playerTwo;

        } else if (!rule.startPlayer(playerOne.getDiceNumber1(), playerTwo.getDiceNumber2())) {
            System.out.println("Spieler 2 startet");
            playerTwo.rollDice(diceOne, diceTwo);
            currentPlayer = playerTwo;
            otherPlayer = playerOne;
        } else
            startPlayer1();
    }*/


        System.out.println("--Schleifenbeginn--");

        // Test-Schleife Spieldurchlauf
        for(int i = 0; i<5; i++)
        {
            currentPlayer.rollDice(diceOne, diceTwo);
            System.out.println("Gewürfelt: " + currentPlayer.getDiceNumber1());
            currentPlayer.printgameBoard();
            currentPlayer.moveStone(board.getField(),currentPlayer.getDiceNumber1(), otherPlayer);
            PrintBoard.printBoard(board.getField());

            otherPlayer.rollDice(diceOne,diceTwo);
            System.out.println("Gewürfelt: " + otherPlayer.getDiceNumber2());
            otherPlayer.printgameBoard();
            otherPlayer.moveStone(board.getField(), otherPlayer.getDiceNumber2(), currentPlayer);
            PrintBoard.printBoard(board.getField());
        }
        // Ende: Spiel mit fuenf Steinen und der neuen Klasse Board.java
    }


}
