package bisnis.com.official;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.ls.LSInput;

import bisnis.com.official.R;

import model.Frame_ListBerita;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Stack extends ArrayAdapter<Frame_ListBerita> {
	 
    private ArrayList<Frame_ListBerita> items;
    private Context ctx;
 
    public Adapter_Stack(Context context, int textViewResourceId,
            ArrayList<Frame_ListBerita> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.ctx = context;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_widget, null);
        }
 
        Frame_ListBerita m = items.get(position);
        File sdCardDirectory = Environment.getExternalStorageDirectory();
    	File image2 = new File(sdCardDirectory+"/Bisniscom",m.getImage_content()+".cache");
 
        if (m != null) {
                TextView text = (TextView) v.findViewById(R.id.textView1);
                ImageView img = (ImageView)v.findViewById(R.id.imageView1);
 
                if (text != null) {
                    text.setText(m.getJudulberita());
                    new ImageDownloaderTaks(img, null, m.getImage_content()).execute(m.getImgberita());
                   // img.setImageResource(R.drawable.ic_people);
                  //  img.setImageDrawable(R.d);
                    }
        }
        return v;
    }
}
