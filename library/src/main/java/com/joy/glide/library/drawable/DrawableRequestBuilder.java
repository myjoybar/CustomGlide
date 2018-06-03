package com.joy.glide.library.drawable;

import android.content.Context;

import com.joy.glide.library.data.source.local.LocalDataSource;

/**
 * Created by joybar on 2018/5/24.
 */

public class DrawableRequestBuilder<ModelType> extends GenericRequestBuilder<ModelType> {


    public DrawableRequestBuilder(Context context, Class<ModelType> modelClass, ModelType url) {
        super(context, modelClass, url);
    }

    @Override
    public DrawableRequestBuilder<ModelType> load(ModelType model) {
        super.load(model);
        return this;
    }


    @Override
    public GenericRequestBuilder<ModelType> diskCacheStrategy(LocalDataSource.DiskCacheStrategy
                                                                          strategy) {
        super.diskCacheStrategy(strategy);
        return this;
    }

    @Override
    public GenericRequestBuilder<ModelType> memoryCacheStrategy(LocalDataSource
                                                                            .MemoryCacheStrategy
                                                                            strategy) {
        super.memoryCacheStrategy(strategy);
        return this;
    }


}
