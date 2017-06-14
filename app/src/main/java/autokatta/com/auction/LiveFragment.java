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
import autokatta.com.response.GetLiveExchangeEventsResponse;
import autokatta.com.response.GetLiveLoanEventsResponse;
import autokatta.com.response.GetLiveSaleEventsResponse;
import autokatta.com.response.GetLiveServiceEventsResponse;
import autokatta.com.response.ModelLiveFragment;
import retrofit2.Response;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

/**
 * Created by ak-001 on 3/4/17.
 */

public class LiveFragment extends Fragment implements RequestNotifier {
    View mLive;
    List<ModelLiveFragment> mLiveEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveLoanEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveExchangeEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveServiceEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveSaleEventList = new ArrayList<>();
    RecyclerView mEventRecyclerView, mLoanMelaRecyclerView, mExchangeEventRecyclerView, mServiceEventRecyclerView, mSaleEventRecyclerView;
    AuctionNotificationAdapter mAdapter;
    boolean isFirstViewClick;
    LinearLayout mAuctionEventLinear, mLoanMelaLinear, mExchangeEventLinear, mSaleEventLinear;
    LinearLayout mServiceEventLinear;
    TextView mEventCount, mLoanMelaCount, mExchangeEventCount, mSaleEventCount, mServiceEventCount;
    RelativeLayout mAuctionEvent;
    RelativeLayout mLoanMela;
    RelativeLayout mExchangeEvent;
    RelativeLayout mServiceEvent, mSaleEvent;
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

        mAuctionEvent = (RelativeLayout) mLive.findViewById(R.id.auction_event);
        mLoanMela = (RelativeLayout) mLive.findViewById(R.id.loan_mela_layout);
        mExchangeEvent = (RelativeLayout) mLive.findViewById(R.id.exchange_event_layout);
        mServiceEvent = (RelativeLayout) mLive.findViewById(R.id.service_event_layout);
        mSaleEvent = (RelativeLayout) mLive.findViewById(R.id.sale_event_layout);

        mAuctionEventLinear = (LinearLayout) mLive.findViewById(R.id.event_horizontal);
        mLoanMelaLinear = (LinearLayout) mLive.findViewById(R.id.loan_horizontal);
        mExchangeEventLinear = (LinearLayout) mLive.findViewById(R.id.exchange_event_horizontal);
        mSaleEventLinear = (LinearLayout) mLive.findViewById(R.id.sale_event_horizontal);
        mServiceEventLinear = (LinearLayout) mLive.findViewById(R.id.service_event_horizontal);

        mEventCount = (TextView) mLive.findViewById(R.id.event_count);
        mLoanMelaCount = (TextView) mLive.findViewById(R.id.loan_mela_count);
        mExchangeEventCount = (TextView) mLive.findViewById(R.id.exchange_event_count);
        mSaleEventCount = (TextView) mLive.findViewById(R.id.sale_event_count);
        mServiceEventCount = (TextView) mLive.findViewById(R.id.service_event_count);

        mEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.event_recycler_view);
        mLoanMelaRecyclerView = (RecyclerView) mLive.findViewById(R.id.loan_mela_recycler_view);
        mExchangeEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.exchange_event_recycler_view);
        mServiceEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.service_event_recycler_view);
        mSaleEventRecyclerView = (RecyclerView) mLive.findViewById(R.id.sale_event_recycler_view);

        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mLoanMelaRecyclerView.setHasFixedSize(true);
        mLoanMelaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mExchangeEventRecyclerView.setHasFixedSize(true);
        mExchangeEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mServiceEventRecyclerView.setHasFixedSize(true);
        mServiceEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mSaleEventRecyclerView.setHasFixedSize(true);
        mSaleEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAuctionEvent();
                    getLoanMela();
                    getExchangeEvent();
                    getSaleEvent();
                    getServiceEvent();

                    mAuctionEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveEventList == null || mLiveEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mAuctionEventLinear.setVisibility(View.VISIBLE);
                                    mAuctionEvent.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                                } else {
                                    isFirstViewClick = false;
                                    mAuctionEventLinear.setVisibility(View.GONE);
                                    mAuctionEvent.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            }
                        }
                    });

                    mLoanMela.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveLoanEventList == null || mLiveLoanEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any loan mela live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mLoanMelaLinear.setVisibility(View.VISIBLE);
                                    mLoanMela.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                                } else {
                                    isFirstViewClick = false;
                                    mLoanMelaLinear.setVisibility(View.GONE);
                                    mLoanMela.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            }
                        }
                    });

                    mExchangeEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveExchangeEventList == null || mLiveExchangeEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any exchange mela live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mExchangeEventLinear.setVisibility(View.VISIBLE);
                                    mExchangeEvent.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                                } else {
                                    isFirstViewClick = false;
                                    mExchangeEventLinear.setVisibility(View.GONE);
                                    mExchangeEvent.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            }
                        }
                    });

                    mServiceEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveServiceEventList == null || mLiveServiceEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any service mela live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mServiceEventLinear.setVisibility(View.VISIBLE);
                                    mServiceEvent.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                                } else {
                                    isFirstViewClick = false;
                                    mServiceEventLinear.setVisibility(View.GONE);
                                    mServiceEvent.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            }
                        }
                    });

                    mSaleEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveSaleEventList == null || mLiveSaleEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any sale mela live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mSaleEventLinear.setVisibility(View.VISIBLE);
                                    mSaleEvent.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                                } else {
                                    isFirstViewClick = false;
                                    mSaleEventLinear.setVisibility(View.GONE);
                                    mSaleEvent.setBackgroundColor(getResources().getColor(R.color.white));
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
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getLiveLoanEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    /*
    Exchange Event...
     */
    private void getExchangeEvent() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getLiveExchangeEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    /*
    Sale Event...
     */
    private void getSaleEvent() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getLiveSaleEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    /*
    Service Event...
     */
    private void getServiceEvent() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getLiveServiceEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));

    }

    /*
    Responses...
     */
    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof GetLiveEventsResponse) {
                    mLiveEventList.clear();
                    GetLiveEventsResponse mGetLiveEventsResponse = (GetLiveEventsResponse) response.body();
                    for (GetLiveEventsResponse.Success success : mGetLiveEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setAuctionId(success.getAuctionId());
                        model.setUsername(success.getAuctioneer());
                        model.setName(success.getActionTitle());
                        model.setContact(success.getContact());
                        model.setStartDate(success.getStartDate());
                        model.setStartTime(success.getStartTime());
                        model.setEndDate(success.getEndDate());
                        model.setEndTime(success.getEndTime());
                        model.setAuctionType(success.getAuctionType());
                        model.setLocation(success.getLocation());
                        model.setSpecialClauses(success.getClausesNames());
                        model.setVehicleIds(success.getVehicleIds());
                        model.setNoOfVehicles(success.getNoOfVehicles());
                        model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());
                        model.setEndDateTime(success.getEndDateTime());
                        model.setStartDateTime(success.getStartDateTime());
                        model.setOpenClose(success.getOpenClose());
                        model.setShowPrice(success.getShowPrice());
                        model.setBlackListStatus(success.getBlackListStatus());
                        model.setAuctionCategory(success.getAuctioncategory());

                        if (success.getStockLocation().equals(""))
                            model.setLocation(success.getLocation());
                        else
                            model.setLocation(success.getStockLocation());

                        model.setKeyWord("auction");
                        mLiveEventList.add(model);
                    }
                    mEventCount.setText(String.valueOf(mLiveEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Live");
                    mEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //Loan Event
                else if (response.body() instanceof GetLiveLoanEventsResponse) {
                    mLiveLoanEventList.clear();
                    GetLiveLoanEventsResponse mGetLiveLoanEventsResponse = (GetLiveLoanEventsResponse) response.body();
                    for (GetLiveLoanEventsResponse.Success success : mGetLiveLoanEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setLoan_id(success.getId());
                        model.setUsername(success.getLoanOwnerName());
                        model.setName(success.getName());
                        model.setContact(success.getContact());
                        model.setStartDate(success.getStartDate());
                        model.setStartTime(success.getStartTime());
                        model.setEndDate(success.getEndDate());
                        model.setEndTime(success.getEndTime());
                        model.setLocation(success.getLocation());
                        model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());
                        model.setEndDateTime(success.getEndDateTime());
                        model.setStartDateTime(success.getStartDateTime());

                        model.setKeyWord("loan");
                        mLiveLoanEventList.add(model);
                    }
                    mLoanMelaCount.setText(String.valueOf(mLiveLoanEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveLoanEventList, "Live");
                    mLoanMelaRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //Exchange Event
                else if (response.body() instanceof GetLiveExchangeEventsResponse) {
                    mLiveExchangeEventList.clear();
                    GetLiveExchangeEventsResponse mGetLiveExchangeEventsResponse = (GetLiveExchangeEventsResponse) response.body();
                    for (GetLiveExchangeEventsResponse.Success success : mGetLiveExchangeEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setExchange_id(success.getId());
                        model.setContact(success.getContact());
                        model.setName(success.getName());
                        model.setStartDate(success.getStartDate());
                        model.setStartTime(success.getStartTime());
                        model.setEndDate(success.getEndDate());
                        model.setEndTime(success.getEndTime());
                        model.setLocation(success.getLocation());
                        model.setAddress(success.getAddress());
                        model.setImage(success.getImage());
                        model.setStartDateTime(success.getStartDateTime());
                        model.setEndDateTime(success.getEndDateTime());
                        model.setCreateDate(success.getCreateDate());
                        model.setDetails(success.getDetails());
                        model.setUsername(success.getExchangeOwnerName());
                        model.setExchangeOwnerName(success.getExchangeOwnerName());
                        model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

                        model.setKeyWord("exchange");
                        mLiveExchangeEventList.add(model);
                    }
                    mExchangeEventCount.setText(String.valueOf(mLiveExchangeEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveExchangeEventList, "Live");
                    mExchangeEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //Sale Event
                else if (response.body() instanceof GetLiveSaleEventsResponse) {
                    mLiveSaleEventList.clear();
                    GetLiveSaleEventsResponse mGetLiveSaleEventsResponse = (GetLiveSaleEventsResponse) response.body();
                    for (GetLiveSaleEventsResponse.Success success : mGetLiveSaleEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setSale_id(success.getId());
                        model.setContact(success.getContact());
                        model.setName(success.getName());
                        model.setStartDate(success.getStartDate());
                        model.setStartTime(success.getStartTime());
                        model.setEndDate(success.getEndDate());
                        model.setEndTime(success.getEndTime());
                        model.setLocation(success.getLocation());
                        model.setAddress(success.getAddress());
                        model.setImage(success.getImage());
                        model.setStartDateTime(success.getStartDateTime());
                        model.setEndDateTime(success.getEndDateTime());
                        model.setCreateDate(success.getCreateDate());
                        model.setDetails(success.getDetails());
                        model.setUsername(success.getSaleOwnerName());
                        model.setSaleOwnerName(success.getSaleOwnerName());
                        model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

                        model.setKeyWord("sale");
                        mLiveSaleEventList.add(model);
                    }
                    mSaleEventCount.setText(String.valueOf(mLiveSaleEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveSaleEventList, "Live");
                    mSaleEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //Service Event
                else if (response.body() instanceof GetLiveServiceEventsResponse) {
                    mLiveServiceEventList.clear();
                    GetLiveServiceEventsResponse mGetLiveServiceEventsResponse = (GetLiveServiceEventsResponse) response.body();
                    for (GetLiveServiceEventsResponse.Success success : mGetLiveServiceEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setService_id(success.getId());
                        model.setContact(success.getContact());
                        model.setName(success.getName());
                        model.setStartDate(success.getStartDate());
                        model.setStartTime(success.getStartTime());
                        model.setEndDate(success.getEndDate());
                        model.setEndTime(success.getEndTime());
                        model.setLocation(success.getLocation());
                        model.setAddress(success.getAddress());
                        model.setImage(success.getImage());
                        model.setStartDateTime(success.getStartDateTime());
                        model.setEndDateTime(success.getEndDateTime());
                        model.setCreateDate(success.getCreateDate());
                        model.setDetails(success.getDetails());
                        model.setUsername(success.getServiceOwnerName());
                        model.setServiceOwnerName(success.getServiceOwnerName());
                        model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

                        model.setKeyWord("service");
                        mLiveServiceEventList.add(model);
                    }
                    mServiceEventCount.setText(String.valueOf(mLiveServiceEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveServiceEventList, "Live");
                    mServiceEventRecyclerView.setAdapter(mAdapter);
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