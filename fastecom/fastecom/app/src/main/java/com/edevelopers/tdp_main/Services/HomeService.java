package com.edevelopers.tdp_main.Services;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.edevelopers.tdp_main.Geo_Fencing.MapActivity;
import com.edevelopers.tdp_main.R;
import com.edevelopers.tdp_main.activities_M.ProductsViewActivity;
import com.edevelopers.tdp_main.activities_M.ShopViewActivity;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout12;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout7;
import com.edevelopers.tdp_main.adapter.GridViewAdapterlayout8;
import com.edevelopers.tdp_main.adapter.RecyclerViewItem;
import com.edevelopers.tdp_main.fragments.Home_Frag;
import com.edevelopers.tdp_main.models.Productcat;
import com.edevelopers.tdp_main.models.Team;
import com.edevelopers.tdp_main.sgen;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HomeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.edevelopers.tdp_main.Services.action.FOO";
    private static final String ACTION_BAZ = "com.edevelopers.tdp_main.Services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.edevelopers.tdp_main.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.edevelopers.tdp_main.Services.extra.PARAM2";

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private LocationResult newlocationResult;
    private List<LatLng> dangerousareas;
    private int Locationcount = 0;

    private String SHOPKEY = "";

    public HomeService() {
        super("HomeService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, HomeService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, HomeService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String key = intent.getStringExtra("key");

            if (Home_Frag.HomeFILTER_ACTION_KEY.equals(key)) {

            }
            if (Home_Frag.HomeGridFILTER_ACTION_KEY.equals(key)) {

            }
            if (ShopViewActivity.SHOPFILTER_ACTION_KEY.equals(key)) {
                //functioncategoryvalsetvalShopViewActivity(intent.getStringExtra("data"), intent);
            }

        }
    }




}
