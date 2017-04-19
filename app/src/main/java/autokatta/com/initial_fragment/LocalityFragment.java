package autokatta.com.initial_fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;

import autokatta.com.R;

/**
 * Created by ak-003 on 19/4/17.
 */

public class LocalityFragment extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    Activity mActivity;

    ProgressDialog dialog;
    String storelattitude, storelongitude, kmsString;
    ArrayList<String> latList = new ArrayList<>();
    ArrayList<String> longList = new ArrayList<>();
    JSONArray web_array = null;

    Spinner kmsSpinner;
    Button kmsButton;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_locality);

        //root = inflater.inflate(R.layout.fragment_locality, container, false);
        Log.i("In Locality", "o");

// Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

// Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), requestCode);
            dialog.show();

        } else { // Google Play Services are available
            buildGoogleApiClient();

            try {
                getstore();

            } catch (Exception e) {
                e.printStackTrace();
            }
// Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            kmsSpinner = (Spinner) findViewById(R.id.kmsspinner);
            kmsButton = (Button) findViewById(R.id.kmsButton);
            //kmsString=kmsSpinner.getSelectedItem().toString();

            String[] kmsArray = new String[]{"5km", "10km", "15km", "20km", "25km"};
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, kmsArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            kmsSpinner.setAdapter(dataAdapter);

            kmsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            kmsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kmsString = kmsSpinner.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), "Kms-" + kmsString, Toast.LENGTH_SHORT).show();
                }
            });
//            SupportMapFragment fm = new SupportMapFragment();
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            transaction.add(R.id.map, fm).commit();

            System.out.println("Lattitude" + storelattitude);
            System.out.println("Longitude" + storelongitude);
            System.out.println("LongitudeList" + latList.size());
            System.out.println("LongitudeList" + longList.size());


            fm.getMapAsync(this);
// Getting GoogleMap object from the fragment
            fm.getMapAsync(this);

// Enabling MyLocation Layer of Google Map
            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException s) {
                s.printStackTrace();
            }

// Opening the sharedPreferences object
            sharedPreferences = getApplicationContext().getSharedPreferences("location", 0);

// Getting number of locations already stored
            locationCount = sharedPreferences.getInt("locationCount", 0);

// Getting stored zoom level if exists else return 0
            String zoom = sharedPreferences.getString("zoom", "0");

// If locations are already saved
            if (locationCount != 0) {

                String lat = "";
                String lng = "";


// Iterating through all the locations stored
                for (int i = 0; i < locationCount; i++) {

// Getting the latitude of the i-th location
                    lat = sharedPreferences.getString("lat" + i, "0");

// Getting the longitude of the i-th location
                    lng = sharedPreferences.getString("lng" + i, "0");

// Drawing marker on the map
                    drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }

// Moving CameraPosition to last clicked position
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

// Setting the zoom level in the map on last position is clicked
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
            }

        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
//                locationCount++;
//
//// Drawing marker on the map
//                drawMarker(point);
//
///** Opening the editor object to write data to sharedPreferences */
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//// Storing the latitude for the i-th location
//                editor.putString("lat"+ Integer.toString((locationCount-1)), Double.toString(point.latitude));
//
//// Storing the longitude for the i-th location
//                editor.putString("lng"+ Integer.toString((locationCount-1)), Double.toString(point.longitude));
//
//// Storing the count of locations or marker count
//                editor.putInt("locationCount", locationCount);
//
///** Storing the zoom level to the shared preferences */
//                editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));
//
///** Saving the values stored in the shared preferences */
//                editor.commit();
//
//                Toast.makeText(getApplicationContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

// Removing the marker and circle from the Google Map
                googleMap.clear();

// Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

// Clearing the editor
                editor.clear();

// Committing the changes
                editor.commit();

// Setting locationCount to zero
                locationCount = 0;

            }
        });

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Toast.makeText(getApplicationContext(), "LastLocation Latitude" + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "LastLocation Longitude" + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
            System.out.println("LastLocation Latitude" + String.valueOf(mLastLocation.getLatitude()));
            System.out.println("LastLocation Longitude" + String.valueOf(mLastLocation.getLongitude()));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mGoogleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // **************************
        builder.setAlwaysShow(true); // this is the key ingredient
        // **************************

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {


            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result
                        .getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be
                        // fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling
                            // startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult((Activity) getApplicationContext(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have
                        // no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

/*        GPSTracker tracker = new GPSTracker(getActivity());
        String latitude = String.valueOf(tracker.getLatitude());
        String longitude = String.valueOf(tracker.getLongitude());


        System.out.println("GPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  latitude="+latitude);
        System.out.println("GPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  longitude=" + longitude);*/

    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_locality);

        //root = inflater.inflate(R.layout.fragment_locality, container, false);
        Log.i("In Locality","o");

// Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

// Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), requestCode);
            dialog.show();

        } else { // Google Play Services are available
            buildGoogleApiClient();

            try {
                getstore();

            } catch (Exception e) {
                e.printStackTrace();
            }
// Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            kmsSpinner = (Spinner) findViewById(R.id.kmsspinner);
            kmsButton = (Button) findViewById(R.id.kmsButton);
            //kmsString=kmsSpinner.getSelectedItem().toString();

            String[] kmsArray = new String[]{"5km", "10km", "15km", "20km", "25km"};
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, kmsArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            kmsSpinner.setAdapter(dataAdapter);

            kmsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            kmsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kmsString = kmsSpinner.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), "Kms-" + kmsString, Toast.LENGTH_SHORT).show();
                }
            });
