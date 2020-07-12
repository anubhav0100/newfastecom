package com.edevelopers.tdp_main.adapter;

import android.graphics.Bitmap;

public class RecyclerViewItem {

    private Bitmap bitmap;
    private String name;
    private String module3;
    private String module1;
    private String module2;
    private String module4;
    private String cart;
    private String ImageUrl;
    private boolean checkSelected = false;

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

    public RecyclerViewItem(Bitmap bitmap, String name, String module3,String module1,String module2,String cart){
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
        this.module1 = module1;
        this.module2 = module2;
        this.cart = cart;
    }

    public RecyclerViewItem(Bitmap bitmap, String name, String module3,String module1,String module2,String cart,String module4,boolean checkSelected){
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
        this.module1 = module1;
        this.module2 = module2;
        this.cart = cart;
        this.module4 = module4;
        this.checkSelected = checkSelected;
    }

    public RecyclerViewItem(Bitmap bitmap, String name, String module3,String module1,String module2,String cart,String module4,String ImageUrl,boolean checkSelected){
        this.bitmap = bitmap;
        this.name = name;
        this.module3 = module3;
        this.module1 = module1;
        this.module2 = module2;
        this.cart = cart;
        this.module4 = module4;
        this.ImageUrl = ImageUrl;
        this.checkSelected = checkSelected;
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
    public String getModule4() {
        return module4;
    }

    public void setModule1(String module1) {
        this.module1 = module1;
    }

    public void setModule2(String module2) {
        this.module2 = module2;
    }

    public void setModule3(String module3) {
        this.module3 = module3;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setModule4(String module4) {
        this.module4 = module4;
    }

    public String getCart() {
        return cart;
    }

    public boolean getCheckSelected() {
        return checkSelected;
    }

    public void setCheckSelected(boolean checkSelected) {
        this.checkSelected = checkSelected;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
}
