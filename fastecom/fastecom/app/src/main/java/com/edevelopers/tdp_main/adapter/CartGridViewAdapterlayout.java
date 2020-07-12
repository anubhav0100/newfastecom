package com.edevelopers.tdp_main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.activities_M.CartActivity;
import com.edevelopers.tdp_main.models.Team;
import com.squareup.picasso.Picasso;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

public class CartGridViewAdapterlayout extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private CartGridViewAdapterlayout adapterlayout;
    private List<RecyclerViewItem> mItemList;
    private Activity activity;
    private Animation animi;

    public CartGridViewAdapterlayout(Activity activity, List<RecyclerViewItem> items, Animation ani) {
        this.activity = activity;
        this.mItemList = items;
        this.animi = ani;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_itemlist, parent, false);
            return new ItemViewHolder(view,new MyCustomEditTextListener());
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

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView,textView1,carttext,price,Tprice;
        private ImageView imageView;
        private ImageButton add,minus;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ItemViewHolder(@NonNull View view,MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);
            textView1 = (TextView) view.findViewById(R.id.text1);
            price = (TextView) view.findViewById(R.id.price);
            Tprice = (TextView) view.findViewById(R.id.Tprice);
            carttext = (TextView) view.findViewById(R.id.textbtntext);
            add = (ImageButton) view.findViewById(R.id.btnadd);
            minus = (ImageButton) view.findViewById(R.id.btnminus);

            /*this.myCustomEditTextListener = myCustomEditTextListener;
            this.minus.setOnClickListener(myCustomEditTextListener);*/
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

    private void populateItemRows(final ItemViewHolder viewHolder, int position) {
       Picasso.with(activity).load(mItemList.get(position).getImageUrl()).into(viewHolder.imageView);
       viewHolder.textView.setText(mItemList.get(position).getName().split("<#>")[1]);
       viewHolder.textView1.setText(mItemList.get(position).getName().split("<#>")[0]);
       viewHolder.carttext.setText(mItemList.get(position).getModule1());
       viewHolder.price.setText(mItemList.get(position).getModule2());
       viewHolder.Tprice.setText(mItemList.get(position).getModule4());
        adapterlayout = new CartGridViewAdapterlayout(activity,mItemList,animi);
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = viewHolder.getAdapterPosition();
                int i = Integer.parseInt(viewHolder.carttext.getText().toString());
                i++;
                String s = String.valueOf(i);
                mItemList.get(pos).setModule1(s);
                float price = Float.parseFloat(mItemList.get(pos).getModule2().split("Rs.")[1]);
                float tprice = 0;

                tprice = price * i;
                mItemList.get(pos).setModule4("Rs. "+String.valueOf(tprice));
                viewHolder.carttext.setText(mItemList.get(pos).getModule1());
                String Query = "UPDATE CART SET QUANTITY = '"+i+"' WHERE ID = '"+mItemList.get(pos).getModule3()+"' AND CART_ID = '"+mItemList.get(pos).getCart()+"';";
                VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Team> teams) {
                        if(teams.get(0).getcol1().equals("Saved")){
                            try{
                                viewHolder.Tprice.setText(mItemList.get(pos).getModule4());
                                adapterlayout.notifyItemChanged(pos);
                                CartActivity.setdetailsfunction();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = viewHolder.getAdapterPosition();
                int i = Integer.parseInt(viewHolder.carttext.getText().toString());
                if(i>1){
                    i--;
                    String s = String.valueOf(i);
                    mItemList.get(pos).setModule1(s);
                    float price = Float.parseFloat(mItemList.get(pos).getModule2().split("Rs.")[1]);
                    float tprice = 0;
                    tprice = price * i;
                    mItemList.get(pos).setModule4("Rs. "+String.valueOf(tprice));
                    viewHolder.carttext.setText(mItemList.get(pos).getModule1());
                    String Query = "UPDATE CART SET QUANTITY = '"+i+"' WHERE ID = '"+mItemList.get(pos).getModule3()+"' AND CART_ID = '"+mItemList.get(pos).getCart()+"';";
                    VolleyExecute.volleydynamicsavefun(activity, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> teams) {
                            if(teams.get(0).getcol1().equals("Saved")){
                                try{
                                    viewHolder.Tprice.setText(mItemList.get(pos).getModule4());
                                    adapterlayout.notifyItemChanged(pos);
                                    CartActivity.setdetailsfunction();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    private class MyCustomEditTextListener implements View.OnClickListener {
        private int position;

        public void updatePosition(int position) {
            this.position = position;

        }

        @Override
        public void onClick(View v) {

        }
    }
}
