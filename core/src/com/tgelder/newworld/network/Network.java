package com.tgelder.newworld.network;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.function.Predicate;

@AllArgsConstructor
public class Network<T> {

  private final ImmutableList<T> nodes;
  private final ImmutableList<Edge<T>> edges;
//
//  public Network(ImmutableList<T> nodes, ImmutableList<Edge<T>> edges) {
//    this.nodes = nodes;
//    this.edges = edges;
//
//    for (Edge edges : )
//  }

  public Collection<T> getBelow(T node) {
    return null;
  }

  public Collection<T> getAbove(T node) {
    return null;
  }

  public Collection<Edge<T>> getOut(T node) {
    return null;
  }

  public Collection<Edge<T>> getIn(T node) {
    return null;
  }

  public Collection<Edge<T>> getEdges(T from, T to) {
    return null;
  }

  public Collection<Edge<T>> getReverses(Edge<T> edge) {
    return null;
  }


  public Collection<T> findClosest(T start, Predicate<T> stoppingCondition) {
//
//    List<T> open = new ArrayList<T> ();
//    Set<T> closed = new HashSet<T> ();
//    Map<T, Integer> costs = new HashMap<T, Integer>();
//
//
//
//
//
//
//    Comparator<T> openOrder = new Comparator<T>() {
//      @Override
//      public int compare(T a, T b) {
//        return costs.get(a).compareTo(costs.get(b));
//      }
//    }
//
    return null;

  }

  @AllArgsConstructor
  @Getter
  private class Exit {
    private final T neighbour;
    private final Edge edge;
  }

}
