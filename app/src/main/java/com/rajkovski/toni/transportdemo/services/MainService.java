package com.rajkovski.toni.transportdemo.services;

import android.util.Log;

import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.Schema_template;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * The main service.
 */
public class MainService {

  private static final String LOG_TAG = "MainService";

  private IDataLoader dataLoader;
  private IDataParser dataParser;

  public MainService(IDataLoader dataLoader, IDataParser dataParser) {
    this.dataLoader = dataLoader;
    this.dataParser = dataParser;
  }

  /**
   * Retreives the data from outer source, parses it and returns it.
   *
   * @param subscriber subscriber
   * @param from the source where to take the data from
   */
  public <T extends Subscriber<Schema_template>> void getData(final T subscriber, final String from) {

    Observable.OnSubscribe<InputStream> loadFromNetwork = new Observable.OnSubscribe<InputStream>() {
      @Override
      public void call(Subscriber<? super InputStream> sub) {
        try {
          Logger.d(LOG_TAG, "Loading the data from " + from);
          InputStream inputStream = dataLoader.loadData(from);
          sub.onNext(inputStream);
          sub.onCompleted();
        } catch (IOException e) {
          sub.onError(e);
        }
      }
    };

    Func1<InputStream, Schema_template> parserMapping = new Func1<InputStream, Schema_template>() {
      @Override
      public Schema_template call(InputStream inputStream) {
        Logger.d(LOG_TAG, "Transforming the input into model");
        try {
          Schema_template data = dataParser.parseData(inputStream);
          return data;
        } catch (Exception e) {
          subscriber.onError(e);
          return null;
        }
      }
    };

    Observable.create(loadFromNetwork)
      .map(parserMapping)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(subscriber);
  }

}
