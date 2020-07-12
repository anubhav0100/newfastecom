package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.CartService;
import com.edevelopers.tdp_main.Services.HomeService;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout8;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.fragments.Home_Frag;
import com.edevelopers.tdp_main.models.Productcat;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShopViewActivity extends AppCompatActivity {
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





    private MyReceiverpublic myReceiverpublic;
    public static final String SHOPFILTER_ACTION_KEY = "SHOPFILTER_ACTION_KEY";
    
    private RecyclerView gridView1,gridView2,gridView3,gridView4;
    private GridViewAdapterlayout8 gridViewAdapter3;
    private Dialog dialog;
    private Animation anim;
    private TextView shoptextview;
    private ArrayList<RecyclerViewItem> operatingSystems3;
    boolean isLoading1 = false;
    private int currentSize = 0;
    
    
    
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

        setContentView(R.layout.activity_shop_view);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.grid);

        gridView2 = (RecyclerView) findViewById(R.id.grid);
        anim= AnimationUtils.loadAnimation(ShopViewActivity.this, R.animator.cycle);
        gridView2.setHasFixedSize(true);

        shoptextview = findViewById(R.id.Textcontent);

        dialog = new Dialog(ShopViewActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);

        dialog.show();
        sgen.activity = ShopViewActivity.this;
        functioncategoryvalsetvalShopViewActivity(sgen.PRODUCTCATEGORY_ID);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }


    private void functioncategoryvalsetvalShopViewActivity(final String data) {
        String Query = "Select CASE  WHEN pc3.PC_ID IS NULL THEN CASE  WHEN pc2.PC_ID IS NULL THEN pc1.PC_ID ELSE pc2.PC_ID END ELSE pc3.PC_ID  END AS PC_ID,  " +
                " CASE  WHEN pc3.PC_NAME IS NULL THEN CASE  WHEN pc2.PC_NAME IS NULL THEN pc1.PC_NAME ELSE pc2.PC_NAME END ELSE pc3.PC_NAME  END AS PC_NAME, " +
                " CASE  WHEN pc3.PC_LEVEL IS NULL THEN CASE  WHEN pc2.PC_LEVEL IS NULL THEN pc1.PC_LEVEL ELSE pc2.PC_LEVEL END ELSE pc3.PC_LEVEL  END AS PC_LEVEL, " +
                " CASE  WHEN pc3.PC_LEVEL_NAME1 IS NULL THEN CASE  WHEN pc2.PC_LEVEL_NAME1 IS NULL THEN pc1.PC_LEVEL_NAME1 ELSE pc2.PC_LEVEL_NAME1 END ELSE pc3.PC_LEVEL_NAME1  END AS PC_LEVEL_NAME1, " +
                " CASE  WHEN pc3.PC_LEVEL_NAME2 IS NULL THEN CASE  WHEN pc2.PC_LEVEL_NAME2 IS NULL THEN pc1.PC_LEVEL_NAME2 ELSE pc2.PC_LEVEL_NAME2 END ELSE pc3.PC_LEVEL_NAME2  END AS PC_LEVEL_NAME2, " +
                " CASE  WHEN pc3.PC_LEVEL_NAME3 IS NULL THEN CASE  WHEN pc2.PC_LEVEL_NAME3 IS NULL THEN pc1.PC_LEVEL_NAME3 ELSE pc2.PC_LEVEL_NAME3 END ELSE pc3.PC_LEVEL_NAME3  END AS PC_LEVEL_NAME3, " +
                " CASE  WHEN pc3.PC_ORDER IS NULL THEN CASE  WHEN pc2.PC_ORDER IS NULL THEN pc1.PC_ORDER ELSE pc2.PC_ORDER END ELSE pc3.PC_ORDER  END AS PC_ORDER, " +
                " CASE  WHEN pc3.PC_DEFAULT IS NULL THEN CASE  WHEN pc2.PC_DEFAULT IS NULL THEN pc1.PC_DEFAULT ELSE pc2.PC_DEFAULT END ELSE pc3.PC_DEFAULT  END AS PC_DEFAULT, " +
                " CASE  WHEN pc3.PC_UID IS NULL THEN CASE  WHEN pc2.PC_UID IS NULL THEN pc1.PC_UID ELSE pc2.PC_UID END ELSE pc3.PC_UID  END AS PC_UID, " +
                " CASE  WHEN pc3.PC_TYPE IS NULL THEN CASE  WHEN pc2.PC_TYPE IS NULL THEN pc1.PC_TYPE ELSE pc2.PC_TYPE END ELSE pc3.PC_TYPE  END AS PC_TYPE, " +
                " CASE  WHEN pc3.CREATED_DATE IS NULL THEN CASE  WHEN pc2.CREATED_DATE IS NULL THEN pc1.CREATED_DATE ELSE pc2.CREATED_DATE END ELSE pc3.CREATED_DATE  END AS CREATED_DATE, " +
                " CASE  WHEN pc3.IMG_ID IS NULL THEN CASE  WHEN pc2.IMG_ID IS NULL THEN pc1.IMG_ID ELSE pc2.IMG_ID END ELSE pc3.IMG_ID  END AS IMG_ID " +
                " From PRODUCT_CATEGORY_LEVEL pc  " +
                " Left join PRODUCT_CATEGORY_LEVEL pc1 on pc1.PC_LEVEL = '1' and pc1.PC_LEVEL_NAME3 = pc.PC_LEVEL_NAME3 and pc1.PC_LEVEL_NAME2 = pc.PC_LEVEL_NAME1 and pc1.PC_TYPE= pc.PC_TYPE  " +
                " Left join PRODUCT_CATEGORY_LEVEL pc2 on pc2.PC_LEVEL = '2' and pc2.PC_LEVEL_NAME3 = pc1.PC_LEVEL_NAME3 and pc2.PC_LEVEL_NAME2 = pc1.PC_LEVEL_NAME1 and pc2.PC_TYPE= pc1.PC_TYPE " +
                " Left join PRODUCT_CATEGORY_LEVEL pc3 on pc3.PC_LEVEL = '3' and pc3.PC_LEVEL_NAME3 = pc2.PC_LEVEL_NAME3 and pc3.PC_LEVEL_NAME2 = pc2.PC_LEVEL_NAME1 and pc3.PC_TYPE= pc2.PC_TYPE " +
                " where FIND_IN_SET(pc.PC_LEVEL_NAME3,'" + data + "') and  pc.PC_LEVEL = '0' and pc.PC_TYPE='PCL';";

        VolleyExecute.volleydynamicgetfunproductcat(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallbackproductcat() {
            @Override
            public void onSuccess(ArrayList<Productcat> productcats) {
                sgen.shopcatset = "";
                for (int i = 0; i < productcats.size(); i++) {
                    sgen.shopcatset += productcats.get(i).getPC_ID() + ",";
                }
                if (productcats.size() > 0) {
                    sgen.shopcatset = sgen.shopcatset.substring(0, sgen.shopcatset.length() - 1);
                    setadapteradapterShopViewActivity(sgen.shopcatset, data);

                }
            }
        });
    }

    private void setadapteradapterShopViewActivity(String set, final String data) {

        String query = "SELECT DISTINCT SM.S_ID as col1,CONCAT(SM.S_NAME,'!~!~!',SM.G_LOC) as col2,CONCAT(TIME_FORMAT(SPC.OPEN_HRF,'%l %p'),'-',TIME_FORMAT(SPC.OPEN_HRT,'%l %p'))AS COL3, " +
                " IFNULL((SELECT truncate(AVG(SF.R_POINT),1)FROM SHOP_FEEDBACK SF WHERE  SF.S_ID = SM.S_ID),'2.5')  AS COL4,  " +
                "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5 FROM SHOP_MASTER SM  " +
                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = SM.S_ID AND SPC.STATUS = 'A'" +
                "INNER JOIN PRODUCT_MAIN P ON P.PC_ID = SPC.PC_ID AND P.PC_TYPE = 'PRD' AND FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'" + set + "') " +
                "INNER JOIN PRODUCT_CATEGORY_LEVEL PCL ON PCL.PC_TYPE = 'PCL' AND PCL.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                "INNER JOIN M_USER MU ON MU.TYPE = 'SHU' AND MU.LOCATION LIKE '%"+sgen.SGLOC+"%'" +
                "inner join FILE_TAB F ON F.PU_ID = SM.PC_IMG_ID AND F.LINK_ID = SM.S_ID AND F.F_TYPE = 'SMF'  ORDER BY SM.S_ID ASC LIMIT 20";
        /*"WHERE SM.G_LOC = 'G_LOC';"*/

        VolleyExecute.volleydynamicgetfun(getApplicationContext(), "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
                operatingSystems3 = new ArrayList<RecyclerViewItem>();
                for (int i = 0; i < fed.size(); i++) {
                    try {
                        String[] latlang = fed.get(i).getcol2().split("!~!~!")[1].split(",");
                        float distance = sgen.getdistancegeo(sgen.latitudemain, sgen.longitudemain,Double.parseDouble(latlang[0]),Double.parseDouble(latlang[1]));

                        if(distance <= sgen.shopradius){ //10 km radius
                            operatingSystems3.add(new RecyclerViewItem(null,
                                    "" + fed.get(i).getcol2().split("!~!~!")[0],
                                    "" + fed.get(i).getcol1(),
                                    "" + fed.get(i).getcol3(),
                                    "" + fed.get(i).getcol4(),
                                    "",
                                    ""+fed.get(i).getcol2().split("!~!~!")[1],
                                    "" + fed.get(i).getcol5(),
                                    true
                            ));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                GridLayoutManager layoutManager3 = new GridLayoutManager(sgen.activity, 1, GridLayoutManager.VERTICAL, false);
                gridView2.setLayoutManager(layoutManager3);
                gridViewAdapter3 = new GridViewAdapterlayout8(sgen.activity,operatingSystems3,anim);
                gridView2.setAdapter(gridViewAdapter3);
                shoptextview.setText( data +" Shops Near By You");
                dialog.hide();
            }
        });
    }

    private void initScrollListener() {
        gridView2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading1) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == operatingSystems3.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading1 = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        operatingSystems3.add(null);
        gridViewAdapter3.notifyItemInserted(operatingSystems3.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                operatingSystems3.remove(operatingSystems3.size() - 1);
                int scrollPosition = operatingSystems3.size();
                gridViewAdapter3.notifyItemRemoved(scrollPosition);
                currentSize = scrollPosition;
                int nextLimit = currentSize + 20;

                String query = "SELECT DISTINCT SM.S_ID as col1,CONCAT(SM.S_NAME,'!~!~!',SM.G_LOC) as col2,CONCAT(TIME_FORMAT(SPC.OPEN_HRF,'%l %p'),'-',TIME_FORMAT(SPC.OPEN_HRT,'%l %p'))AS COL3, " +
                        " IFNULL((SELECT truncate(AVG(SF.R_POINT),1)FROM SHOP_FEEDBACK SF WHERE  SF.S_ID = SM.S_ID),'2.5')  AS COL4,  " +
                        "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5 FROM SHOP_MASTER SM  " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = SM.S_ID AND SPC.STATUS = 'A'" +
                        "INNER JOIN PRODUCT_MAIN P ON P.PC_ID = SPC.PC_ID AND P.PC_TYPE = 'PRD' AND FIND_IN_SET(P.PRODUCT_CATEGORY_LEVEL,'" + sgen.shopcatset + "') " +
                        "INNER JOIN PRODUCT_CATEGORY_LEVEL PCL ON PCL.PC_TYPE = 'PCL' AND PCL.PC_ID = P.PRODUCT_CATEGORY_LEVEL " +
                        "INNER JOIN M_USER MU ON MU.TYPE = 'SHU' AND MU.LOCATION LIKE '%"+sgen.SGLOC+"%'" +
                        "inner join FILE_TAB F ON F.PU_ID = SM.PC_IMG_ID AND F.LINK_ID = SM.S_ID AND F.F_TYPE = 'SMF'  ORDER BY SM.S_ID ASC LIMIT "+nextLimit+"";

                VolleyExecute.volleydynamicgetfun(ShopViewActivity.this, "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Team> teams) {
                        ArrayList<Team> fed = teams;
                        for (int i = currentSize; i < fed.size(); i++) {
                            try {
                                String[] latlang = fed.get(i).getcol2().split("!~!~!")[1].split(",");
                                float distance = sgen.getdistancegeo(sgen.latitudemain, sgen.longitudemain,Double.parseDouble(latlang[0]),Double.parseDouble(latlang[1]));

                                if(distance <= sgen.shopradius){ //10 km radius
                                    operatingSystems3.add(new RecyclerViewItem(null,
                                            "" + fed.get(i).getcol2().split("!~!~!")[0],
                                            "" + fed.get(i).getcol1(),
                                            "" + fed.get(i).getcol3(),
                                            "" + fed.get(i).getcol4(),
                                            "",
                                            ""+fed.get(i).getcol2().split("!~!~!")[1],
                                            "" + fed.get(i).getcol5(),
                                            true
                                    ));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        gridViewAdapter3.notifyDataSetChanged();
                        isLoading1 = false;
                    }
                });
            }
        }, 2000);
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



    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void  setReceiver(){
        myReceiverpublic = new MyReceiverpublic();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SHOPFILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(ShopViewActivity.this).registerReceiver(myReceiverpublic, intentFilter);
    }

    private  class MyReceiverpublic extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message2 = intent.getStringExtra("broadcastMessage");
            if(message2 == null){
                message2 = "";
            }
            String[] messagess = message2.split("<#>");
            if(messagess[0].equals("UpdateLayout")){


            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}