package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GetPersonDataAdapter;
import autokatta.com.adapter.ManualEnquiryAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.ItemClickListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.request.ManualEnquiryRequest;
import autokatta.com.response.GetPersonDataResponse;
import autokatta.com.response.ManualEnquiryResponse;
import retrofit2.Response;

public class ManualEnquiry extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, ItemClickListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView, mPersonRecyclerView;
    List<ManualEnquiryRequest> mMyGroupsList = new ArrayList<>();
    List<GetPersonDataResponse.Success> mList = new ArrayList<>();
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enquiry);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Enquiry List");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getManualData();
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.manual_swipeRefreshLayout);
                //mPersonSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.person_swipeRefreshLayout);

                mRecyclerView = (RecyclerView) findViewById(R.id.manual_recycler_view);
                mPersonRecyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);

                mFrameLayout = (FrameLayout) findViewById(R.id.manual_enquiry);
                mRecyclerView.setHasFixedSize(true);
                mPersonRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                LinearLayoutManager mLinearLayout1 = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                mRecyclerView.setLayoutManager(mLinearLayout);
                mPersonRecyclerView.setLayoutManager(mLinearLayout1);

                mPersonRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mPersonRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                mSwipeRefreshLayout.setOnRefreshListener(ManualEnquiry.this);
                //mPersonSwipeRefreshLayout.setOnRefreshListener(ManualEnquiry.this);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);

                /*mPersonSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);*/

                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getManualData();
                    }
                });
                /*mPersonSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mPersonSwipeRefreshLayout.setRefreshing(true);
                        //getPersonData();
                    }
                });*/
            }
        });
    }

    /*
    Get Person Data...
     */
    private void getPersonData(String id, String keyword) {
        ApiCall mApiCall = new ApiCall(ManualEnquiry.this, this);
        mApiCall.getPersonData(id, keyword);
    }

    /*
    Get Manual Data...
     */
    private void getManualData() {
        ApiCall mApiCall = new ApiCall(ManualEnquiry.this, this);
        mApiCall.getManualEnquiry(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manual_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mPersonRecyclerView.setVisibility(View.GONE);
                    getManualData();
                } else {
                    onBackPressed();
                }
                break;

            case R.id.add_manual:
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), AddManualEnquiry.class), options.toBundle());
                finish();
                return true;
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
        //mMyGroupsList.clear();
        getManualData();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                mMyGroupsList.clear();
                if (response.body() instanceof ManualEnquiryResponse) {
                    ManualEnquiryResponse manualEnquiry = (ManualEnquiryResponse) response.body();
                    if (manualEnquiry.getSuccess() != null) {

                        /*Used Vehicle*/
                        for (ManualEnquiryResponse.Success.UsedVehicle success : manualEnquiry.getSuccess().getUsedVehicle()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(1);
                            request.setVehicleId(success.getVehicleId());
                            request.setVehicleName(success.getTitle());
                            request.setVehicleCategory(success.getCategory());
                            request.setVehicleSubCategory(success.getSubCategory());
                            request.setVehicleModel(success.getModel());

                            request.setCustomerName("name");
                            request.setCustomerContact("contact");
                            request.setCreatedDate(success.getCreatedDate());
                            request.setFollowupDate(success.getNextFollowupDate());
                            request.setEnquiryStatus(success.getCustEnquiryStatus());

                            if (success.getPrice().equals("") || success.getPrice().isEmpty())
                                request.setVehiclePrice("NA");
                            else
                                request.setVehiclePrice(success.getPrice());

                            request.setEnquiryCount(success.getEnquiryCount());
                            request.setVehicleInventory(success.getCustInventoryType());

                            String[] imageSplit = success.getImage().split(",");
                            request.setVehicleImage(imageSplit[0].substring(0, imageSplit[0].length()));
                            mMyGroupsList.add(request);
                        }

                        /*Products*/
                        for (ManualEnquiryResponse.Success.Product success : manualEnquiry.getSuccess().getProducts()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(2);
                            request.setVehicleId(success.getProductId());
                            request.setProductName(success.getProductName());
                            request.setProductCategory(success.getCategory());
                            request.setProductType(success.getProductType());

                            request.setCustomerName("name");
                            request.setCustomerContact("contact");
                            request.setCreatedDate(success.getCreatedDate());
                            request.setFollowupDate(success.getNextFollowupDate());
                            request.setEnquiryStatus(success.getCustEnquiryStatus());

                            if (success.getPrice().equals("") || success.getPrice().isEmpty())
                                request.setProductPrice("NA");
                            else
                                request.setProductPrice(success.getPrice());


                            request.setEnquiryCount(success.getEnquiryCount());
                            request.setVehicleInventory(success.getCustInventoryType());

                            String[] imageSplit = success.getImages().split(",");
                            request.setProductImage(imageSplit[0].substring(0, imageSplit[0].length()));
                            mMyGroupsList.add(request);
                        }

                        /*Services*/
                        for (ManualEnquiryResponse.Success.Service service : manualEnquiry.getSuccess().getServices()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(3);
                            request.setVehicleId(service.getId());
                            request.setServiceName(service.getName());
                            request.setServiceCategory(service.getCategory());
                            request.setServiceType(service.getType());

                            request.setCustomerName("name");
                            request.setCustomerContact("contact");
                            request.setCreatedDate(service.getCreatedDate());
                            request.setFollowupDate(service.getNextFollowupDate());
                            request.setEnquiryStatus(service.getCustEnquiryStatus());

                            if (service.getPrice().equals("") || service.getPrice().isEmpty())
                                request.setServicePrice("NA");
                            else
                                request.setServicePrice(service.getPrice());


                            request.setEnquiryCount(service.getEnquiryCount());
                            request.setVehicleInventory(service.getCustInventoryType());

                            String[] imageSplit = service.getImages().split(",");
                            request.setServiceImage(imageSplit[0].substring(0, imageSplit[0].length()));
                            mMyGroupsList.add(request);
                        }
                        ManualEnquiryAdapter adapter = new ManualEnquiryAdapter(ManualEnquiry.this, mMyGroupsList);
                        mRecyclerView.setAdapter(adapter);
                        adapter.setClickListener(this);
                        adapter.notifyDataSetChanged();
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(mFrameLayout, manualEnquiry.getError(), Snackbar.LENGTH_SHORT)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getManualData();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.text_color))
                                .setDuration(4000).show();
                    }
                }
                /*Person's information */
                else if (response.body() instanceof GetPersonDataResponse) {
                    GetPersonDataResponse mPersonDataResponse = (GetPersonDataResponse) response.body();
                    if (mPersonDataResponse.getSuccess() != null) {
                        mRecyclerView.setVisibility(View.GONE);
                        mPersonRecyclerView.setVisibility(View.VISIBLE);
                        mList.clear();
                        for (GetPersonDataResponse.Success success : mPersonDataResponse.getSuccess()) {
                            //success.setUsername(success.getUsername());

                            if (success.getUsername().equals("") || success.getUsername().isEmpty())
                                success.setUsername("Unknown");
                            else
                                success.setUsername(success.getUsername());
                            success.setContactNo(success.getContactNo());
                            success.setCity(success.getCity());
                            success.setProfilePic(success.getProfilePic());
                            success.setNextFollowupDate(success.getNextFollowupDate());
                            success.setIsPresent(success.getIsPresent());
                            mList.add(success);
                        }
                        GetPersonDataAdapter adapter = new GetPersonDataAdapter(ManualEnquiry.this, mList);
                        mPersonRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(mFrameLayout, mPersonDataResponse.getError(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mFrameLayout, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mFrameLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mFrameLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof IOException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Manual Enquiry");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View view, int position) {
        ManualEnquiryRequest request = mMyGroupsList.get(position);
        getPersonData(request.getVehicleId(), request.getVehicleInventory());
        Log.i("dsfasd", "->" + request.getVehicleInventory());
        Log.i("dsfaascssd", "->" + request.getVehicleId());
    }
    /*public static boolean isValidPhone(String phone){
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()){
            return true;
        }else{
            return false;
        }
    }*/
}
