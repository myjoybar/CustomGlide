package com.joy.glide.library.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.widget.ImageView;

import com.joy.glide.library.cache.CacheUtils;
import com.joy.glide.library.cache.DrawableSave;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.key.EmptySignature;
import com.joy.glide.library.helper.asynfactory.AsyncFactoryHelper;
import com.joy.glide.library.helper.asynfactory.Callback;
import com.joy.glide.library.load.engine.DiskCacheStrategy;
import com.joy.glide.library.load.resource.bitmap.ImageHeaderParser;
import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.target.RequestListener;
import com.joy.glide.library.utils.GLog;
import com.joy.smoothhttp.call.ICall;
import com.joy.smoothhttp.response.Response;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import me.joy.async.lib.task.AsynchronousTask;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by joybar on 2018/5/24.
 */

public class GenericRequestBuilder<ModelType> {
    private RequestOrder<ModelType> requestTaskOrder;
    private ICall mICall;
    private RequestListener requestListener;
    private int placeholderId;
    private int errorId;
    private boolean isLoadReady;
    private ModelType model;
    protected final Class<ModelType> modelClass;
    private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.RESULT;

    public GenericRequestBuilder(Class<ModelType> modelClass, ModelType url) {
        this.modelClass = modelClass;
        requestTaskOrder = new RequestOrder(url);
    }

    public GenericRequestBuilder<ModelType> load(ModelType model) {
        this.model = model;
        return this;
    }

    public GenericRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
        this.diskCacheStrategy = strategy;

