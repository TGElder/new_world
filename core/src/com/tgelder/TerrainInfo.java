package com.tgelder;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tgelder.downhill.terrain.Terrain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TerrainInfo implements InputProcessor {

  private final Terrain terrain;
  private final OrthographicCamera camera;
  private final Vector3 screenCoords = new Vector3();

  @Getter
  private int x = 0;
  @Getter
  private int y = 0;
  @Getter
  private double altitude = 0;

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
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    screenCoords.set(screenX, screenY, 0);
    Vector3 terrainCoords = camera.unproject(screenCoords);
    x = (int)(terrainCoords.x);
    y = terrain.getWidth() - (int)(terrainCoords.y);

    if (terrain.inBounds(x, y)) {
      altitude = terrain.getAltitudes()[x][y];
    }
    else {
      altitude = -1;
    }

    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
