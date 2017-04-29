package autokatta.com.auction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetLiveEventsResponse;
import autokatta.com.response.GetLiveSaleEventsResponse;
import autokatta.com.response.ModelLiveFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

/**
 * Created by ak-001 on 3/4/17.
 */

public class GoingFragment extends Fragment implements RequestNotifier {
    View mGoing;
    List<ModelLiveFragment> mLiveEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveLoanEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveExchangeEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveServiceEventList = new ArrayList<>();
    List<ModelLiveFragment> mLiveSaleEventList = new ArrayList<>();
    RecyclerView mEventRecyclerView, mLoanMelaRecyclerView, mExchangeEventRecyclerView, mServiceEventRecyclerView, mSaleRecyclerView;
    AuctionNotificationAdapter mAdapter;
    boolean isFirstViewClick;
    LinearLayout mAuctionEventLinear, mLoanMelaLinear, mExchangeEventLinear, mSaleEventLinear;
    LinearLayout mServiceEventLinear;
    TextView mEventCount, mLoanMelaCount, mExchangeEventCount, mSaleCount, mServiceCount;
    RelativeLayout mAuctionEvent;
    RelativeLayout mLoanMela;
    RelativeLayout mExchangeEvent;
    RelativeLayout mServiceEvent, mSaleEvent;
    boolean isNetworkAvailable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGoing = inflater.inflate(R.layout.fragment_auction_live, container, false);
        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                Snackbar.make(mGoing.findViewById(R.id.activity_autokatta_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        mAuctionEvent = (RelativeLayout) mGoing.findViewById(R.id.auction_event);
        mLoanMela = (RelativeLayout) mGoing.findViewById(R.id.loan_mela_layout);
        mExchangeEvent = (RelativeLayout) mGoing.findViewById(R.id.exchange_event_layout);
        mServiceEvent = (RelativeLayout) mGoing.findViewById(R.id.service_event_layout);
        mSaleEvent = (RelativeLayout) mGoing.findViewById(R.id.sale_event_layout);

        mAuctionEventLinear = (LinearLayout) mGoing.findViewById(R.id.event_horizontal);
        mLoanMelaLinear = (LinearLayout) mGoing.findViewById(R.id.loan_horizontal);
        mExchangeEventLinear = (LinearLayout) mGoing.findViewById(R.id.exchange_event_horizontal);
        mSaleEventLinear = (LinearLayout) mGoing.findViewById(R.id.sale_event_horizontal);
        mServiceEventLinear = (LinearLayout) mGoing.findViewById(R.id.service_event_horizontal);

        mEventCount = (TextView) mGoing.findViewById(R.id.event_count);
        mLoanMelaCount = (TextView) mGoing.findViewById(R.id.loan_mela_count);
        mExchangeEventCount = (TextView) mGoing.findViewById(R.id.exchange_event_count);
        mSaleCount = (TextView) mGoing.findViewById(R.id.sale_event_count);
        mServiceCount = (TextView) mGoing.findViewById(R.id.service_event_count);

        mEventRecyclerView = (RecyclerView) mGoing.findViewById(R.id.event_recycler_view);
        mLoanMelaRecyclerView = (RecyclerView) mGoing.findViewById(R.id.loan_mela_recycler_view);
        mExchangeEventRecyclerView = (RecyclerView) mGoing.findViewById(R.id.exchange_event_recycler_view);
        mServiceEventRecyclerView = (RecyclerView) mGoing.findViewById(R.id.service_event_recycler_view);
        mSaleRecyclerView = (RecyclerView) mGoing.findViewById(R.id.sale_event_recycler_view);

        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mLoanMelaRecyclerView.setHasFixedSize(true);
        mLoanMelaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mExchangeEventRecyclerView.setHasFixedSize(true);
        mExchangeEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mServiceEventRecyclerView.setHasFixedSize(true);
        mServiceEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mSaleRecyclerView.setHasFixedSize(true);
        mSaleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAuctionEvent();
                    getLoanMela();
                    getExchangeEvent();
                    getServiceEvent();
                    getSaleEvent();

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

                    mLoanMela.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveLoanEventList == null || mLiveLoanEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mLoanMelaLinear.setVisibility(View.VISIBLE);
                                } else {
                                    isFirstViewClick = false;
                                    mLoanMelaLinear.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

                    mExchangeEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveExchangeEventList == null || mLiveExchangeEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mExchangeEventLinear.setVisibility(View.VISIBLE);
                                } else {
                                    isFirstViewClick = false;
                                    mExchangeEventLinear.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

                    mServiceEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveServiceEventList == null || mLiveServiceEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mServiceEventLinear.setVisibility(View.VISIBLE);
                                } else {
                                    isFirstViewClick = false;
                                    mServiceEventLinear.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

                    mSaleEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveSaleEventList == null || mLiveSaleEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any auction live today", Toast.LENGTH_LONG).show();
                            } else {
                                if (!isFirstViewClick) {
                                    isFirstViewClick = true;
                                    mSaleEventLinear.setVisibility(View.VISIBLE);
                                } else {
                                    isFirstViewClick = false;
                                    mSaleEventLinear.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mGoing;
    }

    /*
    Sale Event...
     */
    private void getSaleEvent() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getGoingSaleEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());

                    model.setKeyWord("sale");
                    mLiveSaleEventList.add(model);
                }
                mSaleCount.setText(String.valueOf(mLiveSaleEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveSaleEventList, "Upcoming");
                mSaleRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetLiveSaleEventsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*
    Service Events...
     */
    private void getServiceEvent() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getGoingServiceEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());

                    model.setKeyWord("service");
                    mLiveServiceEventList.add(model);
                }
                mServiceCount.setText(String.valueOf(mLiveServiceEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveServiceEventList, "Upcoming");
                mServiceEventRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetLiveSaleEventsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*
    Exchange Events...
     */
    private void getExchangeEvent() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getGoingExchangeEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());

                    model.setKeyWord("exchange");
                    mLiveExchangeEventList.add(model);
                }
                mExchangeEventCount.setText(String.valueOf(mLiveExchangeEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveExchangeEventList, "Upcoming");
                mExchangeEventRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetLiveSaleEventsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*
    Loan Mela...
     */
    private void getLoanMela() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getGoingLoanEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());

                    model.setKeyWord("loan");
                    mLiveLoanEventList.add(model);
                }
                mLoanMelaCount.setText(String.valueOf(mLiveLoanEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveLoanEventList, "Upcoming");
                mLoanMelaRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetLiveSaleEventsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*
    get Going Auction Event...
     */
    private void getAuctionEvent() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.geGoingAuctionEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetLiveEventsResponse mGetLiveEventsResponse = (GetLiveEventsResponse) response.body();
                if (response.body() instanceof GetLiveEventsResponse) {
                    mLiveEventList.clear();
                    for (GetLiveEventsResponse.Success success : mGetLiveEventsResponse.getSuccess()) {
                        ModelLiveFragment model = new ModelLiveFragment();
                        model.setAuctionId(success.getAuctionId());
                        model.setAuctioneer(success.getAuctioneer());
                        model.setActionTitle(success.getActionTitle());
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
                        model.setMycontact(success.getMycontact());
                        model.setAuctionCategory(success.getAuctioncategory());

                        if (success.getStockLocation().equals(""))
                            model.setLocation(success.getLocation());
                        else
                            model.setLocation(success.getStockLocation());

                        model.setKeyWord("auction");
                        mLiveEventList.add(model);
                    }
                    mEventCount.setText(String.valueOf(mLiveEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Going");
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
            Log.i("Check Class-", "Going Fragment");
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
