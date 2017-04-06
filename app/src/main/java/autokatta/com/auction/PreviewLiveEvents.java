package autokatta.com.auction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.PreviewAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAuctionEventResponse;
import retrofit2.Response;

public class PreviewLiveEvents extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    //ListView mListView;
    RecyclerView mRecyclerView;
    Button mGoing, mIgnore, mShare, mGoLive;
    CollapsingToolbarLayout mCollapsingToolbar;
    TextView mLiveTimer, mStartDate, mStartTime, mEndDate, mEndTime, mAuctionText, mCloseOpenType;
    String allDetails = "", keyword;

    String auction_id = "", whoseAuction = "";
    String contact = "", auctioneername, auction_startdate, auction_starttime, auction_enddate, auction_endtime,
            no_of_vehicles, auctioncontact, action_title, ignoreGoingStatus, startDateTime, endDateTime, specialcluases,
            blackListStatus, openClose, auctiontype, showPrice;

    CountDownTimer cdt;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    List<GetAuctionEventResponse.Vehicle> vehicles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_live_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auctioneername = getIntent().getExtras().getString("auctioneer");
        auction_id = getIntent().getExtras().getString("auction_id");
        action_title = getIntent().getExtras().getString("action_title");
        auction_startdate = getIntent().getExtras().getString("auction_startdate");
        auction_starttime = getIntent().getExtras().getString("auction_starttime");
        auction_enddate = getIntent().getExtras().getString("auction_enddate");
        auction_endtime = getIntent().getExtras().getString("auction_endtime");
        no_of_vehicles = getIntent().getExtras().getString("no_of_vehicles");
        auctiontype = getIntent().getExtras().getString("auction_type");
        auctioncontact = getIntent().getExtras().getString("auctioncontact");
        ignoreGoingStatus = getIntent().getExtras().getString("ignoreGoingStatus");
        startDateTime = getIntent().getExtras().getString("startDateTime");
        endDateTime = getIntent().getExtras().getString("endDateTime");
        specialcluases = getIntent().getExtras().getString("specialcluases");
        blackListStatus = getIntent().getExtras().getString("blackListStatus");
        openClose = getIntent().getExtras().getString("openClose");
        showPrice = getIntent().getExtras().getString("showPrice");
        keyword = getIntent().getExtras().getString("keyword");

        //mListView = (ListView) findViewById(R.id.listView);
        mRecyclerView = (RecyclerView) findViewById(R.id.auction_event_recycler_view);
        mGoing = (Button) findViewById(R.id.btn_going);
        mIgnore = (Button) findViewById(R.id.btn_ignore);
        mShare = (Button) findViewById(R.id.btn_share);
        mGoLive = (Button) findViewById(R.id.btngotolive);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mLiveTimer = (TextView) findViewById(R.id.live_timer);
        mStartDate = (TextView) findViewById(R.id.start_date);
        mStartTime = (TextView) findViewById(R.id.start_time);
        mEndDate = (TextView) findViewById(R.id.end_date);
        mEndTime = (TextView) findViewById(R.id.end_time);
        mAuctionText = (TextView) findViewById(R.id.auction_text);
        mCloseOpenType = (TextView) findViewById(R.id.closeopentxt);

        mGoLive.setOnClickListener(this);

        /*
        Set Data from Bundles...
         */

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getAuctionPreviewById();

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(PreviewLiveEvents.this);
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mCollapsingToolbar.setTitle(auctioneername);
                mStartDate.setText(auction_startdate);
                mStartTime.setText(auction_starttime);
                mEndDate.setText(auction_enddate);
                mEndTime.setText(auction_endtime);
                mCloseOpenType.setText(openClose + " " + "Type Auction");
                mAuctionText.setText(getString(R.string.live_auction));

                final TextView tv = mLiveTimer;
                cdt = counters.get(mLiveTimer);
                if (cdt != null) {
                    cdt.cancel();
                    cdt = null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date futureDate = dateFormat.parse(endDateTime);
                    Date currentDate = dateFormat.parse(endDateTime);
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

                mGoLive.setVisibility(View.VISIBLE);
                if (ignoreGoingStatus.equals("going")) {
                    mGoing.setEnabled(false);
                    mGoLive.setEnabled(true);
                    mIgnore.setVisibility(View.GONE);
                    mGoing.setVisibility(View.GONE);

                } else if (ignoreGoingStatus.equals("ignore")) {
                    mIgnore.setEnabled(false);
                    mGoing.setEnabled(false);
                }
            }
        });
    }

    /*
    Get Auction Preview By Id...
     */
    private void getAuctionPreviewById() {
        ApiCall mApiCall = new ApiCall(PreviewLiveEvents.this, this);
        mApiCall.getAuctionEvent(auction_id);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetAuctionEventResponse mAuctionEventResponse = (GetAuctionEventResponse) response.body();
                for (GetAuctionEventResponse.Vehicle vehicle : mAuctionEventResponse.getSuccess().getVehicles()) {
                    vehicle.setVehicleId(vehicle.getVehicleId());
                    vehicle.setTitle(vehicle.getTitle());
                    vehicle.setCategory(vehicle.getCategory());
                    vehicle.setSubCategory(vehicle.getSubCategory());
                    vehicle.setBrand(vehicle.getBrand());
                    vehicle.setModel(vehicle.getModel());
                    vehicle.setLocationCity(vehicle.getLocationCity());
                    vehicle.setColor(vehicle.getColor());
                    vehicle.setImage(vehicle.getImage());
                    vehicle.setRegNo(vehicle.getRegNo());
                    vehicle.setRtoCity(vehicle.getRtoCity());
                    vehicle.setKmsRunning(vehicle.getKmsRunning());
                    vehicle.setHrsRunning(vehicle.getHrsRunning());
                    vehicle.setYear(vehicle.getYear());
                    vehicle.setStartPrice(vehicle.getStartPrice());
                    vehicle.setReservePrice(vehicle.getReservePrice());
                    vehicle.setChassisNo(vehicle.getChassisNo());
                    vehicle.setEngineNo(vehicle.getEngineNo());
                    vehicle.setLotNo(vehicle.getLotNo());
                    vehicle.setVehicleStatus(vehicle.getVehicleStatus());
                    vehicles.add(vehicle);
                }
                PreviewAuctionAdapter adapter = new PreviewAuctionAdapter(PreviewLiveEvents.this, vehicles);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btngotolive:
                if (blackListStatus.equals("Yes")) {
                    Toast.makeText(getApplicationContext(), "You are blacklist. Please contact to auction holder..!", Toast.LENGTH_LONG).show();
                } else {
                    Bundle b1 = new Bundle();
                    b1.putString("auctioneer", auctioneername);
                    b1.putString("auction_id", auction_id);
                    b1.putString("action_title", action_title);
                    b1.putString("auction_startdate", auction_startdate);
                    b1.putString("auction_starttime", auction_starttime);
                    b1.putString("auction_enddate", auction_enddate);
                    b1.putString("auction_endtime", auction_endtime);
                    b1.putString("no_of_vehicles", no_of_vehicles);
                    b1.putString("auction_type", auctiontype);
                    b1.putString("auctioncontact", auctioncontact);
                    b1.putString("specialcluases", specialcluases);
                    b1.putString("startDateTime", startDateTime);
                    b1.putString("endDateTime", endDateTime);
                    b1.putString("openClose", openClose);
                    b1.putString("showPrice", showPrice);

                    Intent intent = new Intent(getApplicationContext(), LiveAuctionEventBiding.class);
                    intent.putExtras(b1);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
