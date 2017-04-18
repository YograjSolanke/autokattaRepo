package autokatta.com.view;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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

public class VehicleDetails extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mVehiclePicture;
    CollapsingToolbarLayout collapsingToolbar;
    String name;
    FloatingActionMenu mFab;
    FloatingActionButton mLike, mCall;
    String mVehicle_Id;
    String contact, mLikestr, prefcontact;
    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFab = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mApiCall = new ApiCall(VehicleDetails.this, this);
        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFab.setOnClickListener(this);

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
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit()
                            .putString("vehicle_id", getIntent().getExtras().getString("vehicle_id")).apply();
                    mVehicle_Id = getIntent().getExtras().getString("vehicle_id");

                    prefcontact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                    getVehicleData(mVehicle_Id);
                    mApiCall.callingVehicleDetails(mVehicle_Id, "View");
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
    private void getVehicleData(String mVehicleId) {

        mApiCall.getVehicleById(prefcontact, mVehicleId);
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
                    contact = datum.getContact();
                    mLikestr = datum.getStatus();

                    if (mLikestr.equalsIgnoreCase("no")) {
                        mLike.setLabelText("Like");
                        mLike.setLabelTextColor(Color.WHITE);
                        mFab.setClosedOnTouchOutside(true);
                    } else {
                        mLike.setLabelText("Liked");
                        mLike.setLabelTextColor(Color.RED);
                        mFab.setClosedOnTouchOutside(true);
                    }
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
        if (str != null) {
            if (str.equals("success_like")) {
                CustomToast.customToast(getApplicationContext(), " Liked Successfully");
                mLike.setLabelText("Liked");
                mLike.setLabelTextColor(Color.RED);
                mLikestr = "yes";
            } else if (str.equals("success_unlike")) {
                CustomToast.customToast(getApplicationContext(), " UnLiked Successfully");
                mLike.setLabelText("Like");
                mLike.setLabelTextColor(Color.WHITE);
                mLikestr = "no";
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_c:
                call(contact);
                mApiCall.callingVehicleDetails(mVehicle_Id, "Call");
                break;
            case R.id.like_l:
                if (mLikestr.equalsIgnoreCase("no")) {
                    mApiCall.vehicleLike(prefcontact, contact, "4", mVehicle_Id);
                    mFab.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.vehicleUnLike(prefcontact, contact, "4", mVehicle_Id);
                    mFab.setClosedOnTouchOutside(true);
                }

                break;
            case R.id.share:
                break;
            case R.id.autokatta_share:
                break;
        }
    }

    //Calling Functionality
    private void call(String contact) {

        try {
            Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
            if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in vehicle Details Fragment\n");
        }
    }
}
