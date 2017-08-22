package bisnis.com.official;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bisnis.com.official.home.Activity_Home_Old;
import bisnis.com.official.home.Fragmented_Home;
import bisnis.com.official.home.Fragmented_Home_All;
import bisnis.com.official.home.Fragmented_saved;
import db.Table_List_Nav;
import db.Table_Setting;
import example.FullDrawerLayout;
import lib.anime_pager.ZoomOutPageTransformer;
import model.Frame_nav;
import singelton.Singelton_navsaved;

//import com.google.android.gms.internal.ca;

public class Home3 extends ActionBarActivity
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
    ArrayList<Frame_nav> favorite_name = new ArrayList<Frame_nav>();
    SharedPreferences.Editor editor;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Menu optionsMenu;
    private int refresh_flag;
    private boolean Flag_fragment = false;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int menu_flag = 0;

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
        setContentView(R.layout.activity_home3);
        act_param = getIntent().getExtras();
        setting_save = false;
        mNavigationDrawerFragment = (NavigationDrawerFragment3) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        Table_Setting db = new Table_Setting(this, null, null, 0);
        font_size = db.get_setting_font_size();
        refresh_interfal = db.get_setting_refresh();
        db.close();
        editor = getSharedPreferences("rzgonz", MODE_PRIVATE).edit();
        editor.putString("rzgonz", "hah");

        Singelton_navsaved model = Singelton_navsaved.getInstance();
        model.setString("hah");

        if (act_param.getInt("NAV_ID") == 100) {
            Table_List_Nav db2 = new Table_List_Nav(this, null, null, 0);
            favorite_name.addAll(db2.get_all_nav_cek());
            db2.close();

            model.setString("all");
            editor.putString("rzgonz", "all");
            //  if(favorite_name.size()<1)
            // act_param.putInt("NAV_ID",1);

        }


        editor.commit();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (FullDrawerLayout) findViewById(R.id.drawer_layout));
        nav_setting = act_param.getInt("NAV_ID");
        menu_flag = act_param.getInt("NAV_ID");
        onSectionAttached(act_param.getInt("NAV_ID"));
        onNavigationDrawerItemSelected(act_param.getInt("NAV_ID"));
        mNavigationDrawerFragment.selectItem(act_param.getInt("NAV_ID"));
        new_fragment(act_param.getInt("NAV_ID"));
        if (act_param.getInt("NAV_ID") == 0) {
            Flag_Back = true;
        } else {
            Flag_Back = false;
        }


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        // Bind the tabs to the ViewPager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        if (!(act_param.getInt("NAV_ID") == 100)) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            example.PagerSlidingTabStrip tabs = (example.PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(mViewPager);
        } else if ((act_param.getInt("NAV_ID") == 100) & !favorite_name.isEmpty()) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            example.PagerSlidingTabStrip tabs = (example.PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(mViewPager);
        } else {
            TextView kosong = (TextView) findViewById(R.id.txt_kosong_favorite);
            Typeface open_sans = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
            kosong.setTypeface(open_sans, Typeface.BOLD_ITALIC);
            kosong.setVisibility(View.VISIBLE);

        }
        // mSectionsPagerAdapter.notifyDataSetChanged();


    }

    public void onPause() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        super.onPause();
        //finish();
        System.out.println("apuse");

    }

    public void onStop() {
        super.onStop();
        System.out.println("stope");
        //finish();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (setting_save) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.container, PlaceholderFragment.newInstance(nav_setting))
//                    .commit();
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
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position))
//                .commit();
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
//                Flag_Back=false;
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, Test_Fragment.newInstance(position))
//                        .commit();

                Intent mainIntent = new Intent(this, Activity_Home_Old.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("NAV_ID", position);
                startActivity(mainIntent);

            }
        }

    }

    public void onSectionAttached(int number) {
        if (number != 100) {
            String nav_menu[] = getResources().getStringArray(R.array.nav_name);
            mTitle = nav_menu[number];
        }
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below);
            // Toast.makeText(Home3.this,"Info  ",Toast.LENGTH_LONG).show();

            if (menu_flag == 100) {
                return Fragmented_saved.newInstance(position);
            } else if (menu_flag == 0) {
                return Fragmented_Home.newInstance(position);
            } else {
                return Fragmented_Home_All.newInstance(position);
            }

//                Test_Fragment2 fragment = new Test_Fragment2();
//                if(fragment instanceof Observer)
//                    mObservers.addObserver((Observer) fragment);
//                return fragment.newInstance(position);


        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (menu_flag == 100) {
                if (favorite_name.size() < 1)
                    return 0;

                return favorite_name.size();
            } else if (menu_flag < 1) {
                String[] home = getResources().getStringArray(R.array.array_frontpage);
                return home.length;
            } else {
                String[] home = getResources().getStringArray(R.array.array_allchanel);
                return home.length;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (menu_flag == 100) {
                return favorite_name.get(position).nav;
            } else if (menu_flag < 1) {
                String[] home = getResources().getStringArray(R.array.array_frontpage);
                return home[position];
            } else {
                String[] home = getResources().getStringArray(R.array.array_allchanel);
                return home[position];
            }

        }
    }


}
