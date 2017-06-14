package autokatta.com.view;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.StoreInfo;
import autokatta.com.fragment.StoreProducts;
import autokatta.com.fragment.StoreServices;
import autokatta.com.fragment.StoreVehicles;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.my_store.MyStoreHome;
import autokatta.com.response.StoreResponse;
import retrofit2.Response;

public class StoreViewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact, store_id, storeOtherContact, mFolllowstr, mLikestr, storeRating, str;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    RatingBar storerating;
    String myContact;
    String precsrate = "";
    String preqwrate = "";
    String prefrrate = "";
    String preprrate = "";
    String pretmrate = "";
    String preoverall = "";
    String isDealing = "";
    Double storelattitude;
    Double storelongitude;
    ViewPager viewPager;
    TabLayout tabLayout;
    RatingBar csbar, qwbar, frbar, prbar, tmbar, overallbar;
    Float csrate = 0.0f, qwrate = 0.0f, frrate = 0.0f, prrate = 0.0f, tmrate = 0.0f, total = 0.0f, count = 0.0f;
    FloatingActionButton mCall, mLike, mFollow, mRate, mGoogleMap, mAdd, mAutoshare, mShare, mTeamProduct, mTeamServices, mTeamVehicle;
    MyStoreHome mMyStoreHome;
    StoreInfo storeInfo;
    StoreProducts storeProducts;
    StoreServices storeServices;
    StoreVehicles storeVehicles;
    ApiCall mApiCall;
    CoordinatorLayout mLayout;
    String storeName = "", storeImage = "", storeType = "", storeWebsite = "", storeTiming = "", storeLocation = "", storeWorkingDays = "",
            storeLikeCount, storeFollowCount, strDetailsShare = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_store_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent().setAction("Already created");
        mApiCall = new ApiCall(StoreViewActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mLayout = (CoordinatorLayout) findViewById(R.id.store_coordinate);
                    menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
//                    mCall = (FloatingActionButton) findViewById(R.id.call_c);
//                    mLike = (FloatingActionButton) findViewById(R.id.like_l);
//                    mFollow = (FloatingActionButton) findViewById(R.id.follow_f);
//                    mRate = (FloatingActionButton) findViewById(R.id.rate);
                    mAdd = (FloatingActionButton) findViewById(R.id.add);
                    mTeamProduct = (FloatingActionButton) findViewById(R.id.add_product_team);
                    mTeamServices = (FloatingActionButton) findViewById(R.id.add_services_team);
                    mTeamVehicle = (FloatingActionButton) findViewById(R.id.add_vehicle_team);
                    mGoogleMap = (FloatingActionButton) findViewById(R.id.gotoMap);
                    storerating = (RatingBar) findViewById(R.id.store_rating);
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    collapsingToolbar.setTitle(" ");
                    mOtherPicture = (ImageView) findViewById(R.id.other_store_image);
                    viewPager = (ViewPager) findViewById(R.id.other_store_viewpager);
                    tabLayout = (TabLayout) findViewById(R.id.other_store_tabs);
                    mAutoshare = (FloatingActionButton) findViewById(R.id.autokatta_share);
                    mShare = (FloatingActionButton) findViewById(R.id.share);

                    mMyStoreHome = new MyStoreHome();
                    mMyStoreHome.setArguments(mBundle);
                    storeInfo = new StoreInfo();
                    storeInfo.setArguments(mBundle);
                    storeProducts = new StoreProducts();
                    storeProducts.setArguments(mBundle);
                    storeServices = new StoreServices();
                    storeServices.setArguments(mBundle);
                    storeVehicles = new StoreVehicles();
                    storeVehicles.setArguments(mBundle);

                    mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", "");

                    if (getIntent().getExtras() != null) {
                        store_id = getIntent().getExtras().getString("store_id");
                        storeOtherContact = getIntent().getExtras().getString("StoreContact");
                        str = getIntent().getExtras().getString("flow_tab_name");
                        Log.i("storeOtherContact", "->" + storeOtherContact);
                        getOtherStore(mLoginContact, store_id);
                    }

                    mBundle.putString("store_id", store_id);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_web);
                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                           /* if (storeOtherContact.equals(mOtherContact)) {
                                mTeamVehicle.setVisibility(View.GONE);
                                mTeamServices.setVisibility(View.GONE);
                                mTeamProduct.setVisibility(View.GONE);
                            }*/
                            if (tab.getPosition() == 2) {
                                if (storeOtherContact != null) {
                                    if (storeOtherContact.equals(mOtherContact)) {
                                        mTeamVehicle.setVisibility(View.GONE);
                                        mTeamServices.setVisibility(View.GONE);
                                        mTeamProduct.setVisibility(View.GONE);

                                    }
                                } else {
                                    mTeamVehicle.setVisibility(View.GONE);
                                    mTeamServices.setVisibility(View.GONE);
                                    mTeamProduct.setVisibility(View.VISIBLE);

                                }
                            } else if (tab.getPosition() == 3) {
                                if (storeOtherContact != null) {
                                    if (storeOtherContact.equals(mOtherContact)) {
                                        mTeamVehicle.setVisibility(View.GONE);
                                        mTeamServices.setVisibility(View.GONE);
                                        mTeamProduct.setVisibility(View.GONE);
                                        mShare.setVisibility(View.GONE);
                                        mAutoshare.setVisibility(View.GONE);
                                    }
                                } else {
                                    mTeamVehicle.setVisibility(View.GONE);
                                    mTeamServices.setVisibility(View.VISIBLE);
                                    mTeamProduct.setVisibility(View.GONE);
                                    mShare.setVisibility(View.GONE);
                                    mAutoshare.setVisibility(View.GONE);
                                }
                            } else if (tab.getPosition() == 4) {
                                if (storeOtherContact != null) {
                                    if (storeOtherContact.equals(mOtherContact)) {
                                        mTeamVehicle.setVisibility(View.GONE);
                                        mTeamServices.setVisibility(View.GONE);
                                        mTeamProduct.setVisibility(View.GONE);
                                        mShare.setVisibility(View.GONE);
                                        mAutoshare.setVisibility(View.GONE);
                                    }
                                } else {
                                    mTeamVehicle.setVisibility(View.VISIBLE);
                                    mTeamServices.setVisibility(View.GONE);
                                    mTeamProduct.setVisibility(View.GONE);
                                    mShare.setVisibility(View.GONE);
                                    mAutoshare.setVisibility(View.GONE);
                                }
                            } else {
                                mTeamVehicle.setVisibility(View.GONE);
                                mTeamServices.setVisibility(View.GONE);
                                mTeamProduct.setVisibility(View.GONE);
                              /*  mShare.setVisibility(View.GONE);
                                mAutoshare.setVisibility(View.GONE);*/
                            }
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });


                    if (str.equals("adminMore")) {
                        tabLayout.setScrollPosition(1, 0f, true);
                        viewPager.setCurrentItem(1);
                    } else if (str.equals("FromProduct")) {
                        tabLayout.setScrollPosition(2, 0f, true);
                        viewPager.setCurrentItem(2);
                    } else if (str.equals("FromService")) {
                        tabLayout.setScrollPosition(3, 0f, true);
                        viewPager.setCurrentItem(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        mCall.setOnClickListener(this);
//        mLike.setOnClickListener(this);
//        mFollow.setOnClickListener(this);
//        mRate.setOnClickListener(this);
        mGoogleMap.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mTeamProduct.setOnClickListener(this);
        mTeamServices.setOnClickListener(this);
        mTeamVehicle.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mAutoshare.setOnClickListener(this);


    }

    public void hideFloatingButton() {
        menuRed.hideMenu(true);

    }

    public void showFloatingButton() {
        menuRed.showMenu(true);

    }

    public void filterResult() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreViewActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_store_rate_layout, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alert.setTitle("Rate This Store");
        Button yes = (Button) convertView.findViewById(R.id.btnyes);
        Button no = (Button) convertView.findViewById(R.id.btnno);
        csbar = (RatingBar) convertView.findViewById(R.id.cs_rating);
        qwbar = (RatingBar) convertView.findViewById(R.id.qw_rating);
        frbar = (RatingBar) convertView.findViewById(R.id.fr_rating);
        prbar = (RatingBar) convertView.findViewById(R.id.pr_rating);
        tmbar = (RatingBar) convertView.findViewById(R.id.tm_rating);
        overallbar = (RatingBar) convertView.findViewById(R.id.overall_rating);

        if (!precsrate.equals("0")) {
            csbar.setRating(Float.parseFloat(precsrate));
        }
        if (!preqwrate.equals("0")) {
            qwbar.setRating(Float.parseFloat(preqwrate));
        }
        if (!prefrrate.equals("0")) {
            frbar.setRating(Float.parseFloat(prefrrate));
        }
        if (!preprrate.equals("0")) {
            prbar.setRating(Float.parseFloat(preprrate));
        }
        if (!pretmrate.equals("0")) {
            tmbar.setRating(Float.parseFloat(pretmrate));
        }
        if (!preoverall.equals("0")) {
            overallbar.setRating(Float.parseFloat(preoverall));
        }

        csbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                csrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        qwbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                qwrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        frbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                frrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        prbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                prrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        tmbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tmrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preoverall.equals("0")) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                }
                mApiCall.recommendStore(myContact, store_id);
                alert.dismiss();
            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preoverall.equals("0")) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                }
                alert.dismiss();

            }
        });

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    //calculate rating fuction
    public void calculate(Float r1, Float r2, Float r3, Float r4, Float r5) {
        total = r1 + r2 + r3 + r4 + r5;
        count = total / 5;
        overallbar.setRating(count);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mMyStoreHome, "");
        adapter.addFragment(storeInfo, "About");
        adapter.addFragment(storeProducts, "Products");
        adapter.addFragment(storeServices, "Services");
        adapter.addFragment(storeVehicles, "Vehicles");
        viewPager.setAdapter(adapter);
    }

    private void getOtherStore(String contact, String store_id) {
        mApiCall.getStoreData(contact, store_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.call_c:
//                call();
//                break;
//            case R.id.like_l:
//                if (mLikestr.equalsIgnoreCase("no")) {
//                    mApiCall.otherStoreLike(mLoginContact, mOtherContact, "2", store_id);
//                    menuRed.setClosedOnTouchOutside(true);
//                } else {
//                    mApiCall.otherStoreUnlike(mLoginContact, mOtherContact, "2", store_id);
//                    menuRed.setClosedOnTouchOutside(true);
//                }
//
//                break;
//            case R.id.follow_f:
//                if (mFolllowstr.equalsIgnoreCase("no")) {
//                    mApiCall.otherStoreFollow(mLoginContact, mOtherContact, "2", store_id);
//                    menuRed.setClosedOnTouchOutside(true);
//                } else {
//                    mApiCall.otherStoreUnFollow(mLoginContact, mOtherContact, "2", store_id);
//                    menuRed.setClosedOnTouchOutside(true);
//                }
//                break;
//            case R.id.rate:
//                filterResult();
//                menuRed.setClosedOnTouchOutside(true);
//                break;

            case R.id.gotoMap:
                drawMap(storelattitude, storelongitude);
                menuRed.setClosedOnTouchOutside(true);
                break;

            case R.id.add:
                showAddAlert();
                break;

            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                String imageFilePath = "", imagename = "";
                if (storeImage.equalsIgnoreCase("") || storeImage.equalsIgnoreCase(null) ||
                        storeImage.equalsIgnoreCase("null")) {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + storeImage;
                }
                Log.e("TAG", "img : " + imagename);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(imagename));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "img : " + imagename);

                DownloadManager manager = (DownloadManager) getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);
                Log.e("TAG", "img URL: " + imagename);
                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/store/main/" + store_id + "/" + myContact);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                startActivity(Intent.createChooser(intent, "Autokatta"));

                break;
            case R.id.autokatta_share:

                strDetailsShare = storeName + "=" + storeWebsite + "="
                        + storeTiming + "=" + storeWorkingDays + "="
                        + storeType + "=" + storeLocation + "="
                        + storeImage + "=" + storeRating + "="
                        + storeLikeCount + "=" + storeFollowCount;

                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", strDetailsShare).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_store_id", store_id).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                startActivity(new Intent(StoreViewActivity.this, ShareWithinAppActivity.class), options.toBundle());
                break;
        }

    }

    private void showAddAlert() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(StoreViewActivity.this);
        builderSingle.setIcon(R.drawable.ic_cart_plus);
        builderSingle.setTitle("Select One:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StoreViewActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Add Product");
        arrayAdapter.add("Add Service");
        arrayAdapter.add("Add Vehicle");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);

                if (strName != null) {
                    if (strName.equals("Add Product")) {
                        Intent intent = new Intent(StoreViewActivity.this, AddProductActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    } else if (strName.equals("Add Service")) {
                        Intent intent = new Intent(StoreViewActivity.this, AddServiceActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    } else if (strName.equals("Add Vehicle")) {
                        if (isDealing.equalsIgnoreCase("false")) {
                            Intent intent = new Intent(StoreViewActivity.this, VehicleUpload.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //Toast.makeText(getApplicationContext(), "new vehicle", Toast.LENGTH_SHORT).show();
                            alertVehicle();
                        }
                    }
                }

            }
        });
        builderSingle.show();


    }

    private void alertVehicle() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(StoreViewActivity.this);
        builderSingle.setIcon(R.drawable.ic_cart_plus);
        builderSingle.setTitle("Select Vehicle Type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StoreViewActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Used Vehicle");
        arrayAdapter.add("New Vehicle");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);

                if (strName != null) {
                    if (strName.equals("Used Vehicle")) {
                        //Toast.makeText(getApplicationContext(), "used vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StoreViewActivity.this, VehicleUpload.class);
                        startActivity(intent);
                        finish();

                    } else if (strName.equals("New Vehicle")) {
                        //Toast.makeText(getApplicationContext(), "new vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StoreViewActivity.this, VehicleUpload.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
        builderSingle.show();
    }

    private void drawMap(Double storelattitude, Double storelongitude) {
        double destinationLatitude = storelattitude;
        double destinationLongitude = storelongitude;

        String url = "http://maps.google.com/maps?f=d&daddr=" + destinationLatitude + "," + destinationLongitude + "&dirflg=d&layer=t";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                StoreResponse storeResponse = (StoreResponse) response.body();
                for (StoreResponse.Success success : storeResponse.getSuccess()) {
                    storeName = success.getName();
                    storeImage = success.getStoreImage();
                    storeWebsite = success.getWebsite();
                    storeTiming = success.getStoreOpenTime() + " " + success.getStoreCloseTime();
                    storeLocation = success.getLocation();
                    storeWorkingDays = success.getWorkingDays();
                    storeType = success.getStoreType();
                    storeLikeCount = success.getLikecount();
                    storeFollowCount = success.getFollowcount();

                    mOtherContact = success.getContact();
                    Log.i("dsafdsafdas", "->" + mOtherContact);
                    storeRating = success.getRating();
                    mLikestr = success.getLikestatus();
                    mFolllowstr = success.getFollowstatus();
                    preoverall = success.getRate();
                    precsrate = success.getRate1();
                    preqwrate = success.getRate2();
                    prefrrate = success.getRate3();
                    preprrate = success.getRate4();
                    pretmrate = success.getRate5();
                    storelattitude = success.getLatitude();
                    storelongitude = success.getLongitude();
                    isDealing = success.getIsDealing();
                }

                if (mOtherContact.equals(mLoginContact)) {
//                    mCall.setVisibility(View.GONE);
//                    mLike.setVisibility(View.GONE);
//                    mFollow.setVisibility(View.GONE);
//                    mRate.setVisibility(View.GONE);
                    mAdd.setVisibility(View.VISIBLE);
                }

                storerating.setRating(Float.parseFloat(storeRating));
                //  mBundle.putString("StoreContact", mOtherContact);

                String dp_path = "http://autokatta.com/mobile/store_profiles/" + storeImage;
                Glide.with(StoreViewActivity.this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                //collapsingToolbar.setTitle(storeName);


                /*
                App Bar layout on scrolled...
                 */
                AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbar.setTitle(storeName);
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                            isShow = false;
                        }
                    }
                });
