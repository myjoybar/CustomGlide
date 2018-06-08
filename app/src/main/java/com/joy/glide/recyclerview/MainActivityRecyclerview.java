package com.joy.glide.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.joy.glide.R;
import com.joy.glide.recyclerview.scroll.MyOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRecyclerview extends AppCompatActivity {

	public static String TAG = "MainActivityRecyclerview";
	RecyclerView mRecyclerViewGlide;
	RecyclerView mRecyclerViewCustom;
	MyRecyclerViewAdapterGlide mAdapterGlide;
	MyRecyclerViewAdapterCustom mAdapterCustom;
	private List<String> list = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recyclerview);
		initView();
		initData();
		init();

	}

	public static void launch(Context context) {
		Intent intent = new Intent(context, MainActivityRecyclerview.class);
		context.startActivity(intent);
	}


	private void initView() {
		mRecyclerViewGlide = this.findViewById(R.id.recyclerView_glide);
		mRecyclerViewCustom = this.findViewById(R.id.recyclerView_custom);
		setSyncScrollListener();
	}

	private void initData() {
		list.add("http://img5.imgtn.bdimg.com/it/u=2668624558,387203383&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=2405687313,174008985&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=4197274669,181175531&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=1857462899,1195494486&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3522003827,1226970953&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=3695012849,2187312718&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=707640961,180539372&fm=27&gp=0.jpg");

		list.add("http://img4.imgtn.bdimg.com/it/u=2805785016,341375152&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=4147106169,473776928&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=2609761047,1225651583&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=1812633575,1812552987&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=1587434617,1277385928&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=1235620826,1419062545&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=1301694567,547733201&fm=27&gp=0.jpg");

		list.add("http://img0.imgtn.bdimg.com/it/u=809274124,4233219552&fm=200&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1022566681,4123623721&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=2482506259,4267507750&fm=27&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1915399866,2742610562&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=2189305125,2874193813&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=1019279958,955675459&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3468206513,79926427&fm=27&gp=0.jpg");

		list.add("http://img1.imgtn.bdimg.com/it/u=3578955904,2425731367&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3468206513,79926427&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=3417801443,870095972&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=2382366205,4060739127&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=4069765457,2488818548&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=2429354072,3430411453&fm=200&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=1744307959,22535274&fm=27&gp=0.jpg");

		list.add("http://img3.imgtn.bdimg.com/it/u=1258366230,620718135&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=1646600758,860827457&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=2013176511,2264551775&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=361385290,489958975&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=1765788176,22532200&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=240097132,1017964631&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=1757009763,2984559819&fm=27&gp=0.jpg");

		list.add("http://img5.imgtn.bdimg.com/it/u=3904771758,1418800557&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=4191549131,1692484288&fm=27&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=4179113148,4276785818&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=4261980161,245101910&fm=27&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1365680493,2124111847&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=1250799230,514903738&fm=200&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=2411785438,798115507&fm=27&gp=0.jpg");

		list.add("http://img0.imgtn.bdimg.com/it/u=2007284061,380792847&fm=200&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1465133425,2439891423&fm=27&gp=0.jpg");
		list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=881393512,3394628152&fm=27&gp=0.jpg");
		list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2890688175,1942477241&fm=27&gp=0.jpg");
		list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2531471898,4115172998&fm=27&gp=0.jpg");
		list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1401876863,2911612294&fm=27&gp=0.jpg");
		list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2710492287,3970757263&fm=27&gp=0.jpg");

		list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=23401111,3556448271&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3614988334,3346936356&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=927046211,4051053014&fm=27&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1998769469,2582487303&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=1167727341,2900089500&fm=27&gp=0.jpg");
		list.add("http://img2.imgtn.bdimg.com/it/u=3977338133,2306661451&fm=27&gp=0.jpg");
		list.add("http://img4.imgtn.bdimg.com/it/u=1257600181,2871133911&fm=27&gp=0.jpg");

		list.add("http://img2.imgtn.bdimg.com/it/u=222864698,3741205794&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=2142830456,2128672714&fm=27&gp=0.jpg");
		list.add("http://img3.imgtn.bdimg.com/it/u=1487780665,1631739872&fm=27&gp=0.jpg");
		list.add("http://img1.imgtn.bdimg.com/it/u=147275873,1191144746&fm=27&gp=0.jpg");
		list.add("http://img0.imgtn.bdimg.com/it/u=3528786179,1285043399&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=1686018272,3524608711&fm=27&gp=0.jpg");
		list.add("http://img5.imgtn.bdimg.com/it/u=749640632,266828823&fm=27&gp=0.jpg");

	}

	private void init() {
		mRecyclerViewGlide.setLayoutManager(new GridLayoutManager(this, 1));
		mRecyclerViewCustom.setLayoutManager(new GridLayoutManager(this, 1));
		mAdapterGlide = new MyRecyclerViewAdapterGlide(list);
		mAdapterCustom = new MyRecyclerViewAdapterCustom(list);
		mRecyclerViewGlide.setAdapter(mAdapterGlide);
		mRecyclerViewCustom.setAdapter(mAdapterCustom);
	}

	private final RecyclerView.OnScrollListener mLayerOSL = new MyOnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			// 当楼层列表滑动时，单元（房间）列表也滑动
			mRecyclerViewGlide.scrollBy(dx, dy);
		}
	};


	private final RecyclerView.OnScrollListener mRoomOSL = new MyOnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			// 当单元（房间）列表滑动时，楼层列表也滑动
			mRecyclerViewCustom.scrollBy(dx, dy);
		}
	};

	private void setSyncScrollListener() {
		mRecyclerViewCustom.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

			private int mLastY;

			@Override
			public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
				// 当列表是空闲状态时
				if (rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					onTouchEvent(rv, e);
				}
				return false;
			}

			@Override
			public void onTouchEvent(RecyclerView rv, MotionEvent e) {
				// 若是手指按下的动作，且另一个列表处于空闲状态
				if (e.getAction() == MotionEvent.ACTION_DOWN && mRecyclerViewGlide.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					// 记录当前另一个列表的y坐标并对当前列表设置滚动监听
					mLastY = rv.getScrollY();
					rv.addOnScrollListener(mLayerOSL);
				} else {
					// 若当前列表原地抬起手指时，移除当前列表的滚动监听
					if (e.getAction() == MotionEvent.ACTION_UP && rv.getScrollY() == mLastY) {
						rv.removeOnScrollListener(mLayerOSL);
					}
				}
			}

			@Override
			public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

			}
		});

	}


	private void setSyncScrollListener1() {


		mRecyclerViewGlide.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

			private int mLastY;

			@Override
			public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
				if (rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					onTouchEvent(rv, e);
				}
				return false;
			}

			@Override
			public void onTouchEvent(RecyclerView rv, MotionEvent e) {
				if (e.getAction() == MotionEvent.ACTION_DOWN && mRecyclerViewCustom.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					mLastY = rv.getScrollY();
					rv.addOnScrollListener(mRoomOSL);
				} else {
					if (e.getAction() == MotionEvent.ACTION_UP && rv.getScrollY() == mLastY) {
						rv.removeOnScrollListener(mRoomOSL);
					}
				}
			}

			@Override
			public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

			}
		});
	}


}
