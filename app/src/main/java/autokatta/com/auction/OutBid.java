package autokatta.com.auction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.YourBidResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 6/4/17.
 */

public class OutBid extends Fragment implements RequestNotifier {
    View mOutBid;
    String showPrice, openClose;
    int auctionId = 0;
    RecyclerView mRecyclerView;
    List<YourBidResponse.Success> successes = new ArrayList<>();
    TextView mLimitBid;
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOutBid = inflater.inflate(R.layout.fragment_all_bid_items, container, false);
        return mOutBid;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();
        auctionId = b.getInt("auction_id");
        openClose = b.getString("openClose");
        showPrice = b.getString("showPrice");

        mNoData = (TextView) mOutBid.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mLimitBid = (TextView) getActivity().findViewById(R.id.limitforbid);
        mRecyclerView = (RecyclerView) mOutBid.findViewById(R.id.all_bids);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                getOutBidData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getOutBidData();
                hasViewCreated = true;
            }
        }
    }

    /*
        get Out Bid
         */
    private void getOutBidData() {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getOutBid(auctionId, getActivity().getSharedPreferences(getString(R.string.my_preference),
                    Context.MODE_PRIVATE).getString("loginContact", ""));
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                successes.clear();
                YourBidResponse yourBidResponse = (YourBidResponse) response.body();
                if (!yourBidResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (YourBidResponse.Success success : yourBidResponse.getSuccess()) {
                        success.setTitle(success.getTitle());
                        success.setBrand(success.getBrand());
                        success.setModel(success.getModel());
                        success.setYear(success.getYear());
                        success.setVehicleid(success.getVehicleid());
                        success.setAuctionid(success.getAuctionid());
                        success.setImage(success.getImage());
                        success.setLocationCity(success.getLocationCity());
                        success.setRtoCity(success.getRtoCity());
                        success.setRcAvailable(success.getRcAvailable());
                        success.setKmsRunning(success.getKmsRunning());
                        success.setHrsRunning(success.getHrsRunning());
                        success.setInvoice(success.getInvoice());
                        success.setRegNo(success.getRegNo());
                        success.setStartPrice(success.getStartPrice());
                        success.setReservePrice(success.getReservePrice());
                        success.setLotNo(success.getLotNo());
                        success.setCurrentBidPrice(success.getCurrentBidPrice());
                        success.setDate(success.getDate());
                        success.setAuctionBidId(success.getAuctionBidId());
                        success.setBidReceivedPrice(success.getBidReceivedPrice());
                        if (success.getStartPrice().equals("")) {
                            success.setStartPrice("0");
                        }
                        successes.add(success);
                    }
                    BidRecyclerAdapter adapter = new BidRecyclerAdapter(getActivity(), successes, auctionId, openClose, showPrice, "0", mLimitBid);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    //mSwipeRefreshLayout.setRefreshing(false);
                }
            } else {
                // CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check class", "Out Bid Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
