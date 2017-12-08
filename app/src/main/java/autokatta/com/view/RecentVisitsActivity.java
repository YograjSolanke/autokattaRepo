package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.RecentVisitsAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.GetMyRecentVisitsResponse;
import retrofit2.Response;

public class RecentVisitsActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mNoData;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;
    List<GetMyRecentVisitsResponse.Success.MyRecentVisit> mRecentVisitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_visits);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("My Recent Visits");

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) findViewById(R.id.no_category);

                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(RecentVisitsActivity.this);
                /*code to ascending or descending list*/
                mLayoutManager.setReverseLayout(false);
                mLayoutManager.setStackFromEnd(false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getRecentVisitsData();
                    }
                });

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void getRecentVisitsData() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.GetMyRecentVisits(mLoginContact);
    }


    @Override
    public void onRefresh() {
        getRecentVisitsData();
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                GetMyRecentVisitsResponse visitsResponse = (GetMyRecentVisitsResponse) response.body();
                if (!visitsResponse.getSuccess().getMyRecentVisit().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    mRecentVisitList.clear();

                    for (GetMyRecentVisitsResponse.Success.MyRecentVisit myRecentVisit : visitsResponse.getSuccess().getMyRecentVisit()) {

                        myRecentVisit.setWatchedItemID(myRecentVisit.getWatchedItemID());
                        myRecentVisit.setLayOut(myRecentVisit.getLayOut());
                        myRecentVisit.setNewVehicleID(myRecentVisit.getNewVehicleID());
                        myRecentVisit.setNewVehicleImage(myRecentVisit.getNewVehicleImage());
                        myRecentVisit.setUploadVehicleID(myRecentVisit.getUploadVehicleID());
                        myRecentVisit.setUploadVehicleTitile(myRecentVisit.getUploadVehicleTitile());
                        myRecentVisit.setStoreServiceID(myRecentVisit.getStoreServiceID());
                        myRecentVisit.setStoreServiceName(myRecentVisit.getStoreServiceName());
                        myRecentVisit.setProductID(myRecentVisit.getProductID());
                        myRecentVisit.setProductName(myRecentVisit.getProductName());
                        myRecentVisit.setStoreID(myRecentVisit.getStoreID());
                        myRecentVisit.setStoreImage(myRecentVisit.getStoreImage());
                        myRecentVisit.setStoreName(myRecentVisit.getStoreName());
                        myRecentVisit.setProfileID(myRecentVisit.getProfileID());
                        myRecentVisit.setUserName(myRecentVisit.getUserName());
                        myRecentVisit.setProfilePicture(myRecentVisit.getProfilePicture());
                        myRecentVisit.setContactNo(myRecentVisit.getContactNo());

                        //myRecentVisit.setUploadVehicleImage(myRecentVisit.getUploadVehicleImage());
                        String vehicleImage = myRecentVisit.getUploadVehicleImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            myRecentVisit.setUploadVehicleImage(items[0]);
                        } else {
                            myRecentVisit.setUploadVehicleImage(vehicleImage);
                        }

                        //myRecentVisit.setStoreServiceImage(myRecentVisit.getStoreServiceImage());
                        String serviceImage = myRecentVisit.getStoreServiceImage();
                        if (serviceImage.contains(",")) {
                            String[] items = serviceImage.split(",");
                            myRecentVisit.setStoreServiceImage(items[0]);
                        } else {
                            myRecentVisit.setStoreServiceImage(serviceImage);
                        }

                        myRecentVisit.setProductImage(myRecentVisit.getProductImage());
                        String productImage = myRecentVisit.getStoreServiceImage();
                        if (productImage.contains(",")) {
                            String[] items = productImage.split(",");
                            myRecentVisit.setStoreServiceImage(items[0]);
                        } else {
                            myRecentVisit.setStoreServiceImage(productImage);
                        }

                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(myRecentVisit.getDate());
                            String output = outputFormat.format(date);
                            myRecentVisit.setDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mRecentVisitList.add(myRecentVisit);

                    }

                    RecentVisitsAdapter mAdapter = new RecentVisitsAdapter(RecentVisitsActivity.this, mRecentVisitList, mLoginContact);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string.no_internet));
        }

    }

    @Override
    public void notifyError(Throwable error) {

        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(RecentVisitsActivity.this, getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "RecentVisitsActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

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
    }

}
