package backgammon.game.basic_backend;

import java.util.Set;
import java.util.HashSet;

public class Board {

    private final int[][] field = new int[24][5];

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
   //     field[0][0] = -1;
   //     field[0][1] = -2;
       /* field[19][2] = -3;
        field[19][3] = -4;
        field[19][4] = -5;
        field[22][0] = -6;
        field[22][1] = -7;
        field[22][2] = -8;
        field[20][2] = -9;
        field[21][3] = -10;*/



        field[0][0] = -1;
        field[0][1] = -2;

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
        // field[23][0] = -1;
        // field[23][1] =- 2;
        field[23][0] = 1;
        field[23][1] = 2;
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
    }


    // Erstellt Set mit denen die Homebasis der Spieler verglichen wird. Set von 1, ..., 15 beziehungsweise auch mit Minus
    public void createReverenceSet(){
        for (int i = 1; i < 16; i++) {
            reverenceSetBlack.add(i);
        }
        for (int i = -1; i > -16; i--) {
            reverenceSetWhite.add(i);

        }
    }


    public void adjustReverenceSet(int stoneToRemove){
        if (stoneToRemove > 0) {
            this.reverenceSetBlack.remove(stoneToRemove);
            this.homeFieldBlack.remove(stoneToRemove);
        }
        else {
            this.reverenceSetWhite.remove(stoneToRemove);
           this.homeFieldWhite.remove(stoneToRemove);
        }
    }


    // Vergleicht die Reverenz-Sets mit den erstellten Sets von der Homebasis des Spielers. Sollten die Sets gleich sein
    // → alle Steine des Spielers sind in der Basis wird, erlaubt die Steine abbauen zu können.
    public void compareSetsEnableRemoveStones() {
        this.blackPermittedRemoveStones = homeFieldBlack.equals(reverenceSetBlack);
        this.whitePermittedRemoveStones = homeFieldWhite.equals(reverenceSetWhite);
    }

    public Set<Integer> getHomeFieldWhite() {
        return homeFieldWhite;
    }

    // Getter-Methode
    public boolean isBlackPermittedRemoveStones() {
        return blackPermittedRemoveStones;
    }

    public Set<Integer> getReverenceSetBlack() {
        return reverenceSetBlack;
    }

    public Set<Integer> getHomeFieldBlack() {
        return homeFieldBlack;
    }

    public Set<Integer> getReverenceSetWhite() {
        return reverenceSetWhite;
    }

    public boolean isWhitePermittedRemoveStones() {
        return whitePermittedRemoveStones;
    }

}
