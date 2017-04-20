package autokatta.com.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;

    String storelattitude, storelongitude, kmsString;
    ArrayList<String> latList = new ArrayList<>();
    ArrayList<String> longList = new ArrayList<>();
    JSONArray web_array = null;

    Spinner kmsSpinner;
    Button kmsButton;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
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

            kmsSpinner = (Spinner) findViewById(R.id.kmsspinner);
            kmsButton = (Button) findViewById(R.id.kmsButton);

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

            System.out.println("Lattitude" + storelattitude);
            System.out.println("Longitude" + storelongitude);
            System.out.println("LongitudeList" + latList.size());
            System.out.println("LongitudeList" + longList.size());

            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException s) {
                s.printStackTrace();
            }

            sharedPreferences = getApplicationContext().getSharedPreferences("location", 0);
            locationCount = sharedPreferences.getInt("locationCount", 0);
            String zoom = sharedPreferences.getString("zoom", "0");
            if (locationCount != 0) {
                String lat = "";
                String lng = "";
                for (int i = 0; i < locationCount; i++) {
                    lat = sharedPreferences.getString("lat" + i, "0");
                    lng = sharedPreferences.getString("lng" + i, "0");
                    drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
            }
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
            }
        });
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                googleMap.clear();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                locationCount = 0;

            }
        });
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void drawMarker(LatLng point) {
// Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        markerOptions.position(point);

// Adding marker on the Google Map
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        googleMap = googleMaps;

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
}
