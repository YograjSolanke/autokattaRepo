//member list frag
package autokatta.com.groups;

import android.content.Context;
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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MemberListRefreshAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.GroupContactFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupContactsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mMemberList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatCreateGroup;
    List<GetGroupContactsResponse.Success> mSuccesses = new ArrayList<>();
    List<String> mlist = new ArrayList<>();
    MemberListRefreshAdapter mMemberListAdapter;
    String call;
    Bundle bundle = new Bundle();
    String group_id;
    String mCallfrom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberList = inflater.inflate(R.layout.fragment_member_list, container, false);
        mRecyclerView = (RecyclerView) mMemberList.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mMemberList.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMemberList.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setHasFixedSize(true);
        group_id = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("group_id", "");
        bundle = getArguments();
        if (bundle != null) {
            // group_id = bundle.getString("id");
            call = bundle.getString("call");
            System.out.println("Call in MemberList:" + call);
        }

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
                getGroupContact(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("group_id", ""));
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCallfrom = bundle.getString("grouptype");
            Log.i("Other", "->" + mCallfrom);
        } else
            mCallfrom = "groups";
//For Other Profile
        if (mCallfrom.equalsIgnoreCase("OtherGroup")) {
            floatCreateGroup.setVisibility(View.GONE);
        }


        floatCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GroupContactFragment fragment = new GroupContactFragment();
                Bundle b = new Bundle();
                //pass Group Id to ContactFrag as a Bundle
                b.putString("id", group_id);
                // b.putStringArrayList("list",);
                b.putString("call", "existGroup");
                fragment.setArguments(b);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ///If call from Profile
                if (mCallfrom.equalsIgnoreCase("profile")) {
                    fragmentTransaction.replace(R.id.profile_groups_container, fragment);
                    fragmentTransaction.addToBackStack("groupcontactfragment");
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.replace(R.id.group_container, fragment);
                    fragmentTransaction.addToBackStack("groupcontactfragment");
                    fragmentTransaction.commit();
                }
            }
        });

        return mMemberList;
    }


    /*
    Get Group Contact...
     */
    private void getGroupContact(String group_id) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getGroupContacts(group_id);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSuccesses.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                GetGroupContactsResponse mGetGroupContactsResponse = (GetGroupContactsResponse) response.body();
                for (GetGroupContactsResponse.Success success : mGetGroupContactsResponse.getSuccess()) {
                    success.setUsername(success.getUsername());
                    success.setContact(success.getContact());
                    success.setStatus(success.getStatus());
                    success.setDp(success.getDp());
                    success.setMember(success.getMember());
                    success.setVehiclecount(success.getVehiclecount());
                    mSuccesses.add(success);
                }
                if (mCallfrom.equals("profile")) {
                    mMemberListAdapter = new MemberListRefreshAdapter(getActivity(), mSuccesses, mCallfrom);
                    mRecyclerView.setAdapter(mMemberListAdapter);
                    mMemberListAdapter.notifyDataSetChanged();

                } else if (mCallfrom.equals("groups") || mCallfrom.equalsIgnoreCase("OtherGroup") || mCallfrom.equalsIgnoreCase("MyGroup")) {
                    mMemberListAdapter = new MemberListRefreshAdapter(getActivity(), mSuccesses);
                    mRecyclerView.setAdapter(mMemberListAdapter);
                    mMemberListAdapter.notifyDataSetChanged();
                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
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
            Log.i("Check Class-", "MemberList Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        mSuccesses.clear();
        getGroupContact(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("group_id", ""));
    }
}