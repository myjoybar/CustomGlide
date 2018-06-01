package com.joy.glide;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joy.glide.library.Glide;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.request.target.RequestListener;
import com.joy.glide.library.utils.GLog;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	public static String TAG = "MainActivity";
	ImageView imv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		//testString();
		//testJson();
		setClickListener();

	}
	private void setClickListener(){
		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				load();
				//MainActivityB.launch(MainActivity.this);
			}
		});
	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
	}

	private void test2(){
		File file = new File(getExternalCacheDir() + "/image.jpg");
		//Glide.with(this).load(file).into(imageView);
	}


	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		String url2 = "\t\thttps://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527160616639&di=383b0369f49ac965de63a578779c3fea" +
				"&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2013%2F02%2F20130214y_5%2Fimage291_wm.jpg\n";

		String urlGif = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3777450208,2776164194&fm=27&gp=0.jpg";

		File file = new File("/storage/emulated/0/Pictures/1525965726567.jpg");
		File file2 = new File("/storage/emulated/0/DCIM/Camera/IMG_20160603_211526.jpg");

		//GLog.printInfo("file="+file.getName());
		GLog.printInfo("file="+file2.getAbsolutePath());
		String urlBig  = "https://github.com/myjoybar/Android-RecyclerView/blob/master/Android-RecyclerView/Image/demo.gif?raw=true";

		String urlbig2 = "https://github.com/myjoybar/Android-RecyclerView/blob/master/Android-RecyclerView/Image/demo.gif";
		Glide.with(this)
				.load(url)
				.placeholder(R.drawable.placeholder)
				.error(R.drawable.error)
				.cacheStrategySwitcher(new LocalDataSource.CacheStrategySwitcher(true,true))
				.listener(new RequestListener() {
			@Override
			public void onLoadStarted() {
				GLog.printInfo("onLoadStarted");
			}

			@Override
			public void onProgressUpdate(int value) {
				//GLog.printInfo("onProgressUpdate, value"+value);
			}

			@Override
			public void onResourceReady(Bitmap bitmap) {
				GLog.printInfo("onResourceReady");
			}

			@Override
			public void onException(Throwable throwable) {
				GLog.printInfo("onException, "+throwable.getMessage());
			}

			@Override
			public void onCancelled() {
				GLog.printInfo("onCancelled");
			}
		}).into(imv1);

	}


	private void testString() {
		RequestQueue mQueue = Volley.newRequestQueue(this);
		String url1 = "https://www.baidu.com";
		String url2 = "http://www.weather.com.cn/data/cityinfo/101010100.html";
		StringRequest stringRequest = new StringRequest(url1, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, error.getMessage(), error);
			}
		});

		StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,  new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, error.getMessage(), error);
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("params1", "value1");
				map.put("params2", "value2");
				return map;
			}
		};

		mQueue.add(stringRequest);
	}

	private void testJson() {
		String url1 = "https://www.baidu.com";
		String url2 = "http://www.weather.com.cn/data/cityinfo/101010100.html";
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url2, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, error.getMessage(), error);
			}
		});
		RequestQueue mQueue = Volley.newRequestQueue(this);
		mQueue.add(jsonObjectRequest);
	}

	private void test(){

	}

}
