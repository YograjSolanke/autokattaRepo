package autokatta.com.initial_fragment;

import android.content.SharedPreferences;
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
import autokatta.com.adapter.SavedAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MySavedAuctionResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 31/3/17.
 */

public class SavedAuctionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    SharedPreferences mSharedPreferences;
    List<MySavedAuctionResponse.Success> mysavedAuctionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_auction, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMySavedAuctions);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        apiCall = new ApiCall(getActivity(), this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(layoutManager);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);

            }
        });


        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.getMySavedAuctions(mSharedPreferences.getString("loginContact", ""));
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        apiCall.getMySavedAuctions(mSharedPreferences.getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MySavedAuctionResponse savedAuctionResponse = (MySavedAuctionResponse) response.body();
                if (!savedAuctionResponse.getSuccess().isEmpty()) {
                    mysavedAuctionList = new ArrayList<>();
                    for (MySavedAuctionResponse.Success success : savedAuctionResponse.getSuccess()) {
                        success.setAuctionId(success.getAuctionId());
                        success.setActionTitle(success.getActionTitle());
                        //success.setStartDate(success.getStartDate());
                        success.setStartTime(success.getStartTime());
                        //success.setEndDate(success.getEndDate());
                        success.setEndTime(success.getEndTime());
                        success.setNoOfVehicles(success.getNoOfVehicles());
                        success.setSpecialClauses(success.getSpecialClauses());
                        success.setSpecialPosition(success.getSpecialPosition());
                        success.setPositionArray(success.getPositionArray());
                        success.setAuctioncategory(success.getAuctioncategory());
                        success.setSpecialIds(success.getSpecialIds());
                        if (success.getStockLocation().equals(""))
                            success.setStockLocation(success.getLocation());
                        else
                            success.setStockLocation(success.getStockLocation());


                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");

                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(success.getStartDate());
                            Date date1 = inputFormat.parse(success.getEndDate());

                            String output = outputFormat.format(date);
                            String output1 = outputFormat.format(date1);

                            success.setStartDate(output);
                            success.setEndDate(output1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mysavedAuctionList.add(success);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    SavedAuctionAdapter adapter = new SavedAuctionAdapter(getActivity(), mysavedAuctionList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
            }

        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
            Log.i("Check Class-"
                    , "SavedAuctionFragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
