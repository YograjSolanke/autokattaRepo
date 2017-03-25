package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.R.id.fabCreateGroup;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mMemberList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatCreateGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberList = inflater.inflate(R.layout.fragment_member_list, container, false);
        mRecyclerView = (RecyclerView) mMemberList.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mMemberList.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMemberList.findViewById(R.id.swipeRefreshLayout);
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
                getGroupContact(getActivity().getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE)
                        .getString("group_id",""));
            }
        });
        return mMemberList;
    }

    /*
    Get Group Contact...
     */
    private void getGroupContact(String group_id) {

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
