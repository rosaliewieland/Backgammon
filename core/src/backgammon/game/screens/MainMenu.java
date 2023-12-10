package backgammon.game.screens;

import backgammon.game.Backgammon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.io.ObjectInputFilter;

public class MainMenu implements Screen{
    Backgammon game;

    private Texture background;
    private Texture startbutton;
    private Texture settingsbutton;
    private Texture quitbutton;
    private OrthographicCamera menucam;
    private Viewport menuport;
    public MainMenu(Backgammon game) {
        this.game = game;
        background = new Texture("MainMenu.png");
        startbutton = new Texture("PlayButton.png");
        settingsbutton = new Texture("SettingsButton.png");
        quitbutton = new Texture("QuitButton.png");
        menucam = new OrthographicCamera();
        menuport = new ScreenViewport(menucam);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(menucam.combined);
        game.batch.begin();
        game.batch.draw(background, -600, -475);
        game.batch.draw(startbutton, -150, 45);
        game.batch.draw(settingsbutton, -325, -100);
        game.batch.draw(quitbutton, 25, -100);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        menuport.update(width,height);
        menucam.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        background.dispose();
        startbutton.dispose();
        settingsbutton.dispose();
        quitbutton.dispose();
    }
}

