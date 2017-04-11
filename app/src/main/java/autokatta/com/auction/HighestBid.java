package autokatta.com.auction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.YourBidResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 6/4/17.
 */

public class HighestBid extends Fragment implements RequestNotifier {
    View mHighestBid;
    String auctionId, showPrice, openClose;
    RecyclerView mRecyclerView;
    List<YourBidResponse.Success> successes = new ArrayList<>();
    TextView mHighTotal, mLimitBid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHighestBid = inflater.inflate(R.layout.fragment_all_bid_items, container, false);

        Bundle b = getArguments();
        auctionId = b.getString("auction_id");
        openClose = b.getString("openClose");
        showPrice = b.getString("showPrice");

        mHighTotal = (TextView) getActivity().findViewById(R.id.highbidtotal);
        mLimitBid = (TextView) getActivity().findViewById(R.id.limitforbid);
        mRecyclerView = (RecyclerView) mHighestBid.findViewById(R.id.all_bids);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getYourBidData();
            }
        });
        return mHighestBid;
    }

    /*
    Get Bid Data...
     */
    private void getYourBidData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getHighestBid(auctionId, getActivity().getSharedPreferences(getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""));
        //mApiCall.getHighestBid("1047", "7841023392");
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                successes.clear();
                int highestBidTotal = 0;
                YourBidResponse yourBidResponse = (YourBidResponse) response.body();
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
                    highestBidTotal = highestBidTotal + Integer.parseInt(success.getCurrentBidPrice());
                    if (success.getStartPrice().equals("")) {
                        success.setStartPrice("0");
                    }
                    mHighTotal.setText(getString(R.string.Rs) + " " + highestBidTotal);
                    successes.add(success);
                }
                BidRecyclerAdapter adapter = new BidRecyclerAdapter(getActivity(), successes, auctionId, openClose, showPrice, "1", mLimitBid);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
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
