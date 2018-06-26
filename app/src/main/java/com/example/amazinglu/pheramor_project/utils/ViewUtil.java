package com.example.amazinglu.pheramor_project.utils;

import android.support.v7.widget.AppCompatSpinner;

public class ViewUtil {

    public static void setSpinText(AppCompatSpinner spinner, String text) {
        for (int i = 0; i < spinner.getAdapter().getCount(); ++i) {
            if (spinner.getAdapter().getItem(i).toString().equals(text)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}
