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
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionAnalyticsAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionAnalyticsResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 6/4/17.
 */

public class AuctionAnalyticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    public AuctionAnalyticsFragment() {
        //empty constructor
    }

    boolean hasViewCreated = false;
    TextView mNoData;
    View mAuctionAnalytics;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int strAuctionId = 0;
    List<AuctionAnalyticsResponse.Success> analyticsList = new ArrayList<>();
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuctionAnalytics = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        return mAuctionAnalytics;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mAuctionAnalytics.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);


        mRecyclerView = (RecyclerView) mAuctionAnalytics.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mAuctionAnalytics.findViewById(R.id.swipeRefreshLayoutMain);

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
                    strAuctionId = bundle.getInt("auctionid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            getAuctionAnalytics(strAuctionId);
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
        getAuctionAnalytics(strAuctionId);
    }

    private void getAuctionAnalytics(int strAuctionId) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.AuctionAnalyticsData(strAuctionId);
            //apiCall.AuctionAnalyticsData("1047");
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                analyticsList.clear();
                AuctionAnalyticsResponse analyticsResponse = (AuctionAnalyticsResponse) response.body();
                if (!analyticsResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                for (AuctionAnalyticsResponse.Success success : analyticsResponse.getSuccess()) {

                    success.setReachedCount(success.getReachedCount());
                    success.setGoingCount(success.getGoingCount());
                    success.setIgnoreCount(success.getIgnoreCount());
                    success.setSharedCount(success.getSharedCount());

                    success.setGoingStudent(success.getGoingStudent());
                    success.setGoingSelfStudent(success.getGoingSelfStudent());
                    success.setGoingEmployee(success.getGoingEmployee());

                    success.setIgnoreStudent(success.getIgnoreStudent());
                    success.setIgnoreSelfStudent(success.getIgnoreSelfStudent());
                    success.setIgnoreEmployee(success.getIgnoreEmployee());

                    analyticsList.add(success);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                AuctionAnalyticsAdapter adapter = new AuctionAnalyticsAdapter(getActivity(), strAuctionId, analyticsList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                //CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Auction Analytics Fragment");
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
            if (isVisibleToUser && !hasViewCreated) {
                getAuctionAnalytics(strAuctionId);
                hasViewCreated = true;
            }
        }
    }

}
