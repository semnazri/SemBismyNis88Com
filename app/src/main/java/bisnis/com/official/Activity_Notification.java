package bisnis.com.official;
import bisnis.com.official.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Activity_Notification extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notification_main);
  }

  public void createNotification(View view) {
    // Prepare intent which is triggered if the
    // notification is selected
    Intent intent = new Intent(this, Notification_Recive.class);
    intent.putExtra("KEY", "WKWKWKWKWWK");
    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    // Build notification
    // Actions are just fake
    Notification noti = new Notification.Builder(this)
        .setContentTitle("Hari Ini Kita lIbur")
        .setContentText("INdonesia Libuir Besok karena adanya saran Cipulir").setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pIntent)
        .addAction(R.drawable.ic_launcher, "Call", pIntent)
        .addAction(R.drawable.ic_launcher, "More", pIntent)
        .addAction(R.drawable.ic_launcher, "And more", pIntent).build();
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // hide the notification after its selected
    noti.flags |= Notification.FLAG_AUTO_CANCEL;

    notificationManager.notify(0, noti);

  }
} 