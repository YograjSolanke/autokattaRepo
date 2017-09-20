package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetGroupQuotationResponse;
import autokatta.com.view.MyVehicleQuotationListActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 19/9/17.
 */

public class GetGroupQuotationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GetGroupQuotationResponse.Success.UsedVehicle> groupQuotationList;
    private Activity mActivity;
    private String mLoginContact;
    private int mGroupId;

    public GetGroupQuotationAdapter(Activity activity1, List<GetGroupQuotationResponse.Success.UsedVehicle> groupQuotationList1,
                                    String mLoginContact1, int mGrpId) {
        mActivity = activity1;
        groupQuotationList = groupQuotationList1;
        mLoginContact = mLoginContact1;
        mGroupId = mGrpId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_get_group_quotation, parent, false);
        return new QuotationHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final QuotationHolder holder = (QuotationHolder) holder1;

        holder.Title.setText(groupQuotationList.get(position).getTitle());
        holder.Category.setText(groupQuotationList.get(position).getCategory());
        holder.Brand.setText(groupQuotationList.get(position).getManufacturer());
        holder.Model.setText(groupQuotationList.get(position).getModel());
        holder.Price.setText(groupQuotationList.get(position).getPrice());

        if (groupQuotationList.get(position).getImage().equalsIgnoreCase("") ||
                groupQuotationList.get(position).getImage() == null) {
            holder.Image.setBackgroundResource(R.drawable.vehiimg);
        } else {
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + groupQuotationList.get(position).getImage())
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .into(holder.Image);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Title", holder.Title.getText().toString());
                bundle.putString("Category", holder.Category.getText().toString());
                bundle.putString("Brand", holder.Brand.getText().toString());
                bundle.putString("Model", holder.Model.getText().toString());
                bundle.putString("Price", holder.Price.getText().toString());
                bundle.putString("Image", groupQuotationList.get(holder.getAdapterPosition()).getImage());
                bundle.putInt("bundle_GroupId", mGroupId);
                bundle.putInt("bundle_VehicleId", groupQuotationList.get(holder.getAdapterPosition()).getVehicleId());
                bundle.putString("bundle_Type", groupQuotationList.get(holder.getAdapterPosition()).getType());
                bundle.putString("bundle_Contact", groupQuotationList.get(holder.getAdapterPosition()).getContactNo());

                Intent intent = new Intent(mActivity, MyVehicleQuotationListActivity.class);
                intent.putExtras(bundle);
                mActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return groupQuotationList.size();
    }

    private static class QuotationHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView Title, Category, Brand, Model, Keyword, Price;
        RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel;
        ImageView Image;
        LinearLayout linearButton;

        QuotationHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            Keyword = (TextView) itemView.findViewById(R.id.keyword);
            Title = (TextView) itemView.findViewById(R.id.settitle);
            Category = (TextView) itemView.findViewById(R.id.setcategory);
            Brand = (TextView) itemView.findViewById(R.id.setbrand);
            Model = (TextView) itemView.findViewById(R.id.setmodel);
            Image = (ImageView) itemView.findViewById(R.id.image);
            Price = (TextView) itemView.findViewById(R.id.setprice);
            relCategory = (RelativeLayout) itemView.findViewById(R.id.relative2);
            relBrand = (RelativeLayout) itemView.findViewById(R.id.relative3);
            relModel = (RelativeLayout) itemView.findViewById(R.id.relative4);
            relPrice = (RelativeLayout) itemView.findViewById(R.id.relative5);
            MainRel = (RelativeLayout) itemView.findViewById(R.id.MainRel);
            linearButton = (LinearLayout) itemView.findViewById(R.id.llButton);
            Keyword.setVisibility(View.GONE);
            linearButton.setVisibility(View.GONE);
        }
    }
}
