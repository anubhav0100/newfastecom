package com.edevelopers.fastecom.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.edevelopers.fastecom.MainActivity;
import com.edevelopers.fastecom.R;
import com.edevelopers.fastecom.Services.VolleyExecute;
import com.edevelopers.fastecom.Tabbedd_fragment.MonthFixturesFragment;
import com.edevelopers.fastecom.Tabbedd_fragment.TodaysFixturesFragment;
import com.edevelopers.fastecom.Tabbedd_fragment.WeekFixturesFragment;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout4;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout5;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout7;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout8;
import com.edevelopers.fastecom.adapter.GridViewAdapterlayout9;
import com.edevelopers.fastecom.adapter.ListViewAdapterlayout1;
import com.edevelopers.fastecom.adapter.MyCustomPagerAdaptor;
import com.edevelopers.fastecom.adapter.RecyclerViewItem;
import com.edevelopers.fastecom.classes.AnimatedGifImageView;
import com.edevelopers.fastecom.models.Team;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.material.tabs.TabLayout;
import com.edevelopers.fastecom.sgen;

import java.util.ArrayList;
import java.util.List;

import static com.edevelopers.fastecom.DbHelper.context;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    int images[] = {R.drawable.ic_heart_black_24dp, R.drawable.img};
    private MyCustomPagerAdaptor myCustomPagerAdapter;
    private RecyclerView listView;
    private RecyclerView gridView;
    private RecyclerView gridView1,gridView2,gridView3,gridView4;
    private ListViewAdapterlayout1 listViewAdapter;
    private GridViewAdapterlayout4 gridViewAdapter;
    private GridViewAdapterlayout5 gridViewAdapter1;
    private GridViewAdapterlayout7 gridViewAdapter2;
    private GridViewAdapterlayout8 gridViewAdapter3;
    private GridViewAdapterlayout9 gridViewAdapter4;
    private ArrayList<RecyclerViewItem> corporations;
    private ArrayList<RecyclerViewItem> operatingSystems,operatingSystems1,operatingSystems2,operatingSystems3,operatingSystems4;
    boolean isLoading = false;
    boolean isLoading1 = false;
    Animation anim;
    
    
    public Home_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Frag newInstance(String param1, String param2) {
        Home_Frag fragment = new Home_Frag();
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
        View v = inflater.inflate(R.layout.fragment_home_, container, false);
        gridView = (RecyclerView) v.findViewById(R.id.grid);
        gridView1 = (RecyclerView) v.findViewById(R.id.grid1);
        gridView3 = (RecyclerView) v.findViewById(R.id.grid3);
        gridView2 = (RecyclerView) v.findViewById(R.id.grid2);
        gridView4 = (RecyclerView) v.findViewById(R.id.grid4);

        gridView.setHasFixedSize(true);
        gridView1.setHasFixedSize(true);
        gridView2.setHasFixedSize(true);
        gridView3.setHasFixedSize(true);
        gridView4.setHasFixedSize(true);
        gridView.setNestedScrollingEnabled(false);
        gridView1.setNestedScrollingEnabled(false);
        gridView2.setNestedScrollingEnabled(false);
        gridView3.setNestedScrollingEnabled(false);
        gridView4.setNestedScrollingEnabled(false);
        anim = AnimationUtils.loadAnimation(getActivity(), R.animator.cycle);

        setdata1();
        setdata2();
        setdata3();
        setdata4();
        setdata5();

       /* GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapterlayout4(getActivity(),operatingSystems1,anim);
        gridView.setAdapter(gridViewAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        gridView1.setLayoutManager(layoutManager1);
        gridViewAdapter1 = new GridViewAdapterlayout5(getActivity(),operatingSystems,anim);
        gridView1.setAdapter(gridViewAdapter1);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridView3.setLayoutManager(layoutManager2);
        gridViewAdapter2 = new GridViewAdapterlayout7(getActivity(),operatingSystems2,anim);
        gridView3.setAdapter(gridViewAdapter2);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        gridView2.setLayoutManager(layoutManager3);
        gridViewAdapter3 = new GridViewAdapterlayout8(getActivity(),operatingSystems3,anim);
        gridView2.setAdapter(gridViewAdapter3);*/


        return v;
    }
    
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        sgen.backview = "Home_Frag";
        super.onDetach();
    }

    private void setDummyData() {
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
        if (fed.size() < 1) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_LONG).show();
        }
        operatingSystems = new ArrayList<RecyclerViewItem>();
        operatingSystems1 = new ArrayList<RecyclerViewItem>();
        operatingSystems2 = new ArrayList<RecyclerViewItem>();
        operatingSystems3 = new ArrayList<RecyclerViewItem>();
        for (int i = 0; i < fed.size(); i++) {
            if(i>=8){
                break;
            }
            try {
                operatingSystems.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                operatingSystems1.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                operatingSystems2.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                operatingSystems3.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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
        gridViewAdapter = new GridViewAdapterlayout4(getActivity(),operatingSystems,anim);
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
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        gridView1.setLayoutManager(layoutManager1);
        gridViewAdapter1 = new GridViewAdapterlayout5(getActivity(),operatingSystems1,anim);
        gridView1.setAdapter(gridViewAdapter1);
    }
    private void setdata3(){
        operatingSystems2 = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {

            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems2.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridView3.setLayoutManager(layoutManager2);
        gridViewAdapter2 = new GridViewAdapterlayout7(getActivity(),operatingSystems2,anim);
        gridView3.setAdapter(gridViewAdapter2);
    }
    private void setdata4(){
        operatingSystems3 = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems3.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        gridView2.setLayoutManager(layoutManager3);
        gridViewAdapter3 = new GridViewAdapterlayout8(getActivity(),operatingSystems3,anim);
        gridView2.setAdapter(gridViewAdapter3);
    }
    private void setdata5(){
        operatingSystems4 = new ArrayList<RecyclerViewItem>();
        ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select CA_NAME AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Category;");
        for (int i = 0; i < fed.size(); i++) {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),Integer.parseInt(fed.get(i).getcol2().toString()));
                operatingSystems4.add(new RecyclerViewItem(icon, fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridView4.setLayoutManager(layoutManager);
        gridViewAdapter4 = new GridViewAdapterlayout9(getActivity(),operatingSystems4,anim);
        gridView4.setAdapter(gridViewAdapter4);
    }

    //Online Fetch
    private void onlinefetch(){
        operatingSystems = new ArrayList<RecyclerViewItem>();
        operatingSystems1 = new ArrayList<RecyclerViewItem>();
        operatingSystems2 = new ArrayList<RecyclerViewItem>();
        //Grid View 1
        VolleyExecute.volleydynamicgetfun(getActivity(), "", "", "", "", "", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
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

                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
                gridView.setLayoutManager(layoutManager);
                gridViewAdapter = new GridViewAdapterlayout4(getActivity(),operatingSystems,anim);
                gridView.setAdapter(gridViewAdapter);


            }
        });
        //Grid View 2
        VolleyExecute.volleydynamicgetfun(getActivity(), "", "", "", "", "", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
                for (int i = 0; i < fed.size(); i++) {
                    if(i>=8){
                        break;
                    }
                    try {
                        operatingSystems1.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                gridView1.setLayoutManager(layoutManager1);
                gridViewAdapter1 = new GridViewAdapterlayout5(getActivity(),operatingSystems1,anim);
                gridView1.setAdapter(gridViewAdapter1);

            }
        });
        //Grid View 3
        VolleyExecute.volleydynamicgetfun(getActivity(), "", "", "", "", "", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
                for (int i = 0; i < fed.size(); i++) {
                    if(i>=8){
                        break;
                    }
                    try {
                        operatingSystems2.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
                gridView3.setLayoutManager(layoutManager2);
                gridViewAdapter2 = new GridViewAdapterlayout7(getActivity(),operatingSystems2,anim);
                gridView3.setAdapter(gridViewAdapter2);
            }
        });
        //Grid View 4 Load 40 and more on scroll
        VolleyExecute.volleydynamicgetfun(getActivity(), "", "", "", "", "", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
                for (int i = 0; i < fed.size(); i++) {
                    if(i>=8){
                        break;
                    }
                    try {
                        operatingSystems3.add(new RecyclerViewItem(sgen.Base64ToImage(fed.get(i).getcol2().toString()), fed.get(i).getcol1(), fed.get(i).getcol4().trim().toString()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                gridView2.setLayoutManager(layoutManager3);
                gridViewAdapter3 = new GridViewAdapterlayout8(getActivity(),operatingSystems3,anim);
                gridView2.setAdapter(gridViewAdapter3);
            }
        });
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

                ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
                if (fed.size() < 1) {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_LONG).show();
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

                ArrayList<Team> fed = sgen.getdata_fromsql(getActivity(), "select TITLE AS col1, IMG AS col2, DATE AS col3, ID AS col4,'-' AS col5 from Head;");
                if (fed.size() < 1) {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_LONG).show();
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

    private void show_Progress(final String event) {

        final Dialog dialogP = new Dialog(getActivity());
        dialogP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogP.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogP.setContentView(R.layout.dialog_progress1);
        //  dialog.setCancelable(true);


        AnimatedGifImageView animatedGifImageView = ((AnimatedGifImageView) dialogP.findViewById(R.id.animatedGifImageView));
        animatedGifImageView.setAnimatedGif(R.raw.ajax,
                AnimatedGifImageView.TYPE.FIT_CENTER);


        dialogP.show();

    }
}
