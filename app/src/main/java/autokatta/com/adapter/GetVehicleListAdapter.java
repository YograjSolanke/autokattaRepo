package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetVehicleListResponse;

/**
 * Created by ak-001 on 20/3/17.
 */

public class GetVehicleListAdapter extends BaseAdapter {
    Activity mContext;
    List<GetVehicleListResponse.Success> mVehicleList;

    public GetVehicleListAdapter(Activity mContext, List<GetVehicleListResponse.Success> mVehicleList) {
        this.mContext = mContext;
        this.mVehicleList = mVehicleList;
    }

    @Override
    public int getCount() {
        return mVehicleList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mVehicleList.get(position).hashCode();
    }

    class VehicleListHolder{
        TextView mName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VehicleListHolder vehicleListHolder = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_get_vehicle, null);

            vehicleListHolder = new VehicleListHolder();
            vehicleListHolder.mName = (TextView) convertView.findViewById(R.id.vehicle_name);
            convertView.setTag(vehicleListHolder);
        }else {
            vehicleListHolder = (VehicleListHolder) convertView.getTag();
        }
        GetVehicleListResponse.Success success = mVehicleList.get(position);
        vehicleListHolder.mName.setText(success.getName());
        return convertView;
    }
}
