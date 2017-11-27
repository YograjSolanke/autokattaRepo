package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.ModelSuggestionsResponse;
import autokatta.com.view.VehicleDetails;

/**
 * Created by ak-003 on 27/11/17.
 */

public class SuggestionVehicleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<ModelSuggestionsResponse> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    public SuggestionVehicleAdapter(Activity mActivity1, List<ModelSuggestionsResponse> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
        mActivity = mActivity1;
        mSuggestionList = storeResponseArrayList1;
        mSuggestionAbout = txtSuggestion;
        this.mLoginContact = mLoginContact;
    }

    @Override
    public int getItemCount() {
        return mSuggestionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        //return mSuggestionList.get(position).getLayoutId();
        //return Integer.parseInt("-2");
    }


    /*Vehicle Suggestions Class...*/
    private static class VehicleSuggestions extends RecyclerView.ViewHolder {
        CardView mVehicleCardView;
        ImageView mVehiclePic;
        Button mView;
        TextView mVehicleName, mVehicleCategory, mVehicleBrand, mVehicleModel, mVehicleYear, mVehicleLocation;

        private VehicleSuggestions(View vehicleView) {
            super(vehicleView);
            mVehicleCardView = (CardView) vehicleView.findViewById(R.id.store_card_view);
            mVehiclePic = (ImageView) vehicleView.findViewById(R.id.vehiclePic);
            mView = (Button) vehicleView.findViewById(R.id.btnView);
            mVehicleName = (TextView) vehicleView.findViewById(R.id.vehicleName);
            mVehicleCategory = (TextView) vehicleView.findViewById(R.id.vehicleCategory);
            mVehicleBrand = (TextView) vehicleView.findViewById(R.id.vehicleBrand);
            mVehicleModel = (TextView) vehicleView.findViewById(R.id.vehicleModel);
            mVehicleYear = (TextView) vehicleView.findViewById(R.id.vehicleYear);
            mVehicleLocation = (TextView) vehicleView.findViewById(R.id.vehicleLocation);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("viewType", "Suggestion->" + viewType);
        View mView;

        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_vehicle_layout_adapter, parent, false);
        return new VehicleSuggestions(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final VehicleSuggestions mVehicleVSuggestions = (VehicleSuggestions) holder;
        mVehicleVSuggestions.mVehicleName.setText(mSuggestionList.get(position).getName());
        mVehicleVSuggestions.mVehicleCategory.setText(mSuggestionList.get(position).getVehicleCategory());
        mVehicleVSuggestions.mVehicleYear.setText(mSuggestionList.get(position).getVehicleMfgYear());
        mVehicleVSuggestions.mVehicleBrand.setText(mSuggestionList.get(position).getVehicleBrand());
        mVehicleVSuggestions.mVehicleModel.setText(mSuggestionList.get(position).getVehicleModel());
        mVehicleVSuggestions.mVehicleLocation.setText(mSuggestionList.get(position).getLocation());

        mSuggestionAbout.setText("Suggestion Based On Vehicle");

        if (mSuggestionList.get(position).getImage().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mVehicleVSuggestions.mVehiclePic);
        } else {
            mVehicleVSuggestions.mVehiclePic.setBackgroundResource(R.drawable.vehiimg);
        }

        mVehicleVSuggestions.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("vehicle_id", mSuggestionList.get(mVehicleVSuggestions.getAdapterPosition()).getVehicleId());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                mActivity.startActivity(mVehicleDetails, options.toBundle());
            }
        });

    }
}
