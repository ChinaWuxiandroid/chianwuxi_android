package com.wuxi.app.db;

import java.util.ArrayList;
import java.util.List;

import com.wuxi.domain.MenuItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
					"insert into " + DataBaseHelper.TABLE_FAVORITE
							+ " (id,name,parentMenuId) values(?,?,?)",
					new Object[] { menItem.getId(), menItem.getName(),
							menItem.getParentMenuId() });
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

				menuItems.add(menuItem);
			}

			cursor.close();

			db.close();
		}

		return menuItems;

	}

}
