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
//         - (erledigt) Anni: andere Variablen für currentPlayer, otherPlayer --> firstPlayer, secondPlayer
//         - Anni: 3 Arten von Siege (Sieg, Gammon-Sieg, BackGammon-Sieg)
//         - Anni: Eingabe überprüfen (isValidInput) --> try, except
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
        Player firstPlayer; // Spieler der startet
        Player secondPlayer;

        // Ausgabe des Boards
        Board board = new Board();
        PrintBoard.printBoard(board.getField());

        // Spieler mit höherem Würfelergebnis startet
        firstPlayer = startPlayer.startPlayer(playerOne, playerTwo, diceOne, diceTwo); //übergibt den starteten Spieler
        if (firstPlayer == playerOne)
            secondPlayer = playerTwo;
        else
            secondPlayer = playerOne;

        // Ab hier beginnt der Spielverlauf
        System.out.println("--Schleifenbeginn--");

        // Test-Schleife Spieldurchlauf
        for(int i = 0; i<5; i++)
        {
            firstPlayer.rollDice(diceOne, diceTwo);
            System.out.println("Gewürfelt: " + firstPlayer.getDiceNumber1());
            firstPlayer.printgameBoard();
            firstPlayer.moveStone(board.getField(),firstPlayer.getDiceNumber1(), secondPlayer);
            PrintBoard.printBoard(board.getField());

            secondPlayer.rollDice(diceOne,diceTwo);
            System.out.println("Gewürfelt: " + secondPlayer.getDiceNumber2());
            secondPlayer.printgameBoard();
            secondPlayer.moveStone(board.getField(), secondPlayer.getDiceNumber2(), firstPlayer);
            PrintBoard.printBoard(board.getField());
        }
        // Ende: Spiel mit fuenf Steinen und der neuen Klasse Board.java
    }


}
