package com.rajkovski.toni.transportdemo.services.loader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Loads data from a specific source.
 */
public interface IDataLoader {

  /**
   * Loads the data.
   * The implementation can load the data locally or from network.
   * It is expected that this method is called on background thread.
   *
   * @param from the place that the data can be retreived(url or file location)
   * @return InputStream
   *
   * @throws IOException if problems with IO occurs.
   */
  InputStream loadData(String from) throws IOException;

}
