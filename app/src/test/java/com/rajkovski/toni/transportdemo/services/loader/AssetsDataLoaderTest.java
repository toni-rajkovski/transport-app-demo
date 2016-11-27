package com.rajkovski.toni.transportdemo.services.loader;

import android.content.Context;
import android.content.res.AssetManager;

import com.rajkovski.toni.transportdemo.TestBuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link AssetsDataLoader}.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = TestBuildConfig.class, sdk = 21, manifest = Config.NONE)
public class AssetsDataLoaderTest {


  @Test
  public void loadData_success() throws IOException {
    //given
    AssetsDataLoader assetsDataLoader = new AssetsDataLoader(prepareContext(false));
    String from = "data.json";

    //when
    InputStream inputStream = assetsDataLoader.loadData(from);

    //then
    assertNotNull(inputStream);
  }

  @Test(expected = IOException.class)
  public void parseData_not_exist() throws IOException {
    //given
    AssetsDataLoader assetsDataLoader = new AssetsDataLoader(prepareContext(true));
    String from = "data.json";

    //when
    assetsDataLoader.loadData(from);

    //then
    fail("IOException is expected");
  }

  private Context prepareContext(boolean assetsWithException) throws IOException {
    AssetManager mockAssets = mock(AssetManager.class);
    if (assetsWithException) {
      when(mockAssets.open(any(String.class))).thenThrow(IOException.class);
    } else {
      when(mockAssets.open(any(String.class))).thenReturn(
        new ByteArrayInputStream(new byte[]{1, 2, 3}));
    }

    Context mockContext = mock(Context.class);
    when(mockContext.getAssets()).thenReturn(mockAssets);

    return mockContext;
  }

}
