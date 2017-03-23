package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.events.MyActiveAuctionFragment;
import autokatta.com.events.MyActiveExchangeMelaFrament;
import autokatta.com.events.MyActiveLoanMelaFragment;

public class MyActiveEventsTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_active_events_tab);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_myactive_event_viewpager);
        final TabLayout mTabLayout = (TabLayout) findViewById(R.id.activity_myactive_event_tab);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSupportActionBar(toolbar);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }


                    if (mViewPager != null) {
                        setUpPager(mViewPager);
                    }


                    mTabLayout.setupWithViewPager(mViewPager);
                    //        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
                    //        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);


                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setUpPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyActiveAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyActiveLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyActiveExchangeMelaFrament(), "Exchange Mela");

        viewPager.setAdapter(tabAdapterName);
    }
}
