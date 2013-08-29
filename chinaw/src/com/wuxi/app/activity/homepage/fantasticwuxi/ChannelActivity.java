/*
 * (#)ChannelActivity.java 1.0 2013-8-29 2013-8-29 GMT+08:00
 */
package com.wuxi.app.activity.homepage.fantasticwuxi;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.ChannelContentListFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.CityMapFragment;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * @author wanglu 泰得利通 魅力锡城Activity  频道最外层activity
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00
 * 
 */
public class ChannelActivity extends BaseSlideActivity implements
		InitializContentLayoutListner {

	private TitleScrollLayout mtitleScrollLayout;

	private static final int MANCOTENT_ID = R.id.model_main;

	private static final int TITLE__LOAD_SUCESS = 0;

	private static final int TITLE_LOAD_ERROR = 1;

	protected static final String TAG = "ChannelFragment";

	public static final String SHOWCHANNEL_LAYOUT_INDEXKEY = "show_channel_layout_index";

	private ImageButton ib_nextItems;

	private List<Channel> titleChannels;// 头部频道

	private int perCount = 4;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case TITLE__LOAD_SUCESS:
				showTitleData();
				break;
			case TITLE_LOAD_ERROR:
				Toast.makeText(ChannelActivity.this, tip, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void findMainContentViews(View view) {

		Bundle bundle = getIntent().getExtras();
		int showIndex = 0;
		if (bundle != null) {
			showIndex = bundle.getInt(SHOWCHANNEL_LAYOUT_INDEXKEY);//
		}

		mtitleScrollLayout = (TitleScrollLayout) view.findViewById(R.id.title_scroll_action);// 头部控件
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器
		mtitleScrollLayout.setPerscreenCount(perCount);
		int screenIndex = showIndex / perCount;// 第几屏
		int showScreenIndex = showIndex % perCount;// 屏的

		mtitleScrollLayout.setShowItemIndex(showScreenIndex);// 设置显示的默认布局
		mtitleScrollLayout.setmCurScreen(screenIndex);
		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);// 头部下一个按钮
		ib_nextItems.setOnClickListener(this);

		loadTitleData();

	}

	@Override
	public void bindContentLayout(BaseFragment fragment) {

		bindFragment(fragment);

	}

	/**
	 * 
	 * wanglu 泰得利通 加载头部Channel
	 */
	@SuppressWarnings("unchecked")
	public void loadTitleData() {

		if (CacheUtil.get(menuItem.getChannelId()) != null) {// 从缓存获取

			titleChannels = (List<Channel>) CacheUtil.get(menuItem.getChannelId());
			showTitleData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ChannelService channelService = new ChannelService(
					ChannelActivity.this);
				try {
					titleChannels = channelService.getSubChannels(menuItem.getChannelId());
					if (null != titleChannels) {
						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						handler.sendEmptyMessage(TITLE__LOAD_SUCESS);

					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(TITLE_LOAD_ERROR);
					}

				} catch (NetException e) {

					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();

					handler.sendEmptyMessage(TITLE_LOAD_ERROR);

				}
			}
		}

		).start();

	}

	/**
	 * 显示头部数据 wanglu 泰得利通
	 */
	private void showTitleData() {
		initializSubFragmentsLayout();
		mtitleScrollLayout.initChannelScreen(
			ChannelActivity.this, getLayoutInflater(), titleChannels);// 初始化头部空间

		// initData(titleChannels.get(0));// 默认显示第一个channel的子channel页

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	private void bindFragment(BaseFragment fragment) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(MANCOTENT_ID, fragment);// 替换内容界面
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();

	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void initializSubFragmentsLayout() {

		for (Channel channel : titleChannels) {
			if (channel.getChannelName().equals("城市地图")) {
				channel.setContentFragment(CityMapFragment.class);
			} else if (channel.getChildrenChannelsCount() > 0) {
				channel.setContentFragment(NavigatorWithContentFragment.class);
			} else if (channel.getChildrenContentsCount() > 0) {
				channel.setContentFragment(ChannelContentListFragment.class);
			}
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_chanel_layout;
	}

	@Override
	protected String getTitleText() {

		return menuItem.getName();
	}

	@Override
	public void redirectFragment(MenuItem showMenuItem, int showMenuPositon,
			int subMenuPostion) {

	}

}
