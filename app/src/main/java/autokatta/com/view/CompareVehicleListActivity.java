package autokatta.com.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.CompareVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.VehicleForCompareResponse;
import retrofit2.Response;

public class CompareVehicleListActivity extends AppCompatActivity implements RequestNotifier {

    RecyclerView mHorizontalRecyclerView;
    TextView textcount;
    String vehicle_ids;
    List<VehicleForCompareResponse.Success.UsedVehicle> mVehicleCompareList = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_vehicle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.comphorizontl);
        textcount = (TextView) findViewById(R.id.countcomp);


        mHorizontalRecyclerView.setHasFixedSize(true);
        mHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (getIntent().getExtras() != null) {
            vehicle_ids = getIntent().getExtras().getString("vehicle_ids");
            getComparisonData(vehicle_ids);
        }
    }

    private void getComparisonData(String vehicle_ids) {
        ApiCall mApiCall = new ApiCall(this, this);
        dialog.show();
        mApiCall.getVehiclesForCompare(vehicle_ids);
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
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                VehicleForCompareResponse forCompareResponse = (VehicleForCompareResponse) response.body();
                if (!forCompareResponse.getSuccess().getUsedVehicle().isEmpty()) {
                    //mNoData.setVisibility(View.GONE);
                    mVehicleCompareList.clear();
                    for (VehicleForCompareResponse.Success.UsedVehicle success : forCompareResponse.getSuccess().getUsedVehicle()) {

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
                        success.setYearOfManufacture(success.getYearOfManufacture());
                        success.setTaxPaidUpto(success.getTaxPaidUpto());
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

                        mVehicleCompareList.add(success);

                    }
                    textcount.setText(String.valueOf(mVehicleCompareList.size()));

                    CompareVehicleListAdapter mAdapter = new CompareVehicleListAdapter(this, mVehicleCompareList);
                    mHorizontalRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    /*mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);*/
                    CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
                }
            } else {
                //mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
            }
        } else {
            // mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_internet));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "CompareVehicleListActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
