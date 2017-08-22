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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import bisnis.com.official.R;
import bisnis.com.official.Activity_Detail_Backup.SectionsPagerAdapter;
import bisnis.com.official.AnalyticBisnisApp.TrackerName;
import bisnis.com.official.home.Activity_Home_Old;
import db.Table_ListBerita;
import example.FullDrawerLayout;
import koneksi.Json_Connector;
import model.*;

public class Activity_Saved extends ActionBarActivity implements
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
        setContentView(R.layout.activity_detail);
        Bundle act_param = getIntent().getExtras();
   
		
        mNavigationDrawerFragment = (NavigationDrawerFragment3)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (FullDrawerLayout) findViewById(R.id.drawer_layout));
        
       onSectionAttached(6);
       onNavigationDrawerItemSelected(6);
       mNavigationDrawerFragment.selectItem(6);
       
   	ProgressBar prosesdata =(ProgressBar)findViewById(R.id.prosesdata);
	prosesdata.setVisibility(View.GONE);
     
   
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
		if(flag_nav&position!=6){
            {
                if(position==0){
                    Intent mainIntent = new Intent(this,Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",0);
                    startActivity(mainIntent);
                }else
                if(position==2){
                    Intent mainIntent = new Intent(this,Home3.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("NAV_ID",100);
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

        private boolean list_delete_mode=false;


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
			data_saved.clear();
	        View rootView = inflater.inflate(R.layout.fragment_save, container, false);
	        footerView= inflater.inflate(R.layout.loading, null, false);
	        listberita = (ListView)rootView.findViewById(R.id.listviewberita);
	        txtdate= (TextView)rootView.findViewById(R.id.txttgl);
	        tracker = GoogleAnalytics.getInstance(getActivity());
	        porses = (ProgressBar)rootView.findViewById(R.id.prosesdata);
			canal=getArguments().getString(id_canal);
			System.out.println(canal);
	    	 db= new Table_ListBerita(getActivity(), null, null, 0);
	    	final RelativeLayout layout_deletemode = (RelativeLayout)rootView.findViewById(R.id.layout_deletemodel);
            layout_deletemode.setContentDescription("false");
			data_saved.addAll(db.getAllBerita());
			for (int i = 0; i < data_saved.size(); i++) {
				data_delete.add("NO");
			}
			Tracker t = ((AnalyticBisnisApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("apps/bookmarks");
	        t.send(new HitBuilders.AppViewBuilder().build());
	        t.enableAutoActivityTracking(true);
			adapter_saved = new Adapter_Saved(getActivity(), R.layout.list_saved,data_saved,data_delete,layout_deletemode);
		
			
	    	//new DataDownloader(adapterheadline,porses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+canal+"/1/0/20/0");
		
	    //	Toast.makeText(getActivity(), " BOOK = "+  book.size(), Toast.LENGTH_LONG).show();
			ImageView option_cancel = (ImageView)rootView.findViewById(R.id.opt_cance);
			ImageView option_okdelete = (ImageView)rootView.findViewById(R.id.opt_delete);
			ImageView option_delete_all = (ImageView)rootView.findViewById(R.id.opt_delete_all);
			
			option_delete_all.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new Builder(getActivity()).setIcon(R.drawable.ic_launcher)
					.setTitle("Waring")
					.setMessage("Are You Sure To Delete All Data")
					.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							for (int i = 0; i < data_saved.size(); i++) {
								Table_ListBerita db = new Table_ListBerita(getActivity(), null, null,0);
								//System.out.println(listberita.getChildAt(i).getId());
								db.deleteBerita(data_saved.get(i));
							}
							dialog.dismiss();
							data_saved.clear();
							//data_saved.addAll(db.getAllBerita());
							adapter_saved.notifyDataSetChanged();
						}
					} ).setNegativeButton("No", null)
					.show();
				}
			});
			
			option_okdelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i = 0; i < listberita.getChildCount(); i++) {
						if(data_delete.get(i).equals("YES")){
							Table_ListBerita db = new Table_ListBerita(getActivity(), null, null,0);
							System.out.println(listberita.getChildAt(i).getId());
							db.deleteBerita(data_saved.get(i));
						}
						
					}
					data_saved.clear();
					data_saved.addAll(db.getAllBerita());
					data_delete.clear();
					for (int i = 0; i < data_saved.size(); i++) {
						data_delete.add("NO");
					}
					adapter_saved.notifyDataSetChanged();
					layout_deletemode.setVisibility(View.GONE);
				}
			});
			
			option_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
                layout_deletemode.setContentDescription("false");
				layout_deletemode.setVisibility(View.GONE);
				delete_mode=false;
				for (int i = 0; i < listberita.getChildCount(); i++) {
					listberita.getChildAt(i).setBackgroundResource(R.color.list_background);
					
				}
				data_delete.clear();
				for (int i = 0; i < data_saved.size(); i++) {
					data_delete.add("NO");
				}
				
				adapter_saved.notifyDataSetChanged();

                listberita.refreshDrawableState();
			
				}


			});
			
			
			listberita.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int posision,
						long arg3) {
					// TODO Auto-generated method stub
				
					if(delete_mode){
					if(v.getContentDescription().equals("Delete")){
					//	Toast.makeText(getActivity(), "relase", Toast.LENGTH_LONG).show();
						//v.setBackgroundColor(R.drawable.counter_bg);
						v.setBackgroundResource(android.R.color.white);
						//v.setBackgroundColor(R.drawable.list_item_bg_pressed);
						v.setContentDescription("Remove");
						//v.setSelected(false);
						//v.setPressed(false);
					
					}else{
						v.setBackgroundResource(R.color.blue_bisnis);
						v.setContentDescription("Delete");
						//v.setSelected(true);
						//v.setPressed(true);
					}
						}else
						{
					Intent intent = new Intent();
					intent.setClass(getActivity(), Activity_Detail_Backup.class);
					intent.putExtra("POST_ID",(data_saved.get(posision).getIdberita()));
					intent.putExtra("CANAL",canal);
					intent.putExtra("DATA", data_saved);
					intent.putExtra("NAV_ID", nav_menu);
					startActivity(intent);
						}
					/*
					if((posision<20)&(canal.length()>4)){
						intent.setClass(getActivity(), Activity_Detail.class);
						intent.putExtra("post_id",(arraylistheadline.get(posision).idberita));
						intent.putExtra("canal",canal);
						intent.putExtra("data", arraylistheadline);
						startActivity(intent);
					}
					else
						if(posision<10&canal.length()>4){
							intent.setClass(getActivity(), Activity_Detail.class);
							intent.putExtra("post_id",(arraylistheadline.get(posision).idberita));
							intent.putExtra("student", arraylistheadline);
							intent.putExtra("canal",canal);
							intent.putExtra("data", arraylistheadline);
							startActivity(intent);
						
					}
						else {
							intent.setClass(getActivity(), Activity_Detail_Index.class);
							intent.putExtra("date", arraylistheadline.get(posision).datepost.toString());
							intent.putExtra("catagoty_id", arraylistheadline.get(posision).category.toString());
							intent.putExtra("post_id",(arraylistheadline.get(posision).idberita));
							intent.putExtra("data", arraylistheadline);
							startActivity(intent);	
						}
					*/
				}
						
			});
			 
			listberita.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View v,
						final int arg2, long arg3) {
					// TODO Auto-generated method stub
					//FrameLayout.LayoutParams par = (android.widget.FrameLayout.LayoutParams)v.getLayoutParams();
					Builder optiondelete = new Builder(getActivity());
					LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout= inflater.inflate(R.layout.option_saved, container,false);
					ImageView opt_delete = (ImageView)layout.findViewById(R.id.opt_delete);
					ImageView opt_deleteall = (ImageView)layout.findViewById(R.id.opt_deleteall);
					ImageView opt_select = (ImageView)layout.findViewById(R.id.opt_select);
					//Toast.makeText(getActivity(), "Delete MOde", Toast.LENGTH_LONG).show();
					optiondelete.setView(layout);
				
					optiondelete.create();
					final AlertDialog dialog;  // instance variable

					dialog = optiondelete.show();
					opt_deleteall.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							// TODO Auto-generated method stub
							new Builder(getActivity()).setIcon(R.drawable.ic_launcher)
							.setTitle("Waring")
							.setMessage("Are You Sure To Delete All Data")
							.setPositiveButton("YES", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									for (int i = 0; i < data_saved.size(); i++) {
										Table_ListBerita db = new Table_ListBerita(getActivity(), null, null,0);
										//System.out.println(listberita.getChildAt(i).getId());
										db.deleteBerita(data_saved.get(i));
									}
									dialog.dismiss();
									data_saved.clear();
									//data_saved.addAll(db.getAllBerita());
									adapter_saved.notifyDataSetChanged();
								}
							} ).setNegativeButton("No", null)
							.show();
							
						}
					});
				
					opt_delete.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
						
								Table_ListBerita db = new Table_ListBerita(getActivity(), null, null,0);
								//System.out.println(listberita.getChildAt(i).getId());
								db.deleteBerita(data_saved.get(arg2));
								dialog.dismiss();
								data_saved.clear();
								data_saved.addAll(db.getAllBerita());
								adapter_saved.notifyDataSetChanged();
						}
					});
					opt_select.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							delete_mode=true;
							layout_deletemode.setVisibility(View.VISIBLE);
                            list_delete_mode=true;
                            layout_deletemode.setContentDescription("true");
                            listberita.refreshDrawableState();
                            dialog.dismiss();

						}
					});
					return true;
				}
			});
	
			 
			 

			
			((Activity_Saved)getActivity()).setRefreshActionButtonState(false);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM  yyyy");
	        txtdate.setText("Bookmark");
	    
	        listberita.setAdapter(adapter_saved);
	        return rootView;
	        
	    }

		protected void refresh() {
			
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Activity_Saved) activity).onSectionAttached(getArguments().getInt(position));
		}
	}
	
	@Override
	public void onBackPressed(){
		 this.finish();
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


    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
}
