package bisnis.com.official;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bisnis.com.official.R;

import singelton.Singelton_List_Berita;

import model.Frame_ListBerita;
import koneksi.Json_Connector;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

public class    StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
  //  private static final int mCount = 10;
    public ArrayList<Frame_ListBerita> mWidgetItems = new ArrayList<Frame_ListBerita>();
    private Context mContext;
    public int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
    	
      //  for (int i = 0; i < mCount; i++) {
        //    mWidgetItems.add(new WidgetItem(i + "!"));
        //}

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the StackWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      	mWidgetItems.clear();
  	  Json_Connector con =  new Json_Connector();
      try {
			String asd=  new DataDownloaderOfWidget(null).execute(con.URL+"breaking/1/0/10").get();
			System.out.println("DATDATDATDATD : "+asd );
      	JSONArray jsonarray;
			try {
				jsonarray = new JSONArray(asd);
				for (int i = 0; i < 10; i++) {
					JSONObject obj = jsonarray.getJSONObject(i);
					JSONObject obj2 = obj.getJSONObject("date");
					String [] split =obj.getString("category_link").split("-");
					 split [0]=Character.toUpperCase(split [0].charAt(0)) + split [0].substring(1);
					 String sub_canal = split [0];//+" "+split [1];
					 for (int j = 1; j < split.length; j++) {
						 split [j]=Character.toUpperCase(split [j].charAt(0)) + split [j].substring(1);
						 sub_canal += " "+split [j] ;
					}
					 mWidgetItems.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=100", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live"), obj.getString("subtitle")));
				}
				Singelton_List_Berita.getInstance().setArray_widget(mWidgetItems);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
		//		dataload.setContentDescription("abis");
			//	  dataload.setVisibility(View.GONE);   
				e.printStackTrace();
			}
      
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
    	String max_legh=mWidgetItems.get(position).getJudulberita().toString();
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_widget);
       
        if(max_legh.length()>60){
        	max_legh=max_legh+".....";
        }
        
        rv.setTextViewText(R.id.widget_item, max_legh);
        rv.setTextViewText(R.id.widget_tgl, mWidgetItems.get(position).getTglberita() +" "+mWidgetItems.get(position).getPukul());
        
        Bitmap bitmap = null;
     

        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
        	bitmap = new ImageDownloaderTaksWidget().execute(mWidgetItems.get(position).getImgberita()).get();
            Thread.sleep(500);
            System.out.println("Loading view " + position);
            rv.setImageViewBitmap(R.id.img_widget_stack, bitmap);
            Bundle extras = new Bundle();
            extras.putInt(StackWidgetProvider.EXTRA_ITEM, Integer.parseInt(mWidgetItems.get(position).getIdberita()));
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.widget_click, fillInIntent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
     
        // Return the remote views object.
        
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    	 System.out.println("DATDATDATDTADTATDATDATDTADTATDATDTADTATDATDTADTDTDATDAT");

         // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
         // for example downloading or creating content etc, should be deferred to onDataSetChanged()
         // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
     	mWidgetItems.clear();
     	  Json_Connector con =  new Json_Connector();
         try {
 			String asd=  new DataDownloaderOfWidget(null).execute(con.URL+"breaking/1/0/10").get();
 			System.out.println("DATDATDATDATD : "+asd );
         	JSONArray jsonarray;
 			try {
 				jsonarray = new JSONArray(asd);
 				for (int i = 0; i < 10; i++) {
 					JSONObject obj = jsonarray.getJSONObject(i);
 					JSONObject obj2 = obj.getJSONObject("date");
 					String [] split =obj.getString("category_link").split("-");
 					 split [0]=Character.toUpperCase(split [0].charAt(0)) + split [0].substring(1);
 					 String sub_canal = split [0];//+" "+split [1];
 					 for (int j = 1; j < split.length; j++) {
 						 split [j]=Character.toUpperCase(split [j].charAt(0)) + split [j].substring(1);
 						 sub_canal += " "+split [j] ;
 					}
 					 mWidgetItems.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=200", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live"), obj.getString("subtitle")));
 					Singelton_List_Berita.getInstance().setArray_widget(mWidgetItems);
 				}
 			} catch (JSONException e) {
 				// TODO Auto-generated catch block
 		//		dataload.setContentDescription("abis");
 			//	  dataload.setVisibility(View.GONE);   
 				e.printStackTrace();
 			}
         
 		} catch (InterruptedException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		} catch (ExecutionException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
     	
       //  for (int i = 0; i < mCount; i++) {
         //    mWidgetItems.add(new WidgetItem(i + "!"));
         //}

         // We sleep for 3 seconds here to show how the empty view appears in the interim.
         // The empty view is set in the StackWidgetProvider and should be a sibling of the
         // collection view.
         try {
             Thread.sleep(3000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     
    	
    }
}