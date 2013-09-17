package com.wuxi.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author wanglu 泰得利通 APP使用次数日志数据库处理
 * 
 */
public class AppUseCountDao {

	private DataBaseHelper dbHelper;

	public AppUseCountDao(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取APP使用次数
	 * 
	 * @return
	 */
	public int getUseAppCount() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor curosor = (Cursor) db.rawQuery("select * from "
					+ DataBaseHelper.TABLE_APPLOG_COUNT, null);
			if (curosor.moveToNext()) {

				int count = curosor.getInt(0);
				curosor.close();
				db.close();

				return count;

			}

			curosor.close();
			db.close();
			return 0;

		}
		return 0;

	}

	public void updateUseCount() {

		int count = getUseAppCount() + 1;

		String sql = "";
		if (count == 1) {
			sql = "insert into " + DataBaseHelper.TABLE_APPLOG_COUNT
					+ " values(?)";

		} else {
			sql = "update " + DataBaseHelper.TABLE_APPLOG_COUNT
					+ " set  use_count=?";
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {

			db.execSQL(sql, new Object[] { count });
			db.close();

		}

	}

}
