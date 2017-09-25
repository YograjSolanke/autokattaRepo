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

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.VehicleForCompareResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 25/9/17.
 */

public class CompareVehicleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<VehicleForCompareResponse.Success.UsedVehicle> mVehicleCompareList = new ArrayList<>();

    public CompareVehicleListAdapter(Activity activity, List<VehicleForCompareResponse.Success.UsedVehicle> mVehicleCompareList1) {
        mActivity = activity;
        mVehicleCompareList = mVehicleCompareList1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_compare_vehicle_list, parent, false);
        return new VehicleHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VehicleHolder mVehicleHolder = (VehicleHolder) holder;

        mVehicleHolder.mBrand.setText(mVehicleCompareList.get(position).getManufacturer());
        mVehicleHolder.mInsuranceValid.setText(mVehicleCompareList.get(position).getInsuranceValid());
        mVehicleHolder.mInsurenceIDV.setText(mVehicleCompareList.get(position).getInsuranceIdv());
        mVehicleHolder.mOwner.setText(mVehicleCompareList.get(position).getNoOfOwners());
        mVehicleHolder.mFuel.setText(mVehicleCompareList.get(position).getFualType());
        mVehicleHolder.mColor.setText(mVehicleCompareList.get(position).getColor());
        mVehicleHolder.mRc.setText(mVehicleCompareList.get(position).getRcAvailable());
        mVehicleHolder.mFitness.setText(mVehicleCompareList.get(position).getFitnessValidity());
        mVehicleHolder.mPermitValid.setText(mVehicleCompareList.get(position).getPermitValidity());
        mVehicleHolder.mSeatingCap.setText(mVehicleCompareList.get(position).getSeatingCapacity());
        mVehicleHolder.mHypo.setText(mVehicleCompareList.get(position).getHypothication());
        mVehicleHolder.mDrive.setText(mVehicleCompareList.get(position).getDrive());
        mVehicleHolder.mBody.setText(mVehicleCompareList.get(position).getBodyType());
        mVehicleHolder.mApplication.setText(mVehicleCompareList.get(position).getApplication());
        mVehicleHolder.mLocationCity.setText(mVehicleCompareList.get(position).getLocationCity());
        mVehicleHolder.mPrice.setText(mVehicleCompareList.get(position).getPrice());
        mVehicleHolder.mKms.setText(String.valueOf(mVehicleCompareList.get(position).getKmsRunning()));
        mVehicleHolder.mRegNo.setText(mVehicleCompareList.get(position).getRegistrationNumber());
        mVehicleHolder.mEngine.setText(mVehicleCompareList.get(position).getEngineNo());
        mVehicleHolder.mChasis.setText(mVehicleCompareList.get(position).getChassisNo());
        mVehicleHolder.mMfgYear.setText(mVehicleCompareList.get(position).getYearOfManufacture());
        mVehicleHolder.mTaxAvailable.setText(mVehicleCompareList.get(position).getTaxValidity());
        mVehicleHolder.mModel.setText(mVehicleCompareList.get(position).getModel());
        mVehicleHolder.mRegYear.setText(mVehicleCompareList.get(position).getYearOfRegistration());
        mVehicleHolder.mTaxPaid.setText(mVehicleCompareList.get(position).getTaxPaidUpto());

        mVehicleHolder.vehicleRating.setRating(0);

        mVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mVehicleCompareList.get(mVehicleHolder.getAdapterPosition()).getContactNo());
            }
        });

        if (mVehicleCompareList.get(position).getImage().equals("") || mVehicleCompareList.get(position).getImage() == null ||
                mVehicleCompareList.get(position).getImage().equals("null")) {
            mVehicleHolder.mVehiImg.setBackgroundResource(R.drawable.vehiimg);
        } else {
            String dppath = mActivity.getString(R.string.base_image_url) + mVehicleCompareList.get(position).getImage();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mVehicleHolder.mVehiImg);
        }
    }

    @Override
    public int getItemCount() {
        return mVehicleCompareList.size();
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
        TextView mBrand, mInsuranceValid, mInsurenceIDV, mOwner, mFuel, mColor, mRc, insvaltext, mTaxPaid,
                mFitness, mPermitValid, mSeatingCap, permittext, mHypo, mDrive, transmissiontext, mBody,
                boattext, rvtext, mApplication, mLocationCity, mPrice, mKms, tyretext, bustypetext,
                airtext, invoicetext, impltext, mRegNo, mEngine, mChasis, mMfgYear, mTaxAvailable,
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
            mTaxAvailable = (TextView) itemView.findViewById(R.id.txttaxavailable);
            mTaxPaid = (TextView) itemView.findViewById(R.id.txttaxpaid);
            mVehiImg = (ImageView) itemView.findViewById(R.id.imgvehicle);
            vehicleRating = (RatingBar) itemView.findViewById(R.id.vehi_rating);

            mCall = (ImageView) itemView.findViewById(R.id.call);

        }
    }
}
