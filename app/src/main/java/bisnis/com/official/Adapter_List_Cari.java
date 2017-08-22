package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import db.Table_Setting;
import model.Frame_ListBerita;
import bisnis.com.official.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Adapter_List_Cari extends ArrayAdapter<Frame_ListBerita>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Frame_ListBerita> item = new ArrayList<Frame_ListBerita>();
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	ImageView imagecontoh;
	File sdCardDirectory = Environment.getExternalStorageDirectory();
	private int currentapiVersion = Build.VERSION.SDK_INT;
    
	
	public Adapter_List_Cari(Context context, int layoutResourceId,ArrayList<Frame_ListBerita> objects) {
		super(context, layoutResourceId, objects);
		// TODO Auto-generated constructor stub
		item.clear();
		this.context = context;
		this.layoutResourceId=layoutResourceId;
		this.item= objects;
	}
	
	  @Override
		public int getCount() {
			return item.size();
		}

		@Override
		public Frame_ListBerita getItem(int arg0) {
			return item.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		
		public void loadBitmap(int resId, ImageView imageView,String string) {
			if (cancelPotentialWork(resId, imageView)) {
				final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
				//imageView.setBackgroundResource(R.drawable.ic_drawer);
				if (currentapiVersion < Build.VERSION_CODES.HONEYCOMB){
           		    // Do something for froyo and above versions
					task.execute(string);
           		} else{
           		    // do something for phones running an SDK before froyo
           			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,string);
           		}
			
			}
		}
		
		static class AsyncDrawable extends BitmapDrawable {
			private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

			public AsyncDrawable(Resources res, Bitmap bitmap,
					BitmapWorkerTask bitmapWorkerTask) {
				super(res, bitmap);
				bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
						bitmapWorkerTask);
			}

			public BitmapWorkerTask getBitmapWorkerTask() {
				return bitmapWorkerTaskReference.get();
			}
		}
		
		
		public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				//mMemoryCache.put(key, bitmap);
			}
		}

		public Bitmap getBitmapFromMemCache(String key) {
			return null;
			//return (Bitmap) mMemoryCache.get(key);
		}

		class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
			public int data;
			private final WeakReference<ImageView> imageViewReference;

			public BitmapWorkerTask(ImageView imageView) {
				// Use a WeakReference to ensure the ImageView can be garbage
				// collected
				imageViewReference = new WeakReference<ImageView>(imageView);
			}

			// Decode image in background.
			@Override
			protected Bitmap doInBackground(String... params) {
				//data = params[0];
			//	final Bitmap bitmap = decodeSampledBitmapFromResource(img_path.get(data).toString(), 200, 200);
				System.out.println("CACAC :"+data);
				 Bitmap bitmap ;
				if(params[0].endsWith("0")){
					bitmap = decodeSampledBitmapFromResource2(params[0], data, 250,250);
				}else
				{
					bitmap = decodeSampledBitmapFromResource2(params[0], data, 150,150);
				}
			
			//	addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
				return bitmap;
			}

			// Once complete, see if ImageView is still around and set bitmap.
			@Override
			protected void onPostExecute(Bitmap bitmap) {
				if (imageViewReference != null && bitmap != null) {
					final ImageView imageView = imageViewReference.get();
					if (imageView != null) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		}
		
		private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
			if (imageView != null) {
				final Drawable drawable = imageView.getDrawable();
				if (drawable instanceof AsyncDrawable) {
					final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
					return asyncDrawable.getBitmapWorkerTask();
				}
			}
			return null;
		}
		
		public static boolean cancelPotentialWork(int data, ImageView imageView) {
			final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

			if (bitmapWorkerTask != null) {
				final int bitmapData = bitmapWorkerTask.data;
				if (bitmapData != data) {
					// Cancel previous task
					bitmapWorkerTask.cancel(true);
				} else {
					// The same work is already in progress
					return false;
				}
			}
			// No task associated with the ImageView, or an existing task was
			// cancelled
			return true;
		}
		
		public static Bitmap decodeSampledBitmapFromResource2(String string,
				int resId, int reqWidth, int reqHeight) {

			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
		//	BitmapFactory.decodeResource(string, resId, options);
			BitmapFactory.decodeFile(string, options);
			

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			//return BitmapFactory.decodeResource(string, resId, options);
			return BitmapFactory.decodeFile(string, options);
		}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		BeritaHolder holder = new BeritaHolder();
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    	row = inflater.inflate(layoutResourceId, parent,false);
    	assert row != null;

        Table_Setting db = new Table_Setting(getContext(), null, null, 0);
        int size_font = db.get_setting_font_size();
        db.close();
        switch (size_font) {
            case 0:
                size_font = 16;
                break;
            case 1:
                size_font = 18;
                break;
            case 2:
                size_font = 20;
                break;
            case 3:
                size_font = 22;
                break;

            default:
                break;
        }
    	holder.imgberita =(ImageView)row.findViewById(R.id.imgfitured);
    	holder.imgberita2 =(ImageView)row.findViewById(R.id.imgfitured2);
    	holder.txtdate = (TextView)row.findViewById(R.id.txtdate);
    	holder.txtjudul = (TextView)row.findViewById(R.id.txtjudul);
    	holder.txtdate2 = (TextView)row.findViewById(R.id.txtdate2);
    	holder.txtjudul2 = (TextView)row.findViewById(R.id.txtjudul2);
    	holder.linerberita= (LinearLayout)row.findViewById(R.id.LinierBerita);
    	holder.txtminut = (TextView)row.findViewById(R.id.txtminut);
    	holder.txtminut	= 	(TextView)row.findViewById(R.id.txtminut);
    	holder.row_bates=(TableRow)row.findViewById(R.id.tableRow3);
    	holder.txt_canal_subcanal = (TextView)row.findViewById(R.id.txt_canal_subcanal);
    	holder.table_headline = (TableRow)row.findViewById(R.id.table_headline);
    	holder.img_live = (ImageView)row.findViewById(R.id.img_live_home);
    	holder.img_live2 = (ImageView)row.findViewById(R.id.img_live_home2);
 
    	row.setTag(holder);
    	Frame_ListBerita berita = item.get(position);
    	holder.txtdate.setText(berita.getDatepost());
    	holder.txtjudul.setText(berita.getJudulberita());
    	if(item.get(position).getLive().equals("0")){
    		holder.img_live.setVisibility(View.GONE);
    	}
    	if(position!=-1){
		holder.imgberita.setVisibility(View.GONE);
		holder.txtdate.setVisibility(View.GONE);
		holder.table_headline.setVisibility(View.GONE);
		holder.row_bates.setVisibility(View.GONE);
		holder.linerberita.setVisibility(View.VISIBLE);
		String a = Html.fromHtml(berita.getParent_category()).toString();
		holder.txt_canal_subcanal.setText(a);
		holder.txtdate2.setText(berita.getTglberita());
		if(item.get(position).getLive().equals("0")){
    		holder.img_live2.setVisibility(View.GONE);
    	 	holder.txtjudul2.setText(berita.getJudulberita());
    	}else{
    		holder.txtjudul2.setText(""+berita.getJudulberita());
    	}
   
    	holder.txtminut.setText(berita.getPukul());

            Typeface open_sans= Typeface.createFromAsset(getContext().getAssets(),"OpenSans-Regular.ttf");
            holder.txtminut.setTypeface(open_sans);
            holder.txtjudul2.setTypeface(open_sans);
            holder.txtjudul2.setTextSize(size_font);

    	}
    	
    	File image2 = new File(sdCardDirectory+"/Bisniscom",berita.getImage_content()+".cache");
    	//LinkedHashSet<Bitmap> listToSet = new LinkedHashSet<Bitmap>(imagelist);
        
        //Creating Arraylist without duplicate values
      //  List<Bitmap> listWithoutDuplicates = new ArrayList<Bitmap>(listToSet);

    	//holder.imgberita.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_photos));
    	 if(position==0){    		
    		// if(imagelist.isEmpty()){
    		 if(!ceknamaimage(berita.getImage_content()+".cache")){
    			 if (currentapiVersion < Build.VERSION_CODES.HONEYCOMB){
            		    // Do something for froyo and above versions
    			//	 new ImageDownloaderTaks(holder.imgberita,null,berita.getImage_content()).execute(berita.getImgberita());
            		} else{
            		    // do something for phones running an SDK before froyo
            	//		 new ImageDownloaderTaks(holder.imgberita,null,berita.getImage_content()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.getImgberita());
            		}
    			
    			 /*
        		 try {
					imagelist.add(new ImageDownloaderTaks(holder.imgberita).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.imgberita).get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();			
					
				}
        */
    		 }else{
    			// loadBitmap(position, holder.imgberita,image2.getAbsolutePath());
    			 //holder.imgberita.setImageBitmap(decodeSampledBitmapFromResource(image2.getAbsolutePath(), 150, 150));
    			// Bitmap myBitmap = BitmapFactory.decodeFile(image2.getAbsolutePath());
	            // System.out.println(image2.getAbsolutePath());
	          ///   holder.imgberita.setImageBitmap(myBitmap);
    		// holder.imgberita.setImageBitmap(imagelist.get(position));
    		 }
    		
    	 }
    	 else{
    		 
    		 if(!ceknamaimage(berita.getImage_content()+".cache")){
    			 if (currentapiVersion < Build.VERSION_CODES.HONEYCOMB){
         		    // Do something for froyo and above versions
 			//	 new ImageDownloaderTaks(holder.imgberita2,null,berita.getImage_content()).execute(berita.getImgberita());
         		} else{
         	//	    // do something for phones running an SDK before froyo
         	//		 new ImageDownloaderTaks(holder.imgberita2,null,berita.getImage_content()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.getImgberita());
         		}
    		// new ImageDownloaderTaks(holder.imgberita2,null,berita.image_content).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.imgberita);
    	
    		 /*
    			 try {
 					imagelist.add(new ImageDownloaderTaks(holder.imgberita2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,berita.imgberita).get());
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (ExecutionException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();			
 					
 				}
 		*/
        	// holder.imgberita2.setImageBitmap(imagelist.get(position));
    		 }
    		 else{
    			// final BitmapFactory.Options options = new BitmapFactory.Options();
    			 //  options.inSampleSize = 4;
    			//   options.inJustDecodeBounds = true;
    			// Bitmap myBitmap = BitmapFactory.decodeFile(image2.getAbsolutePath(),options);
	            // System.out.println(image2.getAbsolutePath());
	           //  holder.imgberita2.setImageBitmap(myBitmap);
	            // myBitmap.recycle();
    		//	 loadBitmap(position, holder.imgberita2,image2.getAbsolutePath());
    			// holder.imgberita2.setImageBitmap(decodeSampledBitmapFromResource(image2.getAbsolutePath(), 100, 100));
    		 }
    		
            
    		 
    	 }
        Animation visible = AnimationUtils.loadAnimation(this.context, R.anim.visable);
        row.setAnimation(visible);


    	 
		return row;	
	}
	public static Bitmap decodeSampledBitmapFromResource(String res,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(res, options);
	    //(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	   // return BitmapFactory.decodeResource(res, resId, options);
	    return BitmapFactory.decodeFile(res, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 9;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}


	private boolean ceknamaimage(String path) {
		// TODO Auto-generated method stub
		File ceknama = new File(Environment.getExternalStorageDirectory()+"/Bisniscom",path);
		if(!ceknama.exists()){
			Log.e("TravellerLog :: ", "gak ada");
			return false;
		}else{
			
			Log.e("TravellerLog :: ", "Ada");
			
			return true;
			}
	}

	static class BeritaHolder{
		ImageView imgberita,imgberita2,img_live,img_live2;
		TextView txtjudul,txtdate,txtjudul2,txtdate2,txtminut,txt_canal_subcanal;
		LinearLayout linerberita;
		TableRow row_bates,table_headline;
		
	}
	


}
