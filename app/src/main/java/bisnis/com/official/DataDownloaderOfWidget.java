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

public class DataDownloaderOfWidget extends AsyncTask<String, Void, String> {
@SuppressWarnings("rawtypes")
private WeakReference list_berita,loading;
private static ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
public List<NameValuePair> params = new ArrayList<NameValuePair>();

	public DataDownloaderOfWidget(List<Frame_ListBerita> mWidgetItems) {
		// TODO Auto-generated constructor stub.
		list_berita = new WeakReference(mWidgetItems);
	}


	@Override
	protected String doInBackground(String... params) {
	 String response;
      response = getRequest(params[0]);
      return response;    								
	}
	
	@Override
	protected void onPostExecute(String result){
		List<Frame_ListBerita> adapter_list_berita = (List<Frame_ListBerita>) list_berita.get();
		 if (isCancelled()) {
			 result = null;
	        }
	        if (list_berita != null) {}
	   //   adapter_list_berita.addAll(array_list_berita);
	      //  adapter_list_berita.notifyDataSetChanged();
	        //dataload.setVisibility(View.INVISIBLE);   
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


