package autokatta.com.auction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

public class LiveAuctionEventBiding extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    HighestBid mHighestBid;
    IncreaseBidLimit mIncreaseBidLimit;
    OutBid mOutBid;
    WatchedItem mWatchedItem;
    YourBid mYourBid;
    String auctioneername, AuctionId, action_title, auction_startdate, auction_starttime, auction_enddate, auction_endtime,
            no_of_vehicles, auctioncontact, specialcluases, endDateTime, openClose, auctiontype, showPrice;
    Boolean isEMDPaid;
    TextView mLiveTitle, mLiveVehicles, mLiveAuctionType, mLiveCurrentlyActive, mEndDate, mEndTime, mLiveTimer;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_auction_event_biding);

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

        Bundle b1 = new Bundle();
        b1.putString("auction_id", AuctionId);
        b1.putString("openClose", openClose);
        b1.putString("showPrice", showPrice);

        mLiveTitle = (TextView) findViewById(R.id.live_title);
        mLiveVehicles = (TextView) findViewById(R.id.live_no_of_vehicles);
        mLiveAuctionType = (TextView) findViewById(R.id.live_auction_type);
        mLiveCurrentlyActive = (TextView) findViewById(R.id.live_currently_active);
        mEndDate = (TextView) findViewById(R.id.end_date);
        mEndTime = (TextView) findViewById(R.id.end_time);
        mLiveTimer = (TextView) findViewById(R.id.live_timer);

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
                    mLiveCurrentlyActive.setText(openClose + "" + "type auction");
                    mEndDate.setText(auction_enddate);
                    mEndTime.setText(auction_endtime);
                    //mLiveTimer.setText(action_title);

                    ViewPager viewPager = (ViewPager) findViewById(R.id.bid_viewpager);
                    if (viewPager != null) {
                        setupViewPager(viewPager);
                    }

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.bid_tabs);
                    tabLayout.setupWithViewPager(viewPager);

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

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(mYourBid, "Your Bid");
        adapter.addFragment(mHighestBid, "Highest Bid");
        adapter.addFragment(mOutBid, "Out Bid");
        adapter.addFragment(mWatchedItem, "Watched Item");
        adapter.addFragment(mIncreaseBidLimit, "Increase Bid Limit");
        viewPager.setAdapter(adapter);
    }
}
