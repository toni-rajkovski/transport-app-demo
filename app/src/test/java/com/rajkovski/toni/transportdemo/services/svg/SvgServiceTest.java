package com.rajkovski.toni.transportdemo.services.svg;

import com.google.gson.JsonSyntaxException;
import com.rajkovski.toni.transportdemo.TestLogger;
import com.rajkovski.toni.transportdemo.TestUtil;
import com.rajkovski.toni.transportdemo.logger.AndroidLogger;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.Schema_template;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link SvgService}
 */
public class SvgServiceTest {

  @Before
  public void prepare() {
    Logger.replaceDefaultLogger(new TestLogger());
    RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
      @Override
      public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
      }
    });
  }

  @After
  public void tearDown() {
    Logger.replaceDefaultLogger(new AndroidLogger());
    RxAndroidPlugins.getInstance().reset();
  }

  @Test
  public void loadSvgFromServer_success_from_network() throws IOException {
    //given
    byte[] image = {1, 2, 3};

    Buffer buffer = new Buffer();
    buffer.write(image);
    Response response = new Response.Builder()
      .protocol(Protocol.HTTP_1_1)
      .request(new Request.Builder().url("http://test").build())
      .code(200)
      .body(new RealResponseBody(new Headers.Builder().set("Content-Length", "3").build(), buffer))
      .build();

    Call mockCall = mock(Call.class);
    when(mockCall.execute()).thenReturn(response);

    OkHttpClient mockHttpClient = mock(OkHttpClient.class);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

    SvgService svgService = new SvgService(mockHttpClient, new MemorySvgCache());
    TestSubscriber<byte[]> testSubscriber = new TestSubscriber<>();

    //when
    svgService.loadSvgFromServer(testSubscriber, "http://svglocation");
    testSubscriber.awaitTerminalEvent();

    //then
    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    testSubscriber.assertValueCount(1);
    Assert.assertArrayEquals(image, testSubscriber.getOnNextEvents().get(0));
  }

  @Test
  public void loadSvgFromServer_success_from_cache() throws IOException {
    //given
    byte[] image = {1, 2, 3};

    ISvgCache mockSvgCache = mock(ISvgCache.class);
    when(mockSvgCache.getImageByUrl(any(String.class))).thenReturn(image);

    SvgService svgService = new SvgService(null, mockSvgCache);
    TestSubscriber<byte[]> testSubscriber = new TestSubscriber<>();

    //when
    svgService.loadSvgFromServer(testSubscriber, "test");
    testSubscriber.awaitTerminalEvent();

    //then
    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    testSubscriber.assertValueCount(1);
    Assert.assertArrayEquals(image, testSubscriber.getOnNextEvents().get(0));
  }

  @Test
  public void loadSvgFromServer_error() throws IOException {
    //given
    Call mockCall = mock(Call.class);
    when(mockCall.execute()).thenThrow(IOException.class);

    OkHttpClient mockHttpClient = mock(OkHttpClient.class);
    when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

    SvgService svgService = new SvgService(mockHttpClient, new MemorySvgCache());
    TestSubscriber<byte[]> testSubscriber = new TestSubscriber<>();

    //when
    svgService.loadSvgFromServer(testSubscriber, "http://test");
    testSubscriber.awaitTerminalEvent();

    //then
    testSubscriber.assertError(IOException.class);
  }

}
