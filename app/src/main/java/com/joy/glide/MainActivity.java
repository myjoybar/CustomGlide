package com.joy.glide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joy.glide.library.Glide;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.load.resource.bitmap.CircleCrop;
import com.joy.glide.library.request.RequestOrder;
import com.joy.glide.library.request.target.SimpleDrawableViewTarget;
import com.joy.glide.library.utils.GLog;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "MainActivity";
    private Context context;
    private ImageView imv;
    private Button btnNet;
    private Button btnSdcard;
    private Button btnIntoViewGroup;
    private Button btnPreload;
    private Button btnCustomUrl;
    private LinearLayout viewGroupForImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        setClickListener();

    }

    private void initView() {
        imv = this.findViewById(R.id.imv);
        btnNet = this.findViewById(R.id.btn_net);
        btnSdcard = this.findViewById(R.id.btn_sdcard);
        btnIntoViewGroup = this.findViewById(R.id.btn_into_viewgroup);
        btnPreload = this.findViewById(R.id.btn_preload);
        btnCustomUrl = this.findViewById(R.id.btn_custom_url);
        viewGroupForImg = this.findViewById(R.id.lin_for_img);
    }

    @Override
    public void onClick(View v) {
        imv.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.btn_net:
                loadImageFromServer();
                break;
            case R.id.btn_sdcard:
                loadImageFromSdcard();
                break;
            case R.id.btn_into_viewgroup:
                imv.setVisibility(View.GONE);
                viewGroupForImg.setVisibility(View.VISIBLE);
                loadImageIntoViewGroup();
                break;
            case R.id.btn_preload:
                preload();
                break;
            case R.id.btn_custom_url:
                LoadCustomUrl();
                break;
            default:
                break;

        }
    }


    private void setClickListener() {
        btnSdcard.setOnClickListener(this);
        btnIntoViewGroup.setOnClickListener(this);
        btnPreload.setOnClickListener(this);
        btnCustomUrl.setOnClickListener(this);
        btnNet.setOnClickListener(this);

    }


    private void loadImageFromServer() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528541262255&di=4a4ab2d5bd7419e7aa9fd8f8e71a3f3b&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd31b0ef41bd5ad6e181f5a0587cb39dbb7fd3c7e.jpg";
        String urlGif = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=37774502082776164194&fm=27&gp=0.jpg";
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy (true, 60))
                .diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
                .transform(new CircleCrop(MainActivity.this)).listener(new DataSource
                .LoadDataListener() {
            @Override
            public void onLoadStarted() {
                GLog.printInfo("onLoadStarted");
            }

            @Override
            public void onDataLoaded(Object resource) {
                GLog.printInfo("onResourceReady");
            }

            @Override
            public void onDataLoadedError(@NonNull Throwable throwable) {
                GLog.printInfo("onException, " + throwable.getMessage());
            }

            @Override
            public void onProgressUpdate(int value) {
                GLog.printInfo("onProgressUpdate, value" + value);
            }

            @Override
            public void onCancelled() {
                GLog.printInfo("onCancelled");
            }
        }).into(imv);

    }

    private void loadImageFromSdcard() {
        File file = new File("/storage/emulated/0/Pictures/1525965726567.jpg");
        File file2 = new File("/storage/emulated/0/DCIM/Camera/IMG_20160603_211526.jpg");
        Glide.with(context)
                .load(file)
                .error(R.drawable.error)
                .memoryCacheStrategy(new  LocalDataSource.MemoryCacheStrategy(true, 60))
                .diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
                .transform(new CircleCrop(context))
                .into(imv);
    }

    private void loadImageIntoViewGroup() {
        String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        Glide.with(context)
                .load(url).error(R.drawable.error)
                .memoryCacheStrategy(new  LocalDataSource.MemoryCacheStrategy(true, 60))
                .diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
                .transform(new CircleCrop(context))
                .into(new SimpleDrawableViewTarget(viewGroupForImg));
    }

    private void preload() {
        String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        Glide.with(context)
                .load(url)
                .error(R.drawable.error)
                .memoryCacheStrategy(new  LocalDataSource.MemoryCacheStrategy(true, 60))
                .diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
                .preload();
    }

    private void LoadCustomUrl() {
        String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        Glide.with(context)
                .load(new MyUrl(url))
                .error(R.drawable.error)
                .memoryCacheStrategy(new  LocalDataSource.MemoryCacheStrategy(true, 60))
                .diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
                .preload();
    }

    public class MyUrl extends RequestOrder<String> {

        public MyUrl(String url) {
            super(url);
        }

        @Override
        public String getUrl() {
            return "this is new url";
        }
    }


}
