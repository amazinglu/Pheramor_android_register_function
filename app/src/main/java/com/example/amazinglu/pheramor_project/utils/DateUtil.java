package com.example.amazinglu.pheramor_project.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static DateFormat dateFormatDateOnly = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormatDateOnly.format(date);
    }

    public static Date stringToDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            return dateFormatDateOnly.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
