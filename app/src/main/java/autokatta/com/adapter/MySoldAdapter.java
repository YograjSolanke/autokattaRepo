package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.SoldVehicleResponse;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-001 on 20/9/17.
 */

public class MySoldAdapter extends RecyclerView.Adapter<MySoldAdapter.VehicleHolder> {

    Activity activity;
    List<SoldVehicleResponse.Success.SoldVehicle> mMainList;

    public MySoldAdapter(Activity activity, List<SoldVehicleResponse.Success.SoldVehicle> mMainList) {
        this.activity = activity;
        this.mMainList = mMainList;
    }

    static class VehicleHolder extends RecyclerView.ViewHolder {
        ImageView vehicleimage;
        Button delete;
        CardView mcardView;
        TextView mCustName, mSoldDate, mCustAddress, mCustContact, mSoldVehicleName, mCategory, mSubCategory, mBrand, mModel;

        VehicleHolder(View itemView) {
            super(itemView);
            mSoldVehicleName = (TextView) itemView.findViewById(R.id.sold_vehicle_name);
            mCategory = (TextView) itemView.findViewById(R.id.category);
            mSubCategory = (TextView) itemView.findViewById(R.id.sub_category);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mModel = (TextView) itemView.findViewById(R.id.model);
            vehicleimage = (ImageView) itemView.findViewById(R.id.vehiprofile);
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
            mCustName = (TextView) itemView.findViewById(R.id.customer_name);
            mSoldDate = (TextView) itemView.findViewById(R.id.sold_date);
            mCustAddress = (TextView) itemView.findViewById(R.id.address);
            mCustContact = (TextView) itemView.findViewById(R.id.editcontact);
        }
    }

    @Override
    public MySoldAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_sold_vehicle_adapter, parent, false);
        return new VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(final MySoldAdapter.VehicleHolder holder, int position) {
        holder.mCustName.setText(mMainList.get(position).getCustName());
        holder.mSoldDate.setText(mMainList.get(position).getSoldDate());
        holder.mCustAddress.setText(mMainList.get(position).getAddress());
        holder.mCustContact.setText(mMainList.get(position).getSoldToContact());
        holder.mSoldVehicleName.setText(mMainList.get(position).getTitle());
        holder.mCategory.setText(mMainList.get(position).getCategory());
        holder.mSubCategory.setText(mMainList.get(position).getSubCategory());
        holder.mBrand.setText(mMainList.get(position).getManufacturer());
        holder.mModel.setText(mMainList.get(position).getModel());

         /* Vehicle pic */

        if (mMainList.get(position).getImage() == null ||
                mMainList.get(position).getImage().equals("") ||
                mMainList.get(position).getImage().equals("null")) {
            holder.vehicleimage.setBackgroundResource(R.drawable.vehiimg);
        } else {
            Glide.with(activity)
                    .load(activity.getString(R.string.base_image_url) + mMainList.get(position).getImage())
                    .bitmapTransform(new CropSquareTransformation(activity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .into(holder.vehicleimage);
        }

        holder.mcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent mVehicleDetails = new Intent(activity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                activity.startActivity(mVehicleDetails, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }
}
