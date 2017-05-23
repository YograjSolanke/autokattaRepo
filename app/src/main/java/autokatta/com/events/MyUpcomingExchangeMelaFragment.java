package autokatta.com.events;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.UpcomingExchangeAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUpcomingExchangeMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 23/3/17.
 */

public class MyUpcomingExchangeMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mMyUpcomngExchange;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyUpcomingExchangeMelaResponse.Success> upcomingExchangeResponseList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;

    public MyUpcomingExchangeMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyUpcomngExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        return mMyUpcomngExchange;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mNoData = (TextView) mMyUpcomngExchange.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyUpcomngExchange.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyUpcomngExchange.findViewById(R.id.recyclerMain);

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
                getExchangeData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });
    }

    private void getExchangeData(String loginContact) {
        apiCall = new ApiCall(getActivity(), this);
        apiCall.MyUpcomingExchangeMela(loginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {
                upcomingExchangeResponseList.clear();
                MyUpcomingExchangeMelaResponse myUpcomingExchangeMelaResponse = (MyUpcomingExchangeMelaResponse) response.body();
                if (!myUpcomingExchangeMelaResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (MyUpcomingExchangeMelaResponse.Success successExchange : myUpcomingExchangeMelaResponse.getSuccess()) {

                        successExchange.setId(successExchange.getId());
                        successExchange.setName(successExchange.getName());
                        successExchange.setLocation(successExchange.getLocation());
                        successExchange.setAddress(successExchange.getAddress());
                        successExchange.setStartDate(successExchange.getStartDate());
                        successExchange.setStartTime(successExchange.getStartTime());
                        successExchange.setEndDate(successExchange.getEndDate());
                        successExchange.setEndTime(successExchange.getEndTime());
                        successExchange.setImage(successExchange.getImage());
                        successExchange.setDetails(successExchange.getDetails());
                        successExchange.setContact(successExchange.getContact());

                        upcomingExchangeResponseList.add(successExchange);

                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    UpcomingExchangeAdapter adapter = new UpcomingExchangeAdapter(getActivity(), upcomingExchangeResponseList, "Exchange Mela :");
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size exchange list up", String.valueOf(upcomingExchangeResponseList.size()));

                } else
                    mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-", "My Upcoming Exchange Mela");
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

    @Override
    public void onRefresh() {
        apiCall.MyUpcomingExchangeMela(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

}
