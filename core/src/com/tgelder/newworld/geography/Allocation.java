package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.tgelder.network.FindClosest;
import com.tgelder.network.Network;
import com.tgelder.network.NetworkSearch;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class Allocation {

  private final ImmutableList<Integer> metDemand;
  private final ImmutableList<Integer> metSupply;
  private final ImmutableList<Integer> unmetDemand;
  private final ImmutableList<Integer> unmetSupply;

  public Allocation(ImmutableList<Integer> demand,
                    Map<Integer, Integer> supply,
                    Network<Integer> network) {

    ImmutableList.Builder<Integer> metDemandBuilder = ImmutableList.builder();
    ImmutableList.Builder<Integer> metSupplyBuilder = ImmutableList.builder();
    ImmutableList.Builder<Integer> unmetDemandBuilder = ImmutableList.builder();
    ImmutableList.Builder<Integer> unmetSupplyBuilder = ImmutableList.builder();

    int[] free = new int[network.getNodes().size()];
    int totalFree = 0;


    for (Map.Entry<Integer, Integer> entry : supply.entrySet()) {
      int supplyAtSettlement = entry.getValue();
      free[entry.getKey()] = supplyAtSettlement;
      totalFree += supplyAtSettlement;
    }

    for (Integer d : demand) {

      if (totalFree == 0) {
        unmetDemandBuilder.add(d);
      } else {
        Set<Integer> candidates = NetworkSearch.search(network, d,
                                                       new FindClosest<>((n) -> free[n] > 0));

        if (candidates.isEmpty()) {
          unmetDemandBuilder.add(d);
        } else {
          int choiceIndex = candidates.stream().findFirst().get();
          metDemandBuilder.add(d);
          metSupplyBuilder.add(choiceIndex);
          free[choiceIndex]--;
        }

      }
    }

    for (int s = 0; s < free.length; s++) {
      while (free[s] > 0) {
        unmetSupplyBuilder.add(s);
        free[s]--;
      }
    }

    metDemand = metDemandBuilder.build();
    metSupply = metSupplyBuilder.build();
    unmetDemand = unmetDemandBuilder.build();
    unmetSupply = unmetSupplyBuilder.build();
  }

}
