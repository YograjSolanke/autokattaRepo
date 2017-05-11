package autokatta.com.adapter;

import android.app.Activity;
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
import autokatta.com.request.ManualEnquiryRequest;

/**
 * Created by ak-001 on 5/5/17.
 */

public class ManualEnquiryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    List<ManualEnquiryRequest> mItemList = new ArrayList<>();

    public ManualEnquiryAdapter(Activity mActivity, List<ManualEnquiryRequest> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    /*
   Used Vehicle Details...
    */
    private static class UsedVehicleDetails extends RecyclerView.ViewHolder {
        ImageView mVehiclePic;
        TextView mVehicleName, mVehicleCategory, mVehicleSubCategory, mVehicleModel, mVehiclePrice, mVehicleCount;

        private UsedVehicleDetails(View profileView) {
            super(profileView);
            mVehiclePic = (ImageView) profileView.findViewById(R.id.vehicle_icon);
            mVehicleName = (TextView) profileView.findViewById(R.id.vehicle_title);
            mVehicleCategory = (TextView) profileView.findViewById(R.id.category_str);
            mVehicleCount = (TextView) profileView.findViewById(R.id.count);
            mVehicleSubCategory = (TextView) profileView.findViewById(R.id.sub_category_str);
            mVehicleModel = (TextView) profileView.findViewById(R.id.model_str);
            mVehiclePrice = (TextView) profileView.findViewById(R.id.price_str);
        }
    }

    /*
   Product Details...
    */
    private static class ProductDetails extends RecyclerView.ViewHolder {
        ImageView mProductPic;
        TextView mProductName, mProductCategory, mProductType, mProductPrice, mProductCount;

        private ProductDetails(View profileView) {
            super(profileView);
            mProductPic = (ImageView) profileView.findViewById(R.id.product_image);
            mProductName = (TextView) profileView.findViewById(R.id.product_name);
            mProductCount = (TextView) profileView.findViewById(R.id.count);
            mProductCategory = (TextView) profileView.findViewById(R.id.product_category_str);
            mProductType = (TextView) profileView.findViewById(R.id.product_type_str);
            mProductPrice = (TextView) profileView.findViewById(R.id.product_price_str);
        }
    }

    /*
   Service Details...
    */
    private static class ServiceDetails extends RecyclerView.ViewHolder {
        ImageView mServicePic;
        TextView mServiceName, mServiceCategory, mServiceType, mServicePrice, mServiceCount;

        private ServiceDetails(View profileView) {
            super(profileView);
            mServicePic = (ImageView) profileView.findViewById(R.id.service_icon);
            mServiceName = (TextView) profileView.findViewById(R.id.service_title);
            mServiceCount = (TextView) profileView.findViewById(R.id.count);
            mServiceCategory = (TextView) profileView.findViewById(R.id.category_str);
            mServiceType = (TextView) profileView.findViewById(R.id.type_str);
            mServicePrice = (TextView) profileView.findViewById(R.id.price_str);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return mItemList.get(position).getLayoutNo();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case 1:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_manual_enquiry, parent, false);
                return new UsedVehicleDetails(mView);

            case 2:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_enquiry, parent, false);
                return new ProductDetails(mView);

            case 3:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_service, parent, false);
                return new ServiceDetails(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                UsedVehicleDetails vehicleDetails = (UsedVehicleDetails) holder;
                vehicleDetails.mVehicleName.setText(mItemList.get(position).getVehicleName());
                vehicleDetails.mVehicleCategory.setText(mItemList.get(position).getVehicleCategory());
                vehicleDetails.mVehicleSubCategory.setText(mItemList.get(position).getVehicleSubCategory());
                vehicleDetails.mVehicleModel.setText(mItemList.get(position).getVehicleModel());
                vehicleDetails.mVehiclePrice.setText(mItemList.get(position).getVehiclePrice());
                vehicleDetails.mVehicleCount.setText(mItemList.get(position).getEnquiryCount());

                if (mItemList.get(position).getVehicleImage().equals("") || mItemList.get(position).getVehicleImage().equals("null")
                        || mItemList.get(position).getVehicleImage().equals(null)) {
                    vehicleDetails.mVehiclePic.setBackgroundResource(R.drawable.hdlogo);
                } else {
                    String used_pic = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getVehicleImage();
                    Glide.with(mActivity)
                            .load(used_pic)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(vehicleDetails.mVehiclePic);
                }
                break;

            case 2:
                ProductDetails productDetails = (ProductDetails) holder;
                productDetails.mProductName.setText(mItemList.get(position).getProductName());
                productDetails.mProductCategory.setText(mItemList.get(position).getProductCategory());
                productDetails.mProductType.setText(mItemList.get(position).getProductType());
                productDetails.mProductPrice.setText(mItemList.get(position).getProductPrice());
                productDetails.mProductCount.setText(mItemList.get(position).getEnquiryCount());

                if (mItemList.get(position).getProductImage().equals("") || mItemList.get(position).getProductImage().equals("null")
                        || mItemList.get(position).getProductImage().equals(null)) {
                    productDetails.mProductPic.setBackgroundResource(R.drawable.hdlogo);
                } else {
                    String product_pic = "http://autokatta.com/mobile/Product_pics/" + mItemList.get(position).getProductImage();
                    Glide.with(mActivity)
                            .load(product_pic)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(productDetails.mProductPic);
                }
                break;

            case 3:
                ServiceDetails serviceDetails = (ServiceDetails) holder;
                serviceDetails.mServiceName.setText(mItemList.get(position).getServiceName());
                serviceDetails.mServiceCategory.setText(mItemList.get(position).getServiceCategory());
                serviceDetails.mServiceType.setText(mItemList.get(position).getServiceType());
                serviceDetails.mServicePrice.setText(mItemList.get(position).getServicePrice());
                serviceDetails.mServiceCount.setText(mItemList.get(position).getEnquiryCount());

                if (mItemList.get(position).getServiceImage().equals("") || mItemList.get(position).getServiceImage().equals("null")
                        || mItemList.get(position).getServiceImage().equals(null)) {
                    serviceDetails.mServicePic.setBackgroundResource(R.drawable.hdlogo);
                } else {
                    String service_pic = "http://autokatta.com/mobile/Service_pics/" + mItemList.get(position).getServiceImage();
                    Glide.with(mActivity)
                            .load(service_pic)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(serviceDetails.mServicePic);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
