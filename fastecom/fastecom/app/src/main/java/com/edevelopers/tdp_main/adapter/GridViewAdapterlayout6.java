package com.edevelopers.tdp_main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.activities_M.ViewActivity;
import com.edevelopers.tdp_main.sgen;

import java.util.List;

public class GridViewAdapterlayout6 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<RecyclerViewItem> mItemList;
    private Activity activity;
    private Animation animi;

    public GridViewAdapterlayout6(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.mItemList = items;
        this.animi = ani;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid4, parent, false);
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

        public ItemViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.head);
            textView1 = (TextView) view.findViewById(R.id.body);
            imageView = (ImageView) view.findViewById(R.id.imagehead);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sgen.mq5 = mItemList.get(getAdapterPosition()).getModule3().toString();
                    sgen.Menu_T1 = mItemList.get(getAdapterPosition()).getName().toString();
                    sgen.viewId = mItemList.get(getAdapterPosition()).getModule3().toString();
                    activity.startActivity(new Intent(activity, ViewActivity.class));
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

        viewHolder.imageView.setImageBitmap(mItemList.get(position).getBitmapId());
        viewHolder.textView.setText(mItemList.get(position).getName());
    }


}