package backgammon.game.basic_frontend;

import backgammon.game.basic_backend.Board;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class HelperClass {
    public static void removeStoneId(int id, int[][]field, ArrayList<ArrayList<Label> >alb)
    {

        for(int i=0; i<24;i++)
        {
            for(int j=0;j<5;j++)
            {
                if(Integer.parseInt(String.valueOf(alb.get(i).get(j).getText())) == id)
                {
                    System.out.println("Stoneid Remove: "+ id);
                    alb.get(i).get(j).setText("0");

                    field[i][j] = 0;
                }
            }
        }

    }

    public static void startStoneField(int i, int j, Label.LabelStyle labelStyle, Board board, ArrayList<ArrayList<Label> >alb)
    {
        int[][] field = board.getField();
        Label label;
        if(field[i][j] != 0)
        {
            label = new Label("" + field[i][j], labelStyle);
        }
        else
        {
            label = new Label("0", labelStyle);
        }
        alb.get(i).add(j,label);
    }

    public static int checkGameField(int[][] field, int stoneId)
    {
        for(int i=0;i<24;i++)
        {
            for(int j=0; j<5;j++)
            {
                if(field[i][j] == stoneId)
                {
                    return i;
                }
            }
        }
        return -100;
    }
    public static int getDifferenz(ArrayList<ArrayList<Label> >alb, int newPositon, boolean isBlack, int stoneId)
    {
        for(int i=0;i<24;i++)
        {
            for(int j=0; j<5;j++)
            {
                if(Integer.parseInt(String.valueOf(alb.get(i).get(j).getText()))== stoneId)
                {
                        return abs(newPositon-i);

                   // System.out.println("Stein liegt an Stelle: i:"+ i + " j:" + j + "Stein: " + Integer.parseInt(String.valueOf(alb.get(i).get(j).getText())));
                }
            }
        }
        if(isBlack)
        {
            return abs(24-newPositon);
        }
        return abs(newPositon+1);
    }
}
