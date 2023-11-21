package basic_backend;

import java.util.Set;
import java.util.HashSet;

public class Board {

    private final int[][] field = new int[24][5];

    // Test Feld um schneller meine Methoden zu prüfen.
    // private final int[][] testfield = new int[24][5];

    // boolean ob der Spieler anfangen darf die Steine abzubauen
    // true = darf abbauen
    // false = darf NICHT abbauen
    private boolean blackPermittedRemoveStones = false;
    private boolean whitePermittedRemoveStones = false;


    // Erzeugt ein hashset von der Homebasis des schwarzen Feldes. Set gewählt, da Steine eine eindeutige Nummerierung haben
    // und die 0 nur eineinziges mal gespeichert wird (Mengen).
    private Set<Integer> homeFieldBlack = new HashSet<>();
    private Set<Integer> homeFieldWhite = new HashSet<>();

    // Erzeugt ein Hashset von den Mengen die jeder Spieler braucht, um seine Steine abzubauen.
    private Set<Integer> reverenceSetBlack = new HashSet<>();
    private Set<Integer> reverenceSetWhite = new HashSet<>();


    public Board(){
        field[0][0] = -1;
        field[0][1] = -2;

        // Nur fuer Test zwei Steine belegen ein Feld
        //field[1][0] = 16;
        //field[1][1] = 0;
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
        //field[22][0] = -16;
        field[23][0] = 1;
        field[23][1] = 2;

/*
        // Testarry befüllt durch den Konstruktor
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
    }


    public int[][] getField() {
        return field;
    }


    // Methode läuft durch die beiden Homes der Spieler und erstellt das Hashset homeFieldBlack and homeFieldWhite
    public void createsSetOfHomeField() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (field[i][j] > 0) {
                    homeFieldBlack.add(field[i][j]);
                }
            }
        }
        for (int i = 18; i < 24; i++) {
            for (int j = 0; j < 5; j++) {
                if (field[i][j] < 0 ) {
                    homeFieldWhite.add(field[i][j]);
                }
            }
        }
        // Testausgabe
        // System.out.println(homeFieldBlack);
        // System.out.println(homeFieldWhite);
    }


    // Erstellt Set mit denen die Homebasis der Spieler verglichen wird. Set von 1, ..., 15 beziehungsweise auch mit Minus
    public void createReverenceSet(){
        for (int i = 1; i < 16; i++) {
            reverenceSetBlack.add(i);
        }
        for (int i = -1; i > -16; i--) {
            reverenceSetWhite.add(i);
        }
        // Testausgabe
        // System.out.println(reverenceSetBlack);
        // System.out.println(reverenceSetWhite);
    }


    public void adjustReverenceSet(int stoneToRemove){ // (Anni) anpassen vom Referenzset
        if (stoneToRemove > 0) {
            this.reverenceSetBlack.remove(stoneToRemove);
        }
        else {
            this.reverenceSetWhite.remove(stoneToRemove);
        }
    }


    // Vergleicht die Reverenz-Sets mit den erstellten Sets von der Homebasis des Spielers. Sollten die Sets gleich sein
    // → alle Steine des Spielers sind in der Basis wird, erlaubt die Steine abbauen zu können.
    public void compareSetsEnableRemoveStones() {
        this.blackPermittedRemoveStones = homeFieldBlack.equals(reverenceSetBlack);
        this.whitePermittedRemoveStones = homeFieldWhite.equals(reverenceSetWhite);
    }

    // Getter-Methode
    public boolean isBlackPermittedRemoveStones() {
        return blackPermittedRemoveStones;
    }

    public boolean isWhitePermittedRemoveStones() {
        return whitePermittedRemoveStones;
    }

}
