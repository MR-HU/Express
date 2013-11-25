package com.zjut.express.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库维护和管理帮助类
 * 
 * @author HuGuojun
 * @date 2013-11-25 下午9:38:06
 * @modify
 * @version 1.0.0
 */
public class DBOpenHelp extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "express.db";
	private static final int DB_VERSIOV = 1;

	public DBOpenHelp(Context context) {
		super(context, DB_NAME, null, DB_VERSIOV);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建history表,_id为主键,date为日期,time为时间,
		//order为快递单号,code为快递公司代码,name为快递公司名字
		db.execSQL("CREATE TABLE IF NOT EXISTS history(_id INTEGER PRIMARY KEY AUTOINCREMENT, _date VARCHAR, _time VARCHAR, _order VARCHAR, _code VARCHAR, _name VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
