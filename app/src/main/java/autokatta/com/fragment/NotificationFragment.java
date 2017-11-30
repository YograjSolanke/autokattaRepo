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
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.FcmNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.GetFCMNotificationResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 20/11/17.
 */

public class NotificationFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mNotificationView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mWallNotify;
    TextView mNoData;
    boolean _hasLoadedOnce = false;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;
    List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList = new ArrayList<>();

    public NotificationFragment() {

        //Empty Constuctor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mNotificationView = inflater.inflate(R.layout.fragment_notification, container, false);
        return mNotificationView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    /*dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");*/
                mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) mNotificationView.findViewById(R.id.no_category);
                // layout = (LinearLayout) mNotificationView.findViewById(R.id.no_connection);

                mRecyclerView = (RecyclerView) mNotificationView.findViewById(R.id.recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mNotificationView.findViewById(R.id.swipeRefreshLayout);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                //mLayoutManager.setReverseLayout(true);
                //mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                // mRecyclerView.setAdapter(adapter);

                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getNotificationData();
                    }
                });

            }
        });
    }

    private void getNotificationData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.GetFCMNotificationOnUserBased(mLoginContact);
    }


    @Override
    public void onRefresh() {
        getNotificationData();
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                GetFCMNotificationResponse notificationResponse = (GetFCMNotificationResponse) response.body();
                if (!notificationResponse.getSuccess().getFCMNotification().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);

                    for (GetFCMNotificationResponse.Success.FCMNotification fcmNotification : notificationResponse.getSuccess().getFCMNotification()) {
                        fcmNotification.setFCMNotificationID(fcmNotification.getFCMNotificationID());
                        fcmNotification.setReceiverContact(fcmNotification.getReceiverContact());
                        fcmNotification.setMessage(fcmNotification.getMessage());
                        fcmNotification.setDeviceToken(fcmNotification.getDeviceToken());
                        fcmNotification.setContactNo(fcmNotification.getContactNo());
                        fcmNotification.setUserName(fcmNotification.getUserName());
                        fcmNotification.setStoreID(fcmNotification.getStoreID());
                        fcmNotification.setGroupID(fcmNotification.getGroupID());
                        fcmNotification.setVehicleID(fcmNotification.getVehicleID());
                        fcmNotification.setProductID(fcmNotification.getProductID());
                        fcmNotification.setServiceID(fcmNotification.getServiceID());
                        fcmNotification.setStatusID(fcmNotification.getStatusID());
                        fcmNotification.setSearchID(fcmNotification.getSearchID());
                        mFcmNotiList.add(fcmNotification);

                    }

                    FcmNotificationAdapter mAdapter = new FcmNotificationAdapter(getActivity(), mFcmNotiList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_data));
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
            //layout.setVisibility(View.VISIBLE);
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            // layout.setVisibility(View.VISIBLE);
        } else {
            Log.i("Check Class-", "Wall Notification Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
