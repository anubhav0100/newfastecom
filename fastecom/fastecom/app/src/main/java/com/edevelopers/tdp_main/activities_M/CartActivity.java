package com.edevelopers.tdp_main.activities_M;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.CartGridViewAdapterlayout;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout9;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.edevelopers.tdp_main.showmap;
import com.google.android.gms.maps.model.LatLng;

import java.security.PrivilegedAction;
import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CartActivity extends AppCompatActivity {
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

    private TextView paybtn,AddressChange,DeliveryAddress;
    private RecyclerView gridView;
    private CartGridViewAdapterlayout gridViewAdapter1;
    private String latlang ="";
    private String getFullAddress1 = "",getFullAddress2 = "",returnaddress = "";
    public static ArrayList<RecyclerViewItem> operatingSystems1;
    public static  ArrayList<RecyclerViewItem> operatingSystems2;
    private  ArrayList<RecyclerViewItem> ftokensystem;
    Animation anim;

    private String[] ftokenid = {};

    public static TextView itotal;
    public static TextView delivery;
    public static TextView gtotal;
    public static TextView gtotal1;

    private Dialog dialog;

    private String mainQuery = "";
    private String[] items = {},itemid = {};
    private String AddressSelect = "";
    private String AddressSelectID = "";
    private String Addressnewadd = "";

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

        setContentView(R.layout.activity_cart);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        paybtn = (TextView) findViewById(R.id.paybtn);
        gridView = (RecyclerView) findViewById(R.id.grid);
        anim= AnimationUtils.loadAnimation(CartActivity.this, R.animator.cycle);
        gridView.setHasFixedSize(true);

        dialog = new Dialog(CartActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);

        AddressChange = findViewById(R.id.AddressChange);
        DeliveryAddress = findViewById(R.id.DeliveryAddress);

        sgen.activty_logincall = "CartActivity";
        sgen.activty_logincallnew = "CartActivity";

        itotal = (TextView) findViewById(R.id.itemTotal);
        gtotal = (TextView) findViewById(R.id.grandtotal);
        gtotal1 = (TextView) findViewById(R.id.grandtotal1);
        delivery = (TextView) findViewById(R.id.deliverycharge);


        setdata2();

        DeliveryAddress.setText("Select Address");
        AddressChange.setText("Select");

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sgen.LOGIN_STATUS.toUpperCase().equals("SUCCESS")){
                    if(operatingSystems1.size()>0){
                        if(DeliveryAddress.getText().toString().equals("Select Address")||DeliveryAddress.getText().toString().equals("")){
                            Toast.makeText(CartActivity.this,"Please Select Address",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            PlaceOrder();
                        }
                    }
                    else{
                        Toast.makeText(CartActivity.this,"Cart Is Empty",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    startActivity(new Intent(CartActivity.this,SigninActivity.class));
                }
            }
        });

        AddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sgen.LOGIN_STATUS.toUpperCase().equals("SUCCESS")){
                    if(sgen.Addressrecord.size() == 0){
                        startActivityForResult(new Intent(CartActivity.this, showmap.class),1);
                    }
                    else{
                        functionAlertsection1(100);
                    }
                }
                else{
                    startActivity(new Intent(CartActivity.this,SigninActivity.class));
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    private void functionAlertsection1(int checkedItem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("Select Address");

        String selectaddress = "";
        String selectaddressid = "";
        for(int i = 0;i<sgen.Addressrecord.size();i++){
            selectaddress += sgen.Addressrecord.get(i).getName()+"<#>";
            selectaddressid += sgen.Addressrecord.get(i).getModule3()+"<#>"+sgen.Addressrecord.get(i).getModule1()+"<#>"+sgen.Addressrecord.get(i).getModule2()+"!~!~!";
        }
        selectaddress += "Add New Address";
        items = selectaddress.split("<#>");
        itemid = selectaddressid.split("!~!~!");
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!items[which].toString().equals("Add New Address")){
                    DeliveryAddress.setText(items[which].toString());
                    AddressSelect = items[which].toString();
                    AddressSelectID = itemid[which].toString();
                }
                else{
                    dialog.dismiss();
                    startActivityForResult(new Intent(CartActivity.this, showmap.class),1);
                }
            }
        });
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String addre = data.getStringExtra("result");
            returnaddress = addre.split("<##>")[0];
            sgen.CUR_Address = addre.split("<##>")[0];
            latlang = addre.split("<##>")[1];
            functionAlertsection2();
        }
    }

    private void functionAlertsection2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(sgen.CUR_CITY);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

