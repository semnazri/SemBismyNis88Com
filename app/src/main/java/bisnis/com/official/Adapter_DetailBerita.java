package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.File;
import java.util.ArrayList;

import bisnis.com.official.R;
import model.Frame_DetailBerita;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class Adapter_DetailBerita extends ArrayAdapter<Frame_DetailBerita>{
	private Context context;
	private int layoutResourceId;
	public ArrayList<Frame_DetailBerita> data = new ArrayList<Frame_DetailBerita>();
	
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	File sdCardDirectory = Environment.getExternalStorageDirectory();
	
	public Adapter_DetailBerita(Context context, int layoutResourceId,ArrayList<Frame_DetailBerita> objects) {
		super(context, layoutResourceId, objects);
		// TODO Auto-generated constructor stub
//		imagelist.clear();
		data.clear();
		this.context = context;
		this.layoutResourceId=layoutResourceId;
		this.data= objects;
	}
	
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		
		BeritaHolder holder = new BeritaHolder();
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    	row = inflater.inflate(layoutResourceId, parent,false);
    	assert row != null;
    	holder.imgberita =(ImageView)row.findViewById(R.id.imgfitured);
    	holder.txtdate = (TextView)row.findViewById(R.id.txtdate);
    	holder.txtjudul = (TextView)row.findViewById(R.id.txtjudul);
    	holder.txtpenulis = (TextView)row.findViewById(R.id.txt_detail_penulis);
    	holder.webdetail = (WebView)row.findViewById(R.id.webdetail);
 
    	holder.txtimg_caption = (TextView)row.findViewById(R.id.txtimg_caption);
    	holder.txteditor = (TextView)row.findViewById(R.id.txteditor);
    	
    	
    	row.setTag(holder);
    	Frame_DetailBerita berita = data.get(position);
    	holder.webdetail.loadData(berita.content, "text/html; charset=UTF-8", "utf-8");
    	holder.webdetail.getSettings().setJavaScriptEnabled(true);
		holder.webdetail.getSettings().setBuiltInZoomControls(true);
    	String string = berita.date;
		String tgl[] = string.split(" ");
		String jam[] = tgl[1].split(":");
		String thn[] = tgl[0].split("-"); 
    	
		
    	holder.txtdate.setText(thn[2]+"/"+thn[1]+"/"+thn[0]+" "+jam[0]+":"+jam[1]+" WIB");
    	holder.txtjudul.setText(berita.judul);
    	holder.txtpenulis.setText("Editor : "+berita.penulis);
    	holder.txteditor.setText("Editor : " +berita.editor);
    	if(!berita.img_caption.equals("")){
    		holder.txtimg_caption.setText(berita.img_caption);
    	}else{
    		holder.txtimg_caption.setVisibility(View.GONE);
    	}
    	
    	File image2 = new File(sdCardDirectory+"/BIS",berita.image_content+".BIS");
    	
    	//if(imagelist.isEmpty()){
    		 if(!ceknamaimage(berita.image_content+".BIS")){
    		
    		new ImageDownloaderTaks(holder.imgberita,null,berita.image_content).execute(berita.image);
    		 }
    		/*
   		 try {
				//imagelist.add(new ImageDownloaderTaks(holder.imgberita,imagelist).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.image).get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
				
			}
   		 */
		// }
    		 else{
    			 final BitmapFactory.Options options = new BitmapFactory.Options();
  			   options.inSampleSize = 2;
  			 Bitmap myBitmap = BitmapFactory.decodeFile(image2.getAbsolutePath(),options);
	             System.out.println(image2.getAbsolutePath());
	             holder.imgberita.setImageBitmap(myBitmap);
		//	 holder.imgberita.setImageBitmap(imagelist.get(position));
		 }
    		 
    		 
    	
		return row;	
	}
	
	
	private boolean ceknamaimage(String path) {
		// TODO Auto-generated method stub
		File ceknama = new File(Environment.getExternalStorageDirectory()+"/BIS",path);
		if(!ceknama.exists()){
			Log.e("TravellerLog :: ", "gak ada");
			return false;
		}else{
			
			Log.e("TravellerLog :: ", "Ada");
			
			return true;
			}
	}


	static class BeritaHolder{
		ImageView imgberita;
		TextView txtjudul,txtdate,txtpenulis,txtimg_caption,txteditor;
		WebView webdetail,webcomment;
	}
	


}
