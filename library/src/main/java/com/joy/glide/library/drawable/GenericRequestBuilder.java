package com.joy.glide.library.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

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
import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by joybar on 2018/5/24.
 */

public class GenericRequestBuilder<ModelType> {
	private RequestOrder requestTaskOrder;
	private ICall mICall;
	private RequestListener requestListener;
	private int placeholderId;
	private int errorId;
	private boolean isLoadReady;
	private ModelType model;
	private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.RESULT;

	public GenericRequestBuilder(String url) {
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
		handleLoadStart(imageView);
		AsyncFactoryHelper.getInstance().produce(getRequestTaskOrder(), new Callback<Response>() {

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
				Bitmap bitmap = BitmapFactory.decodeByteArray(response.getResponseBody().getBytes(), 0, response.getResponseBody().getBytes()
						.length);
				if (null != requestListener) {
					requestListener.onResourceReady(bitmap);
				}
				try {
					InputStream inputStream = new ByteArrayInputStream(response.getResponseBody().getBytes());
					ImageHeaderParser.ImageType type = new ImageHeaderParser(inputStream).getType();
					if (type == ImageHeaderParser.ImageType.GIF) {
						// gif1加载一个动态图gif
						//byte array
						GLog.printInfo("load gif ");
						GifDrawable gifFromBytes = new GifDrawable(response.getResponseBody().getBytes());
						imageView.setImageDrawable(gifFromBytes);
					} else {
						GLog.printInfo("load image ");
						imageView.setImageBitmap(bitmap);
					}

				} catch (IOException e) {
					GLog.printInfo("ImageHeaderParser error  = " + e.getMessage());
					e.printStackTrace();
					handleLoadError(imageView, e);
				}
			}

			@Override
			public void onProgressUpdate(RequestOrder requestOrder, int values) {
				//GLog.printInfo("onProgressUpdate,values=" + values);
				if (null != requestListener) {
					requestListener.onProgressUpdate(values);
				}
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

	private void handleLoadStart(ImageView imageView) {
		isLoadReady = false;
		if (null != requestListener) {
			requestListener.onLoadStarted();
		}
		if (0 != placeholderId) {
			imageView.setImageResource(placeholderId);
		}
	}

	private void handleLoadFinish() {
		isLoadReady = true;
	}

	private void handleLoadError(ImageView imageView, Throwable throwable) {
		if (0 != errorId) {
			imageView.setImageResource(errorId);
		}
		if (null != requestListener) {
			requestListener.onException(throwable);
		}
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
