package autokatta.com.other;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    List<String> latList = new ArrayList<>();
    List<String> longList = new ArrayList<>();
    String[] kmsArray = new String[]{"5km", "10km", "15km", "20km", "25km"};
    Spinner kmsSpinner;
    Button kmsButton;
    int locationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        kmsSpinner = (Spinner) findViewById(R.id.kmsspinner);
        kmsButton = (Button) findViewById(R.id.kmsButton);
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
                /*kmsString = kmsSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Kms-" + kmsString, Toast.LENGTH_SHORT).show();*/
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStore();
            }
        });
    }

    /*
    Get Store Locations...
     */
    private void getStore() {
        latList.add("41.9408447");
        longList.add("-87.6494265");
        latList.add("18.4698714");
        longList.add("73.8584464");
        latList.add("32.9494207");
        longList.add("-96.8187902");

        for (int i = 0; i < latList.size(); i++) {
            getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("lat", latList.get(i)).apply();
            getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("lng", longList.get(i)).apply();
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
        mMap = googleMap;
        getApplicationContext().getSharedPreferences("location", 0);
        // Getting number of locations already stored
        locationCount = getSharedPreferences("location", MODE_PRIVATE).getInt("locationCount", 0);
        // Getting stored zoom level if exists else return 0
        String zoom = getSharedPreferences("location", MODE_PRIVATE).getString("zoom", "0");

        /*if (locationCount != 0) {*/
            double lat = 0;
            double lng = 0;

        for (int i = 0; i < latList.size(); i++) {
                lat = Double.parseDouble(latList.get(i));
                lng = Double.parseDouble(longList.get(i));
            // Add a marker in Sydney and move the camera
            LatLng india = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
            }
        //}
    }
}
