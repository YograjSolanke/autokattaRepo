package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.NewVehicleAllResponse;

/**
 * Created by ak-003 on 12/10/17.
 */

public class NewVehicleContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();


    public NewVehicleContainerAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList) {
        mActivity = activity;
        mVehicleList = VehicleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_container, parent, false);
        return new YoHolder(mView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final YoHolder mVehicleHolder = (YoHolder) holder;

        mVehicleHolder.mCategory.setText("mCategory");
        mVehicleHolder.mSubCategory.setText("subcategory");
        mVehicleHolder.mBrand.setText("brand");
        mVehicleHolder.mModel.setText("model");
        mVehicleHolder.mVersion.setText("version");


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

}
