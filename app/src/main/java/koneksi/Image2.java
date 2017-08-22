package koneksi;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Image2 extends AsyncTask<String, Void, Bitmap>{
	@Override
	protected Bitmap doInBackground(String... urls) {
		// TODO Auto-generated method stub
		Bitmap response;
        response = DownloadImage(urls[0]);
        return response;
	}

	 private Bitmap DownloadImage(String URL)
	    {        
	        Bitmap bitmap = null;
	        InputStream in;
	        try {
	            in = OpenHttpConnection(URL);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	        return bitmap;                
	    }
	 
	 private InputStream OpenHttpConnection(String urlString) 
			    throws IOException
			    {
			        InputStream in = null;
			        int response;
			                
			        URL url = new URL(urlString); 
			        URLConnection conn = url.openConnection();
			                  
			        if (!(conn instanceof HttpURLConnection))                     
			            throw new IOException("Not an HTTP connection");
			         
			        try{
			            HttpURLConnection httpConn = (HttpURLConnection) conn;
			            httpConn.setAllowUserInteraction(false);
			            httpConn.setInstanceFollowRedirects(true);
			            httpConn.setRequestMethod("GET");
			            httpConn.connect(); 
			 
			            response = httpConn.getResponseCode();                 
			            if (response == HttpURLConnection.HTTP_OK) {
			                in = httpConn.getInputStream();                                 
			            }                     
			        }
			        catch (Exception ex)
			        {
			            throw new IOException("Error connecting");            
			        }
			        return in;     
			    }

}
	