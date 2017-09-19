package autokatta.com.fragment_profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetOwnVehiclesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 13/4/17.
 */

public class MyVehicles extends android.support.v4.app.Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mMyVehicles;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<GetOwnVehiclesResponse.Success> mGetOwnVehiclesResponse = new ArrayList<>();
    MyVehiclesAdapter adapter;
    ApiCall mApiCall;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;
    TextView mNoData;
    private ProgressDialog dialog;
    Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyVehicles = inflater.inflate(R.layout.fragment_profile_myvehicles, container, false);
        return mMyVehicles;
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
                mNoData = (TextView) mMyVehicles.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");

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
                        getOwnVehicles();
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
                getOwnVehicles();
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getOwnVehicles();
    }

    private void getOwnVehicles() {
        if (mTestConnection.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getOwnVehicles(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                mGetOwnVehiclesResponse.clear();
                mNoData.setVisibility(View.GONE);
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
                        if (success.getTaxValidity() != null) {
                            success.setTaxValidity(success.getTaxValidity().replace("T00:00:00", ""));
                        }
                        if (success.getPermitValidity() != null) {
                            success.setPermitValidity(success.getPermitValidity().replace("T00:00:00", ""));
                        }
                        if (success.getFitnessValidity() != null) {
                            success.setFitnessValidity(success.getFitnessValidity().replace("T00:00:00", ""));
                        }
                        if (success.getInsurance() != null) {
                            success.setInsurance(success.getInsurance().replace("T00:00:00", ""));
                        }
                        if (success.getPuc() != null) {
                            success.setPuc(success.getPuc().replace("T00:00:00", ""));
                        }
                        if (success.getLastServiceDate() != null) {
                            success.setLastServiceDate(success.getLastServiceDate().replace("T00:00:00", ""));
                        }
                        if (success.getNextServiceDate() != null) {
                            success.setNextServiceDate(success.getNextServiceDate().replace("T00:00:00", ""));
                        }
                        if (success.getUploaddate() != null) {
                            success.setUploaddate(success.getUploaddate().replace("T00:00:00", ""));
                        }
                        mGetOwnVehiclesResponse.add(success);
                    }
                    adapter = new MyVehiclesAdapter(getActivity(), mGetOwnVehiclesResponse);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
//                    CustomToast.customToast(getActivity(), getString(R.string.no_data));
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
//                if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string._404_));
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
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            if (isAdded()) {
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            }
        } else if (error instanceof NullPointerException) {
//            if (isAdded()) {
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
//            }
        } else if (error instanceof ClassCastException) {
//            if (isAdded()) {
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
//            }
        } else if (error instanceof ConnectException) {
            if (isAdded()) {
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            }
        } else if (error instanceof UnknownHostException) {
            if (isAdded()) {
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            }
        } else {
            Log.i("Check Class-", "My Vehicles");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getOwnVehicles();

    }
}
