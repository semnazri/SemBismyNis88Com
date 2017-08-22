package db;

import java.util.ArrayList;

import model.Frame_ListBerita;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Table_Setting extends SQLiteOpenHelper  {

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	//	db.execSQL("DROP TABLE IF EXISTS list_berita");
		String CREATE_LISTBERITA_TABLE3 = "CREATE TABLE setting ( " +
        "id TEXT PRIMARY KEY, " + 
        "refresh TEXT, "+
        "font_size TEXT, "+
        "notive TEXT"+
        ")";
		db.execSQL(CREATE_LISTBERITA_TABLE3);
		
		
	}
	public Table_Setting(Context context, String name,
			CursorFactory factory, int version) {
		super(context, MySqLiteHelper.Database_Name, null, (MySqLiteHelper.DataBase_V));
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS setting");
		 
        // create fresh books table
        this.onCreate(db);
	}

//MySqLiteHelper connect = new  MySqLiteHelper(,null, null, null);
	protected static final String table_setting = "setting";
	
	
	
	//protected static final String[] books_columns = {book_id,book_title,book_title};

	public void add_setting(String id,String refresh,String font_size,String notive){
		Log.d("addbook", refresh);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put("id",id);
		value.put("refresh", refresh);
		value.put("font_size", font_size);
		value.put("notive", font_size);
		db.insert(table_setting, null, value);
		db.close();
	}
	/*
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
	*/
	
	public int get_setting_refresh(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select * from "+table_setting;
		Cursor cursor = db.rawQuery(query, null);
		String beritas = null;
		if(cursor.moveToFirst()){
			do {
				beritas=cursor.getString(1);
			} while (cursor.moveToNext());
		}
		Log.d("get All berita","OPK");
		db.close();
		return Integer.parseInt(beritas);
	}
	public int get_setting_font_size(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select * from "+table_setting;
		Cursor cursor = db.rawQuery(query, null);
		String beritas = null;
		if(cursor.moveToFirst()){
			do {
				beritas=cursor.getString(2);
			} while (cursor.moveToNext());
		}
		Log.d("get All berita","OPK");
		db.close();
		return Integer.parseInt(beritas);
	}
	public int get_setting_font_notive(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select * from "+table_setting;
		Cursor cursor = db.rawQuery(query, null);
		String beritas = null;
		if(cursor.moveToFirst()){
			do {
				beritas=cursor.getString(3);
			} while (cursor.moveToNext());
		}
		Log.d("get All berita","OPK");
		db.close();
		return Integer.parseInt(beritas);
	}
	
	public int update_setting_notive(String id,String opt)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put("notive", opt);
		    // updating row
		    return db.update(table_setting, values,"id = ?",new String[] { String.valueOf(id) });
	}
	
	
	public int update_setting_refresh(String id,String opt)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put("refresh", opt);
		    // updating row
		    return db.update(table_setting, values,"id = ?",new String[] { String.valueOf(id) });
	}
	
	public int update_setting_font_size(String id,String opt)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put("font_size", opt);
		    // updating row
		    return db.update(table_setting, values,"id = ?",new String[] { String.valueOf(id) });
	}
	public int deleteBerita(int berita){
		return berita;
		/*
		SQLiteDatabase db = this.getWritableDatabase();
		String sql="delete from list_berita where idberita="+berita;
		db.rawQuery(sql, null);
		db.close();
		Log.d("Delete Berita Id",""+berita);
		return 0;	
		*/
	}
		
}