// Set up the input

        final EditText input_address1 = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input_address1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input_address1.setHint("Complete Address Line1");
        input_address1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40)});
        layout.addView(input_address1);

        final EditText input_address2 = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input_address2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input_address2.setHint("Complete Address Line2");
        input_address2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40)});
        layout.addView(input_address2);

        builder.setView(layout);

        String address1 = returnaddress.substring(0,40);
        String address2 = returnaddress.substring(40,returnaddress.length());

        input_address1.setText(address1);
        input_address2.setText(address2);


// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFullAddress1 = input_address1.getText().toString();
                getFullAddress2 = input_address2.getText().toString();
               // DeliveryAddress.setText(getFullAddress1 + getFullAddress2);
                String addresses = getFullAddress1 + getFullAddress2;
                Boolean b = true;
                for(int i = 0;i<sgen.Addressrecord.size();i++){
                    if(sgen.Addressrecord.get(0).getName().equals(addresses)){
                        b = false;
                        break;
                    }
                    else{
                        b = true;
                    }
                }
                if(b){
                    functionAddress();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updatecart(){
        String Query = "UPDATE CART SET C_ID = '"+sgen.Login_Id+"' WHERE C_ID = '"+sgen.getandroidID()+"';";
        VolleyExecute.volleydynamicsavefun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                sgen.cartupdate = "Saved";
                setdata2();
            }
        });
    }


    private void setdata2(){
        String LoginID = "";
        if(sgen.Login_Id.equals("")){
            LoginID = sgen.getandroidID();
        }else{
            if(sgen.cartupdate.equals("")){
                updatecart();
            }
            LoginID = sgen.Login_Id;
            getshopidset();
        }
        String Query = "SELECT CONCAT(C.ID,'!~!~!',C.CART_ID,'!~!~!',C.S_ID) AS COL1," +
                " CONCAT(SM.S_NAME,'!~!~!',C.PC_ID,'!~!~!',C.QUANTITY,'!~!~!',P.PC_NAME) AS COL2, " +
                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) AS COL3,CONCAT('Rs.',truncate((SPC.PC_PRICE * C.QUANTITY),2)) AS COL4, " +
                "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5  " +
                "FROM CART C  " +
                "INNER JOIN SHOP_MASTER SM ON SM.S_ID = C.S_ID  " +
                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = C.S_ID AND SPC.PC_ID = C.PC_ID  " +
                "INNER JOIN PRODUCT_MAIN P ON P.PC_ID = C.PC_ID AND P.PC_TYPE = 'PRD' " +
                "INNER JOIN FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F.F_TYPE = 'PRD' " +
                "WHERE C.C_ID = '"+LoginID+"' order by C.S_ID;";
        VolleyExecute.volleydynamicgetfun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
              if(teams.size()>0){
                  ArrayList<Team> fed = teams;
                  operatingSystems1 = new ArrayList<RecyclerViewItem>();
                  for (int i = 0; i < fed.size(); i++) {
                      try {
                          operatingSystems1.add(new RecyclerViewItem(null,
                                  "" +fed.get(i).getcol2().split("!~!~!")[0]+"<#>" + fed.get(i).getcol2().split("!~!~!")[3],
                                  "" +  fed.get(i).getcol1().split("!~!~!")[0],
                                  "" + fed.get(i).getcol2().split("!~!~!")[2],
                                  "" + fed.get(i).getcol3(),
                                  ""+String.valueOf(Integer.parseInt(fed.get(i).getcol1().split("!~!~!")[1])),
                                  ""+fed.get(i).getcol4(),
                                  "" + fed.get(i).getcol5(),
                                  true
                          ));
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }

                  try{
                      CartActivity.operatingSystems2 = operatingSystems1;
                      GridLayoutManager layoutManager = new GridLayoutManager(CartActivity.this, 1, GridLayoutManager.VERTICAL, false);
                      gridView.setLayoutManager(layoutManager);
                      gridViewAdapter1 = new CartGridViewAdapterlayout(CartActivity.this,operatingSystems1,anim);
                      gridView.setAdapter(gridViewAdapter1);
                      dialog.hide();
                      setdetailsfunction();
                  }catch (Exception e) {
                      e.printStackTrace();
                  }
              }
            }
        });
    }

    public static void setdetailsfunction(){
        float i_total = 0;
        float g_total = 0;
        float _delivery = 0;
        for(int i = 0;i<operatingSystems1.size();i++){
           float ito = Float.parseFloat(operatingSystems1.get(i).getModule4().split("Rs.")[1]);
           i_total += ito;
        }
        g_total = i_total + _delivery;
        itotal.setText("Rs. "+String.valueOf(i_total));
        gtotal.setText("Rs. "+String.valueOf(g_total));
        gtotal1.setText("Rs. "+String.valueOf(g_total));
        delivery.setText("Rs. "+String.valueOf(_delivery));
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

    private void functionAddress(){
        dialog.show();
        String LoginID = "";
        if(sgen.Login_Id.equals("")){
            LoginID = sgen.getandroidID();
        }else{
            LoginID = sgen.Login_Id;
        }

        String Query = "CALL AUTO_C_ADDRESS('"+LoginID+"','"+getFullAddress1+"','"+getFullAddress2+"','0','0','0',STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'),'"+latlang+"');";
        VolleyExecute.volleydynamicsavefun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                Addressnewadd = "newAdd";
                dbAddress();
                Toast.makeText(CartActivity.this,"Address status: "+teams.get(0).getcol1(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dbAddress(){
        String Query = "SELECT ID AS COL1, A_ID AS COL2, C_ID AS COL3,CONCAT(C_ADD1,C_ADD2) AS COL4, LOCATION AS COL5 FROM C_ADDRESS WHERE C_ID = '"+sgen.Login_Id+"' ORDER BY ID;";
        VolleyExecute.volleydynamicgetfun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.size()>0){
                    sgen.Addressrecord = new ArrayList<>();
                    for(int i = 0; i < teams.size(); i++){
                        sgen.Addressrecord.add(new RecyclerViewItem(
                                null,
                                ""+teams.get(i).getcol4(),
                                ""+teams.get(i).getcol1(),
                                ""+teams.get(i).getcol2(),
                                ""+teams.get(i).getcol3(),
                                ""+teams.get(i).getcol5(),
                                ""+teams.get(i).getcol5(),
                                false
                        ));
                    }

                    dialog.hide();
                    if(Addressnewadd.equals("newAdd")){
                     functionAlertsection1(0);
                    }
                }
            }
        });
    }

    private void PlaceOrder(){
        dialog.show();
        String Query = "SELECT CONCAT(C.ID,'!~!~!',C.CART_ID,'!~!~!',C.S_ID) AS COL1," +
                " CONCAT(SM.S_NAME,'!~!~!',C.PC_ID,'!~!~!',C.QUANTITY,'!~!~!',P.PC_NAME) AS COL2, " +
                "CONCAT('Rs.',truncate(SPC.PC_PRICE,2)) AS COL3,truncate((SPC.PC_PRICE * C.QUANTITY),2) AS COL4, " +
                "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5  " +
                "FROM CART C  " +
                "INNER JOIN SHOP_MASTER SM ON SM.S_ID = C.S_ID  " +
                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = C.S_ID AND SPC.PC_ID = C.PC_ID  " +
                "INNER JOIN PRODUCT_MAIN P ON P.PC_ID = C.PC_ID AND P.PC_TYPE = 'PRD' " +
                "INNER JOIN FILE_TAB F ON F.PU_ID = P.PC_IMG_ID AND F.LINK_ID = P.PC_ID AND F.F_TYPE = 'PRD' " +
                "WHERE C.C_ID = '"+sgen.Login_Id+"' order by C.S_ID;";
        VolleyExecute.volleydynamicgetfun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.size()>0){
                    ArrayList<Team> fed = teams;
                    for (int i = 0; i < fed.size(); i++) {
                        mainQuery += "CALL AUTO_ORDER_TABLE('0'," +
                                "'"+fed.get(i).getcol2().split("!~!~!")[1]+"'," +
                                "'"+sgen.Login_Id+"'," +
                                "'"+fed.get(i).getcol1().split("!~!~!")[2]+"'," +
                                "'P','C'," +
                                "'"+fed.get(i).getcol2().split("!~!~!")[2]+"'," +
                                "'"+fed.get(i).getcol4()+"'," +
                                "'"+AddressSelectID.split("<#>")[1]+"'," +
                                "STR_TO_DATE('" + sgen.gettodaydate_timemysql() + "','%m/%d/%Y %r'));";
                    }

                    VolleyExecute.volleydynamicsavefun(CartActivity.this, "-", mainQuery, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> teams) {
                            if(teams.get(0).getcol1().equals("Saved")){
                                String Query = "DELETE FROM CART WHERE C_ID = '"+sgen.Login_Id+"';";
                                VolleyExecute.volleydynamicsavefun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Team> teams) {
                                        sendAlert();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        /*mainQuery = "CALL AUTO_ORDER_TABLE('0','PC_ID','C_ID','S_ID','STATUS','PAY_STATUS','QUANTITY','PAY_AMT','DELIVERY_ADD','CREATED_DATE');";*/
    }

    private void sendAlert(){
        for(int i = 0; i < ftokensystem.size();i++){
            VolleyExecute.volleydynamicsendalertTDPS(CartActivity.this, ftokensystem.get(i).getName(), "Order Recieved", "You Have New Order To Be Delivered", new VolleyExecute.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Team> teams) {
                }
            });
        }
        dialog.hide();
        Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
        intent.putExtra("Address",DeliveryAddress.getText().toString());
        intent.putExtra("item",itotal.getText().toString());
        intent.putExtra("delivery",delivery.getText().toString());
        intent.putExtra("total",gtotal.getText().toString());
        startActivity(intent);
        finish();
    }

    private void getshopidset(){
        String Query = "select DISTINCT S_ID AS COL1,'-' AS COL2,'-' AS COL3,'-' AS COL4,'-' AS COL5  From CART where C_ID = '"+sgen.Login_Id+"';";
        VolleyExecute.volleydynamicgetfun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                if(teams.size()>0){
                    String setshop = "";
                    for(int i = 0; i<teams.size();i++){
                        setshop += "'"+ teams.get(0).getcol1().toString() + "',";
                    }
                    setshop = setshop.substring(0,setshop.length()-1);
                    String Query = "SELECT M.FTOKEN AS COL1, M.COMP_ID AS COL2,M.C_UNIT_ID AS COL3,LOCATION AS COL4,'-' AS COL5 FROM M_USER M WHERE M.TYPE = 'SHU' AND M.M_USERN IN ("+setshop+");";
                    VolleyExecute.volleydynamicgetfun(CartActivity.this, "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> teams) {
                            ftokensystem = new ArrayList<>();
                            for (int i = 0; i < teams.size(); i++){
                                ftokensystem.add(new RecyclerViewItem(
                                        null,
                                        ""+teams.get(0).getcol1(),
                                        ""+teams.get(0).getcol2(),
                                        ""+teams.get(0).getcol3(),
                                        ""+teams.get(0).getcol4(),
                                        "",
                                        "",
                                        false
                                ));
                            }
                        }
                    });
                }
            }
        });
    }
}