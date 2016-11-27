package com.rajkovski.toni.transportdemo.services.svg;

/**
 * Cache for SVG images.
 */
public interface ISvgCache {

  /**
   * Checks if the image exists in the cache and returns it.
   * If the image does not exists in the cache then null is returned;
   *
   * @param url the url of the image
   * @return bytes of the image or null
   */
  byte[] getImageByUrl(String url);

  /**
   * Adds the image into cache.
   *
   * @param url the url of the image
   * @param image the image
   */
  void putImage(String url, byte[] image);
}
