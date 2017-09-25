package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.VehicleForCompareResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 25/9/17.
 */

public class CompareVehicleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    public CompareVehicleListAdapter(Activity activity, List<VehicleForCompareResponse.Success.UsedVehicle> mVehicleCompareList) {
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_compare_vehicle_list, parent, false);
        return new VehicleHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VehicleHolder mVehicleHolder = (VehicleHolder) holder;

        mVehicleHolder.mBrand.setText();
        mVehicleHolder.mInsuranceValid.setText();
        mVehicleHolder.mInsurenceIDV.setText();
        mVehicleHolder.mOwner.setText();
        mVehicleHolder.mFuel.setText();
        mVehicleHolder.mColor.setText();
        mVehicleHolder.mRc.setText();
        mVehicleHolder.mFitness.setText();
        mVehicleHolder.mPermitValid.setText();
        mVehicleHolder.mSeatingCap.setText();
        mVehicleHolder.mHypo.setText();
        mVehicleHolder.mDrive.setText();
        mVehicleHolder.mBody.setText();
        mVehicleHolder.mApplication.setText();
        mVehicleHolder.mLocationCity.setText();
        mVehicleHolder.mPrice.setText();
        mVehicleHolder.mLocationCity.setText();
        mVehicleHolder.mKms.setText();
        mVehicleHolder.mRegNo.setText();
        mVehicleHolder.mEngine.setText();
        mVehicleHolder.mChasis.setText();
        mVehicleHolder.mMfgYear.setText();
        mVehicleHolder.mTaxValid.setText();
        mVehicleHolder.mModel.setText();
        mVehicleHolder.mTaxValid.setText();
        mVehicleHolder.mRegYear.setText();

        mVehicleHolder.vehicleRating.setRating(0);

        mVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(mVehicleHolder.getAdapterPosition()).getContact());
            }
        });

        if (mItemList.get(position).getSingleImage().equals("") || mItemList.get(position).getSingleImage().equals(null) ||
                mItemList.get(position).getSingleImage().equals("null")) {
            holder.mCardImage.setBackgroundResource(R.drawable.vehiimg);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = mActivity.getString(R.string.base_image_url) + mItemList.get(position).getSingleImage();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mVehicleHolder.mVehiImg);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
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


    private static class VehicleHolder extends RecyclerView.ViewHolder {
        TextView mBrand, mInsuranceValid, mInsurenceIDV, mOwner, mFuel, mColor, mRc, insvaltext, taxpaidtext,
                mFitness, mPermitValid, mSeatingCap, permittext, mHypo, mDrive, transmissiontext, mBody,
                boattext, rvtext, mApplication, mLocationCity, mPrice, mKms, tyretext, bustypetext,
                airtext, invoicetext, impltext, mRegNo, mEngine, mChasis, mMfgYear, mTaxValid,
                mModel, versiontext, mRegYear, regcitytext;

        ImageView mVehiImg, mCall;
        RatingBar vehicleRating;

        VehicleHolder(View itemView) {
            super(itemView);

            mBrand = (TextView) itemView.findViewById(R.id.txtcompname);
            mModel = (TextView) itemView.findViewById(R.id.txtmodel);
            // versiontext=(TextView)itemView.findViewById(R.id.txtversion);
            mInsuranceValid = (TextView) itemView.findViewById(R.id.txtinsurenceval);
            mInsurenceIDV = (TextView) itemView.findViewById(R.id.txtinsurenceIdv);
            mOwner = (TextView) itemView.findViewById(R.id.txtowner);
            mLocationCity = (TextView) itemView.findViewById(R.id.txtcity);
            //  regcitytext=(TextView)itemView.findViewById(R.id.txtregcity);
            mRegYear = (TextView) itemView.findViewById(R.id.txtregyr);
            mRc = (TextView) itemView.findViewById(R.id.txtrcavailabel);
            // taxpaidtext=(TextView)itemView.findViewById(R.id.txttaxpaid);
            mFitness = (TextView) itemView.findViewById(R.id.txtfeatness);
            mSeatingCap = (TextView) itemView.findViewById(R.id.txtseatingcap);
            mPermitValid = (TextView) itemView.findViewById(R.id.txtpermit);
            mHypo = (TextView) itemView.findViewById(R.id.txthypo);
            mDrive = (TextView) itemView.findViewById(R.id.txtdrivetype);
            mBody = (TextView) itemView.findViewById(R.id.txtbodystyle);
            mApplication = (TextView) itemView.findViewById(R.id.txtappl);
            //tyretext=(TextView)itemView.findViewById(R.id.txttyrecon);
            //bustypetext=(TextView)itemView.findViewById(R.id.txtbustype);
            // airtext=(TextView)itemView.findViewById(R.id.txtair);
            // invoicetext=(TextView)itemView.findViewById(R.id.txtinvoice);
            // impltext=(TextView)itemView.findViewById(R.id.txtimpl);
            mFuel = (TextView) itemView.findViewById(R.id.txtfualtype);
            mColor = (TextView) itemView.findViewById(R.id.txtcolor);
            mRegNo = (TextView) itemView.findViewById(R.id.txtregno);
            mEngine = (TextView) itemView.findViewById(R.id.txtengineno);
            mChasis = (TextView) itemView.findViewById(R.id.txtchessicno);
            mPrice = (TextView) itemView.findViewById(R.id.txtprice);
            mKms = (TextView) itemView.findViewById(R.id.txtkm);
            mMfgYear = (TextView) itemView.findViewById(R.id.txtmakeyr);
            mTaxValid = (TextView) itemView.findViewById(R.id.txttaxavailable);
            mVehiImg = (ImageView) itemView.findViewById(R.id.imgvehicle);
            vehicleRating = (RatingBar) itemView.findViewById(R.id.vehi_rating);


            mCall = (ImageView) itemView.findViewById(R.id.call);

        }
    }
}
