package com.wuxi.app.fragment;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverInteractPeopleNevigationAdapter;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.MyGoverInterPeopleContentFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment
 * 
 * @author 杨宸 智佳
 * */

public class MainMineFragment extends BaseSlideFragment implements
OnItemClickListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = "402881de3f758726013f75873a3200a6";
	private static final int DETAIL_ID = R.id.gover_interact_people_maincontent_fragment;// 点击左侧导航时右侧要显示内容区域的ID
	protected static final int LEFT_CHANNEL_DATA__LOAD_SUCCESS = 1;// 左侧频道(菜单)加载
	protected static final int LEFT_MENUITEM_DATA__LOAD_SUCCESS = 2;// 左侧频道(菜单)加载
	protected static final int LEFT_DATA__LOAD_ERROR = 0;// 左侧频道(菜单)加载失败

	protected ListView mListView;// 左侧ListView
	private List<MenuItem> listMenus;// 头部菜单选项

	//	private List<Channel> channels;


	private MenuItem parentMenuItem; // 父菜单
	private int defaultCheckPosition=0;

	private GoverInteractPeopleNevigationAdapter adapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			//			case LEFT_CHANNEL_DATA__LOAD_SUCCESS:
			//				showLeftChannelData();
			//				break;
			case LEFT_MENUITEM_DATA__LOAD_SUCCESS:
				showLeftMenuItemData();
				break;

			case LEFT_DATA__LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}
		};
	};

	public void getArgumentsFromOtherFragment(){
		defaultCheckPosition= (Integer) this.getArguments().get("checkPosition");
	}
	
	@Override
	public void initUI() {
		super.initUI();
		mListView = (ListView) view.findViewById(R.id.gover_interact_people_mainmenu_listview);
		
		getArgumentsFromOtherFragment();
		
		if(parentMenuItem==null){
			loadMenuItemData(id);
		}
		else{
			if (parentMenuItem.getType() == MenuItem.CHANNEL_MENU) {
				//				loadChannelData();
			} else if (parentMenuItem.getType() == MenuItem.CUSTOM_MENU) {// 普通菜单
				loadMenuItemData(parentMenuItem.getId());// 加载子菜单

			}
		}
	}

	@SuppressWarnings("unchecked")
	private void loadMenuItemData(final String id) {

		if (null != CacheUtil.get(id)) {
			listMenus = (List<MenuItem>) CacheUtil.get(id);
			showLeftMenuItemData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				MenuService menuService = new MenuService(context);
				Message msg = handler.obtainMessage();
				try {
					listMenus = menuService.getSubMenuItems(id);
					if (listMenus != null) {
						CacheUtil.put(id, listMenus);// 放入缓存
						msg.what = LEFT_MENUITEM_DATA__LOAD_SUCCESS;
						handler.sendMessage(msg);

					}

				} catch (NetException e) {
					e.printStackTrace();

					msg.obj = "网络连接错误稍后重试";
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
					msg.what = LEFT_DATA__LOAD_ERROR;
					handler.sendMessage(msg);
				}
			}
		}

				).start();

	}

	private void showLeftMenuItemData() {

		adapter = new GoverInteractPeopleNevigationAdapter(mInflater, listMenus, managers);
		adapter.setSelectedPosition(defaultCheckPosition);
		mListView.setDividerHeight(0);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setOnItemClickListener(this);

		if (listMenus.size() > 0) {
			showContentFragment(showMenItemContentFragment(listMenus.get(defaultCheckPosition)));// 默认显示第一个ConentFragment
		}
	}

	private BaseFragment showMenItemContentFragment(MenuItem menuItem) {

		MyGoverInterPeopleContentFragment myGoverInterPeopleContentFragment = new MyGoverInterPeopleContentFragment();

		myGoverInterPeopleContentFragment.setMenuItem(menuItem);

		Bundle bundle = new Bundle();
		bundle.putSerializable("BaseSlideFragment", this);
		myGoverInterPeopleContentFragment.setArguments(bundle);// 将对象传递过去
		return myGoverInterPeopleContentFragment;
	};

	private void showContentFragment(BaseFragment fragment) {
		if (fragment != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(DETAIL_ID, fragment);// 替换视图
			fragment.setManagers(managers);// 传递mangers
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

			ft.commit();
		}

	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.main_me_fragment_layout;
	}

	@Override
	protected String getTitleText() {
		if(parentMenuItem==null)
			return "政民互动";
		else
			return parentMenuItem.getName();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		Object object = parent.getItemAtPosition(position);

		adapter.setSelectedPosition(position); // 刷新左侧导航listView背景
		adapter.notifyDataSetInvalidated();
		showContentFragment(showMenItemContentFragment((MenuItem) object));
	}
}
