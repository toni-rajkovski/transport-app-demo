package com.rajkovski.toni.transportdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rajkovski.toni.transportdemo.model.Schema_template;
import com.rajkovski.toni.transportdemo.services.MainService;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onLoadData(View view) {
    MainService mainService = new MainService();
    mainService.getData(new Subscriber<Schema_template>() {
      @Override
      public void onCompleted() {
        Log.d(LOG_TAG, "onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        Log.d(LOG_TAG, "onError", e);
      }

      @Override
      public void onNext(Schema_template schema) {
        Log.d(LOG_TAG, "Data received");

      }
    }, "data.json");
  }
}
