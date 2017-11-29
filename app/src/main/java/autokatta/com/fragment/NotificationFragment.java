package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 20/11/17.
 */

public class NotificationFragment extends Fragment implements RequestNotifier, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    View mNotificationView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mWallNotify;
    TextView mNoData;
    boolean _hasLoadedOnce = false;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;

    public NotificationFragment() {

        //Empty Constuctor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mNotificationView = inflater.inflate(R.layout.fragment_notification, container, false);
        return mNotificationView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    /*dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");*/
                mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) mNotificationView.findViewById(R.id.no_category);
                // layout = (LinearLayout) mNotificationView.findViewById(R.id.no_connection);

                mRecyclerView = (RecyclerView) mNotificationView.findViewById(R.id.recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mNotificationView.findViewById(R.id.swipeRefreshLayout);

                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                //mLayoutManager.setReverseLayout(true);
                //mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                // mRecyclerView.setAdapter(adapter);

                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        // getData(1, 10);
                    }
                });

            }
        });
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //layout.setVisibility(View.VISIBLE);
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            // layout.setVisibility(View.VISIBLE);
        } else {
            Log.i("Check Class-", "Wall Notification Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
