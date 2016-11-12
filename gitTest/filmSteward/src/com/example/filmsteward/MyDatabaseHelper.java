package com.example.filmsteward;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	final String CREATE_TABLEString = "create table userMessage(_id integer primary key autoincrement,name text,pwd text)";
	final String CREATE_TABLEString2 = "create table filmPoster(_id integer primary key autoincrement,userNameId integer,filmName text,filmText text)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLEString);
		db.execSQL(CREATE_TABLEString2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
