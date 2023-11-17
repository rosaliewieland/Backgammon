import basic_backend.*;

// ToDo's: - Stein ins Ziel bringen ermoeglichen
//         - Anni: 3 Arten von Siege (Sieg, Gammon-Sieg, BackGammon-Sieg)
//         - Ziel des Spiels
//              -ist im Feld (Cey)
//              -Abbauen (Marco)
//         - Problem: die steine sind nicht an Spielerfarbe gekoppelt
//         - Rosie: 4 Steine bewegen
//         - (erledigt) Anni: isAccessible überarbeiten, Problemlösen eigene Steine

public class Main {
    public static void main(String[] args)
    {

    // Anfang: Spiel mit fuenf Steinen und der neuen Klasse Board.java
        // Initialisierung von zwei Würfeln die beiden Spieler zum Würfeln zur Verfügung stehen
        Dice diceOne = new Dice();
        Dice diceTwo = new Dice();
        Rules rules = new Rules();


        // Initialisierung von zwei Spielern.
        Player playerColor = new Player();
        boolean color = playerColor.enterPlayerColor(); // Spieler kann Farbe auswählen

        Player playerOne = new Player(color);
        Player playerTwo = new Player(!color); // der zweite Spieler bekommt automatisch die andere Farbe


        Player startPlayer = new Player();
        Player firstPlayer; // Spieler der startet
        Player secondPlayer;

        // Ausgabe des Boards
        // erzeugt das Board muss zwingend mit der Methode createReverenceSet initialisiert werden!
        Board board = new Board();
        board.createReverenceSet();

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
            System.out.println("Würfel1: " + firstPlayer.getDiceNumber1()+ " Würfel 2: " + firstPlayer.getDiceNumber2());
            //firstPlayer.printGameBar();
            firstPlayer.moveStone(board.getField(), firstPlayer.getDiceNumber1(), firstPlayer.getDiceNumber2(),secondPlayer, board);
            PrintBoard.printBoard(board.getField());

            secondPlayer.rollDice(diceOne,diceTwo);
            System.out.println("Würfel1: " + secondPlayer.getDiceNumber1()+ " Würfel 2:"+ secondPlayer.getDiceNumber2());
            //secondPlayer.printGameBar();
            secondPlayer.moveStone(board.getField(), secondPlayer.getDiceNumber1(),secondPlayer.getDiceNumber2(), firstPlayer, board);
            PrintBoard.printBoard(board.getField());
        }
       // Ende: Spiel mit fuenf Steinen und der neuen Klasse Board.java
    }

}
