package com.tgelder.newworld.geography;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Resource {

  private final ResourceType type;
  private final Settlement location;

}
