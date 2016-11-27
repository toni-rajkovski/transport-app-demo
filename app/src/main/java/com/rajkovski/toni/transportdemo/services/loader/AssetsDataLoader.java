package com.rajkovski.toni.transportdemo.services.loader;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Loads the data from Assets.
 */
public class AssetsDataLoader implements IDataLoader {

  private Context context;

  public AssetsDataLoader(Context context) {
    this.context = context;
  }

  @Override
  public InputStream loadData(String from) throws IOException {
    return context.getAssets().open(from);
  }

}
