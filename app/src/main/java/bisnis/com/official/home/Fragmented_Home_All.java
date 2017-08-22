package bisnis.com.official.home;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.smaato.soma.AdDownloaderInterface;
import com.smaato.soma.AdListenerInterface;
import com.smaato.soma.BannerView;
import com.smaato.soma.ReceivedBannerInterface;
import com.smaato.soma.bannerutilities.constant.BannerStatus;
import com.smaato.soma.exception.AdReceiveFailed;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bisnis.com.official.Activity_Detail_Backup;
import bisnis.com.official.Adapter_List_Berita;
import bisnis.com.official.Adapter_List_Cari;
import bisnis.com.official.AnalyticBisnisApp;
import bisnis.com.official.Home3;
import bisnis.com.official.R;
import db.Table_Setting;
import example.AutoSpanRecyclerView;
import koneksi.Json_Connector;
import model.Frame_ListBerita;

/**
 * Created by sibertama on 1/27/15.
 */
public class Fragmented_Home_All extends Fragment{

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String CANAL_ID = "section_number";
    private AutoSpanRecyclerView listberita;
    //ArrayAdapter<String> arraylistberita = new ArrayAdapter<String>(null, 0);
    private ArrayList<Frame_ListBerita> array_list_berita = new ArrayList<Frame_ListBerita>();
    Adapter_List_Berita_Rec adapter_list_berita;
    Adapter_List_Cari adapter_List_Cari;
    private TextView txt_canal;
    private String canal;
    private int load=0;
    Date date = new Date();
    ProgressBar proses;
    View footerView ;
    Json_Connector con = new Json_Connector();
    Boolean flag =true;
    int limit=20,offset=0;
    GoogleAnalytics tracker;
    private int per_page=2,index_page=0;
    int timmer=5;

    AsyncTask<String, Void, String> download_data;

    private TextView index_start,index_much,index_day;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String id_canal = "id_canal";
    private static final String position = "number";

    SwipeRefreshLayout swipeLayout;

    //buat selisih tanggal
    Date waktuSatu,WaktuDua;
    String pola,waktuSatuStr,waktuDuaStr;
    SimpleDateFormat sdf ;


