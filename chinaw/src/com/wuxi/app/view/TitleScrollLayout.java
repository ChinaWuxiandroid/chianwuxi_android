package com.wuxi.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Scroller;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.adapter.TitleChannelAdapter;
import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.ChannelContentListFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.CityMapFragment;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 头部可滑动自定义view
 * 
 * @author wanglu
 * 
 */
public class TitleScrollLayout extends ViewGroup {
	private static final int PERSCREEN_ITEM_COUNT = 7;// 每一屏item数量
	private Scroller mScroller;
	private int mCurScreen;// 当前屏
	private int totalScreenNum;// 总屏数
	// private int checkPostion = -1;
	private int mDefaultScreen = 0;
	private VelocityTracker mVelocityTracker;
	private float mLastMotionX;
	// private float mLastMotionY;
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	private static final int SNAP_VELOCITY = 1200;
	private static final String TAG = "TITLESCROLLLAYOUT";
	private int mTouchSlop;

	private InitializContentLayoutListner initializContentLayoutListner;// 该自定义控件所在的fragment
	private int perscreenCount = PERSCREEN_ITEM_COUNT;// 每屏数量,默认为7
	private int checkPositons[];// 选中的坐标
	private MenuItemInitLayoutListener menuItemInitLayoutListener;// 菜单点击与指点界面绑定监听器

	public MenuItemInitLayoutListener getMenuItemInitLayoutListener() {
		return menuItemInitLayoutListener;
	}

	public void setMenuItemInitLayoutListener(
			MenuItemInitLayoutListener menuItemInitLayoutListener) {
		this.menuItemInitLayoutListener = menuItemInitLayoutListener;
	}

	public int getPerscreenCount() {
		return perscreenCount;
	}

	public void setPerscreenCount(int perscreenCount) {
		this.perscreenCount = perscreenCount;
	}

	public void setInitializContentLayoutListner(
			InitializContentLayoutListner initializContentLayoutListner) {
		this.initializContentLayoutListner = initializContentLayoutListner;
	}

