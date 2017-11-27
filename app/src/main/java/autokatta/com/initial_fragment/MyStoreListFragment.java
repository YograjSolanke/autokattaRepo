package autokatta.com.initial_fragment;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.github.clans.fab.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.MyStoreListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.view.CreateStoreActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 28/3/17
 */

public class MyStoreListFragment extends Fragment implements View.OnClickListener, RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mMyStoreList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    FloatingActionButton fabCreateStore;
    ArrayList<MyStoreResponse.Success> storeResponseArrayList;
    ApiCall apiCall;
    String myContact;
    boolean hasViewCreated = false;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    MyStoreListAdapter adapter;

    public MyStoreListFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreList = inflater.inflate(R.layout.fragment_mystorelist, container, false);
        return mMyStoreList;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                storeResponseArrayList = new ArrayList<>();
                storeResponseArrayList.clear();
                MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();
                if (!myStoreResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (MyStoreResponse.Success Sresponse : myStoreResponse.getSuccess()) {
                        Sresponse.setId(Sresponse.getId());
                        Sresponse.setName(Sresponse.getName());
                        Sresponse.setLocation(Sresponse.getLocation());
                        Sresponse.setWebsite(Sresponse.getWebsite());
                        Sresponse.setStoreOpenTime(Sresponse.getStoreOpenTime());
                        Sresponse.setStoreCloseTime(Sresponse.getStoreCloseTime());
                        Sresponse.setStoreImage(Sresponse.getStoreImage());
                        Sresponse.setCoverImage(Sresponse.getCoverImage());
                        Sresponse.setWorkingDays(Sresponse.getWorkingDays());
                        Sresponse.setRating(Sresponse.getRating());
                        Sresponse.setLikecount(Sresponse.getLikecount());
                        Sresponse.setFollowcount(Sresponse.getFollowcount());
                        Sresponse.setStoreType(Sresponse.getStoreType());
                        //getActivity().setTitle("My Store");
                        storeResponseArrayList.add(Sresponse);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter = new MyStoreListAdapter(getActivity(), storeResponseArrayList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("No Store Found")
                            .setMessage("No store found please create store first do you want to create store")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("className", "MyStoreListFragment");
                                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                    Intent intent = new Intent(getActivity(), CreateStoreActivity.class);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent, options.toBundle());
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "MyStoreListFragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabCreateStore:
                Bundle bundle = new Bundle();
                bundle.putString("className", "MyStoreListFragment");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(getActivity(), CreateStoreActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent, options.toBundle());
        }
    }

    @Override
    public void onRefresh() {
        apiCall.MyStoreList(myContact, 1, 10);
        mRecyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                apiCall.MyStoreList(myContact, 1, 10);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiCall = new ApiCall(getActivity(), this);
        mTestConnection = new ConnectionDetector(getActivity());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().setTitle("My Store");
                mNoData = (TextView) mMyStoreList.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                fabCreateStore = (FloatingActionButton) mMyStoreList.findViewById(R.id.fabCreateStore);
                mRecyclerView = (RecyclerView) mMyStoreList.findViewById(R.id.rv_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMyStoreList.findViewById(R.id.swipeRefreshLayoutMyStoreList);
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        apiCall.MyStoreList(myContact, 1, 10);
                    }
                });
            }
        });
        fabCreateStore.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
}
