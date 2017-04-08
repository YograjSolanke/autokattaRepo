package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import autokatta.com.response.GetAuctionEventResponse;

/**
 * Created by ak-001 on 5/4/17.
 */

public class PreviewAuctionAdapter extends RecyclerView.Adapter<PreviewAuctionAdapter.MyViewHolder> {
    private Activity mActivity;
    private List<GetAuctionEventResponse.Vehicle> mItemList = new ArrayList<>();

    public PreviewAuctionAdapter(Activity mActivity, List<GetAuctionEventResponse.Vehicle> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        ImageView mAuctionVehicleImage;
        TextView mRegistrationNo, mSetLotNo, mVehicleName, mVehicleBrand, mVehicleModel, mVehicleYearOfMfg,
                mVehicleKmsHrs, mVehicleLocation, mVehicleRtoCity, mViewMore;

        MyViewHolder(View itemView) {
            super(itemView);
            mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
            mAuctionVehicleImage = (ImageView) itemView.findViewById(R.id.auction_vehicle_image);

            mRegistrationNo = (TextView) itemView.findViewById(R.id.live_registration_no_txt);
            mSetLotNo = (TextView) itemView.findViewById(R.id.setlotno);
            mVehicleName = (TextView) itemView.findViewById(R.id.vehicle_name);
            mVehicleBrand = (TextView) itemView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) itemView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) itemView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) itemView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) itemView.findViewById(R.id.vehicle_locations);
            mVehicleRtoCity = (TextView) itemView.findViewById(R.id.vehicle_rto_city);
            mViewMore = (TextView) itemView.findViewById(R.id.view_more);
        }
    }

    @Override
    public PreviewAuctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_preview_live_event_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PreviewAuctionAdapter.MyViewHolder vh = new PreviewAuctionAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreviewAuctionAdapter.MyViewHolder holder, final int position) {
        if (mItemList.get(position).getImage() != null || mItemList.get(position).getImage().equals("")
                || mItemList.get(position).getImage().isEmpty()) {
            String images[] = mItemList.get(position).getImage().split(",");
            Glide.with(mActivity)
                    .load("http://autokatta.com/mobile/uploads/" + images[0].replaceAll(" ", "%20"))
                    //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .override(100, 100)
                    .into(holder.mAuctionVehicleImage);
        } else {
            holder.mAuctionVehicleImage.setBackgroundResource(R.drawable.hdlogo);
        }

        holder.mRegistrationNo.setText(mItemList.get(position).getRegNo());
        holder.mSetLotNo.setText(mItemList.get(position).getLotNo());
        holder.mVehicleName.setText(mItemList.get(position).getTitle());
        holder.mVehicleBrand.setText(mItemList.get(position).getBrand());
        holder.mVehicleModel.setText(mItemList.get(position).getModel());
        holder.mVehicleYearOfMfg.setText(mItemList.get(position).getYear());
        holder.mVehicleKmsHrs.setText(mItemList.get(position).getKmsRunning());
        holder.mVehicleLocation.setText(mItemList.get(position).getLocationCity());
        holder.mVehicleRtoCity.setText(mItemList.get(position).getRtoCity());

        holder.mViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mItemList.get(position).getVehicleId().startsWith("A ")) {
                                /*Bundle b = new Bundle();
                                MyAuctionVehicleDetails frag = new MyAuctionVehicleDetails();
                                b.putString("vehicle_id", VehiId);
                                b.putString("auction_id", auction_id);
                                frag.setArguments(b);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerView, frag);
                                fragmentTransaction.addToBackStack("myauctionvehicledetails");
                                fragmentTransaction.commit();*/
                } else {
                                /*Toast.makeText(getActivity(),"Admin vehicle",Toast.LENGTH_SHORT).show();
                                Bundle b = new Bundle();
                                b.putString("vehicle_id", VehiId);
                                b.putString("lotNo", lotNo.getText().toString());

                                AdminVehicleMoreDetails fragment = new AdminVehicleMoreDetails();
                                fragment.setArguments(b);
                                FragmentManager mFragmentManagerm = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = mFragmentManagerm.beginTransaction();
                                fragmentTransaction.replace(R.id.containerView, fragment).addToBackStack("adminvehiclemoredetails").commit();
*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
