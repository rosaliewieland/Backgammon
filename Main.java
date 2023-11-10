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
//         - Anni: Spielbeginn: Bei Unentschieden wird noch mal gewürfelt + auslagern
//         - Anni: 3 Arten von Siege (Sieg, Gammon-Sieg, BackGammon-Sieg)
//         - Alle: andere Variablen für currentPlayer / otherPlayer
//         - Alle: Eingabe überprüfen (isValidInput) implementieren
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


        //playerOne.rollDice(diceOne, diceTwo);
        //playerTwo.rollDice(diceOne, diceTwo);

        // Spieler ausgaben
        //System.out.println("Gewurfelt: " + playerOne.getDiceNumber1());
        //System.out.println(playerTwo.toString() + " " + playerTwo.getDiceNumber1() + " " + playerTwo.getDiceNumber2());

        int field[][] = new int[24][5];

        //PrintBoard.printBoard(field);

        Board board = new Board();
        PrintBoard.printBoard(board.getField());

        // Spieler mit höherem Würfelergebnis startet
        Rules rule = new Rules();
        Player currentPlayer; // Spieler der startet
        Player otherPlayer;
        //int playerOneDice = playerOne.getDiceNumber1();
        //int playerTwoDice = playerTwo.getDiceNumber2();

        playerOne.rollDice(diceOne, diceTwo);
        System.out.println("Spieler 1 " +playerOne.getName() + " Gewürfelt: " + playerOne.getDiceNumber1());
        playerTwo.rollDice(diceOne,diceTwo);
        System.out.println("Spieler 2 " +playerTwo.getName()+ " Gewürfelt: " + playerTwo.getDiceNumber2());


        // Aktuell wird unentschieden nicht berücksichtigt!

        if (rule.startPlayer(playerOne.getDiceNumber1(), playerTwo.getDiceNumber2())){
            System.out.println("Spieler 1 startet");
            currentPlayer = playerOne;
            otherPlayer = playerTwo;

        } else //if (!rule.startPlayer(playerOne.getDiceNumber1(), playerTwo.getDiceNumber2()))
        {
            System.out.println("Spieler 2 startet");
            playerTwo.rollDice(diceOne, diceTwo);
            currentPlayer = playerTwo;
            otherPlayer = playerOne;
            }
            // else{} --> Hier rekursiv aufrufen




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
