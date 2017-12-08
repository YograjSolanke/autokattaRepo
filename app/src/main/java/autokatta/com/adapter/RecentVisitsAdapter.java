package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetMyRecentVisitsResponse;
import autokatta.com.view.NewVehicleDetails;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ProductViewActivity;
import autokatta.com.view.ServiceViewActivity;
import autokatta.com.view.StoreViewActivity;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 7/12/17.
 */

public class RecentVisitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    List<GetMyRecentVisitsResponse.Success.MyRecentVisit> mRecentVisitList;
    private String mMyContact;

    public RecentVisitsAdapter(FragmentActivity activity, List<GetMyRecentVisitsResponse.Success.MyRecentVisit> mRecentVisitList1, String mLoginContact) {
        mActivity = activity;
        mRecentVisitList = mRecentVisitList1;
        mMyContact = mLoginContact;
    }

    @Override
    public int getItemCount() {
        return mRecentVisitList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class RecentViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mLayoutType, mDateTime;
        ImageView mImageView;

        RecentViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.name);
            mLayoutType = (TextView) itemView.findViewById(R.id.layout);
            mDateTime = (TextView) itemView.findViewById(R.id.dateTime);
            mImageView = (ImageView) itemView.findViewById(R.id.Pic);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recent_visits, parent, false);
        return new RecentViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {

        final RecentViewHolder holder = (RecentViewHolder) holder1;

        switch (mRecentVisitList.get(holder.getAdapterPosition()).getLayOut()) {

            case "Profile":
                holder.mTitle.setText(mRecentVisitList.get(holder.getAdapterPosition()).getUserName());
                holder.mLayoutType.setText(mRecentVisitList.get(holder.getAdapterPosition()).getLayOut());
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getProfilePicture())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(mActivity, OtherProfile.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", mRecentVisitList.get(holder.getAdapterPosition()).getContactNo());
                        intent.putExtras(bundle);
                        mActivity.startActivity(intent, options.toBundle());
                    }
                });

                break;

            case "Store":
                holder.mTitle.setText(mRecentVisitList.get(holder.getAdapterPosition()).getStoreName());
                holder.mLayoutType.setText(mRecentVisitList.get(holder.getAdapterPosition()).getLayOut());
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getStoreImage())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b = new Bundle();
                        b.putInt("store_id", mRecentVisitList.get(holder.getAdapterPosition()).getStoreID());
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(mActivity, StoreViewActivity.class);
                        intent.putExtras(b);
                        mActivity.startActivity(intent, options.toBundle());
                    }
                });

                break;

            case "Group":
                break;
            case "UploadVehicle":
                holder.mTitle.setText(mRecentVisitList.get(holder.getAdapterPosition()).getUploadVehicleTitile());
                holder.mLayoutType.setText(mActivity.getString(R.string.vehicle));
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getUploadVehicleImage())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(mActivity, VehicleDetails.class);
                        intent.putExtra("vehicle_id", mRecentVisitList.get(holder.getAdapterPosition()).getUploadVehicleID());
                        mActivity.startActivity(intent, options.toBundle());
                    }
                });

                break;
            case "Product":
                holder.mTitle.setText(mRecentVisitList.get(holder.getAdapterPosition()).getProductName());
                holder.mLayoutType.setText(mRecentVisitList.get(holder.getAdapterPosition()).getLayOut());
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getProductImage())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(mActivity, ProductViewActivity.class);
                        intent.putExtra("product_id", mRecentVisitList.get(holder.getAdapterPosition()).getProductID());
                        mActivity.startActivity(intent, options.toBundle());
                    }
                });

                break;
            case "Service":
                holder.mTitle.setText(mRecentVisitList.get(holder.getAdapterPosition()).getStoreServiceName());
                holder.mLayoutType.setText(mRecentVisitList.get(holder.getAdapterPosition()).getLayOut());
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getStoreServiceImage())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(mActivity, ServiceViewActivity.class);
                        intent.putExtra("service_id", mRecentVisitList.get(holder.getAdapterPosition()).getStoreServiceID());
                        mActivity.startActivity(intent, options.toBundle());
                    }
                });
                break;
            case "New Vehicle":
                holder.mTitle.setText(mActivity.getString(R.string.new_vehicle));
                holder.mLayoutType.setText(mRecentVisitList.get(holder.getAdapterPosition()).getLayOut());
                holder.mDateTime.setText(mRecentVisitList.get(holder.getAdapterPosition()).getDate());

                Glide.with(mActivity)
                        .load(mActivity.getString(R.string.base_image_url) +
                                mRecentVisitList.get(holder.getAdapterPosition()).getNewVehicleImage())
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);

                holder.mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle mBundle = new Bundle();
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        mBundle.putInt("newVehicleId", mRecentVisitList.get(holder.getAdapterPosition()).getNewVehicleID());
                        Intent mIntent = new Intent(mActivity, NewVehicleDetails.class);
                        mIntent.putExtras(mBundle);
                        mActivity.startActivity(mIntent, options.toBundle());
                    }
                });
                break;
        }
    }


}
