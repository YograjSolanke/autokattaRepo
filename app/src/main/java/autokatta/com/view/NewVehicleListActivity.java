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
import autokatta.com.adapter.NewVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import retrofit2.Response;

public class NewVehicleListActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    ApiCall apiCall;
    String myContact;
    List<NewVehicleAllResponse.Success.NewVehicle> newVehicleList = new ArrayList<>();
    int categoryId = 0, subCategoryId = 0, brandId = 0, modelId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.newVehicleResultRecycler);

        apiCall = new ApiCall(this, this);
        mTestConnection = new ConnectionDetector(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getIntent().getExtras() != null) {
                    categoryId = getIntent().getExtras().getInt("categoryId");
                    subCategoryId = getIntent().getExtras().getInt("subCategoryId");
                    brandId = getIntent().getExtras().getInt("brandId");
                    modelId = getIntent().getExtras().getInt("modelId");
                }

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                }

                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(NewVehicleListActivity.this);
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
                        getNewVehicleList(categoryId, subCategoryId, brandId, modelId);
                    }
                });
            }
        });

    }

    private void getNewVehicleList(int categoryId, int subCategoryId, int brandId,
                                   int modelId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getNewVehicleList(categoryId, subCategoryId, brandId, modelId);
    }

    @Override
    public void onRefresh() {
        getNewVehicleList(categoryId, subCategoryId, brandId, modelId);
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

                    NewVehicleListAdapter mAdapter = new NewVehicleListAdapter(this, newVehicleList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_internet));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "NewVehicleListActivity");
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
