package com.joy.glide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

	public static String TAG = "MainActivity";
	ImageView imv1;
	LinearLayout lin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setClickListener();

	}

	private void setClickListener() {
		findViewById(R.id.btn_net).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadImageFromServer();
			}
		});

		findViewById(R.id.btn_sdcard).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadImageFromSdcard();
			}
		});

		findViewById(R.id.btn_into_viewgroup).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadImageFromSdcard();
			}
		});
	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
		lin = this.findViewById(R.id.lin);
	}

	private void loadImageFromServer(){
		String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527160616639&di=383b0369f49ac965de63a578779c3fea"
				+ "&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2013%2F02%2F20130214y_5%2Fimage291_wm.jpg";
		Glide.with(MainActivity.this)
				.load(url)
				.placeholder(R.drawable.placeholder)
				.error(R.drawable.error)
				.memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
				.diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true,true))
				.transform(new CircleCrop(MainActivity.this))
				.listener(new DataSource.LoadDataListener() {
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
				}).into(imv1);

	}

	private void loadImageFromSdcard(){
		File file = new File("/storage/emulated/0/Pictures/1525965726567.jpg");
		File file2 = new File("/storage/emulated/0/DCIM/Camera/IMG_20160603_211526.jpg");
		Glide.with(MainActivity.this)
				.load(file)
				.error(R.drawable.error)
				.memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
				.diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true,true))
				.transform(new CircleCrop(MainActivity.this))
				.into(imv1);

	}

	private void loadImageIntoViewGroup() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		Glide.with(MainActivity.this)
				.load(url)
				.error(R.drawable.error)
				.memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
				.diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true,true))
				.transform(new CircleCrop(MainActivity.this))
				.into(new SimpleDrawableViewTarget(lin));
	}


	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527160616639&di=383b0369f49ac965de63a578779c3fea" +
				"&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2013%2F02%2F20130214y_5%2Fimage291_wm.jpg";

		String urlGif = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3777450208,2776164194&fm=27&gp=0.jpg";

		File file = new File("/storage/emulated/0/Pictures/1525965726567.jpg");
		File file2 = new File("/storage/emulated/0/DCIM/Camera/IMG_20160603_211526.jpg");

		//GLog.printInfo("file="+file.getName());
		GLog.printInfo("file=" + file2.getAbsolutePath());
		String urlBig = "https://github.com/myjoybar/Android-RecyclerView/blob/master/Android-RecyclerView/Image/demo.gif?raw=true";

		String urlbig2 = "https://github.com/myjoybar/Android-RecyclerView/blob/master/Android-RecyclerView/Image/demo.gif";
		Glide.with(this).load(url)
				//.load(new MyUrl(url))
				.placeholder(R.drawable.placeholder).error(R.drawable.error).memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
				.transform(new CircleCrop(this)).diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(false, false)).listener(new DataSource
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
				//	GLog.printInfo("onProgressUpdate, value" + value);
			}


			@Override
			public void onCancelled() {
				GLog.printInfo("onCancelled");
			}
		})
				//		.preload();
				.into(imv1);
		//.asDrawable()
		//		.into(new SimpleDrawableViewTarget(lin));

	}

	public class MyUrl extends RequestOrder<String> {


		public MyUrl(String url) {
			super(url);
		}

		@Override
		public String getUrl() {
			return "aaaa";
		}
	}



}
