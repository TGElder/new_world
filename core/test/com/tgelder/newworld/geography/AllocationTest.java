package com.tgelder.newworld.geography;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.tgelder.network.Edge;
import com.tgelder.network.Network;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocationTest {

  private Network<Integer> getNetwork(int width) {
    int[] neighbourDXs = {-1, 1};
    int[] neighbourDYs = {0, 0};

    return Network.createGridNetwork(10, 1, neighbourDXs, neighbourDYs);
  }

  @Test
  public void shouldAllocateClosestSupply() {
    Allocation allocation = new Allocation(ImmutableList.of(5),
                                           ImmutableMap.of(1, 1, 6, 1),
                                           getNetwork(10));

    assertThat(allocation.getMetDemand()).containsExactly(5);
    assertThat(allocation.getMetSupply()).containsExactly(6);
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).containsExactly(1);
  }

  @Test
  public void supplyOnSameNode() {
    Allocation allocation = new Allocation(ImmutableList.of(1),
                                           ImmutableMap.of(1, 1),
                                           getNetwork(3));

    assertThat(allocation.getMetDemand()).containsExactly(1);
    assertThat(allocation.getMetSupply()).containsExactly(1);
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }

  @Test
  public void multipleSupplyOnOneNode() {
    Allocation allocation = new Allocation(ImmutableList.of(1, 2, 3),
                                           ImmutableMap.of(4, 5),
                                           getNetwork(4));

    assertThat(allocation.getMetDemand()).containsExactly(1, 2, 3);
    assertThat(allocation.getMetSupply()).containsExactly(4, 4, 4);
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).containsExactly(4, 4);
  }

  @Test
  public void multipleDemandOnOneNode() {
    Allocation allocation = new Allocation(ImmutableList.of(4, 4, 4, 4, 4),
                                           ImmutableMap.of(1, 1, 2, 1, 3, 1),
                                           getNetwork(4));

    assertThat(allocation.getMetDemand()).containsExactly(4, 4, 4);
    assertThat(allocation.getMetSupply()).containsExactly(3, 2, 1);
    assertThat(allocation.getUnmetDemand()).containsExactly(4, 4);
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }

  @Test
  public void multipleSupplyOnOneNodeAndMultipleDemandOnOneNode() {
    Allocation allocation = new Allocation(ImmutableList.of(1, 1),
                                           ImmutableMap.of(3, 2),
                                           getNetwork(3));

    assertThat(allocation.getMetDemand()).containsExactly(1, 1);
    assertThat(allocation.getMetSupply()).containsExactly(3, 3);
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }


  @Test
  public void firstInDemandListShouldHavePriority() {
    Allocation allocation = new Allocation(ImmutableList.of(0, 9),
                                           ImmutableMap.of(8, 1),
                                           getNetwork(10));

    assertThat(allocation.getMetDemand()).containsExactly(0);
    assertThat(allocation.getMetSupply()).containsExactly(8);
    assertThat(allocation.getUnmetDemand()).containsExactly(9);
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }

  @Test
  public void noDemand() {
    Allocation allocation = new Allocation(ImmutableList.of(),
                                           ImmutableMap.of(1, 1),
                                           getNetwork(3));

    assertThat(allocation.getMetDemand()).isEmpty();
    assertThat(allocation.getMetSupply()).isEmpty();
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).containsExactly(1);
  }

  @Test
  public void noSupply() {
    Allocation allocation = new Allocation(ImmutableList.of(1),
                                           ImmutableMap.of(),
                                           getNetwork(3));

    assertThat(allocation.getMetDemand()).isEmpty();
    assertThat(allocation.getMetSupply()).isEmpty();
    assertThat(allocation.getUnmetDemand()).containsExactly(1);
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }

  @Test
  public void noDemandOrSupply() {
    Allocation allocation = new Allocation(ImmutableList.of(),
                                           ImmutableMap.of(),
                                           getNetwork(3));

    assertThat(allocation.getMetDemand()).isEmpty();
    assertThat(allocation.getMetSupply()).isEmpty();
    assertThat(allocation.getUnmetDemand()).isEmpty();
    assertThat(allocation.getUnmetSupply()).isEmpty();
  }

  @Test
  public void inaccessibleSupply() {
    ImmutableSet<Integer> nodes = ImmutableSet.of(0, 1, 2);
    ImmutableSet<Edge<Integer>> edges = ImmutableSet.of(new Edge<>(0, 1, 1),
                                                        new Edge<>(2, 1, 1));
    Network<Integer> network = new Network<>(nodes, edges);

    Allocation allocation = new Allocation(ImmutableList.of(0),
                                           ImmutableMap.of(2, 2),
                                           network);

    assertThat(allocation.getMetDemand()).isEmpty();
    assertThat(allocation.getMetSupply()).isEmpty();
    assertThat(allocation.getUnmetDemand()).containsExactly(0);
    assertThat(allocation.getUnmetSupply()).containsExactly(2, 2);

  }


}
