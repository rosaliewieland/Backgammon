package backgammon.game;

import backgammon.game.basic_backend.Board;
import backgammon.game.basic_backend.Player;
import backgammon.game.basic_backend.PrintBoard;
import backgammon.game.basic_backend.Rules;
import backgammon.game.basic_frontend.DiceManager;
import backgammon.game.basic_frontend.HelperClass;
import backgammon.game.screens.EndingScreen;
import backgammon.game.screens.ScreenHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
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

import java.util.*;

public class Backgammon extends ScreenAdapter implements InputProcessor{
	private Player playerOne;
	private Player playerTwo;
	private Rules rules;
	private DiceManager dice1;
	private DiceManager dice2;
	private Sound soundshake;
	private Animation diceanimation, diceanimation2;
	private Texture whiteTurnTexture, blackTurnTexture, gamebarBottomWhite, gamebarTopBlack, dicebutton;
	private TiledMapTileMapObject stone;
	private Label whiteLabelDismantle, blackLabelDismantle;
	private Label greenLabel;
	public OrthographicCamera camera;
	private TiledMap gameBoardMap;
	private TiledMapRenderer gameBoardRenderer;
	private Texture diceResultTexture, diceResultTexture2;
	private BitmapFont font;
	private Board board;
	private ShapeRenderer shape;
	public SpriteBatch batch;
	private ArrayList<ArrayList<Label>> alb;
	private ArrayList<Label> blackGameBarLabel, whiteGameBarLabel;

