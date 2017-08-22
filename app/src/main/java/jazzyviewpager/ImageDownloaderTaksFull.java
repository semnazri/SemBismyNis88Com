package jazzyviewpager;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import bisnis.com.official.R;

public class ImageDownloaderTaksFull extends AsyncTask<String, Void, Bitmap> {
private final WeakReference<View> imageViewReference;
//ArrayList<Bitmap>  imageView2 ;
File sdCardDirectory;
//private static ArrayList<Bitmap> arraylistheadline2 = new ArrayList<Bitmap>();

	@SuppressWarnings({ "unchecked", "rawtypes" })	 
	public ImageDownloaderTaksFull(ImageZoom img_view) {
		// TODO Auto-generated constructor stub
		imageViewReference = new WeakReference(img_view);
	}

	@Override
	protected void onPreExecute(){
		//imageView2.add(bm);

	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		 
		return downloadBitmap(params[0]);
		
	}
	 
	@Override
	protected void onPostExecute(Bitmap bitmap){
		 if (isCancelled()) {
	       //     bitmap = null;
	        }
	        if (imageViewReference != null) {
	        	ImageZoom imageView = (ImageZoom) imageViewReference.get();
	          
	          //  imageView2.add(null);
	          //  imageView2.set(position, bitmap);
	          
	            ImageDownloaderTaksFull bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
	          // imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
	           if(this==bitmapDownloaderTask){
	        	 
	            
	    
	        	 //  imageView.setImageBitmap(bitmap);
	           }
	           if (imageView != null) {
	        		 
	                if (bitmap != null) {
	    	     
	    	               imageView.setImageBitmap(bitmap);
//	    	             //	int width=;
//	    	               float width= imageView.getContext().getResources().getDisplayMetrics().widthPixels/200;
//	    	               System.out.println("VIEW DETAIL"+bitmap.getHeight());
//	    	             
//	    		    		if(width<=2){
//	    		    		 if(bitmap.getHeight()<250){
//	    		    	  		 if(bitmap.getHeight()<250)
//	    		    	  			imageView.getLayoutParams().height=(int) (300-(width*20));
//	    		    		 }else{
//	    		    				imageView.getLayoutParams().height=(int) (bitmap.getHeight()-(width*20));
//	    		    		 }
//	    		   
//	    		    		}else{
//	    		    	  		 if(bitmap.getHeight()<300)
//	    		    	  		 {
//	    		    	  			imageView.getLayoutParams().height=(int) (300+(width*40));
//	    		    	  		 }else{
//	    		    	  			imageView.getLayoutParams().height=(int) (bitmap.getHeight()+(width*40));
//	    		    	  		 }
//		    		    			
//	    		    		
//	    		    		}
	    		    		
	    	               //imageView.getLayoutParams().height=bitmap.getHeight()+100;
	    				
	    	               imageView.setVisibility(View.VISIBLE);
	                 //   imageView.setImageBitmap(bitmap);
	                } else {
	                    imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
	                }
	               
	            }
	        
	        }
	 
	       
	        
	}

private static ImageDownloaderTaksFull getBitmapDownloaderTask(ImageView imageView) {
	    if (imageView != null) {
	        Drawable drawable = imageView.getDrawable();
	        if (drawable instanceof DownloadedDrawable) {
	            DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
	            return downloadedDrawable.getBitmapDownloaderTask();
	        }
	    }
	    return null;
	}

static class DownloadedDrawable extends ColorDrawable {
    private final WeakReference<ImageDownloaderTaksFull> bitmapDownloaderTaskReference;

    public DownloadedDrawable(ImageDownloaderTaksFull bitmapDownloaderTask) {
        super(Color.WHITE);
        bitmapDownloaderTaskReference =
            new WeakReference<ImageDownloaderTaksFull>(bitmapDownloaderTask);
    }

    public ImageDownloaderTaksFull getBitmapDownloaderTask() {
        return bitmapDownloaderTaskReference.get();
    }
}
	            
static Bitmap downloadBitmap(String url){
	final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
	final HttpGet getRequest = new HttpGet(url);
	try {
		HttpResponse response = client.execute(getRequest);
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            Log.w("ImageDownloader", "Error " + statusCode
                    + " while retrieving bitmap from " + url);
            return null;
        }

        final HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = null;
            try {
                inputStream = entity.getContent();
                BufferedInputStream bis = new BufferedInputStream(inputStream);                 
            //    bis.mark(250 * 250);     
                final Bitmap bitmap = BitmapFactory.decodeStream(bis);
                return bitmap;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                entity.consumeContent();
            }
        }
	} catch (Exception e) {
		// IllegalStateException
		// Could provide a more explicit error message for IOException or
        getRequest.abort();
        Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
    } finally {
        if (client != null) {
            client.close();
        }
    }
	
	return null;
		
		
	}
}
