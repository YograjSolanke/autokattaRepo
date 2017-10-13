package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.NewVehicleAllResponse;

/**
 * Created by ak-003 on 11/10/17.
 */

public class NewVehicleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();
    private Button mSelectStore;
    private CheckBox mSelectAllCheckBox;
    private List<Boolean> posABoolean;
    private List<String> mFinalVehiclelist;
    private boolean mSelectAll;

    public NewVehicleListAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList,
                                 Button mSelectStore, boolean mSelectAll, CheckBox mSelectAllCheckBox) {
        mActivity = activity;
        mVehicleList = VehicleList;
        this.mSelectStore = mSelectStore;
        this.mSelectAll = mSelectAll;
        this.mSelectAllCheckBox = mSelectAllCheckBox;
        posABoolean = new ArrayList<>(mVehicleList.size());
        mFinalVehiclelist = new ArrayList<>(mVehicleList.size());

        for (int i = 0; i < mVehicleList.size(); i++) {
            posABoolean.add(mSelectAll);

            if (mSelectAll == true) {
                mFinalVehiclelist.add(String.valueOf(mVehicleList.get(i).getNewVehicleID()));
                mSelectStore.setVisibility(View.VISIBLE);
            } else
            mFinalVehiclelist.add("0");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_list, parent, false);
        return new YoHolder(mView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final YoHolder mVehicleHolder = (YoHolder) holder;

        mVehicleHolder.mCategory.setText(mVehicleList.get(position).getCategoryName());
        mVehicleHolder.mSubCategory.setText(mVehicleList.get(position).getSubCategoryName());
        mVehicleHolder.mBrand.setText(mVehicleList.get(position).getBrandName());
        mVehicleHolder.mModel.setText(mVehicleList.get(position).getModelName());
        mVehicleHolder.mVersion.setText(mVehicleList.get(position).getVersionName());
        mVehicleHolder.mCheckBox.setChecked(mSelectAll);


        mVehicleHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    posABoolean.set(holder.getAdapterPosition(), true);
                    mFinalVehiclelist.set(holder.getAdapterPosition(), String.valueOf(mVehicleList.get(holder.getAdapterPosition()).getNewVehicleID()));
                } else {
                    posABoolean.set(holder.getAdapterPosition(), false);
                    mFinalVehiclelist.set(holder.getAdapterPosition(), "0");

//                    if(mSelectAllCheckBox.isChecked())
//                        mSelectAllCheckBox.setChecked(false);
                }

                //For Button visible/gone
                if (posABoolean.contains(true))
                    mSelectStore.setVisibility(View.VISIBLE);
                else
                    mSelectStore.setVisibility(View.GONE);
            }
        });

        mVehicleHolder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    private static class YoHolder extends RecyclerView.ViewHolder {

        TextView mCategory, mSubCategory, mBrand, mModel, mVersion;
        CheckBox mCheckBox;
        Button mDetails;

        YoHolder(View itemView) {
            super(itemView);

            mCategory = (TextView) itemView.findViewById(R.id.editcategory);
            mSubCategory = (TextView) itemView.findViewById(R.id.editsubcategory);
            mBrand = (TextView) itemView.findViewById(R.id.editbrand);
            mModel = (TextView) itemView.findViewById(R.id.editmodel);
            mVersion = (TextView) itemView.findViewById(R.id.editversion);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            mDetails = (Button) itemView.findViewById(R.id.vehibtndetails);
        }


    }

    public List<String> getVehicleIds() {
        return mFinalVehiclelist;
    }
}
