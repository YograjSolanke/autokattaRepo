package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetRequestedContactsResponse;
import autokatta.com.view.OtherProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 28/9/17.
 */

public class RequestedMemberListAdapter extends RecyclerView.Adapter<RequestedMemberListAdapter.MyViewHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<GetRequestedContactsResponse.Success> mItemList = new ArrayList<>();
    private String mMyContact, mRequestedContact;
    private int mGroupId, mRequestId;
    private ApiCall mApiCall;
    private MyViewHolder mView;
    private ConnectionDetector mTestConnection;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mContact;
        ImageView mCall, mProfilePic;
        Button mAddMember;
        RelativeLayout mRelativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.names);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mCall = (ImageView) itemView.findViewById(R.id.cal_cnt);
            mProfilePic = (ImageView) itemView.findViewById(R.id.pro_pic);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);
            mAddMember = (Button) itemView.findViewById(R.id.add_member);
        }
    }

    public RequestedMemberListAdapter(Activity mActivity1, List<GetRequestedContactsResponse.Success> mItemList) {
        this.mActivity = mActivity1;
        this.mItemList = mItemList;
        mApiCall = new ApiCall(mActivity, this);
        mMyContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mTestConnection = new ConnectionDetector(mActivity);
    }

    @Override
    public RequestedMemberListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requested_member_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RequestedMemberListAdapter.MyViewHolder holder, final int position) {
        mView = holder;
        mGroupId = mItemList.get(position).getGroupID();
        mRequestedContact = mItemList.get(position).getRequestedContact();
        mRequestId = mItemList.get(position).getRequestID();

        holder.mName.setText(mItemList.get(position).getUsername());
        holder.mContact.setText(mItemList.get(position).getContact());

        //Set Profile Photo
        if (mItemList.get(position).getProfilePic() == null || mItemList.get(position).getProfilePic().equalsIgnoreCase("") || mItemList.get(position).getProfilePic().equalsIgnoreCase(null)
                || mItemList.get(position).getProfilePic().equalsIgnoreCase("null")) {
            holder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
        } else {
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mItemList.get(position).getProfilePic())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .override(110, 100)
                    .into(holder.mProfilePic);
        }


        holder.mAddMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.addContactInGroup(mGroupId, mRequestedContact);
                mItemList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(holder.getAdapterPosition()).getRequestedContact());
            }
        });


        holder.mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", holder.mContact.getText().toString());
                bundle.putString("action", "otherProfile");
                Log.i("Contact", "->" + holder.mContact.getText().toString());
                Intent mOtherProfile = new Intent(mActivity, OtherProfile.class);
                mOtherProfile.putExtras(bundle);
                mActivity.startActivity(mOtherProfile);
            }
        });


    }


    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "RequestedMemberListRefreshAdapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success\nsuccess_add_contact")) {
                CustomToast.customToast(mActivity, "Contact Added successfully");
                mApiCall.Like(mMyContact, mRequestedContact, "3", 0, mGroupId, 0, 0, 0, 0, 0);
                mApiCall.DeleteRequestedMember(mRequestId);
            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
    }

}
