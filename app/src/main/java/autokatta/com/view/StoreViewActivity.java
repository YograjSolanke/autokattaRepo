package autokatta.com.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.StoreInfo;
import autokatta.com.fragment.StoreProducts;
import autokatta.com.fragment.StoreServices;
import autokatta.com.fragment.StoreVehicles;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreResponse;
import retrofit2.Response;

public class StoreViewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact, store_id,mFolllowstr,mLikestr;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    RatingBar storerating;
    String myContact;
    String overAllRate;
    String precsrate = "";
    String preqwrate = "";
    String prefrrate = "";
    String preprrate = "";
    String pretmrate = "";
    String preoverall = "";
    Double storelattitude;
    Double storelongitude;
    ViewPager viewPager;
    TabLayout tabLayout;
    RatingBar csbar, qwbar, frbar, prbar, tmbar, overallbar;
    Float csrate = 0.0f, qwrate = 0.0f, frrate = 0.0f, prrate = 0.0f, tmrate = 0.0f, total = 0.0f, count = 0.0f;
    FloatingActionButton mCall, mLike, mFollow, mRate, mGoogleMap, mAdd;
    StoreInfo storeInfo;
    StoreProducts storeProducts;
    StoreServices storeServices;
    StoreVehicles storeVehicles;
    ApiCall mApiCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_store_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApiCall = new ApiCall(StoreViewActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);
        mRate = (FloatingActionButton) findViewById(R.id.rate);
        mAdd = (FloatingActionButton) findViewById(R.id.add);
        mGoogleMap = (FloatingActionButton) findViewById(R.id.gotoMap);
        storerating = (RatingBar) findViewById(R.id.store_rating);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mOtherPicture = (ImageView) findViewById(R.id.other_store_image);
        viewPager = (ViewPager) findViewById(R.id.other_store_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.other_store_tabs);
        storerating.setRating(2);

        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);
        mRate.setOnClickListener(this);
        mGoogleMap.setOnClickListener(this);
        mAdd.setOnClickListener(this);



        storeInfo = new StoreInfo();
        storeInfo.setArguments(mBundle);
        storeProducts = new StoreProducts();
        storeProducts.setArguments(mBundle);
        storeServices = new StoreServices();
        storeServices.setArguments(mBundle);
        storeVehicles = new StoreVehicles();
        storeVehicles.setArguments(mBundle);

        /*
        Get Bundle Data...
         */

        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getIntent().getExtras() != null) {
                        store_id = getIntent().getExtras().getString("store_id");
                        getOtherStore(mLoginContact, store_id);


                    }

                    mBundle.putString("store_id", store_id);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }


                    tabLayout.setupWithViewPager(viewPager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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
                    mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                            String.valueOf(csrate),
                            String.valueOf(qwrate),
                            String.valueOf(frrate),
                            String.valueOf(prrate),
                            String.valueOf(tmrate),
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

                mApiCall.recommendStore(myContact, store_id);
                alert.dismiss();

            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (preoverall.equals("0")) {
                    mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                            String.valueOf(csrate),
                            String.valueOf(qwrate),
                            String.valueOf(frrate),
                            String.valueOf(prrate),
                            String.valueOf(tmrate),
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
            case R.id.call_c:
                call();
                break;
            case R.id.like_l:
                if (mLikestr.equalsIgnoreCase("no")) {
                    mApiCall.otherStoreLike(mLoginContact,mOtherContact, "2",store_id);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.otherStoreUnlike(mLoginContact,mOtherContact, "2",store_id);
                    menuRed.setClosedOnTouchOutside(true);
                }

                break;
            case R.id.follow_f:
                if (mFolllowstr.equalsIgnoreCase("no")) {
                    mApiCall.otherStoreFollow(mLoginContact,mOtherContact, "2",store_id);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.otherStoreUnFollow(mLoginContact,mOtherContact, "2",store_id);
                    menuRed.setClosedOnTouchOutside(true);
                }
                break;
            case R.id.rate:
                filterResult();
                menuRed.setClosedOnTouchOutside(true);
                break;

            case R.id.gotoMap:

                drawMap(storelattitude, storelongitude);

                menuRed.setClosedOnTouchOutside(true);
                break;

            case R.id.add:

                showAddAlert();

                break;
        }

    }

    private void showAddAlert() {


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(StoreViewActivity.this);
        builderSingle.setIcon(R.drawable.ic_cart_plus);
        builderSingle.setTitle("Select One Name:-");

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
                String userName = "", dp = "";
                StoreResponse storeResponse = (StoreResponse) response.body();
                for (StoreResponse.Success success : storeResponse.getSuccess()) {
                    userName = success.getName();
                    dp = success.getStoreImage();
                    mOtherContact = success.getContact();
                    mLikestr=success.getLikestatus();
                    mFolllowstr=success.getFollowstatus();
                    preoverall = success.getRate();
                    precsrate = success.getRate1();
                    preqwrate = success.getRate2();
                    prefrrate = success.getRate3();
                    preprrate = success.getRate4();
                    pretmrate = success.getRate5();
                    storelattitude = success.getLatitude();
                    storelongitude = success.getLongitude();
                }

                if (mOtherContact.contains(mLoginContact)) {
                    mCall.setVisibility(View.GONE);
                    mLike.setVisibility(View.GONE);
                    mFollow.setVisibility(View.GONE);
                    mRate.setVisibility(View.GONE);
                }


                mBundle.putString("StoreContact", mOtherContact);



                String dp_path = "http://autokatta.com/mobile/store_profiles/" + dp;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                collapsingToolbar.setTitle(userName);

                if (mLikestr.equalsIgnoreCase("no")) {
                    mLike.setLabelText("Like");
                    mLike.setLabelTextColor(Color.WHITE);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mLike.setLabelText("Liked");
                    mLike.setLabelTextColor(Color.RED);
                    menuRed.setClosedOnTouchOutside(true);
                }
                if (mFolllowstr.equalsIgnoreCase("no")) {
                    mFollow.setLabelText("Follow");
                    mFollow.setLabelTextColor(Color.WHITE);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mFollow.setLabelText("Following");
                    mFollow.setLabelTextColor(Color.RED);
                    menuRed.setClosedOnTouchOutside(true);
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
            if (str.equals("success_follow")) {
                CustomToast.customToast(getApplicationContext(), " Following Successfully");
                mFollow.setLabelText("Following");
                mFollow.setLabelTextColor(Color.RED);
                mFolllowstr="yes";
            } else if (str.equals("success_unfollow")) {
                CustomToast.customToast(getApplicationContext(), " UnFollowed Successfully");
                mFollow.setLabelText("Follow");
                mFollow.setLabelTextColor(Color.WHITE);
                mFolllowstr="no";
            } else if (str.equals("success_like")) {
                CustomToast.customToast(getApplicationContext(), " Liked Successfully");
                mLike.setLabelText("Liked");
                mLike.setLabelTextColor(Color.RED);
                mLikestr="yes";
            } else if (str.equals("success_unlike")) {
                CustomToast.customToast(getApplicationContext(), " UnLiked Successfully");
                mLike.setLabelText("Like");
                mLike.setLabelTextColor(Color.WHITE);
                mLikestr="no";
            } else if (str.equals("success_rating_submitted")) {
                CustomToast.customToast(getApplicationContext(), "Rating Submitted");
            } else if (str.equals("success_rating_updated")) {
                CustomToast.customToast(getApplicationContext(), "Rating updated");
            } else if (str.equals("success_recommended")) {
                CustomToast.customToast(getApplicationContext(), "Store Recommended");
            }
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
}
