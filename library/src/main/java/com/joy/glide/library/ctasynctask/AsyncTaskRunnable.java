package com.joy.glide.library.ctasynctask;

import com.joy.glide.library.ctasynctask.netexecutor.AbstractExecutor;
import com.joy.glide.library.ctasynctask.netexecutor.IRequestCallback;
import com.joy.glide.library.engine.LoaderEngine;

/**
 * Created by joybar on 2018/5/9.
 */

public class AsyncTaskRunnable<Result> extends TaskRunnable {

	private final IRequestCallback iRequestCallback;
	private final LoaderEngine loaderEngine;

	AsyncTaskRunnable(LoaderEngine<Result> loaderEngine, IRequestCallback iRequestCallback) {
		super("AsyncTaskClient %s", loaderEngine.getTaskOrder().getUrl());
		this.iRequestCallback = iRequestCallback;
		this.loaderEngine = loaderEngine;

	}


	@Override
	protected Result doInBackground(Object[] objects) {
		AbstractExecutor<Result> executor = loaderEngine.getAbLoader();

		Result result = (Result) executor.execute(new IRequestCallback<Result>() {
			@Override
			public void onSuccess(Result response) {
				iRequestCallback.onSuccess(response);
			}

			@Override
			public void onFailure(Throwable throwable) {
				iRequestCallback.onFailure(throwable);
			}
		});
		return result;
	}



}
