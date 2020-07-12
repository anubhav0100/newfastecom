package com.edevelopers.tdp_main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.activities_M.ProductsViewActivity;
import com.edevelopers.tdp_main.activities_M.ViewActivity;
import com.edevelopers.tdp_main.sgen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapterlayout8 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<RecyclerViewItem> mItemList;
    private Activity activity;
    private Animation animi;

    public GridViewAdapterlayout8(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.mItemList = items;
        this.animi = ani;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid3, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nrecycler, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView,textView1;
        private RatingBar ratingBar;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text); //name
            textView1 = (TextView) view.findViewById(R.id.intro); // time
            imageView = (ImageView) view.findViewById(R.id.image);
            ratingBar = (RatingBar) view.findViewById(R.id.MyRating);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sgen.SHOP_Id = mItemList.get(getAdapterPosition()).getModule3();
                    activity.startActivity(new Intent(activity, ProductsViewActivity.class));
                }
            });
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        Picasso.with(activity).load(mItemList.get(position).getImageUrl()).into(viewHolder.imageView);
        viewHolder.textView.setText(mItemList.get(position).getName());
        viewHolder.textView1.setText(mItemList.get(position).getModule1());
        viewHolder.ratingBar.setRating(Float.parseFloat(mItemList.get(position).getModule2()));
    }
}