package basic_backend;

import java.util.Scanner;

public class Player{

    private String name;
    private boolean isBlack;
    private int stone = 15;

    private int diceNumber1;
    private int diceNumber2;

    private Rules rules= new Rules();
    private int[] gameBoardEdge = new int[10];

    // Konstruktor
    public Player() {

    }

    public Player(String name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
        for(int i=0; i<gameBoardEdge.length; i++)
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



    // Bewegt aktuell einen Stein (int field[][] ist die Adresse vom Board field[][]
    public void moveStone(int field[][], int dice, Player opponentPlayer) {
        int stone=-1;
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
            System.out.println("Welchen Stein moechte "+ name + " bewegen?:");
            Scanner scanner = new Scanner(System.in);
            stone = scanner.nextInt();

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
                        if( rules.isAccessibile(isBlack, i+dice, field, opponentPlayer.gameBoardEdge)) {
                            // Stein in neues Feld schreiben.
                            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                                if (field[i + dice][counterFreeField] == 0 && !control) {
                                    // Bewege Negativen Stein
                                    field[i + dice][counterFreeField] = (int) stone;
                                    // Loesche Stein aus altem Feld
                                    field[i][j] = 0;
                                    control = true;
                                }
                            }
                        }
                        else {
                            moveStone(field, dice, opponentPlayer);
                        }
                    }
                    else if(rules.haveYouStonesOut(gameBoardEdge))
                    {
                        for(int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                            if (field[-1 + dice][counterFreeField] == 0 && !control) {
                                // Bewege Negativen Stein
                                field[-1 + dice][counterFreeField] = (int) stone;
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
                        if(rules.isAccessibile(isBlack, i-dice, field, opponentPlayer.gameBoardEdge)) {
                            // Stein in neues Feld schreiben.
                            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                                if (field[i - dice][counterFreeField] == 0 && !control) {
                                    // Bewege Postiven Stein
                                    field[i - dice][counterFreeField] = (int) stone;
                                    // Loesche Stein aus altem Feld
                                    field[i][j] = 0;
                                    control = true;
                                }
                            }
                        }
                        else {
                            moveStone(field, dice, opponentPlayer);
                        }
                    }

                    else if(rules.haveYouStonesOut(gameBoardEdge))
                    {
                        for(int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                            if (field[24 - dice][counterFreeField] == 0 && !control) {
                                // Bewege Negativen Stein
                                field[24 - dice][counterFreeField] = (int) stone;
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
            moveStone(field,dice, opponentPlayer);
        }
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
        System.out.println("Spieler 2 " +playerTwo.getName()+ " Gewürfelt: " + dicePlayerTwo);

        if (dicePlayerOne > dicePlayerTwo) {
            return playerOne;
        }
        else if (dicePlayerOne < dicePlayerTwo) {
            return playerTwo;
        }
        else // Wenn unentschieden, es wird noch mal gewürfelt
            System.out.println("Unentschieden. Es wird nochmal gewürfelt");
            return startPlayer(playerOne, playerTwo, diceOne, diceTwo);
    }

}
