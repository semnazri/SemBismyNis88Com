package bisnis.com.official;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.SearchManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import bisnis.com.official.R;
import bisnis.com.official.Activity_Detail_Backup.SectionsPagerAdapter;
import bisnis.com.official.AnalyticBisnisApp.TrackerName;
import bisnis.com.official.home.Activity_Home_Old;
import db.Table_ListBerita;
import db.Table_Setting;
import example.FullDrawerLayout;
import koneksi.Json_Connector;
import model.*;

public class Activity_Setting extends ActionBarActivity implements
		NavigationDrawerFragment3.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment3 mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	public static int nav_menu;

	private int fragmantedload;
	boolean nav;

	private boolean flag_nav=false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Bundle act_param = getIntent().getExtras();
 
   
		
        mNavigationDrawerFragment = (NavigationDrawerFragment3)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (FullDrawerLayout) findViewById(R.id.drawer_layout));
        
       onSectionAttached(25);
       onNavigationDrawerItemSelected(25);
       mNavigationDrawerFragment.selectItem(25);
      

    }
	
	@Override
	public void onRestart() {
	    super.onRestart();  // Always call the superclass method first
	   // Bundle xnim = getIntent().getExtras();
	    
		
	  //  Toast.makeText(getApplication(), "Muali Baru" +  xnim.getString("canal"), Toast.LENGTH_LONG).show();
	    // Get the Camera instance as the activity achieves full user focus
	    
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		//Toast.makeText(getApplication(), "as " +position, Toast.LENGTH_LONG).show();
		if(flag_nav&position!=25){
            {
                if(position==0){
                    Intent mainIntent = new Intent(this,Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",0);
                    startActivity(mainIntent);
                }else
                if(position==2){
                    Intent mainIntent = new Intent(this,Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",2);
                    startActivity(mainIntent);
                }else
                if(position==3){
                    Intent mainIntent = new Intent(this,Activity_Saved.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("canal","breaking");
                    startActivity(mainIntent);
                }else
                if(position>0&position<7){
                    Intent mainIntent = new Intent(this,Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",1);
                    startActivity(mainIntent);
                }else{
//                Flag_Back=false;
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, Test_Fragment.newInstance(position))
//                        .commit();

                    Intent mainIntent = new Intent(this,Activity_Home_Old.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",position);
                    startActivity(mainIntent);

                }
            }
		}else{
				nav_menu=position;
				FragmentManager fragmentManager = getSupportFragmentManager();
				String[] id_canal = getResources().getStringArray(R.array.canal_id);
				fragmentManager.beginTransaction().replace(R.id.container,PlaceholderFragment.newInstance(id_canal[nav_menu],nav_menu, null)).commit();
				nav=true;
				flag_nav=true;
			}
	}

	public void onSectionAttached(int number) {
		String[] nav_title = getResources().getStringArray(R.array.nav_name);
		mTitle = nav_title[number];
	}

	public void restoreActionBar() {
	//	ActionBar actionBar = getSupportActionBar();
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	//	actionBar.setDisplayShowTitleEnabled(true);
		//actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	//this.optionsMenu = menu;
  	
      if (!mNavigationDrawerFragment.isDrawerOpen()) {
          // Only show items in the action bar relevant to this screen
          // if the drawer is not showing. Otherwise, let the drawer
          // decide what to show in the action bar.
			//MenuInflater inflater = getMenuInflater();
		 //   inflater.inflate(R.menu.menu_refresh, menu);
		    
		 //   MenuItem searchItem = menu.findItem(R.id.action_search);
		//    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
		//	ActionBar actionBar = getSupportActionBar();
		//	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		//	actionBar.setDisplayShowTitleEnabled(true);
		//	actionBar.setTitle("Hasil Pencarian");
			
			Builder imageDialog = new Builder(this);
	        LayoutInflater inflater = getLayoutInflater();
	        View layout = inflater.inflate(R.layout.popup_cari, null);
	        
	        imageDialog.setView(layout);
	        final EditText txtcari = (EditText)layout.findViewById(R.id.txtcari);
	        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which) {
	            	//Toast.makeText(getApplication()	, "OK "+ txtcari.getText(), Toast.LENGTH_LONG).show();
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
	
	public void setRefreshActionButtonState(final boolean refresing){
		/*
		if(optionsMenu !=null){
			//final MenuItem refreshItem = optionsMenu.findItem(R.id.action_refresh);
		//	if(refreshItem !=null){
				if(refresing){
					//error api 8
					//refreshItem.setActionView(R.layout.loading);
				//	startActivity(getIntent());
				//	this.finish();
				}else {
					// error api 8
					//refreshItem.setActionView(null);
				}
			
			}
			
		}
		*/
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private ListView listberita;
		//ArrayAdapter<String> arraylistberita = new ArrayAdapter<String>(null, 0);
		Adapter_Saved adapter_saved;
		private TextView txtdate;
		private String canal;
		private int load=0;
		Date date = new Date();
		 ProgressBar porses;
		 View footerView ;
		 Json_Connector con = new Json_Connector();
		 Boolean flag =true;
		 int limit=20,offset=10;
		 GoogleAnalytics tracker;
		 
		 boolean delete_mode=false;
		 
		 ArrayList<Book> book = new ArrayList<Book>();
		 
		 ArrayList<Frame_ListBerita> data_saved = new ArrayList<Frame_ListBerita>();
		 
		 ArrayList<String> data_delete =  new ArrayList<String>();
			
		  
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String id_canal = "id_canal";
		private static final String position = "number";
		Table_ListBerita db;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(String id,int number,String query) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putString(id_canal, id);
			args.putInt(position, number);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, final ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	        View rootView = inflater.inflate(R.layout.setting, container, false);
	    	Spinner spinner_inteval_refresh = (Spinner)rootView.findViewById(R.id.spinner_interval_refresh);
			Spinner spinner_font_size = (Spinner)rootView.findViewById(R.id.spinner_font_size);
			ToggleButton toogle_notive = (ToggleButton)rootView.findViewById(R.id.toggle_notiv);
			Table_Setting db2 = new Table_Setting(getActivity(), null, null, 0);
			spinner_inteval_refresh.setSelection(db2.get_setting_refresh());
			spinner_font_size.setSelection(db2.get_setting_font_size());
			if(db2.get_setting_font_notive()==1){
				toogle_notive.setChecked(true);
			}else{
				toogle_notive.setChecked(false);
			}
			
			toogle_notive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
						//Toast.makeText(getActivity(), "TREU", Toast.LENGTH_LONG).show();
						Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
						 db.update_setting_notive("1","1");
						 db.close();
					}
					else {
						//t.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
						Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
						 db.update_setting_notive("1","2");
						 db.close();
					}
				}
			});
			
			spinner_inteval_refresh.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
					 db.update_setting_refresh("1",""+arg2);
					 db.close();
					//Toast.makeText(getActivity(), " asd"+arg2, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			spinner_font_size.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//Toast.makeText(getActivity(), " asd"+arg2, Toast.LENGTH_SHORT).show();
					Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
					 db.update_setting_font_size("1",""+arg2);
					 db.close();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        return rootView;
	        
	    }

		protected void refresh() {
			
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Activity_Setting) activity).onSectionAttached(getArguments().getInt(position));
		}
	}
	
	@Override
	public void onBackPressed(){
		 this.finish();
	}


}
