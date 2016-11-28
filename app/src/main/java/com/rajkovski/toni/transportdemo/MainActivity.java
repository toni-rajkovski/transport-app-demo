package com.rajkovski.toni.transportdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;
import com.scand.svg.SVGHelper;

import java.io.IOException;

import javax.inject.Inject;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = "MainActivity";

  @Inject
  MainService mainService;

  @Inject
  SvgService svgService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    App.getInstance().getAppComponent().inject(this);
  }

  public void onLoadData(View view) {
    mainService.getData(new Subscriber<TransportRoutes>() {
      @Override
      public void onCompleted() {
        Log.d(LOG_TAG, "onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        Log.d(LOG_TAG, "onError", e);
      }

      @Override
      public void onNext(TransportRoutes schema) {
        Log.d(LOG_TAG, "Data received");

      }
    }, "data.json");

    svgService.loadSvgFromServer(new Subscriber<byte[]>() {
      @Override
      public void onCompleted() {
        Logger.e(LOG_TAG, "Completed");
      }

      @Override
      public void onError(Throwable e) {
        Logger.e(LOG_TAG, "Problem",  e);
      }

      @Override
      public void onNext(byte[] bytes) {
        try {
          ImageView image = (ImageView) findViewById(R.id.image);
          SVGHelper
            .useContext(MainActivity.this)
            .open(new String(bytes))
            .setBaseBounds(20, 20)
            .setKeepAspectRatio(true)
            .pictureAsBackground(image);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }, "https://d3m2tfu2xpiope.cloudfront.net/vehicles/subway.svg");

  }
}
