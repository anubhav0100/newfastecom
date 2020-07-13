package com.edevelopers.tdp_main.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapterlayout18 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<RecyclerViewItem> mItemList;
    private Activity activity;
    private Animation animi;
    private MyReceiver myReceiver;
    public static final String CArtFILTER_ACTION_KEY = "CartGridLayout";

    public GridViewAdapterlayout18(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.mItemList = items;
        this.animi = ani;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            setReceiver(); // Broadcast Reciever
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordermain_itemlist, parent, false);
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
        private RecyclerView nestedscroll;
        private GridViewAdapterlayout17 gridViewAdapter;
        private ArrayList<RecyclerViewItem> operatingSystems;
        private TextView textView,textView1,textView2,textView3,accept,deliverorder,details;
        private ProgressBar progressBar;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            //imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);
            textView1 = (TextView) view.findViewById(R.id.text1);
            textView2 = (TextView) view.findViewById(R.id.price);
            textView3 = (TextView) view.findViewById(R.id.text2);
            accept = (TextView) view.findViewById(R.id.accept);
            deliverorder = (TextView) view.findViewById(R.id.deliverorder);
            details = (TextView) view.findViewById(R.id.detalils);
            nestedscroll = (RecyclerView) view.findViewById(R.id.nestedscroll);
            progressBar = (ProgressBar) view.findViewById(R.id.progressIndividual);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void functiongetlist(String id,String Add_ID){
            String Query = "Select  " +
                    "CONCAT(O.ID,'!~!~!',O.ORDER_ID,'!~!~!',O.S_ID) AS COL1, " +
                    "CONCAT(SM.S_NAME,'!~!~!',O.PC_ID,'!~!~!',O.QUANTITY,'!~!~!',P.PC_NAME,'!~!~!',P.DESCRIPTION) AS COL2, " +
                    "CASE WHEN O.STATUS = 'P' THEN 'PENDING' " +
                    "WHEN O.STATUS = 'A' THEN 'ACCEPTED' " +
                    "WHEN O.STATUS = 'O' THEN 'Out For Delivery' " +
                    "ELSE 'WAITING FOR RESPONCE' " +
                    "END AS COL3, " +
                    "CONCAT('Rs.',truncate((SPC.PC_PRICE * O.QUANTITY),2)) AS COL4, " +
                    "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5  " +
                    "From ORDER_TABLE O " +
                    "INNER JOIN SHOP_MASTER SM ON SM.S_ID = O.S_ID  " +
                    "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = O.S_ID AND SPC.PC_ID = O.PC_ID  " +
                    "INNER JOIN PRODUCT_MAIN P ON P.PC_ID = O.PC_ID AND P.PC_TYPE = 'PRD' " +
                    "INNER JOIN FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F.F_TYPE = 'PRD' " +
                    "WHERE O.S_ID = '"+ id +"' AND O.C_ID = '"+sgen.Login_Id+"' AND O.DELIVERY_ADD = '"+Add_ID+"'  AND O.STATUS IN ('P','A','W','O') order by O.ORDER_ID ASC;";

            VolleyExecute.volleydynamicgetfun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                    ArrayList<Team> fed = teams;
                    operatingSystems = new ArrayList<>();
                    for (int i = 0; i < teams.size(); i++) {
                        try {
                            Bitmap icon = null;
                            operatingSystems.add(new RecyclerViewItem(null,
                                    "" +fed.get(i).getcol2().split("!~!~!")[0]+"<#>" + fed.get(i).getcol2().split("!~!~!")[3]+"<#>" + fed.get(i).getcol2().split("!~!~!")[4],
                                    "" +  fed.get(i).getcol1().split("!~!~!")[0],
                                    "" + fed.get(i).getcol2().split("!~!~!")[2],
                                    "" + fed.get(i).getcol3(),
                                    ""+String.valueOf(Integer.parseInt(fed.get(i).getcol1().split("!~!~!")[1])),
                                    ""+fed.get(i).getcol4(),
                                    "" + fed.get(i).getcol5(),
                                    true
                            ));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    try{
                        GridLayoutManager layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
                        nestedscroll.setLayoutManager(layoutManager);
                        gridViewAdapter = new GridViewAdapterlayout17(activity,operatingSystems,animi);
                        nestedscroll.setAdapter(gridViewAdapter);
                    }catch (Exception e){
                        e.printStackTrace();
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
        //Picasso.with(activity).load(mItemList.get(position).getImageUrl()).into(viewHolder.imageView);
        viewHolder.textView.setText(mItemList.get(position).getName());
        viewHolder.textView2.setText(mItemList.get(position).getModule4());
        viewHolder.textView1.setText("Rs. "+mItemList.get(position).getModule2());
        viewHolder.textView3.setText("Status: "+mItemList.get(position).getCart());
        viewHolder.functiongetlist(mItemList.get(position).getModule3(),mItemList.get(position).getImageUrl().split("<#>")[1]);

        viewHolder.accept.setVisibility(View.VISIBLE);
        viewHolder.deliverorder.setVisibility(View.GONE);
        viewHolder.details.setVisibility(View.GONE);

        viewHolder.nestedscroll.setNestedScrollingEnabled(true);
        viewHolder.nestedscroll.setHasFixedSize(true);

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