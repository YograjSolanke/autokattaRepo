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

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    FloatingActionButton mCall, mLike, mFollow;
    List<FloatingActionMenu> menus = new ArrayList<>();
    Groups mGroupsFrag;
    Event mEventFrag;
    Katta mKattaFrag;
    Follow mFollowFrag;
    //private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);

        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.like);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*
        Get Bundle Data...
         */

        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

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

                    mOtherContact = getIntent().getExtras().getString("contactOtherProfile");
                    mBundle.putString("otherContact", mOtherContact);

                    mGroupsFrag = new Groups();
                    mGroupsFrag.setArguments(mBundle);
                    mEventFrag = new Event();
                    mEventFrag.setArguments(mBundle);
                    mKattaFrag = new Katta();
                    mKattaFrag.setArguments(mBundle);
                    mFollowFrag = new Follow();
                    mFollowFrag.setArguments(mBundle);


                } catch (Exception e) {
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
        mApiCall.getOtherProfile(mOtherContact, mLoginContact);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mGroupsFrag, "GROUP");
        adapter.addFragment(mEventFrag, "EVENT");
        adapter.addFragment(mKattaFrag, "KATTA");
        adapter.addFragment(mFollowFrag, "FOLLOW");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                String userName = "", dp = "";
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                for (ProfileAboutResponse.Success success : mProfileAboutResponse.getSuccess()) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_c:
                CustomToast.customToast(getApplicationContext(), mCall.getLabelText());
                break;
            case R.id.like_l:
                if (mLike.getLabelText().equalsIgnoreCase("Liked")) {
                    mLike.setLabelText("Like");
                    mLike.setLabelTextColor(Color.WHITE);
                    menuRed.setClosedOnTouchOutside(true);
                }else {
                    mLike.setLabelText("Liked");
                    mLike.setLabelTextColor(Color.RED);
                    menuRed.setClosedOnTouchOutside(true);
                }

            break;
            case R.id.follow_f:

                break;
        }
    }
}
