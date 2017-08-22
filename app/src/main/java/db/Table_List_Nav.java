package db;

import java.util.ArrayList;

import model.Frame_nav;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class Table_List_Nav extends MySqLiteHelper  {

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	//	db.execSQL("DROP TABLE IF EXISTS list_berita");
		String CREATE_LISTBERITA_TABLE = "CREATE TABLE IF NOT EXISTS list_nav ( " +
	              "id_nav TEXT PRIMARY KEY, " + 
	              "nav_title TEXT, "+
	              "icon integer," +
	              "position integer," +
	              "status integer"+
	              ")";
		//db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_LISTBERITA_TABLE);
		
		String CREATE_LISTBERITA_TABLE2 = "CREATE TABLE IF NOT EXISTS list_berita ( " +
	              "idberita TEXT PRIMARY KEY, " + 
	              "imgberita TEXT, "+
	              "tglberita TEXT," +
	              "judulberita TEXT,"+
	              "pukul TEXT,"+
	              "category TEXT,"+
	              "datepost TEXT,"+
	              "image_content TEXT,"+
	              "parent_category TEXT,"+
	              "slug TEXT,"+
	              "live TEXT"+
	              ")";
		//db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_LISTBERITA_TABLE2);
		
		String CREATE_LISTBERITA_TABLE3 = "CREATE TABLE IF NOT EXISTS setting ( " +
	              "id TEXT PRIMARY KEY, " + 
	              "refresh TEXT, "+
	              "font_size TEXT, "+
	              "notive TEXT"+
	              ")";
		//db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_LISTBERITA_TABLE3);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//db.execSQL("DROP TABLE IF EXISTS list_nav");
		//db.execSQL("DROP TABLE IF EXISTS list_berita");
		db.execSQL("DROP TABLE IF EXISTS setting");
		db.execSQL("DROP TABLE IF EXISTS list_nav");
        // create fresh books table
        this.onCreate(db);
	}
	
	public Table_List_Nav(Context context, String name,
			CursorFactory factory, int version) {
		super(context, MySqLiteHelper.Database_Name, null, MySqLiteHelper.DataBase_V);
		// TODO Auto-generated constructor stub
	}

//MySqLiteHelper connect = new  MySqLiteHelper(,null, null, null);
	protected static final String table_list_nav = "list_nav";
	
	
	
	//protected static final String[] books_columns = {book_id,book_title,book_title};

	public void add_nav(Frame_nav nav){
		Log.d("addnav", nav.toString());
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put("id_nav",nav.id);
		value.put("nav_title", nav.nav);
		value.put("icon", nav.icon);
		value.put("position", nav.position);
		value.put("status", nav.status);
		db.insert(table_list_nav, null, value);
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
	
	public ArrayList<Frame_nav> get_all_nav(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ArrayList<Frame_nav> list_nav =  new ArrayList<Frame_nav>();
		String query = "select * from "+table_list_nav;
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do {
				
				list_nav.add(new Frame_nav(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4)));
			} while (cursor.moveToNext());
		}
		Log.d("get All berita","OPK");
		db.close();
		return list_nav;
		
	}
	
	public ArrayList<Frame_nav> get_all_nav_cek(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ArrayList<Frame_nav> list_nav =  new ArrayList<Frame_nav>();
		String query = "select * from "+table_list_nav+" where status=1";
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do {
				
				list_nav.add(new Frame_nav(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4)));
			} while (cursor.moveToNext());
		}
		Log.d("get All berita","OPK");
		db.close();
		return list_nav;
		
	}
	
	public int update_nav_icon(Frame_nav nav) {
		 
	    // 1. get reference to writable DB
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    // 2. create ContentValues to add key "column"/value
	    ContentValues values = new ContentValues();
	    values.put("icon", nav.icon); // get author
	 
	    // 3. updating row
	    int i = db.update(table_list_nav, //table
	            values, // column/value
	            "id_nav = ?", // selections
	            new String[] { String.valueOf(nav.id) }); //selection args
	    // 4. close
	    db.close();
	    Log.d("update Book", nav.toString());
	    return i;
	 
	}
	
	public int update_nav_favorite(Frame_nav nav) {
		 
	    // 1. get reference to writable DB
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    // 2. create ContentValues to add key "column"/value
	    ContentValues values = new ContentValues();
	    values.put("status", nav.status); // get author
	 
	    // 3. updating row
	    int i = db.update(table_list_nav, //table
	            values, // column/value
	            "id_nav = ?", // selections
	            new String[] { String.valueOf(nav.id) }); //selection args
	    // 4. close
	    db.close();
		Log.d("update Book", nav.toString());
	    return i;
	 
	}
	
	public int delete_nav(Frame_nav nav){
		SQLiteDatabase db = this.getWritableDatabase();
		int i = db.delete(table_list_nav,"id_nav =?",	 new String[] {String.valueOf(nav.id)});
		db.close();
		db.close();
		Log.d("Delete nav",nav.id);
		return i;	
		
	}
	public int delete_nav(int berita){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql="delete from list_nav where id_nav='"+berita+"'";
		db.rawQuery(sql, null);
		db.close();
		Log.d("Delete nav Id",""+berita);
		return 0;	
	}
		
}
