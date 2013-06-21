package com.wuxi.app.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wuxi.app.util.LeftMenuData;

/**
 *左侧菜单
 * @author wanglu
 *
 */
public class TitleFragment extends ListFragment {
	//正在显示的选项位置
		int showposition=-1;
		//点击的位置
		int checkposition=0;
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1,LeftMenuData.TITLES);
			
			setListAdapter(adapter);
			
			//如果程序被系统回收后再度启用，则会从onSaveInstanceState中读取保存的信息
			if(savedInstanceState!=null){
				showposition=savedInstanceState.getInt("show",-1);
				checkposition=savedInstanceState.getInt("check",0);
			}
			
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			showData(checkposition);
		}
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			
			//fragment被系统回收时，将信息保存下来
			outState.putInt("show",showposition);
			outState.putInt("check", checkposition);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showData(position);
		}

		private void showData(int position) {
			checkposition=position;
			getListView().setItemChecked(checkposition, true);
			
			if(checkposition!=showposition){
				DataFragment df=DataFragment.Instance(checkposition);
				
				FragmentTransaction ft=getFragmentManager().beginTransaction();
				ft.replace(com.wuxi.app.R.id.details, df);
				
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
				
				showposition=checkposition;
			}
		}

}
