package backgammon.game;

import backgammon.game.basic_backend.Board;
import backgammon.game.basic_backend.PrintBoard;
import backgammon.game.basic_backend.Rules;
import backgammon.game.basic_frontend.DiceManager;
import backgammon.game.basic_frontend.HelperClass;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

public class Backgammon extends Game implements InputProcessor {

	OrthographicCamera camera;
	private TiledMap gameBoardMap;
	private Rules rules;
	private TiledMapRenderer gameBoardRenderer;
	private Texture stone;
	public SpriteBatch batch;
	BitmapFont font;
	Board board;
	private int[][] field;

	private ShapeRenderer shape;
	float x;
	float y;
	private int newPostionID;
	private int stoneId;

	private final int STONE_WIDTH = 64;
	private final int STONE_HEIGHT = 64;

	ArrayList<ArrayList<Label>> alb;

	Texture dicebutton;

	//Texture dicesheet;
	//private final int FRAME_COLS = 6;
	//private final int FRAME_ROWS = 1;
	//Animation diceanimation;
	//Animation diceanimation2;
	float stateTime;
	float stateTime2;

	private final int DICE_BUTTON_WIDTH = 70;

	private final int DICE_BUTTON_HEIGHT = 80;

	DiceManager dice1;
	DiceManager dice2;

	int dicenumber1;
	int dicenumber2;


	private Texture diceResultTexture;
	private Texture diceResultTexture2;
	private final int DICE1_BUTTON_X = 50;
	private final int DICE1_BUTTON_Y = 50;
	private final int DICE2_BUTTON_X = 50;
	private final int DICE2_BUTTON_Y = 120;


