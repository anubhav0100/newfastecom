package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.CartGridViewAdapterlayout;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout12;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout13;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout18;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OrderActivity extends AppCompatActivity {
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

    private GridViewAdapterlayout18 gridViewAdapter1;
    private ArrayList<RecyclerViewItem> operatingSystems,ordermainsystem;
    private RecyclerView gridview;
    Animation anim;
    private Dialog dialog;

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

        setContentView(R.layout.activity_order);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        gridview = (RecyclerView) findViewById(R.id.grid);
        anim= AnimationUtils.loadAnimation(OrderActivity.this, R.animator.cycle);
        gridview.setHasFixedSize(true);


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

        dialog = new Dialog(OrderActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);
        ordermainset();



        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    private void ordermainset(){
        dialog.show();
        String Query = "select DISTINCT (SELECT SUM(T.PAY_AMT) FROM ORDER_TABLE T WHERE T.S_ID = O.S_ID AND T.C_ID = O.C_ID AND T.STATUS = O.STATUS AND T.DELIVERY_ADD = O.DELIVERY_ADD) as col1," +
                "CONCAT(A.C_ADD1,A.C_ADD2) AS COL2,CONCAT(C.CF_NAME,' ',C.CL_NAME) AS COL3,CONCAT(C.FTOKEN,'!~!~!',O.S_ID,'!~!~!'," +
                "CASE WHEN O.STATUS = 'P' THEN 'PENDING'" +
                "WHEN O.STATUS = 'A' THEN 'ACCEPTED'" +
                "WHEN O.STATUS = 'O' THEN 'OUT FOR DELIVERY'" +
                "ELSE 'WAITING FOR RESPONCE'" +
                "END " +
                ",'!~!~!',O.DELIVERY_ADD) AS COL4,A.LOCATION AS COL5 from ORDER_TABLE O  " +
                "INNER JOIN C_ADDRESS A ON A.A_ID = O.DELIVERY_ADD AND  A.C_ID = O.C_ID " +
                "INNER JOIN C_USER C ON C.C_ID = O.C_ID AND C.TYPE = 'CUS' " +
                "WHERE O.C_ID = '"+sgen.Login_Id+"' AND O.STATUS IN ('P','A','W','O') order by O.ORDER_ID ASC;";

        VolleyExecute.volleydynamicgetfun(OrderActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.size()>0){
                    ordermainsystem = new ArrayList<RecyclerViewItem>();
                    for (int i = 0;i<teams.size();i++){
                        ordermainsystem.add(new RecyclerViewItem(null,
                                "" +teams.get(i).getcol3(),
                                "" +  teams.get(i).getcol4().split("!~!~!")[1],
                                "" + teams.get(i).getcol4().split("!~!~!")[0],
                                "" + teams.get(i).getcol1(),
                                ""+ teams.get(i).getcol4().split("!~!~!")[2],
                                ""+teams.get(i).getcol2(),
                                "" + teams.get(i).getcol5()+"<#>"+ teams.get(i).getcol4().split("!~!~!")[3],
                                true
                        ));
                    }
                }
                GridLayoutManager layoutManager = new GridLayoutManager(OrderActivity.this, 1, GridLayoutManager.VERTICAL, false);
                gridview.setLayoutManager(layoutManager);
                gridViewAdapter1 = new GridViewAdapterlayout18(OrderActivity.this,ordermainsystem,anim);
                gridview.setAdapter(gridViewAdapter1);
                dialog.hide();
            }
        });
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