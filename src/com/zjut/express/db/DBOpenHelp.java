package com.zjut.express.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ�ά���͹��������
 * 
 * @author HuGuojun
 * @date 2013-11-25 ����9:38:06
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
		//����history��,_idΪ����,dateΪ����,timeΪʱ��,
		//orderΪ��ݵ���,codeΪ��ݹ�˾����,nameΪ��ݹ�˾����
		db.execSQL("CREATE TABLE IF NOT EXISTS history(_id INTEGER PRIMARY KEY AUTOINCREMENT, _date VARCHAR, _time VARCHAR, _order VARCHAR, _code VARCHAR, _name VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
