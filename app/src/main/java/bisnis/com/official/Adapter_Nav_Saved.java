package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.File;
import java.util.ArrayList;

//import com.google.android.gms.internal.da;
//import com.google.android.gms.internal.ia;

import bisnis.com.official.R;
import model.Frame_ListBerita;
import model.Frame_nav;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_Nav_Saved extends ArrayAdapter<Frame_nav>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Frame_nav> data = new ArrayList<Frame_nav>();
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	ImageView imagecontoh;
	File sdCardDirectory = Environment.getExternalStorageDirectory();

    
	
	public Adapter_Nav_Saved(Context context, int layoutResourceId,ArrayList<Frame_nav> objects) {
		super(context, layoutResourceId, objects);
		// TODO Auto-generated constructor stub
		
		data.clear();
		this.context = context;
		this.layoutResourceId=layoutResourceId;
		this.data= objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		BeritaHolder holder = new BeritaHolder();
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    	row = inflater.inflate(layoutResourceId, parent,false);
    	assert row != null;
    //	holder.imgberita =(ImageView)row.findViewById(R.id.imgfitured);
    	//holder.imgberita2 =(ImageView)row.findViewById(R.id.imgfitured2);
    	holder.txtdate = (TextView)row.findViewById(R.id.title);
    	holder.imgnav=(ImageView)row.findViewById(R.id.icon);
    	holder.cek_favorite = (CheckBox)row.findViewById(R.id.cek_favorite);
    	
    	row.setTag(holder);
    	Frame_nav nav = data.get(position);
    	holder.txtdate.setText(nav.nav);
    	holder.imgnav.setImageResource(nav.icon);
    	if(nav.status==0){
    		holder.cek_favorite.setChecked(false);
    	}else{
    		holder.cek_favorite.setChecked(true);
    	}
    	
    	 
		return row;	
	}

	static class BeritaHolder{
		ImageView imgnav,imgberita2;
		TextView txtjudul,txtdate,txtjudul2,txtdate2,txtminut;
		LinearLayout linerberita;
		CheckBox cek_favorite;
	}
	


}
