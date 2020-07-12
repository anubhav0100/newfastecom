package com.edevelopers.tdp_main.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout7;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout9;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.models.Productcat;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView gridView;
    private GridViewAdapterlayout9 gridViewAdapter;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems;
    Animation anim;
    private static Dialog dialog;

    public Search_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Frag newInstance(String param1, String param2) {
        Search_Frag fragment = new Search_Frag();
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
        View v = inflater.inflate(R.layout.fragment_search_, container, false);

        gridView = (RecyclerView) v.findViewById(R.id.grid);

        dialog = new Dialog(getContext(), R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);

        dialog.show();
        setdata1();
        gridView.setHasFixedSize(true);
        anim = AnimationUtils.loadAnimation(getActivity(), R.animator.cycle);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        sgen.backview = "Search_Frag";
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialog.hide();
    }


    private void setdata1(){
        String query = "SELECT PC.PC_ID, PC.PC_NAME, PC.PC_LEVEL, PC.PC_LEVEL_NAME1, PC.PC_LEVEL_NAME2, PC.PC_LEVEL_NAME3, PC.PC_ORDER, PC.PC_DEFAULT,  PC.PC_TYPE, PC.CREATED_DATE,   " +
                "CASE WHEN F.FILE_lOC IS NULL THEN CONCAT('0') ELSE CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) END AS PC_UID from PRODUCT_CATEGORY_LEVEL PC  " +
                "LEFT JOIN FILE_TAB F ON F.F_TYPE = 'PCL' AND F.PU_ID = PC.IMG_ID AND F.LINK_ID = PC.PC_ID  " +
                "where PC.PC_LEVEL = '0' AND PC.PC_TYPE = 'PCL';";

        VolleyExecute.volleydynamicgetfunproductcat(getContext(), "-", query, "-", "-", "-", new VolleyExecute.VolleyCallbackproductcat() {
            @Override
            public void onSuccess(ArrayList<Productcat> productcats) {

                operatingSystems = new ArrayList<RecyclerViewItem>();
                for (int i = 0; i < productcats.size(); i++) {
                    try {
                        Bitmap icon = null;
                        operatingSystems.add(new RecyclerViewItem(icon,
                                ""+productcats.get(i).getPC_NAME(),
                                ""+productcats.get(i).getPC_ID(),
                                ""+productcats.get(i).getPC_LEVEL_NAME1(),
                                ""+productcats.get(i).getPC_UID(),
                                ""+productcats.get(i).getPC_LEVEL_NAME3(),
                                "",
                                ""+productcats.get(i).getPC_UID(),
                                false
                        ));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                gridView.setLayoutManager(layoutManager);
                gridViewAdapter = new GridViewAdapterlayout9(getActivity(), operatingSystems, anim);
                gridView.setAdapter(gridViewAdapter);
                dialog.hide();
            }
        });

    }
}
