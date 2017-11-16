package autokatta.com.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.OtherAbout;
import autokatta.com.fragment_profile.OtherWall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
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
    OtherAbout mAbout;
    Groups mGroupsFrag;
    AboutStore mStore;
    String dp = "";
    int ProfileId = 0;
    Event mEventFrag;
    private ProgressDialog dialog;
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
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mLike = (FloatingActionButton) findViewById(R.id.like_l);
        mFollow = (FloatingActionButton) findViewById(R.id.follow_f);
        mOtherPicture = (ImageView) findViewById(R.id.other_profile_image);
        mOtherPicture.setOnClickListener(this);

        mCall.setLabelTextColor(Color.BLACK);
        mCall.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mFollow.setOnClickListener(this);

        mAbout = new OtherAbout();
        mAbout.setArguments(mBundle);

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
                            if (key.equals("Profile")) {
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
        dialog.show();
        mApiCall.profileAbout(contact, mLoginContact);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mAbout, "ABOUT");
        adapter.addFragment(mGroupsFrag, "GROUP");
        adapter.addFragment(mStore, "STORE");
        //adapter.addFragment(mEventFrag, "EVENT");
        adapter.addFragment(mOtherWallFrag, "WALL");
        adapter.addFragment(mFollowFrag, "FOLLOW");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                String userName = "";
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                for (ProfileAboutResponse.Success success : mProfileAboutResponse.getSuccess()) {
                    userName = success.getUsername();
                    dp = success.getProfilePic();
                    mFolllowstr = success.getFollowstatus();
                    mLikestr = success.getLikestatus();
                    ProfileId = success.getId();

                    if (mLikestr.equalsIgnoreCase("no")) {
                        mLike.setLabelText("Like");
                        mLike.setLabelTextColor(Color.BLACK);
                        menuRed.setClosedOnTouchOutside(true);
                    } else {
                        mLike.setLabelText("Liked");
                        mLike.setLabelTextColor(Color.BLUE);
                        menuRed.setClosedOnTouchOutside(true);
                    }
                    if (mFolllowstr.equalsIgnoreCase("no")) {
                        mFollow.setLabelText("Follow");
                        mFollow.setLabelTextColor(Color.BLACK);
                        menuRed.setClosedOnTouchOutside(true);
                    } else {
                        mFollow.setLabelText("Following");
                        mFollow.setLabelTextColor(Color.BLUE);
                        menuRed.setClosedOnTouchOutside(true);
                    }
                }
                String dp_path = getString(R.string.base_image_url) + dp;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mOtherPicture);
                collapsingToolbar.setTitle(userName);
                AddSuggestionData(ProfileId);
            } else {
                Snackbar.make(mOtherProfile, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mOtherProfile, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void AddSuggestionData(int profileId) {
        mApiCall.AddDataForSuggestions(mLoginContact, "Profile", 0,
                0, 0, 0, 0, ProfileId);
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "OtherProfile");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (str != null) {
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(getApplicationContext(), " Following Successfully");
                    mFollow.setLabelText("Following");
                    mFollow.setLabelTextColor(Color.BLUE);
                    mFolllowstr = "yes";
                    break;
                case "success_unfollow":
                    CustomToast.customToast(getApplicationContext(), " UnFollowed Successfully");
                    mFollow.setLabelText("Follow");
                    mFollow.setLabelTextColor(Color.BLACK);
                    mFolllowstr = "no";
                    break;
                case "success_like":
                    CustomToast.customToast(getApplicationContext(), " Liked Successfully");
                    mLike.setLabelText("Liked");
                    mLike.setLabelTextColor(Color.BLUE);
                    mLikestr = "yes";
                    break;
                case "success_unlike":
                    CustomToast.customToast(getApplicationContext(), " UnLiked Successfully");
                    mLike.setLabelText("Like");
                    mLike.setLabelTextColor(Color.BLACK);
                    mLikestr = "no";
                    break;

                case "success_added_suggestion":
                    Log.i("Suugesstion", "Profile->" + ProfileId);
                    break;
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
            case R.id.other_profile_image:
                String image;
                if (dp.equals(""))
                    image = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = getString(R.string.base_image_url) + dp;

                Bundle b = new Bundle();
                b.putString("image", image);
                Intent intent = new Intent(OtherProfile.this, FullImageActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }

    //Calling Functio
    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mOtherContact));
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
