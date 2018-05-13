package com.joy.glide.library.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.joy.glide.library.request.RequestOrder;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joybar on 2018/5/7.
 */

public class HttpUrlConnectionExecutor<TResult> extends AbstractHttpExecutor {

    public static String TAG = "HttpUrlConnectionExecutor";

    public HttpUrlConnectionExecutor(RequestOrder requestOrder) {
        super(requestOrder);
    }


    @Override
    public TResult execute() {
        return execute(null);
    }

    @Override
    public TResult execute(IRequestCallback requestCallback) {

        Bitmap bitmap = null;
        HttpURLConnection con = null;
        RequestOrder requestOrder = getRequestOrder();
        try {
            Log.d(TAG, "url=" + requestOrder.getUrl());
            URL url = new URL(requestOrder.getUrl());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        TResult result = (TResult) bitmap;
        return result;
    }
}
