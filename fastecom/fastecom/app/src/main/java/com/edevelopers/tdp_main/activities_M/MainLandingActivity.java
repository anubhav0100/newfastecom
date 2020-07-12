package com.edevelopers.tdp_main.activities_M;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.edevelopers.tdp_main.Geo_Fencing.MapActivity;
import com.edevelopers.tdp_main.Services.CartService;
import com.edevelopers.tdp_main.Services.GPSTracker;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.fragments.CartFragment;
import com.edevelopers.tdp_main.models.Team;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.fragments.Account_Frag;
import com.edevelopers.tdp_main.fragments.Home_Frag;
import com.edevelopers.tdp_main.fragments.Search_Frag;
import com.edevelopers.tdp_main.sgen;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.edevelopers.tdp_main.sgen.senderpage;

public class MainLandingActivity extends AppCompatActivity {

    private LatLng mCenterLatLong;
    SharedPreferences sharedpreferences;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing);
        StrictMode.setThreadPolicy(sgen.policy); //EQUAL OR ABOVE ANDROID 90
        BottomNavigationView navigation = findViewById(R.id.navigation);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sgen.Context = getApplicationContext();
        try{
            sgen.create_tables(sgen.Context);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(sgen.LOGIN_STATUS.equals("")){
            try{
                sharedpreferences = getApplicationContext().getSharedPreferences(sgen.mypreferencegroup,Context.MODE_PRIVATE);
                String LoginStatus = sharedpreferences.getString(sgen.sp_LOGIN_STATUS, "");

                if(!LoginStatus.equals("Failed")){
                    startActivity(new Intent(this,SigninActivity.class));
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        getlocation();

        if(sgen.cartload.equals("load")&& !sgen.Login_Id.equals("")){
            handlecartdetails();
        }




        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Fragment fragment = new Home_Frag();
                        sgen.senderpage = "Home_Frag";
                        getSupportActionBar().setTitle("Home");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "MainLandingactivity").addToBackStack(senderpage).commit();
                    }
                }, 10);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.HomeP:
                        sgen.senderpage = "Home_Frag";
                        getSupportActionBar().setTitle("Home");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        Fragment fragment = new Home_Frag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment, senderpage);
                        fragmentTransaction.addToBackStack(senderpage);
                        fragmentTransaction.commit();

//                        startActivity(new Intent(MainActivity.this,MainActivity.class));
//                        overridePendingTransition(R.anim.to_right, R.anim.to_left);
                        return true;
                    case R.id.SearchP:
                        sgen.senderpage = "Search_Frag";
                        getSupportActionBar().setTitle("Search");
                        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon*/
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        Fragment fragment1 = new Search_Frag();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.content_frame, fragment1, senderpage);
                        fragmentTransaction1.addToBackStack(senderpage);
                        fragmentTransaction1.commit();

                        return true;
                    case R.id.ProfileP:
                        sgen.senderpage = "Account_Frag";
                        getSupportActionBar().setTitle("Profile");
                        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon*/
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        Fragment fragment2 = new Account_Frag();
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.content_frame, fragment2, senderpage);
                        fragmentTransaction2.addToBackStack(senderpage);
                        fragmentTransaction2.commit();
                        return true;

                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menus, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
                int seletedItemId1 = bottomNavigationView.getSelectedItemId();
                int seletedItemId = 0;
                backfragmentfunction(seletedItemId,seletedItemId1);
                if(sgen.backview == "Home_Frag"){
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
               // Toast.makeText(this, "click..!!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainLandingActivity.this, ExpandableList.class).putExtra("from", "MainLandingActivity"));

                return true;

            case R.id.CartP:
                startActivity(new Intent(MainLandingActivity.this,CartActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }




    @Override
    public void onBackPressed() {
       super.onBackPressed();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        int seletedItemId1 = bottomNavigationView.getSelectedItemId();
        int seletedItemId = 0;
        backfragmentfunction(seletedItemId,seletedItemId1);

        if(sgen.backview == "Home_Frag"){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    private void backfragmentfunction(int seletedItemId, int seletedItemId1){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            String Backname = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();

            if(Backname.equals("Search_Frag")){
                seletedItemId = R.id.SearchP;
            }
            if(Backname.equals("Account_Frag")){
                seletedItemId = R.id.ProfileP;
            }
            if(Backname.equals("Home_Frag")){
                seletedItemId = R.id.HomeP;
            }
            if(Backname.equals("CartFragment")){
                seletedItemId = R.id.CartP;
            }
            getSupportFragmentManager().popBackStack();
        }
        if (R.id.HomeP != seletedItemId1) {
            setHomeItem(MainLandingActivity.this,seletedItemId);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity,int id) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(id);
    }

    private void handlecartdetails(){
        String Query = "select CART_ID as col1,CONCAT(S_ID ,'!~!~!', PC_ID ,'!~!~!', QUANTITY) as col2, C_ID as col3, EDIT_DATE as col4, CREATED_DATE as col5 From CART where C_ID = '1';";
        VolleyExecute.volleydynamicgetfun(getApplicationContext(), "-", Query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                for (int i = 0; i < teams.size(); i++){
                    String Query = "INSERT INTO CART(CART_ID," +
                            "SHOP_ID,PRODUCT_ID," +
                            "QUANTITY,C_ID,DATE1,DATE2,SYNC)"+
                            "VALUES('"+teams.get(i).getcol1()+"'," +
                            "'" + teams.get(i).getcol2().split("!~!~!")[0] + "','" + teams.get(i).getcol2().split("!~!~!")[1] + "'," +
                            "'"+teams.get(i).getcol2().split("!~!~!")[2]+"','" + teams.get(i).getcol3() + "','"+teams.get(i).getcol4()+"','"+teams.get(i).getcol5()+"'," +
                            "'Y'"+
                            ")";
                    try {
                        sgen.exc_sqlite(getApplicationContext(),Query);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                sgen.cartload = "load";
            }
        });
    }

    private void getlocation(){
        try {
            GPSTracker gps = new GPSTracker(this);

            Location mLocation = new Location("");
            mLocation.setLatitude(gps.getLatitude());
            mLocation.setLongitude(gps.getLongitude());

            sgen.latitudemain = gps.getLatitude();
            sgen.longitudemain = gps.getLongitude();

            //tartIntentService(mLocation);

            Geocoder geocoder = new Geocoder(MainLandingActivity.this, Locale.getDefault());

            /**********************   get street and address location*************************************/

            List<Address> addresses;
            addresses = geocoder.getFromLocation(
                    gps.getLatitude(), gps.getLongitude(), 1);

            String Country="";
            String State="";
            String District="";
            String CITY="";
            String PstalCode="";
            String Address = "";
            String Address1 = "";

            if (addresses.size() > 0) {
                Address = (addresses.get(0).getAddressLine(0));
                Address1 = (addresses.get(0).getAddressLine(1));
                Country = (addresses.get(0).getCountryName());
                State = (addresses.get(0).getAdminArea());
                District = (addresses.get(0).getSubAdminArea());
                CITY = (addresses.get(0).getLocality());
                PstalCode = (addresses.get(0).getPostalCode());
            }

            sgen.CUR_COUNTRY =Country;
            sgen.CUR_STATE =State;
            sgen.CUR_DISTRICT =District;
            sgen.CUR_CITY =CITY;
            sgen.CUR_PstalCode =PstalCode;

            sgen.SGLOC = District;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
