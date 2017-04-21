package autokatta.com.view;

import android.Manifest.permission;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;

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
    FloatingActionButton mLike, mCall, mAutoshare, mShare;
    String mVehicle_Id, Title, mPrice, mBrand, mModel, mYear, mKms, mRTO_City, mAddress, mRegistration, mSendImage, imgUrl;
    String contact, mLikestr, prefcontact, allDetails;
    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mFab = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mAutoshare = (FloatingActionButton) findViewById(R.id.autokatta_share);
        mShare = (FloatingActionButton) findViewById(R.id.share);

        mApiCall = new ApiCall(VehicleDetails.this, this);
        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mAutoshare.setOnClickListener(this);

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
                    Title = datum.getTitle();
                    mAddress = (String) datum.getLocationAddress();
                    mRegistration = datum.getRegistrationNumber();
                    mYear = datum.getYearOfRegistration();
                    mPrice = datum.getPrice();
                    mBrand = datum.getBrand();
                    mModel = datum.getModel();
                    mRTO_City = datum.getRTOCity();
                    mSendImage = datum.getImage();
                    mKms = datum.getKmsRunning();

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
                String imageFilePath;
                Intent intent = new Intent(Intent.ACTION_SEND);

                if (mSendImage.equalsIgnoreCase("") || mSendImage.equalsIgnoreCase(null)) {
                    imgUrl = "http://autokatta.com/mobile/uploads/" + "abc.jpg";
                } else {
                    imgUrl = "http://autokatta.com/mobile/uploads/" + mSendImage;
                }
                Log.e("TAG", "img : " + imgUrl);

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(imgUrl));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(imgUrl, null, MimeTypeMap.getFileExtensionFromUrl(imgUrl));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "img : " + imgUrl);

                DownloadManager manager = (DownloadManager) getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Log.e("TAG", "img URL: " + imgUrl);

                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/vehicle/" + mVehicle_Id);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                startActivity(Intent.createChooser(intent, "Autokatta"));

                break;
            case R.id.autokatta_share:
                allDetails = Title + "=" +
                        mPrice + "=" +
                        mBrand + "=" +
                        mModel + "=" +
                        mYear + "=" +
                        mKms + "=" +
                        mRTO_City + "=" +
                        mAddress + "=" +
                        mRegistration + "=" +
                        mSendImage + "=" + "0";

                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_vehicle_id", mVehicle_Id).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "vehicle").apply();


                Intent i = new Intent(getApplicationContext(), ShareWithinAppActivity.class);
                startActivity(i);
                finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(VehicleDetails.this, R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        } else {
            finish();
        }
    }
}
