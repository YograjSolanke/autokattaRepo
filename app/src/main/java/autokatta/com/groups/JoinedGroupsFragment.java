package autokatta.com.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.R.id.fabCreateGroup;


/**
 * Created by ak-003 on 19/3/17.
 */

public class JoinedGroupsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mJoinedGroups;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatCreateGroup;
    List<ModelGroups> mJoinedGroupsList = new ArrayList<>();
    MyAdapter mMyAdapter;

    public JoinedGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mJoinedGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);

        mRecyclerView = (RecyclerView) mJoinedGroups.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mJoinedGroups.findViewById(fabCreateGroup);
        floatCreateGroup.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mJoinedGroups.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //getData();//Get Api...
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));
            }
        });
        return mJoinedGroups;
    }

    /*
    Get Group Data...
     */
    private void getData(String loginContact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.profileGroup(loginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                mSwipeRefreshLayout.setRefreshing(false);
                mJoinedGroupsList.clear();
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                for (ProfileGroupResponse.JoinedGroup joinedGroup : profileGroupResponse.getSuccess().getJoinedGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(joinedGroup.getId());
                    modelGroups.setTitle(joinedGroup.getTitle());
                    modelGroups.setImage(joinedGroup.getImage());
                    modelGroups.setGroupCount(joinedGroup.getGroupcount());
                    modelGroups.setVehicleCount(joinedGroup.getVehiclecount());
                    mJoinedGroupsList.add(modelGroups);
                }
                mMyAdapter = new MyAdapter(getActivity(), mJoinedGroupsList,"JoinedGroups");
                mRecyclerView.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
            }else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        }else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof ConnectException) {
            Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof UnknownHostException) {
            Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
        } else {
            Log.i("Check Class-"
                    , "JoinedGroupsFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        mJoinedGroupsList.clear();
        getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }
}
