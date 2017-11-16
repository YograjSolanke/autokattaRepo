package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.MySoldAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SoldVehicleResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 29/7/17.
 */

public class SoldVehicle extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    View mSoldVehicle;
    ConnectionDetector mTestConnection;
    TextView mNoData;
    List<SoldVehicleResponse.Success.SoldVehicle> myUploadedVehiclesResponseList = new ArrayList<>();
    boolean hasView = false;
    String myContact;
    int mStoreID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSoldVehicle = inflater.inflate(R.layout.fragment_sold_vehicle, container, false);
        return mSoldVehicle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle mBundle = getArguments();
                myContact = mBundle.getString("bundle_contact");
                mStoreID = mBundle.getInt("bundle_storeId");

                mTestConnection = new ConnectionDetector(getActivity());
                mSwipeRefreshLayout = (SwipeRefreshLayout) mSoldVehicle.findViewById(R.id.swipeRefreshLayoutSold);
                mRecyclerView = (RecyclerView) mSoldVehicle.findViewById(R.id.recyclerSold);
                mNoData = (TextView) mSoldVehicle.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);


                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getSoldVehicle(myContact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getSoldVehicle(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getSoldVehicle(loginContact, mStoreID);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), "No Internet connection");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasView) {
                getSoldVehicle(myContact);
                hasView = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getSoldVehicle(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                myUploadedVehiclesResponseList.clear();
                SoldVehicleResponse soldResponse = (SoldVehicleResponse) response.body();
                if (!soldResponse.getSuccess().getSoldVehicle().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (SoldVehicleResponse.Success.SoldVehicle myVehicleSuccess : soldResponse.getSuccess().getSoldVehicle()) {
                        myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                        myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                        myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                        myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                        myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                        myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                        myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                        myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                        myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                        myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                        myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                        myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());

                        if (myVehicleSuccess.getRegistrationNumber().equals(""))
                            myVehicleSuccess.setRegistrationNumber("NA");
                        else
                            myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());

                        myVehicleSuccess.setVersion(myVehicleSuccess.getVersion());
                        myVehicleSuccess.setRcAvailable(myVehicleSuccess.getRcAvailable());

                        myVehicleSuccess.setSoldToContact(myVehicleSuccess.getSoldToContact());
                        myVehicleSuccess.setCustName(myVehicleSuccess.getCustName());
                        myVehicleSuccess.setAddress(myVehicleSuccess.getAddress());
                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(myVehicleSuccess.getSoldDate());
                            String output = outputFormat.format(date);
                            myVehicleSuccess.setSoldDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String vehicleImage = myVehicleSuccess.getImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            myVehicleSuccess.setImage(items[0]);
                            /*for (String item : items) {
                                notification.setUpVehicleImage(item);
                            }*/
                        } else {
                            myVehicleSuccess.setImage(vehicleImage);
                        }


                        myUploadedVehiclesResponseList.add(myVehicleSuccess);
                    }
                    Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);
                    MySoldAdapter adapter = new MySoldAdapter(getActivity(), myUploadedVehiclesResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "SoldVehicle");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
