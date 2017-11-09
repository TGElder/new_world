package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Settlement {

  private final String name;
  private final int index;
  private final int terrainX;
  private final int terrainY;
  private final int terrainIndex;
  private final int population;
  private final ImmutableMap<ResourceType, ImmutableList<Resource>> demand;
  private final ImmutableMap<ResourceType, ImmutableList<Resource>> supply;
}


