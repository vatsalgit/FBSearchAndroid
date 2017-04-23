package com.example.vatsalshah.facebooksearchapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Favorites_Activity extends AppCompatActivity implements UserFragment.OnListFragmentInteractionListener,
        PageFragment.OnListFragmentInteractionListener,PlaceFragment.OnListFragmentInteractionListener,
        EventFragment.OnListFragmentInteractionListener, GroupFragment.OnListFragmentInteractionListener
{
    public static List<ResultItem> fav_user;
    public static List<ResultItem> fav_place;
    public static List<ResultItem> fav_page;
    public static List<ResultItem> fav_event;
    public static List<ResultItem> fav_group;
    public void onListFragmentInteraction(ResultItem item)
    {

    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favorites");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#000000"));

        int[] imageResId = {
                R.drawable.users,
                R.drawable.pages,
                R.drawable.events,
                R.drawable.places,
                R.drawable.groups
        };

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);

        }
        SharedPreferences mPrefs = this.getSharedPreferences("Favorites_User",MODE_PRIVATE);
        Map<String, ?> allEntries = mPrefs.getAll();

        fav_user=new ArrayList<ResultItem>();
        Gson gson = new Gson();
        for(Map.Entry<String,?> key: allEntries.entrySet())
        {
            String json = mPrefs.getString(key.getKey(), "-1");
            ResultItem obj = gson.fromJson(json, ResultItem.class);
            fav_user.add(obj);
        }


        mPrefs = this.getSharedPreferences("Favorites_Place",MODE_PRIVATE);
        allEntries = mPrefs.getAll();

        fav_place=new ArrayList<ResultItem>();
        gson = new Gson();
        for(Map.Entry<String,?> key: allEntries.entrySet())
        {
            String json = mPrefs.getString(key.getKey(), "-1");
            ResultItem obj = gson.fromJson(json, ResultItem.class);
            fav_place.add(obj);
        }

        mPrefs = this.getSharedPreferences("Favorites_Page",MODE_PRIVATE);
        allEntries = mPrefs.getAll();

        fav_page=new ArrayList<ResultItem>();
        gson = new Gson();
        for(Map.Entry<String,?> key: allEntries.entrySet())
        {
            String json = mPrefs.getString(key.getKey(), "-1");
            ResultItem obj = gson.fromJson(json, ResultItem.class);
            fav_page.add(obj);
        }

        mPrefs = this.getSharedPreferences("Favorites_Event",MODE_PRIVATE);
        allEntries = mPrefs.getAll();

        fav_event=new ArrayList<ResultItem>();
        gson = new Gson();
        for(Map.Entry<String,?> key: allEntries.entrySet())
        {
            String json = mPrefs.getString(key.getKey(), "-1");
            ResultItem obj = gson.fromJson(json, ResultItem.class);
            fav_event.add(obj);
        }

        mPrefs = this.getSharedPreferences("Favorites_Group",MODE_PRIVATE);
        allEntries = mPrefs.getAll();

        fav_group=new ArrayList<ResultItem>();
        gson = new Gson();
        for(Map.Entry<String,?> key: allEntries.entrySet())
        {
            String json = mPrefs.getString(key.getKey(), "-1");
            ResultItem obj = gson.fromJson(json, ResultItem.class);
            fav_group.add(obj);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_favorites_, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args;
            switch (position)
            {
                case 0:
                    UserFragment user = new UserFragment();
                    args = new Bundle();
                    args.putInt("fav",1);
                    user.setArguments(args);
                    return user;
                case 1:
                    PageFragment page = new PageFragment();
                    args = new Bundle();
                    args.putInt("fav",1);
                    page.setArguments(args);
                    return page;
                case 2:
                    EventFragment event = new EventFragment();
                    args = new Bundle();
                    args.putInt("fav",1);
                    event.setArguments(args);
                    return event;
                case 3:
                    PlaceFragment place = new PlaceFragment();
                    args = new Bundle();
                    args.putInt("fav",1);
                    place.setArguments(args);
                    return place;
                case 4:
                    GroupFragment group = new GroupFragment();
                    args = new Bundle();
                    args.putInt("fav",1);
                    group.setArguments(args);
                    return group;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }




        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Users";
                case 1:
                    return "Pages";
                case 2:
                    return "Events";
                case 3:
                    return "Places";
                case 4:
                    return "Groups";
            }
            return null;
        }
    }
}
