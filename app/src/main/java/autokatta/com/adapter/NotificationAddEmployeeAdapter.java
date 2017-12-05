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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetMyRequestsForEmployeeResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 4/12/17.
 */

public class NotificationAddEmployeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    Activity mActivity;
    private List<GetMyRequestsForEmployeeResponse.Success> mMyRequestsList;

    public NotificationAddEmployeeAdapter(Activity activity, List<GetMyRequestsForEmployeeResponse.Success> myRequests) {
        mActivity = activity;
        mMyRequestsList = myRequests;
    }

    @Override
    public int getItemCount() {
        return mMyRequestsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    private class EmployeeHolder extends RecyclerView.ViewHolder {

        Button mBtnApprove, mBtnReject;
        TextView mSenderName, mMessage, mStoreName;
        ImageView mSenderPic;

        EmployeeHolder(View itemView) {
            super(itemView);
            mBtnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            mBtnReject = (Button) itemView.findViewById(R.id.btnReject);
            mSenderName = (TextView) itemView.findViewById(R.id.username);
            mMessage = (TextView) itemView.findViewById(R.id.message);
            mSenderPic = (ImageView) itemView.findViewById(R.id.profilePic);
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

        holder.mSenderName.setText(mMyRequestsList.get(holder.getAdapterPosition()).getSenderName());
        holder.mStoreName.setText(mMyRequestsList.get(holder.getAdapterPosition()).getStoreName());


        if (mMyRequestsList.get(holder.getAdapterPosition()).getSenderPicture() != null ||
                !mMyRequestsList.get(holder.getAdapterPosition()).getSenderPicture().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) +
                            mMyRequestsList.get(holder.getAdapterPosition()).getSenderPicture())
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mSenderPic);
        } else
            holder.mSenderPic.setBackgroundResource(R.mipmap.profile);

        holder.mBtnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrReject(mMyRequestsList.get(holder.getAdapterPosition()).getStoreEmplyeeID(), "Accepted");
                mMyRequestsList.remove(mMyRequestsList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });

        holder.mBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrReject(mMyRequestsList.get(holder.getAdapterPosition()).getStoreEmplyeeID(), "Rejected");
                mMyRequestsList.remove(mMyRequestsList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });

        holder.mSenderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, OtherProfile.class);
                bundle.putString("contactOtherProfile", mMyRequestsList.get(holder.getAdapterPosition()).getStoreContactNo());
                intent.putExtras(bundle);
                mActivity.startActivity(intent, options.toBundle());
            }
        });

        holder.mStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("store_id", mMyRequestsList.get(holder.getAdapterPosition()).getStoreID());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, StoreViewActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
            }
        });
    }


    private void acceptOrReject(Integer storeEmplyeeID, String status) {
        ApiCall mApiCall = new ApiCall(mActivity, this);
        mApiCall.updateDeleteEmployee(storeEmplyeeID, "", "", "", "",
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
                    , "NotificationAddEmployeeAdapter");
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