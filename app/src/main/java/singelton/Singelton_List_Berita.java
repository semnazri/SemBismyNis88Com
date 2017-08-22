package singelton;

import java.util.ArrayList;

import model.Frame_ListBerita;;

public class Singelton_List_Berita {
	
	private static Singelton_List_Berita mInstance = null;
		
	private String mString;		
	
    public ArrayList<Frame_ListBerita> array_widget = new ArrayList<Frame_ListBerita>();
	
	public ArrayList<Frame_ListBerita> getArray_widget() {
		return array_widget;
	}

	public void setArray_widget(ArrayList<Frame_ListBerita> array_widget) {
		this.array_widget.clear();
		this.array_widget.addAll(array_widget);
	}

	private Singelton_List_Berita(){
		
	}
	
	public static Singelton_List_Berita getInstance(){
		  if(mInstance == null)
	        {
	            mInstance = new Singelton_List_Berita();
	        }
	        return mInstance;
	}
	public String getString(){
        return this.mString;
    }
 
    public void setString(String value){
        mString = value;
    }

}
