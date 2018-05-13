package com.joy.glide.library.http;

import com.joy.glide.library.request.RequestOrder;

/**
 * Created by joybar on 2018/5/11.
 */

public abstract class AbstractHttpExecutor implements IHttpExecutor {
    protected RequestOrder requestOrder;

    public RequestOrder getRequestOrder() {
        return requestOrder;
    }

    public AbstractHttpExecutor(RequestOrder requestOrder) {
        this.requestOrder = requestOrder;
    }
}
