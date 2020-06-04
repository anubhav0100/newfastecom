package com.edevelopers.fastecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.edevelopers.fastecom.adapter.GridViewAdapterlayout4;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout5;
import com.edevelopers.fastecom.adapter.ListViewAdapterlayout1;
import com.edevelopers.fastecom.adapter.MyCustomPagerAdaptor;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.models.Team;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    int images[] = {R.drawable.ic_heart_black_24dp, R.drawable.img};
    private MyCustomPagerAdaptor myCustomPagerAdapter;
    private RecyclerView listView;
    private RecyclerView gridView;
    private RecyclerView gridView1;
    private ListViewAdapterlayout1 listViewAdapter;
    private GridViewAdapterlayout4 gridViewAdapter;
    private GridViewAdapterlayout5 gridViewAdapter1;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems,operatingSystems1;
    boolean isLoading = false;
    boolean isLoading1 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.list);
        gridView = (RecyclerView) findViewById(R.id.grid);
        gridView1 = (RecyclerView) findViewById(R.id.grid1);


        /***********************Vectors Add*********************************************************/
        ArrayList<Integer> dr = new ArrayList<>();
        dr.add(R.drawable.ic_close_black_24dp);
        dr.add(R.drawable.ic_close_black_24dp);

        /*****************************************************************************/

        sgen.Context = getApplicationContext();

        try{
            sgen.create_tables(sgen.Context);
            sgen.savedata(sgen.Context);
        }catch(Exception e){
            e.printStackTrace();
        }

        listView.setHasFixedSize(true);
        gridView.setHasFixedSize(true);
        gridView1.setHasFixedSize(true);
        setDummyData();
        @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.animator.cycle);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout4(MainActivity.this,operatingSystems1,anim);
        gridView.setAdapter(gridViewAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        gridView1.setLayoutManager(layoutManager1);
        gridViewAdapter1 = new GridViewAdapterlayout5(MainActivity.this,operatingSystems,anim);
        gridView1.setAdapter(gridViewAdapter1);



        initScrollListener();

       // startActivity(new Intent(MainActivit,j8y.this, MainLandingActivity.class));
    }

    private void setDummyData() {
        ArrayList<Team> fed = sgen.getdata_fromsql(this, "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
        if (fed.size() < 1) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();
        }

        /*corporations = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {

            corporations.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()),fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));

        }*/

        operatingSystems = new ArrayList<RecyclerViewItem>();
        operatingSystems1 = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {
            if(i>=8){
                break;
            }
            try {
                    operatingSystems.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    operatingSystems1.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == operatingSystems1.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading1 = true;
                    }
                }
            }
        });
        gridView1.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == operatingSystems.size() - 1) {
                        //bottom of list!
                        loadMore1();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        operatingSystems1.add(null);
        gridViewAdapter.notifyItemInserted(operatingSystems1.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                operatingSystems1.remove(operatingSystems1.size() - 1);
                int scrollPosition = operatingSystems1.size();
                gridViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 8;

                ArrayList<Team> fed = sgen.getdata_fromsql(MainActivity.this, "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
                if (fed.size() < 1) {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                }

                int i = currentSize - 1;
                while (i < nextLimit) {
                    try {
                        operatingSystems1.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    i++;
                }

                gridViewAdapter.notifyDataSetChanged();
                isLoading1 = false;
            }
        }, 2000);
    }
    private void loadMore1() {
        operatingSystems.add(null);
        gridViewAdapter1.notifyItemInserted(operatingSystems.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                operatingSystems.remove(operatingSystems.size() - 1);
                int scrollPosition = operatingSystems.size();
                gridViewAdapter1.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 8;

                ArrayList<Team> fed = sgen.getdata_fromsql(MainActivity.this, "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
                if (fed.size() < 1) {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                }

                int i = currentSize - 1;
                while (i < nextLimit) {
                    try {
                        operatingSystems.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    i++;
                }

                gridViewAdapter1.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
}