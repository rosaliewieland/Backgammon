package backgammon.game.screens;

import backgammon.game.Backgammon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen{
    int i;
    //nehme Backgammon Klasse
    Backgammon game;
    //Hinzufüge Texturen
    private Texture background;
    private Button startbutton;
    private Button setbutton;
    public final static int GAME = 0;
    public final static int SETTING = 1;
    private SettingsMenu SetMenu;
    private OrthographicCamera menucam;
    private Viewport menuport;
    Stage stage;
    Table table = new Table();

    public MainMenu(Backgammon game) {
        this.game = game;
        background = new Texture("MainMenu.png");

        startbutton.texture = new Texture("PlayButton.png");
        startbutton.value = 0;

        setbutton.texture = new Texture("SettingsButton.png");
        setbutton.value = 1;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
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
        game.batch.dispose();
    }
    public void changeScreen(int screen){
        switch(screen){
            case GAME:
                /*if(menuScreen == null) menuScreen = new MenuScreen();
                this.setScreen(menuScreen);*/
                break;
            case SETTING:
                if(SetMenu == null) SetMenu = new SettingsMenu(game);
                game.setScreen(SetMenu);
                break;
        }
    }
}

