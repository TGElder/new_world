package com.tgelder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.tgelder.downhill.renderer.HeightRenderer;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.image.PixmapImage;

public class TerrainViewer extends ApplicationAdapter {
  private Terrain terrain;
  private SpriteBatch batch;
  private SpriteBatch batch2;
  private Sprite sprite = new Sprite();
  private BitmapFont font;
  private OrthographicCamera cam;
  private TerrainInfo terrainInfo;
  private int seaLevel = 0;
  private final static HeightRenderer renderer = new HeightRenderer();
  private PixmapImage image;
  private Texture texture;

  @Override
  public void create () {

    batch = new SpriteBatch();
    batch2 = new SpriteBatch();



    terrain = new Terrain(1986, 10, 3000);
    image = new PixmapImage(terrain.getWidth(), terrain.getWidth());
    texture = new Texture(terrain.getWidth(), terrain.getWidth(), Pixmap.Format.RGB888);
    updateSprite();
    sprite = new Sprite(texture);

    cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    terrainInfo = new TerrainInfo(terrain, cam);

    InputMultiplexer multiplexer = new InputMultiplexer();
    multiplexer.addProcessor(new GestureDetector(new OrphographicCameraController(cam)));
    multiplexer.addProcessor(terrainInfo);

    Gdx.input.setInputProcessor(multiplexer);

    font = new BitmapFont();
    font.setColor(Color.RED);
  }

  @Override
  public void render () {

    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      seaLevel = Math.min(seaLevel + 5, 3000);
      updateSprite();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      seaLevel = Math.max(seaLevel - 5, 0);
      updateSprite();
    }

    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.setProjectionMatrix(cam.combined);

    batch.begin();
    sprite.setPosition(0, 0);
    sprite.draw(batch);
    batch.end();

    batch2.begin();
    font.draw(batch2, terrainInfo.getAltitude() + "\n" + terrainInfo.getX() + "\n" + terrainInfo.getY()
            , 200, 200);
    batch2.end();

  }

  @Override
  public void dispose () {
    batch.dispose();
  }

  @Override
  public void resize(int width, int height) {
    cam.viewportWidth = Gdx.graphics.getWidth();
    cam.viewportHeight = Gdx.graphics.getHeight();
    cam.update();
  }


  private void updateSprite() {



    renderer.render(terrain.getAltitudes(), seaLevel, 3000, image);

    texture.draw(image.getPixmap(), 0, 0);
    //sprite = new Sprite(texture);

  }

}
