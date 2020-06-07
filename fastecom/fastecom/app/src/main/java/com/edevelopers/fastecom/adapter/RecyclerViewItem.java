package com.edevelopers.fastecom.adapter;

import android.graphics.Bitmap;
import android.widget.ImageButton;

public class RecyclerViewItem {

    private Bitmap bitmap;
    private String name;
    private String module3;
    private String module1;
    private String module2;
    private String cart;

    public RecyclerViewItem(Bitmap bitmap, String name, String module3) {
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
    }

    public RecyclerViewItem(Bitmap bitmap, String name, String module3,String module1){
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
        this.module1 = module1;
    }

    public RecyclerViewItem(Bitmap bitmap, String name, String module3,String module1,String cart){
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
        this.module1 = module1;
        this.cart = cart;
    }

    public Bitmap getBitmapId() {
        return bitmap;
    }

    public String getName() {
        return name;
    }

    public String getModule3() {
        return module3;
    }
    public String getModule1() {
        return module1;
    }
    public String getModule2() {
        return module2;
    }
    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
}
