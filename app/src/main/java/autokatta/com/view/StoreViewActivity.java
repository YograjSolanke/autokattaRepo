package autokatta.com.view;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.AdminCallContactAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.StoreInfo;
import autokatta.com.fragment.StoreProducts;
import autokatta.com.fragment.StoreServices;
import autokatta.com.fragment.StoreVehicles;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.model.LikeUnlike;
import autokatta.com.my_store.MyStoreHome;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.response.StoreResponse;
import retrofit2.Response;

public class StoreViewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    String mOtherContact;
    ArrayList<String> storeAdmins = new ArrayList<>();
    String storeOtherContact;
    String mFolllowstr;
    String mLikestr;
    int storeRating;
    String str;
    int store_id;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    RatingBar storerating;
    String myContact;
    int preqwrate = 0;
    int prefrrate = 0;
    int preprrate = 0;
    int pretmrate = 0;
    int precsrate = 0;
    int preoverall = 0;
    String isDealing = "";
    String storelattitude;
    String storelongitude;
    RatingBar csbar, qwbar, frbar, prbar, tmbar, overallbar;
    Float csrate = 0.0f, qwrate = 0.0f, frrate = 0.0f, prrate = 0.0f, tmrate = 0.0f, total = 0.0f, count = 0.0f;
    FloatingActionButton mGoogleMap, mAdd, mShare, mTeamProduct, mTeamServices, mTeamVehicle;
    MyStoreHome mMyStoreHome;
    StoreInfo storeInfo;
    StoreProducts storeProducts;
    StoreServices storeServices;
    StoreVehicles storeVehicles;
    ApiCall mApiCall;
    CoordinatorLayout mLayout;
    String storeName = "";
    String storeImage = "";
    String storeCoverImage = "";
    String storeType = "";
    String storeWebsite = "";
    String storeTiming = "";
    String storeLocation = "";
    String storeWorkingDays = "";
    int storeLikeCount;
    int storeFollowCount;
    String strDetailsShare = "";
    private ProgressDialog dialog;
    AdminCallContactAdapter adapter;
    LinearLayout mLinear, mAbout, mProducts, mService, mVehicle;
    ImageView mBannerImage, mStoreImage;
    TextView mStoreName, mWebSite, mLocation, mLikeCount, mFollowCount;
    ImageView mCall;
    LinearLayout otherViewLayout;
    ImageView mLike, mUnlike, mRating, mFollow, mUnFollow, mMap, mAddReview;
    private int likecountint, followcountint;
    LikeUnlike likeUnlike = new LikeUnlike();

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

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

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
                    menuRed.setClosedOnTouchOutside(true);
                    mAdd = (FloatingActionButton) findViewById(R.id.add);
                    mTeamProduct = (FloatingActionButton) findViewById(R.id.add_product_team);
                    mTeamServices = (FloatingActionButton) findViewById(R.id.add_services_team);
                    mTeamVehicle = (FloatingActionButton) findViewById(R.id.add_vehicle_team);
                    mGoogleMap = (FloatingActionButton) findViewById(R.id.gotoMap);
                    storerating = (RatingBar) findViewById(R.id.store_rating);
                    mShare = (FloatingActionButton) findViewById(R.id.share);

                    mLinear = (LinearLayout) findViewById(R.id.my_home);
                    mLike = (ImageView) findViewById(R.id.like);
                    mUnlike = (ImageView) findViewById(R.id.unlike);
                    mRating = (ImageView) findViewById(R.id.rating);
                    mFollow = (ImageView) findViewById(R.id.unfollow);
                    mUnFollow = (ImageView) findViewById(R.id.follow);
                    mMap = (ImageView) findViewById(R.id.map);
                    mAddReview = (ImageView) findViewById(R.id.add_review);
                    otherViewLayout = (LinearLayout) findViewById(R.id.actionLayout);
                    mAbout = (LinearLayout) findViewById(R.id.about);
                    mProducts = (LinearLayout) findViewById(R.id.product);
                    mService = (LinearLayout) findViewById(R.id.service);
                    mVehicle = (LinearLayout) findViewById(R.id.vehicle);

                    mBannerImage = (ImageView) findViewById(R.id.other_store_image);
                    mStoreImage = (ImageView) findViewById(R.id.other_store_images);

                    mStoreName = (TextView) findViewById(R.id.store_name);
                    mWebSite = (TextView) findViewById(R.id.web);
                    mLocation = (TextView) findViewById(R.id.location);
                    mLikeCount = (TextView) findViewById(R.id.likeCount);
                    mFollowCount = (TextView) findViewById(R.id.followCount);
                    mCall = (ImageView) findViewById(R.id.call);




                    mShare.setLabelTextColor(Color.BLACK);
                    mTeamProduct.setLabelTextColor(Color.BLACK);
                    mTeamServices.setLabelTextColor(Color.BLACK);
                    mTeamVehicle.setLabelTextColor(Color.BLACK);
                    mGoogleMap.setLabelTextColor(Color.BLACK);
                    mAdd.setLabelTextColor(Color.BLACK);