        return this;
    }

    public void into(final ImageView imageView) {

        GLog.printInfo("getWidth=" + imageView.getWidth());
        GLog.printInfo("getHeight=" + imageView.getHeight());


        handleLoadStart(imageView);
        //load from cache

        Pair<Bitmap, GifDrawable> pair = loadDrawableSaveFromCache(imageView);
        Bitmap bitmap = pair.first;
        GifDrawable gifDrawable = pair.second;
        if (bitmap != null || gifDrawable != null) {
            GLog.printInfo("load from cache");
            handleLoadReady(bitmap);
            handleSetImageResource(bitmap, gifDrawable, imageView);
            handleLoadFinish();
        } else {
            if (requestTaskOrder.getUrl() instanceof String) {
                GLog.printInfo("load from server");
                loadFromServer(imageView);
            } else if (requestTaskOrder.getUrl() instanceof File) {
                GLog.printInfo("load from File");
                loadFromFile(imageView);
            }
        }


    }


    private Pair loadDrawableSaveFromCache(ImageView imageView) {
        DrawableKey drawableKey = new DrawableKey(requestTaskOrder.getUrl().toString(), imageView
                .getWidth(), imageView.getHeight(), EmptySignature.obtain());
        //load from cache
        DrawableSave drawableSave = CacheUtils.get(drawableKey);
        Bitmap bitmap = null;
        GifDrawable gifDrawable = null;
        if (null != drawableSave) {
            bitmap = drawableSave.getBitmap();
            gifDrawable = drawableSave.getGifDrawable();
        }
        Pair<Bitmap, GifDrawable> pair = new Pair(bitmap, gifDrawable);
        return pair;
    }

    private void saveToCache(ImageView imageView, Bitmap bitmap, GifDrawable gifDrawable) {
        DrawableKey drawableKey = new DrawableKey(requestTaskOrder.getUrl().toString(), imageView
                .getWidth(), imageView.getHeight(), EmptySignature.obtain());

        CacheUtils.saveDrawableSave(drawableKey, new
                DrawableSave(bitmap, gifDrawable));
    }


    private void loadFromFile(final ImageView imageView) {
        AsynchronousTask<Integer, Bitmap> asynchronousTask = new AsynchronousTask<Integer,
                Bitmap>() {

            @Override
            protected Bitmap doInBackground() {
                FileInputStream fis = null;
                Bitmap bitmap = null;
                try {
                    fis = new FileInputStream((File) requestTaskOrder.getUrl());
                    bitmap = BitmapFactory.decodeStream(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    GLog.printInfo("loadFromFile error:" + e.getMessage());
                    handleLoadError(imageView, e);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                handleLoadReady(bitmap);
                handleSetImageResource(bitmap, null, imageView);
                handleLoadFinish();
            }
        };
        asynchronousTask.execute();

    }

    private void loadFromServer(final ImageView imageView) {

        AsyncFactoryHelper.getInstance().produce(requestTaskOrder, new Callback<Response>() {

            @Override
            public void onPreExecute(ICall iCall) {
                setICall(iCall);
            }

            @Override
            public void onFailure(RequestOrder requestOrder, Throwable throwable) {
                GLog.printInfo(throwable.getMessage());
                handleLoadError(imageView, throwable);

            }

            @Override
            public void onResponse(RequestOrder requestOrder, Response response) {
                GLog.printInfo("onResponse");
                Bitmap bitmap = BitmapFactory.decodeByteArray(response.getResponseBody().getBytes
                        (), 0, response.getResponseBody().getBytes().length);
                handleLoadReady(bitmap);
                try {

                    InputStream inputStream = new ByteArrayInputStream(response.getResponseBody()
                            .getBytes());
                    ImageHeaderParser.ImageType type = new ImageHeaderParser(inputStream).getType();
                    GifDrawable gifDrawable = null;
                    if (type == ImageHeaderParser.ImageType.GIF) {
                        gifDrawable = new GifDrawable(response.getResponseBody().getBytes());
                    }
                    handleSetImageResource(bitmap, gifDrawable, imageView);

                } catch (IOException e) {
                    GLog.printInfo("ImageHeaderParser error  = " + e.getMessage());
                    e.printStackTrace();
                    handleLoadError(imageView, e);
                }
            }

            @Override
            public void onProgressUpdate(RequestOrder requestOrder, int values) {
                handleLoadProgressUpdate(values);
            }

            @Override
            public void onFinish(RequestOrder requestOrder) {
                GLog.printInfo("onFinish");
                handleLoadFinish();
            }


        });

    }

    public GenericRequestBuilder listener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public GenericRequestBuilder placeholder(int resourceId) {
        this.placeholderId = resourceId;
        return this;
    }

    public GenericRequestBuilder error(int resourceId) {
        this.errorId = resourceId;
        return this;
    }

    public void handleLoadStart(ImageView imageView) {
        isLoadReady = false;
        if (null != requestListener) {
            requestListener.onLoadStarted();
        }
        if (0 != placeholderId) {
            imageView.setImageResource(placeholderId);
        }
    }

    private void handleLoadProgressUpdate(int values) {
        if (null != requestListener) {
            requestListener.onProgressUpdate(values);
        }
    }

    private void handleLoadReady(Bitmap bitmap) {
        if (null != requestListener) {
            requestListener.onResourceReady(bitmap);
        }
    }

    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }

    private boolean isGif(byte[] bytes, Bitmap bitmap) throws IOException {
        if (null == bytes) {
            bytes = getBytesByBitmap(bitmap);
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ImageHeaderParser.ImageType type = new ImageHeaderParser(inputStream).getType();
        return type == ImageHeaderParser.ImageType.GIF;
    }

    private void handleSetImageResource(Bitmap bitmap, GifDrawable gifDrawable, ImageView
            imageView) {

        if (null != gifDrawable) {
            GLog.printInfo("this is  gif");
            imageView.setImageDrawable(gifDrawable);
        } else {
            GLog.printInfo("this is not gif");
            imageView.setImageBitmap(bitmap);
        }

        saveToCache(imageView, bitmap, gifDrawable);
    }


    private void handleLoadError(ImageView imageView, Throwable throwable) {
        if (0 != errorId) {
            imageView.setImageResource(errorId);
        }
        if (null != requestListener) {
            requestListener.onException(throwable);
        }
    }

    private void handleLoadFinish() {
        isLoadReady = true;
    }


    public void cancelRequest() {
        if (null != mICall) {
            mICall.cancel();
        }
    }

    public RequestOrder getRequestTaskOrder() {
        return requestTaskOrder;
    }

    public void setICall(ICall mICall) {
        this.mICall = mICall;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public boolean isLoadReady() {
        return isLoadReady;
    }


}
