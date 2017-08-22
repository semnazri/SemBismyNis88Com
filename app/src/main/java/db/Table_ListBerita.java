package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.util.ArrayList;

import model.Frame_ListBerita;


public class Table_ListBerita extends MySqLiteHelper {


    //MySqLiteHelper connect = new  MySqLiteHelper(,null, null, null);
    protected static final String table_listberita = "list_berita";

    public Table_ListBerita(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, MySqLiteHelper.Database_Name, null, MySqLiteHelper.DataBase_V);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //	db.execSQL("DROP TABLE IF EXISTS list_berita");
        String CREATE_LISTBERITA_TABLE = "CREATE TABLE list_berita ( " +
                "idberita TEXT PRIMARY KEY, " +
                "imgberita TEXT, " +
                "tglberita TEXT," +
                "judulberita TEXT," +
                "pukul TEXT," +
                "category TEXT," +
                "datepost TEXT," +
                "image_content TEXT," +
                "parent_category TEXT," +
                "slug TEXT," +
                "live TEXT," +
                "subtitle TEXT" +
                ")";
        //db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_LISTBERITA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //	db.execSQL("DROP TABLE IF EXISTS list_berita");
        // create fresh books table
        this.onCreate(db);
    }


    //protected static final String[] books_columns = {book_id,book_title,book_title};

    public void addBerita(Frame_ListBerita berita) {
        Log.d("addbook", berita.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("idberita", berita.getIdberita());
        value.put("imgberita", berita.getImgberita());
        value.put("tglberita", berita.getTglberita());
        value.put("judulberita", berita.getJudulberita());
        value.put("pukul", berita.getPukul());
        value.put("category", berita.getCategory());
        value.put("datepost", berita.getDatepost());
        value.put("image_content", berita.getImage_content());
        value.put("parent_category", berita.getParent_category());
        value.put("slug", berita.getSlug());
        value.put("live", berita.getLive());
//        value.put("subtitle", berita.getSubtitle());
        db.insert(table_listberita, null, value);

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

    public ArrayList<Frame_ListBerita> getAllBerita() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Frame_ListBerita> beritas = new ArrayList<Frame_ListBerita>();
        String query = "select * from " + table_listberita;
        Cursor cursor = db.rawQuery(query, null);

        Frame_ListBerita berita = null;
        if (cursor.moveToFirst()) {
            do {
                berita = new Frame_ListBerita();
                berita.setIdberita(cursor.getString(0));
                berita.setImgberita(cursor.getString(1));
                berita.setTglberita(cursor.getString(2));
                berita.setJudulberita(cursor.getString(3));
                berita.setPukul(cursor.getString(4));
                berita.setCategory(cursor.getString(5));
                berita.setDatepost(cursor.getString(6));
                berita.setImage_content(cursor.getString(7));
                berita.setParent_category(cursor.getString(8));
                berita.setSlug(cursor.getString(9));
                berita.setLive(cursor.getString(10));
//                berita.setSubtitle(cursor.getString(11));
                beritas.add(berita);
            } while (cursor.moveToNext());
        }
        Log.d("get All berita", "OPK");
        db.close();
        return beritas;

    }

    public int deleteBerita(Frame_ListBerita berita) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(table_listberita, "idberita =?", new String[]{String.valueOf(berita.getIdberita())});
        db.close();
        Log.d("Delete berita", berita.getIdberita());
        return i;

    }

    public int deleteBerita(int berita) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "delete from list_berita where idberita=" + berita;
        db.rawQuery(sql, null);
        db.close();
        Log.d("Delete Berita Id", "" + berita);
        return 0;
    }

}
