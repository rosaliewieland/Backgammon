package basic_backend;

// Folgende Regelen sind implementiert:
// - Es wird in isStoneYours(...) geprueft, ob der gewaehlte Stein dem Spieler gehoert!
// - Es wird in isAccessibile(...) geprueft, ob sich
//   schon zwei Gegenersteine auf dem neuen Feld sich befinden.
// - Wir brauchen aktuell (07.11.2023) KEINE Regel die prueft, ob
//   ein Feld schon voll ist auf das man seinen Stein bewegen moechte.
//   -> Dies ist schon in der moveStone(...) direkt implementiert.

public class Rules{
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
        for(int i=0; i<2; i++)
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
        for(int i = 0; i<2; i++)
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

    public boolean haveYouStonesOut(int[] gameBoardEdge)
    {
        for(int i=0; i<gameBoardEdge.length; i++)
        {
            if(gameBoardEdge[i] != 0)
            {
                return true;
            }
        }
        return false;
    }


}