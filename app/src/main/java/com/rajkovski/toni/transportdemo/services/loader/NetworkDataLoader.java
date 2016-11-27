package com.rajkovski.toni.transportdemo.services.loader;

import java.io.InputStream;

/**
 * Loads the data from the network.
 */
public class NetworkDataLoader implements IDataLoader {

  @Override
  public InputStream loadData(String from) {
    throw new RuntimeException("not implemented");
  }

}
