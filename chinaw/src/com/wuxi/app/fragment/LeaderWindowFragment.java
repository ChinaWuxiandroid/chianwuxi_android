package com.wuxi.app.fragment;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.MenuSevice;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 领导之窗fragment
 * 
 */
public class LeaderWindowFragment extends BaseFragment {
	protected static final int HEAD_LOAD_FAIL = 0;
	protected static final int HEAD_LOAD_SUCCESS = 1;
	private View view;
	private MenuItem parentItem;
	private List<MenuItem> headItems;
	private RadioGroup rg_head;
	private Context context;
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch(msg.what){
			case HEAD_LOAD_FAIL:
				String tip="";
				if(msg.obj!=null){
					tip=msg.obj.toString();
					Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				}
				break;
			case HEAD_LOAD_SUCCESS:
				showHeadData();
				break;
			}
			
		};
	};

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.leader_window_layout, null);
		this.context = getActivity();
		initUI();
		return view;
	}

	private void initUI() {
		rg_head = (RadioGroup) view.findViewById(R.id.content_head_rg);
		loadHeadItemData();

	}

	/**
	 * 
	 * wanglu 泰得利通 加载导航数据
	 */
	@SuppressWarnings("unchecked")
	private void loadHeadItemData() {

		if (CacheUtil.get(parentItem.getId()) != null) {// 从缓存中查找
			headItems = (List<MenuItem>) CacheUtil.get(parentItem.getId());
			showHeadData();
			return;
		}

		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				MenuSevice menuSevice=new MenuSevice(context);
				try {
					headItems=menuSevice.getSubMenuItems(parentItem.getId());
					if(headItems!=null){
						handler.sendEmptyMessage(HEAD_LOAD_SUCCESS);
						CacheUtil.put(parentItem.getId(), headItems);//放入缓存
					}
					
				} catch (NetException e) {
					e.printStackTrace();
					Message msg=handler.obtainMessage();
					msg.obj=e.getMessage();
					msg.what=HEAD_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					Message msg=handler.obtainMessage();
					msg.obj=e.getMessage();
					msg.what=HEAD_LOAD_FAIL;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg=handler.obtainMessage();
					msg.obj=e.getMessage();
					msg.what=HEAD_LOAD_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * 
	 * 
	 * wanglu 泰得利通 <RadioButton android:layout_width="wrap_content"
	 * android:layout_height="wrap_content" android:padding="6dip"
	 * android:layout_gravity="center_horizontal"
	 * android:layout_marginLeft="5dip"
	 * android:background="@drawable/content_header_item_selector"
	 * android:button="@null" android:checked="true" android:text="市委领导"
	 * android:textSize="12sp" />
	 */

	private void showHeadData() {

		if (headItems == null)
			return;

		for (MenuItem meItem : headItems) {

			RadioButton rb = new RadioButton(context);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					30);
			layoutParams.leftMargin=5;
			layoutParams.gravity=Gravity.CENTER_HORIZONTAL;
			rb.setPadding(6, 0, 6, 0);
			rb.setText(meItem.getName());
			rb.setTextSize(12);
			rb.setBackgroundResource(R.drawable.content_header_item_selector);
			rb.setLayoutParams(layoutParams);
			rb.setButtonDrawable(android.R.color.transparent);
			rg_head.addView(rb);
		}

	}

}
