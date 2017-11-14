package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tgelder.network.Network;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocationTest {

  @Test
  @SuppressWarnings("unchecked")
  public void shouldAllocateClosestSupply() {
    int[] neighbourDXs = {-1, 0, 1, 0};
    int[] neighbourDYs = {0, -1, 0, 1};

    Network network = Network.createGridNetwork(10, 1, neighbourDXs, neighbourDYs);

    Allocation allocation = new Allocation(ImmutableList.of(5),
                                           ImmutableMap.of(1, 1, 6, 1),
                                           network);

    assertThat(allocation.getMetDemand()).containsExactly(5);
    assertThat(allocation.getMetSupply()).containsExactly(6);
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).containsExactly(1);

  }
}
