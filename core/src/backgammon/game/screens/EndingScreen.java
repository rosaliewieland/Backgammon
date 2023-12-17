package backgammon.game.screens;

import backgammon.game.Backgammon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class EndingScreen implements Screen {
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_X = 1000;
    private static final int EXIT_BUTTON_X = 150;
    private static final int BUTTON_Y = 150;
    Backgammon game;
    Texture winner;
    Texture againbutton;
    Texture returnbutton;
    Texture background;
    public EndingScreen(final Backgammon game, int x) {
        this.game = game;
        if(x == 0) {
            winner = new Texture("WhiteWon!.png");
        }
        if(x == 1) {
            winner = new Texture("BlackWon!.png");
        }
        background = new Texture("MainMenu.png");
        againbutton = new Texture("AgainButton.png");
        returnbutton= new Texture("ReturnButton.png");
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Again button
                if (screenX < PLAY_BUTTON_X + BUTTON_WIDTH && screenX > PLAY_BUTTON_X && Backgammon.WORLD_HEIGHT - screenY < BUTTON_Y + BUTTON_HEIGHT && Backgammon.WORLD_HEIGHT - screenY > BUTTON_Y) {
                    dispose();
                    ScreenHandler.INSTANCE.setScreen(new Backgammon());
                }

                //Menu button
                if (screenX < EXIT_BUTTON_X + BUTTON_WIDTH && screenX > EXIT_BUTTON_X && Backgammon.WORLD_HEIGHT - screenY < BUTTON_Y + BUTTON_HEIGHT && Backgammon.WORLD_HEIGHT - screenY > BUTTON_Y) {
                    dispose();
                    ScreenHandler.INSTANCE.setScreen(new MainMenu());
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1500, 1000);
        game.batch.draw(winner, game.WORLD_WIDTH/2-250, game.WORLD_HEIGHT/2);
        game.batch.draw(returnbutton, EXIT_BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(againbutton, PLAY_BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.end();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.batch.dispose();
    }
}