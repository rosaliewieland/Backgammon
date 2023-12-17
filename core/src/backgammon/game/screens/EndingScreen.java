package backgammon.game.screens;

import backgammon.game.Backgammon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class EndingScreen implements Screen {
    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_X = 10;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_X = 1000;
    private static final int BUTTON_Y = 100;

    Backgammon game;
    Texture winner;
    Texture againbutton;
    Texture returnbutton;
    public EndingScreen(final Backgammon game) {
        this.game = game;
        winner = new Texture("WhiteWon!.png");
        againbutton = new Texture("AgainButton.png");
        returnbutton= new Texture("ReturnButton.png");
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Exit button
                if (screenX < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH && screenX > EXIT_BUTTON_X && Backgammon.WORLD_HEIGHT - screenY < BUTTON_Y + EXIT_BUTTON_HEIGHT && Backgammon.WORLD_HEIGHT - screenY > BUTTON_Y) {
                    Gdx.app.exit();
                }

                //Play game button
                if (screenX < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH && screenX > PLAY_BUTTON_X && Backgammon.WORLD_HEIGHT - screenY < BUTTON_Y + PLAY_BUTTON_HEIGHT && Backgammon.WORLD_HEIGHT - screenY > BUTTON_Y) {
                    game.setScreen(new MainMenu());
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
        game.batch.draw(winner, game.WORLD_WIDTH/2-280, game.WORLD_HEIGHT/2);
        game.batch.draw(returnbutton, EXIT_BUTTON_X, BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        game.batch.draw(againbutton, PLAY_BUTTON_X, BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
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