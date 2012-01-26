package org.java.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "BuyersGuide";
	private static final int DATABASE_VERSION = 1;
	private static final String COMMENT = "comment";
	private static final String AUTHOR = "author";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + COMMENT + " (_id integer primary key autoincrement," +
				"commentBody text not null, date text not null);");
		db.execSQL("create table " + AUTHOR + " (_id integer primary key autoincrement," +
				"author_id integer not null, name text not null, password text not null, email text not null);");
		db.execSQL("create index author_id_idx on " + AUTHOR + "(author_id)");
		db.execSQL("create index date_idx on " + COMMENT + "(date)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
