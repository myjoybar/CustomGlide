package com.joy.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.joy.glide.library.Glide;

public class MainActivity extends AppCompatActivity {

	public static String TAG = "MainActivity";
	ImageView imv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		load();

	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
	}

	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		Glide.with(this).load(url).into(imv1);

	}









}
