package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.BussinessMsgSendersAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastReceivedResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 10/4/17.
 */

public class BussinessMsgSenders extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    String mContact;
    String product_id = "", service_id = "", vehicle_id = "";
     View root;
    BussinessMsgSendersAdapter mMsgReplyAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiCall mApiCall;
    List<BroadcastReceivedResponse.Success> mSuccesses = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root = inflater.inflate(R.layout.mybroadcastmsglist, null);
        mApiCall=new ApiCall(getActivity(),this);
        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_viewmsglist);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayoutBussinessChatmsglist);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //Set animation attribute to each item
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

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

                try {
                    Bundle b = getArguments();
                    product_id = b.getString("product_id");
                    service_id = b.getString("service_id");
                    vehicle_id = b.getString("vehicle_id");

                   mApiCall.getBroadcastReceivers(mContact,product_id,service_id,vehicle_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    @Override
    public void onRefresh() {
        try {
            Bundle b = getArguments();
            product_id = b.getString("product_id");
            service_id = b.getString("service_id");
            vehicle_id = b.getString("vehicle_id");

            mApiCall.getBroadcastReceivers(mContact,product_id,service_id,vehicle_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                mSuccesses.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                BroadcastReceivedResponse mGetBroadcastReceiver = (BroadcastReceivedResponse) response.body();
                for (BroadcastReceivedResponse.Success msenders : mGetBroadcastReceiver.getSuccess()){
                    msenders.setSender(msenders.getSender());
                    msenders.setSendername(msenders.getSendername());
                    mSuccesses.add(msenders);
                }
                mMsgReplyAdapter = new BussinessMsgSendersAdapter(getActivity(), mSuccesses,product_id,service_id,vehicle_id);
                mRecyclerView.setAdapter(mMsgReplyAdapter);
                mMsgReplyAdapter.notifyDataSetChanged();
            }else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        }else {
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
            Log.i("Check Class-", "Bussiness msg sender");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
