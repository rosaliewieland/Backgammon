package basic_backend;
public class Player {
    private String name;
    private int stone;
    private boolean isBlack;

    private int dice1;
    private int dice2;

    // Konstruktor
    public Player() {

    }

    public Player(String name, int stone, boolean isBlack) {
        this.name = name;
        this.stone = stone;
        this.isBlack = isBlack;
    }


    // Getter
    public String getName(){
        return name;
    }
    public int getStones() {
        return stone;
    }
    public boolean isBlack() {
        return isBlack;
    }
    public int getDice1() {
        return dice1;
    }
    public int getDice2() {
        return dice2;
    }


    // Setter
    public void setStone(int stone) {
        this.stone = stone;
    }
    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }
    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\nAmount of Stones: " + getStones() + "\nColor is Black: " + isBlack();
    }
}
