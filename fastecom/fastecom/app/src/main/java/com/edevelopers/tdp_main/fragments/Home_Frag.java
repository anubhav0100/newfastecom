package com.edevelopers.tdp_main.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.edevelopers.tdp_main.Geo_Fencing.MapActivity;
import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.Services.CartService;
import com.edevelopers.tdp_main.Services.HomeService;
import com.edevelopers.tdp_main.Services.VolleyExecute;
import com.edevelopers.tdp_main.activities_M.ProductsViewActivity;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout12;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout4;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout5;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout7;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout8;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout9;
import com.edevelopers.tdp_main.adapter.ListViewAdapterlayout1;
import com.edevelopers.tdp_main.adapter.MyCustomPagerAdaptor;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.classes.AnimatedGifImageView;
import com.edevelopers.tdp_main.models.Productcat;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.edevelopers.tdp_main.showmap;

import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

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

    private MyReceiver myReceiver;
    public static final String HomeFILTER_ACTION_KEY = "HomeFILTER_ACTION_KEY";
    public static final String HomeGridFILTER_ACTION_KEY = "HomeGridFILTER_ACTION_KEY";

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
    private ArrayList<RecyclerViewItem> operatingSystems,operatingSystems1,operatingSystems2,operatingSystems3;
    boolean isLoading = false;
    boolean isLoading1 = false;
    private Animation anim;
    private String latlang;
    private Dialog dialog;
    private TextView SelectLocation,addresshome;

    private int currentSize = 0;

    private TextView shoptextview;
    
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
        StrictMode.setThreadPolicy(sgen.policy); //EQUAL OR ABOVE ANDROID 90
        gridView = (RecyclerView) v.findViewById(R.id.grid);
        gridView3 = (RecyclerView) v.findViewById(R.id.grid3);
        gridView2 = (RecyclerView) v.findViewById(R.id.grid2);

        SelectLocation = (TextView) v.findViewById(R.id.select_Location);
        addresshome = (TextView) v.findViewById(R.id.addresshome);

        shoptextview = (TextView) v.findViewById(R.id.shoptextview);

        addresshome.setText(sgen.CUR_Address);
        SelectLocation.setText(sgen.CUR_CITY);

        dialog = new Dialog(getContext(), R.style.FullHeightDialog);
        dialog.setContentView(R.layout.reclycer_data);
        dialog.setCancelable(false);
        sgen.activity = getActivity();

        gridView.setHasFixedSize(true);
        gridView2.setHasFixedSize(true);
        gridView3.setHasFixedSize(true);
        gridView.setNestedScrollingEnabled(false);
        gridView2.setNestedScrollingEnabled(false);
        gridView3.setNestedScrollingEnabled(false);
        anim = AnimationUtils.loadAnimation(getActivity(), R.animator.cycle);

        if(!sgen.loadonceHome.equals("Home")){
            dialog.show();
            setdata1();
            setdata4();
        }

        SelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), showmap.class),1);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String addre = data.getStringExtra("result");
            addresshome.setText(addre.split("<##>")[0]);
            sgen.CUR_Address = addre.split("<##>")[0];
            SelectLocation.setText(sgen.CUR_CITY);
            latlang = addre.split("<##>")[1];
        }
    }

    @Override
    public void onStart() {
     // getActivity().startActivity(new Intent(getActivity(), MapActivity.class));
        initScrollListener();
        setReceiver();
        super.onStart();
    }

    @Override
    public void onStop() {
        dialog.hide();
       // getActivity().unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        sgen.backview = "Home_Frag";
        super.onDetach();
    }

    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HomeFILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("Setlayout1");
            String message1 = intent.getStringExtra("Setlayout2");
            if(message1 == null){
                message1 = "";
            }
            if(message == null){
                message = "";
            }
            if(message.equals("Setlayout1")){
                GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
                gridView3.setLayoutManager(layoutManager3);
                gridViewAdapter2 = new GridViewAdapterlayout7(getActivity(),operatingSystems2,anim);
                gridView3.setAdapter(gridViewAdapter2);
                message = "";
            }
            if(message1.equals("Setlayout2")){
                GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                gridView2.setLayoutManager(layoutManager3);
                gridViewAdapter3 = new GridViewAdapterlayout8(getActivity(),operatingSystems3,anim);
                gridView2.setAdapter(gridViewAdapter3);
                message1 = "";
            }
            sgen.loadonceHome = "Home";
            dialog.hide();
        }
    }

    private void setdata1() {

        String query = "SELECT PC.PC_ID, PC.PC_NAME, PC.PC_LEVEL, PC.PC_LEVEL_NAME1, PC.PC_LEVEL_NAME2, PC.PC_LEVEL_NAME3, PC.PC_ORDER, PC.PC_DEFAULT,  PC.PC_TYPE, PC.CREATED_DATE,   " +
                "CASE WHEN F.FILE_lOC IS NULL THEN CONCAT('0') ELSE CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) END AS PC_UID from PRODUCT_CATEGORY_LEVEL PC  " +
                "LEFT JOIN FILE_TAB F ON F.F_TYPE = 'PCL' AND F.PU_ID = PC.IMG_ID AND F.LINK_ID = PC.PC_ID  " +
                "where PC.PC_LEVEL = '0' AND PC.PC_TYPE = 'PCL';";

        VolleyExecute.volleydynamicgetfunproductcat(getActivity(), "-", query, "-", "-", "-", new VolleyExecute.VolleyCallbackproductcat() {
            @Override
            public void onSuccess(ArrayList<Productcat> productcats) {

                operatingSystems2 = new ArrayList<RecyclerViewItem>();
                for (int i = 0; i < productcats.size(); i++) {
                    try {
                        Bitmap icon = null;
                        operatingSystems2.add(new RecyclerViewItem(icon,
                                "" + productcats.get(i).getPC_NAME(),
                                "" + productcats.get(i).getPC_ID(),
                                "" + productcats.get(i).getPC_LEVEL_NAME1(),
                                "" + productcats.get(i).getPC_UID(),
                                "" + productcats.get(i).getPC_LEVEL_NAME3(),
                                "",
                                "" + productcats.get(i).getPC_UID(),
                                false
                        ));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
                gridView3.setLayoutManager(layoutManager3);
                gridViewAdapter2 = new GridViewAdapterlayout7(getActivity(),operatingSystems2,anim);
                gridView3.setAdapter(gridViewAdapter2);
                dialog.hide();
            }
        });
    }

    private void setdata4() {
        String query = "SELECT DISTINCT SM.S_ID as col1,CONCAT(SM.S_NAME,'!~!~!',SM.G_LOC) as col2,CONCAT(TIME_FORMAT(SPC.OPEN_HRF,'%l %p'),'-',TIME_FORMAT(SPC.OPEN_HRT,'%l %p'))AS COL3, " +
                " IFNULL((SELECT truncate(AVG(SF.R_POINT),1)FROM SHOP_FEEDBACK SF WHERE  SF.S_ID = SM.S_ID),'2.5')  AS COL4,  " +
                "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5 FROM SHOP_MASTER SM  " +
                "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = SM.S_ID AND SPC.STATUS = 'A'  " +
                "INNER JOIN M_USER MU ON MU.TYPE = 'SHU' AND MU.LOCATION LIKE '%"+sgen.SGLOC+"%'" +
                "inner join FILE_TAB F ON F.PU_ID = SM.PC_IMG_ID AND F.LINK_ID = SM.S_ID AND F.F_TYPE = 'SMF' ORDER BY SM.S_ID ASC LIMIT 3";

        VolleyExecute.volleydynamicgetfun(getActivity(), "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Team> teams) {
                ArrayList<Team> fed = teams;
                operatingSystems3 = new ArrayList<RecyclerViewItem>();
                for (int i = 0; i < fed.size(); i++) {
                    try {
                        String[] latlang = fed.get(i).getcol2().split("!~!~!")[1].split(",");
                        float distance = sgen.getdistancegeo(sgen.latitudemain, sgen.longitudemain,Double.parseDouble(latlang[0]),Double.parseDouble(latlang[1]));
                        if(distance <= sgen.shopradius){
                            operatingSystems3.add(new RecyclerViewItem(null,
                                    "" + fed.get(i).getcol2().split("!~!~!")[0],
                                    "" + fed.get(i).getcol1(),
                                    "" + fed.get(i).getcol3(),
                                    "" + fed.get(i).getcol4(),
                                    "",
                                    ""+fed.get(i).getcol2().split("!~!~!")[1],
                                    "" + fed.get(i).getcol5(),
                                    true
                            ));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                GridLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                gridView2.setLayoutManager(layoutManager3);
                gridViewAdapter3 = new GridViewAdapterlayout8(getActivity(),operatingSystems3,anim);
                gridView2.setAdapter(gridViewAdapter3);
                dialog.hide();
            }
        });
    }

    private void initScrollListener() {
        gridView2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading1) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == operatingSystems3.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading1 = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
       operatingSystems3.add(null);
       gridViewAdapter3.notifyItemInserted(operatingSystems3.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                operatingSystems3.remove(operatingSystems3.size() - 1);
                int scrollPosition = operatingSystems3.size();
                gridViewAdapter3.notifyItemRemoved(scrollPosition);
                currentSize = scrollPosition;
                int nextLimit = currentSize + 3;

                String query = "SELECT DISTINCT SM.S_ID as col1,CONCAT(SM.S_NAME,'!~!~!',SM.G_LOC) as col2,CONCAT(TIME_FORMAT(SPC.OPEN_HRF,'%l %p'),'-',TIME_FORMAT(SPC.OPEN_HRT,'%l %p'))AS COL3, " +
                        " IFNULL((SELECT truncate(AVG(SF.R_POINT),1)FROM SHOP_FEEDBACK SF WHERE  SF.S_ID = SM.S_ID),'2.5')  AS COL4,  " +
                        "CONCAT('http://Images.aasinfotech.com',substr(F.FILE_lOC,2)) AS COL5 FROM SHOP_MASTER SM  " +
                        "INNER JOIN SHOP_PRODUCT_PRICE SPC ON SPC.S_ID = SM.S_ID AND SPC.STATUS = 'A'  " +
                        "INNER JOIN M_USER MU ON MU.TYPE = 'SHU' AND MU.LOCATION LIKE '%"+sgen.SGLOC+"%'" +
                        "inner join FILE_TAB F ON F.PU_ID = SM.PC_IMG_ID AND F.LINK_ID = SM.S_ID AND F.F_TYPE = 'SMF'  ORDER BY SM.S_ID ASC LIMIT "+nextLimit+"";

                VolleyExecute.volleydynamicgetfun(getActivity(), "-", query, "-", "-", "-", new VolleyExecute.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Team> teams) {
                        ArrayList<Team> fed = teams;
                        for (int i = currentSize; i < fed.size(); i++) {
                            try {
                                String[] latlang = fed.get(i).getcol2().split("!~!~!")[1].split(",");
                                float distance = sgen.getdistancegeo(sgen.latitudemain, sgen.longitudemain,Double.parseDouble(latlang[0]),Double.parseDouble(latlang[1]));

                                if(distance <= sgen.shopradius){ //10 km radius
                                    operatingSystems3.add(new RecyclerViewItem(null,
                                            "" + fed.get(i).getcol2().split("!~!~!")[0],
                                            "" + fed.get(i).getcol1(),
                                            "" + fed.get(i).getcol3(),
                                            "" + fed.get(i).getcol4(),
                                            "",
                                            ""+fed.get(i).getcol2().split("!~!~!")[1],
                                            "" + fed.get(i).getcol5(),
                                            true
                                    ));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        gridViewAdapter3.notifyDataSetChanged();
                        isLoading1 = false;
                    }
                });
            }
        }, 2000);
    }
}
