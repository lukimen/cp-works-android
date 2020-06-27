package com.kmr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterHelper {

  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String DATE_FORMAT_MINGGU_DATE = "yyyy-ww";
  private static final String DATE_FORMAT_TAHUN = "yyyy";
  private static final String DATE_FORMAT_BULAN = "MM";
  private static final String DATE_FORMAT_MINGGU = "ww";

  public static String dateToString(Date date) {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  public static String dateToStringTahun(Date date) {
    return new SimpleDateFormat(DATE_FORMAT_TAHUN).format(date);
  }

  public static String dateToStringBulan(Date date) {
    return new SimpleDateFormat(DATE_FORMAT_BULAN).format(date);
  }

  public static String dateToStringMinggu(Date date) {
    return new SimpleDateFormat(DATE_FORMAT_MINGGU).format(date);
  }

  public static Date stringToDate(String sDate) {

    Date date = new Date();
    try {
      date = new SimpleDateFormat(DATE_FORMAT).parse(sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date stringToDateMinggu(String sDate) {

    Date date = new Date();
    try {
      date = new SimpleDateFormat(DATE_FORMAT_MINGGU_DATE).parse(sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

}
