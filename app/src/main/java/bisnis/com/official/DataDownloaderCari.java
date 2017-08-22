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

public class DataDownloaderCari extends AsyncTask<String, Void, String> {
@SuppressWarnings("rawtypes")
private final WeakReference list_berita,loading;
//private static ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
public List<NameValuePair> params = new ArrayList<NameValuePair>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataDownloaderCari(Adapter_List_Cari adapter_List_Cari, ProgressBar porses) {
		// TODO Auto-generated constructor stub
		
		list_berita = new WeakReference(adapter_List_Cari);
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
		Adapter_List_Cari adapter_list_berita = (Adapter_List_Cari) list_berita.get();
		ProgressBar dataload = (ProgressBar) loading.get();
		 if (isCancelled()) {
			 result = null;
	        }
	        if (list_berita != null) {
	        	
	        	try {
					JSONObject obj  = new JSONObject(result);
					JSONObject obj2= obj.getJSONObject("response");
					JSONArray array = obj2.getJSONArray("docs");
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj3 = array.getJSONObject(i);
						JSONArray array2 = obj3.getJSONArray("title");
						JSONArray array3 = obj3.getJSONArray("content");
						String post_id = null;
						if(obj3.getString("id").length()<6){
							 post_id = obj3.getString("id");
						}else{
							 post_id = obj3.getString("id").substring(0, 6);
						}
						String time = obj3.getString("last_modified");
						String date =  obj3.getString("last_modified"); 
						time = time.substring(11, 16);
						date =date.substring(0,10);
						String [] slug =obj3.getString("url").split("/");
						
						String[] tgl =date.split("-");
						String url = tgl[0]+tgl[1]+tgl[2];
						System.err.println("Slug : "+slug[7]);
						
						//adapter_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=450", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content")));
						//array_list_berita.add(new Frame_ListBerita(post_id,obj3.getString("category"),url,array3.getString(0),tgl[2]+"/"+tgl[1]+"/"+tgl[0],time+" WIB", array2.getString(0).toString(),array3.getString(0),"",slug[7],"0"));
						adapter_list_berita.add(new Frame_ListBerita(post_id,obj3.getString("category"),url,array3.getString(0),tgl[2]+"/"+tgl[1]+"/"+tgl[0],time+" WIB", array2.getString(0).toString(),array3.getString(0),"",slug[7],"0", ""));
					}
					//System.out.println(array);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					dataload.setContentDescription("abis");
					dataload.setVisibility(View.GONE);  
					dataload.setEnabled(false);
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


