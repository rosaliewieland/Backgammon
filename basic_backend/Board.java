package basic_backend;

import java.util.Set;
import java.util.HashSet;

public class Board {

    private int[][] field = new int[24][5];

    // Test Feld um schneller meine Methoden zu prüfen.
    private int[][] testfield = new int[24][5];

    // boolean ob der Spieler anfangen darf die Steine abzubauen
    // true = darf abbauen
    // false = darf NICHT abbauen
    private boolean blackPermittedRemoveStones = false;
    private boolean whitePermittedRemoveStones = false;


    // Erzeugt ein hashset von der Homebasis des schwarzen Feldes. Set gewählt, da Steine eine eindeutige Nummerierung haben
    // und die 0 nur eineinziges mal gespeichert wird (Mengen).
    private Set<Integer> homeFieldBlack = new HashSet<Integer>();
    private Set<Integer> homeFieldWhite = new HashSet<Integer>();

    // Erzeugt ein Hashset von den Mengen die jeder Spieler braucht, um seine Steine abzubauen.
    private Set<Integer> neededNumbersToFinishGameBlack = new HashSet<Integer>();
    private Set<Integer> neededNumbersToFinishGameWhite = new HashSet<Integer>();


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


        // Testarry befüllt durch den Konstruktor
        /*
        testfield[0][0]=1;
        testfield[0][1]=2;
        testfield[0][2]=3;
        testfield[0][3]=4;
        testfield[0][4]=5;

        testfield[1][0]=6;
        testfield[1][1]=7;
        testfield[1][2]=8;
        testfield[1][3]=9;
        testfield[1][4]=10;

        testfield[2][0]=11;
        testfield[2][1]=12;
        testfield[2][2]=13;
        testfield[2][3]=14;
        testfield[2][4]=15;

        testfield[23][0]=-1;
        testfield[23][1]=-2;
        testfield[23][2]=-3;
        testfield[23][3]=-4;
        testfield[23][4]=-5;

        testfield[22][0]=-6;
        testfield[22][1]=-7;
        testfield[22][2]=-8;
        testfield[22][3]=-9;
        testfield[22][4]=-10;

        testfield[21][0]=-11;
        testfield[21][1]=-12;
        testfield[21][2]=-13;
        testfield[21][3]=-14;
        testfield[21][4]=-15;
         */
        testfield[5][0] = 11;
        testfield[5][1] = 12;
        testfield[5][2] = 13;
        testfield[5][3] = 14;
        testfield[5][4] = 15;
        testfield[3][0] = 10;
        testfield[3][1] = 9;
        testfield[3][2] = 8;
        testfield[3][3] = 7;
        testfield[3][4] = 6;
        testfield[2][0] = 5;
        testfield[2][1] = 4;
        testfield[2][2] = 3;
        testfield[2][3] = 2;
        testfield[2][4] = 1;
    }



    public int[][] getField() {
        return field;
    }



    // Methode läuft durch die beiden Homes der Spieler und erstellt das Hashset homeFieldBlack and homeFieldWhite
    //
    public void runHomeFieldsCreateSets() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                homeFieldBlack.add(testfield[i][j]);
            }
        }
        for (int i = 18; i < 24; i++) {
            for (int j = 0; j < 5; j++) {
                homeFieldWhite.add(testfield[i][j]);
            }
        }
        // Testausgabe
        // System.out.println(homeFieldBlack);
        // System.out.println(homeFieldWhite);
    }


    // Erstellt Set mit denen die Homebasis der Spieler verglichen wird.
    public void createReverenceSet(){
        for (int i = 0; i < 16; i++) {
            neededNumbersToFinishGameBlack.add(i);
        }
        for (int i = 0; i > -16; i--) {
            neededNumbersToFinishGameWhite.add(i);
        }
        // Testausgabe
        // System.out.println(neededNumbersToFinishGameBlack);
        // System.out.println(neededNumbersToFinishGameWhite);
    }


    // Vergleicht die Reverenz-Sets mit den erstellten Sets von der Homebasis des Spielers. Sollten die Sets gleich sein
    // -> alle Steine des Spielers sind in der Basis wird, erlaubt die Steine abbauen zu können.
    public void compareSetsEnableRemoveStones(){
        if (homeFieldBlack.equals(neededNumbersToFinishGameBlack)){
            this.blackPermittedRemoveStones = true;
            // Tesausgabe
            // System.out.println("Black");
        }
        if (homeFieldWhite.equals(neededNumbersToFinishGameWhite)) {
            this.whitePermittedRemoveStones = true;
            // Testausgabe
            // System.out.println("White");
        }
    }

    // Getter-Methode
    public boolean isBlackPermittedRemoveStones() {
        return blackPermittedRemoveStones;
    }

    public boolean isWhitePermittedRemoveStones() {
        return whitePermittedRemoveStones;
    }

}
