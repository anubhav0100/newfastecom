package com.edevelopers.fastecom.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.activities_M.ViewActivity;
import com.edevelopers.fastecom.sgen;

import java.util.List;


public class ListViewAdapterlayout2 extends RecyclerView.Adapter<ListViewAdapterlayout2.ViewHolder> {
    private Activity activity;
    private List<RecyclerViewItem> items;

    public ListViewAdapterlayout2(Activity activity, List<RecyclerViewItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
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
            textView.setOnClickListener(new View.OnClickListener() {
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
