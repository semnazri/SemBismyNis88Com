package model;

/**
* Riki Setiyawan 
* Email: rzgonz@gmail.com
* @copyright 2014
* PT. Bisnis Indonesia Sibertama
*/ 

import android.os.Parcel;
import android.os.Parcelable;

public class Frame_ListBerita2 implements Parcelable {	
	public String idberita;
	public String imgberita;
	public String tglberita;
	public String judulberita;
	public String pukul;
	public String category;
	public String datepost;
	public String image_content;
	

	public Frame_ListBerita2(String idberita,String category,String datepost, String imgberita, String tglberita, String pukul, String judulberita, String image_content) {
		// TODO Auto-generated constructor stub
		this.idberita = idberita;
		this.imgberita = imgberita;
		this.judulberita = judulberita;
		this.pukul = pukul;
		this.tglberita = tglberita;
		this.category=category;
		this.datepost=datepost;
		this.image_content=image_content;
	}
	
	  public Frame_ListBerita2(Parcel in){
          String[] data = new String[8];

          in.readStringArray(data);
          		this.idberita = data[0];
  				this.imgberita =  data[1];
  				this.judulberita =  data[2];
  				this.pukul =  data[3];
  				this.tglberita =  data[4];
  				this.category= data[5];
  				this.datepost= data[6];
  				this.image_content= data[7];
      }


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	   @Override
       public void writeToParcel(Parcel dest, int flags) {
           dest.writeStringArray(new String[] {this.idberita,
				this.imgberita,
				this.judulberita,
				this.pukul,
				this.tglberita,
				this.category,
				this.datepost,
				this.image_content});
       }
	   
    @SuppressWarnings("rawtypes")
	public static final Creator CREATOR = new Creator() {
           public Frame_ListBerita2 createFromParcel(Parcel in) {
               return new Frame_ListBerita2(in); 
           }

           public Frame_ListBerita2[] newArray(int size) {
               return new Frame_ListBerita2[size];
           }
       };
	
	
	

}
