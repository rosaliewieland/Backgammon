package basic_backend;

public class Board {

    private int field[][] = new int[24][5];


    public Board(){

        field[0][0] = -1;
        field[0][1] = -2;

        // Nur fuer Test zwei Steine belegen ein Feld
        //field[1][0] = 16;
        //field[2][1] = 2;
        //Ende Test

        field[5][0] = 11;
        field[5][1] = 12;
        field[5][2] = 13;
        field[5][3] = 14;
        field[5][4] = 15;

        field[7][0] = 8;
        field[7][1] = 9;
        field[7][2] = 10;

        field[11][0] = -3;
        field[11][1] = -4;
        field[11][2] = -5;
        field[11][3] = -6;
        field[11][4] = -7;

        field[12][0] = 3;
        field[12][1] = 4;
        field[12][2] = 5;
        field[12][3] = 6;
        field[12][4] = 7;

        field[16][0] = -8;
        field[16][1] = -9;
        field[16][2] = -10;

        field[18][0] = -11;
        field[18][1] = -12;
        field[18][2] = -13;
        field[18][3] = -14;
        field[18][4] = -15;

        //field[22][0] = -1;
        field[23][0] = 1;
        field[23][1] = 2;


    }





    public int[][] getField() {
        return field;
    }


    public void setField() {


    }



    /*

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
    */

}
