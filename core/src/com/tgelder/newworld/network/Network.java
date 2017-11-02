package com.tgelder.newworld.network;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.function.Predicate;

@AllArgsConstructor
public class Network<T> {

  private final ImmutableList<T> nodes;
  private final ImmutableList<Edge<T>> edges;

  public Collection<T> findClosest(T start, Predicate<T> stoppingCondition) {
    return null;
  }

}
