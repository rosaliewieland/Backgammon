package basic_backend;

// Folgende Regelen sind implementiert:
// - Es wird in isStoneYours(...) geprueft, ob der gewaehlte Stein dem Spieler gehoert!
// - Es wird in isAccessibile(...) geprueft, ob sich
//   schon zwei Gegenersteine auf dem neuen Feld sich befinden.
// - Wir brauchen aktuell (07.11.2023) KEINE Regel die prueft, ob
//   ein Feld schon voll ist auf das man seinen Stein bewegen moechte.
//   -> Dies ist schon in der moveStone(...) direkt implementiert.

import java.util.InputMismatchException;
import java.util.Scanner;

public class Rules{

    Scanner scanner = new Scanner(System.in);
    public boolean isStoneYours(boolean isBlack, int movingStone)
    {
        if(movingStone>0 && isBlack)
        {
            return true;
        }
        else if(movingStone < 0 && !isBlack)
        {
            return true;
        }
        return false;
    }
    public boolean isAccessibile(boolean isBlack, int newPosition, int[][] field, int[] gameBoardEdge)
    {
        /*int index=0;
        boolean control = false;*/
        int counterStone = 0;
        int foundedStone = 0;

        for(int i=0; i<5; i++) {
            if(field[newPosition][i] != 0) { // wenn ein Stein im Feld gefunden wurde
                foundedStone = field[newPosition][i]; //speichert den Stein in foundedStone
                counterStone += 1;
            }
        }

        if (counterStone == 0) { // der counterStone ist null, wenn kein Stein im Feld gefunden wurde
            return true;
        }
        else if (counterStone > 0) { // counterStone ist größer als null, wenn mindestens ein Stein gefunden wurde
            if (foundedStone <0 && isBlack || foundedStone >0 && !isBlack ) { // wenn foundedStone ein Gegnerstein ist
                if (counterStone == 1) { //wenn nur ein Gegnerstein im Feld liegt, wird dieser geschlagen
                    hitStone(field, field[newPosition][0], gameBoardEdge, newPosition);
                }
                else { //wenn mehr als ein Gegnerstein im Feld ist
                    System.out.println("Feld ist von Gegnersteinen belegt");
                    return false;
                }
            }
            else { //wenn im Feld ein eigener Stein liegt
                if(counterStone == 5) { //wenn fünf eigene Steine da liegen
                    System.out.println("Feld ist mit den eigenen Steinen belegt");
                    return false;
                }
            }

        }
        return true;


    }
    public void hitStone(int[][] field, int killStone, int[] gameBoardEdge, int newPosition)
    {
            for(int i=0; i < gameBoardEdge.length; i++)
            {
                if(gameBoardEdge[i] == 0)
                {
                    gameBoardEdge[i] = killStone;
                    //System.out.printf("GameBoardEdge: " + gameBoardEdge[i]);
                    field[newPosition][0] = 0;
                    return;
                }
            }
    }

    public boolean haveYouStonesOut(int[] gameBar)
    {
        for(int i=0; i<gameBar.length; i++)
        {
            if(gameBar[i] != 0)
            {
                return true;
            }
        }
        return false;
    }


    public int validIntegerInput() {
        while (true) {
            try {
                return scanner.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Bitte nur Zahlen eingeben: ");
            }
        }

    }

    public String validStringInput() {
        while (true) {
            try {
                String stringInput = scanner.nextLine();
                if (stringInput.matches("[a-zA-Z]+")) {
                    return stringInput;
                } else {
                    System.out.println("Bitte nur Buchstaben eingeben: ");
                    validStringInput();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Fehler beim Einlesen. Bitte noch mal versuchen: ");
            }
        }
    }

    public boolean validColorInput() {
        while (true) {
            try {
                String color = scanner.nextLine();

                if (color.equalsIgnoreCase("Schwarz")) {
                    return true;
                } else if (color.equalsIgnoreCase("Weiß")) {
                    return false;
                } else {
                    System.out.println("Bitte nur Schwarz oder Weiß eingeben: ");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Fehler beim Einlesen. Bitte noch mal versuchen: ");
            }
        }
    }

    public boolean isOutOfBound(int diceNumber, int positionOfStone) {
        boolean inBound = true;
        if (positionOfStone + diceNumber > 24){
            inBound = false;
            return inBound;
        }
        else if (positionOfStone - diceNumber < 0){
            inBound = false;
            return inBound;
        } else {
            return inBound;
        }
    }

    /*public boolean CheckConditionWhite(int[][] field){
        int counter = 0;
        int Amount;
        Player WhitePlayer = new Player();
        Amount = WhitePlayer.getStones() ;
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (field[i][j] > 0){
                    counter++;
                }
            }
        }
        if (counter == Amount){
            return true;
        }
        else if (counter > Amount) {
            System.out.println("Error to read Checker");
            return false;
        }
        else return false;
    }
    public boolean CheckConditionBlack(int[][] field){
        int counter = 0;
        int amount;
        Player whitePlayer = new Player();
        amount = whitePlayer.getStones() ;
        for(int i = 18; i < 24; i++){
            for (int j = 0; j < 5; j++){
                if (field[i][j] < 0){
                    counter++;
                }
            }
        }
        if (counter == amount){
            return true;
        }
        else if (counter > amount) {
            System.out.println("Error to read Checker");
            return false;
        }
        else return false;
    }
     */

    // Checkt die Möglichkeit ob der Spieler den Stein außerhalb des Feldes bewegen möchte.



}