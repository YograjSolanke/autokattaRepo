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
                    String userName = mProfileAboutResponse.getSuccess().get(0).getUsername();
                    String email = mProfileAboutResponse.getSuccess().get(0).getEmail();
                    String contact = mProfileAboutResponse.getSuccess().get(0).getContact();
                    String profession = mProfileAboutResponse.getSuccess().get(0).getProfession();
                    String company = mProfileAboutResponse.getSuccess().get(0).getCompanyName();
                    String designation = mProfileAboutResponse.getSuccess().get(0).getDesignation();
                    String subProfession = mProfileAboutResponse.getSuccess().get(0).getSubProfession();
                    String websitestr = mProfileAboutResponse.getSuccess().get(0).getWebsite();
                    String city = mProfileAboutResponse.getSuccess().get(0).getCity();
                    String skills = mProfileAboutResponse.getSuccess().get(0).getSkills();
                    String dp_path = "http://autokatta.com/mobile/profile_profile_pics/" + dp;
                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mProfilePicture);

                    Log.i("contact in ", "Profile=>" + contact);


                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_contact", contact).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_profession", profession).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_email", email).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_website", websitestr).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_city", city).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_company", company).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_designation", designation).apply();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("user_skills", skills).apply();
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
}
