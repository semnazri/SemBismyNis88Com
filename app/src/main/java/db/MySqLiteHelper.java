package db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySqLiteHelper extends SQLiteOpenHelper {
	
	protected static final int DataBase_V=5;
	
	protected static final String Database_Name = "Bisnis.comdb";
	


	public MySqLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, Database_Name, null, DataBase_V);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "title TEXT, "+
                "author TEXT )";
		
		db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS books");
		//db.execSQL("delete from " + Database_Name);
        // create fresh books table
        this.onCreate(db);
	}
	
	/*
	 * CRUD MODEL
	 * *?
	 */
	
	private static final String Table_Book = "books";
	
	private static final String book_id ="id", book_title="title",book_author="author";
	
	private static final String[] books_columns = {book_id,book_title,book_title};
	
	
	public void addBook(Book book){
		Log.d("addbook", book.toString());
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put(book_title,book.getTitle());
		value.put(book_author, book.getAuthor());
		
		db.insert(Table_Book, null, value);
		
		db.close();
		
	}
	
	public Book getBook(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(Table_Book, books_columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null);
		
		if(cursor!=null)
			cursor.moveToFirst();
		
		Book book = new Book();
		book.setId(Integer.parseInt(cursor.getString(0)));
		book.setTitle(cursor.getString(1));
		book.setAuthor(cursor.getString(2));
		
		db.close();
		Log.d("Get Book", book.toString());
		return book;	
		
	}
	
	public ArrayList<Book> getAllBooks(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ArrayList<Book> books =  new ArrayList<Book>();
		String query = "select * from "+Table_Book;
		Cursor cursor = db.rawQuery(query, null);
		
		Book book=null;
		if(cursor.moveToFirst()){
			do {
				book= new Book();
				book.setId(Integer.parseInt(cursor.getString(0)));
				book.setTitle(cursor.getString(1));
				book.setAuthor(cursor.getString(2));
				books.add(book);
			} while (cursor.moveToNext());
		}
		Log.d("get All Book", books.toString());
		db.close();
		return books;
		
	}
	
	public int updateBook(Book book){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(book_title,book.getTitle());
		values.put(book_author, book.getAuthor());
		int i = db.update(Table_Book, values, book_id +"= ?",new String[] {String.valueOf(book.getId())});
		db.close();
		Log.d("update Book", book.toString());
		return i;
		
	}
	
	public int deleteBook(Book book){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Table_Book, book_id+"=?",	 new String[] {String.valueOf(book.getId())});
		db.close();
		Log.d("Delete Book",book.toString());
		return 0;
	}
	
	
	

}
