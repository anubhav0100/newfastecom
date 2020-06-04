package com.edevelopers.fastecom.activities_M;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout6;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView listView;
    private RecyclerView gridView4;
    private GridViewAdapterlayout6 gridViewAdapter;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        listView = (RecyclerView) findViewById(R.id.list);
        gridView4 = (RecyclerView) findViewById(R.id.grid4);

        sgen.Context = getApplicationContext();

        try{
            sgen.create_tables(sgen.Context);
            sgen.savedata(sgen.Context);
        }catch(Exception e){
            e.printStackTrace();
        }

        listView.setHasFixedSize(true);
        gridView4.setHasFixedSize(true);

        setDummyData();
        @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(ViewActivity.this, R.animator.cycle);

        GridLayoutManager layoutManager1 = new GridLayoutManager(ViewActivity.this, 1, GridLayoutManager.VERTICAL, false);
        gridView4.setLayoutManager(layoutManager1);
        gridViewAdapter = new GridViewAdapterlayout6(ViewActivity.this,operatingSystems,anim);
        gridView4.setAdapter(gridViewAdapter);

    }

    private void setDummyData() {
        ArrayList<Team> fed = sgen.getdata_fromsql(this, "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
        if (fed.size() < 1) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();
        }

        operatingSystems = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {
            if(i>=8){
                break;
            }
            try {
                operatingSystems.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
               }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
