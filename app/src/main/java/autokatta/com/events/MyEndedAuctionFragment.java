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
import autokatta.com.adapter.EndedAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyActiveAuctionResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 25/3/17.
 */

public class MyEndedAuctionFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {


    View mMyEndedAuction;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    String myContact;
    List<MyActiveAuctionResponse.Success.Auction> myActiveAuctionResponseList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    public MyEndedAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyEndedAuction = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mMyEndedAuction;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mNoData = (TextView) mMyEndedAuction.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMyEndedAuction.findViewById(R.id.swipeRefreshLayoutMain);
                mRecyclerView = (RecyclerView) mMyEndedAuction.findViewById(R.id.recyclerMain);
                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getAuctionData(myContact);

                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getAuctionData(String myContact) {
        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.getMyEndedAuction(myContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //  errorMessage(getActivity(), getString(R.string.no_internet));
        }

    }

    @Override
    public void onRefresh() {
        getAuctionData(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MyActiveAuctionResponse myActiveAuctionResponse = (MyActiveAuctionResponse) response.body();
                if (!myActiveAuctionResponse.getSuccess().getAuction().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    myActiveAuctionResponseList = new ArrayList<>();
                    for (MyActiveAuctionResponse.Success.Auction auctionSuccess : myActiveAuctionResponse.getSuccess().getAuction()) {
                        auctionSuccess.setAuctionId(auctionSuccess.getAuctionId());
                        auctionSuccess.setActionTitle(auctionSuccess.getActionTitle());
                        auctionSuccess.setNoOfVehicle(auctionSuccess.getNoOfVehicle());
                        auctionSuccess.setEndDate(auctionSuccess.getEndDate());
                        auctionSuccess.setEndTime(auctionSuccess.getEndTime());
                        auctionSuccess.setStartDate(auctionSuccess.getStartDate());
                        auctionSuccess.setStartTime(auctionSuccess.getStartTime());
                        auctionSuccess.setSpecialClauses(auctionSuccess.getSpecialClauses());
                        auctionSuccess.setAuctionType(auctionSuccess.getAuctionType());
                        auctionSuccess.setGoingcount(auctionSuccess.getGoingcount());

                        auctionSuccess.setAuctioncategory(auctionSuccess.getAuctioncategory());
                        if (auctionSuccess.getStockLocation().equals("")) {
                            auctionSuccess.setStockLocation(auctionSuccess.getLocation());
                        } else {
                            auctionSuccess.setStockLocation(auctionSuccess.getStockLocation());
                        }


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //    TimeZone utc1 = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            ///      DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);
                            //      inputFormat1.setTimeZone(utc1);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            //      DateFormat outputFormat1 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);
                            //    outputFormat1.setTimeZone(utc1);

                            Date date = inputFormat.parse(auctionSuccess.getStartDate());
                            Date date1 = inputFormat.parse(auctionSuccess.getEndDate());
                            //     Date date2 = inputFormat.parse(auctionSuccess.getStartDateTime());
                            //    Date date3 = inputFormat.parse(auctionSuccess.getEndDateTime());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);
                            //   String output2 = outputFormat.format(date2);
                            //   String output3 = outputFormat.format(date3);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            auctionSuccess.setStartDate(output);
                            auctionSuccess.setEndDate(output1);
                            //    auctionSuccess.setEndDateTime(output3);
                            //    auctionSuccess.setStartDateTime(output2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        myActiveAuctionResponseList.add(auctionSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    EndedAuctionAdapter adapter = new EndedAuctionAdapter(getActivity(), myActiveAuctionResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size ended auction list", String.valueOf(myActiveAuctionResponseList.size()));
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } else {
                //  CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));
            }
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getAuctionData(myContact);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            ///   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
            //  showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
            //    showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Ended Auction Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

}
