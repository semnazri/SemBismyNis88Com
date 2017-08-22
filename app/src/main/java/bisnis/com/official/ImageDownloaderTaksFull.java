package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import bisnis.com.official.R;
import jazzyviewpager.ImageZoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageDownloaderTaksFull extends AsyncTask<String, Void, Bitmap> {
private final WeakReference<View> imageViewReference,proses;
//ArrayList<Bitmap>  imageView2 ;
File sdCardDirectory;
//private static ArrayList<Bitmap> arraylistheadline2 = new ArrayList<Bitmap>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ImageDownloaderTaksFull(ImageView imageView, ProgressBar porses) {
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
	         final   ImageView imageView = (ImageView) imageViewReference.get();

	          //  imageView2.add(null);
	          //  imageView2.set(position, bitmap);

	            ImageDownloaderTaksFull bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
	          // imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
	           if(this==bitmapDownloaderTask){

	        	 //  imageView.setImageBitmap(bitmap);
	           }
	           if (imageView != null) {

	                if (bitmap != null) {

                        File image2 = new File(sdCardDirectory+"/Bisniscom","detail.cache");
                        FileOutputStream outStream;
                        try {
                            outStream = new FileOutputStream(image2);
                            bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(), true);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                            outStream = new FileOutputStream(image2);
                            outStream.flush();
                            outStream.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imageView.setImageBitmap(bitmap);

                        Animation visible = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.visable);
                        WindowManager wm = (WindowManager) imageView.getContext().getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();
                        int screen_width =imageView.getContext().getResources().getDisplayMetrics().widthPixels;
                        int screen_height =imageView.getContext().getResources().getDisplayMetrics().heightPixels;
                        int finalHeight = imageView.getMeasuredHeight();
                        int  finalWidth = imageView.getMeasuredWidth();
                       // System.out.println("Image Screen "+ screen +" model" +((display.getWidth()*finalHeight)/finalWidth)/imageView.getLayoutParams().height + " HHA" +((screen*finalHeight)/finalWidth));
                       float perbandingan = display.getHeight()/display.getWidth();
                        System.out.println("Image Screen "+((screen_width * finalHeight) /screen_height )+" screnn "+ display.getWidth() +" width "+ display.getHeight() +" model ");
                        //if(((display.getWidth()*finalHeight)/finalWidth)/imageView.getLayoutParams().height<2) {
                         //   imageView.getLayoutParams().height = ((display.getHeight() * finalHeight) / display.getWidth());
                            imageView.getLayoutParams().height = ((screen_width * finalWidth) / screen_height)+100;
                       // }


                        ViewTreeObserver vto = imageView.getViewTreeObserver();
                        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                                int finalHeight = imageView.getMeasuredHeight();
                                int  finalWidth = imageView.getMeasuredWidth();
                               // imageView.getLayoutParams().height=finalHeight;
                                return true;
                            }
                        });


	    	            imageView.setAnimation(visible);

	                 //   imageView.setImageBitmap(bitmap);
	                } else {
	                    imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.img_logo));
	                }

	            }

	        }

	        dataload.setVisibility(View.INVISIBLE);

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
