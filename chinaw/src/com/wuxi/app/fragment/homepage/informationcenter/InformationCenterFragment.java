package com.wuxi.app.fragment.homepage.informationcenter;
import java.util.List;
import org.json.JSONException;
import com.wuxi.app.R;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.listeners.InitializContentLayoutListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.view.TitleScrollLayout;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;
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

/**
 * 
 * @author wanglu 泰得利通 资讯中心
 * 
 */
public class InformationCenterFragment extends BaseSlideFragment implements
		InitializContentLayoutListner, OnClickListener {

	private LayoutInflater inflater;
	private Context context;
	private MenuItem menuItem;
	private List<MenuItem> titleMenuItems;// 头部子菜单
	private TitleScrollLayout mtitleScrollLayout;
	private ImageButton ib_nextItems;
	private static final int MANCOTENT_ID = R.id.model_main;
	private static final int TITLE_LOAD_SUCCESS = 0;// 头部加载成功
	protected static final int TITLE_LOAD_FAIL = 1;// 加载失败

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case TITLE_LOAD_FAIL:
				String tip = "";
				if (msg.obj != null) {
					tip = msg.obj.toString();
					Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				}
				break;
			case TITLE_LOAD_SUCCESS:
				showTitleData();
				break;
			}

		};
	};

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_chanel_layout, null);
		this.InitBtn();
		this.setFragmentTitle(menuItem.getName());// 设置头部名称
		this.inflater = inflater;
		context = this.getActivity();
		initUI();
		return view;
	}

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
	 * wanglu 泰得利通 加载头部菜单数据
	 */
	@SuppressWarnings("unchecked")
	private void loadTitleData() {

		if (CacheUtil.get(menuItem.getId()) != null) {// 从缓存中查找子菜单
			titleMenuItems = (List<MenuItem>) CacheUtil.get(menuItem.getId());
			showTitleData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuSevice = new MenuService(context);
				try {
					titleMenuItems = menuSevice.getSubMenuItems(menuItem
							.getId());
					if (titleMenuItems != null) {
						handler.sendEmptyMessage(TITLE_LOAD_SUCCESS);
						CacheUtil.put(menuItem.getId(), titleMenuItems);// 放入缓存
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.obj = e.getMessage();
					msg.what = TITLE_LOAD_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	/**
	 * 显示头部数据 wanglu 泰得利通
	 */
	private void showTitleData() {
		initializSubFragmentsLayout();
		mtitleScrollLayout
				.initMenuItemScreen(context, inflater, titleMenuItems);// 初始化头部空间

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_next_screen:// 下一屏
			mtitleScrollLayout.goNextScreen();
			break;

		}

	}

	@Override
	public void bindContentLayout(Fragment fragment) {
		bindFragment(fragment);
	}

	private void bindFragment(Fragment fragment) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(MANCOTENT_ID, fragment);// 替换内容界面

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void initializSubFragmentsLayout() {

		for (MenuItem menu : titleMenuItems) {
			// if (menuItem.getName().equals("最新公开信息")) {
			menu.setContentFragment((LeaderWindowFragment.class));
			// }
		}

	}

}
