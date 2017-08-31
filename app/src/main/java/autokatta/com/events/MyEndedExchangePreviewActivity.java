package autokatta.com.events;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class MyEndedExchangePreviewActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle b=new Bundle();
    TextView mStartdate, mStartTime, mEndTime, mEndDate, mLocation, mtitle;
    String strTitle;
    String strStartdate;
    String strStarttime;
    String strEnddate;
    String strEndTime;
    String strLocation;
    String strEndDateTime, strDetails;
    FloatingActionMenu menuRed;
    FloatingActionButton btnPreview;
    ;
    private int strExchangeID = 0;
    TextView txtTimer;
    CollapsingToolbarLayout mCollapsingToolbar;
    CountDownTimer cdt;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();

    ExchangeMelaParticipantsFragment exchangeMelaParticipantsFragment;
    ExchangeMelaAnalyticsFragment exchangeMelaAnalyticsFragment =new ExchangeMelaAnalyticsFragment();

    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_ended_exchange_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //get Data from Adapter
        strTitle = getIntent().getExtras().getString("title");
        strStartdate = getIntent().getExtras().getString("startdate");
        strStarttime = getIntent().getExtras().getString("starttime");
        strEnddate = getIntent().getExtras().getString("enddate");
        strEndTime = getIntent().getExtras().getString("endtime");
        strLocation = getIntent().getExtras().getString("location");
        strEndDateTime = getIntent().getExtras().getString("enddatetime");
        strExchangeID = getIntent().getExtras().getInt("exchangeid");
        strDetails = getIntent().getExtras().getString("details");

        mViewPager = (ViewPager) findViewById(R.id.preview_myactive_mela_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.preview_myactive_mela_tabs);
        btnPreview = (FloatingActionButton) findViewById(R.id.preview);
        btnPreview.setOnClickListener(this);

        b.putInt("exchangeid", strExchangeID);
        exchangeMelaParticipantsFragment = new ExchangeMelaParticipantsFragment();
        exchangeMelaParticipantsFragment.setArguments(b);
        exchangeMelaAnalyticsFragment = new ExchangeMelaAnalyticsFragment();
        exchangeMelaAnalyticsFragment.setArguments(b);

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mStartdate = (TextView) findViewById(R.id.start_date);
        mStartTime = (TextView) findViewById(R.id.start_time);
        mEndTime = (TextView) findViewById(R.id.end_time);
        mEndDate = (TextView) findViewById(R.id.end_date);
        mLocation = (TextView) findViewById(R.id.location);
        txtTimer = (TextView) findViewById(R.id.live_timer);
        mtitle = (TextView) findViewById(R.id.loan_text);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
                menuRed.setClosedOnTouchOutside(true);
                //Set Data
                mtitle.setText("Ended Exchange Mela");
                mCollapsingToolbar.setTitle("Title: " + strTitle);
                mStartTime.setText(strStarttime);
                mStartdate.setText(strStartdate);
                mEndDate.setText(strEnddate);
                mEndTime.setText(strEndTime);
                mLocation.setText(strLocation);
                if (mViewPager != null) {
                    setupViewPager(mViewPager);
                }
                mTabLayout.setupWithViewPager(mViewPager);

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(exchangeMelaParticipantsFragment, "Participants");
        adapter.addFragment(exchangeMelaAnalyticsFragment, "Analytics");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.preview:

                showDetails(strDetails);
                break;
        }


    }

    private void showDetails(String details) {

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Event Details")
                .setMessage("YOUR_MSG")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        //textView.setMaxLines(5);
        if (textView != null) {
            textView.setScroller(new Scroller(this));
            textView.setVerticalScrollBarEnabled(true);
            textView.setText(details);
            textView.setMovementMethod(new ScrollingMovementMethod());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMarginStart(30);
            textView.setBackgroundColor(Color.WHITE);
            textView.setLayoutParams(lp);
            textView.setPadding(40, 40, 40, 40);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);

            dialog.setView(textView);
        }
    }
}
