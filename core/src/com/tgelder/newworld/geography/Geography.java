package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tgelder.downhill.terrain.Terrain;
import com.tgelder.network.Edge;
import com.tgelder.network.Network;
import com.tgelder.network.search.EuclideanHeuristic;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Geography {

  private final EuclideanHeuristic heuristic;

  private final Terrain terrain;
  private final Network<Integer> terrainNetwork;
  private final ImmutableList<Settlement> settlements;
  private final Network<Integer> settlementNetwork;
  private final ImmutableMap<Edge<Integer>, ImmutableList<Edge<Integer>>> roads;

  public Geography(Terrain terrain,
                   Network<Integer> terrainNetwork,
                   ImmutableList<Settlement> settlements,
                   Network<Integer> settlementNetwork,
                   ImmutableMap<Edge<Integer>, ImmutableList<Edge<Integer>>> roads) {
    this(new EuclideanHeuristic(terrain.getWidth(), terrain.getWidth(), 1),
         terrain,
         terrainNetwork,
         settlements,
         settlementNetwork,
         roads);
  }


//  public Geography evolve() {
//    // get map of demand to supply
//
//  }

  // addSettlement
  // removeSettlement
  // grow (replace each settlement)
  // allocate
  // settle
  // - grow settlements 3
  // - calculate demand and supply 2
  // - allocate 1
  // - for each unsatisfied demand, find nearest supply 6
  // - find best settlement site near that supply 6
  // - add a settlement to the geography 4
  // - add a settlement there and rerun the allocation 6


  public Geography copyWith(Iterable<Settlement> settlements,
                            ImmutableMap<Edge<Integer>, ImmutableList<Edge<Integer>>> roads) {


    return new Geography(
            terrain,
            terrainNetwork,
            ImmutableList.<Settlement>builder().addAll(this.settlements).addAll(settlements).build(),
            settlementNetwork.copyWithEdges(roads.keySet()),
            ImmutableMap.<Edge<Integer>, ImmutableList<Edge<Integer>>>builder().putAll(this.roads).putAll(roads).build());

  }

//  public Map<ResourceType, Allocation> allocate() {
//
//  }

//  public Geography addSettlement(Settlement settlement, ImmutableSet<Settlement> neighbours) {
//    // Add settlement
//    // WOrk out roads to nearest settlements
//    // Add those as well
//  }
//
//  public Geography addRoad(Settlement from, Settlement to){
//    Optional<ImmutableList<Edge<Integer>>> road = getRoad(from, to);
//
//    if (!road.isPresent()) {
//      return this;
//    }
//    else {
//      Edge<Integer> settlementEdge = new Edge<>(from.getIndex(), to.getIndex(), cost(road.get()));
//
//      return new Geography(heuristic,
//                           terrain,
//                           terrainNetwork,
//                           settlements,
//                           settlementNetwork.copyWithEdges(ImmutableSet.of(settlementEdge)),
//                           ImmutableMap.<Edge<Integer>, ImmutableList<Edge<Integer>>>builder().putAll(roads).put(settlementEdge, road.get()).build());
//    }
//
//  }
//
//  public double cost(ImmutableList<Edge<Integer>> road) {
//    return road.size();
//  }
//
//  public Optional<ImmutableList<Edge<Integer>>> getRoad(Settlement from, Settlement to) {
//    return Pathfinder.find(terrainNetwork,
//                           from.getTerrainIndex(),
//                           to.getTerrainIndex(),
//                           heuristic);
//  }


}
