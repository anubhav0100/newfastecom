package com.edevelopers.tdp_main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.activities_M.ViewActivity;
import com.edevelopers.tdp_main.sgen;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private List<RecyclerViewItem> items;
    private Activity activity;
    private Animation animi;

    public GridViewAdapter(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.items = items;
        this.animi = ani;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(GridViewAdapter.ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageBitmap(items.get(position).getBitmapId());
        viewHolder.textView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * View holder to display each RecylerView item
     */

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;



        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sgen.mq5 = items.get(getAdapterPosition()).getModule3().toString();
                    sgen.Menu_T1 = items.get(getAdapterPosition()).getName().toString();
                    sgen.viewId = items.get(getAdapterPosition()).getModule1().toString();
                    activity.startActivity(new Intent(activity, ViewActivity.class));
                }
            });
        }
    }
}
