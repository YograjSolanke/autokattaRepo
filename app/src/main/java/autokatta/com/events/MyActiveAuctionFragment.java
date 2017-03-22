package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 22/3/17.
 */

public class MyActiveAuctionFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    View mMyactiveAuction;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;

    public MyActiveAuctionFragment() {
        //empty fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyactiveAuction = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyactiveAuction.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mMyactiveAuction.findViewById(R.id.recyclerMain);

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
                apiCall.MyActiveAuction(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                        "ACTIVE");
            }
        });
        return mMyactiveAuction;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
