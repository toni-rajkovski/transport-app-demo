package com.rajkovski.toni.transportdemo.services.svg;

import com.rajkovski.toni.transportdemo.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Service that loads the svg images from server.
 */
public class SvgService {

  private static final String LOG_TAG = "SvgService";
  private OkHttpClient httpClient;
  private ISvgCache cache;

  public SvgService(OkHttpClient httpClient, ISvgCache cache) {
    this.httpClient = httpClient;
    this.cache = cache;
  }

  public void loadSvgFromServer(Subscriber<byte[]> subscriber, final String url) {
    Observable.OnSubscribe<byte[]> loadFromNetwork = new Observable.OnSubscribe<byte[]>() {
      @Override
      public void call(Subscriber<? super byte[]> sub) {
        try {
          byte[] cachedImage = cache.getImageByUrl(url);
          if (cachedImage != null) {
            Logger.d(LOG_TAG, "Image with url '" + url + "' found in cache");
            sub.onNext(cachedImage);
            sub.onCompleted();
          } else {
            Logger.d(LOG_TAG, "Loading the data from " + url);
            Request request = new Request.Builder()
              .url(url)
              .build();

            Response response = httpClient.newCall(request).execute();
            cache.putImage(url, response.body().bytes());

            sub.onNext(cache.getImageByUrl(url));
            sub.onCompleted();
          }
        } catch (Exception e) {
          sub.onError(e);
        }
      }
    };

    Observable.create(loadFromNetwork)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(subscriber);
  }

}
