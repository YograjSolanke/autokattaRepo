package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.EndedExchangeAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.EndedSaleMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 25/3/17.
 */

public class MyEndedExchangeMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    View mEndedExchange;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    boolean hasViewCreated = false;
    TextView mNoData;
    List<EndedSaleMelaResponse.Success> activeExchangeMelaList = new ArrayList<>();
    ConnectionDetector mTestConnection;

    public MyEndedExchangeMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEndedExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mEndedExchange;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) mEndedExchange.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mEndedExchange.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mEndedExchange.findViewById(R.id.recyclerMain);

        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getEndedExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });

    }

    private void getEndedExchangeData(String loginContact) {

        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.getEndedExchangeMela(loginContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getEndedExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                EndedSaleMelaResponse endedSaleMelaResponse = (EndedSaleMelaResponse) response.body();
                if (!endedSaleMelaResponse.getSuccess().isEmpty()) {
                    activeExchangeMelaList.clear();
                    mNoData.setVisibility(View.GONE);
                    for (EndedSaleMelaResponse.Success ExchangeSuccess : endedSaleMelaResponse.getSuccess()) {
                        ExchangeSuccess.setId(ExchangeSuccess.getId());
                        ExchangeSuccess.setName(ExchangeSuccess.getName());
                        ExchangeSuccess.setLocation(ExchangeSuccess.getLocation());
                        ExchangeSuccess.setAddress(ExchangeSuccess.getAddress());
                        ExchangeSuccess.setStartDate(ExchangeSuccess.getStartDate());
                        ExchangeSuccess.setStartTime(ExchangeSuccess.getStartTime());
                        ExchangeSuccess.setEndDate(ExchangeSuccess.getEndDate());
                        ExchangeSuccess.setEndTime(ExchangeSuccess.getEndTime());
                        ExchangeSuccess.setImage(ExchangeSuccess.getImage());
                        ExchangeSuccess.setDetails(ExchangeSuccess.getDetails());
                        ExchangeSuccess.setContact(ExchangeSuccess.getContact());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //    TimeZone utc1 = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        //    DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);
                     //       inputFormat1.setTimeZone(utc1);
//
                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                      //      DateFormat outputFormat1 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);
                       //     outputFormat1.setTimeZone(utc1);

                            Date date = inputFormat.parse(ExchangeSuccess.getStartDate());
                            Date date1 = inputFormat.parse(ExchangeSuccess.getEndDate());
                       //     Date date2 = inputFormat.parse(ExchangeSuccess.getStartDateTime());
                        //    Date date3 = inputFormat.parse(ExchangeSuccess.getEndDateTime());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);
                       //     String output2 = outputFormat.format(date2);
                       //     String output3 = outputFormat.format(date3);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            ExchangeSuccess.setStartDate(output);
                            ExchangeSuccess.setEndDate(output1);
                        //    ExchangeSuccess.setEndDateTime(output3);
                        //    ExchangeSuccess.setStartDateTime(output2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        activeExchangeMelaList.add(ExchangeSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    EndedExchangeAdapter adapter = new EndedExchangeAdapter(getActivity(), activeExchangeMelaList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size exchange list", String.valueOf(activeExchangeMelaList.size()));
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } else {
                // CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));
            }

        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //  showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
            //  showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            // errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Ended Exchange Mela Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getEndedExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
                hasViewCreated = true;
            }
        }
    }


    @Override
    public void notifyString(String str) {

    }
}
