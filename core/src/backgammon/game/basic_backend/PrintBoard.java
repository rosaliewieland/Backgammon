package backgammon.game.basic_backend;

public class PrintBoard {

    private static final int PRINTLINE = 57;

    public static void printBoard(int[][] field) {
        for(int i=0; i<PRINTLINE; i++)
            System.out.printf("-");

        System.out.println();
        // Gibt das Spielfeld auf der Konsole aus

        for(int i = 0; i < 5; i++) {
            System.out.print("|\t");
            for (int j = 5; j >= 0; j--) {
                System.out.print(field[j+6][i] + "\t");
            }
            System.out.print("|\t"); // Teilt die Spielseite in einen 6er Block
            for (int j2 = 5; j2 >= 0; j2--) {
                System.out.print(field[j2][i] + "\t");
            }
            System.out.print("|\t");
            System.out.println();
        }
        for(int i=0; i<PRINTLINE; i++)
            System.out.printf("-"); // Trennlinie die beiden Spielhälften in zwei Teile teilt

        System.out.println();

        for (int a = 4; a >= 0; a--) {
            System.out.print("|\t");
            for (int k = 0; k < 6; k++) {
                System.out.print(field[k + 12][a] + "\t");
            }
            System.out.print("|\t"); // Teilt die Spielseite in einen 6er Block
            for (int k2 = 0; k2 < 6; k2++) {
                System.out.print(field[k2 + 18][a] + "\t");
            }
            System.out.print("|\t"); // Teilt die Spielseite in einen 6er Block
            System.out.println();
        }
        for(int i=0; i<PRINTLINE; i++)
            System.out.printf("-");

        System.out.println();
    }
}