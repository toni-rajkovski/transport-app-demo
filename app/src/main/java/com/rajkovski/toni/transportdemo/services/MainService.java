package com.rajkovski.toni.transportdemo.services;

import android.util.Log;

import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.model.Schema_template;
import com.rajkovski.toni.transportdemo.services.loader.AssetsDataLoader;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.GsonDataParser;
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
  private IDataLoader assetsDataLoader = new AssetsDataLoader(App.getInstance());
  private IDataParser gsonDataParser = new GsonDataParser();

  /**
   * Retreives the data from outer source, parses it and returns it.
   *
   * @param subscriber subscriber
   * @param from the source where to take the data from
   */
  public void getData(Subscriber<Schema_template> subscriber, final String from) {

    Observable.OnSubscribe<InputStream> loadFromNetwork = new Observable.OnSubscribe<InputStream>() {
      @Override
      public void call(Subscriber<? super InputStream> sub) {
        try {
          Log.d(LOG_TAG, "Loading the data from " + from);
          InputStream inputStream = assetsDataLoader.loadData(from);
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
        Log.d(LOG_TAG, "Transforming the input into model " + from);
        Schema_template data = gsonDataParser.parseData(inputStream);
        return data;
      }
    };

    Observable.create(loadFromNetwork)
      .map(parserMapping)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(subscriber);
  }

}
