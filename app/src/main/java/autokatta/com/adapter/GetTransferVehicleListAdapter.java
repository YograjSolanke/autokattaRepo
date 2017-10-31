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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetTransferVehicleNotificationResponse;
import autokatta.com.response.GetTransferVehicleNotificationResponse.Success;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-005 on 30/10/17.
 */

public class GetTransferVehicleListAdapter extends BaseAdapter implements RequestNotifier {
    Activity mActivity;
    ApiCall mApiCall;
    List<GetTransferVehicleNotificationResponse.Success> mList;



    public GetTransferVehicleListAdapter(Activity activity, List<Success> mGetTransferVehicleList) {
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

        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.get_transfer_vehicle_adapter, null);
            holder = new ViewHolder();

            holder.mOwnerName = (TextView) view.findViewById(R.id.OwnerName);
            holder.mAddress = (TextView) view.findViewById(R.id.address);
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
            holder = (ViewHolder) view.getTag();
        }
        GetTransferVehicleNotificationResponse.Success success = mList.get(position);
        holder.mOwnerName.setText(success.getOldOwnerName());
        holder.mAddress.setText(success.getAddress());
        holder.mReason.setText(success.getTransferReason());
        holder.mDescription.setText(success.getDescription());
        holder.mVehicleTitle.setText(success.getVehicleName());

        //Set Profile Photo
        if (mList.get(position).getImage() == null || mList.get(position).getImage().equalsIgnoreCase("") || mList.get(position).getImage().equalsIgnoreCase(null)
                || mList.get(position).getImage().equalsIgnoreCase("null")) {
            holder.mImage.setBackgroundResource(R.drawable.hdlogo);
        } else {
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mList.get(position).getImage())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .override(110, 100)
                    .into(holder.mImage);
        }

        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mList.get(position).getMyContact());
            }
        });

        holder.mVehicleDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("vehicle_id", mList.get(position).getVehicleID());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                mActivity.startActivity(mVehicleDetails, options.toBundle());
            }
        });


        holder.mAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.TransferVehicle("accept", mList.get(position).getTransferID());
                mList.remove(mList.get(position));
                notifyDataSetChanged();
            }
        });

        holder.mReject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.TransferVehicle("reject", mList.get(position).getTransferID());
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
                CustomToast.customToast(mActivity, "Vehicle Transfered successfully");
            }else
                if (str.equalsIgnoreCase("transfer_reject_success"))
            {
                CustomToast.customToast(mActivity, "Vehicle Transfered Rejected");
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
