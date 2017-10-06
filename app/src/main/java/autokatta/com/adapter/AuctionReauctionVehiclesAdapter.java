package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.AddVehiclesForAuctionFragment;
import autokatta.com.response.AuctionAllVehicleData;

/**
 * Created by ak-003 on 3/4/17.
 */

public class AuctionReauctionVehiclesAdapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater mInflater;
    private List<AuctionAllVehicleData> UploadedVehiclesData = new ArrayList<>();
    private List<Boolean> positionArray;
    private List<AuctionAllVehicleData> checkedVehiclesData;
    private EditText editNoOfVehicles;

    public AuctionReauctionVehiclesAdapter(Activity activity
            , List<AuctionAllVehicleData> UploadedVehiclesData, EditText editNoOfVehicles) {

        this.activity = activity;
        this.UploadedVehiclesData = UploadedVehiclesData;
        this.editNoOfVehicles = editNoOfVehicles;

        positionArray = new ArrayList<>(UploadedVehiclesData.size());
        checkedVehiclesData = new ArrayList<>(UploadedVehiclesData.size());

        for (int i = 0; i < UploadedVehiclesData.size(); i++) {
            positionArray.add(false);
        }

        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return UploadedVehiclesData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        TextView texttitle;
        TextView textcategory;
        TextView textbrand;
        TextView textmodel;
        TextView textcity;
        TextView textcolor;
        TextView textreg;
        TextView textYOM;
        ImageView textimage;

        CheckBox checkBox;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_auction_vehicles, null);
            holder = new ViewHolder();

            holder.texttitle = (TextView) convertView.findViewById(R.id.vehicletitle);
            holder.textcategory = (TextView) convertView.findViewById(R.id.vehiclecategory);
            holder.textbrand = (TextView) convertView.findViewById(R.id.vehiclebrand);
            holder.textmodel = (TextView) convertView.findViewById(R.id.vehiclemodel);
            holder.textcity = (TextView) convertView.findViewById(R.id.vehiclertocity);
            holder.textcolor = (TextView) convertView.findViewById(R.id.vehiclecolor);
            holder.textreg = (TextView) convertView.findViewById(R.id.vehiclereg);
            holder.textYOM = (TextView) convertView.findViewById(R.id.vehicle_manu_year);
            holder.textimage = (ImageView) convertView.findViewById(R.id.byselfimage);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);
        }

        final AuctionAllVehicleData obj = UploadedVehiclesData.get(position);

        holder.texttitle.setText(obj.getVehicleTitle());
        holder.textcategory.setText(obj.getVehicleCategory());
        holder.textbrand.setText(obj.getVehicleBrand());
        holder.textmodel.setText(obj.getVehicleModel());
        holder.textcity.setText(obj.getVehicleLocation_city());
        holder.textcolor.setText(obj.getVehicleColor());
        holder.textreg.setText(obj.getVehicleRegistrationNo());
        holder.textYOM.setText(obj.getVehicleMfgYear());

        if (obj.getVehicleSingleImage() != null || !obj.getVehicleSingleImage().isEmpty() || !obj.getVehicleSingleImage().equals("")) {

            Glide.with(activity)
                    .load(activity.getString(R.string.base_image_url) + obj.getVehicleSingleImage().replaceAll(" ", "%20"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.textimage);

        } else
            holder.textimage.setBackgroundResource(R.drawable.vehiimg);

        holder.checkBox.setFocusable(false);

        holder.checkBox.setChecked(positionArray.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    AddVehiclesForAuctionFragment.IntVehicleNo++;
                    editNoOfVehicles.setText(String.valueOf(AddVehiclesForAuctionFragment.IntVehicleNo));

                    positionArray.set(position, true);
                    checkedVehiclesData.add(obj);

                } else {

                    AddVehiclesForAuctionFragment.IntVehicleNo--;
                    editNoOfVehicles.setText(String.valueOf(AddVehiclesForAuctionFragment.IntVehicleNo));

                    positionArray.set(position, false);
                    checkedVehiclesData.remove(obj);
                }
            }

        });

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });


        return convertView;
    }

    public List<AuctionAllVehicleData> checkboxVehicleData() {
        return checkedVehiclesData;
    }

}
