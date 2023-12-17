package backgammon.game;

import backgammon.game.basic_backend.Board;
import backgammon.game.basic_backend.Player;
import backgammon.game.basic_backend.PrintBoard;
import backgammon.game.basic_backend.Rules;
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
import java.util.HashSet;
import java.util.Set;

public class Backgammon extends ScreenAdapter implements InputProcessor{

	Player playerOne;
	Player playerTwo;
	Texture whiteTurnTexture;
	Texture blackTurnTexture;
	private Set<Integer> outOfBoard = new HashSet<>();
	private int indexIStone;
	private TiledMapTileMapObject stone;
	Rules rules;
	private boolean turn = false;
	private boolean isMovedDiceOne=false, isMovedDiceTwo=false;
	private boolean blackStonesOut = false;
	private boolean whiteStonesOut = false;
	private Texture gamebarBottomWhite;
	private Texture gamebarTopBlack;

	private Label whiteLabelDismantle;
	private Label blackLabelDismantle;

	public OrthographicCamera camera;
	private TiledMap gameBoardMap;
	private TiledMapRenderer gameBoardRenderer;
	public SpriteBatch batch;
	private ArrayList<Label> blackGameBarLabel;
	private ArrayList<Label> whiteGameBarLabel;
	BitmapFont font;
	Board board;
	private int[][] field;

	private ShapeRenderer shape;
	float x;
	float y;
	private int newPostionID;
	private int stoneId;
	private int diceNumber1 = 4;
	private int diceNumber2 = 2;
	private Label greenLabel;

	private final int STONE_WIDTH = 64;
	private final int STONE_HEIGHT = 64;

	ArrayList<ArrayList<Label>> alb;


	private boolean movedDiceOne = false;
	private boolean movedDiceTwo = false;

	private boolean movedWithTwoStone = false;
	Texture dicebutton;
	Texture dicesheet;

	private final int FRAME_COLS = 6;
	private final int FRAME_ROWS = 1;
	Animation diceanimation;
	Animation diceanimation2;

	float stateTime;
	float stateTime2;

	private final int DICE_BUTTON_WIDTH = 60;

	private final int DICE_BUTTON_HEIGHT = 70;


