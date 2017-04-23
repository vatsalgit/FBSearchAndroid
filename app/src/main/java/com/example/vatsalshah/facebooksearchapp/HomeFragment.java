package com.example.vatsalshah.facebooksearchapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button mButton;
    Button mClearButton;
    EditText mEdit;
    Intent intent;
    Context mcontext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public void Handle_Intent(String type, String result) {

        if (type == "user") {
            Log.v("response_user", result);
            intent = new Intent(mcontext, ResultsActivity.class);
            intent.putExtra("Response_User", result);

        } else if (type == "page") {
            Log.v("response_page", result);
            intent.putExtra("Response_Page", result);
        } else if (type == "event") {
            Log.v("response_event", result);
            intent.putExtra("Response_Event", result);
        } else if (type == "place") {
            Log.v("response_place", result);
            intent.putExtra("Response_Place", result);
        } else if (type == "group") {
            Log.v("response_group", result);
            intent.putExtra("Response_Group", result);
            startActivity(intent);
        }

    }

    public class onbuttonclickHttpPost extends AsyncTask<String, Void, String> {

        String type;
        Intent intent;


        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v("Query", params[0]);
            setType(params[1]);
            try {
                byte[] result = null;
                URL url = new URL("http://vatsal-angularenv.us-west-2.elasticbeanstalk.com/index.php/main.php?query=" + params[0] + "&type=" + params[1]);
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
            Handle_Intent(type, result);
//            Log.v("Response Returned", result);

        }
    }






    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mcontext=view.getContext();
        mButton = (Button) view.findViewById(R.id.searchbutton);
        mClearButton = (Button) view.findViewById(R.id.clearbutton);
        mEdit = (EditText) view.findViewById(R.id.edit_query);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String query = mEdit.getText().toString();
                        Log.v("EditText", query);
                        String types[] = new String[]{"user", "page", "event", "place", "group"};
                        for (String type : types) {
                            new onbuttonclickHttpPost().execute(query, type);
                        }


                    }
                });

        mClearButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mEdit.setText("");

                    }
                });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
