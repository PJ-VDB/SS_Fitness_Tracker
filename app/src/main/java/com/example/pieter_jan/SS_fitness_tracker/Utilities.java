package com.example.pieter_jan.SS_fitness_tracker;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by pieter-jan on 3/3/2017.
 */

 public class Utilities {

    public static String getTodayDate(){

        LocalDate dt = new LocalDate();

        DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("MMM");

        String dayOfMonth = String.valueOf(dt.getDayOfMonth());
        String monthOfYear = String.valueOf(monthFormatter.print(dt));

        DateTimeFormatter formatterForDisplay = DateTimeFormat.forPattern("dd/MMM/yyyy");

        DateTime formattedDate = formatterForDisplay.parseDateTime(
                dayOfMonth
                        + "/"
                        + monthOfYear
                        + "/"
                        + dt.year().getAsText());

        String finalDesiredDate = formattedDate.getYear() + "-" + formattedDate.getMonthOfYear() + "-" + formattedDate.getDayOfMonth();

        return finalDesiredDate;
    }
}
