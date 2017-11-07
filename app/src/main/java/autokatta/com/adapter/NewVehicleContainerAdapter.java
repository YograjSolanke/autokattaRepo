package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.NewVehicleAllResponse;
import autokatta.com.view.AddManualEnquiry;
import autokatta.com.view.NewVehicleDetails;

/**
 * Created by ak-003 on 12/10/17.
 */

public class NewVehicleContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    String myContact;

    private OnLoadMoreListener loadMoreListener;
    final int TYPE_DATA = 0;
    private final int TYPE_LOAD = 1;
    private boolean isLoading = false, isMoreDataAvailable = true;

    public NewVehicleContainerAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList, String myContact) {
        mActivity = activity;
        mVehicleList = VehicleList;
        mConnectionDetector = new ConnectionDetector(activity);
        this.myContact = myContact;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        if (viewType == TYPE_DATA) {
            return new YoHolder(inflater.inflate(R.layout.adapter_new_vehicle_container, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
       /* View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_container, parent, false);
        return new YoHolder(mView);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_DATA) {
            final YoHolder mVehicleHolder = (YoHolder) holder1;

            mVehicleHolder.mCategory.setText(mVehicleList.get(position).getCategoryName());
            mVehicleHolder.mSubCategory.setText(mVehicleList.get(position).getSubCategoryName());
            mVehicleHolder.mBrand.setText(mVehicleList.get(position).getBrandName());
            mVehicleHolder.mModel.setText(mVehicleList.get(position).getModelName());
            mVehicleHolder.mVersion.setText(mVehicleList.get(position).getVersionName());

        /*
        More Items click listener...
         */
            mVehicleHolder.mMoreItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = mActivity.getLayoutInflater().inflate(R.layout.custom_more_used_vehicle, null);
                    ImageView mClose = (ImageView) view.findViewById(R.id.close);
                    Button mEnquiry = (Button) view.findViewById(R.id.Enquiry);
                    Button mQuotation = (Button) view.findViewById(R.id.quotation);
                    mQuotation.setVisibility(View.GONE);
                    Button mTransferStock = (Button) view.findViewById(R.id.transfer_stock);
                    mTransferStock.setVisibility(View.GONE);
                    Button mSold = (Button) view.findViewById(R.id.delete);
                    mSold.setVisibility(View.GONE);
                    Button mViewQuote = (Button) view.findViewById(R.id.view_quotation);
                    mViewQuote.setVisibility(View.GONE);
                    Button mOfferRecived = (Button) view.findViewById(R.id.offerrecived);
                    mOfferRecived.setVisibility(View.GONE);

                    final Dialog mBottomSheetDialog = new Dialog(mActivity, R.style.MaterialDialogSheet);

                    mBottomSheetDialog.setContentView(view);
                    mBottomSheetDialog.setCancelable(true);
                    mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mBottomSheetDialog.show();

                    mClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBottomSheetDialog.dismiss();
                        }
                    });

                 /*
                Enquiry...
                 */
                    mEnquiry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBottomSheetDialog.dismiss();
                            ActivityOptions option = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Bundle b = new Bundle();
                            b.putString("sender", "");
                            b.putString("sendername", "");
                            b.putString("keyword", "New Vehicle");
                            b.putString("category", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getCategoryName());
                            b.putString("title", "New Vehicle");
                            b.putString("brand", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getBrandName());
                            b.putString("model", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getModelName());
                            b.putString("price", "NA");
                            b.putString("image", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getImage());
                            b.putInt("id", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getNewVehicleID());
                            b.putString("classname", "NewVehicleContainerAdapter");

                            Intent intent = new Intent(mActivity, AddManualEnquiry.class);
                            intent.putExtras(b);
                            mActivity.startActivity(intent, option.toBundle());
                        }
                    });
                }
            });

            mVehicleHolder.mDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("newVehicleId", mVehicleList.get(mVehicleHolder.getAdapterPosition()).getNewVehicleID());
                    Intent mIntent = new Intent(mActivity, NewVehicleDetails.class);
                    mIntent.putExtras(mBundle);
                    mActivity.startActivity(mIntent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mVehicleList.get(position).getNewVehicleID() != 0) {
            return TYPE_DATA;
        } else {
            return TYPE_LOAD;
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    /*
    For loading progress bar...
     */
    static class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View itemView) {
            super(itemView);
        }
    }

    private static class YoHolder extends RecyclerView.ViewHolder {
        TextView mCategory, mSubCategory, mBrand, mModel, mVersion;
        Button mDetails;
        ImageView mMoreItems;

        YoHolder(View itemView) {
            super(itemView);
            mCategory = (TextView) itemView.findViewById(R.id.editcategory);
            mSubCategory = (TextView) itemView.findViewById(R.id.editsubcategory);
            mBrand = (TextView) itemView.findViewById(R.id.editbrand);
            mModel = (TextView) itemView.findViewById(R.id.editmodel);
            mVersion = (TextView) itemView.findViewById(R.id.editversion);
            mDetails = (Button) itemView.findViewById(R.id.vehibtndetails);
            mMoreItems = (ImageView) itemView.findViewById(R.id.more_items);
        }
    }
}
