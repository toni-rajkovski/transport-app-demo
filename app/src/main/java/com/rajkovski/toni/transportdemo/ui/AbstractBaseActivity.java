package com.rajkovski.toni.transportdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajkovski.toni.transportdemo.R;

/**
 * Base activity for all other activities in the App.
 * It contains the basic methods like error handling that
 * are needed in all other activities.
 */
public class AbstractBaseActivity extends AppCompatActivity {

  private ViewGroup errorView;
  private TextView errorText;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    errorView = (ViewGroup) findViewById(R.id.error_view);
    errorText = (TextView) findViewById(R.id.error_text);
  }

  protected void displayError(String error) {
    errorView.setVisibility(View.VISIBLE);
    errorText.setText(error);
  }

  protected void removeError() {
    errorView.setVisibility(View.GONE);
    errorText.setText("");
  }
}
