package com.edevelopers.fastecom.Tabbedd_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout1;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout2;
import com.edevelopers.fastecom.adapter.ListViewAdapterlayout1;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekFixturesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFixturesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView listView;
    private RecyclerView gridView;
    private RecyclerView gridView1;
    private ListViewAdapterlayout1 listViewAdapter;
    private GridViewAdapterlayout1 gridViewAdapter;
    private GridViewAdapterlayout2 gridViewAdapter2;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems,operatingSystems1;

    public WeekFixturesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekFixturesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekFixturesFragment newInstance(String param1, String param2) {
        WeekFixturesFragment fragment = new WeekFixturesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_week_fixtures, container, false);
        listView = (RecyclerView) v.findViewById(R.id.list);
        gridView = (RecyclerView) v.findViewById(R.id.grid);
        gridView1 = (RecyclerView) v.findViewById(R.id.grid1);

        listView.setHasFixedSize(true);
        gridView.setHasFixedSize(true);
        gridView1.setHasFixedSize(true);
        setDummyData();
        @SuppressLint("ResourceType") Animation anim= AnimationUtils.loadAnimation(getContext(), R.animator.cycle);

        //set layout manager and adapter for "ListView"
       /* LinearLayoutManager horizontalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(horizontalManager);
        listViewAdapter = new ListViewAdapterlayout1(getActivity(),corporations);
        listView.setAdapter(listViewAdapter);*/

        //set layout manager and adapter for "GridView"
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout1(getActivity(),operatingSystems,anim);
        gridView.setAdapter(gridViewAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 1);
        gridView1.setLayoutManager(layoutManager1);
        gridViewAdapter2 = new GridViewAdapterlayout2(getActivity(),operatingSystems1,anim);
        gridView1.setAdapter(gridViewAdapter2);
        return v;
    }

    private void setDummyData() {
        ArrayList<Team> fed = sgen.getdata_fromsql(getContext(), "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
        if (fed.size() < 1) {
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
        }

        /*corporations = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {

            corporations.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()),fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));

        }*/
        operatingSystems = new ArrayList<RecyclerViewItem>();
        operatingSystems1 = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {

            try {
                if(fed.get(i).getcol4().trim().toString().equals("1")){
                    operatingSystems.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                }
                else {
                    operatingSystems1.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}