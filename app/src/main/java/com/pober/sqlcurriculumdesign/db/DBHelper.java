package com.pober.sqlcurriculumdesign.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, DBInfo.DB.DB_NAME, null, DBInfo.DB.VERSION);//初始化创建数据库
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DBInfo.Table.REPE_TABLE_CREATE);//库存表
		db.execSQL(DBInfo.Table.IMPORT_TABLE_CREATE);//进货表
		db.execSQL(DBInfo.Table.EXPORT_TABLE_CREATE);//出货表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stubhe
		Log.e("Tag", "delete DB");//delete the old table before onUpgrade the database
		db.execSQL("DROP TABLE IF EXISTS " + DBInfo.Table.REPE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBInfo.Table.EXPORT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBInfo.Table.IMPORT_TABLE_NAME);
		onCreate(db);// 创建新表
	}

}
