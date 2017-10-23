package com.tgelder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tgelder.downhill.renderer.HeightRenderer;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.image.PixmapImage;

public class TerrainViewer extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite sprite;
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		Terrain terrain = new Terrain(1986, 10);
		PixmapImage image = new PixmapImage(1024, 1024);
		HeightRenderer renderer = new HeightRenderer();
		renderer.render(terrain.getHeights(), terrain.getMinHeight(), terrain.getMaxHeight(), image);

		Texture texture = new Texture(image.getPixmap());
		sprite = new Sprite(texture);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.setPosition(0, 0);
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		create();
	}

}
