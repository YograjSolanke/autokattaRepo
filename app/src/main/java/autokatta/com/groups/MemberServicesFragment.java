package autokatta.com.groups;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreServiceAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.response.StoreInventoryResponse.Success.Service;
import retrofit2.Response;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberServicesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mService;
    String mGroupId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<Service> serviceList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    StoreServiceAdapter adapter;
    TextView mNoData;
    String mBundleContact;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mService = inflater.inflate(R.layout.member_product_fragment, container, false);
        return mService;
    }

    private void getServices(String GroupId) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupService(GroupId, mBundleContact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            errorMessage(getActivity(), getString(R.string.no_internet));
       }
    }

    @Override
    public void onRefresh() {
        getServices(mGroupId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getServices(mGroupId);
                _hasLoadedOnce = true;
            }
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                serviceList.clear();
                String storeContact = null;
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getService().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (StoreInventoryResponse.Success.Service success : storeResponse.getSuccess().getService()) {
                        success.setServiceId(success.getServiceId());
                        success.setServiceName(success.getServiceName());
                        success.setBrandtags(success.getBrandtags());
                        success.setServicePrice(success.getServicePrice());
                        success.setServiceType(success.getServiceType());
                        success.setServiceDetails(success.getServiceDetails());
                        success.setServicetags(success.getServicetags());
                        success.setServiceImages(success.getServiceImages());
                        success.setServicecategory(success.getServicecategory());
                        success.setServicelikestatus(success.getServicelikestatus());
                        success.setServicelikecount(success.getServicelikecount());
                        success.setServicerating(success.getServicerating());
                        success.setSrate(success.getSrate());
                        success.setSrate1(success.getSrate1());
                        success.setSrate2(success.getSrate2());
                        success.setSrate3(success.getSrate3());
                        success.setSrate3(success.getStorecontact());
                        storeContact = success.getStorecontact();
                        serviceList.add(success);
                    }
                    adapter = new StoreServiceAdapter(getActivity(), serviceList, mBundleContact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "memberservicefragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mNoData = (TextView) mService.findViewById(R.id.no_category);

                mSwipeRefreshLayout = (SwipeRefreshLayout) mService.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mService.findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                Bundle getBundle = getArguments();
                mGroupId = getBundle.getString("bundle_GroupId");
                mBundleContact = getBundle.getString("Rcontact");

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getServices(mGroupId);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getServices(mGroupId);
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
