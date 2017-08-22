package singelton;

/**
 * Created by sibertama on 2/13/15.
 */
public class Singelton_navsaved {

    private static Singelton_navsaved mInstance = null;

    public String mString;

    private Singelton_navsaved(){

   }

    public static Singelton_navsaved getInstance(){
        if(mInstance == null)
        {
            mInstance = new Singelton_navsaved();
        }
        return mInstance;
    }

    public String getString() {
        return mString;
    }

    public void setString(String mString) {
        this.mString = mString;
    }


}