    private BannerView mBanner;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragmented_Home_All newInstance(int sectionNumber) {

        Fragmented_Home_All fragment = new Fragmented_Home_All();
        Bundle args = new Bundle();
        args.putInt(CANAL_ID, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragmented_Home_All() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        per_page=2;
        index_start = new TextView(getActivity());
        index_much = new TextView(getActivity());
        index_day = new TextView(getActivity());

        index_start.setContentDescription("20");
        index_much.setContentDescription("10");
        index_day.setContentDescription("0");
        String canal_id [] = getActivity().getResources().getStringArray(R.array.array_allchanel_id);
        String canal_name [] = getActivity().getResources().getStringArray(R.array.array_allchanel);
        View rootView = inflater.inflate(R.layout.fragment_home_ads, container, false);
        footerView= inflater.inflate(R.layout.loading, null, false);
        listberita = (AutoSpanRecyclerView)rootView.findViewById(R.id.listviewberita);
        txt_canal= (TextView)rootView.findViewById(R.id.txt_canal);
        tracker = GoogleAnalytics.getInstance(getActivity());
        proses = (ProgressBar)rootView.findViewById(R.id.prosesdata);
        canal=canal_id[getArguments().getInt(CANAL_ID)];
        //query= mNavigationDrawerFragment.getQuery();
        adapter_list_berita = new Adapter_List_Berita_Rec(array_list_berita);
        adapter_List_Cari = new Adapter_List_Cari(getActivity(), R.layout.list_cari,array_list_berita);



        txt_canal.setText(canal_name[getArguments().getInt(CANAL_ID)]);
        String apps=canal_name[getArguments().getInt(CANAL_ID)];
        Tracker t = ((AnalyticBisnisApp) getActivity().getApplication()).getTracker(AnalyticBisnisApp.TrackerName.APP_TRACKER);
        if(canal.equals("cari")){
            //  t.setScreenName("apps/"+apps.toLowerCase()+"/"+query);
        }else{
            t.setScreenName("apps/"+apps.toLowerCase());
        }

        t.send(new HitBuilders.AppViewBuilder().build());
        t.enableAutoActivityTracking(false);
        listberita.setAdapter(adapter_list_berita);
        listberita.setSpanCount(1);
        listberita.setHasFixedSize(true);

        listberita.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollColoredViewParallax(dy);
                Log.d("canal leght", listberita.getLastVisableItem() + " --> " + listberita.getItemCount() + "\n");
                if (canal.length() < 5 & (listberita.getLastVisableItem() >= listberita.getItemCount()) & (!proses.isShown() & (!proses.getContentDescription().equals("abis"))))
                //	if((listberita.getLastVisiblePosition()==(arg3-1))&(!proses.isShown())&!proses.getContentDescription().equals("abis"))
                {
                    Animation hiden = AnimationUtils.loadAnimation(getActivity(), R.anim.show);
                    proses.startAnimation(hiden);
                    proses.setVisibility(View.VISIBLE);
                    new CountDownTimer(1500, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub

                            refresh();

                        }
                    }.start();

                }
            }
        });

        System.out.print(" canal cari " +canal+"\n");

        if(canal.length()<4&(!canal.equals("cari"))){
            //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/"+canal);
            //  new DataDownloader(adapter_list_berita,proses).execute(con.URL+"headlines/1/"+canal+"/1");
            get_data(con.URL+"headlines/1/"+canal+"/3");
            //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/"+canal+"/"+offset+"/10");
            //offset=offset+1;
        }
        else if(canal.equals("cari")){
            proses.setContentDescription("baru");
            //  new DataDownloaderCari(adapter_List_Cari,proses).execute(con.URL+"search?q="+query);
            //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
        }
        else{
            proses.setContentDescription("baru");
            //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
            //  new DataDownloader(adapter_list_berita,proses).execute(con.URL+canal+"/1/0/20/0");
            get_data(con.URL+canal+"/1/0/20/0");
        }





        swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        adapter_list_berita.clean();
                        swipeLayout.setRefreshing(false);
                        offset=0;
                        index_start.setContentDescription("20");
                        index_much.setContentDescription("10");
                        index_day.setContentDescription("0");
                        if(canal.length()<4&(!canal.equals("cari"))){
                            // new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"headlines/1/"+canal+"/1");
                            get_data(con.URL+"headlines/1/"+canal+"/3");
                            //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                        }
                        else if(canal.equals("cari")){
                            proses.setContentDescription("baru");
                            //  new DataDownloaderCari(adapter_List_Cari,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"search?q="+query);
                            //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            proses.setContentDescription("baru");
                            //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
                            //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+canal+"/1/0/20/0");
                            get_data(con.URL+canal+"/1/0/20/0");
                        }
                    }
                }, 5000);
            }
        });
        swipeLayout.setColorScheme(R.color.blue_bisnis,
                android.R.color.holo_orange_dark,
                R.color.blue_bisnis,
                android.R.color.holo_orange_dark);

        swipeLayout.setProgressViewOffset(false, 250, 300);

       // refresh_setting();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println( sdf.format(cal.getTime()) );
        pola = "dd-MM-yyyy";
        waktuSatuStr = sdf.format(cal.getTime());
        waktuDuaStr = "18-08-2014 17:43:23";
        waktuSatu = konversiStringkeDate(waktuSatuStr,pola);
        WaktuDua = konversiStringkeDate(waktuDuaStr, pola);
        String hasilSelisih =selisihDateTime(waktuSatu, WaktuDua);
        System.out.println(hasilSelisih);


        mBanner = (BannerView)rootView.findViewById(R.id.bannerView);
        mBanner.getAdSettings().setPublisherId(0);
        mBanner.getAdSettings().setAdspaceId(0);
        mBanner.setScalingEnabled(false);
        mBanner.addAdListener(new AdListenerInterface() {
            @Override
            public void onReceiveAd(AdDownloaderInterface adDownloaderInterface, ReceivedBannerInterface receivedBannerInterface) throws AdReceiveFailed {
                if (receivedBannerInterface.getStatus() == BannerStatus.ERROR) {
                    mBanner.asyncLoadNewBanner();
                    Log.w("mBanner" + receivedBannerInterface.getErrorCode(), "" + receivedBannerInterface.getErrorMessage());
                } else {
                    // Banner download succeeded
                }

            }
        });


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Table_Setting db = new Table_Setting(getActivity(), null, null, 0);
        timmer=db.get_setting_refresh();
        db.close();
    }


    private void refresh_setting(){

        int detik=1000;
        switch (timmer) {
            case 0:
                timmer=5;
                break;
            case 1:
                timmer=15;
                break;
            case 2:
                timmer=30;
                break;
            case 3:
                timmer=60;
                break;
            case 4:
                detik=0;
                break;

            default:
                break;
        }

        if(detik!=0){
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    adapter_list_berita.clean();
                    offset=0;
                    index_start.setContentDescription("0");
                    index_much.setContentDescription("10");
                    index_day.setContentDescription("0");

                    if(canal.length()<4&(!canal.equals("cari"))){
                        //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"headlines/1/"+canal+"/1");
                        get_data(con.URL+"headlines/1/"+canal+"/3");
                        //new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                    }
                    else if(canal.equals("cari")){
                        proses.setContentDescription("baru");
                        // new DataDownloaderCari(adapter_List_Cari,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"search?q="+query);
                        //Toast.makeText(getActivity(), "Tajpa "+ query, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        proses.setContentDescription("baru");

                        //new DataDownloaderCari(adapter_list_berita,proses).execute(con.URL+"search?q="+query);
                        //  new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+canal+"/1/0/20/0");
                        get_data(con.URL+canal+"/1/0/20/0");
                    }
                    refresh_setting();
                }
            }, (timmer*60*1000));
        }
    }

    private void refresh() {
        // TODO Auto-generated method stub

        if(canal.length()<4){
            if(canal.equals("0")){
                if(offset==0){
                    //  new DataDownloader(adapter_list_berita,proses).execute(con.URL+"breaking/1/0/");
                    get_data(con.URL+"breaking/1/0/");
                    offset=offset+1;
                }else{
                    // new DataDownloaderOfIndex(adapter_list_berita,proses,index_start,index_much,index_day).execute(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                    get_data_index(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                    index_page=index_page+1;
                }

            }else{
                //	if(offset==0){
                //	new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"breaking/1/"+canal);
                //	offset=offset+1;

                //}else{
                //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/"+canal+"/"+offset+"/10");
                //offset=offset+1;
                //	}

                if(offset==0){
                    //  new DataDownloader(adapter_list_berita,proses).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,con.URL+"breaking/1/"+canal);
                    get_data(con.URL+"breaking/1/"+canal);
                    offset=offset+1;
                    index_page=0;

                }else{
                    if(index_page==0){
                        String Selisih [] = proses.getContentDescription().toString().split("@");
                        if(Selisih.length>1) {
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
                    }

                    // new DataDownloaderOfIndex(adapter_list_berita,proses,index_start,index_much,index_day).execute(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                    get_data_index(con.URL+"indeks_breaking/1/"+canal+"/"+index_day.getContentDescription()+"/"+index_start.getContentDescription()+"/10");
                    index_page=index_page+1;
                }

            }
        }else{
            //new DataDownloader(adapter_list_berita,proses).execute(con.URL+"indeks/1/0/"+offset+"/10");

            if(canal.equals("cari")){
                //  new DataDownloaderCari(adapter_List_Cari,proses).execute(con.URL+"search/?q="+query+"&per_page="+per_page);
                per_page=per_page+1;
                offset=offset+1;
            }
            //proses.setVisibility(View.GONE);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Home3) activity).onSectionAttached(getArguments().getInt(CANAL_ID));
    }

    protected static String selisihDateTime(Date waktuSatu, Date waktuDua) {
        long selisihMS = Math.abs(waktuSatu.getTime() - waktuDua.getTime());
        // long selisihDetik = selisihMS / 1000 % 60;
        // long selisihMenit = selisihMS / (60 * 1000) % 60;
        // long selisihJam = selisihMS / (60 * 60 * 1000) % 24;
        long selisihHari = selisihMS / (24 * 60 * 60 * 1000);
        //  String selisih = selisihHari + " hari " + selisihJam + " Jam " + selisihMenit + " Menit " + selisihDetik + " Detik";
        return ""+selisihHari;
    }

    protected static Date konversiStringkeDate(String tanggalDanWaktuStr,String pola) {
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

    public void get_data_index(String URL){
        download_data = new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                String response;
                response = getRequest(params[0]);
                System.out.println(params[0]);
                return response;
            }

            @Override
            protected void onPostExecute(String result){

                int mulai = Integer.parseInt(index_start.getContentDescription().toString());
                int much = Integer.parseInt(index_much.getContentDescription().toString());
                int day = Integer.parseInt(index_day.getContentDescription().toString());


                if (isCancelled()) {
                    result = null;
                }
                if (adapter_list_berita != null) {
                    JSONArray jsonarray;
                    try {

                        System.out.println("hari pagging "+mulai+ " asdas dasd "+ much);
                        jsonarray = new JSONArray(result);
                        for (int i=0 ; i < 10 ; i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);
                            JSONObject obj2 = obj.getJSONObject("date");
                            String [] split =obj.getString("category_link").split("-");
                            split [0]=Character.toUpperCase(split [0].charAt(0)) + split [0].substring(1);
                            String sub_canal = split [0];//+" "+split [1];
                            for (int j = 1; j < split.length; j++) {
                                split [j]=Character.toUpperCase(split [j].charAt(0)) + split [j].substring(1);
                                sub_canal += " "+split [j] ;
                            }
                            //array_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=300", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live")));
                            adapter_list_berita.add(new Frame_ListBerita(obj.getString("post_id"), obj.getString("category_id"), obj2.getString("year") + obj2.getString("month") + obj2.getString("day"), obj.getString("image_uri") + "?w=300", obj2.getString("day") + "/" + obj2.getString("month") + "/" + obj2.getString("year")," - "+ obj2.getString("hour") + ":" + obj2.getString("minute") + " WIB", obj.getString("title"),""+canal, sub_canal, obj.getString("slug"), obj.getString("is_live"), obj.getString("subtitle")));
                            Log.d("aab", obj.getString("category_id"));
                        }
                        mulai=mulai+10;
                        much = much+10;
                        index_much.setContentDescription(""+much);
                        index_start.setContentDescription(""+mulai);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //	dataload.setContentDescription("abis");
                        day=day+1;
                        index_start.setContentDescription("0");
                        index_day.setContentDescription(""+day);
                        index_much.setContentDescription("0");
                        proses.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                //   adapter_list_berita.addAll(array_list_berita);

                adapter_list_berita.notifyDataSetChanged();
                proses.setVisibility(View.INVISIBLE);

            }


        };


        download_data.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL);
    }

    public void get_data(String URL){
        download_data = new AsyncTask<String, Void, String>(){


            @Override
            protected String doInBackground(String... params) {
                String response;
                response = getRequest(params[0]);

                System.out.println(params[0]);
                return response;
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);

                System.out.println("CANCA "+ s);
            }



            @Override
            protected void onPostExecute(String result){
                //  adapter_list_berita= (Adapter_List_Berita) list_berita.get();
                // dataload = (ProgressBar) loading.get();
                String tgl_terakhir="";
                String tgl_terakhir_flag="";
                int count_tgl=1;
                if (isCancelled()) {
                    result = "";
                }else {
                    if (adapter_list_berita != null) {
                        JSONArray jsonarray;
                        try {
                            jsonarray = new JSONArray(result);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject obj = jsonarray.getJSONObject(i);
                                JSONObject obj2 = obj.getJSONObject("date");
                                String[] split = obj.getString("category_link").split("-");
                                split[0] = Character.toUpperCase(split[0].charAt(0)) + split[0].substring(1);
                                String sub_canal = split[0];//+" "+split [1];
                                for (int j = 1; j < split.length; j++) {
                                    split[j] = Character.toUpperCase(split[j].charAt(0)) + split[j].substring(1);
                                    sub_canal += " " + split[j];
                                }
                                if (!tgl_terakhir.equals("")) {
                                    count_tgl = count_tgl + 1;
                                    System.out.println("Selsisih : " + count_tgl);
                                } else {
                                    count_tgl = 1;
                                    System.out.println("tabbgal baru : " + count_tgl);
                                    tgl_terakhir = obj.getString("post_date");
                                }
                                tgl_terakhir_flag = obj2.getString("day") + "-" + obj2.getString("month") + "-" + obj2.getString("year");
                                //array_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=300", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal,obj.getString("slug"),obj.getString("is_live")));
                                adapter_list_berita.add(new Frame_ListBerita(obj.getString("post_id"), obj.getString("category_id"), obj2.getString("year") + obj2.getString("month") + obj2.getString("day"), obj.getString("image_uri") + "?w=300", obj2.getString("day") + "/" + obj2.getString("month") + "/" + obj2.getString("year")," - "+ obj2.getString("hour") + ":" + obj2.getString("minute") + " WIB", obj.getString("title"),""+canal, sub_canal, obj.getString("slug"), obj.getString("is_live"), obj.getString("subtitle")));
                                Log.d("aab", obj.getString("category_id"));
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            proses.setContentDescription("abis");
                            proses.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                    //   adapter_list_berita.addAll(array_list_berita);
                    proses.setContentDescription(tgl_terakhir_flag + "@" + count_tgl);
                    adapter_list_berita.notifyDataSetChanged();
                }

                proses.setVisibility(View.INVISIBLE);
            }


        };


        download_data.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,URL);

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


    private void scrollColoredViewParallax(int dy) {
        mBanner.setTranslationY(mBanner.getTranslationY() - dy);

        if (mBanner.getTranslationY() == 0) {
            // swipeRefresh.setEnabled(true);
        } else {
            // swipeRefresh.setEnabled(false);
        }

        //Log.d("Header ",""+(fragmnet_colored_background_view.getTranslationY() - dy));
        if (mBanner.getTranslationY() > -800) {
            //   mViewPager.setTranslationY(mViewPager.getTranslationY()/2);
            //   fragmnet_colored_background_view.setTranslationY(fragmnet_colored_background_view.getTranslationY()/2);
        }

    }


}
