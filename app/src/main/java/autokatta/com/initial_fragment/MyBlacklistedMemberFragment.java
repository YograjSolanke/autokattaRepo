package autokatta.com.initial_fragment;

import android.content.SharedPreferences;
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
import autokatta.com.adapter.BlacklistMemberAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BlacklistMemberResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyBlacklistedMemberFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ApiCall apiCall;
    boolean hasViewCreated = false;
    TextView mNoData;
    View view;
    SharedPreferences sharedPreferences;
    List<BlacklistMemberResponse.Success> blacklistMemberList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blacklisted_members, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) view.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutBlacklist);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        apiCall = new ApiCall(getActivity(), this);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        // final String myContact = sharedPreferences.getString("loginContact", "7841023392");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                apiCall.getBlackListMembers(sharedPreferences.getString("loginContact", ""));
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                apiCall = new ApiCall(getActivity(), this);
                apiCall.getBlackListMembers(sharedPreferences.getString("loginContact", ""));
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        apiCall.getBlackListMembers(sharedPreferences.getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                blacklistMemberList.clear();
                BlacklistMemberResponse blacklistMemberResponse = (BlacklistMemberResponse) response.body();
                if (!blacklistMemberResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (BlacklistMemberResponse.Success success : blacklistMemberResponse.getSuccess()) {
                        success.setId(success.getId());
                        success.setBlacklistContact(success.getBlacklistContact());
                        success.setUsername(success.getUsername());
                        success.setUserimage(success.getUserimage());
                        blacklistMemberList.add(success);
                    }
                    BlacklistMemberAdapter adapter = new BlacklistMemberAdapter(getActivity(), blacklistMemberList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                CustomToast.customToast(getActivity(),getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        swipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(),getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "MyBlacklistedMemberFragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
