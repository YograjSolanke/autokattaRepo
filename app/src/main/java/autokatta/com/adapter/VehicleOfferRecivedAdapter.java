package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.fragment.BussinessMsgSenders;
import autokatta.com.response.VehicleOfferRecivedResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 27/9/17.
 */

public class VehicleOfferRecivedAdapter extends RecyclerView.Adapter<VehicleOfferRecivedAdapter.MyViewHolder> {
    private String fullpath = "";
    private Activity mActivity;
    private List<VehicleOfferRecivedResponse.Success> mItemList = new ArrayList<>();


    public VehicleOfferRecivedAdapter(Activity mActivity, List<VehicleOfferRecivedResponse.Success> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mKeyword, mTitle, mCategory, mBrand, mModel, mPrice;
        ImageView mImage;
        RelativeLayout mRelCategory, mRelBrand, mRelModel, mRelPrice;
        CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mKeyword = (TextView) itemView.findViewById(R.id.keyword);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitle = (TextView) itemView.findViewById(R.id.settitle);
            mCategory = (TextView) itemView.findViewById(R.id.setcategory);
            mBrand = (TextView) itemView.findViewById(R.id.setbrand);
            mModel = (TextView) itemView.findViewById(R.id.setmodel);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mPrice = (TextView) itemView.findViewById(R.id.setprice);
            mRelCategory = (RelativeLayout) itemView.findViewById(R.id.relative2);
            mRelBrand = (RelativeLayout) itemView.findViewById(R.id.relative3);
            mRelModel = (RelativeLayout) itemView.findViewById(R.id.relative4);
            mRelPrice = (RelativeLayout) itemView.findViewById(R.id.relative5);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle b = new Bundle();

           /* if (mKeyword.getText().toString().equals("Product")) {
                b.putInt("product_id", mItemList.get(getAdapterPosition()).getProductId());
                b.putInt("service_id", 0);
                b.putInt("vehicle_id", 0);
                b.putString("keyword", "Products");
                b.putString("title", mItemList.get(getAdapterPosition()).getProductName());
                b.putString("price", mItemList.get(getAdapterPosition()).getPrice());
                b.putString("category", "");
                b.putString("brand", "");
                b.putString("model", "");
                b.putString("image", mItemList.get(getAdapterPosition()).getImages());
            }
            if (mKeyword.getText().toString().equals("Service")) {
                b.putInt("product_id", 0);
                b.putInt("service_id", mItemList.get(getAdapterPosition()).getId());
                b.putInt("vehicle_id", 0);
                b.putString("keyword", "Services");
                b.putString("title", mItemList.get(getAdapterPosition()).getName());
                b.putString("price", mItemList.get(getAdapterPosition()).getPrice());
                b.putString("category", "");
                b.putString("brand", "");
                b.putString("model", "");
                b.putString("image", mItemList.get(getAdapterPosition()).getImages());
            }*/

            if (mKeyword.getText().toString().equals("Vehicle")) {
                b.putInt("product_id", 0);
                b.putInt("service_id", 0);
                b.putInt("vehicle_id", mItemList.get(getAdapterPosition()).getVehicleId());
                b.putString("keyword", "Used Vehicle");
                b.putString("title", mItemList.get(getAdapterPosition()).getVeihicletitle());
                b.putString("price", mItemList.get(getAdapterPosition()).getPrice());
                b.putString("category", mItemList.get(getAdapterPosition()).getSubcategory());
                b.putString("brand", mItemList.get(getAdapterPosition()).getManufacturer());
                b.putString("model", mItemList.get(getAdapterPosition()).getModel());
                b.putString("image", mItemList.get(getAdapterPosition()).getImage());
            }

            BussinessMsgSenders obj = new BussinessMsgSenders();
            obj.setArguments(b);

            ((FragmentActivity) mActivity).getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.ok_left_to_right, R.anim.ok_right_to_left)
                    .replace(R.id.bussines_chat_container, obj, "vehicleofferrecived")
                    .addToBackStack("vehicleofferrecived")
                    .commit();

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bussiness_chat_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mKeyword.setText(mItemList.get(position).getKeyword());
        holder.mTitle.setText(mItemList.get(position).getVeihicletitle());

        holder.mCategory.setText(mItemList.get(position).getSubcategory());
        holder.mBrand.setText(mItemList.get(position).getSubcategory());
        holder.mModel.setText(mItemList.get(position).getModel());
        holder.mPrice.setText(mItemList.get(position).getPrice());

        String vehi_img_url = mActivity.getString(R.string.base_image_url);
        if (mItemList.get(position).getKeyword().equalsIgnoreCase("Vehicle"))
            fullpath = vehi_img_url + mItemList.get(position).getImage();

        fullpath = fullpath.replaceAll(" ", "%20");
        try {
            Glide.with(mActivity)
                    .load(fullpath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .placeholder(R.drawable.logo)
                    .into(holder.mImage);

        } catch (Exception e) {
            System.out.println("Error in uploading images");
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}

