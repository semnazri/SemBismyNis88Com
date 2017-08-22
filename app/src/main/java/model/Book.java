package model;

import android.os.Parcel;  
import android.os.Parcelable;

public class Book implements Parcelable{
	      private String id;
	        public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}
			private String name;
	       private String grade;

	      // Constructor
	        public Book(String id, String name, String grade){
	            this.id = id;
	            this.name = name;
	           this.grade = grade;
	       }
	       // Getter and setter metho
	       
	       // Parcelling part
	       public Book(Parcel in){
	           String[] data = new String[3];
	    
	           in.readStringArray(data);
	           this.id = data[0];
	           this.name = data[1];
	           this.grade = data[2];
	       }
	    
	       @Override
	       public int describeContents(){
	           return 0;
	       }
	    
	       @Override
	       public void writeToParcel(Parcel dest, int flags) {
	           dest.writeStringArray(new String[] {this.id,
	                                               this.name,
	                                               this.grade});
	       }
	      @SuppressWarnings("rawtypes")
		public static final Creator CREATOR = new Creator() {
	           public Book createFromParcel(Parcel in) {
	               return new Book(in); 
	           }
	    
	           public Book[] newArray(int size) {
	               return new Book[size];
	           }
	       };
	   }