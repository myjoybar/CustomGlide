package com.joy.glide.library.request;

/**
 * Created by joybar on 11/05/2018.
 */

public class RequestOrder {
    private String url;

    public RequestOrder() {
    }

    public RequestOrder(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
