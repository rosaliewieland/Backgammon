package basic_backend;

import java.util.HashSet;
import java.util.Set;

public class Player{

    private String name;
    private boolean isBlack;
    private int stone = 15;

    private int diceNumber1;
    private int diceNumber2;

    private Rules rules= new Rules();
    private int[] gameBar = new int[10];

    private Set<Integer> outOfBoard = new HashSet<>();


    // Konstruktor
    public Player() {

    }

    public Player(boolean isBlack) {
        enterPlayerName();
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

    /*
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
    }*/

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
            moveOneStone(field, dice1 + dice2, opponentPlayer);
        } else if (choice == 2) {
            moveTwoStones(field, dice1, dice2, opponentPlayer);
        } else {
            System.out.println("Ungültige Eingabe. Bitte wähle 1 oder 2.");
            moveStone(field, dice1, dice2, opponentPlayer); // Recursive call to handle invalid input
        }
    }




    public void moveOneStone(int field[][], int sum, Player opponentPlayer) {
        boolean playerColor = false;
        boolean accessMove = false;
        // Fuer test: zwei Steine belegen ein Feld
        //dice = 1;
        //sum = 1;
        while(!accessMove)
        {
            if(rules.haveYouStonesOut(gameBar))
            {
                // Setze den Stein aus der Game Bar
                gameBarIsNotEmpty();
            }
            else
            {
                System.out.println("Welchen Stein moechte " + name + " bewegen?:");
                // Ermöglicht und überprüft (Integer) Eingabe
                this.stone = rules.validIntegerInput();
            }
            // Eingabe welcher Stein bewegt werden soll
            playerColor = rules.isStoneYours(isBlack, this.stone);
            // Bewege richtigen Stein (Also Positive Steine oder negative Steine)
            if(playerColor && !isBlack)
            {
                //Suche den Stein im Feld
                accessMove = findStone(field, false, 0, 0, opponentPlayer, sum);
            }
            else if(playerColor && isBlack )
            {
                accessMove = findStone(field,  false, 0 , 0, opponentPlayer, sum);
            }
            else
            {
                System.out.println("Bitte waehle ein Stein der dir gehoert!");
            }
        }
    }
    //Methode mit zwei einzelnen Steinen laufen
    public void moveTwoStones(int field[][],int dice1,int dice2,Player opponentPlayer) {
        // Move the first stone with dice1
        moveOneStone(field, dice1, opponentPlayer);

        // Move the second stone with dice2
        moveOneStone(field, dice2, opponentPlayer);

    }


    public boolean findStone(int[][] field, boolean isStoneAlreadyFound, int indexI, int indexJ, Player opponentPlayer ,int sum ) {
        boolean control = false;
        // HIER AM FREITAG DARUEBER SPRECHEN MACHT KEINEN SINN.....
        if(isStoneAlreadyFound) {
            if (rules.isAccessibile(isBlack, 0, field, opponentPlayer.gameBar)) {
                searchFreeField(field, indexI);
            }
        }
        //
        if(!isBlack) {
            if (rules.haveYouStonesOut(gameBar)) {
                System.out.println("Bringe Stein " + stone + " wieder ins Spiel " + "Wuefel: "+ sum);
                setGameBarStone(field, sum);
                return true;
            }
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone) {
                        if (rules.isAccessibile(isBlack, i + sum, field, opponentPlayer.gameBar)) {
                            // Stein in neues Feld schreiben.
                            searchFreeField(field, i + sum);
                            field[i][j] = 0;
                            control = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }else if(isBlack)
        {
            if (rules.haveYouStonesOut(gameBar)) {
                setGameBarStone(field, sum);
                System.out.println("Bringe Stein " + stone + " wieder ins Spiel");
                return true;
            }
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone) {
                        if (rules.isAccessibile(isBlack, i - sum, field, opponentPlayer.gameBar)) {
                            // Stein in neues Feld schreiben.
                            searchFreeField(field, i - sum);
                            field[i][j] = 0;
                            control = true;
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
                stone= gameBar[i];
                System.out.println("Stone:" + stone);
            }
        }
    }
    public void setGameBarStone(int[][] field, int newIndex)
    {
        boolean control = false;
        if(!isBlack)
        {
            //if(rules.isAccessibile(isBlack, field, -1+newIndex))
            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {

                if (field[-1 + newIndex][counterFreeField] == 0 && !control) {
                    // Bewege Negativen Stein
                    field[-1 + newIndex][counterFreeField] = (int) stone;
                    control = true;
                    gameBar[0]=0;
                }
            }
        }
        else if(isBlack)
        {
            for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
                if (field[24 - newIndex][counterFreeField] == 0 && !control) {
                    // Bewege Negativen Stein
                    field[24 - newIndex][counterFreeField] = (int) stone;
                    control = true;
                    gameBar[0]=0;
                }
            }
        }
    }
    public void searchFreeField(int[][] field, int indexI)
    {
        boolean control = false;
        for (int counterFreeField = 0; counterFreeField < 5; counterFreeField++) {
            if (field[indexI][counterFreeField] == 0 && !control) {
                // Bewege Postiven Stein
                field[indexI][counterFreeField] = (int) stone;
                // Loesche Stein aus altem Feld
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
        return "Name: " + getName() + "\nAmount of Stones: " + getStones() + "\nColor is Black: " + isBlack();
    }
    /*
    // Bewegt aktuell einen Stein (int field[][] ist die Adresse vom Board field[][]
    // bewegt Stein mit addierter Würfelzahl(sum)
    public void OLDmoveOneStone(int field[][], int sum, Player opponentPlayer,int stone) {
        stone=-1;
        boolean control = false;
        boolean playerColor = false;
        // Fuer test: zwei Steine belegen ein Feld
        //dice = 1;


        // Eingabe welcher Stein bewegt werden soll
        if(rules.haveYouStonesOut(gameBar))
        {
            isgameBarEmpty();
        }
        else
        {
            System.out.println("Welchen Stein moechte " + name + " bewegen?:");
            // Ermöglicht und überprüft (Integer) Eingabe
            stone = rules.validIntegerInput();
        }

        playerColor = rules.isStoneYours(isBlack, stone);


        // Bewege richtigen Stein (Also Positive Steine oder negative Steine)
        if(playerColor && !isBlack)
        {
            //Suche den Stein im Feld
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 5 && !control ; j++) {
                    //Pruefe ob Stein gefunden
                    if (field[i][j] == stone) {
                        //Pruefe sind zwei Gegner Steine auf dem neuen Feld sind
                        if( rules.isAccessibile(isBlack, i+sum, field, opponentPlayer.gameBar)) {
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
                            //moveOneStone(field,sum, opponentPlayer,stone);
                        }
                    }
                    else if(rules.haveYouStonesOut(gameBar))
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
                        if(rules.isAccessibile(isBlack, i-sum, field, opponentPlayer.gameBar)) {
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
                            //moveOneStone(field, sum, opponentPlayer,stone);
                        }
                    }
                    else if(rules.haveYouStonesOut(gameBar))
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
            //moveOneStone(field,sum, opponentPlayer,stone);
        }
        System.out.println("Letzte Print");
    }

    */
}
