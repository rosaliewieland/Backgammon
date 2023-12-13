package backgammon.game.basic_frontend;

import backgammon.game.basic_backend.Dice;
import backgammon.game.basic_backend.Player;

public class Integration {
    private Player player1;
    private Player player2;

    Dice dice1;

    Dice dice2;

    Integration()
    {
        player1 = new Player();
        player2 = new Player();
        dice1 = new Dice();
        dice2 = new Dice();
    }

    public void printGameBar(Player p)
    {
        p.printGameBar();
    }
    public void rollDice(Player p)
    {
        p.rollDice(dice1, dice2);
    }





}
