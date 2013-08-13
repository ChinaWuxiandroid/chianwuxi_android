package com.wuxi.app.fragment.homepage.publicservice;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通
 * 公共服务内容列表
 *
 */
public class PublicServiceContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		
		
		Content content=(Content) adapterView.getItemAtPosition(position);
		
	
		
		Bundle bundle=new Bundle();
		bundle.putSerializable("content", content);
		if(super.parentItem!=null){
			
			this.baseSlideFragment.slideLinstener.replaceFragment(null, -1, FragmentName.PUBLICSERVICECONTENTDETAILFRAGMENT, bundle);
			
		}else if(super.channel!=null){
			this.baseSlideFragment.slideLinstener.replaceFragment(null, -1, FragmentName.PUBLICSERVICECONTENTDETAILFRAGMENT, bundle);
		}
		
		
	}

}
