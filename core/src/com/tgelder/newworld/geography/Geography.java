package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.network.Network;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Geography {

  private final Terrain terrain;
  private final Network terrainNetwork;
  private final ImmutableList<Settlement> settlements;
  private final Network settlementNetwork;


//  public Geography evolve() {
//    // get map of demand to supply
//
//  }

}
