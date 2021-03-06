package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import autokatta.com.adapter.RecentVisitsAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.GetMyRecentVisitsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 7/12/17.
 */

public class RecentVisitsFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mRecentView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mNoData;
    boolean hasViewCreated = false;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;
    List<GetMyRecentVisitsResponse.Success.MyRecentVisit> mRecentVisitList = new ArrayList<>();

    public RecentVisitsFragment() {
        //Empty Constuctor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecentView = inflater.inflate(R.layout.fragment_notification, container, false);
        return mRecentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) mRecentView.findViewById(R.id.no_category);

                mRecyclerView = (RecyclerView) mRecentView.findViewById(R.id.recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mRecentView.findViewById(R.id.swipeRefreshLayout);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                /*code to ascending or descending list*/
                mLayoutManager.setReverseLayout(false);
                mLayoutManager.setStackFromEnd(false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getRecentVisitsData();
                    }
                });

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getRecentVisitsData();
                hasViewCreated = true;
            }
        }
    }

    private void getRecentVisitsData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.GetMyRecentVisits(mLoginContact);
    }


    @Override
    public void onRefresh() {
        getRecentVisitsData();
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                GetMyRecentVisitsResponse visitsResponse = (GetMyRecentVisitsResponse) response.body();
                if (!visitsResponse.getSuccess().getMyRecentVisit().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    mRecentVisitList.clear();

                    for (GetMyRecentVisitsResponse.Success.MyRecentVisit myRecentVisit : visitsResponse.getSuccess().getMyRecentVisit()) {

                        myRecentVisit.setWatchedItemID(myRecentVisit.getWatchedItemID());
                        myRecentVisit.setLayOut(myRecentVisit.getLayOut());
                        myRecentVisit.setNewVehicleID(myRecentVisit.getNewVehicleID());
                        myRecentVisit.setNewVehicleImage(myRecentVisit.getNewVehicleImage());
                        myRecentVisit.setUploadVehicleID(myRecentVisit.getUploadVehicleID());
                        myRecentVisit.setUploadVehicleTitile(myRecentVisit.getUploadVehicleTitile());
                        myRecentVisit.setStoreServiceID(myRecentVisit.getStoreServiceID());
                        myRecentVisit.setStoreServiceName(myRecentVisit.getStoreServiceName());
                        myRecentVisit.setProductID(myRecentVisit.getProductID());
                        myRecentVisit.setProductName(myRecentVisit.getProductName());
                        myRecentVisit.setStoreID(myRecentVisit.getStoreID());
                        myRecentVisit.setStoreImage(myRecentVisit.getStoreImage());
                        myRecentVisit.setStoreName(myRecentVisit.getStoreName());
                        myRecentVisit.setProfileID(myRecentVisit.getProfileID());
                        myRecentVisit.setUserName(myRecentVisit.getUserName());
                        myRecentVisit.setProfilePicture(myRecentVisit.getProfilePicture());
                        myRecentVisit.setContactNo(myRecentVisit.getContactNo());

                        //myRecentVisit.setUploadVehicleImage(myRecentVisit.getUploadVehicleImage());
                        String vehicleImage = myRecentVisit.getUploadVehicleImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            myRecentVisit.setUploadVehicleImage(items[0]);
                        } else {
                            myRecentVisit.setUploadVehicleImage(vehicleImage);
                        }

                        //myRecentVisit.setStoreServiceImage(myRecentVisit.getStoreServiceImage());
                        String serviceImage = myRecentVisit.getStoreServiceImage();
                        if (serviceImage.contains(",")) {
                            String[] items = serviceImage.split(",");
                            myRecentVisit.setStoreServiceImage(items[0]);
                        } else {
                            myRecentVisit.setStoreServiceImage(serviceImage);
                        }

                        myRecentVisit.setProductImage(myRecentVisit.getProductImage());
                        String productImage = myRecentVisit.getStoreServiceImage();
                        if (productImage.contains(",")) {
                            String[] items = productImage.split(",");
                            myRecentVisit.setStoreServiceImage(items[0]);
                        } else {
                            myRecentVisit.setStoreServiceImage(productImage);
                        }

                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(myRecentVisit.getDate());
                            String output = outputFormat.format(date);
                            myRecentVisit.setDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mRecentVisitList.add(myRecentVisit);

                    }

                    RecentVisitsAdapter mAdapter = new RecentVisitsAdapter(getActivity(), mRecentVisitList, mLoginContact);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
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
            Log.i("Check Class-", "RecentVisitsFragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
