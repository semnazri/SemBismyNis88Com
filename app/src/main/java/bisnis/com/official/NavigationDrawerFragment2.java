package bisnis.com.official;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.drive.internal.ac;

import db.Table_List_Nav;
import model.Frame_nav;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 *
public class NavigationDrawerFragment2 extends Fragment {

    /**
     * Remember the position of the selected item.
     *
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     *
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     *
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     *
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView,mDrawerListView2,mDrawerListView3;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private ExpandableListView expand_nav;
    Adapter_Nav_New listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<Frame_nav>> listDataChild;
    Adapter_Nav adapter,adapter2,adapter3;
    ArrayList<Frame_nav> array_nav,array_nav_favorite,array_nav_all;
 
    public NavigationDrawerFragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
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
    	
    	 listAdapter = new Adapter_Nav_New(getActivity(), listDataHeader, listDataChild);
    	 
         // setting list adapter
    	// expand_nav.setAdapter(listAdapter);
    	 
    	// expand_nav.expandGroup(0);
    	// expand_nav.expandGroup(1);
    	 
    	 expand_nav.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "OK "+v.getContentDescription(), Toast.LENGTH_SHORT).show();
				selectItem(v.getId());
				return false;
			}
		});
    	
        mDrawerListView = (ListView)rootView.findViewById(R.id.list_nav);
        mDrawerListView2 = (ListView)rootView.findViewById(R.id.list_nav_favorite);
       mDrawerListView3 = (ListView)rootView.findViewById(R.id.list_nav_all);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	view.setBackgroundResource(android.R.color.white);
                selectItem(position);
            }
        });
      
        
        String[] nav_title = getResources().getStringArray(R.array.nav_name);
        String[] nav_id = getResources().getStringArray(R.array.canal_id);
        TypedArray nav_icon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        array_nav = new ArrayList<>();
        	array_nav_favorite = new ArrayList<>();
        	array_nav_all = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
			 array_nav.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
		}
        for (int i = 6; i <(nav_title.length-1); i++) {
        	array_nav_all.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
        }
		
       

         adapter = new Adapter_Nav(getActivity(),R.layout.list_nav,array_nav);
        mDrawerListView.setAdapter(adapter);
         adapter2 = new Adapter_Nav(getActivity(),R.layout.list_nav,array_nav);
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
                /      
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		//mDrawerListView2.setItemChecked(mCurrentSelectedPosition, true);
		//mDrawerListView3.setItemChecked(mCurrentSelectedPosition, true);
		
		updateListViewHeight(mDrawerListView);
		updateListViewHeight(mDrawerListView2);
		updateListViewHeight(mDrawerListView3);
        nav_icon.recycle();
        
        mDrawerListView3.setOnClickListener(new AdapterView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.color.white);
				array_nav.remove(1);
				adapter2.notifyDataSetChanged();
			}
			});
        return rootView;
    }

    private void prepareListData() {
		// TODO Auto-generated method stub
    	 listDataHeader = new ArrayList<String>();
         listDataChild = new HashMap<String, List<Frame_nav>>();
         
         String[] nav_title = getResources().getStringArray(R.array.nav_name);
         String[] nav_id = getResources().getStringArray(R.array.canal_id);
         TypedArray nav_icon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
         String [] canal = getResources().getStringArray(R.array.nav_name);
        // array_nav.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1)));
         // Adding child data
         listDataHeader.add("Front Page");
         listDataHeader.add("Favorite Canal");
         listDataHeader.add("Canal List");

  
         // Adding child data
         List<Frame_nav> list_font = new ArrayList<Frame_nav>();
         for (int i = 0; i <6; i++) {
        	 list_font.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
		}
         
         Table_List_Nav db = new Table_List_Nav(getActivity(), null, null, 0);
       
         ArrayList<Frame_nav> data_nav=new ArrayList<Frame_nav>();
      //   if(db.get_all_nav().equals(null)){
        	//  db.add_nav(new Frame_nav(nav_id[6],nav_title[6],nav_icon.getResourceId(6, -1),6));
        // }
         data_nav.addAll(db.get_all_nav());
        // Toast.makeText(getActivity(), "asd " +data_nav.get(2).nav, Toast.LENGTH_SHORT).show();
        // System.out.println("" +data_nav.get(2).nav);
         
         List<Frame_nav> list_pilihan = new ArrayList<Frame_nav>();
         list_pilihan.addAll(db.get_all_nav());
         for (int i = 6; i < 10; i++) {
        	// list_pilihan.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i));
		}
        
  
         List<Frame_nav> list_canal = new ArrayList<Frame_nav>();
         
         for (int i = 6; i < canal.length-1; i++) {
        	 list_canal.add(new Frame_nav(nav_id[i],nav_title[i],nav_icon.getResourceId(i, -1),i,0));
		}
  
      
  
         listDataChild.put(listDataHeader.get(0), list_font); // Header, Child data
         listDataChild.put(listDataHeader.get(1), list_pilihan);
         listDataChild.put(listDataHeader.get(2), list_canal);
         
	}

	public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     *
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
                getActivity(),                    /* host Activity *
                mDrawerLayout,                    /* DrawerLayout object 
                R.drawable.nav_draw,             /* nav drawer image to replace 'Up' caret *
                R.string.app_name,  /* "open drawer" description for accessibility *
                R.string.app_name  /* "close drawer" description for accessibility *
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
            inflater.inflate(R.menu.menu_nav, menu);
            showGlobalContextActionBar();
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
     *
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
     *
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         *
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
}*/