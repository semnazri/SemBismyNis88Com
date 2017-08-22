package bisnis.com.official;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import example.*;

/**
 * Created by sibertama on 1/27/15.
 */
public class Test_Fragment extends Fragment {

    private ViewPager mViewPager;

    private int menu_flag;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.fragment_detail2,container,false);
        Toast.makeText(getActivity(),"HAHA  "+getArguments().getInt("nav"),Toast.LENGTH_LONG).show();
        menu_flag=getArguments().getInt("nav");

       // mSectionsPagerAdapter.notifyDataSetChanged();



        return rootview;
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public static Fragment newInstance(int position) {
        Test_Fragment fragment = new Test_Fragment();
        Bundle args = new Bundle();
        args.putInt("nav",position);
        fragment.setArguments(args);
        return  fragment;
    }




}
