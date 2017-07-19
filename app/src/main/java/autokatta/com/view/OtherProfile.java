package autokatta.com.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.OtherWall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    CoordinatorLayout mOtherProfile;
    String mOtherContact, mLoginContact, mFolllowstr, mLikestr;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    FloatingActionButton mCall, mLike, mFollow;
    Groups mGroupsFrag;
    AboutStore mStore;
    Event mEventFrag;
    OtherWall mOtherWallFrag;
    String mAction = "other";
    Follow mFollowFrag;
    String key;
    ApiCall mApiCall = new ApiCall(OtherProfile.this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);

        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);

        mGroupsFrag = new Groups();
        mGroupsFrag.setArguments(mBundle);
        mStore = new AboutStore();
        mStore.setArguments(mBundle);
       /* mEventFrag = new Event();
        mEventFrag.setArguments(mBundle);*/
        mOtherWallFrag = new OtherWall();
        mOtherWallFrag.setArguments(mBundle);
        mFollowFrag = new Follow();
        mFollowFrag.setArguments(mBundle);
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
                        key = getIntent().getExtras().getString("like");
                        if (key != null) {
                            if (key.equals("Like")) {
                                mOtherContact = getIntent().getExtras().getString("firebaseContact");
                            }
                        } else {
                            mOtherContact = getIntent().getExtras().getString("contactOtherProfile");
                        }
                    }
                    mBundle.putString("otherContact", mOtherContact);
                    mBundle.putString("action", mAction);
                    getOtherProfile(mOtherContact);
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    mOtherProfile = (CoordinatorLayout) findViewById(R.id.other_profile);
                    mOtherPicture = (ImageView) findViewById(R.id.other_profile_image);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.other_profile_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.other_profile_tabs);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    GET Other Profile...
     */
    private void getOtherProfile(String contact) {
        mApiCall.profileAbout(contact, mLoginContact);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mGroupsFrag, "GROUP");
        adapter.addFragment(mStore, "STORE");
        //adapter.addFragment(mEventFrag, "EVENT");
        adapter.addFragment(mOtherWallFrag, "WALL");
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
                    mFolllowstr = success.getFollowstatus();
                    mLikestr = success.getLikestatus();

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
                }
                String dp_path = "http://autokatta.com/mobile/profile_profile_pics/" + dp;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                collapsingToolbar.setTitle(userName);
            } else {
                Snackbar.make(mOtherProfile, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mOtherProfile, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
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
                mFolllowstr = "yes";
            } else if (str.equals("success_unfollow")) {
                CustomToast.customToast(getApplicationContext(), " UnFollowed Successfully");
                mFollow.setLabelText("Follow");
                mFollow.setLabelTextColor(Color.WHITE);
                mFolllowstr = "no";
            } else if (str.equals("success_like")) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_c:
                call();
                break;
            case R.id.follow_f:
                if (mFolllowstr.equalsIgnoreCase("no")) {
                    mApiCall.Follow(mLoginContact, mOtherContact, "1", 0, 0, 0, 0);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.UnFollow(mLoginContact, mOtherContact, "1", 0, 0, 0, 0);
                    menuRed.setClosedOnTouchOutside(true);
                }
                break;

            case R.id.like_l:
                if (mLikestr.equalsIgnoreCase("no")) {
                    mApiCall.Like(mLoginContact, mOtherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                    menuRed.setClosedOnTouchOutside(true);
                } else {
                    mApiCall.UnLike(mLoginContact, mOtherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                    menuRed.setClosedOnTouchOutside(true);
                }
                break;
        }
    }

    //Calling Functio
    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mOtherContact));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
            ex.printStackTrace();
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
}
