package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import autokatta.com.fragment.UpdatesFragment;
import autokatta.com.fragment_profile.About;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;
import autokatta.com.fragment_profile.Modules;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements RequestNotifier {

    ImageView mProfilePicture;
    Bundle mUserProfileBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserProfileBundle = new Bundle();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle("Autokatta");

            mProfilePicture = (ImageView) findViewById(R.id.user_image);

            ViewPager viewPager = (ViewPager) findViewById(R.id.user_profile_viewpager);
            if (viewPager != null) {
                setupViewPager(viewPager);
            }

            TabLayout tabLayout = (TabLayout) findViewById(R.id.user_profile_tabs);
            tabLayout.setupWithViewPager(viewPager);
            if (tabLayout.getSelectedTabPosition() == 1){
                AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
                appBarLayout.setExpanded(true, true);
                Log.e("here","->");
            }
             /*
            Get Profile Data
             */
            getProfileData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    API Call for get profile data...
     */
    private void getProfileData() {
        ApiCall mApiCall = new ApiCall(UserProfile.this, this);
        mApiCall.profileAbout("8007855589");
    }

    /*
    SETUP TABS INTO VIEWPAGER...
     */
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new About(), "ABOUT");
        adapter.addFragment(new Groups(), "GROUP");
        adapter.addFragment(new AboutStore(), "STORE");
        adapter.addFragment(new Event(), "EVENT");
        adapter.addFragment(new Katta(), "KATTA");
        adapter.addFragment(new Modules(), "MODULE");
        adapter.addFragment(new Follow(), "FOLLOW");
        adapter.addFragment(new UpdatesFragment(), "MY VEHICLES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                if (!mProfileAboutResponse.getSuccess().isEmpty()) {

                    String dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    String dp_path = "http://autokatta.com/mobile/profile_profile_pics/" + dp;
                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mProfilePicture);
                } else {

                }
            } else {
                //       Snackbar.make(findViewById(R.id.user_profile), getString(R.string._404), Snackbar.LENGTH_LONG).show();
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
