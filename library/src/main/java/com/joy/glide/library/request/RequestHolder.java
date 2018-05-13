package com.joy.glide.library.request;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.joy.glide.library.helper.asynfactory.AsyncFactoryHelper;
import com.joy.glide.library.helper.asynfactory.Callback;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/9.
 */

public class RequestHolder {
    public static String TAG = "RequestHolder";
    private RequestOrder requestTaskOrder;
    private LifecycleListener lifecycleListener;
    private AsynchronousTask mAsynchronousTask;

    public RequestHolder(String url) {
        requestTaskOrder = new RequestOrder(url);
    }

    public void cancelRequest() {
        if (null != mAsynchronousTask) {
            mAsynchronousTask.cancel(false);
        }
    }


    public void into(final ImageView imageView) {
        AsyncFactoryHelper.getInstance().produce(requestTaskOrder, new Callback<Bitmap>() {

            @Override
            public void onPreExecute(AsynchronousTask asynchronousTask) {
                mAsynchronousTask = asynchronousTask;
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onResponse(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        });

    }
}
