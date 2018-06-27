package com.example.amazinglu.pheramor_project.fragment_confirm;

import android.content.Context;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestFunc {

    public static final String KEY_JSON_STRING = "jsonStr";
    private static final String UPLOAD_URL = "https://external.dev.pheramor.com/";

    public static String uploadUserInfo(String userStr) {
        OkHttpClient client = new OkHttpClient();
        RequestBody postBody = new FormBody.Builder()
                .add(KEY_JSON_STRING, userStr)
                .build();

        Request request = new Request.Builder()
                .url(UPLOAD_URL)
                .post(postBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responseStr = null;
        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }
}
