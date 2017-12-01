package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetFCMNotificationResponse;
import autokatta.com.view.GroupsActivity;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.StoreViewActivity;

/**
 * Created by ak-003 on 30/11/17.
 */

public class FcmNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList = new ArrayList<>();

    public FcmNotificationAdapter(Activity activity, List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList1) {
        mActivity = activity;
        mFcmNotiList = mFcmNotiList1;
    }

    private class FcmViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName, mMessage;

        FcmViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView) itemView.findViewById(R.id.username);
            mMessage = (TextView) itemView.findViewById(R.id.message);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fcm_notification, parent, false);
        return new FcmViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final FcmViewHolder holder = (FcmViewHolder) holder1;

        holder.mUserName.setText(mFcmNotiList.get(holder.getAdapterPosition()).getUserName());
        holder.mMessage.setText(mFcmNotiList.get(holder.getAdapterPosition()).getMessage());

        holder.mUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, OtherProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", mFcmNotiList.get(holder.getAdapterPosition()).getContactNo());
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });

        holder.mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mFcmNotiList.get(holder.getAdapterPosition()).getLayout()) {
                    case "Group":
                        if (mFcmNotiList.get(holder.getAdapterPosition()).getGroupID() != 0) {

                            Intent i = new Intent(mActivity, GroupsActivity.class);

                            i.putExtra("grouptype", "OtherGroup");
                            i.putExtra("className", "OtherProfile");
                            i.putExtra("bundle_Contact", mFcmNotiList.get(holder.getAdapterPosition()).getContactNo());
                            i.putExtra("bundle_GroupId", mFcmNotiList.get(holder.getAdapterPosition()).getGroupID());

                            mActivity.startActivity(i);

                        } else {
                            Log.i("FcmAdapter", "-> Please check service group id is not present");

                        }
                        break;

                    case "Store":
                        if (mFcmNotiList.get(holder.getAdapterPosition()).getStoreID() != 0) {
                            Bundle b = new Bundle();

                            b.putInt("store_id", mFcmNotiList.get(holder.getAdapterPosition()).getStoreID());
                            //b.putString("StoreContact", mFcmNotiList.get(holder.getAdapterPosition()).getContactNo());
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent intent = new Intent(mActivity, StoreViewActivity.class);
                            intent.putExtras(b);
                            mActivity.startActivity(intent, options.toBundle());

                        } else {
                            Log.i("FcmAdapter", "-> Please check service store id is not present");

                        }
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFcmNotiList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
