package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreServiceAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

public class StoreServiceActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    String Sharedcontact, storeContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    RelativeLayout filterToHide;
    TextView titleText, mNoData;
    List<StoreInventoryResponse.Success.Service> serviceList;
    LinearLayoutManager mLayoutManager;
    StoreServiceAdapter adapter;
    boolean hasView = false;
    int store_id;
    ConnectionDetector mTestConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Store Services");
        mTestConnection = new ConnectionDetector(this);
        mLayoutManager = new LinearLayoutManager(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Sharedcontact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                //titleText = (TextView) mService.findViewById(R.id.titleText);
                filterToHide = (RelativeLayout) findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                //titleText.setText("Services");
                mRecyclerView.setHasFixedSize(true);

                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                if (getIntent().getExtras() != null) {
                    store_id = getIntent().getExtras().getInt("store_id");
                }
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getStoreService(store_id, Sharedcontact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void getStoreService(int store_id, String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(this, this);
            apiCall.getStoreInventory(store_id, sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //errorMessage(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    public void onRefresh() {
        serviceList.clear();
        getStoreService(store_id, Sharedcontact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                serviceList = new ArrayList<>();
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getService().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (StoreInventoryResponse.Success.Service success : storeResponse.getSuccess().getService()) {
                        success.setServiceId(success.getServiceId());
                        success.setServiceName(success.getServiceName());
                        success.setAddedBy(success.getAddedBy());
                        success.setStorecontact(success.getStorecontact());
                        success.setBrandtags(success.getBrandtags());
                        success.setServicePrice(success.getServicePrice());
                        success.setServiceType(success.getServiceType());
                        success.setServiceDetails(success.getServiceDetails());
                        success.setServicetags(success.getServicetags());
                        success.setServiceImages(success.getServiceImages());
                        success.setServicecategory(success.getServicecategory());
                        success.setServicelikestatus(success.getServicelikestatus());
                        success.setServicelikecount(success.getServicelikecount());
                        success.setServicerating(success.getServicerating());
                        success.setSrate(success.getSrate());
                        success.setSrate1(success.getSrate1());
                        success.setSrate2(success.getSrate2());
                        success.setSrate3(success.getSrate3());
                        serviceList.add(success);
                    }
                    adapter = new StoreServiceAdapter(this, serviceList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                // Toast.makeText(getActivity(), R.string._404, Toast.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreServices");
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

    @Override
    protected void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        getStoreService(store_id, Sharedcontact);
    }
}
