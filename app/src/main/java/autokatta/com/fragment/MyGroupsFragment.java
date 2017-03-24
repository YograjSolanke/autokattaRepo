package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.About;
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

public class MyGroupsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, View.OnClickListener {
    View mMyGroups;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatCreateGroup;
    List<ModelGroups> mMyGroupsList = new ArrayList<>();
    MyAdapter mMyAdapter;

    public MyGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);

        mRecyclerView = (RecyclerView) mMyGroups.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mMyGroups.findViewById(fabCreateGroup);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyGroups.findViewById(R.id.swipeRefreshLayout);
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
        return mMyGroups;
    }

    /*
    Get My Groups Data...
     */
    private void getData(String loginContact) {

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.Groups(loginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                mSwipeRefreshLayout.setRefreshing(false);
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                for (ProfileGroupResponse.MyGroup success : profileGroupResponse.getSuccess().getMyGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(success.getId());
                    modelGroups.setTitle(success.getTitle());
                    modelGroups.setImage(success.getImage());
                    modelGroups.setGroupCount(success.getGroupcount());
                    modelGroups.setVehicleCount(success.getVehiclecount());
                    modelGroups.setAdminVehicleCount(success.getAdminVehicleCount());
                    mMyGroupsList.add(modelGroups);
                }
                mMyAdapter = new MyAdapter(getActivity(), mMyGroupsList,"MyGroups");
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
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "MyGroupsFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case fabCreateGroup:
                About about = new About();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.groupFrame, about).commit();

                break;

        }

    }

    @Override
    public void onRefresh() {
        mMyGroupsList.clear();
        getData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }
}
