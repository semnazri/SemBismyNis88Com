package bisnis.com.official;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import java.util.ArrayList;

import example.FullDrawerLayout;
import jazzyviewpager.JazzyViewPager;
import jazzyviewpager.JazzyViewPager.TransitionEffect;
import jazzyviewpager.OutlineContainer;
import model.Frame_ListBerita;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment3.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment3 mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static int CANAL_ID;

    private boolean flag_nav=false;

    private Menu optionsMenu;

    private ArrayList<Frame_ListBerita> data;

    private ProgressBar proses;

    private String post_id;

    private static int save_position;

    private JazzyViewPager mJazzy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail_new);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mNavigationDrawerFragment = (NavigationDrawerFragment3)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (FullDrawerLayout) findViewById(R.id.drawer_layout));

        mNavigationDrawerFragment.selectItem(1);


         setupJazziness(TransitionEffect.CubeOut);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void setupJazziness(TransitionEffect effect) {
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainAdapter3());
        mJazzy.setPageMargin(40);
    }

    private class MainAdapter3 extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View rootView = new View(MainActivity.this.getApplicationContext());
            final LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rootView = inflater.inflate(R.layout.fragment_home,container, false);

            String img_src []=getResources().getStringArray(R.array.img_array);

//			TextView text =  ((TextView)v.findViewById(R.id.txt_hore));
//			text.setGravity(Gravity.CENTER);
//			text.setTextSize(30);
//			text.setTextColor(Color.WHITE);
//			text.setText("Page " + img_src[position]);
//			text.setPadding(30, 30, 30, 30);
//			int bg = Color.rgb((int) Math.floor(Math.random()*128)+64,
//					(int) Math.floor(Math.random()*128)+64,
//					(int) Math.floor(Math.random()*128)+64);
//			text.setBackgroundColor(bg);


            //   new ImageDownloaderTaksFull(img_view).execute(img_src[position]);
            //	container.addView(v);


//			ImageView img_view = new ImageView(MainActivity.this);
//			img_view.setImageResource(R.drawable.ic_launcher);

            //img_view.setScaleType(ScaleType.FIT_XY);


            container.addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mJazzy.setObjectForPosition(rootView, position);
            //mJazzy.setObjectForPosition(img_view, position);
             return rootView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }
}
