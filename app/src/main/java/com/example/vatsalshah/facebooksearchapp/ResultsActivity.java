package com.example.vatsalshah.facebooksearchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements UserFragment.OnListFragmentInteractionListener,
        PageFragment.OnListFragmentInteractionListener,PlaceFragment.OnListFragmentInteractionListener,
EventFragment.OnListFragmentInteractionListener, GroupFragment.OnListFragmentInteractionListener{

    public static HashMap<String,List<ResultItem>> processed_map = new HashMap<String,List<ResultItem>>();
    HashMap<String,String> raw_map = new HashMap<String,String>();
    private RecyclerView mRecyclerView;
//    private MyRecyclerViewAdapter adapter;

    @Override
    public void onListFragmentInteraction(ResultItem item)
    {

    }

    public static void closeStream(Closeable s){
        try{
            if(s!=null)s.close();
        }catch(IOException e){
            //Log or rethrow as unchecked (like RuntimException) ;)
        }
    }

    public static Bitmap loadBitmap(String url)
    {
        try
        {
        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
        return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Process_JSON_Strings()
    {

        for(String key:raw_map.keySet())
        {
            String result=raw_map.get(key);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray result_array= new JSONArray(jsonObject.getString("data"));
                ArrayList result_List = new ArrayList<>();

                for (int i=0;i<result_array.length();i++)
                {
                    JSONObject tempObject = result_array.getJSONObject(i);
                    ResultItem item = new ResultItem();

                    item.setId(tempObject.getString("id").toString());
                    item.setName(tempObject.getString("name").toString());
                    String picture_url = tempObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    Log.v(key,picture_url);
//                    Bitmap bitmap = loadBitmap(picture_url);
//                    if (bitmap!=null)
//                    {
//                        Log.v("picture_null",bitmap.toString());
//                    }
                    item.setPicture(picture_url);
                    result_List.add(item);

                }
                processed_map.put(key,result_List);
//                Log.v("processed_map",processed_map.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }



    TextView mtext;
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
        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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




        mtext = (TextView)findViewById(R.id.display);

        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {

                raw_map.put("user",extras.getString("Response_User"));
                raw_map.put("page",extras.getString("Response_Page"));
                raw_map.put("event",extras.getString("Response_Event"));
                raw_map.put("place",extras.getString("Response_Place"));
                raw_map.put("group",extras.getString("Response_Group"));

                Log.v("Results_User", extras.getString("Response_User"));
                Log.v("Results_Page", extras.getString("Response_Page"));
                Log.v("Results_Event", extras.getString("Response_Event"));
                Log.v("Results_Place", extras.getString("Response_Place"));
                Process_JSON_Strings();


            }

//            mtext.setText(result);
        }
        catch (Exception e)
        {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
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
            View rootView = inflater.inflate(R.layout.fragment_results, container, false);
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
            switch (position)
            {
                case 0:
                    UserFragment user = new UserFragment();
                    return user;
                case 1:
                    PageFragment page = new PageFragment();
                    return page;
                case 2:
                    EventFragment event = new EventFragment();
                    return event;
                case 3:
                    PlaceFragment place = new PlaceFragment();
                    return place;
                case 4:
                    GroupFragment group = new GroupFragment();
                    return group;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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
