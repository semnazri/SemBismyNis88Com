package bisnis.com.official;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import bisnis.com.official.home.Activity_Home_Old;
import db.Table_ListBerita;
import db.Table_Setting;
import example.CustomExpandListView;
import example.FullDrawerLayout;
import example.ImageZoom;
import koneksi.Json_Connector;
import bisnis.com.official.R;
import bisnis.com.official.R.color;
import bisnis.com.official.AnalyticBisnisApp.TrackerName;
import bisnis.com.official.Home.PlaceholderFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import lib.anime_pager.ZoomOutPageTransformer;
import model.Frame_ListBerita;;

public class Activity_Detail_Terkait extends ActionBarActivity
        implements NavigationDrawerFragment3.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment3 mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    ViewPager mViewPager;
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static int CANAL_ID;

	private boolean flag_nav=false;

	private Menu optionsMenu;

	private ArrayList<Frame_ListBerita> data;

	private ProgressBar proses;

	private String post_id;

	private static int save_position;



    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle act_param = getIntent().getExtras();
        CANAL_ID=act_param.getInt("NAV_ID");
        //b=xnim.getString("canal");\
     
		post_id= act_param.getString("POST_ID");
		data = new ArrayList<Frame_ListBerita>((Collection) act_param.getParcelableArrayList("DATA"));
	//	Toast.makeText(getApplication(), " BOOK + "+  data.size(), Toast.LENGTH_LONG).show();
		//nav_menu = xnim.getInt("nav_menu");
		
        
    	// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
        mNavigationDrawerFragment = (NavigationDrawerFragment3)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (FullDrawerLayout) findViewById(R.id.drawer_layout));
        
       onSectionAttached(act_param.getInt("NAV_ID"));
       onNavigationDrawerItemSelected(act_param.getInt("NAV_ID"));
       mNavigationDrawerFragment.selectItem(act_param.getInt("NAV_ID"));
       
       proses = (ProgressBar)findViewById(R.id.prosesdata);
       for (int i = 0; i < data.size(); i++) {
			if(data.get(i).getIdberita().equals(post_id)){
				mViewPager.setCurrentItem(i);
				save_position=i;
			}
			
			proses.setVisibility(View.INVISIBLE);
			
		}



        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());

    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
       
    }
    
	@Override
    protected void onRestart() {
        super.onRestart();
        
    }
    
    public void set_pager(int number) {
    	mViewPager.setCurrentItem(number);
    }
    
    public void onPause(){
    	super.onPause();
    	//finish();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    	System.out.println("apuse");
    }
    public void onStop(){
    	super.onStop();
     	System.out.println("stope");
    	//finish();
    }
    

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //Toast.makeText(getApplication(), "as " +position, Toast.LENGTH_LONG).show();
        if(flag_nav){
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
    }

    public void onSectionAttached(int number) {
    	String nav_menu[] = getResources().getStringArray(R.array.nav_name);
    	 mTitle =nav_menu[number];
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
		 //   inflater.inflate(R.menu.menu_refresh, menu);
		    
		  //  MenuItem searchItem = menu.findItem(R.id.action_search);
		  //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		    // Search view for api 8
		    flag_nav=true;
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
	            	Toast.makeText(getApplication()	, "OK "+ txtcari.getText(), Toast.LENGTH_SHORT).show();
	            	String query =txtcari.getText().toString();
	            	query=query.replaceAll(" ", "+");
	            	startActivity(new Intent(getApplicationContext(), Home2.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("NAV_ID", 31).putExtra("QUERY",query));
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
				//setRefreshActionButtonState(true);
				startActivity(getIntent());
				this.finish();
					//refreshItem.setActionView(null);
				//refreshItem.setActionView(R.layout.loading);
				//onNavigationDrawerItemSelected(refresh_flag);
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
			// below).
			
			return PlaceholderFragment.newInstance(position,data.get(position),data.size());
			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return data.size();
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
        private static final String PAGE_NUMBER = "section_number";
        Adapter_DetailBerita adapterheadline;
		private TextView txtdate;
		public TextView txtnext,txtprevious;
		private ProgressBar porses2;
		
		ImageView imgberita,img_live_detail;
		TextView txtjudul,txtdate2,txtpenulis,txtimg_caption,txteditor,txtimg_sumber,txt_oldest,txt_lastest;
		WebView webdetail,web_live;
		File sdCardDirectory = Environment.getExternalStorageDirectory();
		
		CustomExpandListView list_terkait,list_live;
		private Frame_ListBerita data_save = new Frame_ListBerita();
		private ArrayList<Frame_ListBerita> array_list_terkait = new ArrayList<Frame_ListBerita>();
		Adapter_List_Terkait adapter_list_terkait;
		TableRow table_live,table_series,table_series_bottom;
		
		TableRow cmd_lastest,cmd_olderst;
		
		 int font_size,refresh_interfal,nav_setting;
			boolean setting_save = false;
			int size_font;
			
		String live_save;
		
		
		TextView txtnextseries,txtpreviousseries,txtnextseriesbottom,txtpreviousseriesbottom,txtseries,txtseriesbottom,txtjuduldetailseries;
		
		public static Frame_ListBerita data_series;
	    Json_Connector con;
		private TextView txtpage;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         * @param cANAL_ID 
         */
        public static PlaceholderFragment newInstance(int sectionNumber,Frame_ListBerita frame_ListBerita,int SIZE) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PAGE_NUMBER, sectionNumber);
            args.putInt("PAGE", sectionNumber);
			args.putString("DATE",frame_ListBerita.getDatepost());
			args.putString("CATAGORI_ID",frame_ListBerita.getCategory());
			args.putString("POST_ID",frame_ListBerita.getIdberita());
			args.putParcelable("SAVE", frame_ListBerita);
			args.putString("JUDUL",frame_ListBerita.getJudulberita());
			args.putString("TITLE",frame_ListBerita.getSlug());
			args.putString("IS_LIVE",frame_ListBerita.getLive());
			save_position=sectionNumber;
			//data_save=frame_ListBerita;
			args.putInt("MAX_PAGE",SIZE);
            fragment.setArguments(args);
            return fragment;
        }
        
        @Override
        public void onResume() {
            super.onResume();  // Always call the superclass method first

			// TODO Auto-generated method stub
		    Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
	        if(font_size!=db.get_setting_font_size()||refresh_interfal!=db.get_setting_refresh()){
	        	//  Toast.makeText(getActivity(), "Restrat  " , Toast.LENGTH_LONG).show();
	        	  font_size= db.get_setting_font_size();
	        	  size_font=db.get_setting_font_size();
	              refresh_interfal = db.get_setting_refresh();
	              setting_save=true;
	              db.close();
	            switch (size_font) {
                    case 0:
                        size_font = 16;
                        break;
                    case 1:
                        size_font = 18;
                        break;
                    case 2:
                        size_font = 20;
                        break;
                    case 3:
                        size_font = 22;
                        break;

                    default:
                        break;
	      		}
	            txtjudul.setTextSize(size_font+4);
			    txtpenulis.setTextSize(size_font-2);
			    txteditor.setTextSize(size_font-2);
                String css= "<style> .detail{ font-size:"+(size_font+2)+"px; } img{width:100%;} p{line-height:28px;} </style>";
			    webdetail.loadData(css+"<div class='detail'>"+webdetail.getContentDescription()+"</div>", "text/html", "utf-8");
			    list_terkait.invalidateViews();
			    web_live.loadUrl(live_save+size_font);
	              
	        }
	        db.close();
		
        }
        

		public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                Bundle savedInstanceState) {
        	String canal_id [] = getActivity().getResources().getStringArray(R.array.canal_id);
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
         //   TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           // textView.setText(getArguments().getInt(PAGE_NUMBER)+" opopopopp");
            Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
             size_font = db.get_setting_font_size();
             font_size = db.get_setting_font_size();
             refresh_interfal =db.get_setting_refresh();
        	db.close();
        	switch (size_font) {
                case 0:
                    size_font = 16;
                    break;
                case 1:
                    size_font = 18;
                    break;
                case 2:
                    size_font = 20;
                    break;
                case 3:
                    size_font = 22;
                    break;

                default:
                    break;
    		}
            
            Tracker t = ((AnalyticBisnisApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
	        t.setScreenName("apps/read/"+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+ getArguments().getString("POST_ID")+"/"+getArguments().getString("TITLE"));
	        t.send(new HitBuilders.AppViewBuilder().build());
	        t.enableAutoActivityTracking(false);
	        data_save=getArguments().getParcelable("SAVE");
       
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM  yyyy");
	        Date date = new Date();
	      
	        porses2 = (ProgressBar)rootView.findViewById(R.id.prosesdata);
	        porses2.setVisibility(View.VISIBLE);
	      //  File image2 = null ;
         
	        final RelativeLayout laytout_share  = (RelativeLayout)rootView.findViewById(R.id.layout_share);
		       txtdate= (TextView)rootView.findViewById(R.id.txttgl);
		       txtnext = (TextView)rootView.findViewById(R.id.txtnext);
		       txtprevious= (TextView)rootView.findViewById(R.id.txtprevious);
		       
		       txtjudul= (TextView)rootView.findViewById(R.id.txt_detail_judul);
		       txtjudul.requestFocus();
		       txtdate2 = (TextView)rootView.findViewById(R.id.txtdate);
		       txtimg_caption= (TextView)rootView.findViewById(R.id.txtimg_caption);
		       txtpenulis= (TextView)rootView.findViewById(R.id.txt_detail_penulis);
		       txteditor = (TextView)rootView.findViewById(R.id.txteditor);
		       imgberita= (ImageView)rootView.findViewById(R.id.imgfitured);
		       webdetail = (WebView)rootView.findViewById(R.id.webdetail);
		       web_live = (WebView)rootView.findViewById(R.id.web_live);
		       txtimg_sumber = (TextView)rootView.findViewById(R.id.txtimg_sumber);
		       list_terkait = (CustomExpandListView)rootView.findViewById(R.id.list_terkait);
		       table_live =(TableRow)rootView.findViewById(R.id.table_live);
		       cmd_lastest =(TableRow)rootView.findViewById(R.id.cmd_latest);
		       cmd_olderst =(TableRow)rootView.findViewById(R.id.cmd_older);
		       img_live_detail = (ImageView)rootView.findViewById(R.id.img_live_detail);
		       table_live = (TableRow)rootView.findViewById(R.id.table_live);
		       txt_lastest = (TextView)rootView.findViewById(R.id.txt_lastest);
		       txt_oldest = (TextView)rootView.findViewById(R.id.txt_older);
		       
		       //series
		       table_series = (TableRow)rootView.findViewById(R.id.tableRowSeries);
		       table_series_bottom= (TableRow)rootView.findViewById(R.id.tableRowSeriesbottom);
		       txtjuduldetailseries=(TextView)rootView.findViewById(R.id.txtjuduldetailseries);
		       txtseries = (TextView)rootView.findViewById(R.id.txtseries);
		       txtseriesbottom=(TextView)rootView.findViewById(R.id.txtseriesbottom);
		       txtnextseries =(TextView)rootView.findViewById(R.id.txtnextseries);
		       txtpreviousseries = (TextView)rootView.findViewById(R.id.txtpreviousseries);
		       txtnextseriesbottom=(TextView)rootView.findViewById(R.id.txtnextseriesbottom);
		       txtpreviousseriesbottom=(TextView)rootView.findViewById(R.id.txtpreviousseriesbottom);
		       //end series
		       
		     
		       
		       
		       
		       txtjudul.setTextSize(size_font+4);
		       txtpenulis.setTextSize(size_font-2);
		       txteditor.setTextSize(size_font-2);
            String css= "<style> .detail{ font-size:"+(size_font+2)+"px; } img{width:100%;} p{line-height:28px;} </style>";
		       webdetail.setContentDescription(css);
		       
		       if(getArguments().getString("IS_LIVE").equals("0")){
		    	   table_live.setVisibility(View.GONE);
		    	   img_live_detail.setVisibility(View.GONE);
		    	   web_live.setVisibility(View.GONE);
		    	   txtjudul.setText(getArguments().getString("JUDUL"));
		       }else{
		    	   txtjudul.setText(""+getArguments().getString("JUDUL"));
		       }
		       
		       
		       adapter_list_terkait = new Adapter_List_Terkait(getActivity(), R.layout.list_terkait,array_list_terkait);
		       
		       list_terkait.setAdapter(adapter_list_terkait); 
		    
		
		       list_terkait.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(getActivity(), Activity_Detail_Terkait.class);
					intent.putExtra("POST_ID",array_list_terkait.get(arg2).getIdberita());
					intent.putExtra("CANAL",array_list_terkait.get(arg2).getCategory());
					intent.putExtra("DATA", array_list_terkait);
					intent.putExtra("NAV_ID",getArguments().getString("CATAGORI_ID"));
					//getActivity().finish();
					startActivity(intent);
					
				}
			});
		       
		       final ScrollView scrollbertia = (ScrollView)rootView.findViewById(R.id.scrolldetailberita);
		       
		       final ImageView imgshare = (ImageView)rootView.findViewById(R.id.imgshare);
		       final ImageView imgsave =(ImageView)rootView.findViewById(R.id.imgsave);
		       final ImageView imgcomment =(ImageView)rootView.findViewById(R.id.imgcomment);
		       
		       txtdate.setText(dateFormat.format(date));
		       
		       txtpage=(TextView)rootView.findViewById(R.id.txt_page);
		       txtpage.setText((getArguments().getInt("PAGE")+1)+"/"+getArguments().getInt("MAX_PAGE"));
		       
		       
		       if(getArguments().getInt("PAGE")==0&(getArguments().getInt("MAX_PAGE")-1)==0){
		    	   txtnext.setVisibility(View.GONE);
		    	   txtprevious.setVisibility(View.GONE);
		       }else if(getArguments().getInt("PAGE")==0){
		    	   txtnext.setVisibility(View.GONE);
		       }else if(getArguments().getInt("PAGE")==(getArguments().getInt("MAX_PAGE")-1)){
		    	   txtprevious.setVisibility(View.GONE);
		       }
		       
		       txtprevious.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Activity_Detail_Backup)getActivity()).set_pager(getArguments().getInt("PAGE")+1);
				}
			});
		       
		       txtnext.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Activity_Detail_Backup)getActivity()).set_pager(getArguments().getInt("PAGE")-1);
				}
			});
		       
		      
		       WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		       Display display = wm.getDefaultDisplay();
		       Point size = new Point();
		       con= new Json_Connector();
		       
		    //  new DataDownloaderOfDetail(porses2,txtjudul,txtdate2,txtimg_caption,txtpenulis,txteditor,webdetail,imgberita,imgshare,imgcomment,adapter_list_terkait,list_terkait,txtimg_sumber).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+ getArguments().getString("POST_ID"));
		       new DataDownloaderOfDetail(porses2,txtjudul,txtdate2,txtimg_caption,txtpenulis,txteditor,webdetail,imgberita,imgshare,imgcomment,adapter_list_terkait,list_terkait,txtimg_sumber,table_series,table_series_bottom,txtnextseries,txtnextseriesbottom,txtseries,txtseriesbottom).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+ getArguments().getString("POST_ID"));
			    

		       imgberita.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

                        // TODO Auto-generated method stub
                        //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
                        ImageView tempImageView = imgberita;
                        System.out.println(imgberita.getContentDescription());

                        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

                        View layout = inflater.inflate(R.layout.popup_image,container,false);
                        ImageZoom image = (ImageZoom) layout.findViewById(R.id.imgfull);
                        ProgressBar proses = (ProgressBar)layout.findViewById(R.id.imgproses);
                        //   image.setImageDrawable(tempImageView.getDrawable());
                        new	 ImageDownloaderTaksFullPopUp(image,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imgberita.getContentDescription().toString().replaceAll(" ", "%20"));
                        // new ImageDownloaderTaksFull(image,proses).executeOnExecutor(.THREAD_POOL_EXECUTOR,image_uri);
                        imageDialog.setView(layout);
                        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        });


                        imageDialog.create();
                        imageDialog.show();


                    }
				});
		       
		       imgcomment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					//Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
					System.out.println(imgberita.getContentDescription());
					
					if(!(porses2.isShown())){
					Dialog dialog = new Dialog(getActivity());
					dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
					dialog.setContentView(R.layout.popup_comment);
					dialog.setTitle("Comments");
					dialog.setCancelable(true);
					dialog.show();
					dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);

					WebView web_comment = (WebView) dialog.findViewById(R.id.web_comment);
			      /*  AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
			
			        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE)
			        View layout = inflater.inflate(R.layout.popup_comment,container,false);
			        WebView web_comment = (WebView) layout.findViewById(R.id.web_comment);
			        */
			        
			       web_comment.loadUrl("http://services.bisnis.com/json/comment/"+getArguments().getString("POST_ID")+"/"+imgcomment.getContentDescription());
			      // Toast.makeText(getActivity(), imgcomment.getContentDescription(), Toast.LENGTH_LONG).show();
			 /*   	String script ="<div id='fb-root'></div>"+
										"<script>(function(d, s, id) {"+
										 " var js, fjs = d.getElementsByTagName(s)[0];"+
										  "if (d.getElementById(id)) return;"+
										  "js = d.createElement(s); js.id = id;"+
										  "js.src = '//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=118466595028310&version=v2.0';"+
										  "fjs.parentNode.insertBefore(js, fjs);"+
										"}(document, 'script', 'facebook-jssdk'));</script>"+
										"<div class='fb-comments' data-href='http://"+imgcomment.getContentDescription()+"' data-numposts='5' data-colorscheme='light'></div>";
			    	*/
			     //   web_comment.loadData(script, "text/html", "UTF-8");
			    	 WebSettings webSettings2 = web_comment.getSettings();
			         webSettings2.setJavaScriptEnabled(true);
			         webSettings2.setAppCacheEnabled(true);
			         web_comment.setWebChromeClient(new WebChromeClient());
			         web_comment.setWebViewClient(new WebViewClient());
			         web_comment.requestFocus(View.FOCUS_DOWN);
			         web_comment.setOnTouchListener(new OnTouchListener() {
			             @Override
			             public boolean onTouch(View v, MotionEvent event) {
			                 switch (event.getAction()) {
			                     case MotionEvent.ACTION_DOWN:
			                     case MotionEvent.ACTION_UP:
			                         if (!v.hasFocus()) {
			                             v.requestFocus();
			                            // Toast.makeText(getActivity(), "OK", Toast.LENGTH_LONG).show();
			                         }
			                         break;
			                 }
			                 return false;
			             }
			         });
			        //imageDialog.setView(layout);
			       // imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			      //      public void onClick(DialogInterface dialog, int which) {
			    //            dialog.dismiss();
			   //         }

			    //    });
			       
			      //  imageDialog.create();
			        //imageDialog.show();     
					}
				}
			});
		      
		       
		       imgshare.setOnClickListener(new OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent sharingIntent = new Intent(Intent.ACTION_SEND);
						sharingIntent.setType("text/plain");
						String title = txtjudul.getText().toString();
						String shareBody = imgshare.getContentDescription().toString();
						sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
						sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + shareBody);
						startActivity(Intent.createChooser(sharingIntent, "Share via"));
					}
				});
				
				imgsave.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Table_ListBerita db = new Table_ListBerita(getActivity(), null, null, 0);
						 db.addBerita(data_save);
						 db.close();
						Toast.makeText(getActivity(), "Berita Sudah Di Bookmarks", Toast.LENGTH_LONG).show();
					}
				});
				
			
				 scrollbertia.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						laytout_share.setVisibility(View.INVISIBLE);
						//Toast.makeText(getActivity(), " event "+ event.getAction(), Toast.LENGTH_SHORT).show();
						v.getParent().requestDisallowInterceptTouchEvent(true);
						if(event.getAction()==1){
                            Animation hiden = AnimationUtils.loadAnimation(getActivity(), R.anim.show);
                            laytout_share.startAnimation(hiden);
                            laytout_share.setVisibility(View.VISIBLE);
						}
					
						return false;
					}
				});
				 
			live_save ="http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/latest/";
			web_live.loadUrl("http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/latest/"+size_font);
			
			cmd_lastest.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					cmd_olderst.setBackgroundResource(color.back_table_live);
					cmd_lastest.setBackgroundResource(color.back_button_live);
					txt_lastest.setTextColor(Color.WHITE);
					txt_oldest.setTextColor(color.back_text_live);
					web_live.loadUrl("http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/latest/"+size_font);
					live_save ="http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/latest/";
				}
			});
			
			cmd_olderst.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cmd_lastest.setBackgroundResource(color.back_table_live);
					cmd_olderst.setBackgroundResource(color.back_button_live);
					web_live.loadUrl("http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/oldnest/"+size_font);
					live_save ="http://services.bisnis.com/json/live/"+ getArguments().getString("POST_ID")+"/oldnest/";
					txt_oldest.setTextColor(Color.WHITE);
					txt_lastest.setTextColor(color.back_text_live);
				}
			});
			
			 Setting_Changes();
			 
			 WebView web_iklan = (WebView)rootView.findViewById(R.id.web_iklan);
			
			 String skript_iklan = "<script type='text/javascript'><!--//<![CDATA["+
   "var m3_u = (location.protocol=='https:'?'https://ads.bisnis.com/www/delivery/ajs.php':'http://ads.bisnis.com/www/delivery/ajs.php');"+
   "var m3_r = Math.floor(Math.random()*99999999999);"+
   "if (!document.MAX_used) document.MAX_used = ',';"+
   "document.write ('<scr'+'ipt type='text/javascript' src=''+m3_u);"+
   "document.write ('?zoneid=157');"+
   "document.write ('&amp;cb=' + m3_r);"+
   "if (document.MAX_used != ',') document.write ('&amp;exclude=' + document.MAX_used);"+
   "document.write (document.charset ? '&amp;charset='+document.charset : (document.characterSet ? '&amp;charset='+document.characterSet : ''));"+
   "document.write ('&amp;loc=' + escape(window.location));"+
   "if (document.referrer) document.write ('&amp;referer=' + escape(document.referrer));"+
   "if (document.context) document.write ('&context=' + escape(document.context));"+
   "if (document.mmm_fo) document.write ('&amp;mmm_fo=1');"+
   "document.write (''><scr'+'ipt>');"+
   "//]]>--></script><noscript><a href='http://ads.bisnis.com/www/delivery/ck.php?n=a38cf17d&amp;cb=INSERT_RANDOM_NUMBER_HERE' target='_blank'><img src='http://ads.bisnis.com/www/delivery/avw.php?zoneid=157&amp;cb=INSERT_RANDOM_NUMBER_HERE&amp;n=a38cf17d' border='0' alt='' /></a></noscript>";
            
	//web_iklan.loadData(skript_iklan, "text/html", "utf-8");
	web_iklan.loadUrl("http://services.bisnis.com/json/ads_detail");
	 WebSettings webSettings = web_iklan.getSettings();
     webSettings.setJavaScriptEnabled(true);
     //webSettings2.setAppCacheEnabled(true);
  // WebView web_iklan_menu_item = (WebView)rootView.findViewById(R.id.web_iklan_menu_item);
    // web_iklan_menu_item.loadUrl("http://services.bisnis.com/json/ads_detail");
	// WebSettings webSettings2 = web_iklan.getSettings();
    // webSettings2.setJavaScriptEnabled(true);
	
     txtnextseries.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new PlaceholderFragment();
				// TODO Auto-generated method stub
				porses2.setVisibility(View.VISIBLE);
				 new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+txtnextseries.getContentDescription());
				    
				    
				 
				//  new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtprevious,webdetail,imgberita,imgshare,imgcomment,txtnextseriesbottom,txtpreviousseriesbottom,txtimg_sumber,scrollbertia).execute(con.detail+"20141224/104/"+txtnextseries.getContentDescription());
				    
				//Toast.makeText(getActivity(), "NEXT", Toast.LENGTH_LONG).show();
			}
		});
     
     txtpreviousseries.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			porses2.setVisibility(View.VISIBLE);
			new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+txtpreviousseries.getContentDescription());
			
			 
		}
	});
     
     txtnextseriesbottom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new PlaceholderFragment();
				// TODO Auto-generated method stub
				porses2.setVisibility(View.VISIBLE);
				 new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+txtnextseries.getContentDescription());
				    
					    
				//Toast.makeText(getActivity(), "NEXT", Toast.LENGTH_LONG).show();
				
			}
		});
  
     txtpreviousseriesbottom.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			porses2.setVisibility(View.VISIBLE);
			 new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+txtpreviousseries.getContentDescription());
			
		}
	});
	     
	       
     
     
     return rootView;
        }
        
        public void Setting_Changes(){
        	new CountDownTimer(500,1000) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFinish() {
					
				}
			}.start();
        }
        

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Activity_Detail_Terkait) activity).onSectionAttached(CANAL_ID);
        }
    }

}
