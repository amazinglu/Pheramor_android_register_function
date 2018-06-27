package com.example.amazinglu.pheramor_project.utils;

import android.util.Patterns;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * the length of the password has to be larger than 6
     * */
    public static boolean isPasswordValid(String passWord) {
        return passWord.length() >= 6;
    }

    public static boolean isPasswordMatch(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public static boolean isHeightValid(String height) {
        if (!Character.isDigit(height.charAt(0))) {
            return false;
        }

        boolean foundDecimal  = false;
        for (int i = 1; i < height.length(); ++i) {
            if (height.charAt(i) == '.') {
                if (foundDecimal) {
                    return false;
                } else {
                    foundDecimal = true;
                }
            } else if (!Character.isDigit(height.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * compare the Date object with its year, month and day only
     * */
    public static boolean isDobValid(Date dateOfBirth) {
        Calendar c = Calendar.getInstance();
        Date current = c.getTime();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(dateOfBirth);
        c2.setTime(current);

        if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
            return false;
        } else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            if (c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH)) {
                return false;
            } else if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
                if (c1.get(Calendar.DAY_OF_MONTH) > c2.get(Calendar.DAY_OF_MONTH)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isMinMaxAgeValid(int minAge, int maxAge) {
        return minAge <= maxAge;
    }
}