	/*
	 * public void setItems(List<Channel> items) { this.items = items; }
	 */
	public TitleScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;// 设置初始屏幕
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if (changed) {

			int childLeft = 0;
			final int childCount = getChildCount();

			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft + childWidth,
							childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");
		}

		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		scrollTo(mCurScreen * width, 0);

	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {// 动画还没有结束
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());// //
			// 产生平滑的动画效果，根据当前偏移量，每次滚动一点
			postInvalidate();// // 此时同样也需要刷新View ，否则效果可能有误差
		}
	}

	public void snapToDestination() {
		final int screenWidth = getWidth();

		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	public void snapToScreen(int whichScreen) {

		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {

			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout

			if (whichScreen == 0) {

			} else {

			}
		}
	}

	/**
	 * 下一屏
	 */
	public void goNextScreen() {

		if (mCurScreen == totalScreenNum) {

			snapToScreen(0);

		} else {
			snapToScreen(mCurScreen + 1);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();
		// final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;

		case MotionEvent.ACTION_MOVE:

			int deltaX = (int) (mLastMotionX - x);

			mLastMotionX = x;

			scrollBy(deltaX, 0);
			break;

		case MotionEvent.ACTION_UP:

			// if (mTouchState == TOUCH_STATE_SCROLLING) {
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();

			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {

				snapToScreen(mCurScreen - 1);
			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {

				snapToScreen(mCurScreen + 1);
			} else {

				snapToDestination();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// }
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.e(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		// final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "onInterceptTouchEvent move");
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;

			}
			break;

		case MotionEvent.ACTION_DOWN:
			Log.e(TAG, "onInterceptTouchEvent down");
			mLastMotionX = x;
			// mLastMotionY = y;
			Log.e(TAG, mScroller.isFinished() + "");
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			Log.e(TAG, "onInterceptTouchEvent up or cancel");
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		Log.e(TAG, mTouchState + "====" + TOUCH_STATE_REST);
		return mTouchState != TOUCH_STATE_REST;
	}

	/*
	 * 初始化屏频道
	 */
	public void initChannelScreen(Context context, LayoutInflater inflater,
			List<Channel> chanItems) {
		if (chanItems == null) {
			return;
		}

		int i = 0;
		totalScreenNum = chanItems.size() / getPerscreenCount();// 屏数

		checkPositons = new int[totalScreenNum + 1];
		for (int j = 0; j < checkPositons.length; j++) {// 初始化头部安选中的下标
			checkPositons[j] = -1;
		}

		checkPositons[0] = 0;// 默认选中第一屏第一个Chanel
		int currentScreen = 0;// 当前屏

		List<Channel> onScreenItems = null;// 一个屏上的图标

		for (Channel item : chanItems) {

			if (i % getPerscreenCount() == 0) {

				if (onScreenItems != null) {
					GridView child = (GridView) inflater.inflate(
							R.layout.title_action_gridview_layout, null);
					child.setNumColumns(perscreenCount); // 设置GridView的每列显示view个数
					child.setAdapter(new TitleChannelAdapter(context,
							R.layout.title_grid_item_layout,
							new int[] { R.id.tv_actionname }, null,
							onScreenItems, currentScreen));

					child.setOnItemClickListener(new TitleItemlOnclick());
					currentScreen++;
					addView(child);
				}

				onScreenItems = new ArrayList<Channel>();
			}

			// 最后一屏操作
			if (currentScreen > totalScreenNum + 1) {

				onScreenItems = new ArrayList<Channel>();
			}

			onScreenItems.add(item);

			// add last category screen //最后一屏
			if (i == chanItems.size() - 1) {
				GridView child = (GridView) inflater.inflate(
						R.layout.title_action_gridview_layout, null);
				child.setNumColumns(perscreenCount); // 设置GridView的每列显示view个数
				child.setAdapter(new TitleChannelAdapter(context,
						R.layout.title_grid_item_layout,
						new int[] { R.id.tv_actionname }, null, onScreenItems,
						currentScreen));

				child.setOnItemClickListener(new TitleItemlOnclick());
				addView(child);

			}

			i++;
		}
	}

	public void initMenuItemScreen(Context context, LayoutInflater inflater,
			List<MenuItem> menuItems) {
		if (menuItems == null) {
			return;
		}
		int i = 0;
		totalScreenNum = menuItems.size() / getPerscreenCount();// 屏数

		checkPositons = new int[totalScreenNum + 1];
		for (int j = 0; j < checkPositons.length; j++) {// 初始化头部安选中的下标
			checkPositons[j] = -1;
		}

		checkPositons[0] = 0;// 默认选中第一屏第一个Chanel
		int currentScreen = 0;// 当前屏

		List<MenuItem> onScreenItems = null;// 一个屏上的图标

		for (MenuItem item : menuItems) {

			if (i % getPerscreenCount() == 0) {

				if (onScreenItems != null) {
					GridView child = (GridView) inflater.inflate(
							R.layout.title_action_gridview_layout, null);
					child.setNumColumns(perscreenCount); // 设置GridView的每列显示view个数
					child.setAdapter(new TitleChannelAdapter(context,
							R.layout.title_grid_item_layout,
							new int[] { R.id.tv_actionname }, null,
							onScreenItems, currentScreen));

					child.setOnItemClickListener(new TitleItemlOnclick());
					currentScreen++;
					addView(child);
				}

				onScreenItems = new ArrayList<MenuItem>();
			}

			// 最后一屏操作
			if (currentScreen > totalScreenNum + 1) {

				onScreenItems = new ArrayList<MenuItem>();
			}

			onScreenItems.add(item);

			// add last category screen //最后一屏
			if (i == menuItems.size() - 1) {
				GridView child = (GridView) inflater.inflate(
						R.layout.title_action_gridview_layout, null);
				child.setNumColumns(perscreenCount); // 设置GridView的每列显示view个数
				child.setAdapter(new TitleChannelAdapter(context,
						R.layout.title_grid_item_layout,
						new int[] { R.id.tv_actionname }, null, onScreenItems,
						currentScreen));

				child.setOnItemClickListener(new TitleItemlOnclick());
				addView(child);

			}

			i++;
		}


		/**
		 * 显示一个的子界面
		 */
		if (menuItems.get(0) != null&&initializContentLayoutListner!=null&&menuItemInitLayoutListener!=null) {

			menuItemInitLayoutListener.bindMenuItemLayout(
					initializContentLayoutListner, menuItems.get(0));

		}

	}

	private class TitleItemlOnclick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {

			Channel channel = null;
			MenuItem menuItem = null;
			Object item = parent.getItemAtPosition(position);
			if (item instanceof Channel) {
				channel = (Channel) item;
			} else if (item instanceof MenuItem) {
				menuItem = (MenuItem) item;
			}

			/**
			 * 切换选中与未选择的样式
			 */
			if (checkPositons[mCurScreen] != position) {
				View checkView = parent.getChildAt(position);

				TextView tv_Check = (TextView) checkView
						.findViewById(R.id.tv_actionname);
				tv_Check.setBackgroundResource(R.drawable.title_item_select_bg);

				tv_Check.setTextColor(Color.WHITE);

				View oldCheckView = parent
						.getChildAt(checkPositons[mCurScreen]);
				if (null != oldCheckView) {

					TextView tv_oldCheck = (TextView) oldCheckView
							.findViewById(R.id.tv_actionname);
					tv_oldCheck.setBackgroundResource(R.drawable.title_item_bg);

					tv_oldCheck.setTextColor(Color.parseColor("#177CCA"));

				}

				checkPositons[mCurScreen] = position;
			}

			/**
			 * 频道处理
			 */
			if (channel != null) {
				Class<? extends Fragment> fragmentClass = channel
						.getContentFragment();
				if (fragmentClass == null) {
					return;
				}
				Fragment fragment;

				try {
					fragment = (Fragment) fragmentClass.newInstance();

					if (fragment == null) {
						return;
					}

					NavigatorWithContentFragment nafragment = null;
					CityMapFragment cityNCityMapFragment = null;
					ChannelContentListFragment channelContentListFragment=null;
					if (fragment instanceof NavigatorWithContentFragment) {
						nafragment = (NavigatorWithContentFragment) fragment;
						nafragment.setParentChannel(channel);
					}

					if (fragment instanceof CityMapFragment) {
						cityNCityMapFragment = (CityMapFragment) fragment;

					}

					if(fragment instanceof ChannelContentListFragment){
						channelContentListFragment=(ChannelContentListFragment)fragment;
						channelContentListFragment.setChannel(channel);
					}

					if (initializContentLayoutListner != null) {
						if (nafragment != null) {
							initializContentLayoutListner
							.bindContentLayout(nafragment);
						} else if(cityNCityMapFragment!=null){
							initializContentLayoutListner
							.bindContentLayout(cityNCityMapFragment);
						}else if(channelContentListFragment!=null){
							initializContentLayoutListner.bindContentLayout(channelContentListFragment);
						}

					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}

			/**
			 * 普通菜单处理
			 * */

			if (menuItem != null&&initializContentLayoutListner!=null&&menuItemInitLayoutListener!=null) {

				menuItemInitLayoutListener.bindMenuItemLayout(
						initializContentLayoutListner, menuItem);

			}

		}
	}

}
