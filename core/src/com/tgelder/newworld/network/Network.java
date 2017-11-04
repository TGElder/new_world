package com.tgelder.newworld.network;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@AllArgsConstructor
public class Network<T> {

  private final ImmutableSet<T> nodes;
  private final ImmutableSet<Edge<T>> edges;

  public Stream<T> getBelow(T node) {
    return getOut(node).map(e -> e.getTo()).distinct();
  }

  public Stream<T> getAbove(T node) {
    return getIn(node).map(e -> e.getFrom()).distinct();
  }

  public Stream<Edge<T>> getOut(T node) {
    return edges.stream().filter(e -> e.getFrom().equals(node));
  }

  public Stream<Edge<T>> getIn(T node) {
    return edges.stream().filter(e -> e.getTo().equals(node));
  }

  public Stream<Edge<T>> getEdges(T from, T to) {
    return edges.stream().filter(e -> e.getFrom().equals(from) && e.getTo().equals(to));
  }

  public Stream<Edge<T>> getReverses(Edge<T> edge) {
    return getEdges(edge.getTo(), edge.getFrom());
  }


  public Set<T> findClosest(T start, Predicate<T> stoppingCondition) {
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
