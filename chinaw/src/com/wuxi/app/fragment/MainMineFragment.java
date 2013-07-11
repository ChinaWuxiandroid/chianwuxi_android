package com.wuxi.app.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverInteractPeopleNevigationAdapter;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.GoverInterPeopleMineFragment;
import com.wuxi.app.fragment.search.AdvancedSearchFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.MenuItem;


/**
 * 我的政民互动  主Fragment
 * @author 杨宸 智佳
 * */

public class MainMineFragment extends HomeBaseSlideLevelFragment {
	private ListView mainMenu_listView;     //政民互动  主菜单

	private MenuItem menuItem;
	private List<MenuItem> listMenus;// 头部菜单选项\
	GoverInteractPeopleNevigationAdapter adapter;
	private String id= "402881de3f758726013f75873a3200a6";

	private int FRAME_CONTENT=R.id.gover_interact_people_maincontent_fragment;

	@Override
	protected void initUI() {
		super.initUI();
		loadListData(id);
		findView();
	}

	public void findView(){
		mainMenu_listView=(ListView)view.findViewById(R.id.gover_interact_people_mainmenu_listview);
		 adapter=new GoverInteractPeopleNevigationAdapter(inflater,listMenus,managers);
		mainMenu_listView.setDividerHeight(0);
		mainMenu_listView.setSelection(0);
		mainMenu_listView.setAdapter(adapter);
		mainMenu_listView.setOnItemClickListener(new listViewOnItemClickListener());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}


	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.slide_goverinteractpeople_layout;
	}

	@Override
	protected void onBack() {
		managers.BackPress(this);
	}

	@Override
	protected String getTtitle() {
		// TODO Auto-generated method stub
		return "政民互动";
	}


	/*
	 * 暂时未知怎样获取 期布局MenuItem  先根据其Id固定获取  id= "402881de3f758726013f75873a3200a6"
	 * */

	public void loadListData(String id){
		if (CacheUtil.get(id) != null) {// 从缓存中查找获取子菜单

			listMenus = (List<MenuItem>) CacheUtil.get(id);
			//			showTitleData();
			return;
		}
		//		new Thread(new Runnable() {
		//
		//			@Override
		//			public void run() {
		//
		//				MenuSevice menuSevice = new MenuSevice(context);
		//				try {
		//					titleMenus= menuSevice.getSubMenuItems(menuItem.getId());
		//
		//					if (null != titleMenus) {
		//						CacheUtil.put(menuItem.getId(), titleMenus);// 缓存起来
		//						handler.sendEmptyMessage(TITLE__LOAD_SUCESS);
		//
		//					} else {
		//						Message message = handler.obtainMessage();
		//						message.obj = "error";
		//						handler.sendEmptyMessage(TITLE_LOAD_ERROR);
		//					}
		//
		//				} catch (NetException e) {
		//
		//					LogUtil.i(TAG, "出错");
		//					e.printStackTrace();
		//					Message message = handler.obtainMessage();
		//					message.obj = e.getMessage();
		//
		//					handler.sendEmptyMessage(TITLE_LOAD_ERROR);
		//
		//				} catch (JSONException e) {
		//					LogUtil.i(TAG, "json error");
		//					e.printStackTrace();
		//					Message message = handler.obtainMessage();
		//					message.obj = e.getMessage();
		//				} catch (NODataException e) {
		//					LogUtil.i(TAG, "no data");
		//					e.printStackTrace();
		//					Message message = handler.obtainMessage();
		//					message.obj = e.getMessage();
		//				}
		//			}
		//		}
		//
		//				).start();
	}

	public class listViewOnItemClickListener  implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			adapter.setSelectedPosition(position);
			adapter.notifyDataSetInvalidated();
			GoverInterPeopleMineFragment g=new GoverInterPeopleMineFragment();
			onTransaction(g);
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

}
