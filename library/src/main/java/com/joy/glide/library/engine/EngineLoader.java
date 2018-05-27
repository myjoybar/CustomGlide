package com.joy.glide.library.engine;

import com.joy.glide.library.drawable.GenericRequestBuilder;
import com.joy.glide.library.helper.asynfactory.AsyncFactoryHelper;
import com.joy.glide.library.helper.asynfactory.Callback;
import com.joy.glide.library.request.RequestOrder;

/**
 * Created by joybar on 26/05/2018.
 */

public class EngineLoader<ModelType> {
    public EngineLoader getInstance() {
        return EngineLoaderHolder.INSTANCE;
    }

    private static class EngineLoaderHolder {
        private static EngineLoader INSTANCE = new EngineLoader();
    }


    public void load(RequestOrder<ModelType> taskOrder, GenericRequestBuilder
            genericRequestBuilder, Callback callback) {
        if (taskOrder.getUrl() instanceof String) {
            loadServerBegin(taskOrder, genericRequestBuilder, callback);
        }
    }

    private void loadServerBegin(final RequestOrder taskOrder, GenericRequestBuilder
            genericRequestBuilder, final Callback callback) {
        AsyncFactoryHelper.getInstance().produce(taskOrder, callback);
    }


}
