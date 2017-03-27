package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.VehicleDetailsTwo;
import autokatta.com.fragment.VehicleDetails_Details;

public class VehicleDetails extends AppCompatActivity {

    ImageView mVehiclePicture;
    String mVehicleName;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    get Vehicle Data...
                     */
                    getVehicleData();
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

                    mVehiclePicture = (ImageView) findViewById(R.id.vehicle_image);

                    ViewPager viewPager = (ViewPager) findViewById(R.id.vehicle_details_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.vehicle_details_tabs);
                    tabLayout.setupWithViewPager(viewPager);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    Vehicle Details...
     */
    private void getVehicleData() {

    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new VehicleDetails_Details(), "DETAILS");
        adapter.addFragment(new VehicleDetailsTwo(), "VEHICLE DETAILS");
        adapter.addFragment(new VehicleDetails_Details(), "SPECIFICATION");
        viewPager.setAdapter(adapter);
    }

}
