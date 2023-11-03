import basic_backend.Player;

public class Main {
    public static void main(String[] args) {
        Player p1 = new Player("Simon", 15, true);
        Player p2 = new Player("Anni", 15, false);

        System.out.println(p1.toString());
        System.out.println(p2.toString());
    }
}
