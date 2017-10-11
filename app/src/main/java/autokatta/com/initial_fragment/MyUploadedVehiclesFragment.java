package autokatta.com.initial_fragment;

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
import autokatta.com.adapter.MyUploadedVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUploadedVehiclesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehiclesFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<MyUploadedVehiclesResponse.Success> myUploadedVehiclesResponseList = new ArrayList<>();
    View myVehicles;
    String myContact;
    TextView mNoData;
    ConnectionDetector mTestConnection;
    boolean hasView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.fragment_my_uploaded_vehicles, container, false);
        return myVehicles;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mTestConnection = new ConnectionDetector(getActivity());
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", "");

                mSwipeRefreshLayout = (SwipeRefreshLayout) myVehicles.findViewById(R.id.swipeRefreshLayoutMyUploadedVehicle);
                mRecyclerView = (RecyclerView) myVehicles.findViewById(R.id.recyclerMyUploadedVehicle);
                mNoData = (TextView) myVehicles.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                //mLinearLayoutManager.setReverseLayout(true);
                //mLinearLayoutManager.setStackFromEnd(true);
                mLinearLayoutManager.setSmoothScrollbarEnabled(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getMyUploadedVehicles(myContact);
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
            if (isVisibleToUser && !hasView) {
                getMyUploadedVehicles(myContact);
                hasView = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getMyUploadedVehicles(myContact);
    }

    private void getMyUploadedVehicles(String myContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.MyUploadedVehicles(myContact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                myUploadedVehiclesResponseList.clear();
                MyUploadedVehiclesResponse myVehicleResponse = (MyUploadedVehiclesResponse) response.body();
                if (!myVehicleResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (MyUploadedVehiclesResponse.Success myVehicleSuccess : myVehicleResponse.getSuccess()) {
                        myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                        myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                        myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                        myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                        myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                        myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                        myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                        myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                        myVehicleSuccess.setImages(myVehicleSuccess.getImages());
                        myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                        myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                        myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                        myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                        myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                        myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());
                        myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());
                        if (myVehicleSuccess.getRegistrationNumber().equals(""))
                            myVehicleSuccess.setRegistrationNumber("NA");
                        else
                            myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());

                        myVehicleSuccess.setVersion(myVehicleSuccess.getVersion());
                        myVehicleSuccess.setRcAvailable(myVehicleSuccess.getRcAvailable());
                        myVehicleSuccess.setNoOfOwner(myVehicleSuccess.getNoOfOwner());

                        myVehicleSuccess.setGroupIDs(myVehicleSuccess.getGroupIDs());
                        myVehicleSuccess.setStoreIDs(myVehicleSuccess.getStoreIDs());


                        myUploadedVehiclesResponseList.add(myVehicleSuccess);
                    }
                    Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);
                    MyUploadedVehicleAdapter adapter = new MyUploadedVehicleAdapter(getActivity(), myUploadedVehiclesResponseList);
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
                    , "MyUploadedVehiclesActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
