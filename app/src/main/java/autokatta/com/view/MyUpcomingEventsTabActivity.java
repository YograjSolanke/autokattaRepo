package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.events.MyUpcomingAuctionFragment;
import autokatta.com.events.MyUpcomingExchangeMelaFragment;
import autokatta.com.events.MyUpcomingLoanMelaFragment;

public class MyUpcomingEventsTabActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upcoming_events_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_myupcoming_event_viewpager);
        if (mViewPager != null) {
            setUpPager(mViewPager);
        }

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.activity_myupcoming_event_tab);
        mTabLayout.setupWithViewPager(mViewPager);
        //        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        //        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
    }

    private void setUpPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyUpcomingAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyUpcomingLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyUpcomingExchangeMelaFragment(), "Exchange Mela");

        viewPager.setAdapter(tabAdapterName);
    }

}