//            SupportMapFragment fm = new SupportMapFragment();
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            transaction.add(R.id.map, fm).commit();

            System.out.println("Lattitude" + storelattitude);
            System.out.println("Longitude" + storelongitude);
            System.out.println("LongitudeList" + latList.size());
            System.out.println("LongitudeList" + longList.size());


            fm.getMapAsync(this);
// Getting GoogleMap object from the fragment
            fm.getMapAsync(this);

// Enabling MyLocation Layer of Google Map
            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException s) {
                s.printStackTrace();
            }

// Opening the sharedPreferences object
            sharedPreferences = getApplicationContext().getSharedPreferences("location", 0);

// Getting number of locations already stored
            locationCount = sharedPreferences.getInt("locationCount", 0);

// Getting stored zoom level if exists else return 0
            String zoom = sharedPreferences.getString("zoom", "0");

// If locations are already saved
            if (locationCount != 0) {

                String lat = "";
                String lng = "";


// Iterating through all the locations stored
                for (int i = 0; i < locationCount; i++) {

// Getting the latitude of the i-th location
                    lat = sharedPreferences.getString("lat" + i, "0");

// Getting the longitude of the i-th location
                    lng = sharedPreferences.getString("lng" + i, "0");

// Drawing marker on the map
                    drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }

// Moving CameraPosition to last clicked position
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

// Setting the zoom level in the map on last position is clicked
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
            }

        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
//                locationCount++;
//
//// Drawing marker on the map
//                drawMarker(point);
//
//*//** Opening the editor object to write data to sharedPreferences *//*
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//// Storing the latitude for the i-th location
//                editor.putString("lat"+ Integer.toString((locationCount-1)), Double.toString(point.latitude));
//
//// Storing the longitude for the i-th location
//                editor.putString("lng"+ Integer.toString((locationCount-1)), Double.toString(point.longitude));
//
//// Storing the count of locations or marker count
//                editor.putInt("locationCount", locationCount);
//
//*//** Storing the zoom level to the shared preferences *//*
//                editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));
//
//*/

    /**
     * Saving the values stored in the shared preferences
     *//*
//                editor.commit();
//
//                Toast.makeText(getApplicationContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

// Removing the marker and circle from the Google Map
                googleMap.clear();

// Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

// Clearing the editor
                editor.clear();

// Committing the changes
                editor.commit();

// Setting locationCount to zero
                locationCount = 0;

            }
        });

    }*/


    private void drawMarker(LatLng point) {
// Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        markerOptions.position(point);

// Adding marker on the Google Map
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap1) {

        this.googleMap = googleMap1;


        sharedPreferences = getApplicationContext().getSharedPreferences("location", 0);

// Getting number of locations already stored
        locationCount = sharedPreferences.getInt("locationCount", 0);

// Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

// If locations are already saved
        if (locationCount != 0) {
            double lat = 0;
            double lng = 0;

            for (int i = 0; i < web_array.length(); i++) {
                //double lat = Float.valueOf(storelattitude);
                //double lng = Float.valueOf(storelongitude);
                lat = Double.parseDouble(latList.get(i));
                lng = Double.parseDouble(longList.get(i));
                // Add a marker in Sydney, Australia, and move the camera.
                LatLng India = new LatLng(lat, lng);
                drawMarker(new LatLng(Double.parseDouble(latList.get(i)), Double.parseDouble(longList.get(i))));

//           googleMap.addMarker(new MarkerOptions().position(India).title("Marker in India"));
//           googleMap.moveCamera(CameraUpdateFactory.newLatLng(India));
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

// Setting the zoom level in the map on last position is clicked
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        }
    }


    private void getstore() {
        latList.add("41.9408447");
        longList.add("-87.6494265");
        latList.add("18.4698714");
        longList.add("73.8584464");
        latList.add("32.9494207");
        longList.add("-96.8187902");

        for (int i = 0; i < latList.size(); i++) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lat", latList.get(i));
            editor.putString("lng", longList.get(i));

            editor.apply();
        }
    }

    /////////////////volley ///////////////////

 /*   private void getstore()
    {
        final List<String> countries = new ArrayList<String>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/getAllStore.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        countries.clear();
                        System.out.println("rutu------------ locality ------Latitude Longitude data:"+response);


                        try {
                            web_array = new JSONArray(response);


                            for (int i = 0, count = web_array.length(); i < count; i++) {

                                JSONObject jsonObject = web_array.getJSONObject(i);

                                storelattitude = jsonObject.getString("latitude");
                                storelongitude = jsonObject.getString("longitude");

                                latList.add(storelattitude);
                                longList.add(storelongitude);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("lat", latList.get(i));
                                editor.putString("lng", longList.get(i));
//                editor.putString("lat",Double.toString(Double.parseDouble(storelattitude)));
//                editor.putString("lng",Double.toString(Double.parseDouble(storelattitude)));
                                editor.apply();
                            }
                        }    catch(Exception e)
                        {
                            System.out.println(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:getstore:" + error.toString());


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("contact_no", "9494949494");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }*/


}
