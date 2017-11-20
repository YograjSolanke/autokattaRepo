package autokatta.com.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.TransferEnquiriesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.ItemClickListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.request.ManualEnquiryRequest;
import autokatta.com.response.ManualEnquiryResponse;
import autokatta.com.view.EnquiredPersonsActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 16/11/17.
 */

public class TransferEnquiriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, ItemClickListener {


    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<ManualEnquiryRequest> mMyGroupsList = new ArrayList<>();
    FrameLayout mFrameLayout;
    LinearLayout filterLayout;
    RelativeLayout mRelStatus, mRelDate;
    DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    TransferEnquiriesAdapter adapter;

    View mManualEnquiry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mManualEnquiry = inflater.inflate(R.layout.activity_manual_enquiry, container, false);
        getActivity().setTitle("Recived Transfer Enquiry's");

        showDatePicker();
        //   getActivity().startActivity(new Intent(getActivity(), ManualAppIntro.class));
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getManualData();
                mSwipeRefreshLayout = (SwipeRefreshLayout) mManualEnquiry.findViewById(R.id.manual_swipeRefreshLayout);
                //mPersonSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.person_swipeRefreshLayout);

                mRecyclerView = (RecyclerView) mManualEnquiry.findViewById(R.id.manual_recycler_view);
                mFrameLayout = (FrameLayout) mManualEnquiry.findViewById(R.id.manual_enquiry);
                mRecyclerView.setHasFixedSize(true);
                filterLayout = (LinearLayout) mManualEnquiry.findViewById(R.id.below);
                mRelStatus = (RelativeLayout) mManualEnquiry.findViewById(R.id.rel_status);
                mRelDate = (RelativeLayout) mManualEnquiry.findViewById(R.id.rel_date);

                mRelStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu mPopupMenu = new PopupMenu(getActivity(), v);
                        mPopupMenu.getMenuInflater().inflate(R.menu.manual_status_filter, mPopupMenu.getMenu());
                        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.hot:
                                        adapter.getFilter().filter("Hot");
                                        break;
                                    case R.id.cold:
                                        adapter.getFilter().filter("Cold");
                                        break;
                                    case R.id.warm:
                                        adapter.getFilter().filter("Warm");
                                        break;
                                    case R.id.dropped:
                                        adapter.getFilter().filter("Dropped");
                                        break;
                                    case R.id.booked:
                                        adapter.getFilter().filter("Booked");
                                        break;
                                    case R.id.delivered:
                                        adapter.getFilter().filter("Delivered");
                                        break;
                                    case R.id.sold:
                                        adapter.getFilter().filter("Sold");
                                        break;
                                    case R.id.lost_to_competition:
                                        adapter.getFilter().filter("Lost to competition");
                                        break;
                                    case R.id.postponed:
                                        adapter.getFilter().filter("Postponed");
                                        break;
                                    case R.id.fi:
                                        adapter.getFilter().filter("FI");
                                        break;
                                    case R.id.file_login:
                                        adapter.getFilter().filter("File Login");
                                        break;
                                    case R.id.approved:
                                        adapter.getFilter().filter("Approved");
                                        break;
                                    case R.id.disbursed:
                                        adapter.getFilter().filter("Disbursed");
                                        break;
                                    case R.id.file_rejected:
                                        adapter.getFilter().filter("File rejected");
                                        break;
                                    case R.id.pending:
                                        adapter.getFilter().filter("Pending");
                                        break;
                                }
                                return false;
                            }
                        });
                        mPopupMenu.show();
                    }
                });

                mRelDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getActivity());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                mRecyclerView.setLayoutManager(mLinearLayout);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                mSwipeRefreshLayout.setOnRefreshListener(TransferEnquiriesFragment.this);
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

        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.customToast(getActivity(), "Coming soon....");
            }
        });

        return mManualEnquiry;
    }

    /*
    Get Manual Data...
     */
    private void getManualData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getTransferedManualEnquiry(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }


    //@Override
    public void onBackPressed() {
        super.getActivity().onBackPressed();
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
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
                            request.setFinancerName(success.getFinancerName());
                            request.setLoanamount(success.getLoanAmount());
                            request.setLoanpercent(success.getLoanPercent());
                            request.setFinancerstatus(success.getFinancerStatus());
                            request.setEnquiryID(success.getId());
                            request.setOwnerContact(success.getMyContact());
                            request.setOwnerName(success.getOwnerName());


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

                        /*Products*/
                        for (ManualEnquiryResponse.Success.Product success : manualEnquiry.getSuccess().getProducts()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(2);
                            request.setProductId(success.getProductId());
                            request.setProductName(success.getProductName());
                            request.setProductCategory(success.getCategory());
                            request.setProductType(success.getProductType());

                            request.setFinancerName(success.getFinancerName());
                            request.setLoanamount(success.getLoanAmount());
                            request.setLoanpercent(success.getLoanPercent());
                            request.setFinancerstatus(success.getFinancerStatus());
                            request.setEnquiryID(success.getId());
                            request.setOwnerContact(success.getMyContact());
                            request.setOwnerName(success.getOwnerName());





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

                        /*Services*/
                        for (ManualEnquiryResponse.Success.Service service : manualEnquiry.getSuccess().getServices()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(3);
                            request.setServiceId(String.valueOf(service.getService_id()));
                            request.setServiceName(service.getName());
                            request.setServiceCategory(service.getCategory());
                            request.setServiceType(service.getType());

                            request.setFinancerName(service.getFinancerName());
                            request.setLoanamount(service.getLoanAmount());
                            request.setLoanpercent(service.getLoanPercent());
                            request.setFinancerstatus(service.getFinancerStatus());
                            request.setEnquiryID(service.getId());
                            request.setOwnerContact(service.getMyContact());

                            request.setOwnerName(service.getOwnerName());



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


                        /*New Vehicle*/
                        for (ManualEnquiryResponse.Success.NewVehicle success : manualEnquiry.getSuccess().getNewVehicle()) {
                            ManualEnquiryRequest request = new ManualEnquiryRequest();
                            request.setLayoutNo(4);
                            request.setVehicleId(String.valueOf(success.getNewVehicleID()));
                            request.setVehicleName("newVehicle");
                            request.setVehicleCategory(success.getCategoryName());
                            request.setVehicleSubCategory(success.getSubCategoryName());
                            request.setVehicleModel(success.getModelName());

                            request.setFinancerName(success.getFinancerName());
                            request.setLoanamount(success.getLoanAmount());
                            request.setLoanpercent(success.getLoanPercent());
                            request.setFinancerstatus(success.getFinancerStatus());
                            request.setEnquiryID(success.getId());
                            request.setOwnerContact(success.getMyContact());


                            request.setOwnerName(success.getOwnerName());

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

                        adapter = new TransferEnquiriesAdapter(getActivity(), mMyGroupsList);
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
                                .setActionTextColor(ContextCompat.getColor(getActivity(), R.color.text_color))
                                .setDuration(4000).show();
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
        //getPersonData(request.getVehicleId(), request.getVehicleInventory());

        Intent intent = new Intent(getActivity(), EnquiredPersonsActivity.class);

        intent.putExtra("keyword", request.getVehicleInventory());
        switch (request.getVehicleInventory()) {
            case "Products":
                intent.putExtra("id", request.getProductId());
                intent.putExtra("name", request.getProductName());
                intent.putExtra("financername", request.getFinancerName());
                intent.putExtra("loanamount", request.getLoanamount());
                intent.putExtra("loanpercent", request.getLoanpercent());
                intent.putExtra("financestatus", request.getFinancerstatus());
                intent.putExtra("financestatus", request.getFinancerstatus());
                intent.putExtra("enquiryid", request.getEnquiryID());
                intent.putExtra("callfrom", "transferenquiry");
                intent.putExtra("ownercontact", request.getOwnerContact());
                break;
            case "Services":
                intent.putExtra("id", request.getServiceId());
                intent.putExtra("name", request.getServiceName());
                intent.putExtra("financername", request.getFinancerName());
                intent.putExtra("loanamount", request.getLoanamount());
                intent.putExtra("loanpercent", request.getLoanpercent());
                intent.putExtra("financestatus", request.getFinancerstatus());
                intent.putExtra("enquiryid", request.getEnquiryID());
                intent.putExtra("callfrom", "transferenquiry");
                intent.putExtra("ownercontact", request.getOwnerContact());


                break;
            case "Used Vehicle":
                intent.putExtra("id", request.getVehicleId());
                intent.putExtra("name", request.getVehicleName());
                intent.putExtra("financername", request.getFinancerName());
                intent.putExtra("loanamount", request.getLoanamount());
                intent.putExtra("loanpercent", request.getLoanpercent());
                intent.putExtra("financestatus", request.getFinancerstatus());
                intent.putExtra("enquiryid", request.getEnquiryID());
                intent.putExtra("callfrom", "transferenquiry");
                intent.putExtra("ownercontact", request.getOwnerContact());

                break;
            case "New Vehicle":
                intent.putExtra("id", request.getVehicleId());
                intent.putExtra("name", request.getVehicleName());
                intent.putExtra("financername", request.getFinancerName());
                intent.putExtra("loanamount", request.getLoanamount());
                intent.putExtra("loanpercent", request.getLoanpercent());
                intent.putExtra("financestatus", request.getFinancerstatus());
                intent.putExtra("enquiryid", request.getEnquiryID());
                intent.putExtra("callfrom", "transferenquiry");
                intent.putExtra("ownercontact", request.getOwnerContact());

                break;
        }
        startActivity(intent);

        Log.i("dsfasd", "->" + request.getVehicleInventory());
        Log.i("dsfaascssd", "->" + request.getVehicleId());
    }

    /*
    Date picker...
     */
    private void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (dayOfMonth < 10) {
                    if ((monthOfYear + 1) < 11) {
                        if ((monthOfYear + 1) == 10) {
                            String abc = "0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = null;
                            try {
                                date = inputFormat.parse(abc);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String output = outputFormat.format(date);
                            adapter.getFilter().filter(output);
                        } else if (monthOfYear < 10) {
                            String abc = "0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;

                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = null;
                            try {
                                date = inputFormat.parse(abc);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String output = outputFormat.format(date);
                            adapter.getFilter().filter(output);
                        }
                    } else {
                        String abc = "0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        outputFormat.setTimeZone(utc);

                        Date date = null;
                        try {
                            date = inputFormat.parse(abc);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String output = outputFormat.format(date);
                        adapter.getFilter().filter(output);
                    }
                } else if (dayOfMonth >= 10) {
                    if ((monthOfYear + 1) < 11) {
                        if ((monthOfYear + 1) == 10) {
                            String abc = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = null;
                            try {
                                date = inputFormat.parse(abc);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String output = outputFormat.format(date);
                            adapter.getFilter().filter(output);
                        } else if (monthOfYear < 10) {
                            String abc = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;

                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = null;
                            try {
                                date = inputFormat.parse(abc);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String output = outputFormat.format(date);
                            adapter.getFilter().filter(output);
                        }
                    } else {
                        String abc = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        outputFormat.setTimeZone(utc);

                        Date date = null;
                        try {
                            date = inputFormat.parse(abc);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String output = outputFormat.format(date);
                        adapter.getFilter().filter(output);
                    }
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    /*@Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("manualFirstRun", true)) {
            startActivity(new Intent(getApplicationContext(), ManualAppIntro.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("manualFirstRun", false);
            editor.apply();
        }
    }*/
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
