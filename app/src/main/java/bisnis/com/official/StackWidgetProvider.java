package bisnis.com.official;

import java.util.Random;

import bisnis.com.official.R;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class StackWidgetProvider extends AppWidgetProvider {
	public static final String TAG = "APP_WIDGET";
	public static String ACTION_WIDGET_RANDOM = "ActionReceiverRandom";
	public static String ACTION_WIDGET_RANDOM2 = "ActionReceiverRandom2";
	
	 public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
	    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
	    public static final String EXTRA_ITEM2 = "com.example.android.stackwidget.EXTRA_ITEM2";
		//public ArrayList<Frame_ListBerita> data;
		 public static int id_widget;
		   	
    public static RemoteViews rview;
		@Override
		public void onUpdate(Context paramContext, AppWidgetManager appWidgetManager, 
				int[] appWidgetIds){
			//Toast.makeText(paramContext, "On update ::", Toast.LENGTH_SHORT).show();
			for (int i = 0; i < appWidgetIds.length; i++) {
				id_widget=appWidgetIds[i];
			}
			updateWidgetState(paramContext, "");	
		}
		@Override
		public void onReceive(Context paramContext, Intent paramIntent)
		  {
			//Intent
			 String str = paramIntent.getAction();
			 //Toast.makeText(paramContext, "onrecive ::"+str, Toast.LENGTH_SHORT).show();
			 if(paramIntent.getAction().equals(ACTION_WIDGET_RANDOM2)){
				  	int viewIndex = paramIntent.getIntExtra(EXTRA_ITEM, 0);
		         // Toast.makeText(paramContext, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
		            Intent toastIntent = new Intent(paramContext, Activity_Detail_Widget.class);
		            toastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		            toastIntent.putExtra("DATA", (viewIndex));
		            paramContext.startActivity(toastIntent);   
		          //  Toast.makeText(paramContext, "Start Activity"+(viewIndex+1), Toast.LENGTH_SHORT).show();
			 }
			if (paramIntent.getAction().equals(ACTION_WIDGET_RANDOM)) {
		        updateWidgetState(paramContext, str);   
			}
			else
			{
					if ("android.appwidget.action.APPWIDGET_DELETED".equals(str))
				      {
				        int i = paramIntent.getExtras().getInt("appWidgetId", 0);
				        if (i == 0)
				        {
				        	
				        }
				        else
				        {
					        int[] arrayOfInt = new int[1];
					        arrayOfInt[0] = i;
					        onDeleted(paramContext, arrayOfInt);
				        }
				      }
		      super.onReceive(paramContext, paramIntent);
			}
		  }
		 static void updateWidgetState(Context paramContext, String paramString)
		  {
		    RemoteViews localRemoteViews = buildUpdate(paramContext, paramString);
		    ComponentName localComponentName = new ComponentName(paramContext, StackWidgetProvider.class);
		    AppWidgetManager.getInstance(paramContext).updateAppWidget(localComponentName, localRemoteViews);
		  }
		 @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		private static RemoteViews buildUpdate(Context context, String paramString)
		  {
			
	        rview = new RemoteViews(context.getPackageName(), R.layout.widget_stack);
	        Intent intent = new Intent(context, StackWidgetService.class);
           // intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,id_widget);
        //    id_widget=appWidgetIds[i];
            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        	RemoteViews  rv = new RemoteViews(context.getPackageName(), R.layout.widget_stack);
        	rview.setRemoteAdapter(R.id.stack_view, intent);
        
            // The empty view is displayed when the collection has no items. It should be a sibling
            // of the collection view.
        	rview.setEmptyView(R.id.stack_view, R.id.prosesdata_widget);

            // Here we setup the a pending intent template. Individuals items of a collection
            // cannot setup their own pending intents, instead, the collection as a whole can
            // setup a pending intent template, and the individual items can set a fillInIntent
            // to create unique before on an item to item basis.
            Intent toastIntent = new Intent(context, StackWidgetProvider.class);
            toastIntent.setAction(ACTION_WIDGET_RANDOM2);
          //rview  toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rview.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
            toastIntent.setAction(ACTION_WIDGET_RANDOM);
        	PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, 0);
        	rview.setOnClickPendingIntent(R.id.cmd_refresh_widget, actionPendingIntent);
        	// Toast.makeText(context, "New Number Generated", Toast.LENGTH_SHORT).show();
        	 if(paramString.equals(ACTION_WIDGET_RANDOM))
		    	{
        		 AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        		 appWidgetManager.notifyAppWidgetViewDataChanged(id_widget, R.id.stack_view);
        		 rview.setViewVisibility(R.id.stack_view, View.GONE);
        		 rview.setViewVisibility(R.id.prosesdata_widget, View.VISIBLE);
        	

		    	}else if(paramString.equals(ACTION_WIDGET_RANDOM)){
		    	   // Toast.makeText(context, "Start Activity", Toast.LENGTH_SHORT).show();
		    	}

		   return rview;
		  }
		@Override
		public void onEnabled(Context context){
			super.onEnabled(context);
		}
		@Override
		public void onDeleted(Context context, int [] appWidgetId){
			super.onDeleted(context, appWidgetId);
		}
		@Override
	    public void onDisabled(Context context) {
	        super.onDisabled(context);
	    }
}