	@Override
	public void create() {
		batch = new SpriteBatch();
		board = new Board();
		rules = new Rules();
		field = board.getField();
		shape = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("bitmapHiero.fnt"));
		alb = new ArrayList<ArrayList<Label>>();
		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.YELLOW);
		//Label label = new Label()
		//Map<Integer, ArrayList<ArrayList<Label>>> maps
		for (int i = 0; i < 24; i++) {
			alb.add(new ArrayList<Label>());
			for (int j = 0; j < 5; j++) {
				HelperClass.startStoneField(i, j, labelStyle, board, alb);
				alb.get(i).get(j).setSize(32, 32);
			}
		}

		dice1 = new DiceManager();
		dice2 = new DiceManager();



		dicebutton = new Texture("assets/diceButton.png");


		//dicesheet = new Texture("assets/sprites.png");

		//split to make equal split frames of dicesheet
		//devide through number of height and with to get the single frames
		//TextureRegion[][] tmp = TextureRegion.split(dicesheet,
				//dicesheet.getWidth() / FRAME_COLS, dicesheet.getHeight() / FRAME_ROWS);

		// put in correct order in 1d array to be able to work with animation constructor
		//TextureRegion[] diceFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		//int index = 0;
		//for (int i = 0; i < FRAME_ROWS; i++) {
			//for (int j = 0; j < FRAME_COLS; j++) {
				//diceFrames[index++] = tmp[i][j];
			//}
		//}

		//initialize animations + refresh rate
		//diceanimation = new Animation<>(0.075f, diceFrames);
		//diceanimation2 = new Animation<>(0.075f, diceFrames);

		//set startpoint time
		//stateTime = 0f;
		//stateTime2 = 0.25f;


		gameBoardMap = new TmxMapLoader().load("tiled/export/BackgammonBoard.tmx");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();


		gameBoardRenderer = new OrthogonalTiledMapRenderer(gameBoardMap);


		Gdx.input.setInputProcessor(this);


	}



	@Override
	public void render() {
		int counter = 0;
		int xPos = 0;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//accumulates animation time
		stateTime += Gdx.graphics.getDeltaTime();
		stateTime2 += Gdx.graphics.getDeltaTime();


		gameBoardRenderer.setView(camera);
		gameBoardRenderer.render();


		batch.begin();
		for (int i = 0; i < 12; i++) {
			if (i >= 6) {
				xPos = 895 - (64 * (i + 1));
			} else {
				xPos = 895 - (64 * i);
			}
			for (int j = 0; j < 5; j++) {
				alb.get(i).get(j).setPosition(xPos, 830 - (64 * j));
				alb.get(i).get(j).draw(batch, 1);
			}
		}

		for (int i = 12; i < 24; i++) {
			if (i >= 18) {
				xPos = 128 + (64 * (counter + 1));
			} else {
				xPos = 128 + (64 * counter);
			}
			for (int j = 0; j < 5; j++) {
				alb.get(i).get(j).setPosition(xPos, 65 + (64 * j));
				alb.get(i).get(j).draw(batch, 1);
			}
			counter++;
		}
		batch.end();

		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {
			if (object instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.rect(rect.x, rect.y, rect.width, rect.height);
				shape.end();
			} else if (object instanceof CircleMapObject) {
				Circle circle = ((CircleMapObject) object).getCircle();
				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.circle(circle.x, circle.y, circle.radius);
				shape.end();
			} else if (object instanceof PolygonMapObject) {
				Polygon polygon = ((PolygonMapObject) object).getPolygon();
				shape.begin(ShapeRenderer.ShapeType.Line);
				shape.polygon(polygon.getTransformedVertices());
				shape.end();
			} else if (object instanceof PolylineMapObject) {
				Polyline polyline = ((PolylineMapObject) object).getPolyline();
				shape.begin(ShapeRenderer.ShapeType.Line);
				shape.polyline(polyline.getTransformedVertices());
				shape.end();


			} else if (object instanceof EllipseMapObject) {
				Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.ellipse(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
				shape.end();
			} else if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;


				x = tileMapObject.getX();
				y = tileMapObject.getY();

				TiledMapTile tile = tileMapObject.getTile();
				TextureRegion textureRegion = new TextureRegion(tile.getTextureRegion());


				// current animation frame for current statetime(in dice)
				//TextureRegion currentFrame = (TextureRegion) diceanimation.getKeyFrame(stateTime, true); //loop frames
				//TextureRegion currentFrame2 = (TextureRegion) diceanimation2.getKeyFrame(stateTime2, true);


				batch.begin();
				batch.draw(textureRegion, x, y, STONE_WIDTH, STONE_HEIGHT);

				//roll dice button and animation
				int y = Gdx.graphics.getHeight() - dicebutton.getHeight();
				int x = 50;
				batch.draw(dicebutton, 50, Gdx.graphics.getHeight() - dicebutton.getHeight(), DICE_BUTTON_WIDTH, DICE_BUTTON_HEIGHT);
				if (Gdx.input.getX() < x + DICE_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < y + DICE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y) {
					if (Gdx.input.isTouched()) {

						TextureRegion dice1animation = dice1.diceanimation(stateTime+0.25f);
						TextureRegion dice2animation = dice2.diceanimation(stateTime2);
						//put in dice class dice.diceanimation(x,y)
						batch.draw(dice1animation, DICE1_BUTTON_X, DICE1_BUTTON_Y);
						batch.draw(dice2animation, DICE2_BUTTON_X, DICE2_BUTTON_Y);

						dicenumber2 = dice2.getDiceResult();
						dicenumber1 = dice1.getDiceResult();

						Texture diceTexture = dice1.getDiceTexture(DICE1_BUTTON_X, DICE1_BUTTON_Y, dicenumber1);
						Texture diceTexture2 = dice2.getDiceTexture(DICE2_BUTTON_X, DICE2_BUTTON_Y, dicenumber2);

						diceResultTexture= diceTexture;
						diceResultTexture2=diceTexture2;


						//System.out.println(dicenumber1);
						//System.out.println(dicenumber2);



					}
					//System.out.println(diceResultTexture);

				}
				if (!Gdx.input.isTouched() && diceResultTexture != null) {
					// Draw the result texture when the button is released

					batch.draw(diceResultTexture, DICE1_BUTTON_X, DICE1_BUTTON_Y);
					batch.draw(diceResultTexture2, DICE2_BUTTON_X, DICE2_BUTTON_Y);
					//System.out.println(diceResultTexture);

				}


				//System.out.println(dice1.getDiceResult());
				//if (Gdx.input.isTouched()) {
				// Get the dice texture
				//Texture diceTexture = dice1.getDiceTexture(DICE1_BUTTON_X, DICE1_BUTTON_Y);
				//Texture diceTexture2 = dice2.getDiceTexture(DICE2_BUTTON_X, DICE2_BUTTON_Y);

				// Draw the dice texture
				//batch.draw(diceTexture, DICE2_BUTTON_X, DICE1_BUTTON_Y);
				//batch.draw(diceTexture2, DICE2_BUTTON_X, DICE2_BUTTON_Y);

				//diceResultTexture = diceTexture;
				//diceResultTexture2 = diceTexture2;
				//}
				//if (!Gdx.input.isTouched() && diceResultTexture != null) {
				// Draw the result texture when the button is released
				//batch.draw(diceResultTexture, DICE1_BUTTON_X, DICE1_BUTTON_Y);
				//batch.draw(diceResultTexture2, DICE2_BUTTON_X, DICE2_BUTTON_Y);
				//}

				batch.end();





			}

		}

	}






