package com.wuxi.app.activity.homepage.more;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.wuxi.app.db.FavoriteItemDao;
import com.wuxi.app.engine.FavoritesService;
import com.wuxi.app.engine.MenuService;
import com.wuxi.app.listeners.OnChangedListener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.MenuItemChannelIndexUtil;
import com.wuxi.app.view.SlipButton;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 首页常用栏设置
 * 
 */
public class MenuItemSetActivity extends BaseSlideActivity {
	protected static final int LOAD_ERROR = 1;
	protected static final int LOAD_SUCCESS = 0;
	private static final int MENUITEM_LEVEL_ONE = 1;// 一级菜单
	private static final int MENUITEM_LEVEL_TWO = 2;// 二级菜单
	private static final int MENUITEM_LEVEL_THREE = 3;// 三级菜单
	private List<MenuItem> favaItems;
	private ListView lv_fava;
	private ProgressBar pb_fava;

	protected static final int SET_SUCCESS = 2;
	protected static final int SET_FAIL = 3;
	private FavoriteItemDao favoriteItemDao;

	private ProgressDialog pd;
	private MenuItem checkMenuItem;
	private List<MenuItem> homeMenuItems;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";
			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {

			case LOAD_SUCCESS:
				showFavaItems();
				break;
			case SET_SUCCESS:
				pd.dismiss();
				saveFaveItem();// 保存收藏项
				break;
			case SET_FAIL:
				pd.dismiss();
				Toast.makeText(MenuItemSetActivity.this, tip + "，收藏失败",
						Toast.LENGTH_SHORT).show();
				break;
			case LOAD_ERROR:

				Toast.makeText(MenuItemSetActivity.this, tip,
						Toast.LENGTH_SHORT).show();
				break;

			}
		};

	};

	@SuppressWarnings("unchecked")
	@Override
	protected void findMainContentViews(View view) {

		lv_fava = (ListView) view.findViewById(R.id.lv_fava);
		pb_fava = (ProgressBar) view.findViewById(R.id.pb_fava);
		loadFavaItems();

		favoriteItemDao = new FavoriteItemDao(this);
		pd = new ProgressDialog(this);
		pd.setMessage("正在设置...");
		
		homeMenuItems=(List<MenuItem>) CacheUtil.get(Constants.CacheKey.HOME_MENUITEM_KEY);
	}

	/**
	 * 保存收藏项 wanglu 泰得利通
	 */
	protected void saveFaveItem() {

		favoriteItemDao.addFavoriteItem(checkMenuItem);// 保存收藏列表到数据库
		checkMenuItem.setLocalFavorites(true);
		homeMenuItems.add(checkMenuItem);
		Toast.makeText(this, "收藏" + checkMenuItem.getName() + "成功!",
				Toast.LENGTH_SHORT).show();

	}

	static class ViewHolder {

		public TextView title_text;
		public SlipButton slipButton;
	}

	private class MenuSetAdapter extends BaseAdapter implements
			OnChangedListener {

		public MenuSetAdapter() {

		}

		@Override
		public int getCount() {
			return favaItems.size();
		}

		@Override
		public Object getItem(int position) {
			return favaItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			String name = "";
			MenuItem menuItem = (MenuItem) favaItems.get(position);

			name = menuItem.getName();

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

			if (menuItem.isLocalFavorites()) {
				viewHolder.slipButton.setChecked(true);
			} else {
				viewHolder.slipButton.setChecked(false);
			}

			viewHolder.slipButton.SetOnChangedListener(menuItem, this);
			return convertView;

		}

		@Override
		public void OnChanged(Object o, boolean checkState) {

			checkMenuItem = (MenuItem) o;

			if (checkState) {// 保存手册列表

				int level = getMenuItemLevel(checkMenuItem);// 获取菜单的级别

				switch (level) {
				case MENUITEM_LEVEL_ONE:// 一级菜单
					checkMenuItem.setLevel(1);
					saveFaveItem();// 保存收藏项
					break;
				case MENUITEM_LEVEL_TWO:// 二级菜单
					checkMenuItem.setLevel(2);
					if (CacheUtil.get(checkMenuItem.getId()) == null
							&& checkMenuItem.getType() == MenuItem.CUSTOM_MENU) {// 看缓存中有没有三级子菜单,没有就加载三级子菜单

						laodMenuItems(checkMenuItem.getId());// 加载二级菜单的子菜单

					} else {

						saveFaveItem();
					}

				case MENUITEM_LEVEL_THREE:// 三级菜单
					checkMenuItem.setLevel(3);
					MenuItem paretMenuItem = (MenuItem) CacheUtil
							.get(MenuItem.MENUITEM_KEY
									+ checkMenuItem.getParentMenuId());// 拿到父菜单，二级菜单

					if (CacheUtil.get(paretMenuItem.getId()) == null
							&& paretMenuItem.getType() == MenuItem.CUSTOM_MENU) {// 看缓存中有没有三级子菜单,没有就加载三级子菜单
						laodMenuItems(paretMenuItem.getId());
					} else {
						saveFaveItem();// 保存收藏项
					}

					break;

				}

			} else {
				checkMenuItem.setLocalFavorites(false);

				favoriteItemDao.delFavoriteItem(checkMenuItem.getId());
				homeMenuItems.remove(checkMenuItem);
			}
			
		

			
			/*  Toast.makeText(MenuItemSetActivity.this, " 该功能在施工中",
			  Toast.LENGTH_SHORT).show();*/
			 
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

	/**
	 * 
	 * wanglu 泰得利通 加载下面的菜单
	 * 
	 * @param id
	 */
	public void laodMenuItems(final String parentId) {
		pd.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				MenuService menuService = new MenuService(
						MenuItemSetActivity.this);
				Message msg = handler.obtainMessage();
				try {
					List<MenuItem> items = menuService
							.getSubMenuItems(parentId);
					if (items != null) {
						msg.what = SET_SUCCESS;

					} else {
						msg.what = SET_FAIL;
						msg.obj = "设置失败";
					}

					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = SET_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = SET_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = SET_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showFavaItems() {
		pb_fava.setVisibility(ProgressBar.GONE);
		lv_fava.setAdapter(new MenuSetAdapter());

	}

	/**
	 * 
	 * wanglu 泰得利通 获取菜单的级别
	 * 
	 * @param menuItem
	 * @return
	 */
	private int getMenuItemLevel(MenuItem menuItem) {

		String parentId = menuItem.getParentMenuId();
		if (parentId == null) {

			return MENUITEM_LEVEL_ONE;
		} else {

			if (MenuItemChannelIndexUtil.getInstance().containsKey(
					Constants.CacheKey.MAIN_MENUITEM_KEY, parentId)) {

				return MENUITEM_LEVEL_TWO;
			} else {

				return MENUITEM_LEVEL_THREE;
			}

		}

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
