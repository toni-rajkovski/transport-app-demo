package com.rajkovski.toni.transportdemo.services.parser;

import com.rajkovski.toni.transportdemo.model.Schema_template;

import java.io.InputStream;

/**
 * Parses the json data and stores it into a model.
 */

public interface IDataParser {

  /**
   * Parses the data from the input stream and stores it into a new
   * {Schema_template} model.
   *
   * @param input the input stream with the json.
   * @return Schema_template
   */
  Schema_template parseData(InputStream input);

}
