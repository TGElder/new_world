package com.tgelder.newworld.network;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class Network<T> {

  private final ImmutableSet<T> nodes;
  private final ImmutableSet<Edge<T>> edges;

  public Stream<T> getBelow(T node) {
    return getOut(node).map(Edge::getTo).distinct();
  }

  public Stream<T> getAbove(T node) {
    return getIn(node).map(Edge::getFrom).distinct();
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

    Map<T, Integer> open = new HashMap<>();
    Set<T> closed = new HashSet<>();

    open.put(start, 0);

    Set<T> out = new HashSet<>();
    int closestCost = 0;

    while (!open.isEmpty()) {
      T focus = Collections.min(open.keySet(), Comparator.comparingInt(open::get));
      int focusCost = open.get(focus);

      open.remove(focus);
      closed.add(focus);

      if ((!focus.equals(start)) && stoppingCondition.test(focus)) {
        if (out.isEmpty()) {
          out.add(focus);
          closestCost = focusCost;
        } else if (focusCost == closestCost) {
          out.add(focus);
        } else if (focusCost > closestCost) {
          return out;
        } else {
          throw new RuntimeException("Invalid state");
        }
      }
      
      Map<T, Optional<Edge<T>>> neighbours = getOut(focus)
              .filter(e -> !closed.contains(e.getTo()))
              .collect(Collectors.groupingBy(Edge::getTo, Collectors.minBy(Comparator.comparingInt(Edge::getCost))));

      neighbours.forEach((node, edgeOptional) -> {
        int cost = focusCost + edgeOptional.get().getCost();
        open.merge(node, cost, Math::min);
      });
    }

    return out;

  }

  @AllArgsConstructor
  @Getter
  private class Exit {
    private final T neighbour;
    private final Edge edge;
  }

}
