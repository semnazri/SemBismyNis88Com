package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.util.ArrayList;

import model.Frame_ListBerita;
import bisnis.com.official.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_List_Live extends ArrayAdapter<Frame_ListBerita>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Frame_ListBerita> data = new ArrayList<Frame_ListBerita>();
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	
	public Adapter_List_Live(Context context, int layoutResourceId,ArrayList<Frame_ListBerita> objects) {
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
		BeritaHolderLive holder2 = new BeritaHolderLive();
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    	row = inflater.inflate(layoutResourceId, parent,false);
    	assert row != null;
    	
    	holder2.txtjudul = (TextView)row.findViewById(R.id.txt_title_live);
    	holder2.web_live = (WebView)row.findViewById(R.id.web_live);
    	
    	row.setTag(holder2);
    	Frame_ListBerita berita = data.get(position);
    	holder2.txtjudul.setText(berita.getJudulberita());
    	holder2.web_live.loadData(berita.getSlug(),  "text/html", "utf-8");
		return row;	
	}
	

	static class BeritaHolderLive{
		ImageView imgberita,imgberita2;
		TextView txtjudul,txtdate,txtjudul2,txtdate2,txtminut;
		LinearLayout linerberita;
		WebView web_live;
	}
	


}
