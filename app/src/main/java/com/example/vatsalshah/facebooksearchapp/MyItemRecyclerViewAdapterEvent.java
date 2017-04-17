package com.example.vatsalshah.facebooksearchapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.EventFragment.OnListFragmentInteractionListener;
import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapterEvent extends RecyclerView.Adapter<MyItemRecyclerViewAdapterEvent.ViewHolder> {

    private List<ResultItem> resultItemList;
//    private Context mcontext;

    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapterEvent(List<ResultItem> resultItemList,OnListFragmentInteractionListener listener) {
        this.resultItemList=resultItemList;
        this.mListener=listener;
//        this.mcontext=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ResultItem Item = resultItemList.get(position);
        holder.mItem = Item;
        holder.mPictureView.setText("Picture");
        holder.mNameView.setText(Item.getName());
        holder.mDetailsView.setText("Details");
        holder.mFavView.setText("Fav");


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != resultItemList ? resultItemList.size() : 0);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFavView;
        public final TextView mPictureView;
        public final TextView mNameView;
        public final TextView mDetailsView;
        public ResultItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPictureView = (TextView) view.findViewById(R.id.picture);
            mNameView  = (TextView) view.findViewById(R.id.name);
            mDetailsView  = (TextView) view.findViewById(R.id.details);
            mFavView = (TextView) view.findViewById(R.id.fav);

        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
