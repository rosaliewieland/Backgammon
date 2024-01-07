package backgammon.game.screens;

import backgammon.game.Backgammon;
import com.badlogic.gdx.Gdx;
import backgammon.game.basic_frontend.DiceManager;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayerSettings extends ScreenAdapter {
    DiceManager dice1;
    DiceManager dice2;
    Texture dicebutton;
    float stateTime;
    float stateTime2;
    private final int DICE_BUTTON_WIDTH = 70;
    private final int DICE_BUTTON_HEIGHT = 80;
    int dicenumber1;
    int dicenumber2;
    private Texture diceResultTexture;
    private Texture diceResultTexture2;
    private final int DICE1_BUTTON_X = 50;
    private final int DICE1_BUTTON_Y = 50;
    private final int DICE2_BUTTON_X = 50;
    private final int DICE2_BUTTON_Y = 120;


    private Stage stage;
    private Viewport viewport;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton actionButton;
    private Table mainTable;
    private SpriteBatch batch;



    public PlayerSettings() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(600, 400);
        stage = new Stage(viewport);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        mainTable.setDebug(true);

        addButton("BlackPlayer.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Comming soon. Please choose Multiplayer.");

            }
        });
        addButton("BothPlayers.png").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Multiplayer");
                ScreenHandler.INSTANCE.setScreen(new Backgammon());
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(194/255f, 157/255f, 146/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }


    private ImageButton addButton(String namePNG) {
        myTexture = new Texture(Gdx.files.internal(namePNG));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        actionButton = new ImageButton(myTexRegionDrawable);
        mainTable.add(actionButton).padBottom(-20).padRight(20);

        return actionButton;
    }

/*
    ButtonGroup<CheckBox> buttonGroup = new ButtonGroup<>() {
        @Override
        protected boolean canCheck(CheckBox checkBox, boolean newState) {
            boolean returnValue = super.canCheck(checkBox, newState);

            switch (getCheckedIndex()) {
                case 0:
                    System.out.println("The first checkbox was checked.");
                case 1:
                    System.out.println("The second checkbox was checked.");
            }
            return returnValue;
        }
    };

    private CheckBox addCheckBox() {
        CheckBox checkBox = new CheckBox("Multiplayer", skin);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Multiplayer");
            }
        });
        mainTable.add(checkBox);
        buttonGroup.add(checkBox);

        mainTable.row();
        checkBox = new CheckBox("Singleplayer", skin);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Singleplayer");
            }
        });
        mainTable.add(checkBox);
        buttonGroup.add(checkBox);

        stage.addActor(checkBox);

        return checkBox;
    }
*/
}
