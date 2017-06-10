package autokatta.com.events;

import android.app.Activity;
import android.content.Context;
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
import autokatta.com.adapter.PreviewAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAuctionEventResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 6/4/17.
 */

public class AuctionVehiclesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    public AuctionVehiclesFragment() {
        //empty constructor
    }

    boolean hasViewCreated = false;
    TextView mNoData;

    View mAuctionVehicles;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String strAuctionId = "";
    List<GetAuctionEventResponse.Vehicle> vehicles = new ArrayList<>();
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuctionVehicles = inflater.inflate(R.layout.fragment_simple_listview, container, false);


        return mAuctionVehicles;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mAuctionVehicles.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) mAuctionVehicles.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mAuctionVehicles.findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mTestConnection = new ConnectionDetector(getActivity());
                    Bundle bundle = getArguments();
                    strAuctionId = bundle.getString("auctionid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            /*apiCall.MyActiveAuction(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                                    "ACTIVE");*/
                            getAuctionVehicle(strAuctionId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        getAuctionVehicle(strAuctionId);
    }

    private void getAuctionVehicle(String strAuctionId) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getAuctionEvent(strAuctionId);
        } else {
            errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                vehicles.clear();

                GetAuctionEventResponse mAuctionEventResponse = (GetAuctionEventResponse) response.body();
                if (!mAuctionEventResponse.getSuccess().getVehicles().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (GetAuctionEventResponse.Vehicle vehicle : mAuctionEventResponse.getSuccess().getVehicles()) {
                        vehicle.setVehicleId(vehicle.getVehicleId());
                        vehicle.setTitle(vehicle.getTitle());
                        vehicle.setCategory(vehicle.getCategory());
                        vehicle.setSubCategory(vehicle.getSubCategory());
                        vehicle.setBrand(vehicle.getBrand());
                        vehicle.setModel(vehicle.getModel());
                        vehicle.setLocationCity(vehicle.getLocationCity());
                        vehicle.setColor(vehicle.getColor());
                        vehicle.setImage(vehicle.getImage());
                        vehicle.setRegNo(vehicle.getRegNo());
                        vehicle.setRtoCity(vehicle.getRtoCity());
                        vehicle.setKmsRunning(vehicle.getKmsRunning());
                        vehicle.setHrsRunning(vehicle.getHrsRunning());
                        vehicle.setYear(vehicle.getYear());
                        vehicle.setStartPrice(vehicle.getStartPrice());
                        vehicle.setReservePrice(vehicle.getReservePrice());
                        vehicle.setChassisNo(vehicle.getChassisNo());
                        vehicle.setEngineNo(vehicle.getEngineNo());
                        vehicle.setLotNo(vehicle.getLotNo());
                        vehicle.setVehicleStatus(vehicle.getVehicleStatus());
                        vehicles.add(vehicle);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (getActivity() != null) {
                        PreviewAuctionAdapter adapter = new PreviewAuctionAdapter(getActivity(), vehicles, strAuctionId, "0",
                                getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                        .getString("loginContact", ""));
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getAuctionVehicle(strAuctionId);
                hasViewCreated = true;
            }
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
            Log.i("Check Class-", "Auction Vehicles Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

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
                        getAuctionVehicle(strAuctionId);
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
