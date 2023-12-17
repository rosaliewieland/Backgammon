package backgammon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu extends ScreenAdapter {


    private Stage stage;
    private Viewport viewport;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton actionButton;
    private Table mainTable;
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera camera;
    private Sprite sprite;


    public MainMenu() {
        batch = new SpriteBatch();
        background = new Texture("MainMenu.png");
        sprite = new Sprite(background);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        camera.update();
        viewport = new ScreenViewport(camera);
    }


    @Override
    public void show() {
        stage = new Stage(viewport);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        mainTable.setDebug(true);

        addButton("PlayButton.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Nächster Bildschirm");
                ScreenHandler.INSTANCE.setScreen(new PlayerSettings());
            }
        });
        addButton("SettingsButton.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Settings wurden geöffnet!");
            }
        });
        addButton("QuitButton.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(sprite.getTexture(), 0, 0, sprite.getWidth(), sprite.getHeight());
        batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }

    private ImageButton addButton(String name) {
        myTexture = new Texture(Gdx.files.internal(name));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        actionButton = new ImageButton(myTexRegionDrawable);
        mainTable.add(actionButton).padBottom(-20).padRight(20);

        return actionButton;
    }



}