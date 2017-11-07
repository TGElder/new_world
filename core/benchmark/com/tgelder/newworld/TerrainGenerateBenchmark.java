
package com.tgelder.newworld;

import com.tgelder.downhill.terrain.DownhillException;
import com.tgelder.downhill.terrain.Terrain;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class TerrainGenerateBenchmark {

  @Benchmark
  public void testGenerateTerrain(Blackhole blackhole) throws DownhillException {
    Terrain terrain = new Terrain(1986, 10, 3000);
    blackhole.consume(terrain.getFlow());
  }

}
