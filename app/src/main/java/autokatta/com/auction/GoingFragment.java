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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGoing = inflater.inflate(R.layout.fragment_auction_live, container, false);

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
                                Toast.makeText(getActivity(), "No any going auction event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any going loan event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any going exchange event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any going service event", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(), "No any going sale event", Toast.LENGTH_LONG).show();
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
                mLiveSaleEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());



                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(success.getStartDate().replace("T00:00:00", ""));
                        Date date1 = inputFormat.parse(success.getEndDate().replace("T00:00:00", ""));

                        String output = outputFormat.format(date);
                        String output1 = outputFormat.format(date1);

                        model.setStartDate(output);
                        model.setEndDate(output1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    model.setKeyWord("sale");
                    mLiveSaleEventList.add(model);
                }
                mSaleCount.setText(String.valueOf(mLiveSaleEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveSaleEventList, "Going");
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
                mLiveServiceEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());


                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(success.getStartDate().replace("T00:00:00", ""));
                        Date date1 = inputFormat.parse(success.getEndDate().replace("T00:00:00", ""));

                        String output = outputFormat.format(date);
                        String output1 = outputFormat.format(date1);

                        model.setStartDate(output);
                        model.setEndDate(output1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    model.setKeyWord("service");
                    mLiveServiceEventList.add(model);
                }
                mServiceCount.setText(String.valueOf(mLiveServiceEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveServiceEventList, "Going");
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
                    model.setUsername(success.getEventOwner());
                    model.setBlackListStatus(success.getBlackListStatus());
                    model.setMycontact(success.getMycontact());


                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(success.getStartDate().replace("T00:00:00", ""));
                        Date date1 = inputFormat.parse(success.getEndDate().replace("T00:00:00", ""));

                        String output = outputFormat.format(date);
                        String output1 = outputFormat.format(date1);

                        model.setStartDate(output);
                        model.setEndDate(output1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    model.setKeyWord("exchange");
                    mLiveExchangeEventList.add(model);
                }
                mExchangeEventCount.setText(String.valueOf(mLiveExchangeEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveExchangeEventList, "Going");
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
                mLiveLoanEventList.clear();
                for (GetLiveSaleEventsResponse.Success success : serviceEventsResponse.getSuccess()) {
                    ModelLiveFragment model = new ModelLiveFragment();
                    model.setLoan_id(success.getId());
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


                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(success.getStartDate().replace("T00:00:00", ""));
                        Date date1 = inputFormat.parse(success.getEndDate().replace("T00:00:00", ""));

                        String output = outputFormat.format(date);
                        String output1 = outputFormat.format(date1);

                        model.setStartDate(output);
                        model.setEndDate(output1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    model.setKeyWord("loan");
                    mLiveLoanEventList.add(model);
                }
                mLoanMelaCount.setText(String.valueOf(mLiveLoanEventList.size()));
                mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveLoanEventList, "Going");
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
                        model.setMycontact(success.getMycontact());
                        model.setAuctionCategory(success.getAuctioncategory());

                        if (success.getStockLocation().equals(""))
                            model.setLocation(success.getLocation());
                        else
                            model.setLocation(success.getStockLocation());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(success.getStartDate().replace("T00:00:00", ""));
                            Date date1 = inputFormat.parse(success.getEndDate().replace("T00:00:00", ""));

                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);

                            model.setStartDate(output);
                            model.setEndDate(output1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        model.setKeyWord("auction");
                        mLiveEventList.add(model);
                    }
                    mEventCount.setText(String.valueOf(mLiveEventList.size()));
                    mAdapter = new AuctionNotificationAdapter(getActivity(), mLiveEventList, "Going");
                    mEventRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
//                if (isAdded())
//                    CustomToast.customToast(getActivity(), getString(R.string._404));
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
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Going Fragment");
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
