package com.tgelder.newworld;

import com.tgelder.TestUtils;
import com.tgelder.network.Network;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNetworkFromTerrain {


  @Test
  public void testCreateNetwork() {
    final double[][] altitudes = {{0, 3}, {4, 2}};

    int[] neighbourDxs = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] neighbourDys = {0, -1, -1, -1, 0, 1, 1, 1};

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

  @Test
  public void testCreateWaterNetwork() {
    final boolean[][] water = {{true, true, false}, {false, true, true}, {false, false, false}};

    int[] neighbourDxs = {-1, 0, 1, 0};
    int[] neighbourDys = {0, -1, 0, 1};

    Network<Integer> network = NetworkFromTerrain.createWaterNetwork(
            water,
            1,
            10,
            neighbourDxs,
            neighbourDys
    );

    assertThat(network.getOut(0).count()).isEqualTo(2);
    assertThat(network.getEdges(0, 1).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(0, 3).findFirst().get().getCost()).isEqualTo(1);

    assertThat(network.getOut(1).count()).isEqualTo(2);
    assertThat(network.getEdges(1, 0).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(1, 4).findFirst().get().getCost()).isEqualTo(10);

    assertThat(network.getOut(2).count()).isEqualTo(0);

    assertThat(network.getOut(3).count()).isEqualTo(3);
    assertThat(network.getEdges(3, 0).findFirst().get().getCost()).isEqualTo(1);
    assertThat(network.getEdges(3, 4).findFirst().get().getCost()).isEqualTo(1);
    assertThat(network.getEdges(3, 6).findFirst().get().getCost()).isEqualTo(10);

    assertThat(network.getOut(4).count()).isEqualTo(4);
    assertThat(network.getEdges(4, 1).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(4, 3).findFirst().get().getCost()).isEqualTo(1);
    assertThat(network.getEdges(4, 5).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(4, 7).findFirst().get().getCost()).isEqualTo(1);

    assertThat(network.getOut(5).count()).isEqualTo(1);
    assertThat(network.getEdges(5, 4).findFirst().get().getCost()).isEqualTo(10);

    assertThat(network.getOut(6).count()).isEqualTo(2);
    assertThat(network.getEdges(6, 3).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(6, 7).findFirst().get().getCost()).isEqualTo(10);

    assertThat(network.getOut(7).count()).isEqualTo(3);
    assertThat(network.getEdges(7, 4).findFirst().get().getCost()).isEqualTo(1);
    assertThat(network.getEdges(7, 6).findFirst().get().getCost()).isEqualTo(10);
    assertThat(network.getEdges(7, 8).findFirst().get().getCost()).isEqualTo(10);

    assertThat(network.getOut(8).count()).isEqualTo(1);
    assertThat(network.getEdges(8, 7).findFirst().get().getCost()).isEqualTo(10);
  }



}