//                    mMyStoreHome = new MyStoreHome();
//
//                    mMyStoreHome.setArguments(mBundle);
//                    storeInfo = new StoreInfo();
//                    storeInfo.setArguments(mBundle);
//                    storeProducts = new StoreProducts();
//                    storeProducts.setArguments(mBundle);
//                    storeServices = new StoreServices();
//                    storeServices.setArguments(mBundle);
//                    storeVehicles = new StoreVehicles();
//                    storeVehicles.setArguments(mBundle);

                    if (getIntent().getExtras() != null) {
                        store_id = getIntent().getExtras().getInt("store_id");
                        storeOtherContact = getIntent().getExtras().getString("StoreContact");
                        str = getIntent().getExtras().getString("flow_tab_name");
                        Log.i("storeOtherContact", "->" + store_id);
                        getOtherStore(myContact, store_id);
                    }

                    mBundle.putInt("store_id", store_id);


//                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                        @Override
//                        public void onTabSelected(TabLayout.Tab tab) {
//                           /* if (storeOtherContact.equals(mOtherContact)) {
//                                mTeamVehicle.setVisibility(View.GONE);
//                                mTeamServices.setVisibility(View.GONE);
//                                mTeamProduct.setVisibility(View.GONE);
//                            }*/
//                           /* Products tab */
//                            if (tab.getPosition() == 2) {
//
//                                if (myContact.equals(mOtherContact)) {
//                                    mTeamVehicle.setVisibility(View.GONE);
//                                    mTeamServices.setVisibility(View.GONE);
//                                    mTeamProduct.setVisibility(View.VISIBLE);
//
//                                } else {
//                                    mTeamVehicle.setVisibility(View.GONE);
//                                    mTeamServices.setVisibility(View.GONE);
//                                    mTeamProduct.setVisibility(View.GONE);
//                                }
//                            }
//                            /* Services tab */
//                            else if (tab.getPosition() == 3) {
//
//                                if (myContact.equals(mOtherContact)) {
//                                    mTeamVehicle.setVisibility(View.GONE);
//                                    mTeamServices.setVisibility(View.VISIBLE);
//                                    mTeamProduct.setVisibility(View.GONE);
//                                    /*mShare.setVisibility(View.GONE);
//                                    mAutoshare.setVisibility(View.GONE);*/
//                                } else {
//                                    mTeamVehicle.setVisibility(View.GONE);
//                                    mTeamServices.setVisibility(View.GONE);
//                                    mTeamProduct.setVisibility(View.GONE);
//                                    /*mShare.setVisibility(View.VISIBLE);
//                                    mAutoshare.setVisibility(View.VISIBLE);*/
//                                }
//                            }
//                            /* Vehicles tab */
//                            else if (tab.getPosition() == 4) {
//
//                                if (myContact.equals(mOtherContact)) {
//                                    mTeamVehicle.setVisibility(View.VISIBLE);
//                                    mTeamServices.setVisibility(View.GONE);
//                                    mTeamProduct.setVisibility(View.GONE);
//                                    /*mShare.setVisibility(View.GONE);
//                                    mAutoshare.setVisibility(View.GONE);*/
//                                } else {
//                                    mTeamVehicle.setVisibility(View.GONE);
//                                    mTeamServices.setVisibility(View.GONE);
//                                    mTeamProduct.setVisibility(View.GONE);
//                                   /* mShare.setVisibility(View.VISIBLE);
//                                    mAutoshare.setVisibility(View.VISIBLE);*/
//                                }
//                            }
//                            /* Other tabs */
//                            else {
//                                mTeamVehicle.setVisibility(View.GONE);
//                                mTeamServices.setVisibility(View.GONE);
//                                mTeamProduct.setVisibility(View.GONE);
//                                /*mShare.setVisibility(View.VISIBLE);
//                                mAutoshare.setVisibility(View.VISIBLE);*/
//                            }
//                        }
//
//                        @Override
//                        public void onTabUnselected(TabLayout.Tab tab) {
//
//                        }
//
//                        @Override
//                        public void onTabReselected(TabLayout.Tab tab) {
//
//                        }
//                    });
//
//
//                    if (str != null) {
//                        if (str.equals("adminMore")) {
//                            tabLayout.setScrollPosition(1, 0f, true);
//                            viewPager.setCurrentItem(1);
//                        } else if (str.equals("FromProduct")) {
//                            tabLayout.setScrollPosition(2, 0f, true);
//                            viewPager.setCurrentItem(2);
//                        } else if (str.equals("FromService")) {
//                            tabLayout.setScrollPosition(3, 0f, true);
//                            viewPager.setCurrentItem(3);
//                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mGoogleMap.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mTeamProduct.setOnClickListener(this);
        mTeamServices.setOnClickListener(this);
        mTeamVehicle.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mCall.setOnClickListener(this);
        mWebSite.setOnClickListener(this);
        mBannerImage.setOnClickListener(this);
        mStoreImage.setOnClickListener(this);
        mAddReview.setOnClickListener(this);
        mRating.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mUnlike.setOnClickListener(this);
        mFollow.setOnClickListener(this);
        mUnFollow.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mProducts.setOnClickListener(this);
        mService.setOnClickListener(this);
        mVehicle.setOnClickListener(this);
    }

    public void hideFloatingButton() {
        menuRed.hideMenu(true);

    }

    public void showFloatingButton() {
        menuRed.showMenu(true);

    }

    /*
    Rate Store...
     */
    public void filterResult() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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


        if ((precsrate != 0)) {
            csbar.setRating(precsrate);
            csrate = Float.parseFloat(String.valueOf(precsrate));
        }
        if (preqwrate != 0) {
            qwbar.setRating(preqwrate);
            qwrate = Float.parseFloat(String.valueOf(preqwrate));
        }
        if (prefrrate != 0) {
            frbar.setRating(prefrrate);
            frrate = Float.parseFloat(String.valueOf(prefrrate));
        }
        if (preprrate != 0) {
            prbar.setRating(preprrate);
            prrate = Float.parseFloat(String.valueOf(preprrate));
        }
        if (pretmrate != 0) {
            tmbar.setRating(pretmrate);
            tmrate = Float.parseFloat(String.valueOf(pretmrate));
        }
        if (preoverall != 0) {
            overallbar.setRating(preoverall);
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
                if (preoverall == 0) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(count),
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
                if (preoverall == 0) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(count),
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


    /*
Call Intent...
 */
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        System.out.println("calling started");
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
        }
    }

    private void getCallContactList() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.admin_contact_call_layout, null);
        dialogBuilder.setView(dialogView);

        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        AlertDialog alertDialog = dialogBuilder.create();

        adapter = new AdminCallContactAdapter(this, storeAdmins);
        recyclerView.setAdapter(adapter);
        alertDialog.show();

    }


    private void getOtherStore(String contact, int store_id) {
        dialog.show();
        mApiCall.getStoreData(contact, store_id);
        mApiCall.StoreAdmin(store_id);
    }
    @Override
    public void onClick(View view) {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
        Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.gotoMap:
                drawMap(storelattitude, storelongitude);
                menuRed.setClosedOnTouchOutside(true);
                break;

            case R.id.add:
                showAddAlert();
                break;

            case R.id.share:

                PopupMenu mPopupMenu = new PopupMenu(this, mShare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {


                            case R.id.autokatta:


                                String imageshare = "";
                                imageshare = getString(R.string.base_image_url) + storeImage;

                                imageshare = imageshare.replaceAll(" ", "%20");
                                System.out.println("image============" + imageshare);

                                strDetailsShare = storeName + "=" + storeWebsite + "="
                                        + storeTiming + "=" + storeWorkingDays + "="
                                        + storeType + "=" + storeLocation + "="
                                        + storeImage + "=" + String.valueOf(storeRating) + "="
                                        + storeLikeCount + "=" + storeFollowCount;


                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", strDetailsShare).apply();
                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_store_id", store_id).apply();
                                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "store").apply();
                                startActivity(new Intent(StoreViewActivity.this, ShareWithinAppActivity.class));
                                break;

                            case R.id.other:
                                String imageFilePath = "", imagename = "";
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                if (storeImage.equalsIgnoreCase("") || storeImage.equalsIgnoreCase(null) ||
                                        storeImage.equalsIgnoreCase("null")) {
                                    imagename = getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = getString(R.string.base_image_url) + storeImage;
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

                                strDetailsShare = storeName + "=" + storeWebsite + "="
                                        + storeTiming + "=" + storeWorkingDays + "="
                                        + storeType + "=" + storeLocation + "="
                                        + storeImage + "=" + String.valueOf(storeRating) + "="
                                        + storeLikeCount + "=" + storeFollowCount;


                                String allStoreDetailss = "Store name : " + storeName + "\n" +
                                        "Store type : " + storeType + "\n" +
                                        "Ratings : " + storeRating + "\n" +
                                        "Likes : " + storeLikeCount + "\n" +
                                        "Website : " + storeWebsite + "\n" +
                                        "Timing : " + storeTiming + "\n" +
                                        "Working Days : " + storeWorkingDays + "\n" +
                                        "Location : " + storeLocation;

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + store_id
                                        + "/" + mOtherContact
                                        + "\n" + "\n" + allStoreDetailss);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(Intent.createChooser(intent, "Autokatta"));
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu
                break;
            case R.id.add_product_team:
                CustomToast.customToast(getApplicationContext(), "Coming soon... please be connected for update..");
                break;

            case R.id.add_services_team:
                CustomToast.customToast(getApplicationContext(), "Coming soon... please be connected for update..");
                break;

            case R.id.add_vehicle_team:
                CustomToast.customToast(getApplicationContext(), "Coming soon... please be connected for update..");
                break;

            case R.id.call:
                if (storeAdmins.size() == 0)
                    call(mOtherContact);
                else
                    getCallContactList();

                break;

            case R.id.web:
                String website = mWebSite.getText().toString().trim();
                goToUrl(website);
                break;

            case R.id.other_store_image:
                String image;
                if (storeCoverImage.equals(""))
                    image = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = getString(R.string.base_image_url) + storeCoverImage;

                b.putString("image", image);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case R.id.other_store_images:


                String imageStore;
                if (storeImage.equals(""))
                    imageStore = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    imageStore = getString(R.string.base_image_url) + storeImage;

                b.putString("image", imageStore);
                intent.putExtras(b);
                startActivity(intent);
                break;

            case R.id.add_review:
                Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                intent2.putExtra("store_id", store_id);
                intent2.putExtra("contact", storeOtherContact);
                startActivity(intent2);
                break;

            case R.id.rating:
                filterResult();
                break;

            case R.id.like:

                mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);
                mApiCall.UnLike(myContact, mOtherContact, "2", store_id, 0, 0, 0, 0, 0, 0);
                likecountint = likeUnlike.getCount();
                likecountint = likecountint - 1;
                mLikeCount.setText(String.valueOf("Likes(" + String.valueOf(likecountint) + ")"));
                storeLikeCount = likecountint;
                likeUnlike.setCount(likecountint);
                break;
            case R.id.unlike:

                mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);
                mApiCall.Like(myContact, mOtherContact, "2", store_id, 0, 0, 0, 0, 0, 0);
                likecountint = likeUnlike.getCount();
                likecountint = likecountint + 1;
                mLikeCount.setText(String.valueOf("Likes(" + String.valueOf(likecountint) + ")"));
                storeLikeCount = likecountint;
                likeUnlike.setCount(likecountint);
                break;

            case R.id.unfollow:
                mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);
                mApiCall.Follow(myContact, mOtherContact, "2", store_id, 0, 0, 0);
                followcountint = likeUnlike.getFollowCount();
                followcountint = followcountint + 1;
                mFollowCount.setText(String.valueOf("Followers(" + String.valueOf(followcountint) + ")"));
                storeFollowCount = followcountint;
                likeUnlike.setFollowCount(followcountint);
                break;
            case R.id.follow:
                mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);
                mApiCall.UnFollow(myContact, mOtherContact, "2", store_id, 0, 0, 0);
                followcountint = likeUnlike.getFollowCount();
                followcountint--;
                mFollowCount.setText(String.valueOf("Followers(" + String.valueOf(followcountint) + ")"));
                likeUnlike.setFollowCount(followcountint);
                break;

            case R.id.about:

                b.putInt("store_id", store_id);
                Intent intent1 = new Intent(StoreViewActivity.this, StoreInfoActivity.class);
                intent1.putExtras(b);
                startActivity(intent1, options.toBundle());
                break;

            case R.id.product:
                b.putInt("store_id", store_id);
                Intent intentproduct = new Intent(StoreViewActivity.this, StoreProductActivity.class);
                intentproduct.putExtras(b);
                startActivity(intentproduct, options.toBundle());
                break;

            case R.id.service:
                b.putInt("store_id", store_id);
                Intent intentservice = new Intent(StoreViewActivity.this, StoreServiceActivity.class);
                intentservice.putExtras(b);
                startActivity(intentservice, options.toBundle());
                break;

            case R.id.vehicle:

                b.putInt("store_id", store_id);
                Intent intentvehicle = new Intent(StoreViewActivity.this, StoreVehicleActivity.class);
                intentvehicle.putExtras(b);
                startActivity(intentvehicle, options.toBundle());
                break;

        }

    }

    private void goToUrl(String url) {
        String fullUrl = "http://" + url;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(fullUrl));
        startActivity(intent);
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
                bundle.putInt("store_id", store_id);

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

    private void drawMap(String storelattitude, String storelongitude) {
        double destinationLatitude = Double.parseDouble(storelattitude);
        double destinationLongitude = Double.parseDouble(storelongitude);

        String url = "http://maps.google.com/maps?f=d&daddr=" + destinationLatitude + "," + destinationLongitude + "&dirflg=d&layer=t";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                if (response.body() instanceof StoreResponse) {
                    dialog.dismiss();
                    StoreResponse storeResponse = (StoreResponse) response.body();
                    for (StoreResponse.Success success : storeResponse.getSuccess()) {
                        storeName = success.getName();
                        storeImage = success.getStoreImage();
                        storeOtherContact = success.getContact();
                        storeCoverImage = success.getCoverImage();
                        storeWebsite = success.getWebsite();
                        storeTiming = success.getStoreOpenTime() + " " + success.getStoreCloseTime();
                        storeLocation = success.getLocation();
                        storeWorkingDays = success.getWorkingDays();
                        storeType = success.getStoreType();
                        storeLikeCount = success.getLikecount();
                        likeUnlike.setCount(success.getLikecount());
                        storeFollowCount = success.getFollowcount();
                        likeUnlike.setFollowCount(success.getFollowcount());

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
                        isDealing = success.getDealingWith();
                    }

                    if (mOtherContact.equals(myContact)) {
                        mAdd.setVisibility(View.VISIBLE);
                        otherViewLayout.setVisibility(View.GONE);
                        mCall.setVisibility(View.GONE);
                    }

                    storerating.setRating(storeRating);
                    if (!storeImage.equals("")) {
                        String dp_path = getString(R.string.base_image_url) + storeImage;
                        Glide.with(this)
                                .load(dp_path)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.logo)
                                .into(mStoreImage);
                    } else {
                        mStoreImage.setImageResource(R.drawable.logo);
                    }
                    if (!storeCoverImage.equals("")) {
                        String dp_paths = getString(R.string.base_image_url) + storeCoverImage;
                        Glide.with(this)
                                .load(dp_paths)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.logo)
                                .into(mBannerImage);
                    } else {
                        mBannerImage.setImageResource(R.drawable.logo);
                    }

                    mStoreName.setText(storeName);
                    mLocation.setText(storeLocation);
                    mWebSite.setText(storeWebsite);
                    mFollowCount.setText("Followers(" + String.valueOf(storeFollowCount) + ")");
                    mLikeCount.setText("Likes(" + String.valueOf(storeLikeCount) + ")");


                    if (mLikestr.equalsIgnoreCase("no")) {
                        mLike.setVisibility(View.GONE);
                        mUnlike.setVisibility(View.VISIBLE);
                    } else {
                        mLike.setVisibility(View.VISIBLE);
                        mUnlike.setVisibility(View.GONE);
                    }
                    if (mFolllowstr.equalsIgnoreCase("no")) {
                        mFollow.setVisibility(View.VISIBLE);
                        mUnFollow.setVisibility(View.GONE);
                    } else {
                        mFollow.setVisibility(View.GONE);
                        mUnFollow.setVisibility(View.VISIBLE);
                    }

                } else if (response.body() instanceof StoreOldAdminResponse) {
                    StoreOldAdminResponse adminResponse = (StoreOldAdminResponse) response.body();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        //8007855589-dealer-RUTU
                        storeAdmins.add(myContact + "-" + "Owner" + "-" + "Owner");
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {

                            storeAdmins.add(success.getAdmin());

                        }

                        System.out.println("alreadyadmin=" + storeAdmins.size());

                    }


                }

            } else {
                //  CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }


    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreViewActivity");
            error.printStackTrace();
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
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(getApplicationContext(), "Following");
                    mFolllowstr = "yes";
                    break;
                case "success_unfollow":
                    //if (mActivity != null)
                    CustomToast.customToast(getApplicationContext(), "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);*/
                    mFolllowstr = "no";
                    break;
                case "success_like":
                    //if (mActivity != null)
                    CustomToast.customToast(getApplicationContext(), "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                    mLikestr = "yes";
                    break;
                case "success_unlike":
                    //if (mActivity != null)
                    CustomToast.customToast(getApplicationContext(), "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    mLikestr = "no";
                    break;
                case "success_rating_submitted": {

                    CustomToast.customToast(StoreViewActivity.this, "Rating Submitted");
                    Bundle bundle = new Bundle();
                    bundle.putInt("store_id", store_id);
                    bundle.putString("StoreContact", storeOtherContact);
                    finish();
                    Intent intent = new Intent(StoreViewActivity.this, StoreViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                }
                case "success_rating_updated": {
                    //if (mActivity != null)
                    CustomToast.customToast(StoreViewActivity.this, "Rating Updated");
                    Bundle bundle = new Bundle();
                    bundle.putInt("store_id", store_id);
                    bundle.putString("StoreContact", storeOtherContact);
                    finish();
                    Intent intent = new Intent(StoreViewActivity.this, StoreViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case "success_recommended":
                    //if (mActivity != null)
                    CustomToast.customToast(StoreViewActivity.this, "Store recommended");
                    break;
            }
        }
    }
}
