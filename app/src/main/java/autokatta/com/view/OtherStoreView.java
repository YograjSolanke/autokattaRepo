package autokatta.com.view;

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

public class OtherStoreView extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact, store_id,mFolllowstr,mLikestr;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    RatingBar storerating;
    String overAllRate;
    RatingBar csbar, qwbar, frbar, prbar, tmbar, overallbar;
    Float csrate = 0.0f, qwrate = 0.0f, frrate = 0.0f, prrate = 0.0f, tmrate = 0.0f, total = 0.0f, count = 0.0f;
    FloatingActionButton mCall, mLike, mFollow, mRate;
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

        mApiCall   = new ApiCall(OtherStoreView.this, this);

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);
        mRate = (FloatingActionButton) findViewById(R.id.rate);
        storerating = (RatingBar) findViewById(R.id.store_rating);
        storerating.setRating(2);

        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);
        mRate.setOnClickListener(this);


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
                        mOtherContact = getIntent().getExtras().getString("StoreContact");
                        store_id = getIntent().getExtras().getString("store_id");
                    }
                    mBundle.putString("store_id", store_id);
                    mBundle.putString("StoreContact", mOtherContact);
                    getOtherStore(mOtherContact, store_id);

                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    mOtherPicture = (ImageView) findViewById(R.id.other_store_image);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.other_store_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.other_store_tabs);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void filterResult() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OtherStoreView.this);
        LayoutInflater inflater = getLayoutInflater();

        View convertView = inflater.inflate(R.layout.custom_store_rate_layout, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alertDialog.setTitle("Rate This Store");
        Button yes = (Button) convertView.findViewById(R.id.btnyes);
        Button no = (Button) convertView.findViewById(R.id.btnno);
        csbar = (RatingBar) convertView.findViewById(R.id.cs_rating);
        qwbar = (RatingBar) convertView.findViewById(R.id.qw_rating);
        frbar = (RatingBar) convertView.findViewById(R.id.fr_rating);
        prbar = (RatingBar) convertView.findViewById(R.id.pr_rating);
        tmbar = (RatingBar) convertView.findViewById(R.id.tm_rating);
        overallbar = (RatingBar) convertView.findViewById(R.id.overall_rating);


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

                if (overAllRate.equals("0")) {
                    //  sendstorerating();
                } else {
                    //  sendupdatedstorerating();
                }

                // sendrecommendtask();

            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        }

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
                    mLikestr=success.getLikestatus();
                    mFolllowstr=success.getFollowstatus();
                    overAllRate = success.getRate();
                }
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
