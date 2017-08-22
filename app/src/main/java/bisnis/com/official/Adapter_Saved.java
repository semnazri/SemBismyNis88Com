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

//import com.google.android.gms.internal.da;

import bisnis.com.official.R;
import bisnis.com.official.Adapter_List_Berita.AsyncDrawable;
import bisnis.com.official.Adapter_List_Berita.BitmapWorkerTask;
import model.Frame_ListBerita;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter_Saved extends ArrayAdapter<Frame_ListBerita>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Frame_ListBerita> item = new ArrayList<Frame_ListBerita>();
	private ArrayList<String> item_delete = new ArrayList<String>();
	//private ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	ImageView imagecontoh;
	File sdCardDirectory = Environment.getExternalStorageDirectory();
	private int currentapiVersion = android.os.Build.VERSION.SDK_INT;
	private WeakReference layout_delete;
    
	
	public Adapter_Saved(Context context, int layoutResourceId,ArrayList<Frame_ListBerita> objects, ArrayList<String> data_delete, RelativeLayout layout_deletemode) {
		super(context, layoutResourceId, objects);
		// TODO Auto-generated constructor stub
		
		item.clear();
		this.context = context;
		this.layoutResourceId=layoutResourceId;
		this.item= objects;
		this.item_delete = data_delete;
		this.layout_delete = new WeakReference(layout_deletemode);
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
		
		@SuppressLint("NewApi")
		public void loadBitmap(int resId, ImageView imageView,String string) {
			if (cancelPotentialWork(resId, imageView)) {
				final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
				//imageView.setBackgroundResource(R.drawable.ic_drawer);
				if (currentapiVersion < android.os.Build.VERSION_CODES.HONEYCOMB){
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
				final Bitmap bitmap = decodeSampledBitmapFromResource2(params[0], data,100,100);
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
	public View getView(final int position, final View convertView, ViewGroup parent){
		
		View row = convertView;
		BeritaHolder holder = new BeritaHolder();
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    	row = inflater.inflate(layoutResourceId, parent,false);
    	assert row != null;
    	holder.imgberita =(ImageView)row.findViewById(R.id.imgfitured);
    	holder.txtdate = (TextView)row.findViewById(R.id.txtdate);
    	holder.txtjudul = (TextView)row.findViewById(R.id.txtjudul);
    	holder.txtminut = (TextView)row.findViewById(R.id.txtminut);
    	holder.txtminut	= 	(TextView)row.findViewById(R.id.txtminut);
    	holder.cek_delete = (CheckBox)row.findViewById(R.id.check_delete);
    	
    	final RelativeLayout delete = (RelativeLayout)layout_delete.get();
    	
    	if(item_delete.get(position).equals("NO")){
    		holder.cek_delete.setChecked(false);
    	}else{
    		holder.cek_delete.setChecked(true);
    	}

        if(delete.getContentDescription().toString().equals("true")){
            holder.cek_delete.setVisibility(View.VISIBLE);
        }
    	
    	holder.cek_delete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				delete.setVisibility(View.VISIBLE);
				if(isChecked){
					item_delete.set(position, "YES");
				}else{
					item_delete.set(position, "NO");
				}
			//Toast.makeText(getContext(), ""+item_delete.get(position), Toast.LENGTH_SHORT).show();	
				
			}
		});
    	
    	row.setTag(holder);
    	Frame_ListBerita berita = item.get(position);
    	holder.txtdate.setText(berita.getTglberita() + " "+ berita.getPukul());
        Spannable spanna2 = new SpannableString(holder.txtdate.getText());
        spanna2.setSpan(new BackgroundColorSpan(0xCCffc208),0, holder.txtdate.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtdate.setText(spanna2);


        holder.txtdate.setTypeface(Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf"));




    	holder.txtjudul.setText(berita.getJudulberita());
        Spannable spanna = new SpannableString(holder.txtjudul.getText());
        spanna.setSpan(new BackgroundColorSpan(0xCCffc208),0, holder.txtjudul.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtjudul.setText(spanna);
        holder.txtjudul.setTypeface(Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf"));
    	File image2 = new File(sdCardDirectory+"/BIS",berita.getImage_content()+".BIS");
    	//LinkedHashSet<Bitmap> listToSet = new LinkedHashSet<Bitmap>(imagelist);
        
        //Creating Arraylist without duplicate values
      //  List<Bitmap> listWithoutDuplicates = new ArrayList<Bitmap>(listToSet);
    		// if(imagelist.isEmpty()){
    		 if(!ceknamaimage(berita.getImage_content()+".BIS")){
    			 new ImageDownloaderTaks(holder.imgberita,null,berita.getImage_content()).execute(berita.getImgberita());
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
    			 
    			// Bitmap myBitmap = BitmapFactory.decodeFile(image2.getAbsolutePath());
	             //System.out.println(image2.getAbsolutePath());
	             //holder.imgberita.setImageBitmap(myBitmap);
	             loadBitmap(position, holder.imgberita, image2.getAbsolutePath());
    		// holder.imgberita.setImageBitmap(imagelist.get(position));
    		 }
    		
    	row.setContentDescription("Normal");
    	row.setId(Integer.parseInt(berita.getIdberita()));


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
		File ceknama = new File(Environment.getExternalStorageDirectory()+"/BIS",path);
		if(!ceknama.exists()){
			Log.e("TravellerLog :: ", "gak ada");
			return false;
		}else{
			
			Log.e("TravellerLog :: ", "Ada");
			
			return true;
			}
	}

	static class BeritaHolder{
		ImageView imgberita;
		TextView txtjudul,txtdate,txtminut;
		CheckBox cek_delete;
	}
	


}
