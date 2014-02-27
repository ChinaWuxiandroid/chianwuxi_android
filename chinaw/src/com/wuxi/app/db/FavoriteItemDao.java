package com.wuxi.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 收藏
 * 
 */
public class FavoriteItemDao {

	private DataBaseHelper dbHelper;

	public FavoriteItemDao(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 查找是否存在收藏项目
	 * 
	 * @param id
	 * @return
	 */
	public boolean findFavoriteItem(String id) {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor curosor = (Cursor) db.rawQuery("select id from "
					+ DataBaseHelper.TABLE_FAVORITE + " where id=?",
					new String[] { id });
			if (curosor.moveToNext()) {
				curosor.close();
				db.close();
				return true;
			}

			curosor.close();
			db.close();
		}

		return false;

	}

	/**
	 * 
	 * wanglu 泰得利通 添加收藏项目
	 * 
	 * @param id
	 */
	public void addFavoriteItem(MenuItem menItem) {
		if (findFavoriteItem(menItem.getId())) {
			return;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (db.isOpen()) {
			db.execSQL(
					"insert into "
							+ DataBaseHelper.TABLE_FAVORITE
							+ " (id,name,parentMenuId,level,level_two_p,level_three_p ) values(?,?,?,?,?,?)",
					new Object[] { menItem.getId(), menItem.getName(),
							menItem.getParentMenuId(), menItem.getLevel(),
							menItem.getLevel_two_p(),
							menItem.getLevel_three_p() });
			db.close();
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 删除收藏项
	 * 
	 * @param id
	 */
	public void delFavoriteItem(String id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (db.isOpen()) {
			db.execSQL("delete from " + DataBaseHelper.TABLE_FAVORITE
					+ " where id=?", new Object[] { id });
			db.close();
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 修改menuitem
	 * 
	 * @param menuItem
	 */
	public void updateFavoriteItem(MenuItem menuItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (db.isOpen()) {
			db.execSQL(
					"update " + DataBaseHelper.TABLE_FAVORITE
							+ " set  name=?, parentMenuId=? where id=?",
					new Object[] { menuItem.getName(),
							menuItem.getParentMenuId(), menuItem.getId() });
			db.close();
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 获取所有收藏列表
	 * 
	 * @return
	 */
	public List<MenuItem> getFavoriteItems() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		if (db.isOpen()) {

			Cursor cursor = db.rawQuery("select * from "
					+ DataBaseHelper.TABLE_FAVORITE, null);
			while (cursor.moveToNext()) {

				MenuItem menuItem = new MenuItem();
				menuItem.setId(cursor.getString(0));
				menuItem.setName(cursor.getString(1));
				menuItem.setParentMenuId(cursor.getString(2));
				menuItem.setLevel(cursor.getInt(3));// 菜单的层次
				menuItem.setLevel_two_p(cursor.getInt(4));
				menuItem.setLevel_three_p((cursor.getInt(5)));
				menuItem.setLocalFavorites(true);
				menuItems.add(menuItem);
			}

			cursor.close();

			db.close();
		}

		return menuItems;

	}

	public List<MenuItem> getFavoriteItems(List<MenuItem> mainMenuItems) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		List<MenuItem> homeItems = new ArrayList<MenuItem>();

		for (MenuItem mainMItem : mainMenuItems) {
			homeItems.add(mainMItem);
		}

		if (db.isOpen()) {

			Cursor cursor = db.rawQuery("select * from "
					+ DataBaseHelper.TABLE_FAVORITE, null);
			while (cursor.moveToNext()) {

				MenuItem menuItem = new MenuItem();
				menuItem.setId(cursor.getString(0));
				menuItem.setName(cursor.getString(1));
				menuItem.setParentMenuId(cursor.getString(2));
				menuItem.setLevel(cursor.getInt(3));// 菜单的层次
				menuItem.setLevel_two_p(cursor.getInt(4));
				menuItem.setLevel_three_p(cursor.getInt(5));
				menuItem.setLocalFavorites(true);

				homeItems.add(menuItem);
			}

			cursor.close();

			db.close();
		}

		return homeItems;

	}

}
