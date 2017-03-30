package autokatta.com.events;

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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ActiveAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
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

    public MyActiveAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyactiveAuction = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyactiveAuction.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyactiveAuction.findViewById(R.id.recyclerMain);

        apiCall = new ApiCall(getActivity(), this);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.MyActiveAuction(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                        "ACTIVE");
            }
        });
        return mMyactiveAuction;
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyActiveAuctionResponse myActiveAuctionResponse = (MyActiveAuctionResponse) response.body();
                if (!myActiveAuctionResponse.getSuccess().getAuction().isEmpty()) {
                    myActiveAuctionResponseList = new ArrayList<>();
                    for (MyActiveAuctionResponse.Success.Auction auctionSuccess : myActiveAuctionResponse.getSuccess().getAuction()) {



                        auctionSuccess.setAuctionId(auctionSuccess.getAuctionId());
                        auctionSuccess.setActionTitle(auctionSuccess.getActionTitle());
                        auctionSuccess.setNoOfVehicle(auctionSuccess.getNoOfVehicle());
                        auctionSuccess.setEndDate(auctionSuccess.getEndDate());
                        auctionSuccess.setEndTime(auctionSuccess.getEndTime());
                        auctionSuccess.setStartDate(auctionSuccess.getStartDate());
                        auctionSuccess.setStartTime(auctionSuccess.getStartTime());
                        auctionSuccess.setStartDateTime(auctionSuccess.getStartDateTime());
                        auctionSuccess.setEndDateTime(auctionSuccess.getEndDateTime());
                        auctionSuccess.setSpecialClauses(auctionSuccess.getSpecialClauses());
                        auctionSuccess.setAuctionType(auctionSuccess.getAuctionType());
                        auctionSuccess.setGoingcount(auctionSuccess.getGoingcount());
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
                } else
                    CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "My Active Auction Fragment");
            error.toString();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
