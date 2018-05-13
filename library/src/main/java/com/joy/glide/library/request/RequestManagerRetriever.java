package com.joy.glide.library.request;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.helper.lifecycle.LifecycleHelper;
import com.lifecycle.joybar.lifecyclelistener.interfaces.LifecycleListener;

/**
 * Created by joybar on 11/05/2018.
 */

public class RequestManagerRetriever {

    public static RequestManager get(Context context) {
        final RequestManager requestManager = new RequestManager();
        createLifeMonitor(context, requestManager);
        return requestManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static RequestManager get(android.app.Fragment fragment) {
        final RequestManager requestManager = new RequestManager();
        createLifeMonitor(fragment, requestManager);
        return requestManager;
    }

    public static RequestManager get(android.support.v4.app.Fragment fragment) {
        final RequestManager requestManager = new RequestManager();
        createLifeMonitor(fragment, requestManager);
        return requestManager;
    }


    private static void createLifeMonitor(Context context, final RequestManager requestManager) {

        LifecycleHelper.newInstance().registerLifecycleListener(context, new LifecycleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {
                RequestHolder holder = requestManager.getHolder();
                if (null != holder) {
                    holder.cancelRequest();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void createLifeMonitor(android.app.Fragment fragment, final RequestManager
            requestManager) {

        LifecycleHelper.newInstance().registerLifecycleListener(fragment, "android.app.Fragment",
                new LifecycleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {
                RequestHolder holder = requestManager.getHolder();
                if (null != holder) {
                    holder.cancelRequest();
                }
            }
        });

    }

    private static void createLifeMonitor(android.support.v4.app.Fragment fragment, final
    RequestManager requestManager) {

        LifecycleHelper.newInstance().registerLifecycleListener(fragment, "android.support.v4.app" +
                ".Fragment", new LifecycleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {
                RequestHolder holder = requestManager.getHolder();
                if (null != holder) {
                    holder.cancelRequest();
                }
            }
        });

    }


}
