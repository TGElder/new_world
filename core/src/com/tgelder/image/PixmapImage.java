package com.tgelder.image;

import com.badlogic.gdx.graphics.Pixmap;
import com.tgelder.downhill.image.Image;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;

public class PixmapImage implements Image {

  @Getter
  private final Pixmap pixmap;

  public PixmapImage(int width, int height) {
    pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
  }

  @Override
  public void setColor(int r, int g, int b) {
    pixmap.setColor(r / 255f, g / 255f, b / 255f, 1);
  }

  @Override
  public void setColor(Color color) {
    setColor(color.getRed(), color.getGreen(), color.getBlue());
  }

  @Override
  public void drawPoint(int x, int y) {
    pixmap.drawPixel(x, y);
  }

  @Override
  public void drawLine(int ax, int ay, int bx, int by) {
    pixmap.drawLine(ax, ay, bx, by);
  }

  @Override
  public void save(String s) throws IOException {

  }

  @Override
  public int getWidth() {

    return pixmap.getWidth();
  }

  @Override
  public int getHeight() {

    return pixmap.getHeight();
  }
}
