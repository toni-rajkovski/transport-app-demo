package com.rajkovski.toni.transportdemo.util;

import com.rajkovski.toni.transportdemo.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Manipulation with date and time.
 */
// TODO convert it to service and write unit tests.
public class DateTimeUtil {

  private static final String LOG_TAG = "DateTimeUtil";

  private static final SimpleDateFormat INPUT_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  private static final SimpleDateFormat OUTPUT_FORMAT_HHMM = new SimpleDateFormat("HH:mm");

  /**
   * Parses the date which should be in specific format.
   *
   * @param date the date to be parsed
   * @return Date
   */
  public static Date parseDate(String date) {
    try {
      return INPUT_FORMAT.parse(date);
    } catch (ParseException e) {
      Logger.w(LOG_TAG, "Cannot parse date " + date, e);
      return null;
    }
  }

  /**
   * Formats the time.
   * The date is first parsed and then formatted in format HH:mm.
   * E.g. 12:45
   *
   * @param inputDate the input date
   * @return formatted time
   */
  public static String timeStringHHMM(String inputDate) {
    Date date = parseDate(inputDate);
    if (date != null) {
      return OUTPUT_FORMAT_HHMM.format(date);
    } else {
      return null;
    }
  }

  /**
   * Calculates the difference between the dates in minutes.
   * It returns human readable string e.g. 1h 23min.
   *
   * @param inputDate1 the start date
   * @param inputDate2 the end date
   * @return human readable difference
   */
  public static String minutesDiff(String inputDate1, String inputDate2) {
    Date date1 = parseDate(inputDate1);
    Date date2 = parseDate(inputDate2);
    if (date1 != null && date2 != null) {
      long diff = date2.getTime() - date1.getTime();
      String res = "";
      diff /= 1000L * 60;
      if (diff > 60) {
        res += (diff % 60) + "h ";
        diff /= 60;
      }
      res += diff + "min";
      return res;
    } else {
      return "";
    }
  }

}
