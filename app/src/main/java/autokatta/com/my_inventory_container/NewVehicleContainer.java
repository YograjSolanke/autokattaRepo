package autokatta.com.my_inventory_container;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.NewVehicleContainerAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import autokatta.com.view.AddNewVehicleActivity;
import retrofit2.Response;

public class NewVehicleContainer extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    String myContact = "";
    List<NewVehicleAllResponse.Success.NewVehicle> newVehicleList = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mNoData;
    ConnectionDetector mConnectionDetector;
    FloatingActionButton mAddVehicle;
    NewVehicleContainerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("New Vehicle List");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");

                mAddVehicle = (FloatingActionButton) findViewById(R.id.fab);
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) findViewById(R.id.newVehicleListRecycler);
                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(NewVehicleContainer.this);
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
                        getNewVehicleList(myContact);
                    }
                });

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mConnectionDetector = new ConnectionDetector(this);

        mAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewVehicleActivity.class));
            }
        });

    }

    private void getNewVehicleList(String myContact) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.GetNewVehicleDetailsForContact(myContact);
    }

    @Override
    public void onRefresh() {
        getNewVehicleList(myContact);
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

                    mAdapter = new NewVehicleContainerAdapter(this, newVehicleList, myContact);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
                CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_internet));
        }
    }


    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNoData.setVisibility(View.VISIBLE);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "New Vehicle Container");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