//private void drawdice(Sprite dice) {
	//	dice.setPosition(0, 0);
	//	dice.draw(batch);
	//}

	//private void load_dice() {

		//Array<TextureAtlas.AtlasRegion> regions = dicesides.getRegions();

		//for (TextureAtlas.AtlasRegion region : regions) {
			//Sprite dice = dicesides.createSprite(region.name);

			//dice_sides.put(region.name, dice);
		//}
	//}



	@Override
	public void dispose() {
		batch.dispose();
		stone.dispose();
		gameBoardMap.dispose();

	}
	public BitmapFont getFont()
	{
		return font;
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
		//Gdx.app.log("Mouse", "touch Down");
		//FontGameMap test = new FontGameMap();
		if(button == Input.Buttons.LEFT)
		{
			mouseMoved(screenX,screenY);
			checkObjectClicked(screenX, screenY);
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
		//System.out.println(screenX + " " + screenY);
		return false;
	}
	@Override
	public void resize(int width, int height) {/*
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();*/
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;

	}

	public void checkObjectClicked(int screenX, int screenY) {
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
		this.moveStone(screenX,screenY);
		float width = 64;
		float height = 64;
		float xPos;
		float yPos;
		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {

			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;

				xPos = tileMapObject.getX();
				yPos = tileMapObject.getY();


				if (xPos <= worldCoordinates.x && worldCoordinates.x <= xPos + width &&
						yPos <= worldCoordinates.y && worldCoordinates.y <= yPos + height) {
					Gdx.app.log("Stein angeklickt", "ID: " + tileMapObject.getProperties().get("id", Integer.class));
					stoneId = tileMapObject.getProperties().get("id", Integer.class);
				}
			}
		}
	}
	public void moveStone(int screenX, int screenY) {
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {

			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;
				newPostionID = tileMapObject.getProperties().get("id", Integer.class);
				if (newPostionID == stoneId) {
					for (int i = 0; i < 24; i++) {
						for (int j = 0; j < 5; j++) {
							if (alb.get(i).get(j).getX() <= worldCoordinates.x && worldCoordinates.x <= alb.get(i).get(j).getX() + 32 &&
									alb.get(i).get(j).getY() <= worldCoordinates.y && worldCoordinates.y <= alb.get(i).get(j).getY() + 32) {
								Gdx.app.log("Label", "Klicked");


								//Loesche Stein von altem Feld
								HelperClass.removeStoneId(stoneId, field, alb);

								//Setze Stein auf neues Feld
								field[i][j] = stoneId;
								//int[] gameBoardEdge = new int[10];
								//System.out.println(rules.isAccessible(true, i, field, gameBoardEdge));
								alb.get(i).get(j).setText(stoneId);

								//Bewege den Stein auf neues Feld
								tileMapObject.setX(alb.get(i).get(j).getX());
								tileMapObject.setY(alb.get(i).get(j).getY());

								//CheckBoard ArrayList Label
								HelperClass.checkGameField(alb);
								//PrintBoard mit int[][]
								PrintBoard.printBoard(field);
								//System.out.println(rules.isAccessible(true, i,field,gameBoardEdge));
							}
						}
					}
				}

			}
		}
	}
}