package com.edevelopers.fastecom.activities_M;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.edevelopers.fastecom.fragments.CartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.fragments.Account_Frag;
import com.edevelopers.fastecom.fragments.Home_Frag;
import com.edevelopers.fastecom.fragments.Search_Frag;
import com.edevelopers.fastecom.sgen;

import static com.edevelopers.fastecom.sgen.senderpage;

public class MainLandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sgen.Context = getApplicationContext();
        try{
            sgen.drop_tables(sgen.Context);
            sgen.create_tables(sgen.Context);
            sgen.savedata(sgen.Context);
        }catch(Exception e){
            e.printStackTrace();
        }

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Fragment fragment = new Home_Frag();
                        sgen.senderpage = "Home_Frag";
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
                        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon*/
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        Fragment fragment1 = new Search_Frag();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.content_frame, fragment1, senderpage);
                        fragmentTransaction1.addToBackStack(senderpage);
                        fragmentTransaction1.commit();

                        return true;
                    case R.id.CartP:
                        sgen.senderpage = "CartFragment";
                        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon*/
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        Fragment fragment3 = new CartFragment();
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.replace(R.id.content_frame, fragment3, senderpage);
                        fragmentTransaction3.addToBackStack(senderpage);
                        fragmentTransaction3.commit();

                        return true;
                    case R.id.ProfileP:
                        sgen.senderpage = "Account_Frag";
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

    /*@Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menus, menu);

        return super.onCreateOptionsMenu(menu);
    }
*/

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
       /* if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
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
        }*/
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

}
