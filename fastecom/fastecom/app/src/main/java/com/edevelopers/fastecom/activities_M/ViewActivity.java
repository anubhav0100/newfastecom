package com.edevelopers.fastecom.activities_M;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout11;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout4;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout6;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView listView;
    private RecyclerView gridView;
    private GridViewAdapterlayout11 gridViewAdapter;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems;
    Animation anim;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (RecyclerView) findViewById(R.id.grid4);

        gridView.setHasFixedSize(true);
        anim = AnimationUtils.loadAnimation(ViewActivity.this, R.animator.cycle);

        setdata1();
    }

    private void setdata1(){
        operatingSystems = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(ViewActivity.this, "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(ViewActivity.this.getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString(),"Rs. 299","sub meal"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(ViewActivity.this, 1, GridLayoutManager.VERTICAL, false);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout11(ViewActivity.this,operatingSystems,anim);
        gridView.setAdapter(gridViewAdapter);
    }

}
