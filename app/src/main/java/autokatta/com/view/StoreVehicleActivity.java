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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

public class StoreVehicleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    String Sharedcontact, storeContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout filterToHide;
    RecyclerView mRecyclerView;
    TextView titleText, mNoData;
    List<StoreInventoryResponse.Success.Vehicle> vehicleList;
    LinearLayoutManager mLayoutManager;
    StoreVehicleAdapter adapter;
    int store_id;
    boolean hasMoreView = false;
    ConnectionDetector mTestConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Store Vehicles");
        mTestConnection = new ConnectionDetector(this);
        mLayoutManager = new LinearLayoutManager(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
                filterToHide = (RelativeLayout) findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                //titleText = (TextView) mVehicle.findViewById(R.id.titleText);
                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                //titleText.setText("Vehicles");
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
                        getStoreVehicles(store_id, Sharedcontact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getStoreVehicles(int store_id, String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(this, this);
            apiCall.getStoreInventory(store_id, sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        vehicleList.clear();
        getStoreVehicles(store_id, Sharedcontact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                mSwipeRefreshLayout.setRefreshing(false);
                vehicleList = new ArrayList<>();
                mNoData.setVisibility(View.GONE);
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getVehicle().isEmpty()) {
                    for (StoreInventoryResponse.Success.Vehicle success : storeResponse.getSuccess().getVehicle()) {
                        success.setVehicleId(success.getVehicleId());
                        success.setTitle(success.getTitle());
                        success.setPrice(success.getPrice());
                        success.setCategory(success.getCategory());
                        storeContact = success.getStorecontact();
                        success.setModel(success.getModel());
                        success.setManufacturer(success.getManufacturer());
                        success.setBuyerLeads(success.getBuyerLeads());

                        if (success.getRegno().equals(""))
                            success.setRegno("NA");
                        else
                            success.setRegno(success.getRegno());
                        success.setYear(success.getYear());
                        success.setLocation(success.getLocation());
                        success.setStorecontact(success.getStorecontact());
                        success.setRto(success.getRto());
                        success.setKms(success.getKms());
                        success.setImages(success.getImages());
                        success.setDate(success.getDate().replaceAll("T", " "));

                        success.setGroupIDs(success.getGroupIDs());
                        success.setStoreIDs(success.getStoreIDs());

                        vehicleList.add(success);
                    }
                    adapter = new StoreVehicleAdapter(this, vehicleList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                // CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreVehicles");
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
