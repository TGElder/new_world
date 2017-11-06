package com.tgelder;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.network.Network;
import com.tgelder.newworld.NetworkFromTerrain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class TerrainInfo implements InputProcessor {

  private final Terrain terrain;
  private final OrthographicCamera camera;
  private final Vector3 screenCoords = new Vector3();

  private final Network<Integer> network;
  private final static int[] neighbourDxs = {-1, -1, 0, 1, 1, 1, 0, -1};
  private final static int[] neighbourDys = {0, -1, -1, -1, 0, 1, 1, 1};

  public TerrainInfo(Terrain terrain, OrthographicCamera camera) {
    this(terrain,
            camera,
            NetworkFromTerrain.createNetwork(terrain.getAltitudes(),
                    d -> Math.abs(d),
                    neighbourDxs,
                    neighbourDys
            ));
  }

  @Getter
  private int x = 0;
  @Getter
  private int y = 0;
  @Getter
  private double altitude = 0;
  @Getter
  private Set<Integer> nearestNodes = Collections.emptySet();

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

    if (screenX != screenCoords.x || screenY != screenCoords.y) {
      screenCoords.set(screenX, screenY, 0);
      Vector3 terrainCoords = camera.unproject(screenCoords);
      x = (int) (terrainCoords.x);
      y = terrain.getWidth() - (int) (terrainCoords.y);

      if (terrain.inBounds(x, y)) {
        altitude = terrain.getAltitudes()[x][y];
        nearestNodes = network.getNodes((y * terrain.getWidth()) + x, 25);
      } else {
        altitude = -1;
        nearestNodes = Collections.emptySet();
      }
    }

    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