//
//                if (mLikestr.equalsIgnoreCase("no")) {
//                    mLike.setLabelText("Like");
//                    mLike.setLabelTextColor(Color.WHITE);
//                    menuRed.setClosedOnTouchOutside(true);
//                } else {
//                    mLike.setLabelText("Liked");
//                    mLike.setLabelTextColor(Color.RED);
//                    menuRed.setClosedOnTouchOutside(true);
//                }
//                if (mFolllowstr.equalsIgnoreCase("no")) {
//                    mFollow.setLabelText("Follow");
//                    mFollow.setLabelTextColor(Color.WHITE);
//                    menuRed.setClosedOnTouchOutside(true);
//                } else {
//                    mFollow.setLabelText("Following");
//                    mFollow.setLabelTextColor(Color.RED);
//                    menuRed.setClosedOnTouchOutside(true);
//                }
            } else {
                Snackbar.make(mLayout, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.BLUE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.BLUE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Log.i("Check Class-"
                    , "StoreViewActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
//            if (str.equals("success_follow")) {
//                Snackbar.make(mLayout, "Following", Snackbar.LENGTH_SHORT).show();
//                mFollow.setLabelText("Following");
//                mFollow.setLabelTextColor(Color.RED);
//                mFolllowstr = "yes";
//            } else if (str.equals("success_unfollow")) {
//                mFollow.setLabelText("Follow");
//                mFollow.setLabelTextColor(Color.WHITE);
//                mFolllowstr = "no";
//            } else if (str.equals("success_like")) {
//                Snackbar.make(mLayout, "Liked", Snackbar.LENGTH_SHORT).show();
//                mLike.setLabelText("Liked");
//                mLike.setLabelTextColor(Color.RED);
//                mLikestr = "yes";
//            } else if (str.equals("success_unlike")) {
//                mLike.setLabelText("Like");
//                mLike.setLabelTextColor(Color.WHITE);
//                mLikestr = "no";
//            } else if (str.equals("success_rating_submitted")) {
//                Snackbar.make(mLayout, "Rating Submitted", Snackbar.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putString("store_id", store_id);
//                bundle.putString("StoreContact", storeOtherContact);
//                finish();
//                Intent intent = new Intent(StoreViewActivity.this, StoreViewActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//            } else if (str.equals("success_rating_updated")) {
//                Snackbar.make(mLayout, "SRating updated", Snackbar.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putString("store_id", store_id);
//                bundle.putString("StoreContact", storeOtherContact);
//                finish();
//                Intent intent = new Intent(StoreViewActivity.this, StoreViewActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            } else if (str.equals("success_recommended")) {
//                Snackbar.make(mLayout, "Store Recommended", Snackbar.LENGTH_SHORT).show();
//            }
        }
    }

    //Calling Functio
    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mOtherContact));
        System.out.println("calling started");
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
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

    /*@Override
    protected void onResume() {
        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            Intent intent = new Intent(this, StoreViewActivity.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else {
            getIntent().setAction(null);
        }
        super.onResume();
    }*/
}
