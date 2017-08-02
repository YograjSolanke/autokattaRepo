package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import autokatta.com.adapter.ActiveAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyActiveAuctionResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 22/3/17.
 */

public class MyActiveAuctionFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    View mMyactiveAuction;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyActiveAuctionResponse.Success.Auction> myActiveAuctionResponseList = new ArrayList<>();
    List<MyActiveAuctionResponse.Success.Vehicle> myVehicleResponseList;
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    public MyActiveAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyactiveAuction = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mMyactiveAuction;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) mMyactiveAuction.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyactiveAuction.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyactiveAuction.findViewById(R.id.recyclerMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getAuctionData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                        "ACTIVE");
            }
        });
    }

    private void getAuctionData(String loginContact, String active) {

        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.MyActiveAuction(loginContact, active);
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
           // errorMessage(getActivity(), getString(R.string.no_internet));
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyActiveAuctionResponse myActiveAuctionResponse = (MyActiveAuctionResponse) response.body();
                if (!myActiveAuctionResponse.getSuccess().getAuction().isEmpty()) {
                    myActiveAuctionResponseList = new ArrayList<>();
                    myActiveAuctionResponseList.clear();
                    mNoData.setVisibility(View.GONE);
                    for (MyActiveAuctionResponse.Success.Auction auctionSuccess : myActiveAuctionResponse.getSuccess().getAuction()) {

                        auctionSuccess.setAuctionId(auctionSuccess.getAuctionId());
                        auctionSuccess.setActionTitle(auctionSuccess.getActionTitle());
                        auctionSuccess.setNoOfVehicle(auctionSuccess.getNoOfVehicle());
                        auctionSuccess.setEndDate(auctionSuccess.getEndDate().replace("T00:00:00",""));
                        auctionSuccess.setEndTime(auctionSuccess.getEndTime());
                        auctionSuccess.setStartDate(auctionSuccess.getStartDate().replace("T00:00:00",""));
                        auctionSuccess.setStartTime(auctionSuccess.getStartTime());
                        auctionSuccess.setStartDateTime(auctionSuccess.getStartDateTime().replace("T"," "));
                        auctionSuccess.setEndDateTime(auctionSuccess.getEndDateTime().replace("T"," "));
                        auctionSuccess.setSpecialClauses(auctionSuccess.getSpecialClauses());
                        auctionSuccess.setAuctionType(auctionSuccess.getAuctionType());
                        auctionSuccess.setGoingcount(auctionSuccess.getGoingcount());

                        auctionSuccess.setAuctioncategory(auctionSuccess.getAuctioncategory());


                        if (auctionSuccess.getStockLocation().equals(""))
                            auctionSuccess.setStockLocation(auctionSuccess.getLocation());
                        else
                            auctionSuccess.setStockLocation(auctionSuccess.getStockLocation());

                        myActiveAuctionResponseList.add(auctionSuccess);
/*
                       // loop to add vehicle depend on auction

                        for (MyActiveAuctionResponse.Success.Vehicle vehicle : myActiveAuctionResponse.getSuccess().getVehicles()){
                            if (auctionSuccess.getAuctionId().equals(vehicle.getAuctionId())){
                                myVehicleResponseList.add(vehicle);
                            }
                        }

                        auctionSuccess.setVehicles(myVehicleResponseList);
                        myActiveAuctionResponseList.add(auctionSuccess);

                        Log.i("size", String.valueOf(myActiveAuctionResponseList.size()));
                        Log.i("Vsize", String.valueOf(myVehicleResponseList.size()));*/

                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ActiveAuctionAdapter adapter = new ActiveAuctionAdapter(getActivity(), myActiveAuctionResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size auction list", String.valueOf(myActiveAuctionResponseList.size()));
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
          //  showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
          //  showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        //    showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
          //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
          //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Active Auction Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getAuctionData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                        "ACTIVE");
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        getAuctionData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                "ACTIVE");
    }

/*    public void showMessage(Activity activity, String message) {
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
                        getAuctionData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                                "ACTIVE");
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
