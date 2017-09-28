package autokatta.com.view;

import android.Manifest.permission;
import android.app.Dialog;
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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import autokatta.com.fragment.VehicleDetailsTwo;
import autokatta.com.fragment.VehicleDetails_Details;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.GetVehicleByIdResponse;
import retrofit2.Response;

public class VehicleDetails extends AppCompatActivity implements RequestNotifier, View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ImageView mVehiclePicture;
    CollapsingToolbarLayout collapsingToolbar;
    String name;
    FloatingActionMenu mFab;
    FloatingActionButton mLike, mCall, mShare, mChat, mReview;
    String Title, mPrice, mBrand, mModel, mYear, mRTO_City, mAddress, mRegistration, mSendImage, imgUrl;
    Double mKms;
    String contact, mLikestr, prefcontact, allDetails;
    ApiCall mApiCall;
    int mVehicle_Id;
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
        mShare = (FloatingActionButton) findViewById(R.id.share);
        mChat = (FloatingActionButton) findViewById(R.id.chat_c);
        mReview = (FloatingActionButton) findViewById(R.id.review);

        mApiCall = new ApiCall(VehicleDetails.this, this);
        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mChat.setOnClickListener(this);
        mReview.setOnClickListener(this);

        mChat.setLabelTextColor(Color.BLACK);
        mCall.setLabelTextColor(Color.BLACK);
        mShare.setLabelTextColor(Color.BLACK);
        mReview.setLabelTextColor(Color.BLACK);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    get Vehicle Data...
                     */
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit()
                            .putInt("vehicle_id", getIntent().getExtras().getInt("vehicle_id")).apply();
                    mVehicle_Id = getIntent().getExtras().getInt("vehicle_id");
                    Log.i("vehicle_id", "->" + mVehicle_Id);

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
    private void getVehicleData(int mVehicleId) {
        mApiCall.getVehicleById(prefcontact, mVehicleId);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new VehicleDetails_Details(), "DETAILS");
        adapter.addFragment(new VehicleDetailsTwo(), "VEHICLE DETAILS");
        //    adapter.addFragment(new VehicleDetailsSpecifications(), "SPECIFICATION");
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
                    if (datum.getStatus() == null) {
                        mLikestr = "no";
                    } else {
                        mLikestr = datum.getStatus();
                    }
                    Title = datum.getTitle();
                    mAddress = (String) datum.getLocationCity();
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
                        mLike.setLabelTextColor(Color.BLACK);
                        mFab.setClosedOnTouchOutside(true);
                    } else {
                        mLike.setLabelText("Liked");
                        mLike.setLabelTextColor(Color.BLUE);
                        mFab.setClosedOnTouchOutside(true);
                    }

                    if (contact.equals(prefcontact)) {
                        mLike.setVisibility(View.GONE);
                        mCall.setVisibility(View.GONE);
                        mChat.setVisibility(View.GONE);
                    }

                    Hash_file_maps = new HashMap<String, String>();
                    sliderLayout = (SliderLayout) findViewById(R.id.slider);
                    String dp_path = getString(R.string.base_image_url);// + dp;
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
                for (final String name : Hash_file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(VehicleDetails.this);
                    textSliderView
                            /*.description(name)*/
                            .image(Hash_file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle b = new Bundle();
                                    b.putString("image", Hash_file_maps.get(name));
                                    Intent intent = new Intent(VehicleDetails.this, FullImageActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                }
                            });
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);
                    sliderLayout.addSlider(textSliderView);
                }
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setDuration(4000);
                sliderLayout.addOnPageChangeListener(this);
                collapsingToolbar.setTitle(Title);
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    private void getChatEnquiryStatus(String prefcontact, String contact, int mVehicle_id) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getChatEnquiryStatus(prefcontact, contact, 0, 0, mVehicle_id);
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
                mLike.setLabelTextColor(Color.BLUE);
                mLikestr = "yes";
            } else if (str.equals("success_unlike")) {
                CustomToast.customToast(getApplicationContext(), " UnLiked Successfully");
                mLike.setLabelText("Like");
                mLike.setLabelTextColor(Color.BLACK);
                mLikestr = "no";
            } else if (str.equals("success_message_saved")) {
                CustomToast.customToast(getApplicationContext(), "Offer Sent");
            } else if (str.contains("yes")) {
                mChat.setLabelText("Chat");
            } else if (str.contains("no")) {
                mChat.setLabelText("Send Offer");

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
                    mApiCall.Like(prefcontact, contact, "4", 0, 0, mVehicle_Id, 0, 0, 0, 0);
                    mFab.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.UnLike(prefcontact, contact, "4", 0, 0, mVehicle_Id, 0, 0, 0, 0);
                    mFab.setClosedOnTouchOutside(true);
                }
                break;
            case R.id.chat_c:

                if (mChat.getLabelText().equalsIgnoreCase("send Offer")) {

                    final Dialog openDialog = new Dialog(this);
                    openDialog.setContentView(R.layout.give_offer);
                    openDialog.setTitle("Fill Form For Offer");
                    final EditText offerprice = (EditText) openDialog.findViewById(R.id.txtofferprice);
                    final EditText paymentmode = (EditText) openDialog.findViewById(R.id.paymentmode);
                    final EditText description = (EditText) openDialog.findViewById(R.id.description);

                    Button sendQuotation = (Button) openDialog.findViewById(R.id.btnSend);

                    sendQuotation.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String strprice = offerprice.getText().toString();
                            String strpaymentmode = paymentmode.getText().toString();
                            String strdescription = description.getText().toString();

                            if (strprice.equalsIgnoreCase("")) {
                                CustomToast.customToast(VehicleDetails.this, "please enter give offer price");
                            } else if (strpaymentmode.equalsIgnoreCase("")) {
                                CustomToast.customToast(VehicleDetails.this, "please enter Payment mode");
                            } else if (strdescription.equalsIgnoreCase("")) {
                                CustomToast.customToast(VehicleDetails.this, "please enter Description");
                            } else {
                                ApiCall mpApicall = new ApiCall(VehicleDetails.this, VehicleDetails.this);
                                mpApicall.sendChatMessage(prefcontact, contact, "Offer Price-" + strprice + "\n" +
                                        "Payment Mode-" + strpaymentmode + "\n" +
                                        "Description-" + strdescription, "", 0, 0, mVehicle_Id);
                                openDialog.dismiss();
                            }
                        }
                    });
                    openDialog.show();

                } else {
                    Bundle b = new Bundle();
                    b.putString("sender", contact);
                    b.putString("sendername", name);
                    b.putInt("product_id", 0);
                    b.putInt("service_id", 0);
                    b.putInt("vehicle_id", mVehicle_Id);
                    Intent intent = new Intent(VehicleDetails.this, ChatActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                break;
            case R.id.share:
                //
                PopupMenu mPopupMenu = new PopupMenu(this, mShare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:
                                allDetails = Title + "=" +
                                        mPrice + "=" +
                                        mBrand + "=" +
                                        mModel + "=" +
                                        mYear + "=" +
                                        mKms + "=" +
                                        mRTO_City + "=" +
                                        mAddress + "=" +
                                        mRegistration + "=" +
                                        mSendImage + "=" +
                                        prefcontact + "=" +
                                        "0";

                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allDetails).apply();
                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_vehicle_id", mVehicle_Id).apply();
                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "vehicle").apply();

                                Intent i = new Intent(getApplicationContext(), ShareWithinAppActivity.class);
                                startActivity(i);
                                finish();
                                break;

                            case R.id.other:
                                String imageFilePath;
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                if (mSendImage.equalsIgnoreCase("") || mSendImage.equalsIgnoreCase(null)) {
                                    imgUrl = getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imgUrl = getString(R.string.base_image_url) + mSendImage;
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

                                String allGroupVehicleDetails =
                                        "Vehicle Title : " + Title + "\n" +
                                                "Vehicle Price : " + mPrice + "\n" +
                                                "Vehicle Brand : " + mBrand + "\n" +
                                                "Vehicle Model : " + mModel + "\n" +
                                                "Vehicle Manufacturing Year : " + mYear + "\n" +
                                                "Vehicle Running (Km/Hr) : " + mKms + "\n" +
                                                "RTO City : " + mRTO_City + "\n" +
                                                "Vehicle Location City : " + mAddress + "\n" +
                                                "Vehicle Registration Number : " + mRegistration + "\n" +
                                                "Contact : " + prefcontact;

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + mVehicle_Id + "/" + prefcontact
                                        + "\n" + "\n" + allGroupVehicleDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                startActivity(Intent.createChooser(intent, "Autokatta"));
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu

                break;

            case R.id.review:
                Intent intent = new Intent(VehicleDetails.this, ReviewActivity.class);
                intent.putExtra("vehicle_id", mVehicle_Id);
                intent.putExtra("contact", contact);
                startActivity(intent);
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
