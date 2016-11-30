package com.rajkovski.toni.transportdemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;
import com.rajkovski.toni.transportdemo.ui.overview.OverviewActivity;
import com.rajkovski.toni.transportdemo.ui.overview.OverviewPresenter;
import com.scand.svg.SVGHelper;

import java.io.IOException;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Simple activity that just loads the data and takes the user to the next screen.
 */
public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = "MainActivity";

  @Inject
  MainService mainService;

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
      public void onNext(TransportRoutes routes) {
        Log.d(LOG_TAG, "Data received");
        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
        intent.putExtra(OverviewPresenter.INTENT_KEY_ROUTES, routes);
        startActivity(intent);
      }
    }, "data.json");
  }
}
