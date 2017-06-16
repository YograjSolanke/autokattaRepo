package autokatta.com.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
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
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastSendResponse;
import autokatta.com.view.ChatActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastSendFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    public BroadcastSendFragment() {
        //Empty Constuctor here
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    String myContact;
    MsgReplyAdapter msgSenderAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<BroadcastSendResponse.Success> broadcastMessageArrayList = new ArrayList<>();
    ApiCall apiCall;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.my_broadcast_message_list, null);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        apiCall = new ApiCall(getActivity(), this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

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
                apiCall.getBroadcastSenders(myContact);
            }
        });
        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                broadcastMessageArrayList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                BroadcastSendResponse mGetGroupVehiclesResponse = (BroadcastSendResponse) response.body();
                for (BroadcastSendResponse.Success success : mGetGroupVehiclesResponse.getSuccess()) {
                    success.setReceiver(success.getReceiver());
                    success.setReceivername(success.getReceivername());
                    broadcastMessageArrayList.add(success);
                }

                mSwipeRefreshLayout.setRefreshing(false);
                msgSenderAdapter = new MsgReplyAdapter(getActivity(), broadcastMessageArrayList);
                recyclerView.setAdapter(msgSenderAdapter);
                msgSenderAdapter.notifyDataSetChanged();

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
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
        }else {
            Log.i("Check Class-"
                    , "BroadcastSendFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        apiCall.getBroadcastSenders(myContact);
    }


    ////////////////////////// Adapter Class /////////////////////////////////////////////

    class MsgReplyAdapter extends RecyclerView.Adapter<MsgReplyAdapter.MyViewHolder> {
        List<BroadcastSendResponse.Success> broadcastMessageArrayList;
        Activity activity;

        //ViewHolder Class
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            TextView msgFrom, msgFromCnt;

            public MyViewHolder(View itemView) {
                super(itemView);
                msgFrom = (TextView) itemView.findViewById(R.id.msgFrom);
                msgFromCnt = (TextView) itemView.findViewById(R.id.msgFromCnt);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("sender", broadcastMessageArrayList.get(getAdapterPosition()).getReceiver());
                b.putString("sendername", broadcastMessageArrayList.get(getAdapterPosition()).getReceivername());
                b.putString("product_id", "");
                b.putString("service_id", "");
                b.putString("vehicle_id", "");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent, options.toBundle());

            }

            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(activity,"onLongggClick----",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //Constructor of Main Adapter
        MsgReplyAdapter(Activity activity, List<BroadcastSendResponse.Success>
                broadcastMessageArrayList) {
            this.activity = activity;
            this.broadcastMessageArrayList = broadcastMessageArrayList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_sender_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            BroadcastSendResponse.Success broadcastMessageObj = broadcastMessageArrayList.get(position);
            holder.msgFromCnt.setText(broadcastMessageObj.getReceiver());
            holder.msgFrom.setText(broadcastMessageObj.getReceivername());
        }

        @Override
        public int getItemCount() {
            return broadcastMessageArrayList.size();
        }
    }
}
