package com.wuxi.app.fragment.index.type;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.NavigatorChannelFragment;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 频道菜单类型的fragment
 * 
 * @author wanglu
 * 
 */

public class ChannelFragment extends BaseSlideFragment implements
		InitializContentLayoutListner, OnClickListener {

	private TitleScrollLayout mtitleScrollLayout;
	private static final int MANCOTENT_ID = R.id.model_main;
	private static final int TITLE__LOAD_SUCESS = 0;
	private static final int TITLE_LOAD_ERROR = 1;
	protected static final String TAG = "ChannelFragment";
	private LayoutInflater inflater;
	private ImageButton ib_nextItems;
	private MenuItem menuItem;// 菜单项
	private List<Channel> titleChannels;// 头部频道
	private Context context;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			
			if (msg.obj != null) { tip = msg.obj.toString(); }
			 
			switch (msg.what) {
			case TITLE__LOAD_SUCESS:
				showTitleData();
				break;
			case TITLE_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_chanel_layout, null);
		this.InitBtn();
		this.setFragmentTitle(menuItem.getChannelName());// 设置头部频道名称
		this.inflater = inflater;
		context = this.getActivity();
		initUI();
		return view;
	}

	@Override
	public void bindContentLayout(Fragment fragment) {

		bindFragment(fragment);

	}

	/**
	 * 
	 * wanglu 泰得利通 初始化界面
	 */
	private void initUI() {
		mtitleScrollLayout = (TitleScrollLayout) view
				.findViewById(R.id.title_scroll_action);// 头部控件
		mtitleScrollLayout.setInitializContentLayoutListner(this);// 设置绑定内容界面监听器

		ib_nextItems = (ImageButton) view.findViewById(R.id.btn_next_screen);// 头部下一个按钮
		ib_nextItems.setOnClickListener(this);

		loadTitleData();
		

		

	}

	/**
	 * 
	 *wanglu 泰得利通
	 *初始化界面
	 */
	private void initData(Channel parentChannel) {
	
		NavigatorChannelFragment navigatorChannelFragment=new NavigatorChannelFragment();
		navigatorChannelFragment.setParentChannel(parentChannel);
		bindFragment(navigatorChannelFragment);
	}

	/**
	 * 
	 * wanglu 泰得利通 加载头部Channel
	 */
	@SuppressWarnings("unchecked")
	public void loadTitleData() {

		if (CacheUtil.get(menuItem.getChannelId()) != null) {// 从缓存获取

			titleChannels = (List<Channel>) CacheUtil.get(menuItem
					.getChannelId());
			showTitleData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ChannelService channelService = new ChannelService(context);
				try {
					titleChannels = channelService.getSubChannels(menuItem
							.getChannelId());
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

		mtitleScrollLayout.initScreen(context, inflater, titleChannels);// 初始化头部空间
		initData(titleChannels.get(0));//默认显示第一个channel的子channel页

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	private void bindFragment(Fragment fragment) {
	
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(MANCOTENT_ID, fragment);// 替换内容界面

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
