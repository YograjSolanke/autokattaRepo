package autokatta.com.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.SearchedNewVehicleResultAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleSearchResponse;
import retrofit2.Response;

public class SearchedNewVehicleResultActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ConnectionDetector mTestConnection;
    //LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    ImageView filterData;
    String myContact;
    int categoryId = 0, subCategoryId = 0, brandId = 0, modelId = 0, versionId = 0;
    List<NewVehicleSearchResponse.Success> mSearchNewVehicleList = new ArrayList<>();
    SearchedNewVehicleResultAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_new_vehicle_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        filterData = (ImageView) findViewById(R.id.filter);
        filterData.setVisibility(View.GONE);

        mNoData = (TextView) findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.searchResultRecycler);
        mTestConnection = new ConnectionDetector(this);
        // mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                }

                categoryId = getIntent().getIntExtra("categoryId", 0);
                subCategoryId = getIntent().getIntExtra("subCategoryId", 0);
                brandId = getIntent().getIntExtra("brandId", 0);
                modelId = getIntent().getIntExtra("modelId", 0);
                versionId = getIntent().getIntExtra("versionId", 0);

                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);
                //mLinearLayoutManager.setStackFromEnd(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(SearchedNewVehicleResultActivity.this);
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
                        getNewVehicleSearchResult(categoryId, subCategoryId, brandId, modelId, versionId, myContact);
                    }
                });
            }
        });


        /*
        More Items click listener...
         */
        filterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.custom_more_used_vehicle, null);
                ImageView mClose = (ImageView) view.findViewById(R.id.close);
                Button mEnquiry = (Button) view.findViewById(R.id.Enquiry);
                Button mQuotation = (Button) view.findViewById(R.id.quotation);
                mEnquiry.setVisibility(View.GONE);
                mQuotation.setVisibility(View.GONE);
                Button mTransferStock = (Button) view.findViewById(R.id.transfer_stock);
                Button mSold = (Button) view.findViewById(R.id.delete);
                Button mViewQuote = (Button) view.findViewById(R.id.view_quotation);
                mViewQuote.setVisibility(View.GONE);
                mTransferStock.setText("Low To High");
                mSold.setText("High To Low");

                final Dialog mBottomSheetDialog = new Dialog(SearchedNewVehicleResultActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

            }
        });


    }

    private void getNewVehicleSearchResult(int categoryId, int subCategoryId, int brandId, int modelId,
                                           int versionId, String myContact) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getNewVehicleSearchResult(categoryId, subCategoryId, brandId, modelId, versionId, myContact, 0);
    }

    @Override
    public void onRefresh() {
        getNewVehicleSearchResult(categoryId, subCategoryId, brandId, modelId, versionId, myContact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                NewVehicleSearchResponse vehicleSearchResponse = (NewVehicleSearchResponse) response.body();
                if (!vehicleSearchResponse.getSuccess().isEmpty()) {
                    mSearchNewVehicleList.clear();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);

                    for (NewVehicleSearchResponse.Success success : vehicleSearchResponse.getSuccess()) {

                        success.setStoreID(success.getStoreID());
                        success.setDescription(success.getDescription());
                        success.setAddress(success.getAddress());
                        success.setBrands(success.getBrands());
                        success.setBrandTags(success.getBrandTags());
                        success.setCategory(success.getCategory());
                        success.setContactNo(success.getContactNo());
                        success.setStoreImage(success.getStoreImage());
                        success.setCoverImage(success.getCoverImage());
                        success.setCreationDate(success.getCreationDate());
                        success.setLocation(success.getLocation());
                        success.setLatitude(success.getLatitude());
                        success.setLongitude(success.getLongitude());
                        success.setModifiedDate(success.getModifiedDate());
                        success.setRating(success.getRating());
                        success.setStatus(success.getStatus());
                        success.setName(success.getName());
                        success.setOpenTime(success.getOpenTime());
                        success.setStoreType(success.getStoreType());
                        success.setVehicleID(success.getVehicleID());
                        success.setWebSite(success.getWebSite());
                        success.setWorkingDays(success.getWorkingDays());
                        success.setAverageRate(success.getAverageRate());

                        mSearchNewVehicleList.add(success);
                    }
                    mAdapter = new SearchedNewVehicleResultAdapter(this, mSearchNewVehicleList, myContact);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }


            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNoData.setVisibility(View.VISIBLE);
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
                    , "SearchedNewVehicleResultActivity");
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
