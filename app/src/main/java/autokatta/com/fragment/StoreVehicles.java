package autokatta.com.fragment;

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
import autokatta.com.adapter.StoreVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreVehicles extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mVehicle;
    String Sharedcontact, storeContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout filterToHide;
    RecyclerView mRecyclerView;
    TextView titleText, mNoData;
    List<StoreInventoryResponse.Success.Vehicle> vehicleList;
    LinearLayoutManager mLayoutManager;
    StoreVehicleAdapter adapter;
    int store_id;
    boolean hasMoreView = false;
    ConnectionDetector mTestConnection;
    Activity mActivity;

    public StoreVehicles() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicle = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mVehicle;
    }

    private void getStoreVehicles(int store_id, String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getStoreInventory(store_id, sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        vehicleList.clear();
        getStoreVehicles(store_id, Sharedcontact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                mSwipeRefreshLayout.setRefreshing(false);
                vehicleList = new ArrayList<>();
                mNoData.setVisibility(View.GONE);
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getVehicle().isEmpty()) {
                    for (StoreInventoryResponse.Success.Vehicle success : storeResponse.getSuccess().getVehicle()) {
                        success.setVehicleId(success.getVehicleId());
                        success.setTitle(success.getTitle());
                        success.setPrice(success.getPrice());
                        success.setCategory(success.getCategory());
                        storeContact = success.getStorecontact();
                        success.setModel(success.getModel());
                        success.setManufacturer(success.getManufacturer());
                        success.setBuyerLeads(success.getBuyerLeads());

                        if (success.getRegno().equals(""))
                            success.setRegno("NA");
                        else
                            success.setRegno(success.getRegno());
                        success.setYear(success.getYear());
                        success.setLocation(success.getLocation());
                        success.setRto(success.getRto());
                        success.setKms(success.getKms());
                        success.setImages(success.getImages());
                        success.setDate(success.getDate().replaceAll("T", " "));

                        success.setGroupIDs(success.getGroupIDs());
                        success.setStoreIDs(success.getStoreIDs());

                        vehicleList.add(success);
                    }
                    adapter = new StoreVehicleAdapter(getActivity(), vehicleList, Sharedcontact, storeContact);
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
                    , "StoreVehicles");
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
            if (isVisibleToUser && !hasMoreView) {
                getStoreVehicles(store_id, Sharedcontact);
                hasMoreView = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) mVehicle.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mVehicle.findViewById(R.id.recycler_view);
                filterToHide = (RelativeLayout) mVehicle.findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                //titleText = (TextView) mVehicle.findViewById(R.id.titleText);
                mNoData = (TextView) mVehicle.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                //titleText.setText("Vehicles");
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                Bundle bundle = getArguments();
                store_id = bundle.getInt("store_id");
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getStoreVehicles(store_id, Sharedcontact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = (Activity) context;
        }
    }

}