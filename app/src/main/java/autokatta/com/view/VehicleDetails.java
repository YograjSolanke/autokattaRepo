package autokatta.com.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.VehicleDetailsSpecifications;
import autokatta.com.fragment.VehicleDetailsTwo;
import autokatta.com.fragment.VehicleDetails_Details;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleByIdResponse;
import retrofit2.Response;

public class VehicleDetails extends AppCompatActivity implements RequestNotifier {

    ImageView mVehiclePicture;
    CollapsingToolbarLayout collapsingToolbar;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    get Vehicle Data...
                     */
                    getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE).edit()
                            .putString("vehicle_id",getIntent().getExtras().getString("vehicle_id")).apply();
                    getVehicleData(getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                            .getString("loginContact", ""), getIntent().getExtras().getString("vehicle_id"));
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

                    mVehiclePicture = (ImageView) findViewById(R.id.vehicle_image);

                    ViewPager viewPager = (ViewPager) findViewById(R.id.vehicle_details_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.vehicle_details_tabs);
                    tabLayout.setupWithViewPager(viewPager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    Vehicle Details...
     */
    private void getVehicleData(String contact, String mVehicleId) {
        ApiCall mApiCall = new ApiCall(VehicleDetails.this, this);
        mApiCall.getVehicleById(contact, mVehicleId);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new VehicleDetails_Details(), "DETAILS");
        adapter.addFragment(new VehicleDetailsTwo(), "VEHICLE DETAILS");
        adapter.addFragment(new VehicleDetailsSpecifications(), "SPECIFICATION");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetVehicleByIdResponse mVehicleByIdResponse = (GetVehicleByIdResponse) response.body();
                for (GetVehicleByIdResponse.VehicleDatum datum : mVehicleByIdResponse.getSuccess().getVehicleData()) {
                    String dp = datum.getImage();
                    name = datum.getUsername();
                    String dp_path = "http://autokatta.com/mobile/uploads/" + dp;
                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mVehiclePicture);
                    collapsingToolbar.setTitle(name);
                }
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
