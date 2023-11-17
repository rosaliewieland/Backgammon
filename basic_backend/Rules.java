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
    public boolean isAccessibile(boolean isblack, int newPosition, int[][] field, int[] gameBoardEdge)
    {
        int index=0;
        boolean control = false;
        for(int i = 0; i < 5; i++)
        {
            if(field[newPosition][i] < 0 && isblack)
            {
                if(control)
                {
                    System.out.println("Feld ist belegt");
                    return false;
                }
                control = true;
            }
        }
        for(int i = 0; i<5 ; i++)
        {
            if(field[newPosition][i]  > 0 && !isblack)
            {
                if(control)
                {
                    System.out.println("Feld ist belegt");
                    return false;
                }
                control = true;

            }
        }
        if(control)
        {
            hitStone(field, field[newPosition][0], gameBoardEdge, newPosition);
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
                return scanner.next();
            }
            catch (InputMismatchException e) {
                System.out.println("Bitte nur Buchstaben eingeben: ");
            }
        }
    }

    public boolean validColorInput() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String color = scanner.next();
                if (color.equalsIgnoreCase("Schwarz"))
                    return true;
                else if (color.equalsIgnoreCase("Weiß"))
                    return false;
            }
            catch (InputMismatchException e) {
                System.out.println("Bitte nur Buchstaben eingeben: ");
            }
        }
    }

    public boolean CheckConditionWhite(int[][] field){
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
        int Amount;
        Player WhitePlayer = new Player();
        Amount = WhitePlayer.getStones() ;
        for(int i = 18; i < 24; i++){
            for (int j = 0; j < 5; j++){
                if (field[i][j] < 0){
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

    // Checkt die Möglichkeit ob der Spieler den Stein außerhalb des Feldes bewegen möchte.
    public boolean checkForOutOfBound(int diceNumber, int positionOfStone) {
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


}