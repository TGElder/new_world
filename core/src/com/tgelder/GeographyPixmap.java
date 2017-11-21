package com.tgelder;

import com.badlogic.gdx.graphics.Pixmap;
import com.google.common.collect.ImmutableList;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.network.Edge;
import com.tgelder.newworld.geography.Geography;
import com.tgelder.newworld.geography.Settlement;
import lombok.Getter;

public class GeographyPixmap {

  @Getter
  private final Pixmap pixmap;

  public GeographyPixmap(int width, int height) {
    pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
  }

  public void draw(Terrain terrain, Geography geography) {
    pixmap.setColor(0, 0, 0, 0);
    pixmap.fill();
    pixmap.setColor(0, 1, 0, 1f);
    for (ImmutableList<Edge<Integer>> path : geography.getRoads().values()) {
      for (Edge<Integer> edge : path) {
        int x = edge.getTo() % terrain.getWidth();
        int y = edge.getTo() / terrain.getWidth();
        pixmap.drawPixel(x, y);
      }
    }

    pixmap.setColor(1, 0, 0, 1f);
    for (Settlement settlement : geography.getSettlements()) {
      int x = settlement.getTerrainX();
      int y = settlement.getTerrainY();
      pixmap.drawPixel(x, y);
    }

  }


}
