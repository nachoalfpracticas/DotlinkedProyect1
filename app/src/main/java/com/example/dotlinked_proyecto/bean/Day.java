package com.example.dotlinked_proyecto.bean;

import java.text.DateFormatSymbols;
import java.util.Map;

public class Day {
  private int dyaOfWeek;
  private String dayOfWeek;
  private String[] daysOfWeek;
  private int[] dayList;
  private Map<Integer, String> keyIntDayString;

  public Day() {
    daysOfWeek = new String[]{};
    setDays();
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDaysOfWeek() {
    this.daysOfWeek = setDays();
  }

  private String[] setDays() {
    daysOfWeek = DateFormatSymbols.getInstance().getShortWeekdays();
    return daysOfWeek;
  }
}
