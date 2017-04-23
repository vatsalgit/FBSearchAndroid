package com.example.vatsalshah.facebooksearchapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.PlaceFragment.OnListFragmentInteractionListener;
import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapterPlace extends RecyclerView.Adapter<MyItemRecyclerViewAdapterPlace.ViewHolder> {

    private List<ResultItem> resultItemList;
    private Context mcontext;


    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapterPlace(List<ResultItem> resultItemList,OnListFragmentInteractionListener listener,Context context) {
        this.resultItemList=resultItemList;
        this.mListener=listener;
        this.mcontext=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ResultItem Item = resultItemList.get(position);
        holder.mItem = Item;
        Picasso.with(mcontext).load(Item.getPicture()).resize(40,60).into(holder.mPictureView);

        SharedPreferences  mPrefs = mcontext.getSharedPreferences("Favorites",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        if(isAlreadyFav(Item.getId()))
        {
            holder.mFavButton.setImageResource(R.drawable.favorites_on);


        }
        else
        {
            holder.mFavButton.setImageResource(R.drawable.favorites_off);

        }

//        holder.mPictureView.setText("Picture");
        holder.mNameView.setText(Item.getName());
//        holder.mDetailsView.setText("Details");
//        holder.mFavView.setText("Fav");


        class getDetails extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... params) {
                Log.v("Id", params[0]);
                try {
                    byte[] result = null;
                    URL url = new URL("http://vatsal-angularenv.us-west-2.elasticbeanstalk.com/index.php/main.php?details="+params[0]);
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
                Intent intent=new Intent(mcontext, DetailsActivity.class);
                intent.putExtra("Details_Returned",result);
                mcontext.startActivity(intent);

                Log.v("ResultActivity_Returned", result);
            }
        }




        holder.mDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.v("Clicked On: ",Item.getName());
                    String id = Item.getId();
                    new getDetails().execute(id);
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    SharedPreferences  mPrefs = mcontext.getSharedPreferences("Favorites",MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    if(isAlreadyFav(Item.getId()))
                    {
                        holder.mFavButton.setImageResource(R.drawable.favorites_off);
                        prefsEditor.remove(Item.getId());
                        prefsEditor.commit();
                    }
                    else
                    {
                        holder.mFavButton.setImageResource(R.drawable.favorites_on);
                        prefsEditor.putString(Item.getId(),Item.getName());
                        prefsEditor.commit();
                    }

                    Log.v("Shared Preferences",mPrefs.getString(Item.getId(),"Null"));
                    Log.v("Clicked On: ",Item.getName());
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

    }

    public boolean isAlreadyFav(String id)
    {
        SharedPreferences  mPrefs = mcontext.getSharedPreferences("Favorites",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String favorite=mPrefs.getString(id,"-1");
        if (favorite.equals(new String("-1")))
        {
            return false;
        }

        return true;
    }



    @Override
    public int getItemCount() {
        return (null != resultItemList ? resultItemList.size() : 0);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mFavView;
        public final ImageView mPictureView;
        public final TextView mNameView;
        public final ImageButton mDetailsButton;
        public final ImageButton mFavButton;
        public ResultItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPictureView = (ImageView) view.findViewById(R.id.picture);
            mNameView  = (TextView) view.findViewById(R.id.name);
            mDetailsButton  = (ImageButton) view.findViewById(R.id.details);
            mFavButton = (ImageButton) view.findViewById(R.id.fav);

        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
