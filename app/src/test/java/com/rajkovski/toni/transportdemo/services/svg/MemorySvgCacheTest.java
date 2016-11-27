package com.rajkovski.toni.transportdemo.services.svg;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for {@link MemorySvgCache}
 */
public class MemorySvgCacheTest {

  private MemorySvgCache memorySvgCache;

  @Before
  public void prepare() {
    memorySvgCache = new MemorySvgCache();
  }

  @Test
  public void getImageByUrl_success() {
    //given
    byte[] image = new byte[] {1, 2, 3};
    memorySvgCache.putImage("url1", image);

    //when
    byte[] result = memorySvgCache.getImageByUrl("url1");

    //then
    assertArrayEquals(image, result);
  }

  @Test
  public void getImageByUrl_miss() {
    //given
    byte[] image = new byte[] {1, 2, 3};
    memorySvgCache.putImage("url1", image);

    //when
    byte[] result = memorySvgCache.getImageByUrl("anotherUrl");

    //then
    assertNull(result);
  }

}
