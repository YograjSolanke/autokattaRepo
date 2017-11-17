package autokatta.com.adapter;

import android.Manifest.permission;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetTransferEnquiryRequestResponse;
import autokatta.com.view.NewVehicleDetails;
import autokatta.com.view.ProductViewActivity;
import autokatta.com.view.ServiceViewActivity;
import autokatta.com.view.VehicleDetails;
import retrofit2.Response;

/**
 * Created by ak-005 on 17/11/17.
 */

public class TransferEnquiryAdapter extends BaseAdapter implements RequestNotifier{
    Activity mActivity;
    ApiCall mApiCall;
    List<GetTransferEnquiryRequestResponse.Success.Employee> mList;



    public TransferEnquiryAdapter(Activity activity, List<GetTransferEnquiryRequestResponse.Success.Employee> mGetTransferVehicleList) {
        this.mActivity = activity;
        this.mList = mGetTransferVehicleList;
        mApiCall = new ApiCall(mActivity, this);

    }


    static class ViewHolder {
        TextView mOwnerName, mAddress, mReason, mDescription, mVehicleTitle;
        Button mAccept, mReject;
        ImageView mImage, mCall;
        RelativeLayout mVehicleDetails;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        GetTransferVehicleListAdapter.ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.transfer_enquiry_adapter, null);
            holder = new GetTransferVehicleListAdapter.ViewHolder();

            holder.mOwnerName = (TextView) view.findViewById(R.id.OwnerName);
            holder.mReason = (TextView) view.findViewById(R.id.reason);
            holder.mDescription = (TextView) view.findViewById(R.id.description);
            holder.mVehicleTitle = (TextView) view.findViewById(R.id.txtadmin);
            holder.mAccept = (Button) view.findViewById(R.id.accept);
            holder.mReject = (Button) view.findViewById(R.id.reject);
            holder.mCall = (ImageView) view.findViewById(R.id.call);
            holder.mImage = (ImageView) view.findViewById(R.id.pro_pic);
            holder.mVehicleDetails = (RelativeLayout) view.findViewById(R.id.relative);

            view.setTag(holder);
        } else {
            holder = (GetTransferVehicleListAdapter.ViewHolder) view.getTag();
        }
        final GetTransferEnquiryRequestResponse.Success.Employee success = mList.get(position);
      //  holder.mOwnerName.setText(success.getTransferToName());
       /// holder.mAddress.setText(success.getAddress());
        holder.mReason.setText(success.getReasonForTransfer());
        holder.mDescription.setText(success.getDescription());
        holder.mVehicleTitle.setText(success.getKeyword());


        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mList.get(position).getMyContact());
            }
        });

        holder.mVehicleDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (success.getKeyword().equalsIgnoreCase("Used Vehicle"))
                {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("vehicle_id", Integer.valueOf(mList.get(position).getPSVNID()));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                    mVehicleDetails.putExtras(mBundle);
                    mActivity.startActivity(mVehicleDetails, options.toBundle());
                }else if (success.getKeyword().equalsIgnoreCase("Products"))
                {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("product_id", Integer.valueOf(mList.get(position).getPSVNID()));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent mVehicleDetails = new Intent(mActivity, ProductViewActivity.class);
                    mVehicleDetails.putExtras(mBundle);
                    mActivity.startActivity(mVehicleDetails, options.toBundle());
                }else if (success.getKeyword().equalsIgnoreCase("Services"))
                {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("service_id", Integer.valueOf(mList.get(position).getPSVNID()));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent mVehicleDetails = new Intent(mActivity, ServiceViewActivity.class);
                    mVehicleDetails.putExtras(mBundle);
                    mActivity.startActivity(mVehicleDetails, options.toBundle());
                }else if (success.getKeyword().equalsIgnoreCase("New Vehicle"))
                {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("newVehicleId", Integer.valueOf(mList.get(position).getPSVNID()));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent mVehicleDetails = new Intent(mActivity, NewVehicleDetails.class);
                    mVehicleDetails.putExtras(mBundle);
                    mActivity.startActivity(mVehicleDetails, options.toBundle());
                }


            }
        });


        holder.mAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.transferEnquiryAcceptStatus( mList.get(position).getTransferEnquiryID(),"accept");
                mList.remove(mList.get(position));
                notifyDataSetChanged();
            }
        });

        holder.mReject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.transferEnquiryAcceptStatus( mList.get(position).getTransferEnquiryID(),"reject");
                mList.remove(mList.get(position));
                notifyDataSetChanged();

            }
        });

        return view;
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("transfer_accept_success")) {
                CustomToast.customToast(mActivity, "Enquiry Transfered successfully");
            }else
            if (str.equalsIgnoreCase("transfer_reject_success"))
            {
                CustomToast.customToast(mActivity, "Enquiry Transfered Rejected");
            }
        }
    }


    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
