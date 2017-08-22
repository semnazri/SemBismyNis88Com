package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

import model.Frame_ListBerita;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class DataDownloaderSaved extends AsyncTask<String, Void, String> {
@SuppressWarnings("rawtypes")
private final WeakReference list_berita,loading;
private static ArrayList<Frame_ListBerita> arraylistheadline = new ArrayList<Frame_ListBerita>();
public List<NameValuePair> params = new ArrayList<NameValuePair>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataDownloaderSaved(Adapter_Saved adapter_saved, ProgressBar porses) {
		// TODO Auto-generated constructor stub
		arraylistheadline.clear();
		list_berita = new WeakReference(adapter_saved);
		loading = new WeakReference(porses);
	}
	

	@Override
	protected String doInBackground(String... params) {
	 String response;
      response = getRequest(params[0]);
      return response;    								
	}
	
	@Override
	protected void onPostExecute(String result){
		Adapter_Saved imageView = (Adapter_Saved) list_berita.get();
		ProgressBar dataload = (ProgressBar) loading.get();
		 if (isCancelled()) {
			 result = null;
	        }
	        if (list_berita != null) {
	        	
	        	JSONArray jsonarray;
				try {
					jsonarray = new JSONArray(result);
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject obj = jsonarray.getJSONObject(i);
						JSONObject obj2 = obj.getJSONObject("date");
						//if(i==0){
					//	arraylistheadline.add(new Frame_ListBerita(obj.getInt("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=350", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title")));
					//	}else{
						arraylistheadline.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=450", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+obj.getString("category_link"),obj.getString("slug"),obj.getString("is_live"), obj.getString("subtitle")));
				//		}
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        imageView.addAll(arraylistheadline);
			imageView.notifyDataSetChanged();
	        dataload.setVisibility(View.INVISIBLE);   
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


