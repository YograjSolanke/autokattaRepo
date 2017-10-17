package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import autokatta.com.view.AddManualEnquiry;
import autokatta.com.view.NewVehicleDetails;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * Created by ak-003 on 12/10/17.
 */

public class NewVehicleContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private List<String> mGrouplist = new ArrayList<>();
    private HashMap<String, Integer> mGrouplist1 = new HashMap<>();
    private List<String> parsedData = new ArrayList<>();
    private int groupid = 0;
    private String groupname;
    ApiCall mApiCall;
    String myContact;


    public NewVehicleContainerAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList, String myContact) {
        mActivity = activity;
        mVehicleList = VehicleList;
        mConnectionDetector = new ConnectionDetector(activity);
        mApiCall = new ApiCall(activity, this);
        this.myContact = myContact;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_container, parent, false);
        return new YoHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YoHolder mVehicleHolder = (YoHolder) holder;

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

    @Override
    public int getItemCount() {
        return mVehicleList.size();
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

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "NewVehicleContainerAdapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                /*case "success":
                    //CustomToast.customToast(activity, "vehicle deleted");
                    break;
                case "success_notification":
                    CustomToast.customToast(mActivity, "notification sent");
                    break;
                case "success_added":
                    CustomToast.customToast(mActivity, "data updated");
                    break;*/
                case "sent_quotation":
                    CustomToast.customToast(mActivity, "quotation sent");
                    break;
               /* case "sold_success":
                    CustomToast.customToast(mActivity, "vehicle sold");
                    break;*/
            }
        }
    }

}
