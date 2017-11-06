package com.tgelder.newworld;

import com.google.common.collect.ImmutableSet;
import com.tgelder.network.Edge;
import com.tgelder.network.Network;

import java.util.function.Function;
import java.util.stream.IntStream;

public class NetworkFromTerrain {

  private static boolean inBounds(double[][] matrix, int x, int y) {
    return x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length;
  }

  private static Integer getIndex(double[][] matrix, int x, int y) {
    return (y * matrix.length) + x;
  }

  private static boolean inBounds(boolean[][] matrix, int x, int y) {
    return x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length;
  }

  private static Integer getIndex(boolean[][] matrix, int x, int y) {
    return (y * matrix.length) + x;
  }

  public static Network<Integer> createNetwork(double[][] altitudes,
                                               Function<Double, Double> gradientToCost,
                                               int[] neighbourDxs,
                                               int[] neighbourDys) {
    ImmutableSet<Integer> nodes = IntStream
            .range(0, altitudes.length * altitudes[0].length)
            .boxed()
            .collect(ImmutableSet.toImmutableSet());

    ImmutableSet.Builder<Edge<Integer>> edgeBuilder = ImmutableSet.builder();

    for (int x = 0; x < altitudes.length; x++) {
      for (int y = 0; y < altitudes[0].length; y++) {

        for (int n = 0; n < neighbourDxs.length; n++) {

          int dx = neighbourDxs[n];
          int dy = neighbourDys[n];

          int nx = x + dx;
          int ny = y + dy;

          if (inBounds(altitudes, nx, ny)) {
            double gradient = (altitudes[nx][ny] - altitudes[x][y]) / (Math.sqrt(Math.abs(dx) + Math.abs(dy)));
            edgeBuilder.add(new Edge<>(getIndex(altitudes, x, y), getIndex(altitudes, nx, ny), gradientToCost.apply(gradient)));
          }

        }
      }

    }

    return new Network<>(nodes, edgeBuilder.build());
  }

  public static Network<Integer> createWaterNetwork(boolean[][] water,
                                                    double waterCost,
                                                    double exitingWaterCost,
                                                    int[] neighbourDxs,
                                                    int[] neighbourDys) {

    ImmutableSet<Integer> nodes = IntStream
            .range(0, water.length * water[0].length)
            .boxed()
            .collect(ImmutableSet.toImmutableSet());

    ImmutableSet.Builder<Edge<Integer>> edgeBuilder = ImmutableSet.builder();


    for (int x = 0; x < water.length; x++) {
      for (int y = 0; y < water[0].length; y++) {
        if (water[x][y]) {
          for (int n = 0; n < neighbourDxs.length; n++) {

            int dx = neighbourDxs[n];
            int dy = neighbourDys[n];

            int nx = x + dx;
            int ny = y + dy;

            if (inBounds(water, nx, ny)) {

              if (water[nx][ny]) {
                edgeBuilder.add(new Edge<>(getIndex(water, x, y), getIndex(water, nx, ny), waterCost));
              } else {
                edgeBuilder.add(new Edge<>(getIndex(water, x, y), getIndex(water, nx, ny), exitingWaterCost));
                edgeBuilder.add(new Edge<>(getIndex(water, nx, ny), getIndex(water, x, y), exitingWaterCost));
              }
            }
          }
        }
      }

    }

    return new Network<>(nodes, edgeBuilder.build());
  }

}
