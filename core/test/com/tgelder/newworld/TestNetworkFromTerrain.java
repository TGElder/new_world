package com.tgelder.newworld;

import com.tgelder.TestUtils;
import com.tgelder.network.Network;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNetworkFromTerrain {

  @Test
  public void testCreateNetwork() {
    final double[][] altitudes = {{0, 3}, {4, 2}};
    final int[] neighbourDxs = {-1, -1, 0, 1, 1, 1, 0, -1};
    final int[] neighbourDys = {0, -1, -1, -1, 0, 1, 1, 1};

    Network<Integer> network = NetworkFromTerrain.createNetwork(
            altitudes,
            d -> Math.abs(d),
            neighbourDxs,
            neighbourDys
    );

    assertThat(network.getOut(0).count()).isEqualTo(3);
    assertThat(network.getEdges(0, 0)).isEmpty();
    assertThat(network.getEdges(0, 1).findFirst().get().getCost()).isEqualTo(4);
    assertThat(network.getEdges(0, 2).findFirst().get().getCost()).isEqualTo(3);
    assertThat(network.getEdges(0, 3).findFirst().get().getCost()).isCloseTo(1.41421, TestUtils.PRECISION);

    assertThat(network.getOut(1).count()).isEqualTo(3);
    assertThat(network.getEdges(1, 0).findFirst().get().getCost()).isEqualTo(4);
    assertThat(network.getEdges(1, 1)).isEmpty();
    assertThat(network.getEdges(1, 2).findFirst().get().getCost()).isCloseTo(0.70711, TestUtils.PRECISION);
    assertThat(network.getEdges(1, 3).findFirst().get().getCost()).isEqualTo(2);

    assertThat(network.getOut(2).count()).isEqualTo(3);
    assertThat(network.getEdges(2, 0).findFirst().get().getCost()).isEqualTo(3);
    assertThat(network.getEdges(2, 1).findFirst().get().getCost()).isCloseTo(0.70711, TestUtils.PRECISION);
    assertThat(network.getEdges(2, 2)).isEmpty();
    assertThat(network.getEdges(2, 3).findFirst().get().getCost()).isEqualTo(1);

    assertThat(network.getOut(3).count()).isEqualTo(3);
    assertThat(network.getEdges(3, 0).findFirst().get().getCost()).isCloseTo(1.41421, TestUtils.PRECISION);
    assertThat(network.getEdges(3, 1).findFirst().get().getCost()).isEqualTo(2);
    assertThat(network.getEdges(3, 2).findFirst().get().getCost()).isEqualTo(1);
    assertThat(network.getEdges(3, 3)).isEmpty();
  }

}
