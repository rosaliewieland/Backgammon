package backgammon.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.DestructionListener;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Backgammon extends Game implements InputProcessor {

	OrthographicCamera camera;
	private TiledMap gameBoardMap;
	private TiledMapRenderer gameBoardRenderer;
	private Texture stone;
	private SpriteBatch batch;
	TiledMapRenderer objectGroupRenderer;

	private ShapeRenderer shape;
	float x;
	float y;

	private final int STONE_WIDTH=64;
	private final int STONE_HEIGHT=64;


	@Override
	public void create() {
		batch = new SpriteBatch();

		shape = new ShapeRenderer();

		stone = new Texture("tiled/tilesets/whiteStone64_64.png");

		gameBoardMap = new TmxMapLoader().load("tiled/export/BackgammonBoard.tmx");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();


		gameBoardRenderer = new OrthogonalTiledMapRenderer(gameBoardMap);


		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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

				batch.begin();
				batch.draw(textureRegion, x, y, STONE_WIDTH, STONE_HEIGHT);
				batch.end();
			}
		}
	}


	@Override
	public void dispose() {
		batch.dispose();
		stone.dispose();
		gameBoardMap.dispose();
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

		if(button == Input.Buttons.LEFT)
		{
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
		return false;
	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;

	}

	public void checkObjectClicked(int screenX, int screenY) {
		Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));

		for (MapObject object : gameBoardMap.getLayers().get("Stone").getObjects()) {
			if (object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) object;

				// Hier wird die Position des Tile-Objekts abgerufen
				float x = tileMapObject.getX();
				float y = tileMapObject.getY();
				float width = 64;
				float height = 64;

				if (x <= worldCoordinates.x && worldCoordinates.x <= x + width &&
						y <= worldCoordinates.y && worldCoordinates.y <= y + height) {


					Gdx.app.log("Stein angeklickt", "ID: " + tileMapObject.getProperties().get("id", Integer.class));
				}
			}
		}
	}

}