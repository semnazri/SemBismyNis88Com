package koneksi;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Video_Url2  extends AsyncTask <String ,  Void, String> {
	 
  //  private ProgressDialog dialog;
    public Context applicationContext;
   // public String web="http://10.0.2.2/bkci/";
   //public String web="http://192.168.56.1/bkci/";
//   public String web="http://www.bkdroid.bknime.com/";
    public List<NameValuePair> params = new ArrayList<NameValuePair>();
@Override
  protected void onPreExecute() {
       // this.dialog = ProgressDialog.show(applicationContext, "Data Process", "Tunggu Sebentar", true);
    }


    protected String doInBackground(String... urls) {
          String response;
          response = getRequest(urls[0]);
          return response;
          
    }


        @Override
    protected void onPostExecute(String  result) {
    //    this.dialog.cancel();
  	      	
   }

	
	// buat connect ke server
	public String getRequest(String urls) {
		// TODO Auto-generated method stub
		String sret="";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new  HttpGet(urls);
		try{
			// UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			 //request.setURI(urls);
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





