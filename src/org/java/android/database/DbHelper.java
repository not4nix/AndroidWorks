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
	protected ArrayList<Comment> findAllComments(){
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cursor = db.query(COMMENT, 
					new String[] {"_id","commentBody","date"},null,null,null,null,"date asc");
			ArrayList<Comment> comments = new ArrayList<Comment>();
			int rows = 0;
			if(cursor != null){
				rows = cursor.getCount();
				cursor.moveToFirst();
				for(int i = 0; i < rows; i++){
					Comment comment = new Comment();
					comment.setId(cursor.getInt(0));
					comment.setCommentBody(cursor.getString(1));
					comment.setDate(cursor.getString(2));
					comments.add(comment);
					cursor.moveToNext();
				}
			}
			return comments;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			db.close();
		}
		return null;
	}
	
	protected long saveComment(Comment comment){
		SQLiteDatabase db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("commentBody", comment.getCommentBody());
			values.put("date", comment.getDate());
			return db.insert(COMMENT, null, values);
		}
		finally {
			db.close();
		}
	}
	
	protected void deleteComment(int id){
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.delete(COMMENT, "_id = " + id, null);
		}
		finally {
			db.close();
		}
	}
	
	protected ArrayList<Author> getAuthors(long commentId){
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor c = db.query(AUTHOR,
					new String[]{"_id","comment_id","name"},"comment_id = " + commentId,null,null,null,"name desc");
			ArrayList<Author> a = new ArrayList<Author>();
			int rows = 0;
			if(c != null){
				rows = c.getCount();
				c.moveToFirst();
				for (int i = 0; i < rows; i++){
					Author author = new Author();
					author.setId(c.getInt(0));
					author.setCommentId(c.getInt(1));
					author.setName(c.getString(2));
					a.add(author);
					c.moveToNext();
				}
			}
			return a;
		}
		finally {
			db.close();
		}
	}
}
