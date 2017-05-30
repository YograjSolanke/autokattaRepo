package autokatta.com.fragment_profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import autokatta.com.adapter.MyVehiclesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.GetOwnVehiclesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 13/4/17.
 */

public class MyVehicles extends android.support.v4.app.Fragment implements RequestNotifier,SwipeRefreshLayout.OnRefreshListener{
    View mMyVehicles;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<GetOwnVehiclesResponse.Success>mGetOwnVehiclesResponse=new ArrayList<>();
    MyVehiclesAdapter adapter;
    ApiCall mApiCall;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyVehicles = inflater.inflate(R.layout.fragment_profile_myvehicles, container, false);
        /*addVehicle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), VehicleUpload.class);
                startActivity(i);
                getActivity().finish();
            }
        });*/

        return mMyVehicles;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mGetOwnVehiclesResponse.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                GetOwnVehiclesResponse ownVehiclesResponse = (GetOwnVehiclesResponse) response.body();
                if (!ownVehiclesResponse.getSuccess().isEmpty()) {
                    for (GetOwnVehiclesResponse.Success success : ownVehiclesResponse.getSuccess()) {
                        success.setVehicleType(success.getVehicleType());
                        success.setYear(success.getYear());
                        success.setId(success.getId());
                        success.setBrand(success.getBrand());
                        success.setModelNo(success.getModelNo());
                        success.setVersion(success.getVersion());
                        success.setSubcategory(success.getSubcategory());
                        success.setVehiNo(success.getVehiNo());
                        success.setTaxValidity(success.getTaxValidity());
                        success.setPermitValidity(success.getPermitValidity());
                        success.setFitnessValidity(success.getFitnessValidity());
                        success.setInsurance(success.getInsurance());
                        success.setPuc(success.getPuc());
                        success.setLastServiceDate(success.getLastServiceDate());
                        success.setNextServiceDate(success.getNextServiceDate());
                        mGetOwnVehiclesResponse.add(success);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter = new MyVehiclesAdapter(getActivity(), mGetOwnVehiclesResponse);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_LONG).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof ConnectException) {
            error();
        } else if (error instanceof UnknownHostException) {
            error();
        } else {
            Log.i("Check Class-", "My Vehicles");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.getOwnVehicles(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
        } else {
            error();
        }
    }

    private void error() {
        Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMyVehicles.findViewById(R.id.swipeRefreshLayoutMyVehicles);
                mRecyclerView = (RecyclerView) mMyVehicles.findViewById(R.id.recyclermyVehicles);
                //FloatingActionButton addVehicle = (FloatingActionButton) mMyVehicles.findViewById(R.id.add_vehicle);

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
                        if (mTestConnection.isConnectedToInternet()) {
                            mApiCall.getOwnVehicles(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                        } else {
                            error();
                        }
                    }
                });
            }
        });
        mApiCall = new ApiCall(getActivity(), this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                if (mTestConnection.isConnectedToInternet()) {
                    mApiCall.getOwnVehicles(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                } else {
                    error();
                }
                _hasLoadedOnce = true;
            }
        }
    }
}
