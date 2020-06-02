package com.edevelopers.fastecom.activities_M;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sgen.Context = getApplicationContext();
        try{
            sgen.create_tables(sgen.Context);
            sgen.savedata(sgen.Context);
        }catch(Exception e){
            e.printStackTrace();
        }

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Fragment fragment = new Home_Frag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "MainLandingactivity").addToBackStack("null").commit();
                    }
                }, 10);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.HomeP:
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
               // Toast.makeText(this, "click..!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainLandingActivity.this, ExpandableList.class).putExtra("from", "MainLandingActivity"));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(sgen.backview == "Home_Frag"){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}
