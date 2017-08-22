package jazzyviewpager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bisnis.com.official.R;


public class Home extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View root_view = inflater.inflate(R.layout.home, container, false);
		
		return root_view;
	}

	public static Fragment newInstance(int position) {
		// TODO Auto-generated method stub
		Home frag = new Home();
		return frag;
	}

}
