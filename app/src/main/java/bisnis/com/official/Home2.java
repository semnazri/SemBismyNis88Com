package bisnis.com.official;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bisnis.com.official.AnalyticBisnisApp.TrackerName;
import db.Table_Setting;
import example.FullDrawerLayout;
import koneksi.Json_Connector;
import model.Frame_ListBerita;

//import com.google.android.gms.internal.ca;

public class Home2 extends ActionBarActivity
        implements NavigationDrawerFragment3.NavigationDrawerCallbacks {

    static final String STATE_SCORE = "playerScore";
    static String query;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private static NavigationDrawerFragment3 mNavigationDrawerFragment;
    Bundle act_param;
    boolean Flag_Back = true;
    int font_size, refresh_interfal, nav_setting;
    boolean setting_save = false;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Menu optionsMenu;
    private int refresh_flag;
    private boolean Flag_fragment = false;

    protected static String selisihDateTime(Date waktuSatu, Date waktuDua) {
        long selisihMS = Math.abs(waktuSatu.getTime() - waktuDua.getTime());
        // long selisihDetik = selisihMS / 1000 % 60;
        // long selisihMenit = selisihMS / (60 * 1000) % 60;
        // long selisihJam = selisihMS / (60 * 60 * 1000) % 24;
        long selisihHari = selisihMS / (24 * 60 * 60 * 1000);
        //  String selisih = selisihHari + " hari " + selisihJam + " Jam " + selisihMenit + " Menit " + selisihDetik + " Detik";
        return "" + selisihHari;
    }

    protected static Date konversiStringkeDate(String tanggalDanWaktuStr, String pola) {
        Date tanggalDate = null;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(pola);
        try {
            tanggalDate = formatter.parse(tanggalDanWaktuStr);
        } catch (ParseException ex) {
            //  Logger.getLogger(ContohFormatTanggalWaktu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tanggalDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        act_param = getIntent().getExtras();
        setting_save = false;
        mNavigationDrawerFragment = (NavigationDrawerFragment3) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        Table_Setting db = new Table_Setting(this, null, null, 0);
        font_size = db.get_setting_font_size();
        refresh_interfal = db.get_setting_refresh();
        db.close();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (FullDrawerLayout) findViewById(R.id.drawer_layout));
        nav_setting = act_param.getInt("NAV_ID");
        onSectionAttached(act_param.getInt("NAV_ID"));
        onNavigationDrawerItemSelected(act_param.getInt("NAV_ID"));
        mNavigationDrawerFragment.selectItem(act_param.getInt("NAV_ID"));
        new_fragment(act_param.getInt("NAV_ID"));
        if (act_param.getInt("NAV_ID") == 0) {
            Flag_Back = true;
        } else {
            Flag_Back = false;
        }

        SharedPreferences.Editor editor = getSharedPreferences("rzgonz", MODE_PRIVATE).edit();
        editor.putString("rzgonz", "hah");

    }

    public void onPause() {
        super.onPause();
        //finish();
        System.out.println("apuse");
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

    }

    public void onStop() {
        super.onStop();
        System.out.println("stope");
        //finish();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (setting_save) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(nav_setting))
                    .commit();
        }
        setting_save = false;
        // Get the Camera instance as the activity achieves full user focus
        //Toast.makeText(this, "Resume", Toast.LENGTH_LONG).show();
    }

    public void new_fragment(int position) {
        // TODO Auto-generated method stub
        if (position == 25) {
            query = act_param.getString("QUERY");
            //Toast.makeText(getApplication(), query, Toast.LENGTH_SHORT).show();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
        Flag_fragment = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Table_Setting db = new Table_Setting(this, null, null, 0);
        if (font_size != db.get_setting_font_size() || refresh_interfal != db.get_setting_refresh()) {
            //  Toast.makeText(getApplication(), "Restrat  " , Toast.LENGTH_LONG).show();
            font_size = db.get_setting_font_size();
            refresh_interfal = db.get_setting_refresh();
            db.close();
            setting_save = true;
        }
        db.close();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        nav_setting = position;
        // update the main content by replacing fragments
        //Toast.makeText(getApplication(), "Muali Baru" +position  , Toast.LENGTH_SHORT).show();
        if (Flag_fragment) {
            if (position == 0) {
                Intent mainIntent = new Intent(this, Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("NAV_ID", 0);
                startActivity(mainIntent);
                Flag_Back = true;
            } else if (position == 2) {
                Intent mainIntent = new Intent(this, Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("NAV_ID", 100);
                startActivity(mainIntent);
            } else if (position == 3) {
                Intent mainIntent = new Intent(this, Activity_Saved.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("canal", "breaking");
                startActivity(mainIntent);
            } else if (position > 0 & position < 7) {
                Intent mainIntent = new Intent(this, Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("NAV_ID", 1);
                startActivity(mainIntent);
            } else {
                Flag_Back = false;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position))
                        .commit();
                if (position == 0) {
                    Flag_Back = true;
                }
            }
        }

    }

    public void onSectionAttached(int number) {
        String nav_menu[] = getResources().getStringArray(R.array.nav_name);
        mTitle = nav_menu[number];
        refresh_flag = number;
    }

    public void restoreActionBar() {
        //   ActionBar actionBar = getSupportActionBar();
        //   actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        // actionBar.setDisplayShowTitleEnabled(false);
        //  actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        restoreActionBar();
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            MenuInflater inflater = getMenuInflater();
            // inflater.inflate(R.menu.menu_refresh, menu);

            // MenuItem searchItem = menu.findItem(R.id.action_search);
            // SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            // Search view for api 8


            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_refresh:
                setRefreshActionButtonState(true);
                return true;
            case R.id.action_search:
                //ActionBar actionBar = getSupportActionBar();
                //	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                //actionBar.setDisplayShowTitleEnabled(true);
                //actionBar.setTitle("Hasil Pencarian");

                AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.popup_cari, null);

                imageDialog.setView(layout);
                final EditText txtcari = (EditText) layout.findViewById(R.id.txtcari);
                imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), "Cari : " + txtcari.getText(), Toast.LENGTH_LONG).show();
                        onNavigationDrawerItemSelected(25);
                        query = txtcari.getText().toString();
                        query = query.replaceAll(" ", "+");
                        dialog.dismiss();
                    }
                });
                imageDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        dialog.dismiss();
                    }
                });
                imageDialog.create();
                imageDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void setRefreshActionButtonState(boolean refresing) {
        // TODO Auto-generated method stub
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu.findItem(R.id.action_refresh);
            if (refreshItem != null) {
                if (refresing) {
                    //error api 8
                    //refreshItem.setActionView(R.layout.loading);
                    //onNavigationDrawerItemSelected(nav_menu);
                } else {
                    // error api 8
                    //refreshItem.setActionView(null);
                }
                //refreshItem.setActionView(null);
                //refreshItem.setActionView(R.layout.loading);
                onNavigationDrawerItemSelected(refresh_flag);
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (Flag_Back) {
            File file = new File(Environment.getExternalStorageDirectory(), "/Bisniscom");
            deleteDirectory(file);
            System.clearProperty(getPackageName());
            //System.clearProperty(getPackageResourcePath());

            //IntentFilter filter = new IntentFilter();
            finishActivity(0);
            //System.exit(0);
            finish();
        } else {
            //	onSectionAttached(0);
            // 	onNavigationDrawerItemSelected(0);
            mNavigationDrawerFragment.selectItem(0);
            //  new_fragment(0);
        }
        /*new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
		.setTitle("Exit Application")
		.setMessage("Are You Sure To Exit")
		.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			    File file = new File(Environment.getExternalStorageDirectory(), "/BIS");
			   deleteDirectory(file);
				//System.clearProperty(getPackageName());
				//System.clearProperty(getPackageResourcePath());
			//	System.clearProperty(DOWNLOAD_SERVICE);
				//IntentFilter filter = new IntentFilter();
				finishActivity(0);
				System.exit(0);

				//finish();

			}
		} ).setNegativeButton("No", null)
		.show();
		*/
    }

    protected boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String CANAL_ID = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String id_canal = "id_canal";
        private static final String position = "number";
        Adapter_List_Berita adapter_list_berita;
        Adapter_List_Cari adapter_List_Cari;
        Date date = new Date();
        ProgressBar proses;
        View footerView;
        Json_Connector con = new Json_Connector();
        Boolean flag = true;
        int limit = 20, offset = 0;
        GoogleAnalytics tracker;
        int timmer = 5;
        SwipeRefreshLayout swipeLayout;
        //buat selisih tanggal
        Date waktuSatu, WaktuDua;
        String pola, waktuSatuStr, waktuDuaStr;
        SimpleDateFormat sdf;
        private ListView listberita;
        //ArrayAdapter<String> arraylistberita = new ArrayAdapter<String>(null, 0);
        private ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
        private TextView txt_canal;
        private String canal;
        private int load = 0;
        private int per_page = 2, index_page = 0;
        private TextView index_start, index_much, index_day;
        private TableRow row_canal_name;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(CANAL_ID, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            per_page = 2;
            index_start = new TextView(getActivity());
            index_much = new TextView(getActivity());
            index_day = new TextView(getActivity());

            index_start.setContentDescription("20");
            index_much.setContentDescription("10");
            index_day.setContentDescription("0");
            String canal_id[] = getActivity().getResources().getStringArray(R.array.canal_id);
            String canal_name[] = getActivity().getResources().getStringArray(R.array.nav_name);
            View rootView = inflater.inflate(R.layout.fragment_home_old, container, false);
            footerView = inflater.inflate(R.layout.loading, null, false);
            listberita = (ListView) rootView.findViewById(R.id.listviewberita);
            txt_canal = (TextView) rootView.findViewById(R.id.txt_canal);
            tracker = GoogleAnalytics.getInstance(getActivity());
            proses = (ProgressBar) rootView.findViewById(R.id.prosesdata);
            canal = canal_id[getArguments().getInt(CANAL_ID)];
            //query= mNavigationDrawerFragment.getQuery();
            adapter_list_berita = new Adapter_List_Berita(getActivity(), R.layout.list_berita_new, array_list_berita);
            adapter_List_Cari = new Adapter_List_Cari(getActivity(), R.layout.list_cari, array_list_berita);


            txt_canal.setText(canal_name[getArguments().getInt(CANAL_ID)]);
            Typeface open_sans = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
            txt_canal.setTypeface(open_sans);
            txt_canal.setShadowLayer(9, 0, 2, R.color.title_pager_slider);

            String apps = canal_name[getArguments().getInt(CANAL_ID)];
            Tracker t = ((AnalyticBisnisApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
            if (canal.equals("cari")) {
                t.setScreenName("apps/" + apps.toLowerCase() + "/" + query);
            } else {
                t.setScreenName("apps/" + apps.toLowerCase());
            }

            t.send(new HitBuilders.AppViewBuilder().build());
            t.enableAutoActivityTracking(true);

            listberita.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Animation visible = AnimationUtils.loadAnimation(arg1.getContext(), R.anim.onclick);
                    arg1.startAnimation(visible);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), Activity_Detail_Backup.class);
                    intent.putExtra("POST_ID", (array_list_berita.get(arg2).getIdberita()));
                    intent.putExtra("CANAL", canal);
                    intent.putExtra("DATA", array_list_berita);
                    intent.putExtra("NAV_ID", getArguments().getInt(CANAL_ID));
                    startActivity(intent);
                }
            });
            listberita.setOnScrollListener(new OnScrollListener() {

                private int start;

                @Override
                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    // TODO Auto-generated method stub
                    start = arg1;
                }

                @Override
                public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    if (canal.length() < 5 & (listberita.getLastVisiblePosition() == (arg3 - 1)) & (start == SCROLL_STATE_FLING || start == SCROLL_STATE_IDLE || start == SCROLL_STATE_TOUCH_SCROLL) & (!proses.isShown() & (!proses.getContentDescription().equals("abis"))))
                    //	if((listberita.getLastVisiblePosition()==(arg3-1))&(!proses.isShown())&!proses.getContentDescription().equals("abis"))
                    {
                        Animation hiden = AnimationUtils.loadAnimation(getActivity(), R.anim    .show);
                        proses.startAnimation(hiden);
                        proses.setVisibility(View.VISIBLE);
                        new CountDownTimer(90, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onFinish() {
                                // TODO Auto-generated method stub
                                System.out.print("canal leght" + canal.length() + "\n");
                                refresh();

                            }
                        }.start();

                    }
                }


            });
            System.out.print(" canal cari " + canal + "\n");
            if (canal.equals("cari")) {
                listberita.setAdapter(adapter_List_Cari);

            } else {
                listberita.setAdapter(adapter_list_berita);
            }

            if (canal.length() < 4 & (!canal.equals("cari"))) {
                //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/"+canal);
                //	new DataDownloader(adapter_list_berita,proses).execute(con.URL+"headlines/1/"+canal+"/1");
                //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                //offset=offset+1;
            } else if (canal.equals("cari")) {
                proses.setContentDescription("baru");
                new DataDownloaderCari(adapter_List_Cari, proses).execute(con.URL + "search?q=" + query);
                //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
            } else {
                proses.setContentDescription("baru");
                //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
                //	new DataDownloader(adapter_list_berita,proses).execute(con.URL+canal+"/1/0/20/0");
            }


            swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
            swipeLayout.setOnRefreshListener(new OnRefreshListener() {

                @Override
                public void onRefresh() {
                    // TODO Auto-generated method stub
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter_list_berita.clear();
                            swipeLayout.setRefreshing(false);
                            offset = 0;
                            index_start.setContentDescription("20");
                            index_much.setContentDescription("10");
                            index_day.setContentDescription("0");
                            if (canal.length() < 4 & (!canal.equals("cari"))) {
                                //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"headlines/1/"+canal+"/1");
                                //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                            } else if (canal.equals("cari")) {
                                proses.setContentDescription("baru");
                                new DataDownloaderCari(adapter_List_Cari, proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, con.URL + "search?q=" + query);
                                //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
                            } else {
                                proses.setContentDescription("baru");
                                //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
                                //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+canal+"/1/0/20/0");
                            }
                        }
                    }, 5000);
                }
            });
            swipeLayout.setColorScheme(R.color.blue_bisnis,
                    android.R.color.holo_orange_dark,
                    R.color.blue_bisnis,
                    android.R.color.holo_orange_dark);


            refresh_setting();
            Calendar cal = Calendar.getInstance();
            cal.getTime();
            sdf = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(sdf.format(cal.getTime()));
            pola = "dd-MM-yyyy";
            waktuSatuStr = sdf.format(cal.getTime());
            waktuDuaStr = "18-08-2014 17:43:23";
            waktuSatu = konversiStringkeDate(waktuSatuStr, pola);
            WaktuDua = konversiStringkeDate(waktuDuaStr, pola);
            String hasilSelisih = selisihDateTime(waktuSatu, WaktuDua);
            System.out.println(hasilSelisih);


            row_canal_name = (TableRow) rootView.findViewById(R.id.row_canal_name);
            int color_ini[] = getResources().getIntArray(R.array.array_allchanel_color);
            if (getArguments().getInt(CANAL_ID) < 25)
                row_canal_name.setBackgroundColor(color_ini[getArguments().getInt(CANAL_ID) - 7]);


            return rootView;
        }


        @Override
        public void onResume() {
            super.onResume();  // Always call the superclass method first
            Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
            timmer = db.get_setting_refresh();
            db.close();
        }


        private void refresh_setting() {

            int detik = 1000;
            switch (timmer) {
                case 0:
                    timmer = 5;
                    break;
                case 1:
                    timmer = 15;
                    break;
                case 2:
                    timmer = 30;
                    break;
                case 3:
                    timmer = 60;
                    break;
                case 4:
                    detik = 0;
                    break;

                default:
                    break;
            }

            if (detik != 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter_list_berita.clear();
                        offset = 0;
                        index_start.setContentDescription("0");
                        index_much.setContentDescription("10");
                        index_day.setContentDescription("0");

                        if (canal.length() < 4 & (!canal.equals("cari"))) {
                            //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"headlines/1/"+canal+"/1");
                            //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                        } else if (canal.equals("cari")) {
                            proses.setContentDescription("baru");
                            new DataDownloaderCari(adapter_List_Cari, proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, con.URL + "search?q=" + query);
                            //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
                        } else {
                            proses.setContentDescription("baru");

                            //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
                            //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+canal+"/1/0/20/0");
                        }
                        refresh_setting();
                    }
                }, (timmer * 60 * 1000));
            }
        }

        private void refresh() {
            // TODO Auto-generated method stub

            if (canal.length() < 4) {
                if (canal.equals("0")) {
                    if (offset == 0) {
                        //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/0/");
                        offset = offset + 1;
                    } else {
                        //	new DataDownloaderOfIndex(adapter_list_berita,proses,index_start,index_much,index_day).execute(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                        index_page = index_page + 1;
                    }

                } else {
                    //	if(offset==0){
                    //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"breaking/1/"+canal);
                    //	offset=offset+1;

                    //}else{
                    //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                    //offset=offset+1;
                    //	}

                    if (offset == 0) {
                        //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"breaking/1/"+canal);
                        offset = offset + 1;
                        index_page = 0;

                    } else {
                        if (index_page == 0) {
                            String Selisih[] = proses.getContentDescription().toString().split("@");
                            String last_date = Selisih[0];
                            String last_count = Selisih[1];
                            waktuDuaStr = proses.getContentDescription().toString();
                            waktuSatu = konversiStringkeDate(waktuSatuStr, pola);
                            WaktuDua = konversiStringkeDate(last_date, pola);
                            System.out.println("last_date " + last_date + "Awal tanggal :" + waktuSatuStr + " last count " + last_count);
                            String hasilSelisih = selisihDateTime(waktuSatu, WaktuDua);
                            index_page = Integer.parseInt(hasilSelisih);
                            index_day.setContentDescription("" + index_page);
                            index_start.setContentDescription("" + last_count);
                            System.out.println("Selisish Hari " + hasilSelisih + " mulia : " + last_count);
                        }

                        //new DataDownloaderOfIndex(adapter_list_berita,proses,index_start,index_much,index_day).execute(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                        index_page = index_page + 1;
                    }

                }
            } else {
                //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/0/"+offset+"/10");

                if (canal.equals("cari")) {
                    new DataDownloaderCari(adapter_List_Cari, proses).execute(con.URL + "search/?q=" + query + "&per_page=" + per_page);
                    per_page = per_page + 1;
                    offset = offset + 1;
                }
                //proses.setVisibility(View.GONE);
            }
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Home2) activity).onSectionAttached(getArguments().getInt(CANAL_ID));
        }
    }

}
