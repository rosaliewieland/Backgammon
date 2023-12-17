package backgammon.game.basic_backend;

import backgammon.game.basic_backend.Rules;
import backgammon.game.basic_frontend.ErrorMessage;
import backgammon.game.screens.ScreenHandler;

import java.util.HashSet;
import java.util.Set;

public class Player{

    private String name;
    private boolean isBlack;
    private int stone;
    private int diceNumber1;
    private int diceNumber2;

    private Rules rules= new Rules();
    private int[] gameBar = new int[10];

    private Set<Integer> outOfBoard = new HashSet<>();


    // Konstruktor
    public Player(){

    }

    public int[] getGameBar() {
        return gameBar;
    }

    public Player(boolean isBlack) {
        //enterPlayerName();
        this.isBlack = isBlack;
        for(int i = 0; i < gameBar.length; i++)
        {
            gameBar[i] = 0;
        }
    }


    // Getter
    public String getName(){
        return name;
    }
    public int getStones() {
        return stone;
    }
    public boolean isBlack() {
        return isBlack;
    }
    public int getDiceNumber1() {
        return diceNumber1;
    }
    public int getDiceNumber2() {
        return diceNumber2;
    }

    // Setter
    public void setStone(int stone) {
        this.stone = stone;
    }
    public void setDiceNumber1(int diceNumber1) {
        this.diceNumber1 = diceNumber1;
    }
    public void setDiceNumber2(int diceNumber2) {
        this.diceNumber2 = diceNumber2;
    }


    // Methode die es dem Spieler erlaubt zu würfeln.
    // Input sind Würfel 1 und Würfel 2.
    // Über den Setter werden die gewürfelten Augen auf die
    public void rollDice(Dice d1, Dice d2) {
        int numberOne = d1.getDice();
        int numberTwo = d2.getDice();
        setDiceNumber1(numberOne);
        setDiceNumber2(numberTwo);
    }

    // Spieler mit der höheren Augenzahl beginnt das Spiel
    public Player startPlayer(Player playerOne, Player playerTwo, Dice diceOne, Dice diceTwo) {

        int sumDicePlayerOne;
        int sumDicePlayerTwo;

        playerOne.rollDice(diceOne, diceTwo);
        sumDicePlayerOne= playerOne.diceNumber1 + playerOne.diceNumber2;
        System.out.println("Spieler 1 " + playerOne.name + " Gewürfelt: " + sumDicePlayerOne);

        playerTwo.rollDice(diceOne,diceTwo);
        sumDicePlayerTwo = playerTwo.diceNumber1 + playerTwo.diceNumber2;
        System.out.println("Spieler 2 " + playerTwo.name + " Gewürfelt: " + sumDicePlayerTwo);

        if (sumDicePlayerOne > sumDicePlayerTwo) {
            return playerOne;
        }
        else if (sumDicePlayerOne < sumDicePlayerTwo) {
            return playerTwo;
        }
        else // Wenn unentschieden
            System.out.println("Unentschieden. Es wird nochmal gewürfelt");
        return startPlayer(playerOne, playerTwo, diceOne, diceTwo);
    }

    public void enterPlayerName() {
        System.out.println("Gebe einen Namen an: ");
        name = rules.validStringInput();
    }

    public boolean enterPlayerColor() {
        System.out.println("Wähle zwischen Schwarz oder Weiß: ");
        return rules.validColorInput();
    }

