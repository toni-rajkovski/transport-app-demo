package com.rajkovski.toni.transportdemo.services.parser;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Implementation of {@link IDataParser}.
 * The input provided should be in json format.
 * It uses the {@link com.google.gson.Gson} parser to parse the data.
 */
public class GsonDataParser implements IDataParser {

  private Gson gson;

  public GsonDataParser(Gson gson) {
    this.gson = gson;
  }

  @Override
  public TransportRoutes parseData(InputStream input) {
    if (input != null) {
      Reader reader = new InputStreamReader(input);
      TransportRoutes result = gson.fromJson(reader, TransportRoutes.class);
      return result;
    }
    return null;
  }

}
