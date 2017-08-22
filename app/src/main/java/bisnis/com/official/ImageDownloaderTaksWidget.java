package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import bisnis.com.official.R;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.BlurMaskFilter;
import android.graphics.PorterDuff;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloaderTaksWidget extends AsyncTask<String, Void, Bitmap> {
private String position;
//ArrayList<Bitmap>  imageView2 ;
private boolean success=false;
File sdCardDirectory;
//private static ArrayList<Bitmap> arraylistheadline2 = new ArrayList<Bitmap>();

	 
	@Override
	protected void onPreExecute(){
		//imageView2.add(bm);
		sdCardDirectory = Environment.getExternalStorageDirectory();
        createDirIfNotExists("/Bisniscom");

	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		 params[0]=params[0].replaceAll(" ", "%20");
		return downloadBitmap(params[0]);
		
	}
	 
	@Override
	protected void onPostExecute(Bitmap bitmap){
		
		 if (isCancelled()) {
	       //     bitmap = null;
	        }
	   
	     
	}
private boolean createDirIfNotExists(String path) {
		// TODO Auto-generated method stub
	boolean ret = true;

    File file = new File(Environment.getExternalStorageDirectory(), path);
    if (!file.exists()) {
        if (!file.mkdirs()) {
            Log.e("TravellerLog :: ", "Problem creating Image folder");
            ret = false;
        }
    }
    return ret;
		
	}

private static ImageDownloaderTaksWidget getBitmapDownloaderTask(ImageView imageView) {
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
    private final WeakReference<ImageDownloaderTaksWidget> bitmapDownloaderTaskReference;

    public DownloadedDrawable(ImageDownloaderTaksWidget bitmapDownloaderTask) {
        super(Color.WHITE);
        bitmapDownloaderTaskReference =
            new WeakReference<ImageDownloaderTaksWidget>(bitmapDownloaderTask);
    }

    public ImageDownloaderTaksWidget getBitmapDownloaderTask() {
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
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
            .getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff000000;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    final RectF rectF = new RectF(rect);
    final float roundPx = pixels;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
  //  canvas.drawBitmap(bitmap, rect, rect, paint);
    
    // setup default color
    canvas.drawColor(0, Mode.CLEAR);
    // create a blur paint for capturing alpha
    Paint ptBlur = new Paint();
    ptBlur.setMaskFilter(new BlurMaskFilter(18, Blur.NORMAL));
    int[] offsetXY = new int[2];
    // capture alpha into a bitmap
    Bitmap bmAlpha = bitmap.extractAlpha(ptBlur, offsetXY);
    // create a color paint
    Paint ptAlphaColor = new Paint();
    ptAlphaColor.setColor(color);
    // paint color for captured alpha region (bitmap)
    canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
    // free memory
    canvas.drawBitmap(bitmap, rect, rect, paint);
    bmAlpha.recycle();
   

    
    return output;
}
}


