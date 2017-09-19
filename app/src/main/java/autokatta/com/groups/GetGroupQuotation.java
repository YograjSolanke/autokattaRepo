package autokatta.com.groups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import autokatta.com.adapter.GetGroupQuotationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupQuotationResponse;
import retrofit2.Response;

public class GetGroupQuotation extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<GetGroupQuotationResponse.Success.UsedVehicle> groupQuotationList = new ArrayList<>();
    TextView mNoData;
    ConnectionDetector mTestConnection;
    String mLoginContact;
    String mGrpType, mClassName, mGrpName, mBundleContact, mTabIndex;
    int mGrpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_simple_listview);
        setTitle("Get Quotation");
        mNoData = (TextView) findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(GetGroupQuotation.this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                Intent i = getIntent();
                mGrpType = i.getStringExtra("grouptype");
                mClassName = i.getStringExtra("className");
                mGrpId = i.getIntExtra("bundle_GroupId", 0);
                mGrpName = i.getStringExtra("bundle_GroupName");
                mTabIndex = i.getStringExtra("tabIndex");
                mBundleContact = i.getStringExtra("bundle_Contact");
                Log.i("GetQuote", mGrpType + " " + mClassName + " " + mGrpId + " " + mTabIndex + " " + mBundleContact);

                mTestConnection = new ConnectionDetector(GetGroupQuotation.this);
                mLoginContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                        getString("loginContact", "");

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getGroupQuotation(mGrpId);
                    }
                });
            }
        });
    }

    private void getGroupQuotation(int mGrpId) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.GetGroupQuotation(mGrpId);
        } else
            CustomToast.customToast(this, getString(R.string.no_internet));
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
    public void onRefresh() {
        getGroupQuotation(mGrpId);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetGroupQuotationResponse quotationResponse = (GetGroupQuotationResponse) response.body();
                if (!quotationResponse.getSuccess().getUsedVehicle().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    groupQuotationList.clear();
                    for (GetGroupQuotationResponse.Success.UsedVehicle success : quotationResponse.getSuccess().getUsedVehicle()) {

                        success.setVehicleId(success.getVehicleId());
                        success.setContactNo(success.getContactNo());
                        success.setPrice(success.getPrice());
                        success.setCategory(success.getCategory());
                        success.setSubCategory(success.getSubCategory());
                        success.setModel(success.getModel());
                        success.setManufacturer(success.getManufacturer());
                        success.setRtoCity(success.getRtoCity());
                        success.setLocationCity(success.getLocationCity());
                        success.setYearOfRegistration(success.getYearOfRegistration());
                        success.setYearOfManufaturer(success.getYearOfManufaturer());
                        success.setColor(success.getColor());
                        success.setRegistrationNumber(success.getRegistrationNumber());
                        success.setRcAvailable(success.getRcAvailable());
                        success.setInsuranceValid(success.getInsuranceValid());
                        success.setInsuranceIdv(success.getInsuranceIdv());
                        success.setTaxValidity(success.getTaxValidity());
                        success.setFitnessValidity(success.getFitnessValidity());
                        success.setPermitValidity(success.getPermitValidity());
                        success.setFualType(success.getFualType());
                        success.setSeatingCapacity(success.getSeatingCapacity());
                        success.setPermit(success.getPermit());
                        success.setKmsRunning(success.getKmsRunning());
                        success.setNoOfOwners(success.getNoOfOwners());
                        success.setHypothication(success.getHypothication());
                        success.setEngineNo(success.getEngineNo());
                        success.setChassisNo(success.getChassisNo());
                        success.setDrive(success.getDrive());
                        success.setTransmission(success.getTransmission());
                        success.setBodyType(success.getBodyType());
                        success.setBoatType(success.getBoatType());
                        success.setRvType(success.getRvType());
                        success.setTyreCondition(success.getTyreCondition());
                        success.setBusType(success.getBusType());
                        success.setAirCondition(success.getAirCondition());
                        success.setInvoice(success.getInvoice());
                        success.setImplements(success.getImplements());
                        success.setApplication(success.getApplication());
                        success.setViewcount(success.getViewcount());
                        success.setCallcount(success.getCallcount());
                        success.setBodyManufacturer(success.getBodyManufacturer());
                        success.setSeatManufacturer(success.getSeatManufacturer());
                        success.setStatus(success.getStatus());
                        String vehicleImage = success.getImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            success.setImage(items[0]);
                            /*for (String item : items) {
                                notification.setUpVehicleImage(item);
                            }*/
                        } else {
                            success.setImage(vehicleImage);
                        }

                        success.setQuotationID(success.getQuotationID());
                        success.setQuoteReservePrice(success.getQuoteReservePrice());
                        success.setDeadLineDate(success.getDeadLineDate());
                        success.setGroupIDs(success.getGroupIDs());
                        success.setType(success.getType());

                        groupQuotationList.add(success);

                    }
                    GetGroupQuotationAdapter mAdapter = new GetGroupQuotationAdapter(this, groupQuotationList, mLoginContact,
                            mGrpId);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }


    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(this, getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(this, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(this, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(this, getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(this, getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Get Group Quotation");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
