package com.tgelder.newworld;

import com.google.common.collect.ImmutableSet;
import com.tgelder.network.Edge;
import com.tgelder.network.Network;

import java.util.function.Function;
import java.util.stream.IntStream;

public class NetworkFromTerrain {

  public static Network<Integer> createNetwork(double[][] altitudes,
                                               Function<Double, Double> gradientToCost,
                                               int[] neighbourDxs,
                                               int[] neighbourDys) {
    ImmutableSet<Integer> nodes = IntStream
            .range(0, altitudes.length * altitudes[0].length)
            .boxed()
            .collect(ImmutableSet.toImmutableSet());

    ImmutableSet.Builder<Edge<Integer>> edgeBuilder = ImmutableSet.builder();

    System.out.println("Building network");
    for (int x = 0; x < altitudes.length; x++) {
      System.out.println(x);
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

  private static boolean inBounds(double[][] altitudes, int x, int y) {
    return x >= 0 && y >= 0 && x < altitudes.length && y < altitudes[0].length;
  }

  private static Integer getIndex(double[][] altitudes, int x, int y) {
    return (y * altitudes.length) + x;
  }


}
