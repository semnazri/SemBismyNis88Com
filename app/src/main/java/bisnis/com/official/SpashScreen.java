package bisnis.com.official;

/**
 * Riki Setiyawan
 * Email: rzgonz@gmail.com
 *
 * @copyright 2014
 * PT. Bisnis Indonesia Sibertama
 */

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gcm.GCMRegistrar;

import java.util.regex.Pattern;

import POJO.VersionControl;
import db.Table_List_Nav;
import db.Table_Setting;
import model.Frame_nav;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static bisnis.com.official.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static bisnis.com.official.CommonUtilities.EXTRA_MESSAGE;
import static bisnis.com.official.CommonUtilities.SENDER_ID;

public class SpashScreen extends Activity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static String possibleEmail, mPhoneNumber;
    private final int SPLASH_DISPLAY_LENGHT = 900;
    /**
     * Receiving push messages
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Releasing wake lock
            WakeLocker.release();
        }
    };
    //Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask, VersionControl;
    private String tag_json_obj = "jobj_req";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.spashscreen);

        if (checkPermission()) {
            //TODO ya urus buat baca data
            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNumber = tMgr.getSimSerialNumber();

        } else {
            requestPermission();
        }

        String[] nav_title = getResources().getStringArray(R.array.nav_name);
        String[] nav_id = getResources().getStringArray(R.array.canal_id);
        TypedArray nav_icon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        String[] canal = getResources().getStringArray(R.array.nav_name);

        Table_List_Nav db = new Table_List_Nav(this, null, null, 0);

        if (db.get_all_nav().isEmpty()) {
            for (int i = 7; i < canal.length - 1; i++) {
                db.add_nav(new Frame_nav(nav_id[i], nav_title[i], nav_icon.getResourceId(i, -1), i, 0));
            }
        } else {
            for (int i = 7; i < canal.length - 1; i++) {
                db.update_nav_icon(new Frame_nav(nav_id[i], nav_title[i], nav_icon.getResourceId(i, -1), i, 0));
            }
        }
        db.close();

        Table_Setting db2 = new Table_Setting(this, null, null, 0);
        db2.add_setting("1", "1", "1", "1");
        db2.close();

        nav_icon.recycle();
        /* New Handler to start the Menu-Activity
         *aaaa
         *
         * and close this Splash-Screen after some seconds.*/
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
            }
        }


        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);


        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
            //Toast.makeText(getApplicationContext(), "Kampret", Toast.LENGTH_LONG).show();
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                //Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, mPhoneNumber, possibleEmail, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }

//        checkVersionCOntrol();
        mulai();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

    }


    private void mulai() {


        // TODO Auto-generated method stub
//        String VersionControl = getResources().getString(R.string.version_name);
        if (hasConnection()) {

            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(SPLASH_DISPLAY_LENGHT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        checkVersionCOntrol();
//                        Intent mainIntent = new Intent(getApplication(), Home3.class);
//                        mainIntent.putExtra("NAV_ID", 0);
//                        mainIntent.putExtra("NAV", 0);
//                        mainIntent.putExtra("SUBCANAL_COUNT", 5);
//                        startActivity(mainIntent);
//                        finish();

                    }
                }
            };
            timerThread.start();


        } else {
            new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
                    .setTitle("Warning")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            mulai();
                        }
                    })
                    .show();

        }
    }

    private void checkVersionCOntrol() {
        String url_version = "http://services.bisnis.com/json/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_version)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final POJO.VersionControl service = retrofit.create(VersionControl.class);
        Call<model.VersionControl> call = service.getVersionControl();
        call.enqueue(new Callback<model.VersionControl>() {
            @Override
            public void onResponse(Call<model.VersionControl> call, retrofit2.Response<model.VersionControl> response) {
                String versionName = "";
//                    int versionCode = -1;
                try {
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    versionName = packageInfo.versionName;

//                        versionCode = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                String version = response.body().getVersion();
                Log.d("version__", versionName);
                Log.d("versi server", version);

                if (version.equals(versionName)) {
                    Log.d("if", "if");

                    mulailah();
//                        Toast.makeText(SpashScreen.this, version, Toast.LENGTH_SHORT).show();

                } else {

                    update();
                    Log.d("else", "else");

                }


            }

            @Override
            public void onFailure(Call<model.VersionControl> call, Throwable t) {

            }
        });
    }

    private void mulailah() {

        Intent mainIntent = new Intent(getApplication(), Home3.class);
        mainIntent.putExtra("NAV_ID", 0);
        mainIntent.putExtra("NAV", 0);
        mainIntent.putExtra("SUBCANAL_COUNT", 5);
        startActivity(mainIntent);
        finish();

    }

    private void update() {

        new AlertDialog.Builder(SpashScreen.this)
                .setIcon(R.drawable.alert)
                .setTitle("Please Update")
                .setMessage("New Version has Avaliable! Please update, to enjoy the newest features")
                .setPositiveButton("Update Now!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int wich) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=bisnis.com.official")));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=bisnis.com.official")));
                                }
                            }
                        })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int wich) {
                        mulailah();
                    }
                }).show();

    }

    private void getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    public boolean hasConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
}
