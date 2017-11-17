package autokatta.com.enquiries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.ManualEnquiryAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.ItemClickListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.request.ManualEnquiryRequest;
import autokatta.com.response.ManualEnquiryResponse;
import retrofit2.Response;

public class TodaysFollowUp extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, ItemClickListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<ManualEnquiryRequest> mMyGroupsList = new ArrayList<>();
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_follow_up);
        setTitle("Today's follow-up");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                formattedDate = df.format(c.getTime());
                getManualData();
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.todays_swipeRefreshLayout);
                mRecyclerView = (RecyclerView) findViewById(R.id.todays_recycler_view);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayout);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                mSwipeRefreshLayout.setOnRefreshListener(TodaysFollowUp.this);
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getManualData();
                    }
                });
            }
        });
    }

    /*
    Get Manual Data...
     */
    private void getManualData() {
        ApiCall mApiCall = new ApiCall(TodaysFollowUp.this, this);
        mApiCall.getManualEnquiry(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
                            if (success.getNextFollowupDate().equals(formattedDate)) {
                                request.setLayoutNo(1);
                                request.setVehicleId(success.getVehicleId());
                                request.setVehicleName(success.getTitle());
                                request.setVehicleCategory(success.getCategory());
                                request.setVehicleSubCategory(success.getSubCategory());
                                request.setVehicleModel(success.getModel());

                                request.setCustomerName("name");
                                request.setCustomerContact("contact");
                                //request.setCreatedDate(success.getCreatedDate());
                                //  request.setFollowupDate(success.getNextFollowupDate());
                                request.setEnquiryStatus(success.getCustEnquiryStatus());

                            /*Date format*/
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(success.getNextFollowupDate());
                                    Date date1 = inputFormat.parse(success.getCreatedDate());
                                    //System.out.println("jjj"+date);
                                    String output = outputFormat.format(date);
                                    String output1 = outputFormat.format(date1);
                                    //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                    request.setFollowupDate(output);
                                    request.setCreatedDate(output1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


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
                        }

                        /*Products*/
                        for (ManualEnquiryResponse.Success.Product success : manualEnquiry.getSuccess().getProducts()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            if (success.getNextFollowupDate().equals(formattedDate)) {
                                request.setLayoutNo(2);
                                request.setProductId(success.getProductId());
                                request.setProductName(success.getProductName());
                                request.setProductCategory(success.getCategory());
                                request.setProductType(success.getProductType());

                                request.setCustomerName("name");
                                request.setCustomerContact("contact");
                                request.setCreatedDate(success.getCreatedDate());
                                request.setFollowupDate(success.getNextFollowupDate());
                                request.setEnquiryStatus(success.getCustEnquiryStatus());


                              /*Date format*/
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(success.getNextFollowupDate());
                                    Date date1 = inputFormat.parse(success.getCreatedDate());
                                    //System.out.println("jjj"+date);
                                    String output = outputFormat.format(date);
                                    String output1 = outputFormat.format(date1);
                                    //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                    request.setFollowupDate(output);
                                    request.setCreatedDate(output1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                        }

                        /*Services*/
                        for (ManualEnquiryResponse.Success.Service service : manualEnquiry.getSuccess().getServices()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            if (service.getNextFollowupDate().equals(formattedDate)) {
                                request.setLayoutNo(3);
                                request.setServiceId(service.getId());
                                request.setServiceName(service.getName());
                                request.setServiceCategory(service.getCategory());
                                request.setServiceType(service.getType());

                                request.setCustomerName("name");
                                request.setCustomerContact("contact");
                                request.setCreatedDate(service.getCreatedDate());
                                request.setFollowupDate(service.getNextFollowupDate());
                                request.setEnquiryStatus(service.getCustEnquiryStatus());

                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(service.getNextFollowupDate());
                                    Date date1 = inputFormat.parse(service.getCreatedDate());
                                    //System.out.println("jjj"+date);
                                    String output = outputFormat.format(date);
                                    String output1 = outputFormat.format(date1);
                                    //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                    request.setFollowupDate(output);
                                    request.setCreatedDate(output1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                        }

                        /*New Vehicle*/
                        for (ManualEnquiryResponse.Success.NewVehicle success : manualEnquiry.getSuccess().getNewVehicle()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(4);
                            request.setVehicleId(String.valueOf(success.getNewVehicleID()));
                            request.setVehicleName("newVehicle");
                            request.setVehicleCategory(success.getCategoryName());
                            request.setVehicleSubCategory(success.getSubCategoryName());
                            request.setVehicleModel(success.getModelName());

                            request.setCustomerName("name");
                            request.setCustomerContact("contact");
                            //request.setCreatedDate(success.getCreatedDate());
                            //  request.setFollowupDate(success.getNextFollowupDate());
                            request.setEnquiryStatus(success.getCustEnquiryStatus());

                            /*Date format*/
                            try {
                                TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                //format of date coming from services
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                                inputFormat.setTimeZone(utc);

                                //format of date which we want to show
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                                outputFormat.setTimeZone(utc);

                                Date date = inputFormat.parse(success.getNextFollowupDate());
                                Date date1 = inputFormat.parse(success.getCreatedDate());

                                String output = outputFormat.format(date);
                                String output1 = outputFormat.format(date1);

                                request.setFollowupDate(output);
                                request.setCreatedDate(output1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            /*if (success.getPrice().equals("") || success.getPrice().isEmpty())
                                request.setVehiclePrice("NA");
                            else*/
                            request.setVehiclePrice("NA");

                            request.setEnquiryCount(success.getEnquiryCount());
                            request.setVehicleInventory(success.getCustInventoryType());

                            String[] imageSplit = success.getImage().split(",");
                            request.setVehicleImage(imageSplit[0].substring(0, imageSplit[0].length()));
                            mMyGroupsList.add(request);

                        }

                        ManualEnquiryAdapter adapter = new ManualEnquiryAdapter(TodaysFollowUp.this, mMyGroupsList);
                        mRecyclerView.setAdapter(adapter);
                        adapter.setClickListener(this);
                        adapter.notifyDataSetChanged();
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
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

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View view, int position) {
        ManualEnquiryRequest request = mMyGroupsList.get(position);
        //getPersonData(request.getVehicleId(), request.getVehicleInventory());

        Intent intent = new Intent(TodaysFollowUp.this, TodaysFollowUpPersonList.class);

        intent.putExtra("keyword", request.getVehicleInventory());
        switch (request.getVehicleInventory()) {
            case "Products":
                intent.putExtra("id", request.getProductId());
                intent.putExtra("name", request.getProductName());
                intent.putExtra("enquiryid", request.getEnquiryID());
                break;
            case "Services":
                intent.putExtra("id", request.getServiceId());
                intent.putExtra("name", request.getServiceName());
                intent.putExtra("enquiryid", request.getEnquiryID());
                break;
            case "Used Vehicle":
                intent.putExtra("id", request.getVehicleId());
                intent.putExtra("name", request.getVehicleName());
                intent.putExtra("enquiryid", request.getEnquiryID());
                break;
            case "New Vehicle":
                intent.putExtra("id", request.getVehicleId());
                intent.putExtra("name", request.getVehicleName());
                intent.putExtra("enquiryid", request.getEnquiryID());
                break;
        }
        startActivity(intent);
    }
}
