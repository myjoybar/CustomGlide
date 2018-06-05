package com.joy.glide.library.data.source.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.helper.asynfactory.AsyncFactoryHelper;
import com.joy.glide.library.helper.asynfactory.Callback;
import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.target.Target;
import com.joy.glide.library.utils.GLog;
import com.joy.smoothhttp.call.ICall;
import com.joy.smoothhttp.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/30.
 */

public class RemoteDataSource<R> implements DataSource<R> {


    private RequestOrder requestOrder;
    private ICall<Response> call;
    Target<R>  target;
    public RemoteDataSource(RequestOrder requestOrder) {
        this.requestOrder = requestOrder;
    }

    @Override
    public void saveData(@NonNull DrawableKey key,R resource) {
        // nothing to do
    }

    @Override
    public R getData(@NonNull DrawableKey key) {
        // nothing to do
        return null;
    }

    @Override
    public void getDataAsync(@NonNull DrawableKey key) {

    }

    @Override
    public void getDataAsync(@NonNull DrawableKey key, final LoadDataListener loadDataCallback) {
        GLog.printInfo("url = " + requestOrder.getUrl().toString());
        if (requestOrder.getUrl() instanceof String) {
            GLog.printInfo("load from server");
            AsyncFactoryHelper.getInstance().produce(requestOrder, new Callback<Response>() {

                @Override
                public void onPreExecute(ICall iCall) {
                    call = iCall;
                }

                @Override
                public void onFailure(RequestOrder requestOrder, Throwable throwable) {
                    GLog.printInfo(throwable.getMessage());
                    loadDataCallback.onDataLoadedError(throwable);

                }

                @Override
                public void onResponse(RequestOrder requestOrder, Response response) {
                    GLog.printInfo("onResponse");
                 //   Bitmap bitmap = BitmapFactory.decodeByteArray(response.getResponseBody().getBytes(), 0, response.getResponseBody().getBytes() .length);
                  //  loadDataCallback.onDataLoaded(bitmap);
                    loadDataCallback.onDataLoaded(response);
                }

                @Override
                public void onProgressUpdate(RequestOrder requestOrder, int values) {
                    loadDataCallback.onProgressUpdate(values);
                }

                @Override
                public void onFinish(RequestOrder requestOrder) {
                    GLog.printInfo("onFinish");
                }


            });
        } else if (requestOrder.getUrl() instanceof File) {
            GLog.printInfo("load from File");
            AsynchronousTask<Integer, Bitmap> asynchronousTask = new AsynchronousTask<Integer,
					Bitmap>() {
                @Override
                protected Bitmap doInBackground() {
                    FileInputStream fis = null;
                    Bitmap bitmap = null;
                    try {
                        fis = new FileInputStream((File) requestOrder.getUrl());
                        bitmap = BitmapFactory.decodeStream(fis);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        GLog.printInfo("loadFromFile error:" + e.getMessage());
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    if (null != bitmap) {
                        loadDataCallback.onDataLoaded(bitmap);
                    } else {
                        loadDataCallback.onDataLoadedError(new Exception("file bitmap is null"));

                    }
                }
            };
            asynchronousTask.execute();
        }


    }

    @Override
    public void deleteAll() {
        // nothing to do
    }

    @Override
    public void delete(@NonNull DrawableKey key) {
        // nothing to do
    }

    @Override
    public void cancel() {

        if (null != call) {
            call.cancel();
        }
    }
}