    public void moveStone(int[][] field, int dice1, int dice2,  Player opponentPlayer, Board board){
        int choice = 0;
        int third_choice = 0;
        System.out.println("Spieler " + name + ", möchtest du einen Stein mit der Summe der Würfelaugen bewegen oder zwei Steine mit jeder Würfelaugenanzahl?");

        /*if (dice1==dice2) {
            System.out.println("Pasch gewürfelt. Augenzahl wird verdoppelt");
            dice1 = dice1 * 2;
            dice2 = dice2 * 2;
            System.out.println("Spieler " + name + ", welchen Zug möchtest du machen?");
            System.out.println("1. Einen Stein mit der Summe verdoppelt bewegen");
            System.out.println("2. Zwei Steine mit je doppelter Augenzahl bewegen");
            System.out.println("3. Vier Steine mit der Augenzahl einzeln gehen");

            //third_choice =  rules.validIntegerInput();

            if (third_choice == 1){
                moveOneStone(field,dice1+dice2,opponentPlayer,board);
            } else if (third_choice == 2) {
                moveTwoStones(field, dice1, dice2, opponentPlayer,board);
            } else if (third_choice == 3){
                moveOneStone(field, dice1 / 2, opponentPlayer,board);
                moveOneStone(field, dice1 / 2, opponentPlayer,board);
                moveOneStone(field, dice1 / 2, opponentPlayer,board);
                moveOneStone(field, dice1 / 2, opponentPlayer,board);
            }
        }
        else {*/
            System.out.println("1. Einen Stein mit der Summe bewegen");
            System.out.println("2. Zwei Steine mit jeder Augenzahl bewegen");

            choice = rules.validIntegerInput();

            if (choice == 1) {
                moveOneStone(field, dice1 + dice2, opponentPlayer, board);
            } else if (choice == 2) {
                //moveTwoStones(field, dice1, dice2, opponentPlayer, board);
                moveOneStone(field, dice1, opponentPlayer,board);
                moveOneStone(field, dice2, opponentPlayer,board);

            //} else {
            //    System.out.println("Ungültige Eingabe. Bitte wähle 1 oder 2.");
            //    moveStone(field, dice1, dice2, opponentPlayer, board);
           // }
        }
    }


    public boolean moveOneStone(int[][] field, int sum, Player opponentPlayer, Board board) {
        boolean rightStone = false;
        boolean accessMove = false;
        boolean stoneIsOut = false;

       // while(!accessMove)
       // {
            board.createsSetOfHomeField();

            if(rules.isStoneOut(gameBar))
            {
                // Setze den Stein aus der Game Bar
                stoneIsOut = true;
                gameBarIsNotEmpty();
            }
            /*else
            {
                System.out.println("Welchen Stein moechte " + name + " bewegen?:");
                // Ermöglicht und überprüft (Integer) Eingabe
                stone = rules.validIntegerInput();
            }*/
            // Eingabe welcher Stein bewegt werden soll
            rightStone = rules.isStoneYours(isBlack, stone);
            System.out.println(isBlack);

            // Bewege richtigen Stein (Also Positive Steine oder negative Steine)
            if(rightStone) {
                accessMove = findStone(field, opponentPlayer, sum, board, stoneIsOut);
            }
            else
            {

                System.out.println("Bitte waehle ein Stein der dir gehoert!");
            }
       // }
        return accessMove;
    }
    //Methode mit zwei einzelnen Steinen laufen
    public void moveTwoStones(int[][] field, int dice1, int dice2, Player opponentPlayer, Board board) {
        // Move the first stone with dice1
        moveOneStone(field, dice1, opponentPlayer, board);

        // Move the second stone with dice2
        moveOneStone(field, dice2, opponentPlayer, board);

    }


