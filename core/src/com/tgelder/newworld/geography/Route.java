package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.tgelder.network.Edge;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Route {

  private final Settlement from;
  private final Settlement to;
  private final ImmutableList<Edge<Integer>> edges;
  private final double cost;

  public Route(Settlement from, Settlement to, ImmutableList<Edge<Integer>> edges) {
    this(from, to, edges, edges.stream().mapToDouble(Edge::getCost).sum());
  }

}
