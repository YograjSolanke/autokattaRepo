package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public NewVehicleListAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList) {
        mActivity = activity;
        mVehicleList = VehicleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_list, parent, false);
        return new YoHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YoHolder mVehicleHolder = (YoHolder) holder;
    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    private static class YoHolder extends RecyclerView.ViewHolder {

        YoHolder(View itemView) {
            super(itemView);
        }
    }
}