	@Override
	public void show() {
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

		dicesheet = new Texture("assets/sprites.png");


		//split to make equal split frames of dicesheet
		//devide through number of height and with to get the single frames
		TextureRegion[][] tmp = TextureRegion.split(dicesheet,
				dicesheet.getWidth() / FRAME_COLS, dicesheet.getHeight() / FRAME_ROWS);

		// put in correct order in 1d array to be able to work with animation constructor
		TextureRegion[] diceFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				diceFrames[index++] = tmp[i][j];
			}
		}

		//initialize animations + refresh rate
		diceanimation = new Animation<>(0.25f, diceFrames);
		diceanimation2 = new Animation<>(0.25f, diceFrames);

		//set startpoint time
		stateTime = 0f;
		stateTime2 = 1f;


		//dice1 = dicesides.createSprite("dice1");
		//hasmap for dice value,textures

		//Dice dice1 = new Dice();
		//System.out.println(dice.getDice());

		//HashMap<Integer,Texture> dice_textures = new HashMap<>();
		//for(int i = 1; i<7;i++){
		//Texture dice_texture = new Texture(String.format("assets/dice%s.png", i));
		//dice_textures.put(i,dice_texture);
		//System.out.println(dice_texture.getTextureData());
		//}


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

		for (int i = 0; i < 12; i++) {
			if (i >= 6) {
				xPos = (895 + 128) - (64 * (i + 1));
			} else {
				xPos = (895 + 128) - (64 * i);
			}
			for (int j = 0; j < 5; j++) {
				alb.get(i).get(j).setPosition(xPos, 894 - (64 * j));
				alb.get(i).get(j).draw(batch, 1);
			}
		}

		for (int i = 12; i < 24; i++) {
			if (i >= 18) {
				xPos = 256 + (64 * (counter + 1));
			} else {
				xPos = 256 + (64 * counter);
			}
			for (int j = 0; j < 5; j++) {
				alb.get(i).get(j).setPosition(xPos, 129 + (64 * j));
				alb.get(i).get(j).draw(batch, 1);
			}
			counter++;
		}
		//whiteLabelDismantle.draw(batch,1);


		blackLabelDismantle.draw(batch, 1);
		whiteLabelDismantle.draw(batch,1);


		batch.end();
		//this.printGameBar(playerOne);


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

				// current animation frame for current statetime
				TextureRegion currentFrame = (TextureRegion) diceanimation.getKeyFrame(stateTime, true); //loop frames
				TextureRegion currentFrame2 = (TextureRegion) diceanimation2.getKeyFrame(stateTime2, true);


				batch.begin();
				batch.draw(textureRegion, x, y, STONE_WIDTH, STONE_HEIGHT);

				//roll dice button and animation
				int y = Gdx.graphics.getHeight() - dicebutton.getHeight();
				int x = 50;
				batch.draw(dicebutton, 50, Gdx.graphics.getHeight() - dicebutton.getHeight(), DICE_BUTTON_WIDTH, DICE_BUTTON_HEIGHT);
				if (Gdx.input.getX() < x + DICE_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < y + DICE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y) {
					if (Gdx.input.isTouched()) {
						batch.draw(currentFrame, 50, 50);
						batch.draw(currentFrame2, 50, 120);
					}
				}
				//for(int i=1;i<7;i++){
				//drawdice(dice_sides.get(String.format("dice%s",i)));
				//drawdice(dice_sides.get("dice1"));
				//drawdice(dice_sides.get("dice2"));
				//drawdice(dice_sides.get("getDice1"),100,100);
				//drawing sprite on to the batch
				//batch.draw(dice_textures.get(1),0,0);

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
		gameBoardMap.dispose();
		whiteTurnTexture.dispose();
		blackTurnTexture.dispose();
	}

	public BitmapFont getFont() {
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
		if (button == Input.Buttons.LEFT) {
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

		float width = 64;
		int counter =0;
		float height = 64;
		float xPos;
		float yPos;
		rules = new Rules();
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
	public void pasch(boolean isBlack)
	{

		for(int i=0;i<24;i++)
		{
			for(int j=0;j<5;j++)
			{
				if (field[i][j] == stoneId) {
					if (rules.isAccessible(isBlack, i+  diceNumber1, field, playerTwo.getGameBar(),false) && !movedDiceOne) {
						for (int k = 0; k < 5; k++) {
							if (Integer.parseInt(String.valueOf(alb.get(i + diceNumber1).get(k).getText())) == 0) {
								alb.get(i + diceNumber1).get(k).setColor(Color.BLACK);
							}
						}
					}
				}
			}
		}
	}
	public void fieldsAllowedToMove(boolean isBlack) {
		allowedFields(isBlack,diceNumber1, movedDiceOne);
		allowedFields(isBlack,diceNumber2, movedDiceTwo);
		if(!movedDiceOne && !movedDiceTwo)
		{
			allowedFields(isBlack,diceNumber1+diceNumber2, movedWithTwoStone);
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
										/*if(playerOne.permissionToMoveStoneOutOfBoard(board,diceNumber2,i))
										{
											board.adjustReverenceSet(field[i][j]);
											outOfBoard.add(field[i][j]);
											field[i][j] = 0;
											alb.get(i).get(j).setText("0");
										}
										else {
											accessMove = playerOne.moveOneStone(field, i,playerTwo,board);
										}*/
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






									//Setze Stein auf neues Feld
									//field[i][j] = stoneId;
									//int[] gameBoardEdge = new int[10];
									//System.out.println(rules.isAccessible(true, i, field, gameBoardEdge));

									HelperClass.removeStoneId(stoneId, field, alb);
									alb.get(i).get(j).setText(stoneId);

									//Bewege den Stein auf neues Feld

									tileMapObject.setX(alb.get(i).get(j).getX());
									tileMapObject.setY(alb.get(i).get(j).getY());


									//CheckBoard ArrayList Label

									//PrintBoard mit int[][]
									PrintBoard.printBoard(field);
									resetField();
									if(((movedDiceOne && movedDiceTwo) || movedWithTwoStone) || isMovedDiceOne || isMovedDiceTwo)
									{
										whoesTurn();
										movedWithTwoStone = false;
										movedDiceOne = false;
										movedDiceTwo = false;
										isMovedDiceOne = false;
										isMovedDiceTwo = false;
									}
									this.printGameBar(playerTwo, true);
									this.printGameBar(playerOne, false);

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
		//PrintBoard.printBoard(field);

		//board.adjustReverenceSet(-2);
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
		//System.out.println(playerOne.permissionToMoveStoneOutOfBoard(board,diceNumber2,22));
		//System.out.println("Board: "+ playerOne.permissionToMoveStoneOutOfBoard(board, 2,0));
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
				isMovedDiceOne = true;
			}
			else if(indexIStone+diceNumber2>23 && !isMovedDiceTwo)
			{
				stone.setX(whiteLabelDismantle.getX());
				stone.setY(whiteLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				isMovedDiceTwo = true;
			}
			else if(indexIStone+diceNumber2+diceNumber1 >23 && !isMovedDiceOne && !isMovedDiceTwo)
			{
				stone.setX(whiteLabelDismantle.getX());
				stone.setY(whiteLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				whoesTurn();
			}
		}
		else {
			if(indexIStone-diceNumber1<0 && !isMovedDiceOne)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				isMovedDiceOne = true;
			}
			else if(indexIStone-diceNumber2<0 && !isMovedDiceTwo)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				isMovedDiceTwo = true;
			}
			else if(indexIStone-diceNumber2-diceNumber1 <0 && !isMovedDiceOne && !isMovedDiceTwo)
			{
				stone.setX(blackLabelDismantle.getX());
				stone.setY(blackLabelDismantle.getY());
				HelperClass.removeStoneId(stoneId, field, alb);
				board.adjustReverenceSet(stoneId);
				whoesTurn();
			}
		}
	}
}