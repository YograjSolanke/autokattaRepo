package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import autokatta.com.view.NewVehicleDetails;
import retrofit2.Response;

/**
 * Created by ak-003 on 16/10/17.
 */

public class StoreNewVehicleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    ApiCall mApiCall;
    String myContact;


    public StoreNewVehicleAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList, String myContact) {
        mActivity = activity;
        mVehicleList = VehicleList;
        mConnectionDetector = new ConnectionDetector(activity);
        mApiCall = new ApiCall(activity, this);
        this.myContact = myContact;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_store_new_vehicle, parent, false);
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

        YoHolder(View itemView) {
            super(itemView);

            mCategory = (TextView) itemView.findViewById(R.id.editcategory);
            mSubCategory = (TextView) itemView.findViewById(R.id.editsubcategory);
            mBrand = (TextView) itemView.findViewById(R.id.editbrand);
            mModel = (TextView) itemView.findViewById(R.id.editmodel);
            mVersion = (TextView) itemView.findViewById(R.id.editversion);
            mDetails = (Button) itemView.findViewById(R.id.vehibtndetails);
        }
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
                    , "StoreNewVehiclesAdapter");
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
               /* case "sold_success":
                    CustomToast.customToast(mActivity, "vehicle sold");
                    break;*/
            }
        }
    }
}
