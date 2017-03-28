package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.VehicleDetailsSpecifications;
import autokatta.com.fragment.VehicleDetailsTwo;
import autokatta.com.fragment.VehicleDetails_Details;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity implements RequestNotifier {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.like);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        Get Bundle Data...
         */
        mOtherContact = getIntent().getExtras().getString("contactOtherProfile");
        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact","");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getOtherProfile();
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    mOtherPicture = (ImageView) findViewById(R.id.other_profile_image);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.other_profile_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.other_profile_tabs);
                    tabLayout.setupWithViewPager(viewPager);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    GET Other Profile...
     */
    private void getOtherProfile() {
        ApiCall mApiCall = new ApiCall(OtherProfile.this, this);
        mApiCall.getOtherProfile(mLoginContact, mOtherContact);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new Groups(), "GROUP");
        adapter.addFragment(new Event(), "EVENT");
        adapter.addFragment(new Katta(), "KATTA");
        adapter.addFragment(new Follow(), "FOLLOW");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null){
            if (response.isSuccessful()){
                String userName = "", dp = "";
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                for (ProfileAboutResponse.Success success : mProfileAboutResponse.getSuccess()){
                    userName = success.getUsername();
                    dp = success.getProfilePic();
                }
                String dp_path = "http://autokatta.com/mobile/profile_profile_pics/" + dp;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                collapsingToolbar.setTitle(userName);
            }else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        }else {
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
