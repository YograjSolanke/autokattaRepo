package autokatta.com.events;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class MyEndedServiceMelaPreviewActivity extends AppCompatActivity {


    Bundle b=new Bundle();
    TextView mStartdate, mStartTime, mEndTime, mEndDate, mLocation, mtitle;
    String strTitle;
    String strStartdate;
    String strStarttime;
    String strEnddate;
    String strEndTime;
    String strLocation;
    String strEndDateTime,strServiceid;
    TextView txtTimer;
    CollapsingToolbarLayout mCollapsingToolbar;
    ServiceParticipantsFragment serviceParticipantsFragment=new ServiceParticipantsFragment();
    ServiceMelaAnalyticsFragment serviceMelaAnalyticsFragment=new ServiceMelaAnalyticsFragment();


    ViewPager mViewPager;
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ended_service_mela_preview);
        mViewPager = (ViewPager) findViewById(R.id.preview_myactive_mela_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.preview_myactive_mela_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mStartdate = (TextView) findViewById(R.id.start_date);
        mStartTime = (TextView) findViewById(R.id.start_time);
        mEndTime = (TextView) findViewById(R.id.end_time);
        mEndDate = (TextView) findViewById(R.id.end_date);
        mLocation = (TextView) findViewById(R.id.location);
        txtTimer = (TextView) findViewById(R.id.live_timer);
        mtitle = (TextView) findViewById(R.id.loan_text);

        //get Data from Adapter
        strTitle = getIntent().getExtras().getString("title");
        strStartdate = getIntent().getExtras().getString("startdate");
        strStarttime = getIntent().getExtras().getString("starttime");
        strEnddate = getIntent().getExtras().getString("enddate");
        strEndTime = getIntent().getExtras().getString("endtime");
        strLocation = getIntent().getExtras().getString("location");
        strEndDateTime = getIntent().getExtras().getString("enddatetime");
        strServiceid = getIntent().getExtras().getString("serviceid");



        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Set Data
                mtitle.setText("Ended Service Mela");
                mCollapsingToolbar.setTitle(strTitle);
                mStartTime.setText(strStarttime);
                mStartdate.setText(strStartdate);
                mEndDate.setText(strEnddate);
                mEndTime.setText(strEndTime);
                mLocation.setText(strLocation);
                b.putString("serviceid",strServiceid);
                serviceMelaAnalyticsFragment.setArguments(b);
                serviceParticipantsFragment.setArguments(b);

                if (mViewPager != null) {
                    setupViewPager(mViewPager);
                }
                mTabLayout.setupWithViewPager(mViewPager);

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(serviceParticipantsFragment, "Participants");
        adapter.addFragment(serviceMelaAnalyticsFragment, "Analytics");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(1);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
