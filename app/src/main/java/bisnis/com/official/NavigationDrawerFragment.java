package bisnis.com.official;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bisnis.com.official.R;

//import com.google.android.gms.drive.internal.ac;
//import com.google.android.gms.internal.ia;

import db.Table_ListBerita;
import db.Table_List_Nav;
import db.Table_Setting;
import model.Frame_nav;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView,mDrawerListView2,mDrawerListView3;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 7;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private ExpandableListView expand_nav;
    List<String> listDataHeader;
    HashMap<String, List<Frame_nav>> listDataChild;
    Adapter_Nav adapter,adapter2,adapter3;
    Adapter_Nav_Saved adapter4;
    ArrayList<Frame_nav> array_nav,array_nav_favorite,array_nav_all,array_nav_saved;
    ImageView cmd_favorite;
    TableRow cmd_search,table_favorite ;
    TableRow cmd_about,cmd_setting;
    
    Table_List_Nav db;
    public String query;
    public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        db= new Table_List_Nav(getActivity(), null, null, 0);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
        
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	 View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    	expand_nav = (ExpandableListView)rootView.findViewById(R.id.expand_list_nav);
    
    	//prepareListData();
    	
    	
         // setting list adapter
    	// expand_nav.setAdapter(listAdapter);
    	 
    	// expand_nav.expandGroup(0);
    	// expand_nav.expandGroup(1);
    	cmd_about = (TableRow)rootView.findViewById(R.id.cmd_about);
    	cmd_setting = (TableRow)rootView.findViewById(R.id.cmd_setting);
    	
    	cmd_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent setting = new Intent(getActivity(),Activity_Setting.class);
				startActivity(setting);
				mDrawerLayout.closeDrawer(mFragmentContainerView);
				/*
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.setContentView(R.layout.setting);
				dialog.setCancelable(true);
				dialog.show();
				ImageView cmd_exit_setting = (ImageView)dialog.findViewById(R.id.cmd_exit_setting);
				Spinner spinner_inteval_refresh = (Spinner)dialog.findViewById(R.id.spinner_interval_refresh);
				Spinner spinner_font_size = (Spinner)dialog.findViewById(R.id.spinner_font_size);
				Table_Setting db2 = new Table_Setting(getActivity(), null, null, 0);
				spinner_inteval_refresh.setSelection(db2.get_setting_refresh());
				spinner_font_size.setSelection(db2.get_setting_font_size());
				
				
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
			
				
				cmd_exit_setting.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				*/
			}
		});
    	cmd_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {



				// TODO Auto-generated method stub
				mDrawerLayout.closeDrawer(mFragmentContainerView);
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.setContentView(R.layout.about);
				dialog.setCancelable(true);
				dialog.show();
				TextView cmd_sibertama = (TextView) dialog.findViewById(R.id.cmd_sibertama);
				ImageView cmd_exit_about = (ImageView)dialog.findViewById(R.id.cmd_exit_about);

			 	cmd_exit_about.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				cmd_sibertama.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String url = "http://www.sibertama.com";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
				});
			
				
			}
		});
    	
    	cmd_search = (TableRow)rootView.findViewById(R.id.cmd_search);
    	table_favorite = (TableRow)rootView.findViewById(R.id.table_roow_favorite);
    	table_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
			
					Dialog dialog = new Dialog(getActivity());
					dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
					dialog.setContentView(R.layout.popup_nav_saved);
					dialog.setTitle("Chose Favorite Channels");
					dialog.setCancelable(true);
					dialog.show();
					dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);
					array_nav_saved.clear();
					array_nav_saved.addAll(db.get_all_nav());
					adapter4 = new Adapter_Nav_Saved(getActivity(),R.layout.list_nav_saved,array_nav_saved);
					ListView list_nav_saved = (ListView) dialog.findViewById(R.id.list_nav_saved);
					list_nav_saved.setAdapter(adapter4);
					list_nav_saved.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							CheckBox asd = (CheckBox)arg1.findViewById(R.id.cek_favorite);
							if(asd.isChecked()){
								asd.setChecked(false);
								array_nav_saved.set(arg2, new Frame_nav(array_nav_saved.get(arg2).id, array_nav_saved.get(arg2).nav, array_nav_saved.get(arg2).icon, array_nav_saved.get(arg2).position,0));
								db.update_nav_favorite(array_nav_saved.get(arg2));
								
							}else{
								array_nav_saved.set(arg2, new Frame_nav(array_nav_saved.get(arg2).id, array_nav_saved.get(arg2).nav, array_nav_saved.get(arg2).icon,array_nav_saved.get(arg2).position, 1));
								db.update_nav_favorite(array_nav_saved.get(arg2));
								asd.setChecked(true);
							}
							array_nav_favorite.clear();
							array_nav_favorite.addAll(db.get_all_nav_cek());
							adapter2.notifyDataSetChanged();
							updateListViewHeight(mDrawerListView2);
						}
						
					});
					
				//Toast.makeText(getActivity(), "Tex", Toast.LENGTH_SHORT).show();
			
			}
		});
    	
    	cmd_search.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View arg0) {
				
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawable(new  ColorDrawable(Color.TRANSPARENT));
				dialog.setContentView(R.layout.popup_cari);
				dialog.show();
				Button cmd_cancle_search = (Button)dialog.findViewById(R.id.cmd_cancel_search);
				Button cmd_ok_search = (Button)dialog.findViewById(R.id.cmd_ok_search);
				final EditText txtcari= (EditText)dialog.findViewById(R.id.txtcari);
				
				cmd_ok_search.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(getActivity(), "Cari : "+ txtcari.getText(), Toast.LENGTH_LONG).show();
						String cari = txtcari.getText().toString().replaceAll(" ", "+");
		            	setQuery(cari);
		            	//selectItem(31);
		            	startActivity(new Intent(getActivity(), Home2.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("NAV_ID", 25).putExtra("QUERY",cari));
		            	getActivity().finish();
		                dialog.dismiss();
						
					}
				});
				
				cmd_cancle_search.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
			/*
				// TODO Auto-generated method stub
				AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
		        LayoutInflater inflater = getActivity().getLayoutInflater();
		        View layout = inflater.inflate(R.layout.popup_cari, null);
		        layout.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		        
		        imageDialog.setView(layout);
		        final EditText txtcari = (EditText)layout.findViewById(R.id.txtcari);
		        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
		          

					public void onClick(DialogInterface dialog, int which) {
		            	//Toast.makeText(getActivity(), "Cari : "+ txtcari.getText(), Toast.LENGTH_LONG).show();
						String cari = txtcari.getText().toString().replaceAll(" ", "+");
		            	setQuery(cari);
		            	//selectItem(31);
		            	startActivity(new Intent(getActivity(), Home2.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("NAV_ID", 31).putExtra("QUERY",cari));
		            	getActivity().finish();
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
		      	*/
			}
		
		});
    	
        mDrawerListView = (ListView)rootView.findViewById(R.id.list_nav);
        mDrawerListView2 = (ListView)rootView.findViewById(R.id.list_nav_favorite);
        mDrawerListView3 = (ListView)rootView.findViewById(R.id.list_nav_all);
        
        String[] nav_title = getResources().getStringArray(R.array.nav_name);
        String[] nav_id = getResources().getStringArray(R.array.canal_id);
        TypedArray nav_icon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        array_nav = new ArrayList<Frame_nav>();
        array_nav_favorite = new ArrayList<Frame_nav>();
        array_nav_all = new ArrayList<Frame_nav>();
        array_nav_saved = new ArrayList<Frame_nav>();
        for (int i = 0; i < 7; i++) {
			 array_nav.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
		}
        

        array_nav_favorite.addAll(db.get_all_nav_cek());
	
        for (int i = 7; i <(nav_title.length-1); i++) {
        	array_nav_all.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
        }   

         adapter = new Adapter_Nav(getActivity(),R.layout.list_nav,array_nav);
        mDrawerListView.setAdapter(adapter);
         adapter2 = new Adapter_Nav(getActivity(),R.layout.list_nav,array_nav_favorite);
        mDrawerListView2.setAdapter(adapter2);
         adapter3 = new Adapter_Nav(getActivity(),R.layout.list_nav,array_nav_all);
		mDrawerListView3.setAdapter(adapter3);
		
		/*
        mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                }));
                */       
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
	
		updateListViewHeight(mDrawerListView);
		updateListViewHeight(mDrawerListView2);
		updateListViewHeight(mDrawerListView3);
        nav_icon.recycle();
        
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	mDrawerListView2.setItemChecked(99, true);
				mDrawerListView3.setItemChecked(99, true);
                selectItem(position);
            }
        });
        mDrawerListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	//view.setBackgroundResource(android.R.color.white);
            	mDrawerListView.setItemChecked(99, true);
				mDrawerListView3.setItemChecked(99, true);
                selectItem(Integer.parseInt(view.getContentDescription().toString()));
            }
        });
        
        mDrawerListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//arg1.setBackgroundResource(android.R.color.white);
				mDrawerListView.setItemChecked(99, true);
				mDrawerListView2.setItemChecked(99, true);
				 selectItem(position+7);
				//array_nav_favorite.remove(0);
                //adapter2.notifyDataSetChanged();
                //updateListViewHeight(mDrawerListView2);
			}
		});
    	//mDrawerListView2.setItemChecked(mCurrentSelectedPosition, true);
	//	mDrawerListView3.setItemChecked(mCurrentSelectedPosition, true);
		
        
        cmd_favorite= (ImageView)rootView.findViewById(R.id.cmd_favorite);
        
        cmd_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {}
		});
       
        return rootView;
    }
         

	public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
       
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeeeee")));
        actionBar.setIcon(R.color.abu_abu);
        actionBar.setCustomView(R.layout.action_bar);
        

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.nav_draw,             /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

       // mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void selectItem(int position) {
        mCurrentSelectedPosition = position;
   
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
            if(position==0){
            	 mDrawerListView2.setItemChecked(99, true);
            	 mDrawerListView3.setItemChecked(99, true);
            }
         }
        if (mDrawerListView2 != null) {
          //  mDrawerListView2.setItemChecked(position, true);
         }
        if (mDrawerListView3 != null) {
           // mDrawerListView3.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
    	// ActionBar actionBar = getActionBar();
       //  actionBar.setDisplayShowTitleEnabled(true);
       //  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      //   actionBar.setTitle(R.string.app_name);
        if (mDrawerLayout != null && isDrawerOpen()) {
           // inflater.inflate(R.menu.menu_nav, menu);
           // showGlobalContextActionBar();
        }
        
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
		case R.id.menu_saved:
			 Intent mainIntent = new Intent(getActivity(),Activity_Saved.class);
             mainIntent.putExtra("canal","breaking");
             startActivity(mainIntent);
			break;

		default:
			break;
		}
        
        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
       // ActionBar actionBar = getActionBar();
       // actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
      //  actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
    
    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {            
                 return;
        }
       //get listview height
       int totalHeight = 0;
       int adapterCount = myListAdapter.getCount();
       for (int size = 0; size < adapterCount ; size++) {
           View listItem = myListAdapter.getView(size, null, myListView);
           listItem.measure(0,0);
           totalHeight += listItem.getMeasuredHeight();
       }
       //Change Height of ListView 
       ViewGroup.LayoutParams params = myListView.getLayoutParams();
       params.height = totalHeight + (myListView.getDividerHeight() * (adapterCount - 1));
       myListView.setLayoutParams(params);
   }
}
