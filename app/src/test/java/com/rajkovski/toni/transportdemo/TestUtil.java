package com.rajkovski.toni.transportdemo;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility methods for tests.
 */
public final class TestUtil {

  /**
   * Loads a file resource.
   *
   * @return InputStream
   */
  public static InputStream loadResource(String file) {
    try {
      return TestUtil.class.getClassLoader().getResource(file).openStream();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
