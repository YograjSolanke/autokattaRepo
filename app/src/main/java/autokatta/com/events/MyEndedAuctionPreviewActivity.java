package autokatta.com.events;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class MyEndedAuctionPreviewActivity extends AppCompatActivity implements View.OnClickListener {


    private String strAuctionId = "", strAuctionTitle = "", strVehicleCount = "", strStartDate = "", strStartTime = "",
            strEndDate = "", strEndTime = "", strSpecialClauses = "", strStartdatetime = "", strEnddatetime = "",
            strParticipantcount = "", strSpecialClause = "", strCategory = "", strLocation = "";

    CollapsingToolbarLayout mCollapsingToolbar;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    TextView txtVehicle, txtEndDate, txtEndTime, txtStartTime, txtStartDate, txtParticipant, txtTimer,
            txtCategory, txtLocation;
    FloatingActionButton btnSpecialclause, btnLive;
    AuctionVehiclesFragment auctionVehiclesFragment;
    AuctionParticipantsFragment auctionParticipantsFragment;
    AuctionAnalyticsFragment auctionAnalyticsFragment;
    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ended_auction_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        strAuctionId = getIntent().getExtras().getString("auctionid");
        strAuctionTitle = getIntent().getExtras().getString("auctiontitle");
        strVehicleCount = getIntent().getExtras().getString("vehicle_count");
        strStartDate = getIntent().getExtras().getString("auctionstartdate");
        strStartTime = getIntent().getExtras().getString("auctionstarttime");
        strEndDate = getIntent().getExtras().getString("auctionenddate");
        strEndTime = getIntent().getExtras().getString("auctionendtime");
        strSpecialClauses = getIntent().getExtras().getString("specialclauses");
        strStartdatetime = getIntent().getExtras().getString("startdatetime");
        strEnddatetime = getIntent().getExtras().getString("enddatetime");
        strParticipantcount = getIntent().getExtras().getString("participant_count");
        strCategory = getIntent().getExtras().getString("category");
        strLocation = getIntent().getExtras().getString("location");

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.preview_myended_auction_tabs);
        mViewPager = (ViewPager) findViewById(R.id.preview_myended_auction_viewpager);
        txtVehicle = (TextView) findViewById(R.id.editvehicle);
        txtParticipant = (TextView) findViewById(R.id.editparticipants);
        txtTimer = (TextView) findViewById(R.id.live_timer);
        txtStartDate = (TextView) findViewById(R.id.start_date);
        txtStartTime = (TextView) findViewById(R.id.start_time);
        txtEndDate = (TextView) findViewById(R.id.end_date);
        txtEndTime = (TextView) findViewById(R.id.end_time);
        txtCategory = (TextView) findViewById(R.id.category);
        txtLocation = (TextView) findViewById(R.id.location);
        btnSpecialclause = (FloatingActionButton) findViewById(R.id.clauses);
        btnLive = (FloatingActionButton) findViewById(R.id.gotoauction);

        btnSpecialclause.setOnClickListener(this);
        btnLive.setOnClickListener(this);

        auctionVehiclesFragment = new AuctionVehiclesFragment();
        auctionVehiclesFragment.setArguments(mBundle);

        auctionParticipantsFragment = new AuctionParticipantsFragment();
        auctionParticipantsFragment.setArguments(mBundle);

        auctionAnalyticsFragment = new AuctionAnalyticsFragment();
        auctionAnalyticsFragment.setArguments(mBundle);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mCollapsingToolbar.setTitle("Title: " + strAuctionTitle);
                    txtStartDate.setText(strStartDate);
                    txtStartTime.setText(strStartTime);
                    txtEndDate.setText(strEndDate);
                    txtEndTime.setText(strEndTime);
                    txtVehicle.setText(strVehicleCount);
                    txtParticipant.setText(strParticipantcount);
                    txtCategory.setText(strCategory);
                    txtLocation.setText(strLocation);
                    //mAuctionText.setText(getString(R.string.live_auction));
                    mBundle.putString("auctionid", strAuctionId);

                    if (mViewPager != null) {
                        setupViewPager(mViewPager);
                    }
                    mTabLayout.setupWithViewPager(mViewPager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(auctionVehiclesFragment, "Vehicles");
        adapter.addFragment(auctionParticipantsFragment, "Participants");
        adapter.addFragment(auctionAnalyticsFragment, "Analytics");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clauses:

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MyEndedAuctionPreviewActivity.this);
                alertDialog.setTitle("Special Clauses");

                final TextView input = new TextView(MyEndedAuctionPreviewActivity.this);
                input.setText(strSpecialClauses.replaceAll(",", "\n"));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
               /* input.setLayoutParams(lp);
                alertDialog.setView(input);
*/              lp.setMarginStart(30);
                input.setLayoutParams(lp);
                input.setPadding(40, 40, 40, 40);
                input.setGravity(Gravity.CENTER_VERTICAL);
                input.setTextColor(Color.parseColor("#C39BD3"));
                input.setTextSize(20);
                alertDialog.setView(input);


                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
                break;


            case R.id.gotoauction:
                Bundle b = new Bundle();
                b.putString("auctionid", strAuctionId);
                b.putString("auctiontitle", strAuctionTitle);
                b.putString("vehicle_count", strVehicleCount);
                b.putString("auctionstartdate", strStartDate);
                b.putString("auctionstarttime", strStartTime);
                b.putString("auctionenddate", strEndDate);
                b.putString("auctionendtime", strEndTime);
                b.putString("specialclauses", strSpecialClauses);
                b.putString("enddatetime", strEnddatetime);
                b.putString("startdatetime", strStartdatetime);
                b.putString("participant_count", strParticipantcount);
                b.putString("category", strCategory);
                b.putString("location", strLocation);

                finish();
                Intent intent = new Intent(MyEndedAuctionPreviewActivity.this, MyEndedAuctionPreviewNextActivity.class);
                intent.putExtras(b);
                startActivity(intent);

                break;
        }
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

}
