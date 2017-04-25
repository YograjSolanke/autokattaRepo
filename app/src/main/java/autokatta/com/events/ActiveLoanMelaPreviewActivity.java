package autokatta.com.events;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.StoreInfo;

public class ActiveLoanMelaPreviewActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_loan_mela_preview);
        mViewPager = (ViewPager) findViewById(R.id.activity_myactive_event_preview_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_myactive_event_preview_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mViewPager != null) {
                    setupViewPager(mViewPager);
                }
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });
    }


        private void setupViewPager(ViewPager viewPager) {
            TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
            adapter.addFragment(new StoreInfo(), "Analytics");
            adapter.addFragment(new StoreInfo(), "Sale Description");
            viewPager.setAdapter(adapter);
        }
    }

