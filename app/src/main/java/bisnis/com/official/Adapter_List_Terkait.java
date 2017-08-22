package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.util.ArrayList;

import db.Table_Setting;
import model.Frame_ListBerita;
import bisnis.com.official.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_List_Terkait extends ArrayAdapter<Frame_ListBerita>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Frame_ListBerita> data = new ArrayList<Frame_ListBerita>();
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	
	public Adapter_List_Terkait(Context context, int layoutResourceId,ArrayList<Frame_ListBerita> objects) {
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
    	
    	holder.txtdate = (TextView)row.findViewById(R.id.txt_tgl);
    	holder.txtjudul = (TextView)row.findViewById(R.id.txt_title);
		holder.subtitle = (TextView)row.findViewById(R.id.subtitle);
        Typeface open_sans= Typeface.createFromAsset(getContext().getAssets(),"OpenSans-Regular.ttf");
    	row.setTag(holder);
        Table_Setting db = new Table_Setting(getContext(), null, null, 0);
        int size_font = db.get_setting_font_size();
    	db.close();
    	switch (size_font) {
            case 0:
                size_font = 16;
                break;
            case 1:
                size_font = 18;
                break;
            case 2:
                size_font = 20;
                break;
            case 3:
                size_font = 22;
                break;


		default:
			break;
		}
    	
    	Frame_ListBerita berita = data.get(position);
        holder.txtjudul.setTypeface(open_sans,Typeface.BOLD);
    	holder.txtdate.setText(berita.getPukul());
    	holder.txtdate.setTextSize(size_font-5);
    	holder.txtjudul.setText(berita.getJudulberita());
    	holder.txtjudul.setTextSize(size_font);
//		holder.subtitle.setText(berita.getSubtitle());


    	 
		return row;	
	}
	

	static class BeritaHolder{
		ImageView imgberita,imgberita2;
		TextView txtjudul,txtdate,txtjudul2,txtdate2,txtminut, subtitle;
		LinearLayout linerberita;
	}
	


}
