package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
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
import autokatta.com.initial_fragment.CreateGroupFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 19/3/17.
 */

public class MyGroupsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, View.OnClickListener {
    View mMyGroups;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<ModelGroups> mMyGroupsList = new ArrayList<>();
    com.github.clans.fab.FloatingActionButton mFab;
    MyAdapter mMyAdapter;
    ApiCall mApiCall;
    private TextView mPlaceHolder;
    private ImageButton mNoInternetIcon;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;
    Activity activity;

    public MyGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);
        return mMyGroups;
    }

    /*
    Get My Groups Data...
     */
    private void getData(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.Groups(loginContact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            // errorMessage(activity, getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                mMyGroupsList.clear();
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                if (!profileGroupResponse.getSuccess().getMyGroups().isEmpty()) {
                    mNoInternetIcon.setVisibility(View.GONE);
                    for (ProfileGroupResponse.MyGroup success : profileGroupResponse.getSuccess().getMyGroups()) {
                        ModelGroups modelGroups = new ModelGroups();
                        modelGroups.setId(success.getId());
                        modelGroups.setTitle(success.getTitle());
                        modelGroups.setImage(success.getImage());
                        modelGroups.setGroupCount(success.getGroupcount());
                        modelGroups.setVehicleCount(success.getVehiclecount());
                        modelGroups.setAdminVehicleCount(success.getAdminVehicleCount());
                        modelGroups.setProductcount(success.getProductcount());
                        modelGroups.setServicecount(success.getServicecount());

                        mMyGroupsList.add(modelGroups);
                    }
                    mMyAdapter = new MyAdapter(getActivity(), mMyGroupsList, "MyGroups");
                    mRecyclerView.setAdapter(mMyAdapter);
                    mMyAdapter.notifyDataSetChanged();
                } else {
                    mPlaceHolder.setVisibility(View.VISIBLE);
                    mNoInternetIcon.setVisibility(View.GONE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                //if (isAdded())
                //CustomToast.customToast(getActivity(),getString(R.string._404_));
                //showMessage(activity, getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //showMessage(activity, getString(R.string.no_response));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (activity != null) {
                activity = getActivity();
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(),getString(R.string.no_response));
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
                    , "mygroupfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabCreateGroup:
                /*(1) Create groups after the last step of adding members
                     Allow adding members but after adding check for payment and ask for payment.
                     And (2) On click of a group name in groups list check if payment made.
                     750/- Per month
                     2100/- per quarter
                     3900 /- half yearly
                     6600/- annual
                     */

                CreateGroupFragment createGroupFragment = new CreateGroupFragment();
                Bundle b = new Bundle();
                b.putString("classname", "MyGroupFragment");
                createGroupFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.group_container, createGroupFragment, "createGroupFragment")
                        .addToBackStack("createGroupFragment")
                        .commit();
                break;
        }
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
        mApiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mPlaceHolder = (TextView) mMyGroups.findViewById(R.id.no_category);
                mNoInternetIcon = (ImageButton) mMyGroups.findViewById(R.id.icon_nointernet);
                mPlaceHolder.setVisibility(View.GONE);
                mFab = (com.github.clans.fab.FloatingActionButton) mMyGroups.findViewById(R.id.fabCreateGroup);
                mRecyclerView = (RecyclerView) mMyGroups.findViewById(R.id.rv_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMyGroups.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));//Get Api...
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
                /*
                On Scrolled Changed Listener...
                 */
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        /*if (dy > 0 ||dy<0 && mFab.isShown())
                            mFab.hide();*/
                        if (dy > 0) {
                            // Scroll Down
                            if (mFab.isShown()) {
                                mFab.hide(true);
                            }
                        } else if (dy < 0) {
                            // Scroll Up
                            if (!mFab.isShown()) {
                                mFab.show(true);
                            }
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        /*if (newState == RecyclerView.SCROLL_STATE_IDLE){
                            mFab.show();
                        }*/
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mFab.setOnClickListener(this);
    }

}
