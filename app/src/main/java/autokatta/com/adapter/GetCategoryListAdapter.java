package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import autokatta.com.R;

/**
 * Created by ak-005 on 7/6/17.
 */

public class GetCategoryListAdapter  extends BaseAdapter {
    private Activity mContext;
    private ArrayList mCategoryList;

    public GetCategoryListAdapter(Activity mContext, ArrayList mVehicleList) {
        this.mContext = mContext;
        this.mCategoryList = mVehicleList;
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mCategoryList.get(position).hashCode();
    }

    private class VehicleListHolder{
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
      //  GetVehicleListResponse.Success success = mCategoryList.get(position);
      //  vehicleListHolder.mName.setText(success.getName());
        return convertView;
    }
}
