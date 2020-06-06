package com.edevelopers.fastecom.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout4;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout8;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout9;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.models.Team;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView gridView,gridView1;
    private GridViewAdapterlayout9 gridViewAdapter;
    private GridViewAdapterlayout8 gridViewAdapter1;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems,operatingSystems1;
    Animation anim;
    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        gridView = (RecyclerView) v.findViewById(R.id.grid);
        gridView1 = (RecyclerView) v.findViewById(R.id.grid1);
        anim= AnimationUtils.loadAnimation(getContext(), R.animator.cycle);
        gridView.setHasFixedSize(true);
        setdata1();
        setdata2();

        return v;
    }

    private void setdata1(){
        operatingSystems = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout9(getActivity(),operatingSystems,anim);
        gridView.setAdapter(gridViewAdapter);
    }

    private void setdata2(){
        operatingSystems1 = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems1.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        gridView1.setLayoutManager(layoutManager);
        gridViewAdapter1 = new GridViewAdapterlayout8(getActivity(),operatingSystems1,anim);
        gridView1.setAdapter(gridViewAdapter1);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        sgen.backview = "Cart_Frag";
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private String saveToCart(){
        String status = "";
        try{
            String Query = "INSERT INTO Cart(P_ID,C_ID,DATE) VALUES('','','')";
            sgen.exc_sqlite(getContext(),Query);
            status = "Success";
        }catch (Exception e){
            status = "Failed";
            e.printStackTrace();
        }
        return status;
    }
}