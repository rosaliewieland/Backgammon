package basic_backend;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Player{

    private String name;
    private boolean isBlack;
    private int stone = 15;

    private int diceNumber1;
    private int diceNumber2;

    private Rules rules= new Rules();
    private int[] gameBoardEdge = new int[10];

    private Set<Integer> outOfBoard = new HashSet<>();


    // Konstruktor
    public Player() {

    }

    public Player(boolean isBlack) {
        enterPlayerName();
        this.isBlack = isBlack;
        for(int i = 0; i < gameBoardEdge.length; i++)
        {
            gameBoardEdge[i] = 0;
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

    //Methode Wahl, ob du mit Summe oder einzeln ziehen möchtest
    public void moveStone(int field[][], int dice1, int dice2,  Player opponentPlayer){
        System.out.println("Spieler " + name + ", möchtest du einen Stein mit der Summe der Würfelaugen bewegen oder zwei Steine mit jeder Würfelaugenanzahl?");
        System.out.println("1. Einen Stein mit der Summe bewegen");
        System.out.println("2. Zwei Steine mit jeder Augenzahl bewegen");


        int choice = rules.validIntegerInput();

        if (dice1==dice2) {
            System.out.println("Pasch gewürfelt. Augenzahl wird verdoppelt");
            dice1 = dice1 * 2;
            dice2 = dice2 * 2;
        }
        if (choice == 1) {
            moveOneStone(field, dice1 + dice2, opponentPlayer,stone);
        } else if (choice == 2) {
            moveTwoStones(field, dice1, dice2, opponentPlayer);
        } else {
            System.out.println("Ungültige Eingabe. Bitte wähle 1 oder 2.");
            moveStone(field, dice1, dice2, opponentPlayer); // Recursive call to handle invalid input
        }
    }


    // Bewegt aktuell einen Stein (int field[][] ist die Adresse vom Board field[][]
    // bewegt Stein mit addierter Würfelzahl(sum)
    public void moveOneStone(int field[][], int sum, Player opponentPlayer,int stone) {
        stone=-1;
        boolean control = false;
        boolean playerColor = false;
        // Fuer test: zwei Steine belegen ein Feld
        //dice = 1;


        // Eingabe welcher Stein bewegt werden soll
        if(rules.haveYouStonesOut(gameBoardEdge))
        {
            for(int i=0; i< gameBoardEdge.length; i++)
            {
                if (gameBoardEdge[i]!=0){
                    stone=gameBoardEdge[i];
                    System.out.println("Stone:" + stone);
                }
            }
        }else
        {
            System.out.println("Welchen Stein moechte " + name + " bewegen?:");

            // Ermöglicht und überprüft (Integer) Eingabe
            stone = rules.validIntegerInput();
        }
        playerColor = rules.isStoneYours(isBlack, stone);
        System.out.println("Stone:" + stone);

        // Bewege richtigen Stein (Also Positive Steine oder negative Steine)
        if(playerColor && !isBlack)
        {
            //Suche den Stein im Feld
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control ; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone) {
                        //Pruefe sind zwei Gegner Steine auf dem neuen Feld sind
                        if( rules.isAccessibile(isBlack, i+sum, field, opponentPlayer.gameBoardEdge)) {
                            // Stein in neues Feld schreiben.
                            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                                if (field[i + sum][counterFreeField] == 0 && !control) {
                                    // Bewege Negativen Stein
                                    field[i + sum][counterFreeField] = (int) stone;
                                    // Loesche Stein aus altem Feld
                                    field[i][j] = 0;
                                    control = true;
                                }
                            }
                        }
                        else {
                            moveOneStone(field,sum, opponentPlayer,stone);
                        }
                    }
                    else if(rules.haveYouStonesOut(gameBoardEdge))
                    {
                        for(int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                            if (field[-1 + sum][counterFreeField] == 0 && !control) {
                                // Bewege Negativen Stein
                                field[-1 + sum][counterFreeField] = (int) stone;
                                // Loesche Stein aus altem Feld
                                //field[i][j] = 0;
                                control = true;
                            }
                        }
                    }
                }
            }
        }
        else if(playerColor && isBlack )
        {
            for(int i=0; i < 24;i++)
            {
                for(int j=0; j < 5 && !control;j++)
                {
                    //Pruefe ob Stein gefunden
                    if(field[i][j] == stone)
                    {
                        if(rules.isAccessibile(isBlack, i-sum, field, opponentPlayer.gameBoardEdge)) {
                            // Stein in neues Feld schreiben.
                            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                                if (field[i - sum][counterFreeField] == 0 && !control) {
                                    // Bewege Postiven Stein
                                    field[i - sum][counterFreeField] = (int) stone;
                                    // Loesche Stein aus altem Feld
                                    field[i][j] = 0;
                                    control = true;
                                }
                            }
                        }
                        else {
                            moveOneStone(field, sum, opponentPlayer,stone);
                        }
                    }
                    else if(rules.haveYouStonesOut(gameBoardEdge))
                    {
                        for(int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                            if (field[24 - sum][counterFreeField] == 0 && !control) {
                                // Bewege Negativen Stein
                                field[24 - sum][counterFreeField] = (int) stone;
                                control = true;
                            }
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Bitte waehle ein Stein der dir gehoert!");
            moveOneStone(field,sum, opponentPlayer,stone);
        }
        System.out.println("Letzte Print");
    }
    //Methode mit zwei einzelnen Steinen laufen
    public void moveTwoStones(int field[][],int dice1,int dice2,Player opponentPlayer) {
        int stone1 = -1;
        int stone2 = -1;


        // Move the first stone with dice1
        moveOneStone(field, dice1, opponentPlayer, stone1);

        // Move the second stone with dice2
        moveOneStone(field, dice2, opponentPlayer, stone2);

        System.out.println("Letzte Print");
    }

    public void printgameBoard()
    {
        System.out.println("Game Board von Spieler: " + name);
        for (int i = 0; i < gameBoardEdge.length; i++) {
            System.out.printf(gameBoardEdge[i] + ", ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\nAmount of Stones: " + getStones() + "\nColor is Black: " + isBlack();
    }

    // Spieler mit der höheren Augenzahl beginnt das Spiel
    public Player startPlayer(Player playerOne, Player playerTwo, Dice diceOne, Dice diceTwo) {

        int dicePlayerOne;
        int dicePlayerTwo;

        playerOne.rollDice(diceOne, diceTwo);
        dicePlayerOne = playerOne.diceNumber1;
        System.out.println("Spieler 1 " +playerOne.getName() + " Gewürfelt: " + dicePlayerOne);

        playerTwo.rollDice(diceOne,diceTwo);
        dicePlayerTwo = playerTwo.diceNumber1;
        System.out.println("Spieler 2 " +playerTwo.getName()+ " Gewürfelt " + dicePlayerTwo);

        if (dicePlayerOne > dicePlayerTwo) {
            return playerOne;
        }
        else if (dicePlayerOne < dicePlayerTwo) {
            return playerTwo;
        }
        else // Wenn unentschieden
            System.out.println("Unentschieden. Es wird nochmal gewürfelt");
            return startPlayer(playerOne, playerTwo, diceOne, diceTwo);
    }

    public void enterPlayerName() {
        System.out.println("Gebe einen Namen an: ");
        name = rules.validStringInput();
        System.out.println("Name Spieler: " + name);
    }

    public boolean enterPlayerColor() {
        System.out.println("Wähle zwischen Schwarz oder Weiß: ");
        return rules.validColorInput();
    }

    public void moveStoneOutOfBoard(boolean permittedRemoveStone, int stoneNumber, Board board) {
        try {


        } catch (ArrayIndexOutOfBoundsException b) {
            if (permittedRemoveStone = true) {
                outOfBoard.add(stoneNumber);
                board.adjustReverenceSet(stoneNumber);
            } else {
                System.out.println("Stein darf nicht abgebaut werden. Wähle einen anderen Stein.");
                moveStone();
            }
        }
    }

}
