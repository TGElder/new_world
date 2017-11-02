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
            new Edge<>(nodes.get(0), nodes.get(1), 1),
            new Edge<>(nodes.get(0), nodes.get(2), 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactly(nodes.get(2));
  }

  @Test
  public void shouldNotReturnStartingNode() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(0), nodes.get(0), 1),
            new Edge<>(nodes.get(0), nodes.get(2), 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactly(nodes.get(2));
  }

  @Test
  public void shouldNotReturnStartingNodeVariant() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(0), nodes.get(1), 1),
            new Edge<>(nodes.get(1), nodes.get(0), 1),
            new Edge<>(nodes.get(0), nodes.get(2), 3)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactly(nodes.get(2));
  }

  @Test
  public void shouldNotTravelBackwards() {
    ImmutableList<Integer> nodes = generateNodes(5);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(2), nodes.get(0), 1),
            new Edge<>(nodes.get(0), nodes.get(4), 2)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactly(nodes.get(4));
  }

  @Test
  public void twoEquidistantNodes() {
    ImmutableList<Integer> nodes = generateNodes(5);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(0), nodes.get(2), 1),
            new Edge<>(nodes.get(0), nodes.get(4), 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactlyInAnyOrder(nodes.get(2), nodes.get(4));
  }

  @Test
  public void closestRequiresMoreEdges() {
    ImmutableList<Integer> nodes = generateNodes(7);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(0), nodes.get(2), 10),
            new Edge<>(nodes.get(0), nodes.get(4), 10),
            new Edge<>(nodes.get(0), nodes.get(1), 1),
            new Edge<>(nodes.get(1), nodes.get(6), 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).containsExactly(nodes.get(6));
  }

  @Test
  public void noReachableNodeSatisfyingPredicate() {
    ImmutableList<Integer> nodes = generateNodes(3);

    ImmutableList<Edge<Integer>> edges = ImmutableList.of(
            new Edge<>(nodes.get(1), nodes.get(0), 1),
            new Edge<>(nodes.get(2), nodes.get(0), 1)
    );

    network = new Network<>(nodes, edges);

    assertThat(network.findClosest(nodes.get(0), predicate)).isEmpty();
  }

  @Test
  public void nodeWithNoEdges() {
    ImmutableList<Integer> nodes = generateNodes(1);

    network = new Network<>(nodes, ImmutableList.of());

    assertThat(network.findClosest(nodes.get(0), predicate)).isEmpty();
  }

}
