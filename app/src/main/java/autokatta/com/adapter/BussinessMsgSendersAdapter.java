package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.BroadcastReceivedResponse;
import autokatta.com.view.ChatActivity;

/**
 * Created by ak-005 on 10/4/17.
 */

public class BussinessMsgSendersAdapter extends RecyclerView.Adapter<BussinessMsgSendersAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<BroadcastReceivedResponse.Success> mItemList = new ArrayList<>();

    String msender, msendername;
    String product_id = "", service_id = "", vehicle_id = "";

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView msgFrom, msgFromCnt;


        public MyViewHolder(View itemView) {
            super(itemView);
            msgFrom = (TextView) itemView.findViewById(R.id.msgFrom);
            msgFromCnt = (TextView) itemView.findViewById(R.id.msgFromCnt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle b = new Bundle();
            b.putString("sender", msender);
            b.putString("sendername", msendername);
            b.putString("product_id", product_id);
            b.putString("service_id", service_id);
            b.putString("vehicle_id", vehicle_id);

            Intent intent = new Intent(mActivity, ChatActivity.class);
            intent.putExtras(b);
            mActivity.startActivity(intent);
        }
    }

    public BussinessMsgSendersAdapter(Activity mActivity, List<BroadcastReceivedResponse.Success> mItemList, String product_id, String service_id, String vehicle_id) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.product_id = product_id;
        this.service_id = service_id;
        this.vehicle_id = vehicle_id;

    }

    @Override
    public BussinessMsgSendersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bussiness_msg_senders, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BussinessMsgSendersAdapter.MyViewHolder holder, int position) {

        msender = mItemList.get(position).getSender();
        msendername = mItemList.get(position).getSendername();
        holder.msgFromCnt.setText(msender);
        holder.msgFrom.setText(msendername);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

