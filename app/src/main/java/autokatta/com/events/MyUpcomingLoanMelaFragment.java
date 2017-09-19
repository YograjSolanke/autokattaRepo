package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import autokatta.com.adapter.UpcomingLoanMelaAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUpcomingLoanMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 23/3/17.
 */

public class MyUpcomingLoanMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mMyUpcomngLoan;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyUpcomingLoanMelaResponse.Success> upcomingLoanMelaResponseList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    public MyUpcomingLoanMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyUpcomngLoan = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mMyUpcomngLoan;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) mMyUpcomngLoan.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyUpcomngLoan.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyUpcomngLoan.findViewById(R.id.recyclerMain);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getLoanData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });
    }

    private void getLoanData(String loginContact) {

        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.MyUpcomingLoanMela(loginContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MyUpcomingLoanMelaResponse myUpcomingLoanMelaResponse = (MyUpcomingLoanMelaResponse) response.body();
                if (!myUpcomingLoanMelaResponse.getSuccess().isEmpty()) {
                    upcomingLoanMelaResponseList.clear();
                    mNoData.setVisibility(View.GONE);
                    for (MyUpcomingLoanMelaResponse.Success successLoan : myUpcomingLoanMelaResponse.getSuccess()) {
                        successLoan.setId(successLoan.getId());
                        successLoan.setName(successLoan.getName());
                        successLoan.setLocation(successLoan.getLocation());
                        successLoan.setAddress(successLoan.getAddress());
                        successLoan.setStartDate(successLoan.getStartDate());
                        successLoan.setStartTime(successLoan.getStartTime());
                        successLoan.setEndDate(successLoan.getEndDate());
                        successLoan.setEndTime(successLoan.getEndTime());
                        successLoan.setImage(successLoan.getImage());
                        successLoan.setDetails(successLoan.getDetails());
                        successLoan.setContact(successLoan.getContact());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //           TimeZone utc1 = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            //         DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);
                            //        inputFormat1.setTimeZone(utc1);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            //      DateFormat outputFormat1 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);
                            //       outputFormat1.setTimeZone(utc1);

                            Date date = inputFormat.parse(successLoan.getStartDate());
                            Date date1 = inputFormat.parse(successLoan.getEndDate());
                            //       Date date2 = inputFormat.parse(successLoan.getStartDateTime());
                            //      Date date3 = inputFormat.parse(successLoan.getEndDateTime());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);
                            //      String output2 = outputFormat.format(date2);
                            //    String output3 = outputFormat.format(date3);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            successLoan.setStartDate(output);
                            successLoan.setEndDate(output1);
                            //     successLoan.setEndDateTime(output3);
                            //      successLoan.setStartDateTime(output2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        upcomingLoanMelaResponseList.add(successLoan);

                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    UpcomingLoanMelaAdapter adapter = new UpcomingLoanMelaAdapter(getActivity(), upcomingLoanMelaResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
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
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Upcoming Loan Mela");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getLoanData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getLoanData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

}
