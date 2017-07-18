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
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetLiveEventsResponse;
import autokatta.com.response.GetLiveSaleEventsResponse;
import autokatta.com.response.ModelLiveFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ak-001 on 3/4/17.
 */

public class UpComingFragment extends Fragment implements RequestNotifier {
    View mUpComing;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUpComing = inflater.inflate(R.layout.fragment_auction_live, container, false);

        mAuctionEvent = (RelativeLayout) mUpComing.findViewById(R.id.auction_event);
        mLoanMela = (RelativeLayout) mUpComing.findViewById(R.id.loan_mela_layout);
        mExchangeEvent = (RelativeLayout) mUpComing.findViewById(R.id.exchange_event_layout);
        mServiceEvent = (RelativeLayout) mUpComing.findViewById(R.id.service_event_layout);
        mSaleEvent = (RelativeLayout) mUpComing.findViewById(R.id.sale_event_layout);

        mAuctionEventLinear = (LinearLayout) mUpComing.findViewById(R.id.event_horizontal);
        mLoanMelaLinear = (LinearLayout) mUpComing.findViewById(R.id.loan_horizontal);
        mExchangeEventLinear = (LinearLayout) mUpComing.findViewById(R.id.exchange_event_horizontal);
        mSaleEventLinear = (LinearLayout) mUpComing.findViewById(R.id.sale_event_horizontal);
        mServiceEventLinear = (LinearLayout) mUpComing.findViewById(R.id.service_event_horizontal);

        mEventCount = (TextView) mUpComing.findViewById(R.id.event_count);
        mLoanMelaCount = (TextView) mUpComing.findViewById(R.id.loan_mela_count);
        mExchangeEventCount = (TextView) mUpComing.findViewById(R.id.exchange_event_count);
        mSaleCount = (TextView) mUpComing.findViewById(R.id.sale_event_count);
        mServiceCount = (TextView) mUpComing.findViewById(R.id.service_event_count);

        mEventRecyclerView = (RecyclerView) mUpComing.findViewById(R.id.event_recycler_view);
        mLoanMelaRecyclerView = (RecyclerView) mUpComing.findViewById(R.id.loan_mela_recycler_view);
        mExchangeEventRecyclerView = (RecyclerView) mUpComing.findViewById(R.id.exchange_event_recycler_view);
        mServiceEventRecyclerView = (RecyclerView) mUpComing.findViewById(R.id.service_event_recycler_view);
        mSaleRecyclerView = (RecyclerView) mUpComing.findViewById(R.id.sale_event_recycler_view);

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
                    //geUpcomingAuctionEvents();
                    //getUpcomingLoanEvents();
                    //getUpcomingExchangeEvents();
                    //getUpcomingServiceEvents();
                    //getUpcomingSaleEvents();
                    mAuctionEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLiveEventList == null || mLiveEventList.size() == 0) {
                                Toast.makeText(getActivity(), "No any upcoming auction event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any upcoming loan event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any upcoming exchange event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any upcoming service event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any upcoming sale event", Toast.LENGTH_LONG).show();
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
        return mUpComing;
    }

    /*
    Service Events...
     */
    private void getUpcomingServiceEvents() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getUpcomingServiceEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                mLiveServiceEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
                    ModelLiveFragment model = new ModelLiveFragment();
                    model.setService_id(String.valueOf(success.getId()));
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
                    model.setUsername(success.getUsername());
                    model.setUsername(success.getServiceOwnerName());
                    model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

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
    Exchange Events
     */
    private void getUpcomingExchangeEvents() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getUpcomingExchangeEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                mLiveExchangeEventList.clear();
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
                    model.setUsername(success.getUsername());
                    model.setUsername(success.getExchangeOwnerName());
                    model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

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
    Loan Events...
     */
    private void getUpcomingLoanEvents() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getUpcomingLoanEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                mLiveLoanEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
                    ModelLiveFragment model = new ModelLiveFragment();
                    model.setLoan_id(String.valueOf(success.getId()));
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
                    model.setUsername(success.getUsername());
                    model.setUsername(success.getLoanOwnerName());
                    model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

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
    Get Upcoming Sale Events...
     */
    private void getUpcomingSaleEvents() {
        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<GetLiveSaleEventsResponse> call = getResponse.getUpcomingSaleEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", ""));
        call.enqueue(new Callback<GetLiveSaleEventsResponse>() {
            @Override
            public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                GetLiveSaleEventsResponse serviceEventsResponse = response.body();
                mLiveSaleEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
                    ModelLiveFragment model = new ModelLiveFragment();
                    model.setSale_id(String.valueOf(success.getId()));
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
                    model.setUsername(success.getUsername());
                    model.setUsername(success.getSaleOwnerName());
                    model.setIgnoreGoingStatus(success.getIgnoreGoingStatus());

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
    Upcoming api call...
     */
    private void geUpcomingAuctionEvents() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getUpcomingEvents(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
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
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Upcoming");
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
            Log.i("Check Class-", "Upcoming Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}