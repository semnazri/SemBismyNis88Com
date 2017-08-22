package bisnis.com.official;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import bisnis.com.official.home.Activity_Home_Old;
import db.Table_ListBerita;
import db.Table_Setting;
import example.CustomExpandListView;
import example.FullDrawerLayout;
import jazzyviewpager.JazzyViewPager;
import jazzyviewpager.JazzyViewPager.TransitionEffect;
import jazzyviewpager.OutlineContainer;
import koneksi.Json_Connector;
import model.Frame_ListBerita;

public class Activity_Detail_Backup2 extends ActionBarActivity
        implements NavigationDrawerFragment3.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment3 mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public int CANAL_ID;

    private boolean flag_nav=false;

    private Menu optionsMenu;

    private ArrayList<Frame_ListBerita> data;

    private ProgressBar proses_activity;

    private String post_id;

    private static int save_position;

    private JazzyViewPager mJazzy;



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


    TextView txtpage,txtnextseries,txtpreviousseries,txtnextseriesbottom,txtpreviousseriesbottom,txtseries,txtseriesbottom,txtjuduldetailseries;

    //public static Frame_ListBerita data_series;
    Json_Connector con;
    //detail

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail_new);
        Bundle act_param = getIntent().getExtras();
        CANAL_ID=act_param.getInt("NAV_ID");

        post_id= act_param.getString("POST_ID");
        data = new ArrayList<Frame_ListBerita>((Collection) act_param.getParcelableArrayList("DATA"));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mNavigationDrawerFragment = (NavigationDrawerFragment3)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (FullDrawerLayout) findViewById(R.id.drawer_layout));

        mNavigationDrawerFragment.selectItem(1);


        setupJazziness(TransitionEffect.Accordion);

        proses_activity = (ProgressBar)findViewById(R.id.prosesdata);
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getIdberita().equals(post_id)){
                mJazzy.setCurrentItem(i);
                save_position=i;
            }

            proses_activity.setVisibility(View.INVISIBLE);

        }


        adapter_list_terkait = new Adapter_List_Terkait(Activity_Detail_Backup2.this, R.layout.list_terkait,array_list_terkait);

    }
    public void set_pager(int number) {
        mJazzy.setCurrentItem(number);
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //Toast.makeText(getApplication(), "as " +position, Toast.LENGTH_LONG).show();
        if(flag_nav){
            if(position==0){
                Intent mainIntent = new Intent(this,Home3.class);
                mainIntent.putExtra("NAV_ID",0);
                startActivity(mainIntent);
            }else
            if(position==2){
                Intent mainIntent = new Intent(this,Home3.class);
                mainIntent.putExtra("NAV_ID",2);
                startActivity(mainIntent);
            }else
            if(position==3){
                Intent mainIntent = new Intent(this,Activity_Saved.class);
                mainIntent.putExtra("canal","breaking");
                startActivity(mainIntent);
            }else
            if(position>0&position<7){
                Intent mainIntent = new Intent(this,Home3.class);
                mainIntent.putExtra("NAV_ID",1);
                startActivity(mainIntent);
            }else{
//                Flag_Back=false;
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, Test_Fragment.newInstance(position))
//                        .commit();

                Intent mainIntent = new Intent(this,Activity_Home_Old.class);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void setupJazziness(TransitionEffect effect) {
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainAdapter3());
        mJazzy.setPageMargin(40);

    }

    private class MainAdapter3 extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View rootView = new View(Activity_Detail_Backup2.this.getApplicationContext());
            final LayoutInflater inflater = (LayoutInflater) Activity_Detail_Backup2.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.fragment_detail,container, false);


            Table_Setting db = new Table_Setting(Activity_Detail_Backup2.this, null, null, 0);
            size_font = db.get_setting_font_size();
            font_size = db.get_setting_font_size();
            refresh_interfal =db.get_setting_refresh();
            db.close();
            switch (size_font) {
                case 0:
                    size_font = 12;
                    break;
                case 1:
                    size_font = 14;
                    break;
                case 2:
                    size_font = 16;
                    break;
                case 3:
                    size_font = 18;
                    break;

                default:
                    break;
            }

            final Tracker t = ((AnalyticBisnisApp)getApplication()).getTracker(AnalyticBisnisApp.TrackerName.APP_TRACKER);
           // t.setScreenName("apps/read/"+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+ getArguments().getString("POST_ID")+"/"+getArguments().getString("TITLE"));
            t.setScreenName("apps/read/"+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+data.get(position).getIdberita()+"/"+data.get(position).getSlug());
            t.send(new HitBuilders.AppViewBuilder().build());
            t.enableAutoActivityTracking(true);
            data_save=data.get(position);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM  yyyy");
            final Date date = new Date();

            porses2 = (ProgressBar)rootView.findViewById(R.id.prosesdata);
            porses2.setVisibility(View.VISIBLE);


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
            String css= "<style> .detail{ font-size:"+(size_font+2)+"px; }</style>";
            webdetail.setContentDescription(css);

            if(data.get(position).getLive().equals("0")){
                table_live.setVisibility(View.GONE);
                img_live_detail.setVisibility(View.GONE);
                web_live.setVisibility(View.GONE);
                txtjudul.setText(data.get(position).getJudulberita());
            }else{
                txtjudul.setText("     "+data.get(position).getJudulberita());
            }

          //  adapter_list_terkait.clear();





            list_terkait.setAdapter(adapter_list_terkait);


            list_terkait.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setClass(Activity_Detail_Backup2.this, Activity_Detail_Terkait.class);
                    intent.putExtra("POST_ID",array_list_terkait.get(arg2).getIdberita());
                    intent.putExtra("CANAL",array_list_terkait.get(arg2).getCategory());
                    intent.putExtra("DATA", array_list_terkait);
                    intent.putExtra("NAV_ID",CANAL_ID);
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
            txtpage.setText(position+1+"/"+data.size());



            WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            con= new Json_Connector();

            //  new DataDownloaderOfDetail(porses2,txtjudul,txtdate2,txtimg_caption,txtpenulis,txteditor,webdetail,imgberita,imgshare,imgcomment,adapter_list_terkait,list_terkait,txtimg_sumber).execute(con.detail+getArguments().getString("DATE")+"/"+getArguments().getString("CATAGORI_ID")+"/"+ getArguments().getString("POST_ID"));
            new DataDownloaderOfDetail(porses2,txtjudul,txtdate2,txtimg_caption,txtpenulis,txteditor,webdetail,imgberita,imgshare,imgcomment,adapter_list_terkait,list_terkait,txtimg_sumber,table_series,table_series_bottom,txtnextseries,txtnextseriesbottom,txtseries,txtseriesbottom).execute(con.detail+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+data.get(position).getIdberita());


            imgberita.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
					/*	ImageView tempImageView = imgberita;
						System.out.println(imgberita.getContentDescription());

				        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
				        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

				        View layout = inflater.inflate(R.layout.popup_image,container,false);
				        ImageView image = (ImageView) layout.findViewById(R.id.imgfull);
				        ProgressBar proses = (ProgressBar)layout.findViewById(R.id.imgproses);
				     //   image.setImageDrawable(tempImageView.getDrawable());
				        new ImageDownloaderTaksFull(image,proses).execute(imgberita.getContentDescription().toString().replaceAll(" ", "%20"));
				       // new ImageDownloaderTaksFull(image,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,image_uri);
				        imageDialog.setView(layout);
				        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

				            public void onClick(DialogInterface dialog, int which) {
				                dialog.dismiss();
				            }

				        });


				        imageDialog.create();
				        imageDialog.show();

				        */
                }
            });




            imgcomment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    // TODO Auto-generated method stub
                    //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
                    System.out.println(imgberita.getContentDescription());

                    if(!(porses2.isShown())){
                        Dialog dialog = new Dialog(Activity_Detail_Backup2.this);
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

                        web_comment.loadUrl("http://services.bisnis.com/json/comment/"+data.get(position).getIdberita()+"/"+imgcomment.getContentDescription());
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
                        web_comment.setOnTouchListener(new View.OnTouchListener() {
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


            imgshare.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String title = txtjudul.getText().toString();
                    String shareBody = imgshare.getContentDescription().toString();
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,title + "\n" + shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });

            imgsave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Table_ListBerita db = new Table_ListBerita(Activity_Detail_Backup2.this, null, null, 0);
                    db.addBerita(data_save);
                    db.close();
                    Toast.makeText(Activity_Detail_Backup2.this, "Berita Sudah Di Bookmarks", Toast.LENGTH_LONG).show();
                }
            });


            scrollbertia.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub

                    // TODO Auto-generated method stub
                    laytout_share.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getActivity(), " event "+ event.getAction(), Toast.LENGTH_SHORT).show();
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if(event.getAction()==1){
                        laytout_share.setVisibility(View.VISIBLE);
                    }

                    return false;
                }
            });

            live_save ="http://services.bisnis.com/json/live/"+data.get(position).getIdberita()+"/latest/";
            web_live.loadUrl("http://services.bisnis.com/json/live/"+data.get(position).getIdberita()+"/latest/"+size_font);

            cmd_lastest.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    cmd_olderst.setBackgroundResource(R.color.back_table_live);
                    cmd_lastest.setBackgroundResource(R.color.back_button_live);
                    txt_lastest.setTextColor(Color.WHITE);
                    txt_oldest.setTextColor(R.color.back_text_live);
                    web_live.loadUrl("http://services.bisnis.com/json/live/"+data.get(position).getIdberita()+"/latest/"+size_font);
                    live_save ="http://services.bisnis.com/json/live/"+data.get(position).getIdberita()+"/latest/";
                }
            });

            cmd_olderst.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    cmd_lastest.setBackgroundResource(R.color.back_table_live);
                    cmd_olderst.setBackgroundResource(R.color.back_button_live);
                    web_live.loadUrl("http://services.bisnis.com/json/live/"+ data.get(position).getIdberita()+"/oldnest/"+size_font);
                    live_save ="http://services.bisnis.com/json/live/"+ data.get(position).getIdberita()+"/oldnest/";
                    txt_oldest.setTextColor(Color.WHITE);
                    txt_lastest.setTextColor(R.color.back_text_live);
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

            txtnextseries.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    porses2.setVisibility(View.VISIBLE);
                    new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+txtnextseries.getContentDescription());



                    //  new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtprevious,webdetail,imgberita,imgshare,imgcomment,txtnextseriesbottom,txtpreviousseriesbottom,txtimg_sumber,scrollbertia).execute(con.detail+"20141224/104/"+txtnextseries.getContentDescription());

                    //Toast.makeText(getActivity(), "NEXT", Toast.LENGTH_LONG).show();
                }
            });

            txtpreviousseries.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    porses2.setVisibility(View.VISIBLE);
                    new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+txtpreviousseries.getContentDescription());


                }
            });

            txtnextseriesbottom.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    porses2.setVisibility(View.VISIBLE);
                    new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+txtnextseries.getContentDescription());


                    //Toast.makeText(getActivity(), "NEXT", Toast.LENGTH_LONG).show();

                }
            });

            txtpreviousseriesbottom.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    porses2.setVisibility(View.VISIBLE);
                    new DataDownloaderOfDetailSeries(porses2,txtjuduldetailseries,txtseries,txtseriesbottom,txtnextseries,txtpreviousseries,webdetail,imgberita,txtimg_sumber,txtimg_caption,txtnextseriesbottom,txtpreviousseriesbottom,scrollbertia).execute(con.detail+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+txtpreviousseries.getContentDescription());

                }
            });




            container.addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mJazzy.setObjectForPosition(rootView, position);
            //mJazzy.setObjectForPosition(img_view, position);



           AsyncTask<String,Void,String> downloadterkait = new AsyncTask<String, Void, String>() {
               @Override
               protected String doInBackground(String... params) {
                   String response;
                   response = getRequest(params[0]);
                   System.out.println(params[0]);
                   return response;
               }

               @Override
               protected void onPreExecute() {
                   super.onPreExecute();

               }

               @Override
               protected void onPostExecute(String s) {
                   super.onPostExecute(s);


                   try {

                       JSONArray data_terkiat = new JSONArray(s);

                       if(adapter_list_terkait.getCount()<5) {
                           for (int i = 0; i < 5; i++) {
                               if (!isCancelled()) {
                                   JSONObject obj_terkait = obj_terkait = data_terkiat.getJSONObject(i);
                                   String[] post_date = obj_terkait.getString("post_date").split(" ");
                                   String[] date = post_date[0].split("-");
                                   adapter_list_terkait.add(new Frame_ListBerita(obj_terkait.getString("post_id"), obj_terkait.getString("category_id"), date[0] + date[1] + date[2], "", "", obj_terkait.getString("post_date") + " WIB", obj_terkait.getString("post_title"), "", "", obj_terkait.getString("slug"), "",obj_terkait.getString("subtitle")));
                               }
                           }
                       }

                       adapter_list_terkait.notifyDataSetChanged();

                       updateListViewHeight(list_terkait);



                   }catch (JSONException e){
                       e.printStackTrace();

                   }
                       //	array_list_berita.add(new Frame_ListBerita(post_id,obj3.getString("category"),url,array3.getString(0),tgl[2]+"/"+tgl[1]+"/"+tgl[0],time+" WIB", array2.getString(0).toString(),array3.getString(0),""));
                       //array_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=450", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal));
                       //array_list_terkait.add(new Frame_ListBerita(obj_terkait.getString("post_id"),obj_terkait.getString("category_id"),date[0]+date[1]+date[2], "","",obj_terkait.getString("post_date")+" WIB", obj_terkait.getString("post_title"),"","",obj.getString("slug")));

                   }



               };


           // downloadterkait.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,con.detailterkait+data.get(position).getDatepost()+"/"+data.get(position).getCategory()+"/"+data.get(position).getIdberita());




            return rootView;
        }
        public void Setting_Changes(){
//            new CountDownTimer(500,1000) {
//
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            }.start();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }
        @Override
        public int getCount() {
            return data.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }


    // buat connect ke server
    public String getRequest(String urls) {
        // TODO Auto-generated method stub
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new  HttpPost(urls);
        try{
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            request.setEntity(ent);
            HttpResponse response = client.execute(request);
            sret = request(response);
        }catch(Exception ex){
            //	Toast.makeText(this, "GAGAL" +sret, Toast.LENGTH_LONG).show();

        }

        return sret;
    }


    // buat menerima balasan dari server
    public String request(HttpResponse response) {
        // TODO Auto-generated method stub
        String result;
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                str.append(line).append("\n");
            }
            in.close();
            result =str.toString();
        }catch(Exception ex){
            result ="Error";
        }

        return result;
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
        LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (adapterCount - 1));
        myListView.setLayoutParams(params);
    }

}
