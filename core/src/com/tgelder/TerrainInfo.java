package com.tgelder;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.tgelder.downhill.terrain.DownhillException;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.network.Edge;
import com.tgelder.network.Network;
import com.tgelder.network.search.EuclideanHeuristic;
import com.tgelder.network.search.FindWithinCost;
import com.tgelder.network.search.NetworkSearch;
import com.tgelder.network.search.Pathfinder;
import com.tgelder.newworld.NetworkFromTerrain;
import com.tgelder.newworld.geography.Geography;
import com.tgelder.newworld.geography.Settlement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class TerrainInfo implements InputProcessor {

  private final Terrain terrain;
  @Getter
  private Geography geography;
  private EuclideanHeuristic heuristic;
  private final OrthographicCamera camera;
  private final Vector3 screenCoords = new Vector3();

  private final Network<Integer> network;
  private final static int[] neighbourDxs = {-1, -1, 0, 1, 1, 1, 0, -1};
  private final static int[] neighbourDys = {0, -1, -1, -1, 0, 1, 1, 1};

  public TerrainInfo(Terrain terrain, OrthographicCamera camera) {
    this.terrain = terrain;
    this.camera = camera;

    Network<Integer> terrainNetwork = NetworkFromTerrain.createNetwork(terrain.getAltitudes(),
                                                                       Math::abs,
                                                                       neighbourDxs,
                                                                       neighbourDys);

    geography = new Geography(terrain,
                              terrainNetwork,
                              ImmutableList.of(),
                              new Network<Integer>(ImmutableSet.of(), ImmutableSet.of()),
                              ImmutableMap.of());

    heuristic = new EuclideanHeuristic(terrain.getWidth(), terrain.getWidth(), 1);


    boolean[][] water = new boolean[terrain.getWidth()][terrain.getWidth()];

    try {

      for (int x = 0; x < terrain.getWidth(); x++) {
        for (int y = 0; y < terrain.getWidth(); y++) {
          if (terrain.getFlow()[x][y] >= terrain.getWidth()) {
            water[x][y] = true;
          }
        }
      }
    } catch (DownhillException e) {
      throw new RuntimeException();
    }

    Network<Integer> waterNetwork = NetworkFromTerrain.createWaterNetwork(water,
                                                                          0.1,
                                                                          1,
                                                                          neighbourDxs,
                                                                          neighbourDys);

    network = terrainNetwork.merge(waterNetwork);
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
    Settlement settlement = new Settlement(geography.getSettlements().size(),
                                           x,
                                           y,
                                           (y * terrain.getWidth()) + x);

    if (!geography.getSettlements().isEmpty()) {
      Settlement from = geography.getSettlements().get(geography.getSettlements().size() - 1);
      Optional<ImmutableList<Edge<Integer>>> path = Pathfinder.find(geography.getTerrainNetwork(),
                                                                    from.getTerrainIndex(),
                                                                    settlement.getTerrainIndex(),
                                                                    heuristic);
      if (path.isPresent()) {
        double cost = path.get().stream().mapToDouble(Edge::getCost).sum();
        Edge<Integer> road = new Edge<>(from.getIndex(), settlement.getIndex(), cost);
        geography = geography.copyWith(ImmutableSet.of(settlement), ImmutableMap.of(road, path.get()));
      }
    } else {
      geography = geography.copyWith(ImmutableSet.of(settlement), ImmutableMap.of());
    }

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
        nearestNodes = NetworkSearch.search(network, (y * terrain.getWidth()) + x, new FindWithinCost<>(10));
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
