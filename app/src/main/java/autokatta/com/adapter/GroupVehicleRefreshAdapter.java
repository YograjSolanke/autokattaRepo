package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.view.VehicleDetails;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupVehicleRefreshAdapter extends RecyclerView.Adapter<GroupVehicleRefreshAdapter.MyViewHolder> {
    private Activity mActivity;
    private List<GetGroupVehiclesResponse.Success> mItemList = new ArrayList<>();

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mTitle, mPrice, mModel, mBrand, mUpdatedBy, mLocation, mRtoCity, mYearOfMfg, mKmsHrs;
        ImageView mShareAutokatta, mShareOther, mLike, mCall;
        ImageView mCardImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mRegistrationNo = (TextView) itemView.findViewById(R.id.registration_no);
            mTitle = (TextView) itemView.findViewById(R.id.card_title);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mModel = (TextView) itemView.findViewById(R.id.model);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mUpdatedBy = (TextView) itemView.findViewById(R.id.updated_by);
            mLocation = (TextView) itemView.findViewById(R.id.location);
            mRtoCity = (TextView) itemView.findViewById(R.id.rto_city);
            mYearOfMfg = (TextView) itemView.findViewById(R.id.year_of_mfg);
            mKmsHrs = (TextView) itemView.findViewById(R.id.kms_hrs);

            mShareAutokatta = (ImageView) itemView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageView) itemView.findViewById(R.id.share_other);
            mLike = (ImageView) itemView.findViewById(R.id.like);
            mCall = (ImageView) itemView.findViewById(R.id.call);
            mCardImage = (ImageView) itemView.findViewById(R.id.card_image);

        }
    }

    public GroupVehicleRefreshAdapter(Activity mActivity, List<GetGroupVehiclesResponse.Success> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    @Override
    public GroupVehicleRefreshAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_group_vehicle, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GroupVehicleRefreshAdapter.MyViewHolder holder, final int position) {
        String register = mItemList.get(position).getRegistrationNumber();
        SpannableString sp = new SpannableString(mActivity.getString(R.string.no_register) + register);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.no_register).length(),
                mActivity.getString(R.string.no_register).length() + register.length(), 0);
        holder.mRegistrationNo.setText(sp);

        holder.mTitle.setText(mItemList.get(position).getTitle());

        String price = mItemList.get(position).getPrice();
        SpannableString sp1 = new SpannableString(mActivity.getString(R.string.price) + price);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.price).length(),
                mActivity.getString(R.string.price).length() + price.length(), 0);
        holder.mPrice.setText(sp1);

        String model = mItemList.get(position).getModel();
        SpannableString sp2 = new SpannableString(mActivity.getString(R.string.model) + model);
        sp2.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.model).length(),
                mActivity.getString(R.string.model).length() + model.length(), 0);
        holder.mModel.setText(sp2);

        String brand = mItemList.get(position).getManufacturer();
        SpannableString sp3 = new SpannableString(mActivity.getString(R.string.brand_no) + brand);
        sp3.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.brand_no).length(),
                mActivity.getString(R.string.brand_no).length() + brand.length(), 0);
        holder.mBrand.setText(sp3);

        String uploaded_by = mItemList.get(position).getUsername();
        SpannableString sp4 = new SpannableString(mActivity.getString(R.string.updated_by) + uploaded_by);
        sp4.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.updated_by).length(),
                mActivity.getString(R.string.updated_by).length() + uploaded_by.length(), 0);
        holder.mUpdatedBy.setText(sp4);

        String location = mItemList.get(position).getLocationCity();
        SpannableString sp5 = new SpannableString(mActivity.getString(R.string.location) + location);
        sp5.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.location).length(),
                mActivity.getString(R.string.location).length() + location.length(), 0);
        holder.mLocation.setText(sp5);

        String rto_city = mItemList.get(position).getRTOCity();
        SpannableString sp6 = new SpannableString(mActivity.getString(R.string.rto_city) + rto_city);
        sp6.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.rto_city).length(),
                mActivity.getString(R.string.rto_city).length() + rto_city.length(), 0);
        holder.mRtoCity.setText(sp6);

        String year_of_mfg = mItemList.get(position).getYearOfManufacture();
        SpannableString sp7 = new SpannableString(mActivity.getString(R.string.year_of_mfg) + year_of_mfg);
        sp7.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.year_of_mfg).length(),
                mActivity.getString(R.string.year_of_mfg).length() + year_of_mfg.length(), 0);
        holder.mYearOfMfg.setText(sp7);

        String kms_hrs = mItemList.get(position).getKmsRunning();
        SpannableString sp8 = new SpannableString(mActivity.getString(R.string.kms_hrs) + kms_hrs);
        sp8.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.kms_hrs).length(),
                mActivity.getString(R.string.kms_hrs).length() + kms_hrs.length(), 0);
        holder.mKmsHrs.setText(sp8);

        if (mItemList.get(position).getImage().equals("") || mItemList.get(position).getImage().equals(null) ||
                mItemList.get(position).getImage().equals("null") ) {
            holder.mCardImage.setBackgroundResource(R.mipmap.ic_launcher);
        }else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getImage();
            Glide.with(mActivity)
                    .load(dppath)
                    //.bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mCardImage);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString("vehicle_id", mItemList.get(position).getVehicleId());

                Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                mActivity.startActivity(mVehicleDetails);
            }
        });
        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(position).getContact());
            }
        });
    }
    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
