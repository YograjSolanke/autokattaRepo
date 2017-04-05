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
import autokatta.com.adapter.ActiveExchangeMelaAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
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
    List<MyActiveExchangeMelaResponse.Success> activeExchangeMelaList = new ArrayList<>();

    public MyActiveExchangeMelaFrament() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActiveExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mActiveExchange.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mActiveExchange.findViewById(R.id.recyclerMain);

        mRecyclerView.setHasFixedSize(true);
        apiCall = new ApiCall(getActivity(), this);

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
                apiCall.MyActiveExchangeMela(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
            }
        });
        return mActiveExchange;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {

            if (response.isSuccessful()) {

                MyActiveExchangeMelaResponse myActiveexchangeMelaResponse = (MyActiveExchangeMelaResponse) response.body();
                if (!myActiveexchangeMelaResponse.getSuccess().isEmpty()) {

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

                        activeExchangeMelaList.add(ExchangeSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ActiveExchangeMelaAdapter adapter = new ActiveExchangeMelaAdapter(getActivity(), activeExchangeMelaList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size exchange list", String.valueOf(activeExchangeMelaList.size()));
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
                }

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
            Log.i("Check Class-", "My Active Exchange Mela Fragment");
            error.printStackTrace();
        }
    }


    @Override
    public void notifyString(String str) {

    }
}
