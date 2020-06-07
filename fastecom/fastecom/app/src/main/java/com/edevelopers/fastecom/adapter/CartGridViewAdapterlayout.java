package com.edevelopers.fastecom.adapter;

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

import com.edevelopers.fastecom.R;

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

        private TextView textView,textView1,carttext;
        private ImageView imageView;
        private ImageButton add,minus;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ItemViewHolder(@NonNull View view,MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);
            textView1 = (TextView) view.findViewById(R.id.text1);
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

       viewHolder.textView.setText(mItemList.get(position).getName());
       viewHolder.textView1.setText(mItemList.get(position).getModule1());
       viewHolder.carttext.setText(mItemList.get(position).getCart());
       viewHolder.imageView.setImageBitmap(mItemList.get(position).getBitmapId());
        adapterlayout = new CartGridViewAdapterlayout(activity,mItemList,animi);
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                int i = Integer.parseInt(viewHolder.carttext.getText().toString());
                i++;
                String s = String.valueOf(i);
                mItemList.get(pos).setCart(s);
                try{
                    viewHolder.carttext.setText(mItemList.get(pos).getCart());
                    adapterlayout.notifyItemChanged(pos);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                int i = Integer.parseInt(viewHolder.carttext.getText().toString());
                if(i>1){
                    i--;
                    String s = String.valueOf(i);
                    mItemList.get(pos).setCart(s);
                    try{
                        viewHolder.carttext.setText(mItemList.get(pos).getCart());
                        adapterlayout.notifyItemChanged(pos);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
