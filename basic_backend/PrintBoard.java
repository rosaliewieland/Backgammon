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

        // Gibt das Spielfeld auf der Konsole aus
        for(int i = 4; i >= 0; i--) {
            for (int j = 5; j >= 0; j--) {
                System.out.print(field[5-j][i] + " ");
            }
            System.out.print("* "); // Teilt die Spielseite in einen 6er Block
            for (int j2 = 5; j2 >= 0; j2--) {
                System.out.print(field[j2][i] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------"); // Trennlinie die die beiden Spielhälften in zwei Teile teilt
        for (int a = 0; a < 5; a++) {
            for (int k = 0; k < 6; k++) {
                System.out.print(field[k + 12][a] + " ");
            }
            System.out.print("* "); // Teilt die Spielseite in einen 6er Block
            for (int k2 = 0; k2 < 6; k2++) {
                System.out.print(field[k2 + 18][a] + " ");
            }
            System.out.println();
        }
    }
}
