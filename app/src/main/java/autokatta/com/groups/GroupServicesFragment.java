package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupServiceAdpater;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupServicesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mService;
    String myContact, mGroupType;
    int mGroupId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<StoreInventoryResponse.Success.Service> serviceList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    GroupServiceAdpater adapter;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;
    TextView mNoData;
    Activity activity;
    RelativeLayout filterToHide;

    public GroupServicesFragment() {
        //empty fragments...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mService = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mService;
    }

    private void getServices(int GroupId) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getGroupService(GroupId, myContact);
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getServices(mGroupId);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                serviceList.clear();
                String storeContact = "";
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
                        success.setStorecontact(success.getStorecontact());
                        storeContact = success.getStorecontact();
                        serviceList.add(success);
                    }
                    adapter = new GroupServiceAdpater(getActivity(), serviceList, myContact, storeContact, mGroupType);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (activity != null) {
                activity = getActivity();
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "groupservicesfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                getServices(mGroupId);
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mTestConnection = new ConnectionDetector(getActivity());
                mSwipeRefreshLayout = (SwipeRefreshLayout) mService.findViewById(R.id.swipeRefreshLayout);
                mNoData = (TextView) mService.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                filterToHide = (RelativeLayout) mService.findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                mRecyclerView = (RecyclerView) mService.findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                Bundle bundle = getArguments();
                if (bundle != null) {
                    mGroupType = bundle.getString("grouptype");
                    mGroupId = bundle.getInt("bundle_GroupId");
                    if (bundle.getString("bundle_Contact") != null) {
                        myContact = bundle.getString("bundle_Contact");
                    }

                }
                Log.i("Group", "ServiceContact->" + myContact);
                Log.i("Group", "ServiceType->" + mGroupType);

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
}
