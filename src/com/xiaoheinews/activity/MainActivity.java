package com.xiaoheinews.activity;

import java.util.ArrayList;
import java.util.List;

import xom.xiaoheinews.view.DrawerView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xiaoheinews.adapter.MyFragmentPagerAdapter;
import com.xiaoheinews.fragment.BaseFragment;
import com.xiaoheinews.fragment.newsFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends FragmentActivity implements OnClickListener {

	/** head 头部 的中间的loading */
	private ProgressBar top_progress;

	/** head 头部 的左侧菜单 按钮 */
	private ImageView top_head;

	/** head 头部 的左侧菜单 按钮 */
	private ImageView right_head;

	private SlidingMenu menu;

	private long mExitTime;

	/** 导航栏滚动视图 */
	private HorizontalScrollView mHorizontalScrollView;
	private LinearLayout mLinearLayout;
	private ViewPager pager;
	private ImageView mImageView;
	private int mScreenWidth; // 屏幕宽
	private int item_width;// 导航栏item 宽

	private int endPosition; // 导航栏item 结束位置
	private int beginPosition; // 开始位置
	private int currentFragmentIndex; // 目前的fragment 的index
	private boolean isEnd; // 是否为最后

	private ArrayList<Fragment> fragments; // 存放fragments的list

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * 获取屏幕宽
		 */
		getScreenWidth();

		/**
		 * 侧边栏初始化
		 */
		initSlidingMenu();

		/**
		 * 初始化界面
		 */
		initView();

	}

	/**
	 * 获取屏幕宽
	 */
	private void getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

	}

	/**
	 * 初始化界面
	 */
	private void initView() {

		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
		mLinearLayout = (LinearLayout) findViewById(R.id.hsv_content);
		mImageView = (ImageView) findViewById(R.id.img1);
		item_width = (int) ((mScreenWidth / 4.0 + 0.5f));
		mImageView.getLayoutParams().width = item_width;
		top_head = (ImageView) findViewById(R.id.top_head);
		top_head.setOnClickListener(this);

		pager = (ViewPager) findViewById(R.id.pager);
		// 初始化导航
		initNav();
		// 初始化viewPager
		initViewPager();

	}

	/**
	 * 初始化viewPager
	 */
	private void initViewPager() {
		fragments = new ArrayList<Fragment>();
		newsFragment news = new newsFragment();
		fragments.add(news);
		for (int i = 0; i < 6; i++) {
			Bundle data = new Bundle();
			data.putString("text", (i + 1) + "");
			BaseFragment fragment = new BaseFragment();
			fragment.setArguments(data);
			fragments.add(fragment);
		}

		MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		pager.setAdapter(fragmentPagerAdapter);
		fragmentPagerAdapter.setFragments(fragments);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
		pager.setCurrentItem(0);

	}

	/**
	 * 初始化导航栏
	 */
	private void initNav() {
		List<String> nav = new ArrayList<String>();
		nav.add("推荐");
		nav.add("校园");
		nav.add("学院");
		nav.add("社团");
		nav.add("兼职");
		nav.add("二手");
		nav.add("你好");
		for (int i=0;i<nav.size();i++) {
			RelativeLayout layout = new RelativeLayout(this);
			TextView view = new TextView(this);
			view.setText(nav.get(i));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			layout.setId(100);
			layout.addView(view, params);
			mLinearLayout.addView(layout, (int) (mScreenWidth / 4 + 0.5f), 50);
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					pager.setCurrentItem((Integer) v.getTag());

				}
			});

			layout.setTag(i);
		}

	}

	private void initSlidingMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_drawer_fragment);

	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(final int position) {
			Animation animation = new TranslateAnimation(endPosition, position
					* item_width, 0, 0);

			beginPosition = position * item_width;

			currentFragmentIndex = position;
			if (animation != null) {
				animation.setFillAfter(true);
				animation.setDuration(0);
				mImageView.startAnimation(animation);
				mHorizontalScrollView.smoothScrollTo((currentFragmentIndex - 1)
						* item_width, 0);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (!isEnd) {
				if (currentFragmentIndex == position) {
					endPosition = item_width * currentFragmentIndex
							+ (int) (item_width * positionOffset);
				}
				if (currentFragmentIndex == position + 1) {
					endPosition = item_width * currentFragmentIndex
							- (int) (item_width * (1 - positionOffset));
				}

				Animation mAnimation = new TranslateAnimation(beginPosition,
						endPosition, 0, 0);
				mAnimation.setFillAfter(true);
				mAnimation.setDuration(0);
				mImageView.startAnimation(mAnimation);
				mHorizontalScrollView.invalidate();
				beginPosition = endPosition;
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_DRAGGING) {
				isEnd = false;
			} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
				isEnd = true;
				beginPosition = currentFragmentIndex * item_width;
				if (pager.getCurrentItem() == currentFragmentIndex) {
					// 未跳入下一个页面
					mImageView.clearAnimation();
					Animation animation = null;
					// 恢复位置
					animation = new TranslateAnimation(endPosition,
							currentFragmentIndex * item_width, 0, 0);
					animation.setFillAfter(true);
					animation.setDuration(1);
					mImageView.startAnimation(animation);
					mHorizontalScrollView.invalidate();
					endPosition = currentFragmentIndex * item_width;
				}
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_head:
			if (menu.isSecondaryMenuShowing()) {
				menu.showContent();
			} else {
				menu.showSecondaryMenu();
			}
			break;

		default:
			break;
		}

	}
}
