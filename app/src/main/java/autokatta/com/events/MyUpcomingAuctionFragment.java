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
import autokatta.com.adapter.UpcomingAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUpcomingAuctionResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 23/3/17.
 */

public class MyUpcomingAuctionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mupcomingAuction;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyUpcomingAuctionResponse.Success.Auction> upcomingAuctionResponseList = new ArrayList<>();
    List<MyUpcomingAuctionResponse.Success.Vehicle> upcomingVehicleResponseList;


    public MyUpcomingAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mupcomingAuction = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mupcomingAuction.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mupcomingAuction.findViewById(R.id.recyclerMain);

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
                apiCall.MyUpcomingAuction(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
            }
        });
        return mupcomingAuction;
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyUpcomingAuctionResponse myUpcomingAuctionResponse = (MyUpcomingAuctionResponse) response.body();
                if (!myUpcomingAuctionResponse.getSuccess().getAuction().isEmpty()) {

                    for (MyUpcomingAuctionResponse.Success.Auction successAuction : myUpcomingAuctionResponse.getSuccess().getAuction()) {

                        upcomingVehicleResponseList = new ArrayList<>();

                        successAuction.setAuctionId(successAuction.getAuctionId());
                        successAuction.setActionTitle(successAuction.getActionTitle());
                        successAuction.setNoOfVehicle(successAuction.getNoOfVehicle());
                        successAuction.setEndDate(successAuction.getEndDate());
                        successAuction.setEndTime(successAuction.getEndTime());
                        successAuction.setStartDate(successAuction.getStartDate());
                        successAuction.setStartTime(successAuction.getStartTime());
                        successAuction.setSpecialClauses(successAuction.getSpecialClauses());
                        successAuction.setGoingcount(successAuction.getGoingcount());
                        successAuction.setAuctionType(successAuction.getAuctionType());

                        upcomingAuctionResponseList.add(successAuction);

                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    UpcomingAuctionAdapter adapter = new UpcomingAuctionAdapter(getActivity(), upcomingAuctionResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size auction list up", String.valueOf(upcomingAuctionResponseList.size()));

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
            Log.i("Check Class-", "My Upcoming Auction fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
