package com.edevelopers.tdp_main.Geo_Fencing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import com.edevelopers.tdp_main.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener {

    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private LocationResult newLocationresult;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private List<LatLng> dangerousareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        buildLocationRequest();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapActivity.this);
                        initAreas();
                        SettingGeoFire();
                        buildLocationCallback();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    private void initAreas() {
        dangerousareas = new ArrayList<>();

        dangerousareas.add(new LatLng(27.765421, 80.663393));
        dangerousareas.add(new LatLng(27.758854, 80.671855));
        dangerousareas.add(new LatLng(27.751036, 80.676096));
        dangerousareas.add(new LatLng(27.77261901861762,80.72661504149437));
        dangerousareas.add(new LatLng(27.767421523829274,80.73126800358295));
        dangerousareas.add(new LatLng(27.76556881798089,80.72839368134737));
        dangerousareas.add(new LatLng(27.779459635002574,80.7296509668231));
    }

    private void SettingGeoFire() {
        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);
    }

    private void buildLocationCallback() {

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(final LocationResult locationResult){
                //Create a GeoQuery
                LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude());
                mMap.addCircle(new CircleOptions().center(latLng)
                        .radius(10000) //10000m
                        .fillColor(0x220000FF) //transparent color
                        .strokeWidth(5.0f)
                );
                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude),10.f);//10000m
                geoQuery.addGeoQueryEventListener(MapActivity.this);
            }
        };

       /* locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(final LocationResult locationResult){
                if(mMap != null){


                }
            }
        };*/
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(1f);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(fusedLocationProviderClient != null)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());

        //Add circle for dangerous area
        for(final LatLng latLng: dangerousareas){
            geoFire.setLocation("You", new GeoLocation(latLng.latitude, latLng.longitude),
                    new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {

                        }
                    });
        }


    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
           sendNotification("EDMTDev", String.format("%s entered the Dangerous Area",key));
    }

    @Override
    public void onKeyExited(String key) {
        sendNotification("EDMTDev", String.format("%s leave the Dangerous Area",key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("EDMTDev", String.format("%s move within the Dangerous Area",key));
    }

    private void sendNotification(String title, String content) {
        Toast.makeText(this,""+content, Toast.LENGTH_SHORT).show();

        String NOTIFICATION_CHANNEL_ID = "GEO_FENCING_NOTIFICATION";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Geo_Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Geo Fencing Description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
        .setContentText(content)
        .setAutoCancel(false)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(),notification);

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
