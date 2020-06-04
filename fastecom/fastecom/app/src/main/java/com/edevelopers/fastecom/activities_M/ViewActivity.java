package com.edevelopers.fastecom.activities_M;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private TextView head,body;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        head = (TextView) findViewById(R.id.head);
        body = (TextView) findViewById(R.id.body);
        imageView = (ImageView) findViewById(R.id.imagehead);



        ArrayList<Team> mfed = getdata();

        for(int i = 0;i< mfed.size();i++){
            head.setText(mfed.get(i).getcol1());
            imageView.setImageBitmap(sgen.Base64ToImage(mfed.get(i).getcol2()));
            body.setText(mfed.get(i).getcol5());
        }
    }

    private ArrayList<Team> getdata(){
        ArrayList<Team> fed = sgen.getdata_fromsql(ViewActivity.this, "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,DESCRIPTION AS col5 from Head where ID = '"+sgen.viewId+"';");
        return fed;
    }
}
