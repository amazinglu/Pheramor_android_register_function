package com.example.amazinglu.pheramor_project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    /**
     * load image base on uri and store it into external storage
     * */
    public static Uri loadImage(Context context, Uri uri, ImageView imageView) {
        try {
            /**
             * the way to load image from external storage
             * */
            Uri localUri = getImageUrlWithAuthority(context, uri);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), localUri);
            imageView.setImageBitmap(bitmap);
            return localUri;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * load image base on local uri
     * */
    public static void loadImageLocal(Context context, Uri uri, ImageView imageView) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }

    public static Uri getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        new File("/sdcard/Pictures").mkdirs();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }
}
