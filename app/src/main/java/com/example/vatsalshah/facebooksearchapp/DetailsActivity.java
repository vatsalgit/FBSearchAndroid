package com.example.vatsalshah.facebooksearchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity implements AlbumsFragment.OnFragmentInteractionListener,
PostFragment.OnListFragmentInteractionListener {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public int isFav;
    public String type;
    public String id;
    public String picture="";
    public String Name;
    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;
    public static List<PostItem> Post_List;


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
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager loginManager = LoginManager.getInstance();
        List<String> permissionNeeds = Arrays.asList("publish_actions"); // permission.

        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        shareDialog = new ShareDialog(this);



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
                type=extras.getString("Type");
                Log.v("Type",type);
                id = extras.getString("Id");
                isFav = extras.getInt("isFav");
                Log.v("Details Got Id",id);
                Log.v("Details Activity Got:", details);
                Process_JSON(details);
            }
            else
                Log.v("Details",null);

//            mtext.setText(result);
        } catch (Exception e) {

        }


    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void Process_JSON(String result) {
//        Get Posts
        Post_List = new ArrayList<PostItem>();
        try {
            Name = new JSONObject(result).getString("name");
            JSONObject picObj = new JSONObject(result).getJSONObject("picture");
            if (picObj != null)
                picture = picObj.getJSONObject("data").getString("url");
            else {
                if (picture == "" && type == "group")
                    picture = "https://image.flaticon.com/icons/svg/20/20697.svg";
                else if (picture == "" && type == "event")
                    picture = "http://info.psinfoodservice.nl/img/original/weekly-calendar-icon-69702.png";
            }

            JSONObject postObj = new JSONObject(result).getJSONObject("posts");
            if (postObj != null) {
                JSONArray posts_array = new JSONObject(result).getJSONObject("posts").getJSONArray("data");


                if (posts_array != null) {
                    for (int i = 0; i < posts_array.length(); i++) {
                        JSONObject tempObject = posts_array.getJSONObject(i);
                        PostItem item = new PostItem();
                        item.setPicture(picture);
                        item.setName(Name);
                        item.setDate(tempObject.getString("created_time"));
                        item.SetPost(tempObject.getString("message"));
                        Post_List.add(item);
                    }
                }

            }
        }
        catch (Exception e)
        {
            PostItem item = new PostItem();
            item.setPicture("");
            item.setName("No Posts To Display");
            item.setDate("");
            item.SetPost("");
            Post_List.add(item);
        }
//        Get Albums
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
            try {
                JSONObject jsonObject = new JSONObject(result).getJSONObject("albums");
                if (jsonObject != null) {
                    JSONArray albums_array = new JSONArray(jsonObject.getString("data"));


                    for (int i = 0; i < albums_array.length(); i++) {
                        JSONObject tempObject = albums_array.getJSONObject(i);
                        listDataHeader.add(tempObject.getString("name"));
                        JSONArray photo = tempObject.getJSONObject("photos").getJSONArray("data");
                        ArrayList pictures = new ArrayList();
                        for (int j = 0; j < photo.length(); j++) {
                            String picture = photo.getJSONObject(j).getJSONArray("images").getJSONObject(0).getString("source");
                            pictures.add(picture);
//                    Log.v("Photos",picture.toString());
                        }
                        listDataChild.put(listDataHeader.get(i), pictures);

                    }
                    Log.v("Albums", listDataHeader.toString());
//            String x =listDataHeader.get(0);
                    Log.v("Map", listDataChild.toString());
                }
                else
                {

                }

                } catch(JSONException e){
                    listDataHeader.add("No Albums To Display");
                    e.printStackTrace();
                }


    }

    public boolean isAlreadyFav(String id,String type)
    {
        SharedPreferences mPrefs=null;
        SharedPreferences.Editor prefsEditor;
        if(type.equals(new String("user")))
        {
            mPrefs = this.getSharedPreferences("Favorites_User",MODE_PRIVATE);
            prefsEditor =  mPrefs.edit();
        }
        else if (type.equals(new String("page")))
        {
            mPrefs = this.getSharedPreferences("Favorites_Page",MODE_PRIVATE);
            prefsEditor =  mPrefs.edit();
        }
        else if (type.equals(new String("place")))
        {
            mPrefs = this.getSharedPreferences("Favorites_Place",MODE_PRIVATE);
            prefsEditor =  mPrefs.edit();
        }
        else if (type.equals(new String("event")))
        {
            mPrefs = this.getSharedPreferences("Favorites_Event",MODE_PRIVATE);
            prefsEditor =  mPrefs.edit();
        }
        else if (type.equals(new String("group")))
        {
            mPrefs = this.getSharedPreferences("Favorites_Group",MODE_PRIVATE);
            prefsEditor =  mPrefs.edit();
        }

        String favorite=mPrefs.getString(id,"-1");
        if (favorite.equals(new String("-1")))
        {
            return false;
        }

        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        MenuItem fav_item = menu.findItem(R.id.add_to_fav);
        if(isAlreadyFav(id,type))
        {
            fav_item.setTitle("Remove from Favorite");
        }
        else
        {
            fav_item.setTitle("Add To Favorite");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            Intent intent;
            if(isFav==0) {
                intent = new Intent(this, ResultsActivity.class);
            }
            else
            {
                intent = new Intent(this, Favorites_Activity.class);
            }
            if (type.equals(new String("page")))
                intent.putExtra("position", 1);
            else if (type.equals(new String("event")))
                intent.putExtra("position", 2);
            else if (type.equals(new String("place")))
                intent.putExtra("position", 3);
            else if (type.equals(new String("group")))
                intent.putExtra("position", 4);


            this.startActivity(intent);
            // close this activity and return to preview activity (if there is any)
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_fav) {

            SharedPreferences mPrefs=null;
            SharedPreferences.Editor prefsEditor=null;
            if(type.equals(new String("user")))
            {
                mPrefs = this.getSharedPreferences("Favorites_User",MODE_PRIVATE);
                prefsEditor =  mPrefs.edit();
            }
            else if (type.equals(new String("page")))
            {
                mPrefs = this.getSharedPreferences("Favorites_Page",MODE_PRIVATE);
                prefsEditor =  mPrefs.edit();
            }
            else if (type.equals(new String("place")))
            {
                mPrefs = this.getSharedPreferences("Favorites_Place",MODE_PRIVATE);
                prefsEditor =  mPrefs.edit();
            }
            else if (type.equals(new String("event")))
            {
                mPrefs = this.getSharedPreferences("Favorites_Event",MODE_PRIVATE);
                prefsEditor =  mPrefs.edit();
            }
            else if (type.equals(new String("group")))
            {
                mPrefs = this.getSharedPreferences("Favorites_Group",MODE_PRIVATE);
                prefsEditor =  mPrefs.edit();
            }

            if(isAlreadyFav(this.id,this.type))
            {

                prefsEditor.remove(this.id);
                prefsEditor.commit();
                MyItemRecyclerViewAdapterPage us = new MyItemRecyclerViewAdapterPage();
                us.notifyDataSetChanged();
                Toast.makeText(this, "Removed From Favorites!",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Gson gson = new Gson();
                ResultItem rsitem = new ResultItem();
                rsitem.setName(Name);
                rsitem.setId(this.id);
                rsitem.setPicture(picture);
                String jsonText = gson.toJson(rsitem);
                prefsEditor.putString(this.id,jsonText);
                prefsEditor.commit();
                MyItemRecyclerViewAdapterPage us = new MyItemRecyclerViewAdapterPage();
                us.notifyDataSetChanged();
                Toast.makeText(this, "Added To Favorites!",
                        Toast.LENGTH_LONG).show();
            }





            return true;
        }

        if(id== R.id.post_to_fb)
        {

//            Bitmap image=getBitmapFromURL(Post_List.get(0).getPicture());
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("www.google.com"))
                .setContentDescription("FB Search from USC CSCI 571")
                .setImageUrl(Uri.parse(picture))
                .setContentTitle(Name)
                .build();

                shareDialog.show(content,ShareDialog.Mode.NATIVE);
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(DetailsActivity.this, "You shared this post!",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(DetailsActivity.this, "Post was not shared!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {

                    } });

            }
        }


        return super.onOptionsItemSelected(item);
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
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
