package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.tgelder.network.FindClosest;
import com.tgelder.network.Network;
import com.tgelder.network.NetworkSearch;
import lombok.Getter;

import java.util.Set;

@Getter
public class Allocation {

  private final ImmutableList<Resource> metDemand;
  private final ImmutableList<Resource> metSupply;
  private final ImmutableList<Resource> unmetDemand;
  private final ImmutableList<Resource> unmetSupply;

  public Allocation(ResourceType resourceType, ImmutableList<Settlement> settlements, Network<Integer> network) {

    ImmutableList.Builder<Resource> metDemandBuilder = ImmutableList.builder();
    ImmutableList.Builder<Resource> metSupplyBuilder = ImmutableList.builder();
    ImmutableList.Builder<Resource> unmetDemandBuilder = ImmutableList.builder();
    ImmutableList.Builder<Resource> unmetSupplyBuilder = ImmutableList.builder();

    int[] demandUnmet = new int[settlements.size()];
    int totalDemandUnmet = 0;

    for (Settlement settlement : settlements) {
      int demandAtSettlement = settlement.getDemand().get(resourceType).size();
      demandUnmet[settlement.getIndex()] = demandAtSettlement;
      totalDemandUnmet += demandAtSettlement;
    }

    for (Settlement settlement : settlements) {

      for (Resource supply : settlement.getSupply().get(resourceType)) {
        if (totalDemandUnmet == 0) {
          unmetSupplyBuilder.add(supply);
        } else {

          Set<Integer> demandSettlements = NetworkSearch.search(network, settlement.getIndex(),
                  new FindClosest<>(settlement.getIndex(), (n) -> demandUnmet[settlement.getIndex()] > 0));

          if (demandSettlements.isEmpty()) { //TODO test for this case
            unmetSupplyBuilder.add(supply);
          } else {
            int demandSettlement = demandSettlements.stream().findFirst().get();
            Resource demand = settlements.get(demandSettlement).getDemand().get(resourceType).get(demandUnmet[demandSettlement] - 1);
            metSupplyBuilder.add(supply);
            metDemandBuilder.add(demand);
            demandUnmet[demandSettlement]--;
          }

        }
      }
    }

    int d;
    for (Settlement settlement : settlements) {
      while ((d = demandUnmet[settlement.getIndex()]) > 0) {
        unmetDemandBuilder.add(settlements.get(d).getDemand().get(resourceType).get(demandUnmet[d] - 1));
        demandUnmet[settlement.getIndex()]--;
      }
    }

    metDemand = metDemandBuilder.build();
    metSupply = metSupplyBuilder.build();
    unmetDemand = unmetDemandBuilder.build();
    unmetSupply = unmetSupplyBuilder.build();
  }

  private boolean unmetDemandAt(boolean demandMet[][], int settlement) {
    for (int d = 0; d < demandMet.length; d++) {
      if (!demandMet[settlement][d]) {
        return true;
      }
    }
    return false;
  }

  private int indexOfUnmetDemandAt(boolean demandMet[][], int settlement) {
    for (int d = 0; d < demandMet.length; d++) {
      if (!demandMet[settlement][d]) {
        return d;
      }
    }
    return -1;
  }

}
