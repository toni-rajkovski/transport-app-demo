package com.rajkovski.toni.transportdemo.services.parser;

import com.google.gson.Gson;
import com.rajkovski.toni.transportdemo.model.Schema_template;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Implementation of {@link IDataParser}.
 * The input provided should be in json format.
 * It uses the {@link com.google.gson.Gson} parser to parse the data.
 */
public class GsonDataParser implements IDataParser {

  @Override
  public Schema_template parseData(InputStream input) {
    Gson gson = new Gson();

    Reader reader = new InputStreamReader(input);
    Schema_template result = gson.fromJson(reader, Schema_template.class);

    return result;
  }

}
