package autokatta.com.auction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.adapter.PreviewAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAuctionEventResponse;
import autokatta.com.view.ShareWithinAppActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewGoingEvents extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    //ListView mListView;
    RecyclerView mRecyclerView;
    FloatingActionMenu mFloatingActionMenu;
    FloatingActionButton mCall, mMail;
    Button mGoing, mIgnore, mShare, mGoLive;
    CollapsingToolbarLayout mCollapsingToolbar;
    TextView mLiveTimer, mStartDate, mStartTime, mEndDate, mEndTime, mAuctionText, mCloseOpenType, mCategory, mLocation;
    String allDetails = "", keyword;
int auction_id;
    String  whoseAuction = "";
    String contact, auctioneername, auction_startdate, auction_starttime, auction_enddate, auction_endtime,
            no_of_vehicles, auctioncontact, action_title, ignoreGoingStatus, startDateTime, endDateTime, specialcluases,
            blackListStatus, openClose, auctiontype, showPrice, strCategory, strLocation;

    CountDownTimer cdt;
    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    List<GetAuctionEventResponse.Vehicle> vehicles = new ArrayList<>();
    Boolean boolGoing = true;
    private ConnectionDetector mConnectionDetector = new ConnectionDetector(PreviewGoingEvents.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_live_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        auctioneername = getIntent().getExtras().getString("auctioneer");
        auction_id = getIntent().getExtras().getInt("auction_id");
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
        strCategory = getIntent().getExtras().getString("category");
        strLocation = getIntent().getExtras().getString("location");

        mRecyclerView = (RecyclerView) findViewById(R.id.auction_event_recycler_view);
        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_red);
        mCall = (FloatingActionButton) findViewById(R.id.call_c);
        mMail = (FloatingActionButton) findViewById(R.id.mail);
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
        mCategory = (TextView) findViewById(R.id.category);
        mLocation = (TextView) findViewById(R.id.location);

        mGoLive.setOnClickListener(this);
        mGoing.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mCall.setOnClickListener(this);
        mMail.setOnClickListener(this);

        mLiveTimer.setVisibility(View.GONE);
        mIgnore.setVisibility(View.GONE);
        mGoing.setVisibility(View.GONE);
        mGoLive.setVisibility(View.GONE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && mCall.isShown()) {
                    mFloatingActionMenu.hideMenuButton(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mFloatingActionMenu.showMenuButton(true);
                }
            }
        });
        /*
        Set Data from Bundles...
         */

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getAuctionPreviewById();

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(PreviewGoingEvents.this);
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(PreviewGoingEvents.this, LinearLayoutManager.VERTICAL));

                mCollapsingToolbar.setTitle(auctioneername);
                mStartDate.setText(auction_startdate);
                mStartTime.setText(auction_starttime);
                mEndDate.setText(auction_enddate);
                mEndTime.setText(auction_endtime);
                mCloseOpenType.setText(openClose + " " + "Type Auction");
                //mAuctionText.setText(getString(R.string.going_auction));
                mAuctionText.setText("Title: " + action_title);
                mCategory.setText("Category: " + strCategory);
                //mCategory.setText(strCategory);
                mLocation.setText(strLocation);

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

                if (getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", "").equals(auctioncontact)) {
                    whoseAuction = "myauction";
                } else
                    whoseAuction = "otherauction";
                //mGoLive.setVisibility(View.VISIBLE);
                /*if (ignoreGoingStatus.equals("going")) {
                    mGoing.setEnabled(false);
                    mGoLive.setEnabled(true);
                    mIgnore.setVisibility(View.GONE);
                    mGoing.setVisibility(View.GONE);
                } else if (ignoreGoingStatus.equals("ignore")) {
                    mIgnore.setEnabled(false);
                    mGoing.setEnabled(false);
                }*/
            }
        });
    }

    /*
    Get Auction Preview By Id...
     */
    private void getAuctionPreviewById() {
        ApiCall mApiCall = new ApiCall(PreviewGoingEvents.this, this);
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
                PreviewAuctionAdapter adapter = new PreviewAuctionAdapter(PreviewGoingEvents.this, vehicles, auction_id, showPrice,
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                // CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Preview Going Events Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("1"))
                CustomToast.customToast(getApplicationContext(), "Mail Sent Successfully");
            else
                CustomToast.customToast(getApplicationContext(), "Problem in sending mail");

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));

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
                    b1.putInt("auction_id", auction_id);
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
                    b1.putString("ignoreGoingStatus", ignoreGoingStatus);
                    b1.putString("blackListStatus", blackListStatus);
                    b1.putString("category", strCategory);
                    b1.putString("location", strLocation);
                    /*b1.putString("tabNo", "3");
                    b1.putBoolean("isPayEMD", boolGoing);*/
                    Intent intent = new Intent(getApplicationContext(), LiveAuctionEventBiding.class);
                    intent.putExtras(b1);
                    startActivity(intent);
                }
                break;

            case R.id.btn_going:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreviewGoingEvents.this);
                alertDialogBuilder
                        .setMessage("Pay EMD or auction platform fees")
                        .setCancelable(false)
                        .setPositiveButton("Now",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        try {
                                            if (mConnectionDetector.isConnectedToInternet()) {
                                                //JSON to Gson conversion
                                                Gson gson = new GsonBuilder()
                                                        .setLenient()
                                                        .create();
                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(getString(R.string.base_url))
                                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                                        .client(initLog().build())
                                                        .build();

                                                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                Call<String> add = serviceApi.addIgnoreGoingMe(getSharedPreferences(getString(R.string.my_preference),
                                                        Context.MODE_PRIVATE).getString("loginContact", ""), auction_id, 0, 0, 0, 0, "going");
                                                add.enqueue(new Callback<String>() {
                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                        if (response.isSuccessful()) {
                                                            if (response.body() != null) {
                                                                if (response.body().equals("success")) {
                                                                    mIgnore.setVisibility(View.GONE);
                                                                    mGoing.setVisibility(View.GONE);
                                                                    mGoLive.setEnabled(true);
                                                                    //paymentMethodCall(null, null, "");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("No", "Response");
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {
                                                        t.printStackTrace();
                                                    }
                                                });
                                            } else
                                                CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton("Later",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        //dialog.cancel();
                                        boolGoing = false;
                                        try {
                                            if (mConnectionDetector.isConnectedToInternet()) {
                                                //JSON to Gson conversion
                                                Gson gson = new GsonBuilder()
                                                        .setLenient()
                                                        .create();
                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(getString(R.string.base_url))
                                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                                        .client(initLog().build())
                                                        .build();

                                                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                Call<String> add = serviceApi.addIgnoreGoingMe(getSharedPreferences(getString(R.string.my_preference),
                                                        Context.MODE_PRIVATE).getString("loginContact", ""), auction_id, 0, 0, 0, 0, "ignore");
                                                add.enqueue(new Callback<String>() {
                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                        if (response.isSuccessful()) {
                                                            if (response.body() != null) {
                                                                if (response.body().equals("success")) {
                                                                    mIgnore.setVisibility(View.GONE);
                                                                    mGoing.setVisibility(View.GONE);
                                                                    mGoLive.setEnabled(true);

                                                                }
                                                            }
                                                        } else {
                                                            Log.e("No", "Response");
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {
                                                        t.printStackTrace();
                                                    }
                                                });
                                            } else
                                                CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;

            case R.id.btn_ignore:
                try {
                    if (mConnectionDetector.isConnectedToInternet()) {
                        //JSON to Gson conversion
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(getString(R.string.base_url))
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .client(initLog().build())
                                .build();

                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                        Call<String> add = serviceApi.addIgnoreGoingMe(getSharedPreferences(getString(R.string.my_preference),
                                Context.MODE_PRIVATE).getString("loginContact", ""), auction_id, 0, 0, 0, 0, "going");
                        add.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().equals("success")) {
                                            mIgnore.setEnabled(false);
                                            mGoing.setEnabled(true);
                                            mGoLive.setEnabled(false);
                                        }
                                    }
                                } else {
                                    Log.e("No", "Response");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_share:
                shareButton();
                break;
            case R.id.call_c:
                Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + auctioncontact));
                try {
                    startActivity(in);
                } catch (android.content.ActivityNotFoundException ex) {
                    System.out.println("No Activity Found For Call in Preview Live events\n");
                }
                break;

            case R.id.mail:
                //mail();
                mailAuctionData();
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

    /*
   On Share Butoon Clicked....
    */
    private void shareButton() {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setTitle("Share");
        alert.setMessage("with Autokatta or to other?");
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("Autokatta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                allDetails = action_title + "="
                        + no_of_vehicles + "="
                        + auction_enddate + "=" +
                        auction_endtime + "=" +
                        auctiontype + "=" +
                        "0" + "=" + "0" + "=" + "a";
                String mAuction = "auction";


                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putInt("Share_auction_id", auction_id).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", mAuction).apply();

                Intent i = new Intent(PreviewGoingEvents.this, ShareWithinAppActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (contact.equals(auctioncontact))
                    whoseAuction = "myauction";
                else
                    //whoseAuction = "otherauction";
                    whoseAuction = auctioneername;

                allDetails = "Auction Title: " + action_title + "\n" +
                        "No Of Vehicle: " + no_of_vehicles + "\n" +
                        "Auction End Date: " + auction_enddate + "\n" +
                        "Auction End Time: " + auction_endtime + "\n" +
                        "Auction Type: " + auctiontype + "\n" +
                       /* "0" + "\n"+//.auctionGoingcount+"="+
                        "0" + "\n"+//auctionIgnorecount*/
                        "Auctioneer: " + whoseAuction;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                intent.putExtra(Intent.EXTRA_TEXT, allDetails);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();
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

    private void mailAuctionData() {
        ApiCall apiCall = new ApiCall(PreviewGoingEvents.this, this);
        apiCall.SendAuctionMail(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""), auction_id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(PreviewGoingEvents.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        }*/
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
