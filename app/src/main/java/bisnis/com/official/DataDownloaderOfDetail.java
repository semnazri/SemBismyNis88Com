package bisnis.com.official;

/**
 * Riki Setiyawan
 * Email: rzgonz@gmail.com
 * @copyright 2014
 * PT. Bisnis Indonesia Sibertama
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import model.Frame_ListBerita;

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

import bisnis.com.official.AnalyticBisnisApp.TrackerName;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import example.CustomExpandListView;
import example.TouchImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class DataDownloaderOfDetail extends AsyncTask<String, Void, String> {
    @SuppressWarnings("rawtypes")
    private final WeakReference loading,judul,date,img_caption,penulis,editor,detail,img,share,comment,list_terkait,view_list_terkait,img_sumber,rowseries,rowseriesbawah,nextseries,nextseriebawah,seriestext,seriestextbawah;
    public List<NameValuePair> params = new ArrayList<NameValuePair>();
//private static ArrayList<Frame_ListBerita> array_list_terkait = new ArrayList<Frame_ListBerita>();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public DataDownloaderOfDetail(ProgressBar porses, TextView txtjudul, TextView txtdate2, TextView txtimg_caption, TextView txtpenulis, TextView txteditor, WebView webdetail, ImageView imgberita, ImageView imgshare, ImageView imgcomment, Adapter_List_Terkait adapter_list_terkait, CustomExpandListView list_terkait2, TextView txtimg_sumber, TableRow table_series, TableRow table_series_bottom, TextView txtnextseries, TextView txtnextseriesbottom, TextView txtseries, TextView txtseriesbottom) {
        // TODO Auto-generated constructor stub
        loading = new WeakReference(porses);
        judul= new WeakReference(txtjudul);
        date =  new WeakReference(txtdate2);
        img_caption= new WeakReference(txtimg_caption);
        penulis= new WeakReference(txtpenulis);
        editor= new WeakReference(txteditor);
        detail= new WeakReference(webdetail);
        img=new WeakReference(imgberita);
        share=new WeakReference(imgshare);
        comment=new WeakReference(imgcomment);
        list_terkait= new WeakReference(adapter_list_terkait);
        view_list_terkait = new WeakReference(list_terkait2);
        img_sumber = new WeakReference(txtimg_sumber);
        rowseries = new WeakReference(table_series);
        rowseriesbawah = new WeakReference(table_series_bottom);
        nextseries = new WeakReference(txtnextseries);
        nextseriebawah = new WeakReference(txtnextseriesbottom);
        seriestext=new WeakReference(txtseries);
        seriestextbawah =  new WeakReference(txtseriesbottom);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TextView	txtjudul = (TextView)judul.get();
        Typeface open_sans= Typeface.createFromAsset(txtjudul.getContext().getAssets(),"OpenSans-Regular.ttf");
        txtjudul.setTypeface(open_sans,Typeface.BOLD);

    }

    @Override
    protected String doInBackground(String... params) {
        String response;
        response = getRequest(params[0]);
        System.out.println(params[0]);
        return response;
    }

    @Override
    protected void onPostExecute(String result){
        ProgressBar dataload = (ProgressBar) loading.get();

        TextView	txtdate = (TextView)date.get();
        TextView	txtimg_caption = (TextView)img_caption.get();
        TextView	txtpenulis = (TextView)penulis.get();
        TextView	txteditor = (TextView)editor.get();
        TextView	txt_img_sumber = (TextView)img_sumber.get();
        WebView	webdetail = (WebView)detail.get();
        ImageView imgberita = (ImageView)img.get();
        ImageView imgshare = (ImageView)share.get();
        ImageView imgcomment = (ImageView)comment.get();
        Adapter_List_Terkait adapter_list_terkait = (Adapter_List_Terkait) list_terkait.get();
        CustomExpandListView list_data_terkait =(CustomExpandListView)view_list_terkait.get();
        TableRow tableseries=(TableRow)rowseries.get();
        TableRow tableseriesbawah=(TableRow)rowseriesbawah.get();
        TextView txtnextseries = (TextView)nextseries.get();
        TextView txtnextseriesbawah = (TextView)nextseriebawah.get();
        TextView txtseries = (TextView)seriestext.get();
        TextView txtseriesbottom = (TextView)seriestextbawah.get();


        if (isCancelled()) {
            result = null;
        }
        if (result != null) {
            try {
                JSONObject data = new JSONObject(result);
                JSONObject obj = data.getJSONObject("data");
                JSONObject obj2 = obj.getJSONObject("date");
                JSONObject obj3 = obj.getJSONObject("category");
                //txtjudul.setText(obj.getString("title"));
                txtdate.setText(obj2.getString("0"));
                String [] Caption = obj.getString("image_caption").split("/");
                txtimg_caption.setText(Caption[0]);
                txtimg_caption.setVisibility(View.VISIBLE);
                if(Caption.length>1){
                    String new_caption=Caption[0];
                    for (int i = 1; i < Caption.length-1; i++) {
                    new_caption=new_caption+"/"+Caption[i];
                    }
                    txtimg_caption.setText(new_caption);
                    txt_img_sumber.setText(Caption[Caption.length-1]);
                    txt_img_sumber.setVisibility(View.VISIBLE);

                }

                txtpenulis.setText(obj.getString("author_name") +" \n"+ obj.getString("new_date"));
                String css= "<style>table {twidth: 100%; text-align: center;margin-bottom: 20px;}"+
                        "table tr th td {border: 1px solid #ddd; border-collapse: collapse;}" +
                        " </style>"+webdetail.getContentDescription();
                webdetail.loadData(css+"<div class='detail'>"+obj.getString("content")+"</div>", "text/html", "utf-8");
              //  webdetail.setContentDescription(obj.getString("content"));
             //   System.out.println("HTML "+obj.getString("content"));
                imgshare.setContentDescription(obj3.getString("link"));
                imgberita.setContentDescription(obj.getString("image_uri"));
                if(obj.getString("source").equals("")){
                    txteditor.setText("Editor : "+obj.getString("editor_name"));
                }else{
                    txteditor.setText("Source : "+obj.getString("source")+"\n"+"Editor : "+obj.getString("editor_name"));
                }

                if(obj.getString("is_series").equals("1")){
                    tableseries.setVisibility(View.VISIBLE);
                    tableseriesbawah.setVisibility(View.VISIBLE);
                    txtseries.setText((obj.getJSONObject("series").getInt("order")+1) +" dari "+obj.getString("total_series"));
                    txtseriesbottom.setText((obj.getJSONObject("series").getInt("order")+1) +" dari "+obj.getString("total_series"));
                    txtnextseries.setContentDescription(obj.getJSONObject("next_series").getString("post_id"));
                    txtnextseriesbawah.setContentDescription(obj.getJSONObject("next_series").getString("post_id"));
                    txtnextseries.setVisibility(View.VISIBLE);
                    txtnextseriesbawah.setVisibility(View.VISIBLE);

                }
                imgcomment.setContentDescription(obj3.getString("link").substring(7));

                new ImageDownloaderTaksFull(imgberita,dataload).execute(imgberita.getContentDescription().toString().replaceAll(" ", "%20"));

                JSONArray 	data_terkiat = data.getJSONArray("data_terkait");

                for (int i = 0; (i < data_terkiat.length() & adapter_list_terkait.getCount()<5); i++) {
                    JSONObject obj_terkait = data_terkiat.getJSONObject(i);
                    String [] post_date = obj_terkait.getString("post_date").split(" ");
                    String [] date = post_date[0].split("-");
                    //	array_list_berita.add(new Frame_ListBerita(post_id,obj3.getString("category"),url,array3.getString(0),tgl[2]+"/"+tgl[1]+"/"+tgl[0],time+" WIB", array2.getString(0).toString(),array3.getString(0),""));
                    //array_list_berita.add(new Frame_ListBerita(obj.getString("post_id"),obj.getString("category_id"),obj2.getString("year")+obj2.getString("month")+obj2.getString("day"), obj.getString("image_uri")+"?w=450", obj2.getString("day")+"/"+obj2.getString("month")+"/"+obj2.getString("year"),obj2.getString("hour")+":"+obj2.getString("minute")+" WIB", obj.getString("title"),obj.getString("image_content"),obj.getString("parent_category")+" - "+sub_canal));
                    //array_list_terkait.add(new Frame_ListBerita(obj_terkait.getString("post_id"),obj_terkait.getString("category_id"),date[0]+date[1]+date[2], "","",obj_terkait.getString("post_date")+" WIB", obj_terkait.getString("post_title"),"","",obj.getString("slug")));
                    adapter_list_terkait.add(new Frame_ListBerita(obj_terkait.getString("post_id"),obj_terkait.getString("category_id"),date[0]+date[1]+date[2], "","",obj_terkait.getString("post_date")+" WIB", obj_terkait.getString("post_title"),"","",obj.getString("slug"),obj.getString("is_live"), obj.getString("subtitle")));


                }
                adapter_list_terkait.notifyDataSetChanged();








                //webcomment.loadUrl("http://27.123.222.118/json/comment/"+obj.getString("post_id")+"/"+obj.getString("slug"));
                //System.out.println("http://27.123.222.118/json/comment/"+obj.getString("post_id")+"/"+obj3.getString("slug"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        // new ImageDownloaderTaksFull(imgberita,dataload).execute(imgberita.getContentDescription().toString().replaceAll(" ", "%20"));
        list_data_terkait.setExpanded(true);


        //updateListViewHeight(list_data_terkait);


        //  dataload.setVisibility(View.INVISIBLE);
    }
    public static void setListViewHeightBasedOnChildren(CustomExpandListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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

    private boolean ceknamaimage(String path) {
        // TODO Auto-generated method stub
        File ceknama = new File(Environment.getExternalStorageDirectory()+"/BIS",path);
        System.out.println(path);
        if(!ceknama.exists()){
            Log.e("TravellerLog :: ", "gak ada");
            return false;
        }else{

            Log.e("TravellerLog :: ", "Ada");

            return true;
        }
    }



    // buat connect ke server
    public String getRequest(String urls) {
        // TODO Auto-generated method stub
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new  HttpPost(urls);
        try{
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
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

}


