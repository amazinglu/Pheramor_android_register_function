package com.example.amazinglu.pheramor_project.utils;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Gson only know to Serialize simple data type or object contains only simple data type
 * for other type of data, we need to tell gson how to Serialize
 * */
public class ModelUtil {
    private static Gson gsonForSerialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Bitmap.class, new BitmapSerializer())
            .registerTypeAdapter(Uri.class, new UriSerializer())
            .create();

    private static Gson gsonForDeserialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .registerTypeAdapter(Bitmap.class, new BitmapDeserializer())
            .registerTypeAdapter(Uri.class, new UriDeserializer())
            .create();

    public static <T> T toObject(String json, TypeToken<T> typeToken) {
        return gsonForDeserialize.fromJson(json, typeToken.getType());
    }

    public static String toJson(Object object) {
        return gsonForSerialize.toJson(object);
    }

    static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement dateStr, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return DateUtil.stringToDate(dateStr.getAsString());
        }
    }

    static class DateSerializer implements JsonSerializer<Date> {
        @Override
        public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DateUtil.dateToString(date));
        }
    }

    static class BitmapDeserializer implements JsonDeserializer<Bitmap> {
        @Override
        public Bitmap deserialize(JsonElement bitmapStr, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return ImageUtil.getBitmapFromString(bitmapStr.getAsString());
        }
    }

    static class BitmapSerializer implements JsonSerializer<Bitmap> {
        @Override
        public JsonElement serialize(Bitmap bitmap, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(ImageUtil.getStringFromBitmap(bitmap));
        }
    }

    static class UriDeserializer implements JsonDeserializer<Uri> {
        @Override
        public Uri deserialize(JsonElement uriStr, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return ImageUtil.stringToUri(uriStr.getAsString());
        }
    }

    static class UriSerializer implements JsonSerializer<Uri> {
        @Override
        public JsonElement serialize(Uri uri, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(ImageUtil.uriToString(uri));
        }
    }

}
