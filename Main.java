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

        Player startPlayer = new Player();
        Player currentPlayer; // Spieler der startet
        Player otherPlayer;

        //PrintBoard.printBoard(field);

        Board board = new Board();
        PrintBoard.printBoard(board.getField());

        // Spieler mit höherem Würfelergebnis startet
        currentPlayer = startPlayer.startPlayer(playerOne, playerTwo, diceOne, diceTwo); //übergibt den starteten Spieler
        if (currentPlayer == playerOne)
            otherPlayer = playerTwo;
        else
            otherPlayer = playerOne;

        System.out.println("--Schleifenbeginn--"); // Ab hier beginnt der Spielverlauf

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
