package autokatta.com.events;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

/**
 * Created by ak-003 on 6/4/17.
 */

public class PreviewMyActiveAuctionActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    private String strAuctionId = "", strAuctionTitle = "", strVehicleCount = "", strStartDate = "", strStartTime = "",
            strEndDate = "", strEndTime = "", strSpecialClauses = "", strStartdatetime = "", strEnddatetime = "", strParticipantcount = "",
            strSpecialClause = "", strCategory = "", strLocation = "";

    CountDownTimer cdt;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    CollapsingToolbarLayout mCollapsingToolbar;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    TextView txtVehicle, txtEndDate, txtEndTime, txtStartTime, txtStartDate, txtParticipant, txtTimer, txtCategory, txtLocation;
    FloatingActionButton btnSpecialclause, btnLive, btnMail;
    AuctionVehiclesFragment auctionVehiclesFragment;
    AuctionParticipantsFragment auctionParticipantsFragment;
    AuctionAnalyticsFragment auctionAnalyticsFragment;
    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_myactive_auction);
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
        mTabLayout = (TabLayout) findViewById(R.id.preview_myactive_auction_tabs);
        mViewPager = (ViewPager) findViewById(R.id.preview_myactive_auction_viewpager);
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
        btnLive = (FloatingActionButton) findViewById(R.id.gotolive);
        btnMail = (FloatingActionButton) findViewById(R.id.mail);

        btnSpecialclause.setOnClickListener(this);
        btnLive.setOnClickListener(this);
        btnMail.setOnClickListener(this);

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
                    mCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
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

                    final TextView tv = txtTimer;
                    cdt = counters.get(txtTimer);
                    if (cdt != null) {
                        cdt.cancel();
                        cdt = null;
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date futureDate = dateFormat.parse(strEnddatetime);
                        Date currentDate = dateFormat.parse(strEnddatetime);
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

                    } catch (Exception e) {
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
        adapter.addFragment(auctionVehiclesFragment, "Vehicles");
        adapter.addFragment(auctionParticipantsFragment, "Participants");
        adapter.addFragment(auctionAnalyticsFragment, "Analytics");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        if (cdt != null) {
            cdt.cancel(); // for CountDownTimer
            cdt = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clauses:

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(PreviewMyActiveAuctionActivity.this);
                alertDialog.setTitle("Special Clauses");

                final TextView input = new TextView(PreviewMyActiveAuctionActivity.this);
                input.setText(strSpecialClauses.replaceAll(",", "\n"));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMarginStart(30);
                input.setBackgroundColor(Color.LTGRAY);
                input.setLayoutParams(lp);
                input.setPadding(40, 40, 40, 40);
                input.setGravity(Gravity.CENTER_VERTICAL);
                input.setTextColor(Color.parseColor("#110359"));
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

            case R.id.mail:
                mailAuctionData();
                break;

            case R.id.gotolive:
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

                //finish();
                Intent intent = new Intent(PreviewMyActiveAuctionActivity.this, PreviewNextMyActiveAuctionActivity.class);
                intent.putExtras(b);
                startActivity(intent);

                break;
        }
    }

    private void mailAuctionData() {
        ApiCall apiCall = new ApiCall(PreviewMyActiveAuctionActivity.this, this);
        apiCall.SendAuctionMail(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""), strAuctionId);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Preview My Active Auction Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.startsWith("1"))
                CustomToast.customToast(getApplicationContext(),"Mail Sent Successfully");
            else
            CustomToast.customToast(getApplicationContext(),"Problem in sending mail");

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));

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
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
    }
}

