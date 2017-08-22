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

import bisnis.com.official.home.Adapter_List_Berita_Rec;
import model.Frame_ListBerita;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DataDownloaderOfIndex extends AsyncTask<String, Void, String> {

@SuppressWarnings("rawtypes")
private final WeakReference list_berita,loading,index_start2,index_much2,index_day2;
//private static ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
public List<NameValuePair> params = new ArrayList<NameValuePair>();
	Adapter_List_Berita_Rec adapter_list_berita ;
ProgressBar dataload;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataDownloaderOfIndex(Adapter_List_Berita_Rec list_data, ProgressBar porses, TextView index_start, TextView index_much, TextView index_day) {
		// TODO Auto-generated constructor stub
		list_berita = new WeakReference(list_data);
		loading = new WeakReference(porses);
		index_start2 = new WeakReference(index_start);
		index_much2 = new WeakReference(index_much);
		index_day2= new WeakReference(index_day);
	}
	

	@Override
	protected String doInBackground(String... params) {
	 String response;
      response = getRequest(params[0]);
     System.out.println(params[0]);
      return response;    								
	}
	
	@Override
	protected void onPostExecute(String result){	
		adapter_list_berita= (Adapter_List_Berita_Rec) list_berita.get();
		TextView index_start = (TextView) index_start2.get();
		TextView index_much = (TextView) index_much2.get();
		TextView index_day = (TextView) index_day2.get();
		
		 int mulai = Integer.parseInt(index_start.getContentDescription().toString());
		 int much = Integer.parseInt(index_much.getContentDescription().toString());
		 int day = Integer.parseInt(index_day.getContentDescription().toString());
		
	
		dataload = (ProgressBar) loading.get();
		
	
		 if (isCancelled()) {
			 result = null;
	        }
	        if (list_berita != null) {
	        	JSONArray jsonarray;
				try {
					
					System.out.println("hari pagging "+mulai+ " asdas dasd "+ much);
					jsonarray = new JSONArray(result);
					for (int i=0 ; i < 10 ; i++) {
						JSONObject obj = jsonarray.getJSONObject(i);
						JSONObject obj2 = obj.getJSONObject("date");
						String [] split =obj.getString("category_link").split("-");
						 split [0]=Character.toUpperCase(split [0].charAt(0)) + split [0].substring(1);
						 String sub_canal = split [0];//+" "+split [1];
						 for (int j = 1; j < split.length; j++) {
							 split [j]=Character.toUpperCase(split [j].charAt(0)) + split [j].substring(1);
							 sub_canal += " "+split [j] ;
						}
						//array_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=300", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live")));
                        adapter_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=600", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year")," - "+obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),"0",sub_canal,obj.getString("slug"),obj.getString("is_live"), obj.getString("subtitle")));
                    }
			        mulai=mulai+10;
			        much = much+10;
			        index_much.setContentDescription(""+much);
			        index_start.setContentDescription(""+mulai);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
				//	dataload.setContentDescription("abis");
					day=day+1;
					 index_start.setContentDescription("0");
					 index_day.setContentDescription(""+day);
					 index_much.setContentDescription("0");
					dataload.setVisibility(View.GONE);
					e.printStackTrace();
				}
	        }
	    
	   //   adapter_list_berita.addAll(array_list_berita);

	        adapter_list_berita.notifyDataSetChanged();
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


