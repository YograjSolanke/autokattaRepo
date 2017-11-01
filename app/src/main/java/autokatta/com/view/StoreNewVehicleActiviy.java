package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreNewVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import retrofit2.Response;

public class StoreNewVehicleActiviy extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    String myContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mNoData;
    List<NewVehicleAllResponse.Success.NewVehicle> newVehicleList = new ArrayList<>();
    StoreNewVehicleAdapter adapter;
    int store_id;
    ConnectionDetector mTestConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_new_vehicle_activiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTestConnection = new ConnectionDetector(this);

        setTitle("Store New Vehicle's");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                store_id = getIntent().getExtras().getInt("store_id", 0);
                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(StoreNewVehicleActiviy.this);
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getStoreNewVehicles(store_id, myContact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    private void getStoreNewVehicles(int store_id, String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(this, this);
            apiCall.getStoreNewVehiclesList(store_id);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getStoreNewVehicles(store_id, myContact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                NewVehicleAllResponse vehicleAllResponse = (NewVehicleAllResponse) response.body();
                if (!vehicleAllResponse.getSuccess().getNewVehicle().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    newVehicleList.clear();

                    for (NewVehicleAllResponse.Success.NewVehicle success : vehicleAllResponse.getSuccess().getNewVehicle()) {

                        success.setNewVehicleID(success.getNewVehicleID());
                        success.setCategoryID(success.getCategoryID());
                        success.setSubCategoryID(success.getSubCategoryID());
                        success.setBrandID(success.getBrandID());
                        success.setModelID(success.getModelID());
                        success.setVersionID(success.getVersionID());
                        success.setCategoryName(success.getCategoryName());
                        success.setSubCategoryName(success.getSubCategoryName());
                        success.setBrandName(success.getBrandName());
                        success.setModelName(success.getModelName());
                        success.setVersionName(success.getVersionName());

                        success.setThreePointLinkage((success.getThreePointLinkage() == null ||
                                success.getThreePointLinkage().equalsIgnoreCase("null") ||
                                success.getThreePointLinkage().equalsIgnoreCase("")) ? "NA" : success.getThreePointLinkage());

                        success.setABS((success.getABS() == null ||
                                success.getABS().equalsIgnoreCase("null") ||
                                success.getABS().equalsIgnoreCase("")) ? "NA" : success.getABS());


                        //success.setPrice((success.getPrice().equalsIgnoreCase("")) ? "NA" : success.getPrice());


                            /*String vehicleImage = success.getImage();
                            if (vehicleImage.contains(",")) {
                                String[] items = vehicleImage.split(",");
                                success.setImage(items[0]);
                            } else {
                                success.setImage(vehicleImage);
                            }*/

                        newVehicleList.add(success);

                    }

                    adapter = new StoreNewVehicleAdapter(this, newVehicleList, myContact);
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
                    , "StoreNewVehicleActivity");
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
