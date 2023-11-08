package basic_backend;

public class PrintBoard {

    public static void printBoard(int field[][]) {


        //Kontrolle um das print feld aus richtig ist
/*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 24; j++) {
                field[j][i] = j;
            }
        }
*/
        for(int i=0; i<50; i++)
            System.out.printf("-");
        System.out.println();
        // Gibt das Spielfeld auf der Konsole aus
        for(int i = 0; i < 5; i++) {
            for (int j = 5; j >= 0; j--) {
                System.out.print(field[j+6][i] + "\t");
            }
            System.out.print("|\t"); // Teilt die Spielseite in einen 6er Block
            for (int j2 = 5; j2 >= 0; j2--) {
                System.out.print(field[j2][i] + "\t");
            }
            System.out.println();
        }
        for(int i=0; i<50; i++)
            System.out.printf("-"); // Trennlinie die beiden Spielhälften in zwei Teile teilt

        System.out.println();
        for (int a = 4; a >= 0; a--) {
            for (int k = 0; k < 6; k++) {
                System.out.print(field[k + 12][a] + "\t");
            }
            System.out.print("|\t"); // Teilt die Spielseite in einen 6er Block
            for (int k2 = 0; k2 < 6; k2++) {
                System.out.print(field[k2 + 18][a] + "\t");
            }
            System.out.println();
        }
        for(int i=0; i<50; i++)
            System.out.printf("-");
        System.out.println();
    }
}