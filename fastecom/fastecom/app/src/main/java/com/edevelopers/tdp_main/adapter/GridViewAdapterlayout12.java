package com.edevelopers.tdp_main.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.CartService;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.activities_M.ProductsViewActivity;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GridViewAdapterlayout12 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<RecyclerViewItem> mItemList;
    private Activity activity;
    private Animation animi;
    private MyReceiver myReceiver;
    public static final String CArtFILTER_ACTION_KEY = "CartGridLayout";

    public GridViewAdapterlayout12(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.mItemList = items;
        this.animi = ani;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            setReceiver(); // Broadcast Reciever
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_itemlist, parent, false);
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
        private TextView textView,textView1,textView2,addcart,removecart;
        private ProgressBar progressBar;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);
            textView1 = (TextView) view.findViewById(R.id.text1);
            textView2 = (TextView) view.findViewById(R.id.price);
            addcart = (TextView) view.findViewById(R.id.addcart);
            removecart = (TextView) view.findViewById(R.id.removecart);
            progressBar = (ProgressBar) view.findViewById(R.id.progressIndividual);

            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addcart.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    removecart.setVisibility(View.GONE);
                    String Productid = mItemList.get(getAdapterPosition()).getModule3();
                    String Query = "";
                    final int pos = getAdapterPosition();
                    if(sgen.Login_Id.equals("")){
                        String id =  sgen.getandroidID();
                        Query = "CALL AUTOCART('" + sgen.SHOP_Id + "','" + Productid + "','1','" + id + "',STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'),STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'));";
                        VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                            @Override
                            public void onSuccess(ArrayList<Team> teams) {
                                if(teams.get(0).getcol1().equals("Saved")){
                                    mItemList.get(pos).setCheckSelected(false);
                                    addcart.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.VISIBLE);
                                }
                                else{
                                    mItemList.get(pos).setCheckSelected(true);
                                    addcart.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    else{
                        Query = "CALL AUTOCART('" + sgen.SHOP_Id + "','" + Productid + "','1','" + sgen.Login_Id + "',STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'),STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'));";
                        VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                            @Override
                            public void onSuccess(ArrayList<Team> teams) {
                                if(teams.get(0).getcol1().equals("Saved")){
                                    mItemList.get(pos).setCheckSelected(false);
                                    addcart.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.VISIBLE);
                                }
                                else{
                                    mItemList.get(pos).setCheckSelected(true);
                                    addcart.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            });

            removecart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addcart.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    removecart.setVisibility(View.GONE);
                    String Productid = mItemList.get(getAdapterPosition()).getModule3();
                    final int pos = getAdapterPosition();
                    if(sgen.Login_Id.equals("")){
                        String id =  sgen.getandroidID();
                        String Query = "Delete From CART where PC_ID = '"+ Productid +"' and S_ID = '"+ sgen.SHOP_Id +"' and C_ID = '"+ id +"'";
                        VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                            @Override
                            public void onSuccess(ArrayList<Team> teams) {
                                if(teams.get(0).getcol1().equals("Saved")){
                                    mItemList.get(pos).setCheckSelected(true);
                                    addcart.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.GONE);
                                }
                                else {
                                    mItemList.get(pos).setCheckSelected(false);
                                    addcart.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }else {
                        String Query = "Delete From CART where PC_ID = '"+ Productid +"' and S_ID = '"+ sgen.SHOP_Id +"' and C_ID = '"+ sgen.Login_Id +"'";
                        VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                            @Override
                            public void onSuccess(ArrayList<Team> teams) {
                                if(teams.get(0).getcol1().equals("Saved")){
                                    mItemList.get(pos).setCheckSelected(true);
                                    addcart.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.GONE);
                                }
                                else {
                                    mItemList.get(pos).setCheckSelected(false);
                                    addcart.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    removecart.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
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
        viewHolder.textView1.setText(mItemList.get(position).getModule2());
        viewHolder.textView2.setText(mItemList.get(position).getModule1());
        if(mItemList.get(position).getCheckSelected()){
            viewHolder.addcart.setVisibility(View.VISIBLE);
            viewHolder.removecart.setVisibility(View.GONE);
        }
        else{
            viewHolder.addcart.setVisibility(View.GONE);
            viewHolder.removecart.setVisibility(View.VISIBLE);
        }
    }

    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CArtFILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(activity).registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastMessage");
            // textView.setText(textView.getText() + "\n" + message);
        }
    }
}