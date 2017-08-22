package bisnis.com.official;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import java.util.ArrayList;

import db.Table_Setting;
import model.Frame_ListBerita;

import static bisnis.com.official.CommonUtilities.SENDER_ID;
import static bisnis.com.official.CommonUtilities.displayMessage;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class GCMIntentService extends GCMBaseIntentService {


    private static final String TAG = "GCMIntentService";
    String idberita = "268971222";
    String category = "93";
    String datepost = "20141030";
    String imgberita = "http://img.bisnis.com/dynamic/posts/2014/10/30/269022/bukit-asam.jpg?w=300";
    String tglberita = "30/10/2014";
    String pukul = "11:40 WIB";
    String judulberita = "sessage";
    String image_content = "rupiah.jpg";
    String parent = "MARKET";
    String slug = "kurs-rupiah-30-oktober-rupiah-paling-tertekan-di-asean.-simak-prediksi-dan-pergerakan-rupiahus";
    String live = "0";
    String subtitle = "subtitle";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     *
     * @param nAV_ID
     * @param cANAL
     * @param pOST_ID
     * @param lIVE
     * @param tgl
     */
    private static void generateNotification(Context context, String message, Frame_ListBerita data) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
//        Notification notification = new  Notification.Builder(context)
//        .setContentTitle("Bisnis . com ")
//       // .setContentText(message)
//        .setSmallIcon(R.drawable.ic_launcher)
//        //.setLargeIcon(aBitmap)
//        .setStyle(new Notification.BigTextStyle()
//            .bigText(message))
//        .build();

        String title = context.getString(R.string.app_name);
        ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
        Intent notificationIntent = new Intent(context, Activity_Detail_Notification.class);
        // context.startActivity(notificationIntent);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtra("POST_ID",(array_list_berita.get(arg2).getIdberita()));
//		intent.putExtra("CANAL",canal);
//		intent.putExtra("DATA", array_list_berita);
//		intent.putExtra("NAV_ID", getArguments().getInt(CANAL_ID));
        array_list_berita.clear();
        //array_list_berita.add(new Frame_ListBerita("268971","93","20141030","2014/10/30","2014-10-30 11:40: WIB", "hahah","as","rupiah.jpg","Market","rupiah.jpg","0"));
        //	adapter_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=300", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live")));
        //Frame_ListBeritaa a = new Frame_ListBerita(idberita, category, datepost, imgberita, tglberita, pukul, judulberita, image_content, parent, slug, live)
        array_list_berita.add(data);
        notificationIntent.putExtra("POST_ID", data.getIdberita());
        notificationIntent.putExtra("CANAL", data.getCategory());
        notificationIntent.putExtra("DATA", array_list_berita);
        notificationIntent.putExtra("NAV_ID", 0);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        Table_Setting dbSetting = new Table_Setting(context, null, null, 0);
        if (dbSetting.get_setting_font_notive() == 1) {
            notificationManager.notify(0, notification);
        }

        // context.startActivity(notificationIntent);

    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
//        Log.d("NAME", SpashScreen.mPhoneNumber);
        ServerUtilities.register(context, SpashScreen.mPhoneNumber, SpashScreen.possibleEmail, registrationId);
    }

    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = "Berita Bisnis ya di Bisnis.com";

        if (intent.getExtras().getString("price") != null) {
            message = intent.getExtras().getString("price");
            idberita = intent.getExtras().getString("idberita");
            category = intent.getExtras().getString("category");
            datepost = intent.getExtras().getString("datepost");
            imgberita = intent.getExtras().getString("imgberita");
            tglberita = intent.getExtras().getString("tglberita");
            pukul = intent.getExtras().getString("pukul");
            judulberita = intent.getExtras().getString("judulberita");
            image_content = intent.getExtras().getString("image_content");
            parent = intent.getExtras().getString("parent");
            slug = intent.getExtras().getString("slug");
            live = intent.getExtras().getString("live");
            subtitle = intent.getExtras().getString("subtitle");
        }


        displayMessage(context, message);

        // notifies user


        Frame_ListBerita data = new Frame_ListBerita(idberita, category, datepost, imgberita, tglberita, pukul, judulberita, image_content, parent, slug, live, subtitle);

        generateNotification(context, message, data);
    }

    /**
     * Method called on receiving a deleted message
     */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message, null);
    }

    /**
     * Method called on Error
     */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

}
