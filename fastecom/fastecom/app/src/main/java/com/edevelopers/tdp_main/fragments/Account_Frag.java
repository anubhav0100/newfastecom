package com.edevelopers.tdp_main.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout10;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView gridView;
    private GridViewAdapterlayout10 gridViewAdapter;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems;
    Animation anim;

    public Account_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Account_Frag newInstance(String param1, String param2) {
        Account_Frag fragment = new Account_Frag();
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

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_, container, false);

        /*******************************************************************************/
        gridView = (RecyclerView) v.findViewById(R.id.grid);
        anim= AnimationUtils.loadAnimation(getContext(), R.animator.cycle);
        gridView.setHasFixedSize(true);

        setdata1();
        /*******************************************************************************/
        return v;
    }

    private void setdata1(){
        operatingSystems = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString(),"Rs. 299","06/06/2020"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout10(getActivity(),operatingSystems,anim);
        gridView.setAdapter(gridViewAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        sgen.backview = "Account_Frag";
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
