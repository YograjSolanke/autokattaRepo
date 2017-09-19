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
import autokatta.com.adapter.ActiveSaleMelaAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyActiveSaleMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 24/4/17.
 */

public class MyActiveSaleMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mActiveLoan;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    boolean hasViewCreated = false;
    TextView mNoData;
    List<MyActiveSaleMelaResponse.Success> activeSaleMelaResponseList;
    ConnectionDetector mTestConnection;

    public MyActiveSaleMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActiveLoan = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mActiveLoan;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) mActiveLoan.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mActiveLoan.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mActiveLoan.findViewById(R.id.recyclerMain);

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
                getsalesdata(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });
    }

    private void getsalesdata(String loginContact) {

        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.getSaleMelaDetails(loginContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getsalesdata(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {

            if (response.isSuccessful()) {

                MyActiveSaleMelaResponse myActiveSaleMelaResponse = (MyActiveSaleMelaResponse) response.body();
                if (!myActiveSaleMelaResponse.getSuccess().isEmpty()) {
                    activeSaleMelaResponseList = new ArrayList<>();
                    activeSaleMelaResponseList.clear();
                    mNoData.setVisibility(View.GONE);
                    for (MyActiveSaleMelaResponse.Success loanSuccess : myActiveSaleMelaResponse.getSuccess()) {

                        loanSuccess.setId(loanSuccess.getId());
                        loanSuccess.setName(loanSuccess.getName());
                        loanSuccess.setLocation(loanSuccess.getLocation());
                        loanSuccess.setAddress(loanSuccess.getAddress());
                        loanSuccess.setStartDate(loanSuccess.getStartDate());
                        loanSuccess.setStartTime(loanSuccess.getStartTime());
                        loanSuccess.setEndDate(loanSuccess.getEndDate());
                        loanSuccess.setEndTime(loanSuccess.getEndTime());
                        loanSuccess.setImage(loanSuccess.getImage());
                        loanSuccess.setDetails(loanSuccess.getDetails());
                        loanSuccess.setContact(loanSuccess.getContact());
                        loanSuccess.setGoingcount(loanSuccess.getGoingcount());
                        loanSuccess.setStartDateTime(loanSuccess.getStartDateTime());
                        loanSuccess.setEndDateTime(loanSuccess.getEndDateTime());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");

                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                            outputFormat.setTimeZone(utc);
                            //       outputFormat1.setTimeZone(utc1);
//
                            Date date = inputFormat.parse(loanSuccess.getStartDate());
                            Date date1 = inputFormat.parse(loanSuccess.getEndDate());

                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);

                            loanSuccess.setStartDate(output);
                            loanSuccess.setEndDate(output1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        activeSaleMelaResponseList.add(loanSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ActiveSaleMelaAdapter adapter = new ActiveSaleMelaAdapter(getActivity(), activeSaleMelaResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.i("size sale list", String.valueOf(activeSaleMelaResponseList.size()));
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                //  CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));
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
                CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //  showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
            //    showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //     errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Active Loan Mela Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getsalesdata(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
                hasViewCreated = true;
            }
        }
    }


    @Override
    public void notifyString(String str) {

    }

}
