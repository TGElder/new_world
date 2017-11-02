package com.tgelder.newworld.network;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Edge<T> {

  private final T from;
  private final T to;
  private int cost;

}
