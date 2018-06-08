package com.joy.glide.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joy.glide.R;
import com.joy.glide.library.Glide;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.data.source.local.LocalDataSource;
import com.joy.glide.library.utils.GLog;

import java.util.List;

/**
 * Created by joybar on 2018/6/5.
 */

public class MyRecyclerViewAdapterGlide extends RecyclerView.Adapter<MyRecyclerViewAdapterGlide.ViewHolder> {

	private List<String> list;

	public MyRecyclerViewAdapterGlide(List<String> list) {
		this.list = list;
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
		MyRecyclerViewAdapterGlide.ViewHolder viewHolder = new MyRecyclerViewAdapterGlide.ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tv.setText(list.get(position));
		test2(holder.imageView,list.get(position),holder.progressBar);

	}


	private void test1(ImageView imageView, String url, final ProgressBar progressBar){
		Glide.with(imageView.getContext()).load(url)
				.placeholder(R.drawable.placeholder)
				.error(R.drawable.error)
				.memoryCacheStrategy(new LocalDataSource.MemoryCacheStrategy(true, 60))
				.diskCacheStrategy(new LocalDataSource.DiskCacheStrategy(true, true))
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
					//GLog.printInfo("onProgressUpdate, value" + value);
				progressBar.setProgress(value);
			}


			@Override
			public void onCancelled() {
				GLog.printInfo("onCancelled");
			}
		}).into(imageView);

	}

	private void test2(ImageView imageView, String url, final ProgressBar progressBar){
		com.bumptech.glide.Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.placeholder)
				.error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}


	@Override
	public int getItemCount() {
		return list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView tv;
		ImageView imageView;
		ProgressBar progressBar;

		ViewHolder(View itemView) {
			super(itemView);
			tv = itemView.findViewById(R.id.tv_url);
			imageView = itemView.findViewById(R.id.image);
			progressBar = itemView.findViewById(R.id.progress);
		}
	}
}