	private boolean controlDiceRoll =false;
	private int[][] field;
	private int newPostionID;
	private int stoneId;
	private int indexIStone;
	private int diceNumber1, diceNumber2;
	private boolean turn = false;
	private boolean isMovedDiceOne = false, isMovedDiceTwo=false;
	private boolean blackStonesOut = false, whiteStonesOut = false;
	private boolean isStoneVisible = false;
	private boolean movedDiceOne = false, movedDiceTwo = false,  movedWithTwoStone = false;
	private final int STONE_WIDTH = 64;
	private final int STONE_HEIGHT = 64;
	public static final int WORLD_WIDTH =1500;
	public static final int WORLD_HEIGHT =1000;
	private float stateTime, stateTime2;
	private final int DICE_BUTTON_WIDTH = 70;
	private final int DICE_BUTTON_HEIGHT = 80;
	private final int DICE1_BUTTON_X = 50;
	private final int DICE1_BUTTON_Y = 50;
	private final int DICE2_BUTTON_X = 50;
	private final int DICE2_BUTTON_Y = 120;
	private final int FRAME_COLS = 6;
	private final int FRAME_ROWS = 1;
	@Override
	public void show() {
		int index = 0;

		playerOne = new Player(false);
		playerTwo = new Player(true);

		rules =  new Rules();
		gamebarBottomWhite = new Texture("assets/GamebarbottomWhite.png");
		gamebarTopBlack = new Texture("assets/GamebartopBlack.png");

		whiteTurnTexture = new Texture("assets/WhitePlayer.png");
		blackTurnTexture = new Texture("assets/BlackPlayer.png");
		whiteGameBarLabel = new ArrayList<Label>();


		batch = new SpriteBatch();
		board = new Board();
		field = board.getField();
		shape = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("bitmapHiero.fnt"));
		alb = new ArrayList<ArrayList<Label>>();
		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.YELLOW);
		greenLabel = new Label("", labelStyle);
		greenLabel.setColor(Color.GREEN);
		whiteLabelDismantle = new Label("Ziel", labelStyle);
		whiteLabelDismantle.setColor(Color.BLACK);
		whiteLabelDismantle.setPosition(1275,500);
		whiteLabelDismantle.setSize(32,32);
		blackLabelDismantle = new Label("Ziel", labelStyle);
		blackLabelDismantle.setColor(Color.BLACK);

		blackLabelDismantle.setPosition(1275,550);
		for (int i = 0; i < 24; i++) {
			alb.add(new ArrayList<Label>());
			for (int j = 0; j < 5; j++) {
				HelperClass.startStoneField(i, j, labelStyle, board, alb);
				alb.get(i).get(j).setSize(32, 32);
			}
		}
		blackGameBarLabel = new ArrayList<Label>();
		for (int i = 0; i < 5; i++) {
			blackGameBarLabel.add(new Label("0", labelStyle));
			blackGameBarLabel.get(i).setColor(Color.RED);
			blackGameBarLabel.get(i).setSize(32, 32);
			blackGameBarLabel.get(i).setPosition(1235, 129 + (64 * i));
		}
		for (int i = 0; i < 5; i++) {
			whiteGameBarLabel.add(new Label("0", labelStyle));
			whiteGameBarLabel.get(i).setColor(Color.RED);
			whiteGameBarLabel.get(i).setSize(32, 32);
			whiteGameBarLabel.get(i).setPosition(1235, 894 - (64 * i));
		}

		dicebutton = new Texture("assets/diceButton.png");

		dice1 = new DiceManager();
		dice2 = new DiceManager();

		Texture dicesheet = new Texture("assets/sprites.png");

		//split to make equal split frames of dicesheet
		//devide through number of height and with to get the single frames
		TextureRegion[][] tmp = TextureRegion.split(dicesheet,
		dicesheet.getWidth() / FRAME_COLS, dicesheet.getHeight() / FRAME_ROWS);

		// put in correct order in 1d array to be able to work with animation constructor
		TextureRegion[] diceFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		for (int i = 0; i < FRAME_ROWS; i++)
		{
			for (int j = 0; j < FRAME_COLS; j++) {
				diceFrames[index++] = tmp[i][j];
			}
		}
		//initialize animations + refresh rate
		diceanimation = new Animation<>(0.075f, diceFrames);
		diceanimation2 = new Animation<>(0.075f, diceFrames);

		//set startpoint time
		stateTime = 0f;
		stateTime2 = 1f;

		soundshake = Gdx.audio.newSound(Gdx.files.internal("assets/shaking-dice-25620.mp3"));
		gameBoardMap = new TmxMapLoader().load("tiled/export/BackgammonBoardExpanded.tmx");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		gameBoardRenderer = new OrthogonalTiledMapRenderer(gameBoardMap);

		board.createReverenceSet();
		board.createsSetOfHomeField();
		Gdx.input.setInputProcessor(this);
	}
	@Override
	public void render(float delta) {
		int counter = 0;
		int xPos = 0;
		float tileObjectX = 0;
		float tileObjectY = 0;
		int diceY = 200;
		int diceX = 50;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//accumulates animation time
		stateTime += Gdx.graphics.getDeltaTime();
		stateTime2 += Gdx.graphics.getDeltaTime();

		gameBoardRenderer.setView(camera);
		gameBoardRenderer.render();

		batch.begin();
		batch.draw(gamebarBottomWhite, 1200,100);
		batch.draw(gamebarTopBlack,1200,625);
		for (int i = 0; i < 5; i++) {
			blackGameBarLabel.get(i).draw(batch, 1);
		}
		for (int i = 0; i < 5; i++) {
			whiteGameBarLabel.get(i).draw(batch, 1);
		}
		// Anzeige, wer dran ist
		if (turn) {
			batch.draw(blackTurnTexture, 50, 900);
		} else {
			batch.draw(whiteTurnTexture, 50, 900);
		}
		if(isStoneVisible)
		{
			for (int i = 0; i < 12; i++) {
				if (i >= 6) {
					xPos = (895 + 128) - (64 * (i + 1));
				} else {
					xPos = (895 + 128) - (64 * i);
				}
				for (int j = 0; j < 5; j++) {
					if((alb.get(i).get(j).getColor().equals(greenLabel.getColor())))
					{
						alb.get(i).get(j).setPosition(xPos, 894 - (64 * j));
						alb.get(i).get(j).draw(batch, 1);
					}
				}
			}
			for (int i = 12; i < 24; i++) {
				if (i >= 18) {
					xPos = 256 + (64 * (counter + 1));
				} else {
					xPos = 256 + (64 * counter);
				}
				for (int j = 0; j < 5; j++) {
					if((alb.get(i).get(j).getColor().equals(greenLabel.getColor())))
					{
						alb.get(i).get(j).setPosition(xPos, 129 + (64 * j));
						alb.get(i).get(j).draw(batch, 1);
					}
				}
				counter++;
			}
		}
		blackLabelDismantle.draw(batch, 1);
		whiteLabelDismantle.draw(batch,1);
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

				tileObjectX = tileMapObject.getX();
				tileObjectY = tileMapObject.getY();

				TiledMapTile tile = tileMapObject.getTile();
				TextureRegion textureRegion = new TextureRegion(tile.getTextureRegion());

				// current animation frame for current statetime
				TextureRegion currentFrame = (TextureRegion) diceanimation.getKeyFrame(stateTime, true); //loop frames
				TextureRegion currentFrame2 = (TextureRegion) diceanimation2.getKeyFrame(stateTime2, true);
				batch.begin();
				batch.draw(textureRegion, tileObjectX, tileObjectY, STONE_WIDTH, STONE_HEIGHT);

				//roll dice button and animation
				batch.draw(dicebutton, diceX, diceY, DICE_BUTTON_WIDTH, DICE_BUTTON_HEIGHT);
				if (Gdx.input.getX() < diceX + DICE_BUTTON_WIDTH && Gdx.input.getX() > diceX && Gdx.graphics.getHeight() - Gdx.input.getY() < diceY + DICE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > diceY) {

					if (Gdx.input.isTouched())
					{
						//put in dice class dice.diceanimation(x,y)
						batch.draw(currentFrame, DICE1_BUTTON_X, DICE1_BUTTON_Y);
						batch.draw(currentFrame2, DICE2_BUTTON_X, DICE2_BUTTON_Y);
					}

					if (Gdx.input.isTouched() && !controlDiceRoll) {
						diceNumber1= dice1.getDiceResult();
						diceNumber2= dice2.getDiceResult();

						//put in dice class dice.diceanimation(x,y)
						batch.draw(currentFrame, DICE1_BUTTON_X, DICE1_BUTTON_Y);
						batch.draw(currentFrame2, DICE2_BUTTON_X, DICE2_BUTTON_Y);

						if(diceNumber1==diceNumber2)
						{
							diceNumber2++;
						}

						Texture diceTexture = dice1.getDiceTexture(DICE1_BUTTON_X, DICE1_BUTTON_Y, diceNumber1);
						Texture diceTexture2 = dice2.getDiceTexture(DICE2_BUTTON_X, DICE2_BUTTON_Y, diceNumber2);

						diceResultTexture = diceTexture;
						diceResultTexture2 = diceTexture2;
						controlDiceRoll =true;
					}
				}
				if (!Gdx.input.isTouched() && diceResultTexture != null) {

					// Draw the result texture when the button is release
					batch.draw(diceResultTexture, DICE1_BUTTON_X, DICE1_BUTTON_Y);
					batch.draw(diceResultTexture2, DICE2_BUTTON_X, DICE2_BUTTON_Y);
					soundshake.play();
				}
			}
			batch.end();
		}
	}
	@Override
	public void dispose() {
		batch.dispose();
		gameBoardMap.dispose();
		whiteTurnTexture.dispose();
		blackTurnTexture.dispose();
		diceResultTexture2.dispose();
		diceResultTexture.dispose();
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
		if (button == Input.Buttons.LEFT) {
			checkObjectClicked(screenX, screenY);
		}
		return false;
	}
	public void checkObjectClicked(int screenX, int screenY) {
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
		float width = 64;
		float height = 64;
		float xPos;
		float yPos;

		if (!(this.setStone(screenX, screenY))) {
			for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {

				if (object instanceof TiledMapTileMapObject) {
					TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;

					xPos = tileMapObject.getX();
					yPos = tileMapObject.getY();

					if (xPos <= worldCoordinates.x && worldCoordinates.x <= xPos + width &&
							yPos <= worldCoordinates.y && worldCoordinates.y <= yPos + height) {
						Gdx.app.log("Stein angeklickt", "ID: " + tileMapObject.getProperties().get("id", Integer.class));
						stoneId = tileMapObject.getProperties().get("id", Integer.class);
						resetField();
						this.labelVisabel(true);
						if (turn && stoneId > 0) {
							stone = tileMapObject;
							if (rules.isStoneOut(playerTwo.getGameBar())) {
								playerTwo.gameBarIsNotEmpty();
								this.bringStonesBackInGame(turn);
							} else {
								fieldsAllowedToMove(turn);
							}
							System.out.println("game Ende:" + this.gameEnde(indexIStone));
							indexIStone = HelperClass.checkGameField(field,stoneId);
							System.out.println("StoneIndexI: " + indexIStone);
							if(indexIStone!=-100)
							{
								if (this.gameEnde(indexIStone)) {
									this.moveOut(stoneId, indexIStone);
								}
							}
						} else if (!turn && stoneId < 0) {
							stone = tileMapObject;
							if (rules.isStoneOut(playerOne.getGameBar())) {
								playerOne.gameBarIsNotEmpty();
								this.bringStonesBackInGame(turn);
							} else {
								fieldsAllowedToMove(turn);
							}
							System.out.println("game Ende:" + this.gameEnde(indexIStone));
							indexIStone = HelperClass.checkGameField(field,stoneId);
							System.out.println("StoneIndexI: " + indexIStone);

							if(indexIStone!=-100)
							{
								if (this.gameEnde(indexIStone)) {
									this.moveOut(stoneId, indexIStone);
								}
							}
						}
					}
				}
			}
		}
	}
	public void allowedBringBack(boolean isBlack, int dice, boolean movedDice) {
		if(isBlack)
		{
			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 5; j++) {
					if (stoneId == playerTwo.getStones()) {
						if (rules.isAccessible(isBlack, 24 - dice, field, playerOne.getGameBar(), false) && !movedDice) {
							for (int k = 0; k < 5; k++) {
								if (Integer.parseInt(String.valueOf(alb.get(24 - dice).get(k).getText())) == 0) {
									alb.get(24 - dice).get(k).setColor(Color.GREEN);
									//field[24 - diceNumber1][0] = 2
									blackStonesOut = true;
								}
							}
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 5; j++) {
					if (stoneId == playerOne.getStones()) {
						if (rules.isAccessible(isBlack, -1 + dice, field, playerTwo.getGameBar(), false) && !movedDice) {
							for (int k = 0; k < 5; k++) {
								if (Integer.parseInt(String.valueOf(alb.get(-1 + dice).get(k).getText())) == 0) {
									alb.get(-1 + dice).get(k).setColor(Color.GREEN);
									//field[24 - diceNumber1][0] = 2;
									whiteStonesOut = true;
								}
							}
						}
					}
				}
			}
		}
	}
	public void allowedFields(boolean isBlack, int dice, boolean movedDice) {

		if (isBlack) {
			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 5; j++) {
					if (field[i][j] == stoneId) {
						if (rules.isAccessible(isBlack, i - dice, field, playerOne.getGameBar(), false) && !movedDice) {
							for (int k = 0; k < 5; k++) {
								if (Integer.parseInt(String.valueOf(alb.get(i - dice).get(k).getText())) == 0) {
									alb.get(i - dice).get(k).setColor(Color.GREEN);
								}
							}
						}
					}
				}
			}
		}
		else
		{
			for (int i = 0; i < 24; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					if (field[i][j] == stoneId)
					{
						if (rules.isAccessible(isBlack, i + dice, field, playerTwo.getGameBar(), false) && !movedDice)
						{
							for (int k = 0; k < 5; k++)
							{
								if (Integer.parseInt(String.valueOf(alb.get(i + dice).get(k).getText())) == 0)
								{
									alb.get(i + dice).get(k).setColor(Color.GREEN);
								}
							}
						}
					}
				}
			}
		}
	}
	public void bringStonesBackInGame(boolean isBlack)
	{
		if(isBlack)
		{
			if (rules.isStoneOut(playerTwo.getGameBar())) {
				allowedBringBack(isBlack,diceNumber1, movedDiceOne);
				allowedBringBack(isBlack,diceNumber2, movedDiceTwo);
				if(!movedDiceOne && !movedDiceTwo)
				{
					allowedBringBack(isBlack,diceNumber1+diceNumber2, movedWithTwoStone);
				}
			}
		}else {
			if (rules.isStoneOut(playerOne.getGameBar())) {
				allowedBringBack(isBlack,diceNumber1, movedDiceOne);
				allowedBringBack(isBlack,diceNumber2, movedDiceTwo);
				if(!movedDiceOne && !movedDiceTwo)
				{
					allowedBringBack(isBlack,diceNumber1+diceNumber2, movedWithTwoStone);
				}
			}
		}
	}
	public void resetField()
	{
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 5; j++) {
				alb.get(i).get(j).setColor(Color.YELLOW);
			}
		}
	}
	public void fieldsAllowedToMove(boolean isBlack) {
		if(controlDiceRoll)
		{
			allowedFields(isBlack,diceNumber1, movedDiceOne);
			allowedFields(isBlack,diceNumber2, movedDiceTwo);
			if(!movedDiceOne && !movedDiceTwo)
			{
				allowedFields(isBlack,diceNumber1+diceNumber2, movedWithTwoStone);
			}
		}
	}
	public void whoesTurn()
	{
		if(turn)
		{
			turn=false;
		}
		else {
			turn=true;
		}
		// Reset all trun Variables
		movedWithTwoStone = false;
		movedDiceOne = false;
		movedDiceTwo = false;
		isMovedDiceOne = false;
		isMovedDiceTwo = false;
		controlDiceRoll =false;
	}
	public boolean setStone(int screenX, int screenY) {
		boolean accessMove = false;
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {
			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;
				newPostionID = tileMapObject.getProperties().get("id", Integer.class);
				if (newPostionID == stoneId) {
					for (int i = 0; i < 24; i++) {
						for (int j = 0; j < 5; j++) {
							if (alb.get(i).get(j).getX() <= worldCoordinates.x && worldCoordinates.x <= alb.get(i).get(j).getX() + 32 &&
									alb.get(i).get(j).getY() <= worldCoordinates.y && worldCoordinates.y <= alb.get(i).get(j).getY() + 32)
							{
								Gdx.app.log("Label", "Klicked");
								if(alb.get(i).get(j).getColor().equals(greenLabel.getColor()))
								{
									if(turn && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber1)
									{
										if(rules.isStoneOut(playerTwo.getGameBar()))
										{
											playerTwo.gameBarIsNotEmpty();
											playerTwo.setStone(playerTwo.getStones());
										}
										else {
											playerTwo.setStone(stoneId);
										}
										accessMove = playerTwo.moveOneStone(field, i,playerOne,board);
										movedDiceOne = true;
										if(blackStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										blackStonesOut = false;
									}
									else if(turn && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber2)
									{
										if(rules.isStoneOut(playerTwo.getGameBar()))
										{
											playerTwo.gameBarIsNotEmpty();
											playerTwo.setStone(playerTwo.getStones());
										}
										else {
											playerTwo.setStone(stoneId);
										}
										accessMove = playerTwo.moveOneStone(field, i,playerOne,board);
										movedDiceTwo = true;
										if(blackStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										blackStonesOut = false;
									}
									else if(turn && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber1+diceNumber2)
									{
										if(rules.isStoneOut(playerTwo.getGameBar()))
										{
											playerTwo.gameBarIsNotEmpty();
											playerTwo.setStone(playerTwo.getStones());
										}
										else {
											playerTwo.setStone(stoneId);
										}
										accessMove = playerTwo.moveOneStone(field, i,playerOne,board);
										movedWithTwoStone = true;
										if(blackStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										blackStonesOut = false;
									}
									else if(!turn && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber1)
									{
										if(rules.isStoneOut(playerOne.getGameBar()))
										{
											playerOne.gameBarIsNotEmpty();
											playerOne.setStone(playerOne.getStones());

										}
										else {
											playerOne.setStone(stoneId);
										}
										accessMove = playerOne.moveOneStone(field, i,playerTwo,board);
										movedDiceOne = true;
										if(whiteStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										whiteStonesOut = false;
									}
									else if(!turn && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber2)
									{
										if(rules.isStoneOut(playerOne.getGameBar()))
										{
											playerOne.gameBarIsNotEmpty();
											playerOne.setStone(playerOne.getStones());

										}
										else {
											playerOne.setStone(stoneId);
										}
										accessMove = playerOne.moveOneStone(field, i,playerTwo,board);
										movedDiceTwo = true;
										if(whiteStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										whiteStonesOut = false;
									}
									else if(!turn  && HelperClass.getDifferenz(alb,i,turn, stoneId)==diceNumber1+diceNumber2)
									{

										if(rules.isStoneOut(playerOne.getGameBar()))
										{
											playerOne.gameBarIsNotEmpty();
											playerOne.setStone(playerOne.getStones());

										}
										else {
											playerOne.setStone(stoneId);
										}
										accessMove = playerOne.moveOneStone(field, i,playerTwo,board);
										movedWithTwoStone = true;
										if(whiteStonesOut)
										{
											this.checkGameBar(turn, stoneId);
										}
										whiteStonesOut = false;

									}
									if (!accessMove) {
										return false;
									}

									HelperClass.removeStoneId(stoneId, field, alb);
									alb.get(i).get(j).setText(stoneId);

									tileMapObject.setX(alb.get(i).get(j).getX());
									tileMapObject.setY(alb.get(i).get(j).getY());

									PrintBoard.printBoard(field);
									resetField();
									if(((movedDiceOne && movedDiceTwo) || movedWithTwoStone) || isMovedDiceOne || isMovedDiceTwo)
									{
										whoesTurn();
									}
									this.printGameBar(playerTwo, true);
									this.printGameBar(playerOne, false);
									this.labelVisabel(false);
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	public void printGameBar(Player p, boolean isBlack )
	{
		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BROWN);
		int[] gameBar = p.getGameBar();
		int id=-100;

		for(int i=0;i<5;i++)
		{
			if(gameBar[i]!=0)
			{
				for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects())
				{
					if (object instanceof TiledMapTileMapObject) {
						TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;
						id = tileMapObject.getProperties().get("id", Integer.class);


						if(gameBar[i] == id)
						{
							if(isBlack)
							{
								tileMapObject.setX(blackGameBarLabel.get(i).getX());
								tileMapObject.setY(blackGameBarLabel.get(i).getY());
								blackGameBarLabel.get(i).setText(id);
							}
							else {
								tileMapObject.setX(whiteGameBarLabel.get(i).getX());
								tileMapObject.setY(whiteGameBarLabel.get(i).getY());
								whiteGameBarLabel.get(i).setText(id);
							}

							stoneId = id;
							for(int t=0;t<24;t++)
							{
								for(int k=0;k<5;k++)
								{
									if(Integer.parseInt(String.valueOf(alb.get(t).get(k).getText())) == id)
									{
										alb.get(t).get(k).setText("0");
									}
								}
							}
						}

					}
				}
			}

		}
	}
	public void checkGameBar(boolean isBlack, int stoneId)
	{
		for(int i=0;i<5;i++)
		{
			if(isBlack)
			{
				if (Integer.parseInt(String.valueOf(blackGameBarLabel.get(i).getText()))== stoneId) {
					blackGameBarLabel.get(i).setText("0");
				}
			}else {
				if (Integer.parseInt(String.valueOf(whiteGameBarLabel.get(i).getText()))== stoneId)
				{
					whiteGameBarLabel.get(i).setText("0");
				}
			}
		}
	}
	public boolean gameEnde(int indexIStone)
	{
		board.compareSetsEnableRemoveStones();
		System.out.println("Referenzset: " +board.getReverenceSetWhite());
		System.out.println(playerOne.permissionToMoveStoneOutOfBoard(board, diceNumber2, indexIStone));
		if(!turn)
		{
			if(playerOne.permissionToMoveStoneOutOfBoard(board, diceNumber2, indexIStone) || playerOne.permissionToMoveStoneOutOfBoard(board, diceNumber1, indexIStone)
					|| playerOne.permissionToMoveStoneOutOfBoard(board, diceNumber1 +diceNumber2, indexIStone))
			{
				whiteLabelDismantle.setColor(Color.GREEN);
				return true;
			}
			else {
				whiteLabelDismantle.setColor(Color.BLACK);
			}
		}
		else {
			if(playerTwo.permissionToMoveStoneOutOfBoard(board, diceNumber2, indexIStone) || playerTwo.permissionToMoveStoneOutOfBoard(board, diceNumber1, indexIStone) ||
					playerTwo.permissionToMoveStoneOutOfBoard(board, diceNumber1 +diceNumber2, indexIStone))
			{
				blackLabelDismantle.setColor(Color.GREEN);
				return true;
			}
			else {
				blackLabelDismantle.setColor(Color.BLACK);
			}
		}
		return false;
	}
	public void moveOut(int stoneId,int indexIStone)
	{
		if(!turn)
		{
			if(indexIStone+diceNumber1>23 && !isMovedDiceOne)
			{
				stone.setX(whiteLabelDismantle.getX());
				stone.setY(whiteLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerOne.setOutOfBoard(stoneId,board);
				isMovedDiceOne = true;
			}
			else if(indexIStone+diceNumber2>23 && !isMovedDiceTwo)
			{
				stone.setX(whiteLabelDismantle.getX());
				stone.setY(whiteLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerOne.setOutOfBoard(stoneId, board);
				isMovedDiceTwo = true;
			}
			else if(indexIStone+diceNumber2+diceNumber1 >23 && !isMovedDiceOne && !isMovedDiceTwo)
			{
				stone.setX(whiteLabelDismantle.getX());
				stone.setY(whiteLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerOne.setOutOfBoard(stoneId, board);
				isMovedDiceOne=true;
				isMovedDiceTwo=true;
			}
			if(isMovedDiceOne && isMovedDiceTwo)
			{
				whoesTurn();
			}
			if(playerOne.isTheWinner())
			{
				ScreenHandler.INSTANCE.setScreen(new EndingScreen(this, 0));
			}
		}
		else {
			if(indexIStone-diceNumber1<0 && !isMovedDiceOne)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerTwo.setOutOfBoard(stoneId, board);
				isMovedDiceOne = true;
			}
			else if(indexIStone-diceNumber2<0 && !isMovedDiceTwo)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerTwo.setOutOfBoard(stoneId, board);
				isMovedDiceTwo = true;
			}
			else if(indexIStone-diceNumber2-diceNumber1 <0 && !isMovedDiceOne && !isMovedDiceTwo)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				playerTwo.setOutOfBoard(stoneId,board);
				isMovedDiceOne=true;
				isMovedDiceTwo=true;

			}
			if(isMovedDiceOne && isMovedDiceTwo)
			{
				whoesTurn();
			}

			if(playerTwo.isTheWinner())
			{
				ScreenHandler.INSTANCE.setScreen(new EndingScreen(this, 1));
			}
		}
	}
	public void labelVisabel(boolean visible)
	{
		this.isStoneVisible = visible;
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
	public void resize(int width, int height) {
	}
	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}