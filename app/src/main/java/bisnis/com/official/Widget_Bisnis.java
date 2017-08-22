package bisnis.com.official;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import bisnis.com.official.R;
 

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
 
public class Widget_Bisnis extends AppWidgetProvider {
  DateFormat df = new SimpleDateFormat("hh:mm:ss");
 
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;
 
    Log.i("ExampleWidget",  "Updating widgets " + Arrays.asList(appWidgetIds));
 
    // Perform this loop procedure for each App Widget that belongs to this
    // provider
    
    ComponentName thiswidget = new ComponentName(context,Widget_Bisnis.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thiswidget);
    
    // Build the intent to call the service
    Intent intent = new Intent(context.getApplicationContext(),
        Widget_Service.class);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

    // Update the widgets via the service
    context.startService(intent);
    
    
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
 
      // Create an Intent to launch ExampleActivity
      //Intent intent = new Intent(context, SpashScreen.class);
      //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
      
     // Intent intent = new Intent(context, Widget_Bisnis.class);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    		  
    // Get the layout for the App Widget and attach an on-click listener
      // to the button
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_bisnis);
      views.setOnClickPendingIntent(R.id.button_widget, pendingIntent);
 
      // To update a label
      views.setTextViewText(R.id.widget1label,"Timer Down"+df.format(new Date()));
 
      // Tell the AppWidgetManager to perform an update on the current app
      // widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}