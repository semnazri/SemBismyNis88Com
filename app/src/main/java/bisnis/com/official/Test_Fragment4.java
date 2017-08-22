package bisnis.com.official;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import bisnis.com.official.home.Fragmented_Home_All;

/**
 * Created by sibertama on 1/27/15.
 */
public class Test_Fragment4 extends Fragment {

    private ViewPager mViewPager;
    private SectionsPagerAdapter2 mSectionsPagerAdapter;
    private int menu_flag;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.fragment_detail2,container,false);
        Toast.makeText(getActivity(),"HAHA  "+getArguments().getInt("nav"),Toast.LENGTH_LONG).show();
        menu_flag=getArguments().getInt("nav");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootview.findViewById(R.id.pager);

        // Bind the tabs to the ViewPager
        mSectionsPagerAdapter = new SectionsPagerAdapter2(getActivity().getSupportFragmentManager());

        if(mSectionsPagerAdapter!=null);
        mSectionsPagerAdapter.startUpdate(container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        example.PagerSlidingTabStrip tabs = (example.PagerSlidingTabStrip)rootview.findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
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
        Test_Fragment4 fragment = new Test_Fragment4();
        Bundle args = new Bundle();
        args.putInt("nav",position);
        fragment.setArguments(args);

        return  fragment;
    }


    public class SectionsPagerAdapter2 extends FragmentPagerAdapter {

        public SectionsPagerAdapter2(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below);
            Toast.makeText(getActivity(),"Info  ",Toast.LENGTH_LONG).show();

            //if(!getFragmentManager().getFragments().isEmpty());
            //    getFragmentManager().getFragments().clear();

          //  mSectionsPagerAdapter.destroyItem();

               return Fragmented_Home_All.newInstance(position);


        }



        @Override
        public int getItemPosition(Object object) {
            return POSITION_UNCHANGED;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if(menu_flag<1){
                String [] home = getResources().getStringArray(R.array.array_frontpage);
                return home.length;
            }else{
                String [] home = getResources().getStringArray(R.array.array_allchanel);
                return home.length;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(menu_flag<1){
                String [] home = getResources().getStringArray(R.array.array_frontpage);
                return home[position];
            }else{
                String [] home = getResources().getStringArray(R.array.array_allchanel);
                return home[position];
            }

        }
    }

}
