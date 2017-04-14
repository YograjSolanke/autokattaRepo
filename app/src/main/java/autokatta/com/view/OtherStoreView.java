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

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class OtherStoreView extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
    String mOtherContact, mLoginContact;
    Bundle mBundle = new Bundle();
    FloatingActionMenu menuRed;
    FloatingActionButton mCall, mLike, mFollow;

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
                        mOtherContact = getIntent().getExtras().getString("contactOtherProfile");
                    }
                    mBundle.putString("otherContact", mOtherContact);

                    // getOtherProfile(mOtherContact);
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    mOtherPicture = (ImageView) findViewById(R.id.other_store_image);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.other_store_viewpager);
                    if (viewPager != null) {
                        // setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.other_profile_tabs);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
