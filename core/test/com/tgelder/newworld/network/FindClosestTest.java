package com.tgelder.newworld.network;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FindClosestTest {

  private Network<Integer> network;

  private final Predicate<Integer> predicate = i -> i % 2 == 0;

  private ImmutableList<Integer> generateNodes(int howMany) {
    return IntStream.range(0, howMany).boxed().collect(ImmutableList.toImmutableList());
  }

  @Test
  public void shouldNotReturnNodeFailingPredicate() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(0, 1, 1),
            new Edge<>(0, 2, 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).containsExactly(2);
  }

  @Test
  public void shouldNotReturnStartingNode() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(0, 0, 1),
            new Edge<>(0, 2, 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).containsExactly(2);
  }

  @Test
  public void shouldNotReturnStartingNodeVariant() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(0, 1, 1),
            new Edge<>(1, 0, 1),
            new Edge<>(0, 2, 3)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).containsExactly(2);
  }

  @Test
  public void shouldNotTravelBackwards() {
    ImmutableList<Integer> nodes = generateNodes(5);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(2, 0, 1),
            new Edge<>(0, 4, 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).containsExactly(4);
  }

  @Test
  public void twoEquidistantNodes() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(1, 0, 1),
            new Edge<>(1, 2, 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(1, predicate)).containsExactlyInAnyOrder(0, 2);
  }

  @Test
  public void closestRequiresMoreEdges() {
    ImmutableList<Integer> nodes = generateNodes(7);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(0, 2, 10),
            new Edge<>(0, 4, 10),
            new Edge<>(0, 1, 1),
            new Edge<>(1, 6, 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).containsExactly(6);
  }

  @Test
  public void noReachableNodeSatisfyingPredicate() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(1, 0, 1),
            new Edge<>(2, 0, 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(0, predicate)).isEmpty();
  }

  @Test
  public void nodeWithNoEdges() {
    ImmutableList<Integer> nodes = generateNodes(1);

    network = new Network<>(nodes, ImmutableList.of());

    assertThat(network.findClosest(0, predicate)).isEmpty();
  }

  @Test
  public void duplicateEdges() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(1, 0, 4),
            new Edge<>(1, 0, 1),
            new Edge<>(1, 2, 2),
            new Edge<>(1, 2, 1)
    );

    network = new Network<>(nodes, ImmutableList.of());

    assertThat(network.findClosest(1, predicate)).containsExactlyInAnyOrder(0, 2);
  }

}
