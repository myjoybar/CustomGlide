package com.joy.glide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.utils.GLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivityB extends AppCompatActivity {

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

	public static void launch(Context context) {
		Intent intent = new Intent(context, MainActivityB.class);
		context.startActivity(intent);
	}

	private void setClickListener() {
		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				load();
			}
		});
	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
	}

	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		String url2 = "\t\thttps://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527160616639&di=383b0369f49ac965de63a578779c3fea" +
				"&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2013%2F02%2F20130214y_5%2Fimage291_wm.jpg\n";
		String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527755692&di=92a1fa763f1ad16fab7519b63585f4a1&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fe%2F57ea2e81b367d.jpg";

		Glide.with(this).load(url3).placeholder(R.drawable.placeholder).error(R.drawable.error).listener(new DataSource.LoadDataListener() {
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

		StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url2, null, new Response.Listener<JSONObject>() {
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

	private void test() {

	}

}
