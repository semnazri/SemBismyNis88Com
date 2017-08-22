package koneksi;


/**
 * Riki Setiyawan
 * Email: rzgonz@gmail.com
 *
 * @copyright 2014
 * PT. Bisnis Indonesia Sibertama
 */

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Json_Connector extends AsyncTask<String, Void, String> {

    //  private ProgressDialog dialog;
    public Context applicationContext;
    // public String web="http://10.0.2.2/bkci/";
    // public String web="http://192.168.56.1/bkci/";
    // public String web="http://10.5.5.56/json/";

    public String URL = "http://services.bisnis.com/json/";
    public String URL_dev= "http://services.bisnisdev.com/json/test";
    //public String URL="http://27.123.222.118/json/";


    //public String detail=URL+"detail/";
    public String detail = URL + "newdetail/";
    public String detailterkait = URL + "detailterkaitios/";
    public String breakingloadmore = URL + "breaking/1/0/10/10";


//  public String detail="http://10.5.5.56/json/detail";

    public List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Override
    protected void onPreExecute() {
        //dialog = ProgressDialog.show(applicationContext, "Data Process", "Tunggu Sebentar", true);¡¡

    }


    protected String doInBackground(String... urls) {
        String response;
        response = getRequest(urls[0]);
        return response;

    }


    @Override
    protected void onPostExecute(String result) {
        //    this.dialog.cancel();

    }


    // buat connect ke server
    public String getRequest(String urls) {
        // TODO Auto-generated method stub
        String sret = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(urls);
        try {
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            request.setEntity(ent);
            HttpResponse response = client.execute(request);
            sret = request(response);
        } catch (Exception ex) {
            //	Toast.makeText(this, "GAGAL" +sret, Toast.LENGTH_LONG).show();

        }

        return sret;
    }


    // buat menerima balasan dari server
    public String request(HttpResponse response) {
        // TODO Auto-generated method stub
        String result;
        try {
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line).append("\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex) {
            result = "Error";
        }

        return result;
    }


}





