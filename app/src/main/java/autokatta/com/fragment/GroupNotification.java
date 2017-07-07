package autokatta.com.fragment;

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

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

/**
 * Created by ak-003 on 7/7/17.
 */

public class GroupNotification extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mGroupView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;
    String myContact;

    public GroupNotification() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mGroupView = inflater.inflate(R.layout.fragment_store_layout, container, false);
        mGroupView = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mGroupView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mTestConnection = new ConnectionDetector(getActivity());

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            //groupNotification(strAuctionId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void groupNotification(String id) {
        ApiCall apiCall = new ApiCall(getActivity(), this);

    }

    @Override
    public void onRefresh() {
        //groupNotification(strAuctionId);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                //groupNotification(strAuctionId);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check class", "Group notification Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}

