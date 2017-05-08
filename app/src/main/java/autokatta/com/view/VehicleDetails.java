package autokatta.com.view;

import android.Manifest.permission;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.HashMap;

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

public class VehicleDetails extends AppCompatActivity implements RequestNotifier, View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ImageView mVehiclePicture;
    CollapsingToolbarLayout collapsingToolbar;
    String name;
    FloatingActionMenu mFab;
    FloatingActionButton mLike, mCall, mAutoshare, mShare, mChat;
    String mVehicle_Id, Title, mPrice, mBrand, mModel, mYear, mKms, mRTO_City, mAddress, mRegistration, mSendImage, imgUrl;
    String contact, mLikestr, prefcontact, allDetails;
    ApiCall mApiCall;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;

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
        mChat = (FloatingActionButton) findViewById(R.id.chat_c);

        mApiCall = new ApiCall(VehicleDetails.this, this);
        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mAutoshare.setOnClickListener(this);
        mChat.setOnClickListener(this);

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

        /*
        Banner...
         */
        /*Hash_file_maps = new HashMap<String, String>();
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        Hash_file_maps.put("Android CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
        Hash_file_maps.put("Android Donut", "http://androidblog.esy.es/images/donut-2.png");
        Hash_file_maps.put("Android Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        Hash_file_maps.put("Android Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        Hash_file_maps.put("Android GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");

        for (String name : Hash_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(VehicleDetails.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);*/

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

                    getChatEnquiryStatus(prefcontact, contact, mVehicle_Id);

                    if (mLikestr.equalsIgnoreCase("no")) {
                        mLike.setLabelText("Like");
                        mLike.setLabelTextColor(Color.WHITE);
                        mFab.setClosedOnTouchOutside(true);
                    } else {
                        mLike.setLabelText("Liked");
                        mLike.setLabelTextColor(Color.RED);
                        mFab.setClosedOnTouchOutside(true);
                    }

                    if (contact.equals(prefcontact)) {
                        mLike.setVisibility(View.GONE);
                        mCall.setVisibility(View.GONE);
                        mChat.setVisibility(View.GONE);
                    }

                    Hash_file_maps = new HashMap<String, String>();
                    sliderLayout = (SliderLayout) findViewById(R.id.slider);
                    String dp_path = "http://autokatta.com/mobile/uploads/";// + dp;
                    /*Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mVehiclePicture);*/

                    String firstWord = "", all = "";
                    if (dp.contains(",")) {
                        /*String arr[] = dp.split(",", 2);
                        for (int i=0; i<arr.length; i++){
                            Hash_file_maps.put("Image-"+i,dp_path+arr[0]);
                        }*/
                        String[] items = dp.split(",");
                        for (String item : items) {
                            Hash_file_maps.put("Image-" + item, dp_path + item.replaceAll(" ", ""));
                        }
                        /*
                        success.setSingleImage(dp_path+firstWord);
                        Hash_file_maps.put(""dp_path+firstWord);
                        all = dp.replace(",", "/ ");
                        //success.setAllImage(all);*/
                    } else {
                        /*success.setSingleImage(firstWord);
                        success.setAllImage(all);*/
                        Hash_file_maps.put("Image-" + dp, dp_path + dp.replaceAll(" ", ""));
                    }
                }

                /* Banner...*/
                for (String name : Hash_file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(VehicleDetails.this);
                    textSliderView
                            .description(name)
                            .image(Hash_file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);
                    sliderLayout.addSlider(textSliderView);
                }
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(3000);
                sliderLayout.addOnPageChangeListener(this);
                collapsingToolbar.setTitle(name);
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    private void getChatEnquiryStatus(String prefcontact, String contact, String mVehicle_id) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getChatEnquiryStatus(prefcontact, contact, "", "", mVehicle_id);
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Vehicle Details Activity");
            error.printStackTrace();
        }
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
            } else if (str.equals("success_message_saved")) {
                CustomToast.customToast(getApplicationContext(), "Enquiry Sent");
            } else if (str.equals("yes")) {
                mChat.setLabelText("Chat");
            } else if (str.equals("no")) {
                mChat.setLabelText("Send Enquiry");

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
            case R.id.chat_c:
                if (mChat.getLabelText().equalsIgnoreCase("send enquiry")) {
                    ApiCall mpApicall = new ApiCall(this, this);
                    mpApicall.sendChatMessage(prefcontact, contact, "Please send information About this", "", "",
                            "", mVehicle_Id);
                } else {
                    Bundle b = new Bundle();
                    b.putString("sender", contact);
                    b.putString("sendername", name);
                    b.putString("product_id", "");
                    b.putString("service_id", "");
                    b.putString("vehicle_id", mVehicle_Id);
                    Intent intent = new Intent(VehicleDetails.this, ChatActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
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
                Log.e("TAG", "dp : " + imgUrl);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(imgUrl));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(imgUrl, null, MimeTypeMap.getFileExtensionFromUrl(imgUrl));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "dp : " + imgUrl);
                DownloadManager manager = (DownloadManager) getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Log.e("TAG", "dp URL: " + imgUrl);
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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
