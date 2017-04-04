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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetLiveEventsResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 3/4/17.
 */

public class LiveFragment extends Fragment implements RequestNotifier {
    View mLive;
    List<GetLiveEventsResponse.Success> mLiveEventList = new ArrayList<>();
    RecyclerView mEventRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLive = inflater.inflate(R.layout.fragment_auction_live, container, false);

        RelativeLayout mAuctionEvent = (RelativeLayout) mLive.findViewById(R.id.auction_event);
        RelativeLayout mLoanMela = (RelativeLayout) mLive.findViewById(R.id.loan_mela_layout);
        RelativeLayout mExchangeEvent = (RelativeLayout) mLive.findViewById(R.id.exchange_event_layout);
        TextView mEventCount = (TextView) mLive.findViewById(R.id.event_count);
        TextView mLoanMelaCount = (TextView) mLive.findViewById(R.id.loan_mela_count);
        TextView mExchangeEventCount = (TextView) mLive.findViewById(R.id.exchange_event_count);
        mEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.event_recycler_view);
        RecyclerView mLoanMelaRecyclerView = (RecyclerView) mLive.findViewById(R.id.loan_mela_recycler_view);
        RecyclerView mExchangeEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.exchange_event_recycler_view);

        mEventRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mEventRecyclerView.setLayoutManager(mLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAuctionEvent();
                    getLoanMela();
                    getExchangeEvent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mLive;
    }

    /*
    Auction Event...
     */
    private void getAuctionEvent() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getLiveAuctionEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    /*
    Loan Mela...
     */
    private void getLoanMela() {

    }

    /*
    Exchange Event...
     */
    private void getExchangeEvent() {

    }

    /*
    Responses...
     */
    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetLiveEventsResponse mGetLiveEventsResponse = (GetLiveEventsResponse) response.body();
                if (response.body() instanceof GetLiveEventsResponse) {
                    for (GetLiveEventsResponse.Success success : mGetLiveEventsResponse.getSuccess()) {
                        success.setAuctionId(success.getAuctionId());
                        success.setAuctioneer(success.getAuctioneer());
                        success.setActionTitle(success.getActionTitle());
                        success.setContact(success.getContact());
                        success.setStartDate(success.getStartDate());
                        success.setStartTime(success.getStartTime());
                        success.setEndDate(success.getEndDate());
                        success.setEndTime(success.getEndTime());
                        success.setAuctionType(success.getAuctionType());
                        success.setLocation(success.getLocation());
                        success.setSpecialClauses(success.getSpecialClauses());
                        success.setVehicleIds(success.getVehicleIds());
                        success.setNoOfVehicles(success.getNoOfVehicles());
                        success.setIgnoreGoingStatus(success.getIgnoreGoingStatus());
                        success.setEndDateTime(success.getEndDateTime());
                        success.setStartDateTime(success.getStartDateTime());
                        success.setOpenClose(success.getOpenClose());
                        success.setShowPrice(success.getShowPrice());
                        success.setBlackListStatus(success.getBlackListStatus());
                        success.setKeyWord("auction");
                        mLiveEventList.add(success);
                    }
                    AuctionNotificationAdapter mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Live");
                    mEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
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
            Log.i("Check Class-", "Live Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}