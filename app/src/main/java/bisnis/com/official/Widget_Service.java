package bisnis.com.official;

import java.util.Random;

import bisnis.com.official.R;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class Widget_Service  extends Service{
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
		        .getApplicationContext());

		    int[] allWidgetIds = intent
		        .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		    ComponentName thisWidget = new ComponentName(getApplicationContext(),Widget_Bisnis.class);
		    int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		    for (int widgetId : allWidgetIds) {
		      // create some random data
		      int number = (new Random().nextInt(100));

		      RemoteViews remoteViews = new RemoteViews(this
		          .getApplicationContext().getPackageName(),
		          R.layout.widget_bisnis);
		      // Set the text
		      remoteViews.setTextViewText(R.id.widget1label,
		          "Random: " + String.valueOf(number));

		      // Register an onClickListener
		      Intent clickIntent = new Intent(this.getApplicationContext(),
		          Widget_Bisnis.class);

		      clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		      clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
		          allWidgetIds);

		      PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
		          PendingIntent.FLAG_UPDATE_CURRENT);
		      remoteViews.setOnClickPendingIntent(R.id.button_widget, pendingIntent);
		      appWidgetManager.updateAppWidget(widgetId, remoteViews);
		    }
		    stopSelf();

		    super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
