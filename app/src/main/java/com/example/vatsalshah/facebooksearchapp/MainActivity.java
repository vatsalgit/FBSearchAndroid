package com.example.vatsalshah.facebooksearchapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button   mButton;
    EditText mEdit;
    Intent intent;

    public void Handle_Intent(String type, String result)
    {

        if(type=="user")
        {
            Log.v("response_user",result);
            intent=new Intent(MainActivity.this, ResultsActivity.class);
            intent.putExtra("Response_User",result);

        }
        else if(type=="page")
        {
            Log.v("response_page",result);
            intent.putExtra("Response_Page",result);
        }
        else if(type=="event")
        {
            Log.v("response_event",result);
            intent.putExtra("Response_Event",result);
        }
        else if(type=="place")
        {
            Log.v("response_place",result);
            intent.putExtra("Response_Place",result);
        }
        else if(type=="group")
        {
            Log.v("response_group",result);
            intent.putExtra("Response_Group",result);
            startActivity(intent);
        }

    }
    public class onbuttonclickHttpPost extends AsyncTask<String, Void, String> {

        String type;
        Intent intent;


        public void setType(String type)
        {
            this.type=type;
        }
        public String getType()
        {
            return this.type;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v("Query",params[0]);
            setType(params[1]);
            try {
                byte[] result = null;
                URL url = new URL("http://vatsal-angularenv.us-west-2.elasticbeanstalk.com/index.php/main.php?query="+params[0]+"&type="+params[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // something with data retrieved from server in doInBackground
            type = getType();
            Handle_Intent(type,result);

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mButton = (Button)findViewById(R.id.searchbutton);
        mEdit   = (EditText)findViewById(R.id.edit_query);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String query = mEdit.getText().toString();
                        Log.v("EditText",query);
                        String types[] = new String[] {"user","page","event","place","group"};
                        for (String type:types)
                        {
                            new onbuttonclickHttpPost().execute(query,type);
                        }


                    }
                });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }
//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


