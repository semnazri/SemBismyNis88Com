package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import model.Frame_ListBerita;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bisnis.com.official.AnalyticBisnisApp.TrackerName;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.android.gms.internal.ob;

import example.CustomExpandListView;
import example.TouchImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class DataDownloaderOfDetailSeries extends AsyncTask<String, Void, String> {
@SuppressWarnings("rawtypes")
private final WeakReference porses2,txtjuduldetailseries2,txtseries2,txtseriesbottom2,txtnextseries2,txtprevious2,webdetail2,imgberita2,txtimg_sumber2,txtimg_caption2,txtnextseriesbottom2,txtpreviousseriesbottom2,scrollbertia2;
public List<NameValuePair> params = new ArrayList<NameValuePair>();
//private static ArrayList<Frame_ListBerita> array_list_terkait = new ArrayList<Frame_ListBerita>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataDownloaderOfDetailSeries(ProgressBar porses,
			TextView txtjuduldetailseries, TextView txtseries,
			TextView txtseriesbottom, TextView txtnextseries,
			TextView txtprevious, WebView webdetail, ImageView imgberita,
			TextView txtimg_sumber,TextView txtimg_caption, TextView txtnextseriesbottom,
			TextView txtpreviousseriesbottom, ScrollView scrollbertia) {
		// TODO Auto-generated constructor stub
		porses2 = new WeakReference(porses);
		txtjuduldetailseries2= new WeakReference(txtjuduldetailseries);
		txtseries2 =  new WeakReference(txtseries);
		txtseriesbottom2= new WeakReference(txtseriesbottom);
		txtnextseries2= new WeakReference(txtnextseries);
		txtprevious2= new WeakReference(txtprevious);
		webdetail2= new WeakReference(webdetail);
		imgberita2=new WeakReference(imgberita);
		txtimg_sumber2=new WeakReference(txtimg_sumber);
		txtimg_caption2=new WeakReference(txtimg_caption);
		txtnextseriesbottom2=new WeakReference(txtnextseriesbottom);
		txtpreviousseriesbottom2= new WeakReference(txtpreviousseriesbottom);
		scrollbertia2 = new WeakReference(scrollbertia);

	}


	@Override
	protected String doInBackground(String... params) {
	 String response;
      response = getRequest(params[0]);
      System.out.println(params[0]);
      return response;    								
	}
	
	@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ScrollView scronberita = (ScrollView)scrollbertia2.get();
			 scronberita.smoothScrollTo(0, 0);
		}
	
	@Override
	protected void onPostExecute(String result){
		ProgressBar dataload = (ProgressBar) porses2.get();
		TextView	txtjuduldetailseries = (TextView)txtjuduldetailseries2.get();
		TextView	txtseries = (TextView)txtseries2.get();
		TextView	txtseriesbottom = (TextView)txtseriesbottom2.get();
		TextView	txtnextseries  = (TextView)txtnextseries2.get();
		TextView	txtprevious = (TextView)txtprevious2.get();
		
		
		WebView	webdetail = (WebView)webdetail2.get();
		ImageView imgberita = (ImageView)imgberita2.get();
		TextView txt_img_sumber = (TextView)txtimg_sumber2.get();
		TextView	txtimg_caption = (TextView)txtimg_caption2.get();
		
		TextView	txtnextseriesbottom = (TextView)txtnextseriesbottom2.get();
		TextView	txtpreviousseriesbottom = (TextView)txtpreviousseriesbottom2.get();

		
		
		 if (isCancelled()) {
			 result = null;
	        }
	        if (result != null) {
				try {
					 JSONObject data = new JSONObject(result);
					 JSONObject obj = data.getJSONObject("data");
					JSONObject obj2 = obj.getJSONObject("date");
					JSONObject obj3 = obj.getJSONObject("category");
					//txtjudul.setText(obj.getString("title"));
					String [] Caption = obj.getString("image_caption").split("/");
					txtimg_caption.setText(Caption[0]);
					
					if(Caption.length>1){
						txt_img_sumber.setText(Caption[1]);
						txt_img_sumber.setVisibility(View.VISIBLE);
					}
				
					String css= ""+webdetail.getContentDescription();
					webdetail.loadData(css+"<div class='detail'>"+obj.getString("content")+"</div>", "text/html", "utf-8");
					//webdetail.setContentDescription(obj.getString("content"));
					imgberita.setContentDescription(obj.getString("image_uri"));
					
					txtjuduldetailseries.setVisibility(View.VISIBLE);
					txtjuduldetailseries.setText(obj.getJSONObject("series").getString("title"));
					txtseries.setText((obj.getJSONObject("series").getInt("order")+1) +" dari "+obj.getString("total_series"));
					txtseriesbottom.setText((obj.getJSONObject("series").getInt("order")+1) +" dari "+obj.getString("total_series"));
					if(obj.getJSONObject("series").getInt("order")<obj.getInt("total_series")-1){
					txtnextseries.setContentDescription(obj.getJSONObject("next_series").getString("post_id"));
					txtnextseriesbottom.setContentDescription(obj.getJSONObject("next_series").getString("post_id"));
					txtnextseries.setVisibility(View.VISIBLE);
					txtnextseriesbottom.setVisibility(View.VISIBLE);
					}else{
						txtnextseries.setVisibility(View.INVISIBLE);
						txtnextseriesbottom.setVisibility(View.INVISIBLE);
					}
					if(obj.getJSONObject("series").getInt("order")>=1){
							txtprevious.setContentDescription(obj.getJSONObject("prev_series").getString("post_id"));
							txtpreviousseriesbottom.setContentDescription(obj.getJSONObject("prev_series").getString("post_id"));
							txtprevious.setVisibility(View.VISIBLE);
							txtpreviousseriesbottom.setVisibility(View.VISIBLE);
					}else{
                        txtjuduldetailseries.setVisibility(View.GONE);
						txtprevious.setVisibility(View.INVISIBLE);
						txtpreviousseriesbottom.setVisibility(View.INVISIBLE);
					}
					
	
					
				new ImageDownloaderTaksFull(imgberita,dataload).execute(imgberita.getContentDescription().toString().replaceAll(" ", "%20"));
				    	
					  
				
	
				
				
				
			
		
				
				//webcomment.loadUrl("http://27.123.222.118/json/comment/"+obj.getString("post_id")+"/"+obj.getString("slug"));
				//System.out.println("http://27.123.222.118/json/comment/"+obj.getString("post_id")+"/"+obj3.getString("slug"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
				
		  }
	       
	       // new ImageDownloaderTaksFull(imgberita,dataload).execute(imgberita.getContentDescription().toString().replaceAll(" ", "%20"));
	       

	         //updateListViewHeight(list_data_terkait);
	        
	        
	      //  dataload.setVisibility(View.INVISIBLE);   
	       
	}
	
	
	private boolean ceknamaimage(String path) {
		// TODO Auto-generated method stub
		File ceknama = new File(Environment.getExternalStorageDirectory()+"/BIS",path);
		System.out.println(path);
		if(!ceknama.exists()){
			Log.e("TravellerLog :: ", "gak ada");
			return false;
		}else{
			
			Log.e("TravellerLog :: ", "Ada");
			
			return true;
			}
	}
	
	

	// buat connect ke server
	public String getRequest(String urls) {
		// TODO Auto-generated method stub
		String sret="";
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new  HttpPost(urls);
		try{
			 UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			 request.setEntity(ent);
			HttpResponse response = client.execute(request);
			sret = request(response);
		}catch(Exception ex){
		//	Toast.makeText(this, "GAGAL" +sret, Toast.LENGTH_LONG).show();
			
		}
		
		return sret;
	}


	// buat menerima balasan dari server
	public String request(HttpResponse response) {
		// TODO Auto-generated method stub
		String result;
		try{
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line;
			while((line = reader.readLine())!=null){
				str.append(line).append("\n");
			}
			in.close();
			result =str.toString();
		}catch(Exception ex){
			result ="Error";
		}
		
		return result;
	}

}


