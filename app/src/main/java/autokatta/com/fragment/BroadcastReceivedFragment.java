package autokatta.com.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastReceivedResponse;
import autokatta.com.view.ChatActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastReceivedFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    public BroadcastReceivedFragment() {
        //Empty Constructor
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    String myContact;
    RecyclerView.LayoutManager layoutManager;
    List<BroadcastReceivedResponse.Success> broadcastMessageArrayList = new ArrayList<>();
    ApiCall apiCall;
    MsgReplyAdapter msgSenderAdapter;


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
                apiCall.getBroadcastReceivers(myContact, 0, 0, 0);
            }
        });
        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                broadcastMessageArrayList.clear();
                BroadcastReceivedResponse mGetGroupVehiclesResponse = (BroadcastReceivedResponse) response.body();
                for (BroadcastReceivedResponse.Success success : mGetGroupVehiclesResponse.getSuccess()) {
                    success.setSender(success.getSender());
                    success.setSendername(success.getSendername());
                    success.setMessage(success.getMessage());
                    //success.setDate(success.getDate());
                    success.setProfileImage(success.getProfileImage());
                    success.setLocation(success.getLocation());


                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(success.getDate());
                        //System.out.println("jjj"+date);
                        String output = outputFormat.format(date);
                        //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                        success.setDate(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        apiCall.getBroadcastReceivers(myContact, 0, 0, 0);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }







    ////////////////////////// Adapter Class /////////////////////////////////////////////

    class MsgReplyAdapter extends RecyclerView.Adapter<MsgReplyAdapter.MyViewHolder> {

        List<BroadcastReceivedResponse.Success> broadcastMessageArrayList;
        Activity activity;

        //ViewHolder Class
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
          //  TextView msgFrom, msgFromCnt;
            TextView msgFrom, msgFromCnt, lastMsg, lastMsgTime;
            ImageView profile, enquiry;
            CardView mCardView;

            public MyViewHolder(View itemView) {
                super(itemView);

                msgFrom = (TextView) itemView.findViewById(R.id.msgFrom1);
                msgFromCnt = (TextView) itemView.findViewById(R.id.msgFromCnt);
                lastMsg = (TextView) itemView.findViewById(R.id.msgText);
                lastMsgTime = (TextView) itemView.findViewById(R.id.msgTime);
                profile = (ImageView) itemView.findViewById(R.id.profile);
                enquiry = (ImageView) itemView.findViewById(R.id.enquiry);
                mCardView = (CardView) itemView.findViewById(R.id.mCardview);
                msgFrom = (TextView) itemView.findViewById(R.id.msgFrom1);
                msgFromCnt = (TextView) itemView.findViewById(R.id.msgFromCnt);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }


            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("sender", broadcastMessageArrayList.get(getAdapterPosition()).getSender());
                b.putString("sendername", broadcastMessageArrayList.get(getAdapterPosition()).getSendername());
                b.putString("senderLocation", broadcastMessageArrayList.get(getAdapterPosition()).getLocation());
                b.putInt("product_id", 0);
                b.putInt("service_id", 0);
                b.putInt("vehicle_id", 0);
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
        MsgReplyAdapter(Activity activity, List<BroadcastReceivedResponse.Success>
                broadcastMessageArrayList) {
            this.activity = activity;
            this.broadcastMessageArrayList = broadcastMessageArrayList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_sender_layout, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bussiness_msg_senders, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            BroadcastReceivedResponse.Success broadcastMessageObj = broadcastMessageArrayList.get(position);

            holder.enquiry.setVisibility(View.GONE);
            holder.msgFromCnt.setText(broadcastMessageObj.getSender());
            holder.msgFrom.setText(broadcastMessageObj.getSendername());
            holder.lastMsg.setText(broadcastMessageObj.getMessage());
            holder.lastMsgTime.setText(broadcastMessageObj.getDate());

            String dppath = activity.getString(R.string.base_image_url) +broadcastMessageObj.getProfileImage();
            Glide.with(activity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(holder.profile);
        //    holder.msgFromCnt.setText(broadcastMessageObj.getSender());
          //  holder.msgFrom.setText(broadcastMessageObj.getSendername());
        }

        @Override
        public int getItemCount() {
            return broadcastMessageArrayList.size();
        }


    }
}
