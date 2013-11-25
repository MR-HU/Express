package com.zjut.express.db;

import java.util.ArrayList;
import java.util.List;

import com.zjut.express.entity.History;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 对数据库基本操作的封装
 * 
 * @author HuGuojun
 * @date 2013-11-25 下午9:47:46
 * @modify
 * @version 1.0.0
 */
public class DBManager {
	
	private DBOpenHelp helper;
	private SQLiteDatabase db;
	
	public DBManager(Context context) {
		helper = new DBOpenHelp(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * 添加一条记录
	 * @param record 
	 * @return void
	 */
	public void add(History record) {
		db.execSQL("INSERT INTO history VALUES(null, ?, ?, ?, ?, ?)",
				new Object[]{record.getDate(), record.getTime(), record.getOrder(), record.getCode(), record.getName()});
	}
	
	/**
	 * 添加一组记录
	 * @param records 
	 * @return void
	 */
	public void add(List<History> records) {
		db.beginTransaction();
        try {  
            for (History record : records) {
        		db.execSQL("INSERT INTO history VALUES(null, ?, ?, ?, ?, ?)",
        				new Object[]{record.getDate(), record.getTime(), record.getOrder(), record.getCode(), record.getName()});
        	}  
            db.setTransactionSuccessful(); 
        } catch (Exception e) {
        	e.printStackTrace();
		} finally {  
            db.endTransaction();  
        }  
	}
	
	/**
	 * 更新一条记录的时间和日期
	 * @param record 
	 * @return void
	 */
	public void update(History record) {
		ContentValues values = new ContentValues();
		values.put("_date", record.getDate());
		values.put("_time", record.getTime());
		db.update("history", values, "_order = ?", new String[]{record.getOrder()});
	}
	
	/**
	 * 删除一条记录
	 * @param record 
	 * @return void
	 */
	public void delete(History record) {
		db.delete("history", "_order = ?", new String[]{record.getOrder()});
	}
	
	/**
	 * 查询指定单号的一条记录
	 * @param order
	 * @return History
	 */
	public History query(String order) {
		History record =null;
		Cursor cursor = db.rawQuery("SELECT * FROM history WHERE _order = ?", new String[]{order});
		while (cursor.moveToNext()) {
			record = new History();
			record.setDate(cursor.getString(cursor.getColumnIndex("_date")));
			record.setTime(cursor.getString(cursor.getColumnIndex("_time")));
			record.setOrder(cursor.getString(cursor.getColumnIndex("_order")));
			record.setCode(cursor.getString(cursor.getColumnIndex("_code")));
			record.setName(cursor.getString(cursor.getColumnIndex("_name")));
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return record;
	}
	
	/**
	 * 查询所有记录
	 * @return List<History>
	 */
	public List<History> query() {
		List<History> list = new ArrayList<History>();
		Cursor cursor = db.rawQuery("SELECT * FROM history", null);
		while (cursor.moveToNext()) {
			History record = new History();
			record.setDate(cursor.getString(cursor.getColumnIndex("_date")));
			record.setTime(cursor.getString(cursor.getColumnIndex("_time")));
			record.setOrder(cursor.getString(cursor.getColumnIndex("_order")));
			record.setCode(cursor.getString(cursor.getColumnIndex("_code")));
			record.setName(cursor.getString(cursor.getColumnIndex("_name")));
			list.add(record);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 关闭数据库
	 * @return void
	 */
	public void close() {  
        db.close();  
    } 
}
