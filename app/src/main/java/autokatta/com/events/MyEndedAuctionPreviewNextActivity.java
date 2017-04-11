package autokatta.com.events;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class MyEndedAuctionPreviewNextActivity extends AppCompatActivity implements View.OnClickListener {

    private String strAuctionId = "", strAuctionTitle = "", strVehicleCount = "", strStartDate = "", strStartTime = "",
            strEndDate = "", strEndTime = "", strSpecialClauses = "", strStartdatetime = "", strEnddatetime = "", strParticipantcount = "", strSpecialClause = "";

    CollapsingToolbarLayout mCollapsingToolbar;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    TextView txtVehicle, txtEndDate, txtEndTime, txtStartTime, txtStartDate, txtParticipant, txtTimer;
    FloatingActionButton btnSpecialclause;

    MyEndedAuctionApprovedFragment auctionApprovedFragment;
    MyEndedAuctionHighBidFragment auctionHighBidFragment;
    ActiveAuctionNoBidFragment auctionNoBidFragment;
    MyEndedAuctionAboveReservedFragment auctionAboveReservedFragment;
    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ended_auction_preview_next);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.preview_next_myended_auction_tabs);
        mViewPager = (ViewPager) findViewById(R.id.preview_next_myended_auction_viewpager);
        txtVehicle = (TextView) findViewById(R.id.editvehicle);
        txtParticipant = (TextView) findViewById(R.id.editparticipants);
        txtTimer = (TextView) findViewById(R.id.live_timer);
        txtStartDate = (TextView) findViewById(R.id.start_date);
        txtStartTime = (TextView) findViewById(R.id.start_time);
        txtEndDate = (TextView) findViewById(R.id.end_date);
        txtEndTime = (TextView) findViewById(R.id.end_time);
        btnSpecialclause = (FloatingActionButton) findViewById(R.id.clauses);


        btnSpecialclause.setOnClickListener(this);


        auctionApprovedFragment = new MyEndedAuctionApprovedFragment();
        auctionApprovedFragment.setArguments(mBundle);

        auctionHighBidFragment = new MyEndedAuctionHighBidFragment();
        auctionHighBidFragment.setArguments(mBundle);

        auctionNoBidFragment = new ActiveAuctionNoBidFragment();
        auctionNoBidFragment.setArguments(mBundle);

        auctionAboveReservedFragment = new MyEndedAuctionAboveReservedFragment();
        auctionAboveReservedFragment.setArguments(mBundle);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mCollapsingToolbar.setTitle(strAuctionTitle);
                    txtStartDate.setText(strStartDate);
                    txtStartTime.setText(strStartTime);
                    txtEndDate.setText(strEndDate);
                    txtEndTime.setText(strEndTime);
                    txtVehicle.setText(strVehicleCount);
                    txtParticipant.setText(strParticipantcount);
                    //mAuctionText.setText(getString(R.string.live_auction));
                    mBundle.putString("auctionid", strAuctionId);
                    mBundle.putString("specialclauses", strSpecialClauses);

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
        adapter.addFragment(auctionApprovedFragment, "approved vehicles");
        adapter.addFragment(auctionHighBidFragment, "highest bid");
        adapter.addFragment(auctionNoBidFragment, "no bid");
        adapter.addFragment(auctionAboveReservedFragment, "above reserve price");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clauses:

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MyEndedAuctionPreviewNextActivity.this);
                alertDialog.setTitle("Special Clauses");

                final TextView input = new TextView(MyEndedAuctionPreviewNextActivity.this);
                input.setText(strSpecialClauses.replaceAll(",", "\n"));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);


                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
                break;

        }
    }
}
