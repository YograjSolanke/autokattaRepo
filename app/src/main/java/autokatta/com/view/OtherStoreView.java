package autokatta.com.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
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
    String mOtherContact, mLoginContact, store_id;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    FloatingActionButton mCall, mLike, mFollow;
    StoreInfo storeInfo;
    StoreProducts storeProducts;
    StoreServices storeServices;
    StoreVehicles storeVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_store_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);

        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);


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

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(storeInfo, "About");
        adapter.addFragment(storeProducts, "Products");
        adapter.addFragment(storeServices, "Services");
        adapter.addFragment(storeVehicles, "Vehicles");
        viewPager.setAdapter(adapter);
    }

    private void getOtherStore(String contact, String store_id) {
        ApiCall mApiCall = new ApiCall(OtherStoreView.this, this);
        mApiCall.getStoreData(contact, store_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_c:
                CustomToast.customToast(getApplicationContext(), mCall.getLabelText());
                break;
            case R.id.like_l:
                if (mLike.getLabelText().equalsIgnoreCase("Liked")) {
                    mLike.setLabelText("Like");
                    mLike.setLabelTextColor(Color.WHITE);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mLike.setLabelText("Liked");
                    mLike.setLabelTextColor(Color.RED);
                    menuRed.setClosedOnTouchOutside(true);
                }

                break;
            case R.id.follow_f:

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
                }
                String dp_path = "http://autokatta.com/mobile/store_profiles/" + dp;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                collapsingToolbar.setTitle(userName);
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
