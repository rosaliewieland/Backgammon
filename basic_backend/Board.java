package basic_backend;

public class Board {
    private int stone;
    private int field[][] = new int[24][1];
    public Board(int stone){

        this.stone = stone;
        for(int i = 0; i < 24; i++)
        {
            for(int j=0; j<1; j++)
            {
                field[i][j] = 0;
            }
        }
        field[0][0] = 1;
    }
    public boolean moveStone(int eingabe, int prev){
        if(eingabe > 23)
        {
            System.out.println("Ausserhalb des Spielfeldes");
        }
        else {
            field[prev][0] = 0;
            field[eingabe][0] = 1;

        }
        if(field[23][0]==1)
        {
            return true;
        }
        return false;


    }
    public boolean finishGame()
    {
        return true;
    }
    public void printBoard()
    {

        for(int i = 0; i < 24; i++)
        {
            for(int j=0; j < 1; j++)
            {
                System.out.println("i: " + i + " Field: " + field[i][j]);
            }
        }

    }
}