    public boolean findStone(int[][] field, Player opponentPlayer ,int diceNumber, Board board, boolean stoneIsOut) {
        boolean control = false;

        if(!isBlack) {
            if (stoneIsOut) {
                if (rules.isAccessible(isBlack, diceNumber, field, opponentPlayer.gameBar, true))
                {
                    setGameBarStone(field, diceNumber);
                }
                //setGameBarStone(field, diceNumber);
                return true;
            }
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone)
                    {
                        if (rules.isAccessible(isBlack, diceNumber, field, opponentPlayer.gameBar, true))
                        {
                           /* if(permissionToMoveStoneOutOfBoard(board, diceNumber, i)) {
                                board.adjustReverenceSet(field[i][j]);
                                outOfBoard.add(field[i][j]);
                                field[i][j] = 0;
                                control = true;
                            }
                            else {*/
                                // Stein in neues Feld schreiben.
                                searchFreeField(field, diceNumber);
                                field[i][j] = 0;
                                control = true;
                            }
                            //}
                        else {
                            return false;
                        }
                    }
                }
            }
        }else if(isBlack) {
            if (stoneIsOut) {
                if (rules.isAccessible(isBlack, diceNumber, field, opponentPlayer.gameBar, true))
                {
                    setGameBarStone(field, diceNumber);
                }
                System.out.println("Bringe Stein " + stone + " wieder ins Spiel");
                return true;
            }
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone) {
                        if (rules.isAccessible(isBlack, diceNumber, field, opponentPlayer.gameBar, true)) {
                           /* if (permissionToMoveStoneOutOfBoard(board, diceNumber, i)) {
                                board.adjustReverenceSet(field[i][j]);
                                outOfBoard.add(field[i][j]);
                                field[i][j] = 0;
                                control = true;


                            } else {*/
                                // Stein in neues Feld schreiben.
                                searchFreeField(field, diceNumber);
                                field[i][j] = 0;
                                control = true;
                          //  }
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void gameBarIsNotEmpty()
    {
        for(int i = 0; i< gameBar.length; i++)
        {
            if (gameBar[i]!=0){
                stone = gameBar[i];
                System.out.println("Stein von Game Bar:" + stone);
            }
        }
    }
    public void setGameBarStone(int[][] field, int newIndex)
    {
        boolean control = false;
        if(!isBlack)
        {
            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                if (field[newIndex][counterFreeField] == 0 && !control) {
                    // Bewege Negativen Stein
                    field[newIndex][counterFreeField] = stone;
                    control = true;
                    for(int i=0;i<5;i++)
                    {
                        if(gameBar[i]==stone)
                        {
                            gameBar[i]=0;
                        }
                    }
                }
            }
        }
        else if(isBlack)
        {
            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                if (field[newIndex][counterFreeField] == 0 && !control) {
                    // Bewege Positiven Stein
                    field[newIndex][counterFreeField] = stone;
                    control = true;
                    for(int i=0;i<5;i++)
                    {
                        if(gameBar[i]==stone)
                        {
                            gameBar[i]=0;
                        }
                    }
                }
            }
        }
    }
    public void searchFreeField(int[][] field, int indexI)
    {
        boolean control = false;
        for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
            if (field[indexI][counterFreeField] == 0 && !control) {
                field[indexI][counterFreeField] = stone;
                control = true;
            }
        }
    }

    public void printGameBar()
    {
        System.out.println("Game Board von Spieler: " + name);
        for (int i = 0; i < gameBar.length; i++) {
            System.out.printf(gameBar[i] + ", ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nAmount of Stones: " + stone + "\nColor is Black: " + isBlack;
    }

    public boolean permissionToMoveStoneOutOfBoard(Board board, int diceNumber, int positionOfStone) {
        boolean colorOfStone = isBlack;
        boolean checkForOutOfBound = rules.isOutOfBound(diceNumber, positionOfStone);
        board.compareSetsEnableRemoveStones();
        System.out.println("ColorStone " + colorOfStone);
        System.out.println("Weis erlaubt abzubauen?"+board.isWhitePermittedRemoveStones());
        System.out.println("ChecloutBound"+ !checkForOutOfBound);
        if (colorOfStone) {
            if (board.isBlackPermittedRemoveStones() && !checkForOutOfBound) {
                return true;
            } else {
                return false;
            }
        } else {
            if (board.isWhitePermittedRemoveStones() && !checkForOutOfBound) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void setOutOfBoard(int stoneId) {
        this.outOfBoard.add(stoneId);
        System.out.println(outOfBoard);
    }

    public Set<Integer> getOutOfBoard() {
        return outOfBoard;
    }

    public boolean isTheWinner() {
        if (outOfBoard.size() == 15) {
            return true;
        } else {
            return false;
        }
    }
}
