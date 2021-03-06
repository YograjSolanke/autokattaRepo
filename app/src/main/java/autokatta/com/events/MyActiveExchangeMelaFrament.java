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
import autokatta.com.adapter.ActiveExchangeMelaAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyActiveExchangeMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 22/3/17.
 */

public class MyActiveExchangeMelaFrament extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mActiveExchange;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    boolean hasViewCreated = false;
    TextView mNoData;
    List<MyActiveExchangeMelaResponse.Success> activeExchangeMelaList = new ArrayList<>();
    ConnectionDetector mTestConnection;

    public MyActiveExchangeMelaFrament() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActiveExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mActiveExchange;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());
        mNoData = (TextView) mActiveExchange.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mActiveExchange.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mActiveExchange.findViewById(R.id.recyclerMain);

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
                getExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });
    }

    private void getExchangeData(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.MyActiveExchangeMela(loginContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {

            if (response.isSuccessful()) {

                MyActiveExchangeMelaResponse myActiveexchangeMelaResponse = (MyActiveExchangeMelaResponse) response.body();
                if (!myActiveexchangeMelaResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    activeExchangeMelaList.clear();
                    for (MyActiveExchangeMelaResponse.Success ExchangeSuccess : myActiveexchangeMelaResponse.getSuccess()) {

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
                        ExchangeSuccess.setStartDateTime(ExchangeSuccess.getStartDateTime());
                        ExchangeSuccess.setEndDateTime(ExchangeSuccess.getEndDateTime());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //     TimeZone utc1 = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            inputFormat.setTimeZone(utc);
                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            outputFormat.setTimeZone(utc);
                            //   outputFormat1.setTimeZone(utc1);

                            Date date = inputFormat.parse(ExchangeSuccess.getStartDate());
                            Date date1 = inputFormat.parse(ExchangeSuccess.getEndDate());

                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);

                            ExchangeSuccess.setStartDate(output);
                            ExchangeSuccess.setEndDate(output1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        activeExchangeMelaList.add(ExchangeSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ActiveExchangeMelaAdapter adapter = new ActiveExchangeMelaAdapter(getActivity(), activeExchangeMelaList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size exchange list", String.valueOf(activeExchangeMelaList.size()));
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                //CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
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
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Active Exchange Mela Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
                hasViewCreated = true;
            }
        }
    }


    @Override
    public void notifyString(String str) {
    }

}
