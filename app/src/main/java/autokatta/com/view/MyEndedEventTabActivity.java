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
import autokatta.com.events.MyEndedAuctionFragment;
import autokatta.com.events.MyEndedExchangeMelaFragment;
import autokatta.com.events.MyEndedLoanMelaFragment;

public class MyEndedEventTabActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ended_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.activity_ended_event_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_ended_event_tab);
        fab = (FloatingActionButton) findViewById(R.id.fab);

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
        tabAdapterName.addFragment(new MyEndedAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyEndedLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyEndedExchangeMelaFragment(), "Exchange Mela");

        viewPager.setAdapter(tabAdapterName);
    }
}
