package autokatta.com.events;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class ActiveSaleMelaPreviewActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_sale_mela_preview);
        mViewPager = (ViewPager) findViewById(R.id.preview_myactive_mela_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.preview_myactive_mela_tabs);
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
        adapter.addFragment(new SaleMelaParticipantsFragment(), "Participants");
        adapter.addFragment(new SaleMelaAnalyticsFragment(), "Analytics");
        viewPager.setAdapter(adapter);
    }

}
