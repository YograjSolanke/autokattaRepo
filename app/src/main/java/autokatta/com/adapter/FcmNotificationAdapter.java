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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.my_inventory_container.TransferStock;
import autokatta.com.notifications.NotificationAddEmployeeActivity;
import autokatta.com.response.GetFCMNotificationResponse;
import autokatta.com.view.BussinessChatActivity;
import autokatta.com.view.ChatActivity;
import autokatta.com.view.GroupsActivity;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ProductViewActivity;
import autokatta.com.view.ReviewActivity;
import autokatta.com.view.ServiceViewActivity;
import autokatta.com.view.StoreViewActivity;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 30/11/17.
 */

public class FcmNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    String mLoginContact;
    private List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList = new ArrayList<>();

    public FcmNotificationAdapter(Activity activity, List<GetFCMNotificationResponse.Success.FCMNotification> mFcmNotiList1, String mLoginContact1) {
        mActivity = activity;
        mFcmNotiList = mFcmNotiList1;
        mLoginContact = mLoginContact1;
    }


    @Override
    public int getItemCount() {
        return mFcmNotiList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /* View Holder class */

    private class FcmViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName, mMessage, mDateTime;
        ImageView mUserPic;

        FcmViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView) itemView.findViewById(R.id.username);
            mMessage = (TextView) itemView.findViewById(R.id.message);
            mUserPic = (ImageView) itemView.findViewById(R.id.profilePic);
            mDateTime = (TextView) itemView.findViewById(R.id.dateTime);
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
            holder.mUserPic.setBackgroundResource(R.mipmap.profile);


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
                            Log.i("FcmAdapter", "-> Please check service, group id is not present");

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
                            Log.i("FcmAdapter", "-> Please check service, store id is not present");

                        }
                        break;

                    case "Vehicle":

                        /*For Offer*/
                        if (mFcmNotiList.get(holder.getAdapterPosition()).getVehicleID() != 0 &&
                                mFcmNotiList.get(holder.getAdapterPosition()).getMessage().equalsIgnoreCase("sent offer for your vehicle")) {
                            Intent i = new Intent(mActivity, BussinessChatActivity.class);
                            i.putExtra("callfrom", "myuploadedvehicle");
                            mActivity.startActivity(i);

                        } else if (mFcmNotiList.get(holder.getAdapterPosition()).getVehicleID() != 0) {
                            Intent intent = new Intent(mActivity, VehicleDetails.class);
                            intent.putExtra("vehicle_id", mFcmNotiList.get(holder.getAdapterPosition()).getVehicleID());
                            mActivity.startActivity(intent);

                        } else {
                            Log.i("FcmAdapter", "-> Please check service, vehicle id is not present");

                        }
                        break;

                    case "Product":
                        if (mFcmNotiList.get(holder.getAdapterPosition()).getProductID() != 0) {
                            Intent intent = new Intent(mActivity, ProductViewActivity.class);
                            intent.putExtra("product_id", mFcmNotiList.get(holder.getAdapterPosition()).getProductID());
                            mActivity.startActivity(intent);

                        } else {
                            Log.i("FcmAdapter", "-> Please check service, product id is not present");

                        }
                        break;

                    case "Service":
                        if (mFcmNotiList.get(holder.getAdapterPosition()).getServiceID() != 0) {
                            Intent intent = new Intent(mActivity, ServiceViewActivity.class);
                            intent.putExtra("service_id", mFcmNotiList.get(holder.getAdapterPosition()).getServiceID());
                            mActivity.startActivity(intent);

                        } else {
                            Log.i("FcmAdapter", "-> Please check service, service id is not present");

                        }
                        break;

                    case "TransferStockRequest":
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("bundle_storeId", 0);
                        mBundle.putString("bundle_contact", mLoginContact);
                        mActivity.startActivity(new Intent(mActivity, TransferStock.class).putExtras(mBundle));
                        break;

                        /*Simple chatting*/
                    case "ChatMessage":
                        Bundle b = new Bundle();
                        b.putString("sender", mFcmNotiList.get(holder.getAdapterPosition()).getContactNo());
                        b.putString("sendername", mFcmNotiList.get(holder.getAdapterPosition()).getUserName());
                        b.putInt("product_id", mFcmNotiList.get(holder.getAdapterPosition()).getProductID());
                        b.putInt("service_id", mFcmNotiList.get(holder.getAdapterPosition()).getServiceID());
                        b.putInt("vehicle_id", mFcmNotiList.get(holder.getAdapterPosition()).getVehicleID());
                        Intent intent = new Intent(mActivity, ChatActivity.class);
                        intent.putExtras(b);
                        mActivity.startActivity(intent);
                        break;

                /*Add Employee Request*/
                    case "EmployeeRequest":
                        mActivity.startActivity(new Intent(mActivity, NotificationAddEmployeeActivity.class));
                        /*Intent intent1 = new Intent(mActivity, NotificationAddEmployeeActivity.class);
                        mActivity.startActivity(intent1);*/
                        break;

                /*Review & Reply */
                    case "Review":

                        Intent intent2 = new Intent(mActivity, ReviewActivity.class);
                        intent2.putExtra("contact", mFcmNotiList.get(holder.getAdapterPosition()).getContactNo());
                        intent2.putExtra("store_id", mFcmNotiList.get(holder.getAdapterPosition()).getStoreID());
                        intent2.putExtra("product_id", mFcmNotiList.get(holder.getAdapterPosition()).getProductID());
                        intent2.putExtra("service_id", mFcmNotiList.get(holder.getAdapterPosition()).getServiceID());
                        intent2.putExtra("vehicle_id", mFcmNotiList.get(holder.getAdapterPosition()).getVehicleID());
                        mActivity.startActivity(intent2);

                }
            }
        });

    }

}
