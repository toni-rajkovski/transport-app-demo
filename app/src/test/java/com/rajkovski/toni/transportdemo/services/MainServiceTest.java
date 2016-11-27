package com.rajkovski.toni.transportdemo.services;

import com.google.gson.JsonSyntaxException;
import com.rajkovski.toni.transportdemo.TestLogger;
import com.rajkovski.toni.transportdemo.TestUtil;
import com.rajkovski.toni.transportdemo.logger.AndroidLogger;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.Schema_template;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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
 * Tests for {@link MainService}
 */
public class MainServiceTest {

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
  public void getData_success() throws IOException {
    //given
    Schema_template returnValue = new Schema_template();

    IDataLoader mockLoader = mock(IDataLoader.class);
    when(mockLoader.loadData(any(String.class))).thenReturn(
      TestUtil.loadResource("sample_full.json"));
    
    IDataParser mockParser = mock(IDataParser.class);
    when(mockParser.parseData(any(InputStream.class))).thenReturn(returnValue);
    
    MainService mainService = new MainService(mockLoader, mockParser);
    TestSubscriber<Schema_template> testSubscriber = new TestSubscriber<>();

    //when
    mainService.getData(testSubscriber, "successLocation");
    testSubscriber.awaitTerminalEvent();

    //then
    verify(mockLoader, times(1)).loadData(any(String.class));
    verify(mockParser, times(1)).parseData(any(InputStream.class));
    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    testSubscriber.assertValueCount(1);
    testSubscriber.assertValue(returnValue);
  }

  @Test
  public void getData_error_parsing() throws IOException {
    //given
    IDataLoader mockLoader = mock(IDataLoader.class);
    when(mockLoader.loadData(any(String.class))).thenReturn(
      TestUtil.loadResource("sample_full.json"));

    IDataParser mockParser = mock(IDataParser.class);
    when(mockParser.parseData(any(InputStream.class))).thenThrow(JsonSyntaxException.class);

    MainService mainService = new MainService(mockLoader, mockParser);
    TestSubscriber<Schema_template> testSubscriber = new TestSubscriber<>();

    //when
    mainService.getData(testSubscriber, "parsingProblemLocation");
    testSubscriber.awaitTerminalEvent();

    //then
    verify(mockLoader, times(1)).loadData(any(String.class));
    verify(mockParser, times(1)).parseData(any(InputStream.class));
    testSubscriber.assertError(JsonSyntaxException.class);
  }

  @Test
  public void getData_error_loading() throws IOException {
    //given
    Schema_template returnValue = new Schema_template();

    IDataLoader mockLoader = mock(IDataLoader.class);
    when(mockLoader.loadData(any(String.class))).thenThrow(IOException.class);

    IDataParser mockParser = mock(IDataParser.class);
    when(mockParser.parseData(any(InputStream.class))).thenReturn(returnValue);

    MainService mainService = new MainService(mockLoader, mockParser);
    TestSubscriber<Schema_template> testSubscriber = new TestSubscriber<>();

    //when
    mainService.getData(testSubscriber, "loadingProblemLocation");
    testSubscriber.awaitTerminalEvent();

    //then
    verify(mockLoader, times(1)).loadData(any(String.class));
    verify(mockParser, times(0)).parseData(any(InputStream.class));
    testSubscriber.assertError(IOException.class);
  }

}
