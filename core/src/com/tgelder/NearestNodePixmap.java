package com.tgelder;

import com.badlogic.gdx.graphics.Pixmap;
import com.tgelder.downhill.terrain.Terrain;
import lombok.Getter;

import java.util.Set;

public class NearestNodePixmap {

  @Getter
  private final Pixmap pixmap;

  public NearestNodePixmap(int width, int height) {
    pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
  }

  public void draw(Terrain terrain, Set<Integer> nodes) {
    pixmap.setColor(0, 0, 0, 0);
    pixmap.fill();
    pixmap.setColor(1, 1, 0, 1f);
    for (Integer node : nodes) {
      int x = node % terrain.getWidth();
      int y = node / terrain.getWidth();
      pixmap.drawPixel(x, y);
    }

  }


}
