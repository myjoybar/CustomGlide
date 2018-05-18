package com.joy.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONObject;

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
		load();
		//testString();
		//testJson();

	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
	}

	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		Glide.with(this).load(url).into(imv1);

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
