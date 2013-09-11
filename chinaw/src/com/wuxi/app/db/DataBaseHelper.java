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
	private static final int VERSION = 1;
	public static final String TABLE_FAVORITE="favorite_item";

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_FAVORITE+" (id varchar(32) , name varchar(32),parentMenuId varchar(32))");   //创建收藏表
   }

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
