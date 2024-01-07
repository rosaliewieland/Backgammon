package backgammon.game.basic_backend;

public class Main {
    public void main(String[] args)
    {
        // Initialisierung von zwei Würfeln die beiden Spieler zum Würfeln zur Verfügung stehen
        Dice diceOne = new Dice();
        Dice diceTwo = new Dice();

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

        // Ab hier beginnt das Spiel
        System.out.println("--Spiel beginnt--");

        // Spiele Spiel bis ein Gewinner gefunden wurde
        while(!firstPlayer.isTheWinner() || !secondPlayer.isTheWinner()) {
            firstPlayer.printGameBar();
            firstPlayer.rollDice(diceOne, diceTwo);
            System.out.println("Würfel1: " + firstPlayer.getDiceNumber1() + " Würfel 2: " + firstPlayer.getDiceNumber2());
            firstPlayer.moveStone(board.getField(), firstPlayer.getDiceNumber1(),  firstPlayer.getDiceNumber2(), secondPlayer, board);
            PrintBoard.printBoard(board.getField());

            secondPlayer.printGameBar();
            secondPlayer.rollDice(diceOne,diceTwo);
            System.out.println("Würfel1: " + secondPlayer.getDiceNumber1() + " Würfel 2: "+ secondPlayer.getDiceNumber2());
            secondPlayer.moveStone(board.getField(), secondPlayer.getDiceNumber1(),  secondPlayer.getDiceNumber2(), firstPlayer, board);
            PrintBoard.printBoard(board.getField());
        }
    }
}
