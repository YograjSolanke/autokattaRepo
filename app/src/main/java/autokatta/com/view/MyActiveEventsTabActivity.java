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
import autokatta.com.fragment.AutokattaContactFragment;

public class MyActiveEventsTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_active_events_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_myactive_event_viewpager);
        if (mViewPager != null) {
            setUpPager(mViewPager);
        }

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.activity_myactive_event_tab);
        mTabLayout.setupWithViewPager(mViewPager);
        //        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        //        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyActiveAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new AutokattaContactFragment(), "Loan Mela");
        tabAdapterName.addFragment(new AutokattaContactFragment(), "Exchange Mela");

        viewPager.setAdapter(tabAdapterName);
    }
}
