package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetFCMNotificationResponse;

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

        TextView mText;

        FcmViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.not);
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

        holder.mText.setText(mFcmNotiList.get(holder.getAdapterPosition()).getUserName()
                + " " + mFcmNotiList.get(holder.getAdapterPosition()).getMessage());

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
