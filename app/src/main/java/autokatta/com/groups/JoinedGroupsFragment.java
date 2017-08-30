package autokatta.com.groups;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
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
    com.github.clans.fab.FloatingActionButton floatCreateGroup;
    List<ModelGroups> mJoinedGroupsList = new ArrayList<>();
    MyAdapter mMyAdapter;
    private TextView mPlaceHolder;
    boolean _hasLoadedOnce = false;
    private ImageButton mNoInternetIcon;
    ConnectionDetector mTestConnection;

    public JoinedGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mJoinedGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);
        return mJoinedGroups;
    }

    /*
    Get Group Data...
     */

    private void getData(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.profileGroup(loginContact);
        } else {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));

            // errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                mJoinedGroupsList.clear();
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                if (!profileGroupResponse.getSuccess().getJoinedGroups().isEmpty()) {
                    mNoInternetIcon.setVisibility(View.GONE);
                    for (ProfileGroupResponse.JoinedGroup joinedGroup : profileGroupResponse.getSuccess().getJoinedGroups()) {
                        ModelGroups modelGroups = new ModelGroups();
                        modelGroups.setId(joinedGroup.getId());
                        modelGroups.setTitle(joinedGroup.getTitle());
                        modelGroups.setImage(joinedGroup.getImage());
                        modelGroups.setGroupCount(joinedGroup.getGroupcount());
                        modelGroups.setVehicleCount(joinedGroup.getVehiclecount());
                        modelGroups.setServicecount(joinedGroup.getServicecount());
                        modelGroups.setProductcount(joinedGroup.getProductcount());
                        mJoinedGroupsList.add(modelGroups);
                    }
                    mMyAdapter = new MyAdapter(getActivity(), mJoinedGroupsList, "JoinedGroups");
                    mRecyclerView.setAdapter(mMyAdapter);
                    mRecyclerView.getRecycledViewPool().clear();
                    mMyAdapter.notifyDataSetChanged();
                } else {
                    mPlaceHolder.setVisibility(View.VISIBLE);
                    //mNoInternetIcon.setVisibility(View.GONE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
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
            Log.i("Check Class-"
                    , "joinedgroupsfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
        mRecyclerView.getRecycledViewPool().clear();
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mPlaceHolder = (TextView) mJoinedGroups.findViewById(R.id.no_category);
                mNoInternetIcon = (ImageButton) mJoinedGroups.findViewById(R.id.icon_nointernet);
                mPlaceHolder.setVisibility(View.GONE);
                mRecyclerView = (RecyclerView) mJoinedGroups.findViewById(R.id.rv_recycler_view);
                floatCreateGroup = (com.github.clans.fab.FloatingActionButton) mJoinedGroups.findViewById(fabCreateGroup);
                floatCreateGroup.setVisibility(View.GONE);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mJoinedGroups.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        Activity activity = getActivity();
                        if (activity != null) {
                            getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                    .getString("loginContact", ""));
                        }
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                Activity activity = getActivity();
                if (activity != null) {
                    getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""));
                }
            }
        });
    }

    /*public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                .getString("loginContact", ""));
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
