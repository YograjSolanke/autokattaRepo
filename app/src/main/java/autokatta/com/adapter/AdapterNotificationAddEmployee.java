package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetFCMNotificationResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 4/12/17.
 */

public class AdapterNotificationAddEmployee extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    Activity mActivity;

    public AdapterNotificationAddEmployee(Activity activity, List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList1) {
        mActivity = activity;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    private class EmployeeHolder extends RecyclerView.ViewHolder {

        String strSenderName, strMessage;
        Button mBtnApprove, mBtnReject;
        TextView mSenderName, mMessage, mStoreName;
        ImageView mUserPic;

        EmployeeHolder(View itemView) {
            super(itemView);
            mBtnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            mBtnReject = (Button) itemView.findViewById(R.id.btnReject);
            mSenderName = (TextView) itemView.findViewById(R.id.username);
            mMessage = (TextView) itemView.findViewById(R.id.message);
            mUserPic = (ImageView) itemView.findViewById(R.id.profilePic);
            mStoreName = (TextView) itemView.findViewById(R.id.storename);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notification_add_employee, parent, false);
        return new EmployeeHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final EmployeeHolder holder = (EmployeeHolder) holder1;

            /*holder.mSenderName.setText(mFcmNotiList.get(holder.getAdapterPosition()).getUserName());
            holder.mMessage.setText(mFcmNotiList.get(holder.getAdapterPosition()).getMessage());
            holder.mDateTime.setText(mFcmNotiList.get(holder.getAdapterPosition()).getDateTime());

            if (mFcmNotiList.get(holder.getAdapterPosition()).getProfilePicture() != null ||
                    !mFcmNotiList.get(holder.getAdapterPosition()).getProfilePicture().equals("")) {

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url)+
                                mFcmNotiList.get(holder.getAdapterPosition()).getProfilePicture())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mUserPic);
            }else
                holder.mUserPic.setBackgroundResource(R.mipmap.profile);*/

        holder.mBtnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrReject("Accepted");
            }
        });

        holder.mBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrReject("Rejected");
            }
        });
    }


    private void acceptOrReject(String status) {
        ApiCall mApiCall = new ApiCall(mActivity, this);
        mApiCall.updateDeleteEmployee(0, "", "", "", "",
                "", "Request", status);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "AdapterNotificationAddEmployee");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("Success_Request_Accept")) {
                CustomToast.customToast(mActivity, "you accepted the request");
            } else if (str.equalsIgnoreCase("Success_Request_Reject")) {
                CustomToast.customToast(mActivity, "you rejected the request");
            }
            notifyDataSetChanged();

        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
    }
}