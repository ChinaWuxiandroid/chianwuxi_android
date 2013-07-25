package com.wuxi.app.fragment.homepage.informationcenter;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.ContentListFragment;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 内容列表菜单
 * 
 */
@SuppressLint("HandlerLeak")
public class InforContentListFragment extends ContentListFragment {

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		Content content = (Content) adapterView.getItemAtPosition(position);
		if (super.parentItem != null) {
			if (parentItem.getName().equals("热点专题")) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("content", content);

				BaseSlideFragment baseSlideFragment = (BaseSlideFragment) getArguments()
						.get(MenuItemMainFragment.ROOTFRAGMENT_KEY);
				
				baseSlideFragment.slideLinstener.replaceFragment(null, -1, FragmentName.HOTTOPICCONTENTFRAGMENT, bundle);//跳转

			}

		}

	}
}
