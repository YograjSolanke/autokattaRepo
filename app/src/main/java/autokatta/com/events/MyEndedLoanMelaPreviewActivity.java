package autokatta.com.events;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class MyEndedLoanMelaPreviewActivity extends AppCompatActivity {
    Bundle b=new Bundle();
    TextView mStartdate, mStartTime, mEndTime, mEndDate, mLocation, mtitle;
    String strTitle;
    String strStartdate;
    String strStarttime;
    String strEnddate;
    String strEndTime;
    String strLocation;
    String strEndDateTime,strloanid;
    TextView txtTimer;
    CollapsingToolbarLayout mCollapsingToolbar;

    LoanMelaAnalyticsFragment loanMelaAnalyticsFragment= new LoanMelaAnalyticsFragment();
    LoanMelaParticipantsFragment loanMelaParticipantsFragment= new LoanMelaParticipantsFragment();
    ViewPager mViewPager;
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ended_loan_mela_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.preview_myactive_mela_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.preview_myactive_mela_tabs);

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
        strloanid = getIntent().getExtras().getString("loanid");


    runOnUiThread(new Runnable() {
        @Override
        public void run() {

            //Set Data
            mtitle.setText("Ended Loan Mela");
            mCollapsingToolbar.setTitle(strTitle);
            mStartTime.setText(strStarttime);
            mStartdate.setText(strStartdate);
            mEndDate.setText(strEnddate);
            mEndTime.setText(strEndTime);
            mLocation.setText(strLocation);

            b.putString("loanid",strloanid);
            loanMelaAnalyticsFragment.setArguments(b);
            loanMelaParticipantsFragment.setArguments(b);
            if (mViewPager != null) {
                setupViewPager(mViewPager);
            }
            mTabLayout.setupWithViewPager(mViewPager);

        }
    });
}
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(loanMelaParticipantsFragment, "Participants");
        adapter.addFragment(loanMelaAnalyticsFragment, "Analytics");
        viewPager.setAdapter(adapter);
    }

}
