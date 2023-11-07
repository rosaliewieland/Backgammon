package basic_backend;

import java.util.Scanner;

public class Player {
    private String name;
    private boolean isBlack;
    private int stone = 15;

    private int diceNumber1;
    private int diceNumber2;

    // Konstruktor
    public Player() {

    }

    public Player(String name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
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


    // Bewegt aktuell einen Stein
    public void moveStone(int field[][], int dice) {
        int stone;
        boolean control = false;

        // Eingabe welcher Stein bewegt werden soll
        System.out.println("Welchen Stein moechtest du bewegen?");
        Scanner scanner = new Scanner(System.in);
        stone = scanner.nextInt();
        System.out.println("Stone:" + stone);
        //Suche den Stein im Feld
        for(int i=0; i < 24;i++)
        {
            for(int j=0; j < 5;j++)
            {
                //Pruefe ob Stein gefunden
                if(field[i][j] == stone)
                {


                    // Stein in neues Feld schreiben.
                    for(int counterFreeField = 0; counterFreeField<5; counterFreeField++)
                    {
                        if(field[i+dice][counterFreeField] == 0 && control == false)
                        {
                            field[i+dice][counterFreeField] = (int)stone;
                            // Loesche Stein aus altem Feld
                            field[i][j] = 0;
                            control = true;
                        }
                    }
                }
            }
        }

    }


    @Override
    public String toString() {
        return "Name: " + getName() + "\nAmount of Stones: " + getStones() + "\nColor is Black: " + isBlack();
    }

}
