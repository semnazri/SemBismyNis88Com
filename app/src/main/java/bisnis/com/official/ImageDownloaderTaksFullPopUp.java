package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import example.ImageZoom;

public class ImageDownloaderTaksFullPopUp extends AsyncTask<String, Void, Bitmap> {
private final WeakReference<View> imageViewReference,proses;
//ArrayList<Bitmap>  imageView2 ;
File sdCardDirectory;
//private static ArrayList<Bitmap> arraylistheadline2 = new ArrayList<Bitmap>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ImageDownloaderTaksFullPopUp(ImageView imageView, ProgressBar porses) {
		// TODO Auto-generated constructor stub
		
		imageViewReference = new WeakReference(imageView);
		proses = new WeakReference(porses);
		// imageView2= (ArrayList<Bitmap>) arraylistheadline.get();
		  
	
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
		ProgressBar dataload = (ProgressBar) proses.get();
		 if (isCancelled()) {
	       //     bitmap = null;
	        }
	        if (imageViewReference != null) {
	         final   ImageZoom imageView = (ImageZoom) imageViewReference.get();
	          
	          //  imageView2.add(null);
	          //  imageView2.set(position, bitmap);
	          
	            ImageDownloaderTaksFullPopUp bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
	          // imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
	           if(this==bitmapDownloaderTask){
	        	 
	            
	    
	        	 //  imageView.setImageBitmap(bitmap);
	           }
	           if (imageView != null) {
	        		 
	                if (bitmap != null) {
	    	     
	    	               imageView.setImageBitmap(bitmap);
	    	             //	int width=;
	    	               float width= imageView.getContext().getResources().getDisplayMetrics().widthPixels/200;
	    	               System.out.println("VIEW DETAIL"+bitmap.getHeight());

                        imageView.setImageBitmap(bitmap);
                        int screen_height =imageView.getContext().getResources().getDisplayMetrics().heightPixels;
                        int screen_width =imageView.getContext().getResources().getDisplayMetrics().widthPixels;
                        int  finalWidth = imageView.getMeasuredWidth();
                        // System.out.println("Image Screen "+ screen +" model" +((display.getWidth()*finalHeight)/finalWidth)/imageView.getLayoutParams().height + " HHA" +((screen*finalHeight)/finalWidth));

                      //  System.out.println("Image Screen "+((screen_width * finalHeight) /screen_height )+" screnn "+ display.getWidth() +" width "+ display.getHeight() +" model ");
                        //if(((display.getWidth()*finalHeight)/finalWidth)/imageView.getLayoutParams().height<2) {
                        //   imageView.getLayoutParams().height = ((display.getHeight() * finalHeight) / display.getWidth());
                        imageView.getLayoutParams().height = ((screen_width * finalWidth) / screen_height)+200;

                        // System.out.println("Image Screen "+ screen +" model" +display.getWidth() + " HHA" +((screen*finalHeight)/finalWidth));
                   //     imageView.getLayoutParams().height=((screen*finalHeight)/finalWidth);


                        ViewTreeObserver vto = imageView.getViewTreeObserver();
                        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                                int finalHeight = imageView.getMeasuredHeight();
                                int  finalWidth = imageView.getMeasuredWidth();
                                imageView.getLayoutParams().height=finalHeight;
                                return true;
                            }
                        });

                        //imageView.getLayoutParams().height=bitmap.getHeight()+100;
                        imageView.setVisibility(View.VISIBLE);
                        Animation visible = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.visable);
	    	            imageView.setAnimation(visible);
	                 //   imageView.setImageBitmap(bitmap);
	                } else {
	                    imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.img_logo));
	                }
	               
	            }
	        
	        }
	 
	        dataload.setVisibility(View.INVISIBLE);
	        
	}

private static ImageDownloaderTaksFullPopUp getBitmapDownloaderTask(ImageView imageView) {
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
    private final WeakReference<ImageDownloaderTaksFullPopUp> bitmapDownloaderTaskReference;

    public DownloadedDrawable(ImageDownloaderTaksFullPopUp bitmapDownloaderTask) {
        super(Color.WHITE);
        bitmapDownloaderTaskReference =
            new WeakReference<ImageDownloaderTaksFullPopUp>(bitmapDownloaderTask);
    }

    public ImageDownloaderTaksFullPopUp getBitmapDownloaderTask() {
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
