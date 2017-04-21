package com.example.vatsalshah.facebooksearchapp;

import android.graphics.Color;
import android.net.Uri;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements AlbumsFragment.OnFragmentInteractionListener,
PostFragment.OnListFragmentInteractionListener {

    static List<String> listDataHeader;
    static HashMap<String, List<String>> listDataChild;
    static List<PostItem> Post_List;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onListFragmentInteraction(PostItem item) {

    }

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("More Details");
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
        tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));

        int[] imageResId = {
                R.drawable.albums,
                R.drawable.posts
        };

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);

        }


        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                String details = extras.getString("Details_Returned");
                Log.v("Details Activity Got:", details);
                Process_JSON(details);
            }

//            mtext.setText(result);
        } catch (Exception e) {

        }


    }

    public void Process_JSON(String result) {
//        Get Posts
        try {

            JSONArray posts_array = new JSONObject(result).getJSONObject("posts").getJSONArray("data");

            Post_List = new ArrayList<PostItem>();

            for (int i = 0; i < posts_array.length(); i++) {
                JSONObject tempObject = posts_array.getJSONObject(i);
                PostItem item = new PostItem();
                item.setPicture(new JSONObject(result).getJSONObject("picture").getJSONObject("data").getString("url"));
                item.setName(new JSONObject(result).getString("name"));
                item.setDate(tempObject.getString("created_time"));
                item.SetPost(tempObject.getString("message"));
                Post_List.add(item);
            }
        }
        catch (Exception e)
        {

        }
//        Get Albums
            try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("albums");
            JSONArray albums_array = new JSONArray(jsonObject.getString("data"));

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            for (int i=0;i<albums_array.length();i++)
            {
                JSONObject tempObject = albums_array.getJSONObject(i);
                listDataHeader.add(tempObject.getString("name"));
                JSONArray photo = tempObject.getJSONObject("photos").getJSONArray("data");
                ArrayList pictures=new ArrayList();
                for(int j=0;j<photo.length();j++)
                {
                    String picture=photo.getJSONObject(j).getJSONArray("images").getJSONObject(0).getString("source");
                    pictures.add(picture);
//                    Log.v("Photos",picture.toString());
                }
                listDataChild.put(listDataHeader.get(i),pictures);

            }
            Log.v("Albums",listDataHeader.toString());
//            String x =listDataHeader.get(0);
            Log.v("Map",listDataChild.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    AlbumsFragment album = new AlbumsFragment();
                    return album;
                case 1:
                    PostFragment post = new PostFragment();
                    return post;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Albums";
                case 1:
                    return "Posts";
            }
            return null;
        }
    }
}
