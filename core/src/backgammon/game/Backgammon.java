package backgammon.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.btree.Task;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.DestructionListener;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Backgammon extends Game implements InputProcessor {

	OrthographicCamera camera;
	private TiledMap gameBoardMap;
	private TiledMapRenderer gameBoardRenderer;
	private Texture stone;
	public SpriteBatch batch;
	TiledMapRenderer objectGroupRenderer;
	BitmapFont font;

	private ShapeRenderer shape;
	float x;
	float y;
	private int labelId;
	private int stoneId;

	private final int STONE_WIDTH=64;
	private final int STONE_HEIGHT=64;

	ArrayList<ArrayList<Label>> alb;

	Texture dicebutton;
	Texture dicesheet;

	private final int FRAME_COLS = 6;
	private final int FRAME_ROWS = 1;
	Animation diceanimation;
	Animation diceanimation2;

	float stateTime;
	float stateTime2;

	private final int  DICE_BUTTON_WIDTH = 60;

	private final int  DICE_BUTTON_HEIGHT = 70;




	@Override
	public void create() {
		batch = new SpriteBatch();

		shape = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("bitmapHiero.fnt"));
		alb = new ArrayList<ArrayList<Label>>();
		Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED);
		for(int i=0;i<26;i++)
		{
			alb.add(new ArrayList<Label>());
			for(int j=0;j<5;j++)
			{
				Label label = new Label("0",labelStyle);
				alb.get(i).add(j,label);
				alb.get(i).get(j).setSize(32, 32);

			}

		}

		dicebutton = new Texture("assets/diceButton.png");

		dicesheet = new Texture("assets/sprites.png");


		//split to make equal split frames of dicesheet
		//devide through number of height and with to get the single frames
		TextureRegion[][] tmp = TextureRegion.split(dicesheet,
				dicesheet.getWidth()/FRAME_COLS, dicesheet.getHeight()/FRAME_ROWS);

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





		gameBoardMap = new TmxMapLoader().load("tiled/export/BackgammonBoard.tmx");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();


		gameBoardRenderer = new OrthogonalTiledMapRenderer(gameBoardMap);


		Gdx.input.setInputProcessor(this);
	}
	@Override
	public void render() {
		int counter=0;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//accumulates animation time
		stateTime += Gdx.graphics.getDeltaTime();
		stateTime2 += Gdx.graphics.getDeltaTime();


		gameBoardRenderer.setView(camera);
		gameBoardRenderer.render();



		for(MapObject object: gameBoardMap.getLayers().get("Stone").getObjects())
		{
			if(object instanceof RectangleMapObject)
			{
				Rectangle rect  = ((RectangleMapObject) object).getRectangle();
				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.rect(rect.x, rect.y, rect.width, rect.height);
				shape.end();
			}else if (object instanceof  CircleMapObject)
			{
				Circle circle = ((CircleMapObject) object).getCircle();
				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.circle(circle.x, circle.y, circle.radius);
				shape.end();
			} else if (object instanceof PolygonMapObject)
			{
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
				int y = Gdx.graphics.getHeight()-dicebutton.getHeight();
				int x = 50;
				batch.draw(dicebutton,50,Gdx.graphics.getHeight()-dicebutton.getHeight(),DICE_BUTTON_WIDTH,DICE_BUTTON_HEIGHT);
				if(Gdx.input.getX()< x + DICE_BUTTON_WIDTH && Gdx.input.getX()>x && Gdx.graphics.getHeight() -Gdx.input.getY()< y +DICE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY()>y) {
					if(Gdx.input.isTouched()) {
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
		Gdx.app.log("Mouse", "touch Down");
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
		float x;
		float y;
		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {

			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;

				x = tileMapObject.getX();
				y = tileMapObject.getY();



				if (x <= worldCoordinates.x && worldCoordinates.x <= x + width &&
						y <= worldCoordinates.y && worldCoordinates.y <= y + height) {
					Gdx.app.log("Stein angeklickt", "ID: " + tileMapObject.getProperties().get("id", Integer.class));
					stoneId = tileMapObject.getProperties().get("id", Integer.class);
				}
			}
		}
	}
	public void moveStone(int screenX, int screenY)
	{
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {

			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;
				labelId = tileMapObject.getProperties().get("id", Integer.class);
				if(labelId==stoneId) {
					for(int i=0;i<24;i++) {
						for(int j=0; j<5;j++)
						{
							if (alb.get(i).get(j).getX() <= worldCoordinates.x && worldCoordinates.x <= alb.get(i).get(j).getX() + 32 &&
									alb.get(i).get(j).getY() <= worldCoordinates.y && worldCoordinates.y <= alb.get(i).get(j).getY() + 32) {
								Gdx.app.log("Label", "Klicked");
								tileMapObject.setX(alb.get(i).get(j).getX());
								tileMapObject.setY(alb.get(i).get(j).getY());
							}
						}
					}
				}

			}
		}
	}
}