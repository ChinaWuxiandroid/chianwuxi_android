package com.wuxi.app.fragment;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverInteractPeopleNevigationAdapter;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeople12345Fragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleHotReviewFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleMineFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleOpenTelFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeoplePetitionReceptFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeoplePublicSuperviseFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleSuggestFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.MenuItem;

/**
 * 我的政民互动 主Fragment
 * 
 * @author 杨宸 智佳
 * */

public class MainMineFragment extends BaseSlideFragment {
	private ListView mainMenu_listView; // 政民互动 主菜单

	private MenuItem menuItem;
	private List<MenuItem> listMenus;// 头部菜单选项
	GoverInteractPeopleNevigationAdapter adapter;
	private String id = "402881de3f758726013f75873a3200a6";

	private int FRAME_CONTENT = R.id.gover_interact_people_maincontent_fragment;

	@Override
	public  void initUI() {
		super.initUI();
		loadListData(id);
		if (listMenus != null) {
			findView();
			initView();
		} else {
			Toast.makeText(context, "我的互动菜单列表加载失败！", 2000).show();
			// onBack();
		}

	}

	public void findView() {
		mainMenu_listView = (ListView) view
				.findViewById(R.id.gover_interact_people_mainmenu_listview);
		adapter = new GoverInteractPeopleNevigationAdapter(mInflater, listMenus,
				managers);
		mainMenu_listView.setDividerHeight(0);
		mainMenu_listView.setSelection(0);
		mainMenu_listView.setAdapter(adapter);
		mainMenu_listView
				.setOnItemClickListener(new listViewOnItemClickListener());
	}

	

	@Override
	protected int getLayoutId() {
		return R.layout.main_me_fragment_layout;
	}

	
	
	/*
	 * 暂时未知怎样获取 期布局MenuItem 先根据其Id固定获取 id= "402881de3f758726013f75873a3200a6"
	 */

	public void loadListData(String id) {
		if (CacheUtil.get(id) != null) {// 从缓存中查找获取子菜单

			listMenus = (List<MenuItem>) CacheUtil.get(id);
			// showTitleData();
			return;
		}
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// MenuSevice menuSevice = new MenuSevice(context);
		// try {
		// titleMenus= menuSevice.getSubMenuItems(menuItem.getId());
		//
		// if (null != titleMenus) {
		// CacheUtil.put(menuItem.getId(), titleMenus);// 缓存起来
		// handler.sendEmptyMessage(TITLE__LOAD_SUCESS);
		//
		// } else {
		// Message message = handler.obtainMessage();
		// message.obj = "error";
		// handler.sendEmptyMessage(TITLE_LOAD_ERROR);
		// }
		//
		// } catch (NetException e) {
		//
		// LogUtil.i(TAG, "出错");
		// e.printStackTrace();
		// Message message = handler.obtainMessage();
		// message.obj = e.getMessage();
		//
		// handler.sendEmptyMessage(TITLE_LOAD_ERROR);
		//
		// } catch (JSONException e) {
		// LogUtil.i(TAG, "json error");
		// e.printStackTrace();
		// Message message = handler.obtainMessage();
		// message.obj = e.getMessage();
		// } catch (NODataException e) {
		// LogUtil.i(TAG, "no data");
		// e.printStackTrace();
		// Message message = handler.obtainMessage();
		// message.obj = e.getMessage();
		// }
		// }
		// }
		//
		// ).start();
	}

	public void initView() {
		GoverInterPeopleMineFragment g = new GoverInterPeopleMineFragment();
		onTransaction(g);
	}

	public class listViewOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			adapter.setSelectedPosition(position); // 刷新左侧导航listView背景
			adapter.notifyDataSetInvalidated();

			switch (position) {
			case 0:
				initView();
				break;
			case 1:
				MainMineFragment g1 = new GoverInterPeople12345Fragment();
				onTransaction(g1);
				break;
			case 2:
				MainMineFragment g2 = new GoverInterPeopleSuggestFragment();
				onTransaction(g2);
				break;
			case 3:
				GoverInterPeople12345Fragment g3 = new GoverInterPeople12345Fragment();
				onTransaction(g3);
				break;
			case 4:
				GoverInterPeople12345Fragment g4 = new GoverInterPeople12345Fragment();
				onTransaction(g4);
				break;
			case 5:
				GoverInterPeople12345Fragment g5 = new GoverInterPeople12345Fragment();
				onTransaction(g5);
				break;
			case 6:
				MainMineFragment g6 = new GoverInterPeopleHotReviewFragment();
				onTransaction(g6);
				break;
			case 7:
				MainMineFragment g7 = new GoverInterPeopleOpenTelFragment();
				onTransaction(g7);
				break;
			case 8:
				MainMineFragment g8 = new GoverInterPeoplePublicSuperviseFragment();
				onTransaction(g8);
				break;
			case 9:
				MainMineFragment g9 = new GoverInterPeoplePetitionReceptFragment();
				onTransaction(g9);
				break;
			}
		}

	}

	private void onTransaction(MainMineFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAME_CONTENT, fragment);
		ft.commit();

	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.menuItem = parentMenuItem;
	}

	@Override
	protected String getTitleText() {
		return "政民互动";
	}

	

}
