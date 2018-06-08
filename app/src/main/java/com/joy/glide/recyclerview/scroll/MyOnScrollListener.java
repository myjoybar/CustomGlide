package com.joy.glide.recyclerview.scroll;

import android.support.v7.widget.RecyclerView;

/**
 * Created by joybar on 2018/6/5.
 */

public class MyOnScrollListener extends RecyclerView.OnScrollListener {
	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		if (newState == recyclerView.SCROLL_STATE_IDLE) {
			recyclerView.removeOnScrollListener(this);
		}
	}
}

