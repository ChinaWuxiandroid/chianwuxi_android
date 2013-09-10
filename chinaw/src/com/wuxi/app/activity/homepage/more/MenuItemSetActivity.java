package com.wuxi.app.activity.homepage.more;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.engine.FavoritesService;
import com.wuxi.app.listeners.OnChangedListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.view.SlipButton;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 首页常用栏设置
 * 
 */
public class MenuItemSetActivity extends BaseSlideActivity {
	protected static final int LOAD_ERROR = 1;
	protected static final int LOAD_SUCCESS = 0;

	private List<MenuItem> favaItems;
	private ListView lv_fava;
	private ProgressBar pb_fava;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_SUCCESS:
				showFavaItems();
				break;
			case LOAD_ERROR:
				String tip = msg.obj.toString();
				Toast.makeText(MenuItemSetActivity.this, tip, Toast.LENGTH_SHORT).show();
				break;

			}
		};

	};
	
	
	

	@Override
	protected void findMainContentViews(View view) {
		
		lv_fava = (ListView) view.findViewById(R.id.lv_fava);
		pb_fava = (ProgressBar) view.findViewById(R.id.pb_fava);
		loadFavaItems();
	}

	

	static class ViewHolder {

		public TextView title_text;
		public SlipButton slipButton;
	}

	private class MenuSetAdapter extends BaseAdapter implements
			OnChangedListener {

		@SuppressWarnings("rawtypes")
		private List items;

		public MenuSetAdapter(@SuppressWarnings("rawtypes") List items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			String name = "";
			MenuItem menuItem  = (MenuItem) items.get(position);
			
			name=menuItem.getName();

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(MenuItemSetActivity.this,
						R.layout.menuset_item_layout, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.menuset_tv_name);
				SlipButton slipButton = (SlipButton) convertView
						.findViewById(R.id.slipButton);

				viewHolder.slipButton = slipButton;

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(name);
			if(menuItem.getParentMenuId()==null){
				viewHolder.slipButton.setChecked(true);
			}else{
				viewHolder.slipButton.setChecked(false);
			}
			
			viewHolder.slipButton.SetOnChangedListener("", this);
			return convertView;

		}

		@Override
		public void OnChanged(String strName, boolean CheckState) {
			Toast.makeText(MenuItemSetActivity.this, "改功能在施工中", Toast.LENGTH_SHORT).show();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadFavaItems() {

		if (CacheUtil.get(Constants.CacheKey.FAVAITEMS_KEY) != null) {
			favaItems = (List<MenuItem>) CacheUtil
					.get(Constants.CacheKey.FAVAITEMS_KEY);
			showFavaItems();// 显示收藏列表
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				FavoritesService favoritesService = new FavoritesService(
					MenuItemSetActivity.this);

				try {
					favaItems = favoritesService.getFavorites();
					if (favaItems != null) {
						msg.what = LOAD_SUCCESS;
						CacheUtil.put(Constants.CacheKey.FAVAITEMS_KEY,
								favaItems);

					} else {
						msg.what = LOAD_ERROR;
						msg.obj = "没有加载到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {

					e.printStackTrace();
					msg.what = LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
					msg.what = LOAD_ERROR;
					msg.obj = "数据格式有误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showFavaItems() {
		pb_fava.setVisibility(ProgressBar.GONE);
		lv_fava.setAdapter(new MenuSetAdapter(favaItems));

	}

	@Override
	protected int getLayoutId() {

		return R.layout.index_menuitem_set_layout;
	}

	@Override
	protected String getTitleText() {

		return "首页常用栏设置";
	}

}
