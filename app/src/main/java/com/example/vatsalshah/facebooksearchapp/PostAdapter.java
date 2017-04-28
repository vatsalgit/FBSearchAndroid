package com.example.vatsalshah.facebooksearchapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vatsalshah.facebooksearchapp.PostFragment.OnListFragmentInteractionListener;
import com.example.vatsalshah.facebooksearchapp.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<PostItem> postItemList;
    private Context mcontext;
    private final OnListFragmentInteractionListener mListener;

    public PostAdapter(List<PostItem> postItemList, OnListFragmentInteractionListener listener,Context context) {
        this.postItemList = postItemList;
        this.mListener = listener;
        this.mcontext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PostItem Item = postItemList.get(position);
        holder.mItem = Item;

        if(Item.getPicture()!="")
        Picasso.with(mcontext).load(Item.getPicture()).into(holder.PostPicture);

        holder.NameView.setText(Item.getName());

        if(Item.getDate()!="")
        holder.DateView.setText(Item.getDate().replace("T"," ").substring(0,Item.getDate().length()-5));
        holder.PostContent.setText(Item.getPost());



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
        return (null != postItemList ? postItemList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView NameView;
        public final TextView PostContent;
        public final TextView DateView;
        public final ImageView PostPicture;

        public PostItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            NameView = (TextView) view.findViewById(R.id.post_name);
            PostContent = (TextView) view.findViewById(R.id.post_content);
            DateView = (TextView) view.findViewById(R.id.post_date);
            PostPicture = (ImageView) view.findViewById(R.id.post_img);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
