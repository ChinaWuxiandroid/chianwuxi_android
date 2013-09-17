package com.wuxi.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author wanglu 泰得利通 数据库操作
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "chinawuxi.db";
	public static final int VERSION = 2;
	public static final String TABLE_FAVORITE = "favorite_item";
	public static final String TABLE_APPLOG_COUNT="applog_count";

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);

	}

	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_FAVORITE
				+ " (id varchar(32) ,name varchar(32),parentMenuId varchar(32),level varchar(2),level_two_p varchar(2),level_three_p varchar(2))");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_APPLOG_COUNT+ "( use_count varchar(20))");//创建APP使用次数表
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if (oldVersion != newVersion) {

			switch (newVersion) {
			case 2:
				updateToVieson2(db);
				break;
			}

		}

	}

	/**
	 * 
	 * wanglu 泰得利通
	 */
	private void updateToVieson2(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + TABLE_FAVORITE);
		db.execSQL("drop table if exists " + TABLE_APPLOG_COUNT);
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_FAVORITE
				+ " (id varchar(32) ,name varchar(32),parentMenuId varchar(32),level varchar(2),level_two_p varchar(2),level_three_p varchar(2))");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_APPLOG_COUNT+ "( use_count varchar(20))");//创建APP使用次数表
		

	}

}
