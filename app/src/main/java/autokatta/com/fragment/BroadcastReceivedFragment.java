package autokatta.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.BroadcastReceivedResponse;
import retrofit2.Response;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastReceivedFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {


    public BroadcastReceivedFragment() {
        //Empty Constructor
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BroadcastReceivedResponse.Success> broadcastMessageArrayList = new ArrayList<>();
    ApiCall apiCall;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.my_broadcast_message_list, null);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        apiCall = new ApiCall(getActivity(), this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);


        //Set animation attribute to each item
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //   getGroupVehicles();
            }
        });
        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {
//
//        if (response!=null){
//            if (response.isSuccessful()){
//                mSwipeRefreshLayout.setRefreshing(false);
//                if (response.body() instanceof GetGroupVehiclesResponse){
//                    GetGroupVehiclesResponse mGetGroupVehiclesResponse = (GetGroupVehiclesResponse) response.body();
//                    for (GetGroupVehiclesResponse.Success success : mGetGroupVehiclesResponse.getSuccess()){
//
//                        mSuccesses.add(success);
//                    }
//                    mGroupVehicleRefreshAdapter = new GroupVehicleRefreshAdapter(getActivity(), mSuccesses);
//                    mRecyclerView.setAdapter(mGroupVehicleRefreshAdapter);
//                    mGroupVehicleRefreshAdapter.notifyDataSetChanged();
//                }
//            }else {
//                mSwipeRefreshLayout.setRefreshing(false);
//                CustomToast.customToast(getActivity(), getString(R.string._404));
//            }
//        }else {
//            mSwipeRefreshLayout.setRefreshing(false);
//            CustomToast.customToast(getActivity(), getString(R.string.no_response));
//        }


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
                    , "BroadcastReceivedFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }


    @Override
    public void onRefresh() {

    }
}
