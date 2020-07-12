package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.CartService;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout10;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout12;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.fragments.Home_Frag;
import com.edevelopers.tdp_main.models.Product;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProductsViewActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private RecyclerView gridView;
    private GridViewAdapterlayout12 gridViewAdapter;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> ProductsoperatingSystems;
    Animation anim;
    public static Dialog dialog;
    private SearchView searchView;
    MyReceiver myReceiver;

    boolean isLoading1 = false;
    private int currentSize = 0;
    private String search = "";

    public static final String FILTER_ACTION_KEY = "ProductsViewActivity";


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products_view);

        sgen.backview = "ProductsView";
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.grid);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchproductsview);
       /* final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setClickable(true);
        searchView.setIconified(false);*/
        //searchView.requestFocusFromTouch();

        gridView = (RecyclerView) findViewById(R.id.grid);
        anim= AnimationUtils.loadAnimation(ProductsViewActivity.this, R.animator.cycle);
        gridView.setHasFixedSize(true);

        dialog = new Dialog(ProductsViewActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);

        dialog.show();
        functiongetlist();

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = query;
                dialog.show();
                functiongetlist();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        initScrollListener();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go To Cart", Snackbar.LENGTH_LONG)
                        .setAction("Cart", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProductsViewActivity.this,CartActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }


    private void functiongetlist(){
        String Query = "";
        String LoginID = "";
        if(sgen.Login_Id.equals("")){
            LoginID = sgen.getandroidID();
        }else{
            LoginID = sgen.Login_Id;
        }
        if(sgen.shopcatset.equals("")){
            if(!search.equals("")){
                Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                        "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                        "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                        "SPC.STATUS AS META_KEYWORD," +
                        "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                        "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                        "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                        "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                        "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                        "P.HSN, " +
                        "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                        "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                        "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                        "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                        "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                        "where P.PC_TYPE = 'PRD' and P.PC_NAME LIKE '%"+search+"%' ORDER BY PC_ID asc LIMIT 20 ;";
            }
            else{
                Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                        "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                        "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                        "SPC.STATUS AS META_KEYWORD," +
                        "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                        "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                        "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                        "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                        "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                        "P.HSN, " +
                        "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                        "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                        "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                        "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                        "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                        "where P.PC_TYPE = 'PRD' ORDER BY PC_ID asc LIMIT 20 ;";
            }
        }
        else{
            if(!search.equals("")){
                Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                        "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                        "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                        "SPC.STATUS AS META_KEYWORD," +
                        "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                        "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                        "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                        "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                        "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                        "P.HSN, " +
                        "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                        "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                        "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                        "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                        "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                        "where P.PC_TYPE = 'PRD' and FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'"+ sgen.shopcatset+"') and P.PC_NAME LIKE '%"+search+"%' ORDER BY PC_ID asc LIMIT 20 ;";
            }
            else{
                Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                        "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                        "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                        "SPC.STATUS AS META_KEYWORD," +
                        "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                        "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                        "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                        "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                        "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                        "P.HSN, " +
                        "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                        "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                        "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                        "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                        "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                        "where P.PC_TYPE = 'PRD' and FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'"+ sgen.shopcatset+"') ORDER BY PC_ID asc LIMIT 20 ;";
            }
        }

        VolleyExecute.volleydynamicgetfunproduct(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallbackproducts() {
            @Override
            public void onSuccess(ArrayList<Product> products) {
                ProductsoperatingSystems = new ArrayList<>();
                for (int i = 0; i < products.size(); i++) {
                    try {
                        boolean bb = false;
                        if(products.get(i).getCARTID().equals("ENABLE")){
                            bb = true;
                        }
                        ProductsoperatingSystems.add(new RecyclerViewItem(null,
                                ""+products.get(i).getPC_NAME(),
                                ""+String.valueOf(Integer.parseInt(products.get(i).getPC_ID())),
                                ""+products.get(i).getPC_PRICE(),
                                ""+products.get(i).getDESCRIPTION(),
                                ""+products.get(i).getMETA_TAG(),
                                ""+products.get(i).getMETA_DESCRIPTION(),
                                ""+products.get(i).getPC_IMG_ID(),
                                bb));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                GridLayoutManager layoutManager = new GridLayoutManager(ProductsViewActivity.this, 1, GridLayoutManager.VERTICAL, false);
                gridView.setLayoutManager(layoutManager);
                gridViewAdapter = new GridViewAdapterlayout12(ProductsViewActivity.this,ProductsoperatingSystems,anim);
                gridView.setAdapter(gridViewAdapter);
                dialog.hide();
            }
        });
    }


    private void initScrollListener() {
        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading1) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == ProductsoperatingSystems.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading1 = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        ProductsoperatingSystems.add(null);
        gridViewAdapter.notifyItemInserted(ProductsoperatingSystems.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ProductsoperatingSystems.remove(ProductsoperatingSystems.size() - 1);
                int scrollPosition = ProductsoperatingSystems.size();
                gridViewAdapter.notifyItemRemoved(scrollPosition);
                currentSize = scrollPosition;
                int nextLimit = currentSize + 20;


                String Query = "";
                String LoginID = "";
                if(sgen.Login_Id.equals("")){
                    LoginID = sgen.getandroidID();
                }else{
                    LoginID = sgen.Login_Id;
                }
                if(sgen.shopcatset.equals("")){
                    if(!search.equals("")){
                        Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                                "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                                "SPC.STATUS AS META_KEYWORD," +
                                "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                                "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                                "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                                "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                                "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                                "P.HSN, " +
                                "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                                "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                                "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                                "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                                "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                                "where P.PC_TYPE = 'PRD' and P.PC_NAME LIKE '%"+search+"%' ORDER BY PC_ID asc LIMIT "+nextLimit+";";
                    }
                    else{
                        Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                                "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                                "SPC.STATUS AS META_KEYWORD," +
                                "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                                "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                                "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                                "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                                "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                                "P.HSN, " +
                                "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                                "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                                "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                                "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                                "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                                "where P.PC_TYPE = 'PRD' ORDER BY PC_ID asc LIMIT "+nextLimit+" ;";
                    }
                }
                else{
                    if(!search.equals("")){
                        Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                                "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                                "SPC.STATUS AS META_KEYWORD," +
                                "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                                "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                                "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                                "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                                "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                                "P.HSN, " +
                                "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                                "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                                "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                                "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                                "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                                "where P.PC_TYPE = 'PRD' and FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'"+ sgen.shopcatset+"') and P.PC_NAME LIKE '%"+search+"%' ORDER BY PC_ID asc LIMIT "+nextLimit+" ;";
                    }
                    else{
                        Query = "Select P.PC_ID,P.PC_NAME,P.DESCRIPTION,C.PC_NAME AS PRODUCT_CATEGORY_LEVEL," +
                                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) as PC_PRICE,CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS PC_IMG_ID," +
                                "SPC.SPC_ID AS META_TAG,SPC.ID AS META_DESCRIPTION," +
                                "SPC.STATUS AS META_KEYWORD," +
                                "P.PC_TYPE,P.PC_PRIORITY,PC_MODEL,P.PC_SKU,P.PC_UPC," +
                                "P.PC_EAN,P.PC_JAN,P.PC_ISBN,P.PC_MPN,P.PC_LOCATION,P.PC_QUANTITY,PS.STOCK_NAME AS PC_STOCK_STATUS_ID,P.PC_MANUFACTURING_ID," +
                                "P.PC_SHIPPING,P.PC_POINTS,P.PC_TAX_CLASS_ID,P.PC_DATA_AVAILABLE," +
                                "truncate(P.PC_WEIGHT,2) as PC_WEIGHT,M1.M_UVAL AS PC_WEIGHT_CLASS_ID,truncate(P.PC_LENGTH,2) as PC_LENGTH,truncate(P.PC_WIDTH,2) as PC_WIDTH,truncate(P.PC_HEIGHT,2) as PC_HEIGHT,M2.M_UVAL AS PC_LENGTH_CLASS_ID," +
                                "P.PC_SUBTRACT,P.PC_MINIMUM,P.PC_SORT_ORDER,P.PC_STATUS,P.CREATED_DATE," +
                                "P.HSN, " +
                                "CASE WHEN CA.CART_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS CARTID,CASE WHEN WA.W_ID IS NULL THEN 'ENABLE' ELSE 'DISABLE' END AS WISHID " +
                                "From PRODUCT_MAIN P inner join PRODUCT_CATEGORY_LEVEL C ON C.PC_TYPE = 'PCL' AND C.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                                "inner join FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F_TYPE = 'PRD' " +
                                "left join MASTER M1 ON M1.TYPE = 'WET' AND M1.M_ID = P.PC_WEIGHT_CLASS_ID left join MASTER M2 ON M2.TYPE = 'LNT' AND M2.M_ID = P.PC_LENGTH_CLASS_ID left join PRODUCT_STOCK PS ON PS.STOCK_ID = P.PC_STOCK_STATUS_ID " +
                                "LEFT join CART CA ON CA.PC_ID = P.PC_ID AND CA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "LEFT join WISHLIST WA ON WA.PC_ID = P.PC_ID AND WA.C_ID = '"+LoginID+"' AND CA.S_ID = '"+sgen.SHOP_Id+"' " +
                                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = '"+sgen.SHOP_Id+"' AND SPC.PC_ID = P.PC_ID AND SPC.STATUS = 'A' " +
                                "where P.PC_TYPE = 'PRD' and FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'"+ sgen.shopcatset+"') ORDER BY PC_ID asc LIMIT "+nextLimit+" ;";
                    }
                }

                VolleyExecute.volleydynamicgetfunproduct(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallbackproducts() {
                    @Override
                    public void onSuccess(ArrayList<Product> products) {

                        for (int i = currentSize; i < products.size(); i++) {
                            try {
                                boolean bb = false;
                                if(products.get(i).getCARTID().equals("ENABLE")){
                                    bb = true;
                                }
                                ProductsoperatingSystems.add(new RecyclerViewItem(null,
                                        ""+products.get(i).getPC_NAME(),
                                        ""+String.valueOf(Integer.parseInt(products.get(i).getPC_ID())),
                                        ""+products.get(i).getPC_PRICE(),
                                        ""+products.get(i).getDESCRIPTION(),
                                        ""+products.get(i).getMETA_TAG(),
                                        ""+products.get(i).getMETA_DESCRIPTION(),
                                        ""+products.get(i).getPC_IMG_ID(),
                                        bb));
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        gridViewAdapter.notifyDataSetChanged();
                        isLoading1 = false;
                    }
                });
            }
        }, 2000);
    }

    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastMessage");
            if(message.equals("SetLAyout")){
                GridLayoutManager layoutManager = new GridLayoutManager(ProductsViewActivity.this, 1, GridLayoutManager.VERTICAL, false);
                gridView.setLayoutManager(layoutManager);
                gridViewAdapter = new GridViewAdapterlayout12(ProductsViewActivity.this,ProductsoperatingSystems,anim);
                gridView.setAdapter(gridViewAdapter);
            }
            dialog.hide();
        }
    }

    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}