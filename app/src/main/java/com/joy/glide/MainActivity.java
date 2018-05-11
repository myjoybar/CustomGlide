package com.joy.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.joy.glide.libasync.factory.Callback;
import com.joy.glide.libasync.factory.FactoryManager;
import com.joy.glide.libasync.order.RequestTaskOrder;
import com.joy.glide.libasync.task.TaskRunnable;
import com.joy.glide.library.Glide;
import com.joy.glide.library.asynfactory.AsyncFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

	public static String TAG = "MainActivity";
	ImageView imv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		load();
		//	testTread();
		//testTreadPool();
		testFactory();
	}

	private void initView() {
		imv1 = this.findViewById(R.id.imv1);
		//	AsyncTask
	}

	private void load() {
		String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
		Glide.with(this).load(url).into(imv1);


	}

	String getThreadName() {
		return Thread.currentThread().getName() + ":===";
	}

	private void testTread() {
		for (int i = 0; i < 100; i++) {

			TaskRunnable taskRunnable = new TaskRunnable<Integer, String>("TaskRunnable  %s", "thread" + i) {


				@Override
				protected String doInBackground() {
					Log.d(TAG, getThreadName() + "doInBackground=");
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return "aaaa" + getThreadName();
				}

				@Override
				protected void onProgressUpdate(Integer... values) {
					super.onProgressUpdate(values);
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					Log.d(TAG, getThreadName() + "result=" + result + Thread.currentThread().getName());

				}
			};

			new Thread(taskRunnable).start();
		}
	}

	private void testTreadPool() {
		Executor executor = Executors.newFixedThreadPool(5);
		//Executor executor = Executors.newCachedThreadPool();
		int CORE_POOL_SIZE = Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4));
		Log.d(TAG, "CORE_POOL_SIZE=" + CORE_POOL_SIZE);
		for (int i = 0; i < 200; i++) {

			TaskRunnable taskRunnable = new TaskRunnable<Integer, String>() {

				@Override
				protected String doInBackground() {
					Log.d(TAG, "子线程："+getThreadName() + "后台执行");
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return "AAA";
				}

				@Override
				protected void onProgressUpdate(Integer... values) {
					super.onProgressUpdate(values);
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					Log.d(TAG, getThreadName() + "result=" + result);

				}
			};

			//executor.execute(taskRunnable);
			//ProductLineDispatcher.getInstance().execute(taskRunnable);
			//ProductLineDispatcher2.getInstance().execute(taskRunnable);
			FactoryManager.getInstance().produce(taskRunnable);
		}
	}


	private void  testFactory(){

		RequestTaskOrder order = new RequestTaskOrder();
		AsyncFactory.getInstance().produce(order, new Callback<String>() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onResponse(String result) {
				Log.d(TAG, getThreadName() + "result=" + result);
			}
		});

	}
}
