package com.rajkovski.toni.transportdemo.services.svg;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps the images into memory cache.
 */
public class MemorySvgCache implements ISvgCache {

  private Map<String, byte[]> cache = new HashMap<>();

  @Override
  public byte[] getImageByUrl(String url) {
    return cache.get(url);
  }

  @Override
  public void putImage(String url, byte[] image) {
    cache.put(url, image);
  }

}
