package backgammon.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu extends ScreenAdapter implements InputProcessor {

    public SpriteBatch batch;
    private ImageButton quitButton;
    private ImageButton startButton;
    private ImageButton settingsButton;


    private Texture background;
    private Texture startbutton = new Texture("PlayButton.png");
    private Texture settingsbutton = new Texture("SettingsButton.png");
    private Texture quitbutton = new Texture("QuitButton.png");
    private OrthographicCamera menucam;
    private Viewport menuport;


    public MainMenu() {
        batch = new SpriteBatch();
        background = new Texture("MainMenu.png");
        menucam = new OrthographicCamera();
        menuport = new ScreenViewport(menucam);
        windowQuitButton();
        windowSettingsButton();
        windowStartButton();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(menucam.combined);
        batch.begin();
        batch.draw(background, -600, -475);
        quitButton.draw(batch, 1);
        startButton.draw(batch,1);
        settingsButton.draw(batch,1);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        menuport.update(width,height);
        menucam.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        startbutton.dispose();
        settingsbutton.dispose();
        quitbutton.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }


    public void windowQuitButton(){
        TextureRegionDrawable quitButtonDrawable = new TextureRegionDrawable(new TextureRegion(quitbutton));
        quitButton = new ImageButton(quitButtonDrawable);
        quitButton.setPosition(25, -100);

        quitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });


    }    public void windowStartButton(){
        TextureRegionDrawable startButtonDrawable = new TextureRegionDrawable(new TextureRegion(startbutton));
        startButton = new ImageButton(startButtonDrawable);
        startButton.setPosition(-150, 45);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


    }    public void windowSettingsButton(){
        TextureRegionDrawable settingButtonDrawable = new TextureRegionDrawable(new TextureRegion(settingsbutton));
        settingsButton = new ImageButton(settingButtonDrawable);
        settingsButton.setPosition(-325, -100);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickListener", "Einstellungsbutton wurde geklickt. Koordinaten: (" + x + ", " + y + ")");

                Gdx.app.exit();
            }
        });
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            Gdx.app.log("ClickListener", "TouchDown auf dem Einstellungsbutton");
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

