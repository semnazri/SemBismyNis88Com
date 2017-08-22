package bisnis.com.official;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bisnis.com.official.R;
import bisnis.com.official.AnalyticBisnisApp.TrackerName;
import bisnis.com.official.Home2.PlaceholderFragment;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import koneksi.Json_Connector;
import model.Frame_ListBerita;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

	private Menu optionsMenu;  
	Bundle act_param;
	static final String STATE_SCORE = "playerScore";
	private int refresh_flag;

	private boolean Flag_fragment=false,Flag_pager=false; 
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        act_param = getIntent().getExtras();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
    	
        mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
     // Set up the ViewPager with the sections adapter.
     		mViewPager = (ViewPager) findViewById(R.id.pager);
     		
     		 // Bind the tabs to the ViewPager
     	
     		 
     		mViewPager.setAdapter(mSectionsPagerAdapter);
     		
     		 PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
     		 tabs.setViewPager(mViewPager);
     		 
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
       onSectionAttached(act_param.getInt("NAV_ID"));
       onNavigationDrawerItemSelected(act_param.getInt("NAV_ID"));
       mNavigationDrawerFragment.selectItem(act_param.getInt("NAV_ID"));
       new_fragment(act_param.getInt("NAV_ID"));
       
    }
    public void new_fragment(int position) {
		// TODO Auto-generated method stub
    	//PlaceholderFragment.newInstance(position);
        Flag_fragment=true;
	}

	@Override
    protected void onRestart() {
        super.onRestart();
      //  Toast.makeText(getApplication(), "Restrat  " , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	 //Toast.makeText(getApplication(), "Muali Baru" +position  , Toast.LENGTH_SHORT).show();
    	if(Flag_fragment){
    		//PlaceholderFragment.newInstance(position);
    		 Intent mainIntent = new Intent(getApplication(),Home.class);
             mainIntent.putExtra("NAV_ID",position);
             mainIntent.putExtra("SUBCANAL_COUNT",5);
             startActivity(mainIntent);
    		mSectionsPagerAdapter.getItem(position);
    		this.finish();
    	}
     
    }

    public void onSectionAttached(int number) {
    	String nav_menu[] = getResources().getStringArray(R.array.nav_name);
    	 mTitle =nav_menu[number];
    	 refresh_flag=number;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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
		    inflater.inflate(R.menu.menu_refresh, menu);
		    
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
			ActionBar actionBar = getSupportActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setTitle("Hasil Pencarian");
			
			AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
	        LayoutInflater inflater = getLayoutInflater();
	        View layout = inflater.inflate(R.layout.popup_cari, null);
	        
	        imageDialog.setView(layout);
	        final EditText txtcari = (EditText)layout.findViewById(R.id.txtcari);
	        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which) {
	            	Toast.makeText(getApplication()	, "OK "+ txtcari.getText(), Toast.LENGTH_LONG).show();
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
    	if(optionsMenu !=null){
			final MenuItem refreshItem = optionsMenu.findItem(R.id.action_refresh);
			if(refreshItem !=null){
				if(refresing){
					//error api 8
					//refreshItem.setActionView(R.layout.loading);
					//onNavigationDrawerItemSelected(nav_menu);
				}else {
					// error api 8
					//refreshItem.setActionView(null);
				}
					//refreshItem.setActionView(null);
				//refreshItem.setActionView(R.layout.loading);
				onNavigationDrawerItemSelected(refresh_flag);
			}
			
		}
	}
    
    
    /**
	 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below);
			if(position>0){
				return PlaceholderFragment.newInstance(position);
			}else{
				return PlaceholderFragment.newInstance(act_param.getInt("NAV_ID"));
			}
			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return act_param.getInt("SUBCANAL_COUNT");
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section3).toUpperCase(l);
			case 4:
				return getString(R.string.title_section3).toUpperCase(l);
			case 5:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
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
        private ListView listberita;
		//ArrayAdapter<String> arraylistberita = new ArrayAdapter<String>(null, 0);
		private ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
		Adapter_List_Berita adapter_list_berita;
		private TextView txtdate;
		private String canal,subcanal;
		private int load=0;
		Date date = new Date();
		 ProgressBar proses;
		 View footerView ;
		 Json_Connector con = new Json_Connector();
		 Boolean flag =true;
		 int limit=20,offset=10;
		 GoogleAnalytics tracker;
		private String[] subcanal_id;
		 
		  
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String id_canal = "id_canal";
		private static final String position = "number";

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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	String canal_id [] = getActivity().getResources().getStringArray(R.array.canal_id);
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            footerView= inflater.inflate(R.layout.loading, null, false);
	        listberita = (ListView)rootView.findViewById(R.id.listviewberita);
	        txtdate= (TextView)rootView.findViewById(R.id.txttgl);
	        tracker = GoogleAnalytics.getInstance(getActivity());
	        proses = (ProgressBar)rootView.findViewById(R.id.prosesdata);
	        canal=canal_id[getArguments().getInt(CANAL_ID)];
	        
	        switch (getArguments().getInt(CANAL_ID)) {
			case 0:
				 subcanal_id = getActivity().getResources().getStringArray(R.array.headlines);
				break;

			default:
				subcanal_id = getActivity().getResources().getStringArray(R.array.headlines);
				break;
			}
	        //String[] subcanal_id;
			subcanal=subcanal_id[getArguments().getInt(CANAL_ID)];
	        
	    
			adapter_list_berita = new Adapter_List_Berita(getActivity(), R.layout.list_berita,array_list_berita);
			if(canal.length()<4&(!canal.equals("cari"))){
	   // 		new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/"+canal);
	    	}
	    	else if(canal.equals("cari")){
	    //		new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/"+canal);
	    	}
	    	else{
	    //		new DataDownloader(adapter_list_berita,proses).execute(con.URL+subcanal+"/1/0/20/0");
	    	}
			Tracker t = ((AnalyticBisnisApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
	        t.setScreenName("Apps/"+canal);
	        t.send(new HitBuilders.AppViewBuilder().build());
	        t.enableAutoActivityTracking(true);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM  yyyy");
	        txtdate.setText(dateFormat.format(date));
	        
	        listberita.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(getActivity(), Activity_Detail.class);
					intent.putExtra("POST_ID",(array_list_berita.get(arg2).getIdberita()));
					intent.putExtra("CANAL",canal);
					intent.putExtra("DATA", array_list_berita);
					intent.putExtra("NAV_ID", getArguments().getInt(CANAL_ID));
					startActivity(intent);
				}
			});
	        listberita.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					if((listberita.getLastVisiblePosition()==(arg3-1))&(!proses.isShown())){
					proses.setVisibility(View.VISIBLE);
					refresh();
					}
				}

				
			});
	        listberita.setAdapter(adapter_list_berita);
            return rootView;
        }
        
        private void refresh() {
			// TODO Auto-generated method stub
        	if(canal.length()<4){
			//	new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/"+canal+"/"+offset+"/10");
				offset=offset+10;
			}else{
				//new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/0/"+offset+"/10");
				offset=offset+10;
			}
		}
        

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Home) activity).onSectionAttached(getArguments().getInt(CANAL_ID));
        }
    }
    
    @Override
	public void onBackPressed(){
		new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
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
	}
    
    protected boolean deleteDirectory(File path) {
	    if( path.exists() ) {
		      File[] files = path.listFiles();
		      if (files == null) {
		          return true;
		      }
		      for(int i=0; i<files.length; i++) {
		         if(files[i].isDirectory()) {
		           deleteDirectory(files[i]);
		         }
		         else {
		           files[i].delete();
		         }
		      }
		    }
		    return( path.delete() );
		  }

}
