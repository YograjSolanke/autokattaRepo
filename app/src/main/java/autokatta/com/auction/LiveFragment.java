package autokatta.com.auction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetLiveEventsResponse;
import retrofit2.Response;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

/**
 * Created by ak-001 on 3/4/17.
 */

public class LiveFragment extends Fragment implements RequestNotifier {
    View mLive;
    List<GetLiveEventsResponse.Success> mLiveEventList = new ArrayList<>();
    RecyclerView mEventRecyclerView;
    AuctionNotificationAdapter mAdapter;
    boolean isFirstViewClick;
    LinearLayout mAuctionEventLinear;
    TextView mEventCount;
    boolean isNetworkAvailable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLive = inflater.inflate(R.layout.fragment_auction_live, container, false);

        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
//                Snackbar.make(mLive.findViewById(R.id.activity_autokatta_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        final RelativeLayout mAuctionEvent = (RelativeLayout) mLive.findViewById(R.id.auction_event);
        RelativeLayout mLoanMela = (RelativeLayout) mLive.findViewById(R.id.loan_mela_layout);
        RelativeLayout mExchangeEvent = (RelativeLayout) mLive.findViewById(R.id.exchange_event_layout);
        mAuctionEventLinear = (LinearLayout) mLive.findViewById(R.id.event_horizontal);
        mEventCount = (TextView) mLive.findViewById(R.id.event_count);
        TextView mLoanMelaCount = (TextView) mLive.findViewById(R.id.loan_mela_count);
        TextView mExchangeEventCount = (TextView) mLive.findViewById(R.id.exchange_event_count);
        mEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.event_recycler_view);
        RecyclerView mLoanMelaRecyclerView = (RecyclerView) mLive.findViewById(R.id.loan_mela_recycler_view);
        RecyclerView mExchangeEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.exchange_event_recycler_view);

        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAuctionEvent();
                    getLoanMela();
                    getExchangeEvent();

                    mAuctionEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveEventList == null || mLiveEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mAuctionEventLinear.setVisibility(View.VISIBLE);
                                } else {
                                    isFirstViewClick = false;
                                    mAuctionEventLinear.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
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
                    mLiveEventList.clear();
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


                        success.setAuctioncategory(success.getAuctioncategory());


                        if (success.getStockLocation().equals(""))
                            success.setStockLocation(success.getLocation());
                        else
                            success.setStockLocation(success.getStockLocation());

                        success.setKeyWord("auction");
                        mLiveEventList.add(success);
                    }
                    mEventCount.setText(String.valueOf(mLiveEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Live");
                    mEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
                /*Snackbar.make(mLive.findViewById(R.id.activity_autokatta_main),"No Internet", Snackbar.LENGTH_LONG)
                        .setAction("Go Online", null).show();*/
                /*nackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
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

    @Override
    public void onStop() {
        super.onStop();

        // Dont forget to cancel the running timers
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }
}