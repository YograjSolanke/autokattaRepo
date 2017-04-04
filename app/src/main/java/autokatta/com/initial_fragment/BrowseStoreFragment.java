package autokatta.com.initial_fragment;

import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
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

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.browseStore.ProductBasedStore;
import autokatta.com.browseStore.ServiceBasedStore;

/**
 * Created by ak-004 on 4/4/17.
 */

public class BrowseStoreFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    View view;
    TabLayout browseTab;
    ViewPager browseViewPager;


    GoogleApiClient googleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_store_fragment, container, false);
        browseTab = (TabLayout) view.findViewById(R.id.browse_store_tab);
        browseViewPager = (ViewPager) view.findViewById(R.id.browse_store_viewpager);


        System.out.println("GPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS ");

        if (browseViewPager != null) {
            //custom method
            setupViewPager(browseViewPager);
        }


        browseTab.setupWithViewPager(browseViewPager);
//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
        buildGoogleApiClient();


        return view;
    }


    /*
    Google Api functionality
     */
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
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
                .checkLocationSettings(googleApiClient, builder.build());
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
                            status.startResolutionForResult(getActivity(), 1000);
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

//        GPSTracker tracker = new GPSTracker(getApplicationContext());
//        String latitude = String.valueOf(tracker.getLatitude());
//        String longitude = String.valueOf(tracker.getLongitude());
//
//
//        System.out.println("GPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  latitude="+latitude);
//        System.out.println("GPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  longitude=" + longitude);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setupViewPager(ViewPager viewPager) {

        TabAdapterName tabAdapterName = new TabAdapterName(getActivity().getSupportFragmentManager());
        tabAdapterName.addFragment(new ProductBasedStore(), "Product Based");
        tabAdapterName.addFragment(new ServiceBasedStore(), "Service Based");

        viewPager.setAdapter(tabAdapterName);
    }
}
