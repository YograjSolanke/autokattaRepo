package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.VehicleDetailsSpecifications;
import autokatta.com.fragment.VehicleDetailsTwo;
import autokatta.com.fragment.VehicleDetails_Details;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;

public class OtherProfile extends AppCompatActivity {

    ImageView mOtherPicture;
    CollapsingToolbarLayout collapsingToolbar;
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

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

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new Groups(), "GROP");
        adapter.addFragment(new Event(), "EVENT");
        adapter.addFragment(new Katta(), "KATTA");
        adapter.addFragment(new Follow(), "FOLLOW");
        viewPager.setAdapter(adapter);
    }

}
