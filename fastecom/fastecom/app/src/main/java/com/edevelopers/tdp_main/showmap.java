package com.edevelopers.tdp_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.edevelopers.tdp_main.util.AppUtils;
import com.edevelopers.tdp_main.util.FetchAddressIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class showmap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemClickListener {
    private static String LOG_TAG="MAP";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String TAG = "MAP_LOCATION";
    Context mContext;
    //TextView mLocationMarkerText;
    private LatLng mCenterLatLong;
    AppCompatButton btnnext,btnloc,btnfind;
    AutoCompleteTextView autoCompletegoogle;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyBccgatgVcUJK-BAqG_8ab0USdEqtkSy1c";


    private AddressResultReceiver mResultReceiver;
    /**
     * The formatted location address.
     */
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    EditText mLocationAddress;
    //  TextView mLocationText;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    Toolbar mToolbar;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmap);
        mContext = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

       mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        btnnext = (AppCompatButton) findViewById(R.id.btnnext);
        //mLocationMarkerText = (TextView) findViewById(R.id.locationMarkertext);
        mLocationAddress = (EditText) findViewById(R.id.Address);

        btnloc=(AppCompatButton)findViewById(R.id.btncurr);
        btnloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(showmap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(showmap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    changeMap(mLastLocation);
                    Log.d(TAG, "ON connected");

                }
            }
        });

        //   mLocationText = (TextView) findViewById(R.id.Locality);
        //mLocationText.setVisibility(View.GONE);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

       /*************************************Auto Complete Adapter*************************************************/

        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item_google));
        autoCompView.setOnItemClickListener(this);

        /********************************* Actionbar *********************************/

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        sgen.lat_addr_home = 0;
        sgen.long_addr_home = 0;

//
//
//        LatLng from = new LatLng(28.3719638	,77.28254909999998);
//        LatLng to = new LatLng(28.397726845155976	,77.31313023716211);
//
//        //Calculating the distance in meters
//        Double distance = SphericalUtil.computeDistanceBetween(from, to);
//        LatLng from1 = new LatLng(28.375377530216664,77.28367857635021);
//        LatLng to1 = new LatLng(28.476873379523898,77.03512664884329);
//
//        //Calculating the distance in meters
//        Double distance1 = SphericalUtil.computeDistanceBetween(from1, to1);

        //Displaying the distance
        //    Toast.makeText(this,String.valueOf(distance+" Meters"),Toast.LENGTH_SHORT).show();

        /**************************************** Button Next ********************************/

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mLocationAddress.getText().toString().trim().length()<5)
                {
                    alertbox(sgen.mtitle,"Address Not Updated! Please Load Again");
                    return;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",mAddressOutput+"<##>"+ sgen.lat_addr +","+sgen.long_addr);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
//
        Initialize_Map(mapFragment);

    }




    /**************************************Map Initialization********************************************/

    private void Initialize_Map(SupportMapFragment mapFragment) {
        mapFragment.getMapAsync(this);
        mResultReceiver = new AddressResultReceiver(new Handler());

        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!AppUtils.isLocationEnabled(mContext)) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(mContext, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        mMap = googleMap;

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera postion change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;


                mMap.clear();

                try {

                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);

                    startIntentService(mLocation);
                    if(String.valueOf(sgen.lat_addr).trim().length()<5) {
                        sgen.lat_addr_home = mCenterLatLong.latitude;
                        sgen.long_addr_home = mCenterLatLong.longitude;
                    }
                    sgen.lat_addr = mCenterLatLong.latitude;
                    sgen.long_addr = mCenterLatLong.longitude;

                    Geocoder geocoder = new Geocoder(showmap.this, Locale.getDefault());

                 /**********************   get street and address location*************************************/

                    List<Address> addresses;
                   addresses = geocoder.getFromLocation(
                            mCenterLatLong.latitude,  mCenterLatLong.longitude, 1);

                    String Country="";
                    String State="";
                    String District="";
                    String CITY="";
                    String PstalCode="";

                    if (addresses.size() > 0) {
                             mAddressOutput = (addresses.get(0).getAddressLine(0));
                        Country = (addresses.get(0).getCountryName());
                        State = (addresses.get(0).getAdminArea());
                        District = (addresses.get(0).getSubAdminArea());
                        CITY = (addresses.get(0).getLocality());
                        PstalCode = (addresses.get(0).getPostalCode());

                    }

                    sgen.CUR_COUNTRY =Country;
                    sgen.CUR_STATE =State;
                    sgen.CUR_DISTRICT =District;
                    sgen.CUR_CITY =CITY;
                    sgen.CUR_PstalCode =PstalCode;

                    mLocationAddress.setText(mAddressOutput);
                    sgen.map_addr = mAddressOutput;
                    //mLocationMarkerText.setText("Lat : " + mCenterLatLong.latitude + "," + "Long : " + mCenterLatLong.longitude);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            changeMap(mLastLocation);
            Log.d(TAG, "ON connected");

        } else
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(500);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /*********************Location Change ****************************************/

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location != null)
                changeMap(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    /*********************google api client************************************/

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }

    private void changeMap(Location location) {

        Log.d(TAG, "Reaching map" + mMap);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;


            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(15f).tilt(70).build();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            mMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            // mLocationMarkerText.setText("Lat : " + location.getLatitude() + "," + "Long : " + location.getLongitude());
            startIntentService(location);


        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        getLocationFromAddress(str);

    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);

            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {
                //  showToast(getString(R.string.address_found));


            }


        }

    }

    /**
     * Updates the address in the UI.
     */
    protected void displayAddressOutput() {
        //  mLocationAddressTextView.setText(mAddressOutput);
        try {
            if (mAreaOutput != null)
                //     mLocationText.setText(mAreaOutput+ "");

                mLocationAddress.setText(mAddressOutput);
            sgen.map_addr = mAddressOutput;
            //   mLocationText.setText(mAreaOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService(Location mLocation) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }


    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(mContext, data);

                // TODO call location based filter


                LatLng latLong;


                latLong = place.getLatLng();

                //mLocationText.setText(place.getName() + "");

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(15f).tilt(70).build();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

            }


        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(mContext, data);
        } else if (resultCode == RESULT_CANCELED) {
            // Indicates that the activity closed before a selection was made. For example if
            // the user pressed the back button.
        }
    }
    public void alertbox(String title, String mymessage) {
        new AlertDialog.Builder(this)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .show();
    }


    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&language=en&sensor=true");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            sb.append("&location="+sgen.lat_addr_home+","+sgen.long_addr_home);
            sb.append("&radius=5");
            URL url = new URL(sb.toString());


            //Nearby Resturant Search API
//            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
//            sb.append("?key=" + API_KEY);
//            sb.append("&types=geocode&language=en&sensor=true");
//            sb.append("&keyword=" + URLEncoder.encode(input, "utf8"));
//            sb.append("&location="+sgen.lat_addr+","+sgen.long_addr+"&rankBy=distance");
//            sb.append(" &radius=500&type=restaurant");
//
//            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                String str=predsJsonArray.getJSONObject(i).getString("description");
                String mq="";
                if(str.trim().length()>40)
                {
                    mq=  str.substring(0,40)+"\n"+str.substring(40,str.length());


                }
                else mq=str;
                resultList.add(mq);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }


    /**
     * @method getLocationFromAddress
     * @param strAddress Address/Location String
     * @desc Get searched location points from address and plot/update on map.
     */
    public void getLocationFromAddress(String strAddress)
    {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress,5);

            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location=address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //Put marker on map on that LatLng
            //  Marker srchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.bb)));

            //Animate and Zoon on that map location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}