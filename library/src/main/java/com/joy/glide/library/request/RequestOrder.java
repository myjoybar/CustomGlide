package com.joy.glide.library.request;

/**
 * Created by joybar on 11/05/2018.
 */

public class RequestOrder<ModelType> {
    private ModelType url;

    public RequestOrder( ModelType url) {
        this.url = url;
    }

    public ModelType getUrl() {
        return url;
    }

    public void setUrl(ModelType url) {
        this.url = url;
    }


}
