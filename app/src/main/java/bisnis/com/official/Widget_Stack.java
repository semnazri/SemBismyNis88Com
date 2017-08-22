package bisnis.com.official;

import java.util.ArrayList;

import bisnis.com.official.R;

import model.Frame_ListBerita;
import koneksi.Json_Connector;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.StackView;

public class Widget_Stack extends Activity {

	Button next;
    Button previous;
    StackView sv;
    ProgressBar proses;
    ArrayList<Frame_ListBerita> items;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_stack);
        
       
        StackView stk = (StackView)this.findViewById(R.id.stack_view);
        proses = (ProgressBar)this.findViewById(R.id.prosesdata_widget);
        
        /*
        ArrayList<StackItem> items = new ArrayList<StackItem>();
        items.add(new StackItem("text1", this.getResources().getDrawable(R.drawable.ic_communities)));
        items.add(new StackItem("text2", this.getResources().getDrawable(R.drawable.ic_home)));
        items.add(new StackItem("text3", this.getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new StackItem("text4", this.getResources().getDrawable(R.drawable.ic_photos)));
        items.add(new StackItem("text5", this.getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new StackItem("text6", this.getResources().getDrawable(R.drawable.ic_home)));
        items.add(new StackItem("text7", this.getResources().getDrawable(R.drawable.ic_photos)));
        items.add(new StackItem("text8", this.getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new StackItem("text9", this.getResources().getDrawable(R.drawable.ic_communities)));
         StackAdapter adapter_list_widget = new StackAdapter(this, R.layout.list_widget, items);
 		*/
        items = new ArrayList<Frame_ListBerita>();
        items.add(new Frame_ListBerita("1", "1", "1", "1", "1", "1",  "",  "1",  "1",  "1",  "1", "1"));
     
        Adapter_Stack adapt = new Adapter_Stack(this, R.layout.list_widget,items);
        stk.setAdapter(adapt);
        Json_Connector con =  new Json_Connector();
     //   new DataDownloaderOfWidget(adapt,proses).execute(con.URL+"indeks/1/0/0/10");
      
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
 
}