package backgammon.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Backgammon extends Game implements InputProcessor {


	OrthographicCamera camera, font_camera;
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


		gameBoardRenderer.setView(camera);
		gameBoardRenderer.render();

		batch.begin();
		for(int i=0;i<13;i++)
		{
			for(int j=0;j<5;j++) {
				if (i == 6) {
					alb.get(i).get(j).setPosition(895 - (64 * (i + 1)), 830 - (64 * j));
					alb.get(i).get(j).draw(batch, 1);

				} else {
					alb.get(i).get(j).setPosition(895 - (64 * i), 830 - (64 * j));
					alb.get(i).get(j).draw(batch, 1);
				}
			}
			//System.out.println(i);
		}

		for(int i=13;i<26;i++)
		{
			for(int j=0;j<5;j++) {

                if(i==19)
				{
                    alb.get(i).get(j).setPosition(895 - (64 * (counter + 1)),65+(64*j));
                }else {
                    alb.get(i).get(j).setPosition(895 - (64 * counter) ,65+(64*j));
                }
                alb.get(i).get(j).draw(batch,1);
            }
			counter++;
		}
		batch.end();




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
		font.dispose();
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