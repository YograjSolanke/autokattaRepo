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

import java.net.SocketTimeoutException;
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
 * Created by ak-003 on 28/4/17.
 */

public class MyUpcomingServiceMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mMyUpcomngExchange;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyUpcomingExchangeMelaResponse.Success> upcomingExchangeResponseList = new ArrayList<>();


    public MyUpcomingServiceMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyUpcomngExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyUpcomngExchange.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyUpcomngExchange.findViewById(R.id.recyclerMain);

        apiCall = new ApiCall(getActivity(), this);

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
                apiCall.MyUpcomingServiceMela(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
            }
        });
        return mMyUpcomngExchange;
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyUpcomingExchangeMelaResponse myUpcomingExchangeMelaResponse = (MyUpcomingExchangeMelaResponse) response.body();
                if (!myUpcomingExchangeMelaResponse.getSuccess().isEmpty()) {

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
                    UpcomingExchangeAdapter adapter = new UpcomingExchangeAdapter(getActivity(), upcomingExchangeResponseList, "Service Mela :");
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size sale list up", String.valueOf(upcomingExchangeResponseList.size()));

                } else
                    CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));

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
            Log.i("Check Class-", "My Upcoming Sale Mela");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
