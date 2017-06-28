package autokatta.com.auction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class LiveAuctionEventBiding extends AppCompatActivity implements View.OnClickListener {

    CollapsingToolbarLayout collapsingToolbar;
    HighestBid mHighestBid;
    IncreaseBidLimit mIncreaseBidLimit;
    OutBid mOutBid;
    WatchedItem mWatchedItem;
    YourBid mYourBid;
    String auctioneername, AuctionId, action_title, auction_startdate, auction_starttime, auction_enddate, auction_endtime,
            no_of_vehicles, auctioncontact, specialcluases, endDateTime, openClose, auctiontype, showPrice, ignoreGoing,
            startDateTime, blackListStatus, keyWord, strCategory, strLocation;
    Boolean isEMDPaid;
    TextView mLiveTitle, mLiveVehicles, mLiveAuctionType, mCategory, mEndDate, mEndTime, mLiveTimer;
    TextView mLimitForBid, mHighBidTotal, mTotalRemains;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    CountDownTimer cdt;
    Bundle b1;
    FloatingActionButton fabCal, fabMail;
    FloatingActionMenu menuRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_auction_event_biding);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        auctioneername = getIntent().getExtras().getString("auctioneer");
        AuctionId = getIntent().getExtras().getString("auction_id");
        action_title = getIntent().getExtras().getString("action_title");
        auction_startdate = getIntent().getExtras().getString("auction_startdate");
        auction_starttime = getIntent().getExtras().getString("auction_starttime");
        auction_enddate = getIntent().getExtras().getString("auction_enddate");
        auction_endtime = getIntent().getExtras().getString("auction_endtime");
        no_of_vehicles = getIntent().getExtras().getString("no_of_vehicles");
        auctiontype = getIntent().getExtras().getString("auction_type");
        auctioncontact = getIntent().getExtras().getString("auctioncontact");
        specialcluases = getIntent().getExtras().getString("specialcluases");
        endDateTime = getIntent().getExtras().getString("endDateTime");
        openClose = getIntent().getExtras().getString("openClose");
        showPrice = getIntent().getExtras().getString("showPrice");
        isEMDPaid = getIntent().getExtras().getBoolean("isPayEMD");
        ignoreGoing = getIntent().getExtras().getString("ignoreGoingStatus");
        startDateTime = getIntent().getExtras().getString("startDateTime");
        blackListStatus = getIntent().getExtras().getString("blackListStatus");
        keyWord = getIntent().getExtras().getString("keyword");
        strCategory = getIntent().getExtras().getString("category");
        strLocation = getIntent().getExtras().getString("location");
        b1 = new Bundle();
        b1.putString("auction_id", AuctionId);
        b1.putString("openClose", openClose);
        b1.putString("showPrice", showPrice);

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        menuRed.setClosedOnTouchOutside(true);
        mLiveTitle = (TextView) findViewById(R.id.live_title);
        mLiveVehicles = (TextView) findViewById(R.id.live_no_of_vehicles);
        mLiveAuctionType = (TextView) findViewById(R.id.live_auction_type);
        mCategory = (TextView) findViewById(R.id.category);
        mEndDate = (TextView) findViewById(R.id.end_date);
        mEndTime = (TextView) findViewById(R.id.end_time);
        mLiveTimer = (TextView) findViewById(R.id.live_timer);
        mLimitForBid = (TextView) findViewById(R.id.limitforbid);
        mHighBidTotal = (TextView) findViewById(R.id.highbidtotal);
        mTotalRemains = (TextView) findViewById(R.id.remaininglimit);
        fabCal = (FloatingActionButton) findViewById(R.id.call_c);
        fabMail = (FloatingActionButton) findViewById(R.id.mail);

        fabCal.setOnClickListener(this);
        fabMail.setOnClickListener(this);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.live_auction_collapsing_toolbar);
        mHighestBid = new HighestBid();
        mHighestBid.setArguments(b1);
        mIncreaseBidLimit = new IncreaseBidLimit();
        mIncreaseBidLimit.setArguments(b1);
        mOutBid = new OutBid();
        mOutBid.setArguments(b1);
        mWatchedItem = new WatchedItem();
        mWatchedItem.setArguments(b1);
        mYourBid = new YourBid();
        mYourBid.setArguments(b1);

        collapsingToolbar.setTitle(auctioneername);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    mLiveTitle.setText(action_title);
                    mLiveVehicles.setText(no_of_vehicles);
                    mLiveAuctionType.setText(auctiontype);
                    mCategory.setText(strCategory);
                    mEndDate.setText(auction_enddate);
                    mEndTime.setText(auction_endtime);
                    //mLiveTimer.setText(action_title);
                    if (isEMDPaid) {
                        mLimitForBid.setText("lmt");
                    } else {
                        mLimitForBid.setText(getString(R.string.Rs) + " " + "0");
                    }
                    mTotalRemains.setText("rem");

                    ViewPager viewPager = (ViewPager) findViewById(R.id.bid_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.bid_tabs);
                    tabLayout.setupWithViewPager(viewPager);

                    try {
                        if (getIntent().getExtras().getString("tabNo") != null) {
                            String tabNo = getIntent().getExtras().getString("tabNo");
                            if (tabNo != null)
                                Log.i("TabNo", "->" + tabNo);
                            viewPager.setCurrentItem(Integer.parseInt(tabNo));
                        } else {
                            Log.e("Value", "is Null");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final TextView tv = mLiveTimer;
                    cdt = counters.get(mLiveTimer);
                    if (cdt != null) {
                        cdt.cancel();
                        cdt = null;
                    }

                    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date futureDate = dateFormat.parse(endDateTime);
                        System.out.println("date============================================" + endDateTime);
                        Date currentDate = dateFormat.parse(endDateTime);
                        System.out.println("date============================================" + endDateTime);

                        Date now = new Date();
                        long difference = futureDate.getTime() - currentDate.getTime();
                        long diff = futureDate.getTime() - now.getTime();
                        long abc = difference - diff;
                        cdt = new CountDownTimer(diff, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                int days = 0;
                                int hours = 0;
                                int minutes = 0;
                                int seconds = 0;
                                String sDate = "";

                                if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                                    days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
                                    sDate += days + "d";

                                }

                                millisUntilFinished -= (days * DateUtils.DAY_IN_MILLIS);

                                if (millisUntilFinished > DateUtils.HOUR_IN_MILLIS) {
                                    hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);

                                }

                                millisUntilFinished -= (hours * DateUtils.HOUR_IN_MILLIS);

                                if (millisUntilFinished > DateUtils.MINUTE_IN_MILLIS) {
                                    minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);

                                }

                                millisUntilFinished -= (minutes * DateUtils.MINUTE_IN_MILLIS);

                                if (millisUntilFinished > DateUtils.SECOND_IN_MILLIS) {
                                    seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);

                                }

                                sDate += " " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
                                tv.setText(sDate.trim());
                            }

                            @Override
                            public void onFinish() {
                                tv.setText("Finished");
                            }
                        };
                        counters.put(tv, cdt);
                        cdt.start();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void hideFloatingButton() {
        menuRed.hideMenu(true);

    }

    public void showFloatingButton() {
        menuRed.showMenu(true);

    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mYourBid, "Your Bid");
        adapter.addFragment(mHighestBid, "Highest Bid");
        adapter.addFragment(mOutBid, "Out Bid");
        adapter.addFragment(mWatchedItem, "Watched Item");
        adapter.addFragment(mIncreaseBidLimit, "Increase Bid Limit");
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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(LiveAuctionEventBiding.this, R.anim.pull_in_left, R.anim.push_out_right);
            Bundle bundle = new Bundle();
            bundle.putString("auctioneer", auctioneername);
            bundle.putString("auction_id", AuctionId);
            bundle.putString("action_title", action_title);
            bundle.putString("auction_startdate", auction_startdate);
            bundle.putString("auction_starttime", auction_starttime);
            bundle.putString("auction_enddate", auction_enddate);
            bundle.putString("auction_endtime", auction_endtime);
            bundle.putString("no_of_vehicles", no_of_vehicles);
            bundle.putString("auction_type", auctiontype);
            bundle.putString("auctioncontact", auctioncontact);
            bundle.putString("ignoreGoingStatus", ignoreGoing);
            bundle.putString("startDateTime", startDateTime);
            bundle.putString("endDateTime", endDateTime);
            bundle.putString("specialcluases", specialcluases);
            bundle.putString("blackListStatus", blackListStatus);
            bundle.putString("openClose", openClose);
            bundle.putString("showPrice", showPrice);
            bundle.putString("keyword", keyWord);
            Log.i("ignoreGoingLive", "->" + ignoreGoing);
            Intent intent = new Intent(getApplicationContext(), PreviewGoingEvents.class);
            intent.putExtras(bundle);
            startActivity(intent, options.toBundle());
            //startActivity(new Intent(getApplicationContext(), PreviewLiveEvents.class), options.toBundle());
            finish();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("auctioneer", auctioneername);
            bundle.putString("auction_id", AuctionId);
            bundle.putString("action_title", action_title);
            bundle.putString("auction_startdate", auction_startdate);
            bundle.putString("auction_starttime", auction_starttime);
            bundle.putString("auction_enddate", auction_enddate);
            bundle.putString("auction_endtime", auction_endtime);
            bundle.putString("no_of_vehicles", no_of_vehicles);
            bundle.putString("auction_type", auctiontype);
            bundle.putString("auctioncontact", auctioncontact);
            bundle.putString("ignoreGoingStatus", ignoreGoing);
            bundle.putString("startDateTime", startDateTime);
            bundle.putString("endDateTime", endDateTime);
            bundle.putString("specialcluases", specialcluases);
            bundle.putString("blackListStatus", blackListStatus);
            bundle.putString("openClose", openClose);
            bundle.putString("showPrice", showPrice);
            bundle.putString("keyword", keyWord);

            Intent intent = new Intent(getApplicationContext(), PreviewGoingEvents.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            //startActivity(new Intent(getApplicationContext(), PreviewLiveEvents.class));
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_c:
                call(auctioncontact);
                break;
            case R.id.mail:
                mail();
                break;
        }
    }

    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Live Auction Event Bidding\n");
        }
    }

    //mail Functionality
    private void mail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@mail.com", null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "abc@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send Email..."));
    }
}
