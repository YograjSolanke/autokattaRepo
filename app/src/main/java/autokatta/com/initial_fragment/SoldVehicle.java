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

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MySoldAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SoldVehicleResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

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
                        getSoldVehicle(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                .getString("loginContact", ""));
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getSoldVehicle(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getSoldVehicle(loginContact);
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
                getSoldVehicle(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));
                hasView = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getSoldVehicle(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
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
                        //myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                        //myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                        //myVehicleSuccess.setImages(myVehicleSuccess.getImages());
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
                        //myVehicleSuccess.setNoOfOwner(myVehicleSuccess.getNoOfOwner());

                        //myVehicleSuccess.setGroupIDs(myVehicleSuccess.getGroupIDs());
                        //myVehicleSuccess.setStoreIDs(myVehicleSuccess.getStoreIDs());


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

    }

    @Override
    public void notifyString(String str) {

    }
}
