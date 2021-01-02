package com.project.rest.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MyUtilities {
    public static LocalDate stringToDate(String date)
    {
        int y = Integer.parseInt(date.substring(0, 4));
        int m = Integer.parseInt(date.substring(5, 7));
        int d = Integer.parseInt(date.substring(8, 10));
        return LocalDate.of(y,m,d);
    }

    public static LocalDateTime stringToDateTime(String date)
    {
        int y = Integer.parseInt(date.substring(0, 4));
        int m = Integer.parseInt(date.substring(5, 7));
        int d = Integer.parseInt(date.substring(8, 10));
        return LocalDateTime.of(y,m,d,0,0);
    }

    public static String dateToString(LocalDate date)
    {
        return date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
    }
}
