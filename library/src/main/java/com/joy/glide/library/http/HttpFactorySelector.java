package com.joy.glide.library.http;

import com.joy.glide.library.request.RequestOrder;

/**
 * Created by joybar on 11/05/2018.
 */

public class HttpFactorySelector {

    public HttpFactorySelector() {

    }

    public static HttpFactorySelector getInstance() {
        return HttpFactorySelectorHolder.INSTANCE;
    }

    private static class HttpFactorySelectorHolder {
        private static HttpFactorySelector INSTANCE = new HttpFactorySelector();
    }

    private AbstractHttpExecutor abstractHttpExecutor;

    public void register(AbstractHttpExecutor abstractLoader) {
        this.abstractHttpExecutor = abstractLoader;
    }

    public AbstractHttpExecutor get(RequestOrder requestOrder) {

        if (null == this.abstractHttpExecutor) {
            return getDefaultLoader(requestOrder);
        }
        return abstractHttpExecutor;
    }

    private AbstractHttpExecutor getDefaultLoader(RequestOrder requestOrder) {
        AbstractHttpExecutor abstractLoader = new HttpUrlConnectionExecutor(requestOrder);
        return abstractLoader;
    }
}
