package com.example.amazinglu.pheramor_project.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtil {

    public static boolean isEmpty(String str) {
        return str.isEmpty();
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

}
